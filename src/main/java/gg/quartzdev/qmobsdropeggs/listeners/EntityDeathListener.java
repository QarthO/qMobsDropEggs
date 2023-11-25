package gg.quartzdev.qmobsdropeggs.listeners;

import gg.quartzdev.qmobsdropeggs.qConfig;
import gg.quartzdev.qmobsdropeggs.qMobsDropEggs;
import gg.quartzdev.qmobsdropeggs.util.qLogger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EntityDeathListener implements Listener {
    qMobsDropEggs plugin;
    qConfig config;
    qLogger logger;

    public EntityDeathListener(){
        this.plugin = qMobsDropEggs.instance;
        this.logger = qMobsDropEggs.logger;
        this.config = qMobsDropEggs.config;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        World world = entity.getWorld();

//        World check
        if(config.isDisabledWorld(world)) return;

//        Spawn Reason check
        SpawnReason spawnReason = entity.getEntitySpawnReason();
        if(config.getBlacklistedSpawnReasons().contains(spawnReason)) return;

//        Gets killer
        Player player = event.getEntity().getKiller();

//        Player & Permission check
        if(config.requiresPlayerKiller()){
            if(player == null) return;
            if(config.killerRequiresPermission())
                if(!player.hasPermission("qMDE.killer")) return;
        }

//        Mob check
        EntityType mobType = entity.getType();
        if(config.isBlacklistedMob(mobType)) return;
        ItemStack item = createSpawnEgg(entity.getType());
        if(item == null) return;

//        RNG
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float rng = random.nextFloat(0.01F, 100.0F);

//        Looting check (+1% drop chance per looting level)
        if(player != null)
            if(config.factoringLooting())
                if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS))
                    rng = rng+ player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

//        Checks RNG
        if(rng <= config.getDropChance(mobType))
//            Adds spawn egg
            event.getDrops().add(item);
    }

    private @Nullable ItemStack createSpawnEgg(EntityType type){
        Material spawnEggMaterial = Bukkit.getItemFactory().getSpawnEgg(type);
        if(spawnEggMaterial == null) return null;
        return new ItemStack(spawnEggMaterial);
    }
}
