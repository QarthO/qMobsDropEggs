package gg.quartzdev.qmobsdropeggs.listeners;

import gg.quartzdev.qmobsdropeggs.qConfig;
import gg.quartzdev.qmobsdropeggs.qMobsDropEggs;
import gg.quartzdev.qmobsdropeggs.util.qLogger;
import gg.quartzdev.qmobsdropeggs.util.qUtil;
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

        Player player = event.getEntity().getKiller();
        if(config.requiresPlayerKiller() && player == null) return;

//        If a player killed the mob, check if they have the permission
        if(player != null && config.killerRequiresPermission()) {
//            Case-sensitive - I was dumb and had it 'qMDE' so check both to maintain backwards compatibility
            if(!player.hasPermission("qmde.killer") && !player.hasPermission("qMDE.killer")) return;
        }

//        Spawn Reason check
        SpawnReason spawnReason = entity.getEntitySpawnReason();
        if(config.getBlacklistedSpawnReasons().contains(spawnReason)){
            if(!config.invertBlacklistedSpawnReasons()){
                return;
            }
        }

//        Mob check
        EntityType mobType = entity.getType();
        if(config.isBlacklistedMob(mobType)) return;
        ItemStack spawnEgg = createSpawnEgg(entity.getType());
        if(spawnEgg == null) return;

//        RNG
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float rng = random.nextFloat(0.01F, 100.0F);

        double dropChance = config.getDropChance(mobType);

//        Looting check
        if(player != null && config.factoringLooting()) {
            if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOTING)) {
                int lootingLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
                if (config.complexLooting() && dropChance < config.complexLootingBreakpoint())
                    dropChance = dropChance * (1 + lootingLevel);
                else
                    dropChance = dropChance + lootingLevel;
            }
        }
//        Checks RNG
        if(rng <= dropChance)
//            Adds spawn egg
            event.getDrops().add(spawnEgg);
    }

    private @Nullable ItemStack createSpawnEgg(EntityType type){
        Material spawnEggMaterial = Bukkit.getItemFactory().getSpawnEgg(type);
        if(spawnEggMaterial == null) return null;
        return new ItemStack(spawnEggMaterial);
    }
}
