package gg.quartzdev.qmobsdropeggs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EntityDeathListener implements Listener {
    boolean REQUIRES_PERMISSION = false;
    double DROP_PERCENTAGE = 1;

    List<EntityType> blacklistedSpawnEggs;

    public EntityDeathListener(){
        blacklistedSpawnEggs = new ArrayList<>();
        blacklistedSpawnEggs.add(EntityType.ELDER_GUARDIAN);
        blacklistedSpawnEggs.add(EntityType.ENDER_DRAGON);
        blacklistedSpawnEggs.add(EntityType.WARDEN);
        blacklistedSpawnEggs.add(EntityType.WITHER);
        blacklistedSpawnEggs.add(EntityType.WANDERING_TRADER);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        if(player == null) return;

//        checks perms if needed
        if(REQUIRES_PERMISSION && !player.hasPermission("qmobloot.player")) return;

//        checks if mob needs a spawn egg
        EntityType entityType = entity.getType();
        if(blacklistedSpawnEggs.contains(entityType)) return;
        ItemStack item = createSpawnEgg(entity.getType());
        if(item == null) return;

//        RNG
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float rng = random.nextFloat(0.01F, 100.0F);

//        Factor in looting odds (1% per looting level)
        if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS))
            rng = rng+ player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

//        Add to mob drops if rng passes
        if(rng <= DROP_PERCENTAGE)
            event.getDrops().add(item);
    }

    private ItemStack createSpawnEgg(EntityType type){
        Material spawnEggMaterial = Bukkit.getItemFactory().getSpawnEgg(type);
        if(spawnEggMaterial == null) return null;
        return new ItemStack(spawnEggMaterial);
    }
}
