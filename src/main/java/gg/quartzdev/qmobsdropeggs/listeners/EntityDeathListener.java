package gg.quartzdev.qmobsdropeggs.listeners;

import gg.quartzdev.qmobsdropeggs.qConfig;
import gg.quartzdev.qmobsdropeggs.qMobsDropEggs;
import gg.quartzdev.qmobsdropeggs.util.qLogger;
import gg.quartzdev.qmobsdropeggs.util.qUtil;
import net.kyori.adventure.text.Component;
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
        Bukkit.getServer().sendMessage(Component.text("entity died"));
        Entity entity = event.getEntity();
        World world = entity.getWorld();

//        World check
        if(config.isDisabledWorld(world)) return;
        Bukkit.getServer().sendMessage(Component.text("not disabled world"));

        Player player = event.getEntity().getKiller();
        if(config.requiresPlayerKiller() && player == null) return;
        Bukkit.getServer().sendMessage(Component.text("doesnt require player"));

//        If a player killed the mob, check if they have the permission
        if(player != null && config.killerRequiresPermission()) {
            Bukkit.getServer().sendMessage(Component.text("player not null and requires permissions"));
//            Case-sensitive - I was dumb and had it 'qMDE' so check both to maintain backwards compatibility
            if(!player.hasPermission("qmde.killer") && !player.hasPermission("qMDE.killer")) return;
        }
        Bukkit.getServer().sendMessage(Component.text("player has permission"));

//        Spawn Reason check
        SpawnReason spawnReason = entity.getEntitySpawnReason();
        if(config.getBlacklistedSpawnReasons().contains(spawnReason)){
            Bukkit.getServer().sendMessage(Component.text("spawn reason is blacklisted"));
            if(!config.invertBlacklistedSpawnReasons()){
                return;
            }
            Bukkit.getServer().sendMessage(Component.text("but we're inverting blacklist"));
        }

//        Mob check
        EntityType mobType = entity.getType();
        if(config.isBlacklistedMob(mobType)) return;
        Bukkit.getServer().sendMessage(Component.text("not a blacklisted mob"));
        ItemStack spawnEgg = createSpawnEgg(entity.getType());
        if(spawnEgg == null) return;
        Bukkit.getServer().sendMessage(Component.text("found a spawn egg for this mob type"));

//        RNG
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float rng = random.nextFloat(0.01F, 100.0F);

        double dropChance = config.getDropChance(mobType);

//        Looting check
        if(player != null && config.factoringLooting()) {
            Bukkit.getServer().sendMessage(Component.text("player not null so we're factoring looting"));
            if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOTING)) {
                Bukkit.getServer().sendMessage(Component.text("player has looting"));
                int lootingLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
                if (config.complexLooting() && dropChance < config.complexLootingBreakpoint())
                    dropChance = dropChance * (1 + lootingLevel);
                else
                    dropChance = dropChance + lootingLevel;
            }
        }

        Bukkit.getServer().sendMessage(Component.text("looting math finished"));
//        Checks RNG
        if(rng <= dropChance)
        {

            Bukkit.getServer().sendMessage(Component.text("rng passed agg dropping"));
//            Adds spawn egg
            event.getDrops().add(spawnEgg);
            return;
        }
        Bukkit.getServer().sendMessage(Component.text("rng failed not dropping egg"));
    }

    private @Nullable ItemStack createSpawnEgg(EntityType type){
        Material spawnEggMaterial = Bukkit.getItemFactory().getSpawnEgg(type);
        if(spawnEggMaterial == null) return null;
        return new ItemStack(spawnEggMaterial);
    }
}
