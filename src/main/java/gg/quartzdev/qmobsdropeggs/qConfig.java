package gg.quartzdev.qmobsdropeggs;

import gg.quartzdev.qmobsdropeggs.util.Language;
import gg.quartzdev.qmobsdropeggs.util.qLogger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class qConfig {

    qMobsDropEggs plugin;
    qLogger qLOGGER;
    FileConfiguration file;

//    CONFIG OPTIONS
    String CONFIG_VERSION;
    boolean CHECK_UPDATES;
    boolean REQUIRES_PLAYER_KILLER;
    boolean KILLER_REQUIRES_PERMISSION;
    Set<SpawnReason> BLACKLISTED_SPAWN_REASONS;
    boolean INVERT_BLACKLISTED_SPAWN_REASONS;
    Set<World> DISABLED_WORLDS;
    boolean FACTOR_LOOTING;
    boolean COMPLEX_LOOTING;
    double COMPLEX_LOOTING_BREAKPOINT;
    double DEFAULT_DROP_CHANCE;
    HashMap<EntityType, Double> DROP_CHANCES;
    Set<EntityType> BLACKLISTED_MOBS;

    public qConfig(){
        this.plugin = qMobsDropEggs.getInstance();
        qLOGGER = qMobsDropEggs.getLOGGER();
        this.file = this.plugin.getConfig();
        this.plugin.saveDefaultConfig();
        this.DISABLED_WORLDS = new HashSet<>();
        this.BLACKLISTED_SPAWN_REASONS = new HashSet<>();
        this.DROP_CHANCES = new HashMap<>();
        this.BLACKLISTED_MOBS = new HashSet<>();
        this.loadAll();
    }

    private void save(){
        this.plugin.saveConfig();
    }

    public void reload(){
        this.plugin.reloadConfig();
        this.file = this.plugin.getConfig();
        this.loadAll();
    }

    public void loadAll(){
        this.loadCheckUpdates();
        this.loadRequiresPlayerKiller();
        this.loadKillerRequiresPermission();
        this.loadBlacklistedSpawnReasons();
        this.loadInvertBlacklistedSpawnReasons();
        this.loadDisabledWorlds();
        this.loadFactorLooting();
        this.loadComplexLooting();
        this.loadComplexLootingBreakpoint();
        this.loadMobDropChance();
        this.loadBlacklistedMobs();
    }

    private void loadCheckUpdates(){
        this.CHECK_UPDATES = this.file.getBoolean("check-updates");
    }

    public boolean checkingUpdates(){
        return this.CHECK_UPDATES;
    }

    private void loadRequiresPlayerKiller(){
        this.REQUIRES_PLAYER_KILLER = this.file.getBoolean("requires-player-killer");
    }

    public boolean requiresPlayerKiller(){
        return this.REQUIRES_PLAYER_KILLER;
    }

    private void loadKillerRequiresPermission(){
        this.KILLER_REQUIRES_PERMISSION = this.file.getBoolean("killer-requires-permissions");
    }

    public boolean killerRequiresPermission(){
        return this.KILLER_REQUIRES_PERMISSION;
    }

    public Set<SpawnReason> getBlacklistedSpawnReasons(){
        return this.BLACKLISTED_SPAWN_REASONS;
    }

    public void loadBlacklistedSpawnReasons(){
        this.BLACKLISTED_SPAWN_REASONS.clear();
        List<String> blacklistedSpawnReasonNames = this.file.getStringList("blacklisted-spawn-reasons");
        if(blacklistedSpawnReasonNames.isEmpty()) return;
        for(String spawnReasonName : blacklistedSpawnReasonNames){
            try{
                SpawnReason spawnReason = SpawnReason.valueOf(spawnReasonName);
                BLACKLISTED_SPAWN_REASONS.add(spawnReason);
            } catch(IllegalArgumentException e){
                qLOGGER.error(Language.ERROR_SPAWN_REASON_NOT_FOUND.parse("spawn-reason", spawnReasonName));
            }
        }
    }

    public boolean invertBlacklistedSpawnReasons(){
        return this.INVERT_BLACKLISTED_SPAWN_REASONS;
    }

    public void loadInvertBlacklistedSpawnReasons(){
        this.INVERT_BLACKLISTED_SPAWN_REASONS = this.file.getBoolean("invert-blacklisted-spawn-reasons");
    }

    private void loadDisabledWorlds(){
        this.DISABLED_WORLDS.clear();
        List<String> disabledWorldNames = this.file.getStringList("disabled-worlds");
        if(disabledWorldNames.isEmpty()) return;
        for(String worldName : disabledWorldNames){
            World world = Bukkit.getWorld(worldName);
            if(world == null)
                qLOGGER.error(Language.ERROR_WORLD_NOT_FOUND.parse("world", worldName));
            else {
                DISABLED_WORLDS.add(world);
            }
        }
    }

    public boolean isDisabledWorld(World world){
        return DISABLED_WORLDS.contains(world);
    }

    private void loadFactorLooting() {
        this.FACTOR_LOOTING = this.file.getBoolean("factor-looting");
    }

    public boolean factoringLooting(){
        return this.FACTOR_LOOTING;
    }

    private void loadComplexLooting(){
        this.COMPLEX_LOOTING = this.file.getBoolean("complex-looting");
    }

    public boolean complexLooting(){
        return this.COMPLEX_LOOTING;
    }

    private void loadComplexLootingBreakpoint(){
        this.COMPLEX_LOOTING_BREAKPOINT = this.file.getDouble("complex-looting-breakpoint");
    }

    public double complexLootingBreakpoint(){
        return this.COMPLEX_LOOTING_BREAKPOINT;
    }

    private void loadMobDropChance(){
        DROP_CHANCES.clear();
        ConfigurationSection dropChances = this.file.getConfigurationSection("drop-chance");
        this.DEFAULT_DROP_CHANCE = dropChances.getDouble("default");
        ConfigurationSection mobDropChances = dropChances.getConfigurationSection("mobs");
        Set<String> mobs = mobDropChances.getKeys(false);
        for(String mobName : mobs){
            double chance = mobDropChances.getDouble(mobName);
            if(mobName.equals("default")){
                this.DEFAULT_DROP_CHANCE = chance;
                continue;
            }
            try{
                EntityType type = EntityType.valueOf(mobName);
                DROP_CHANCES.put(type, chance);
            } catch(IllegalArgumentException e){
                qLOGGER.error(Language.ERROR_MOB_NOT_FOUND.parse("mob", mobName));
            }
        }
    }

    public double getDropChance(EntityType mobType){
        if(DROP_CHANCES.containsKey(mobType))
            return DROP_CHANCES.get(mobType);
        return this.DEFAULT_DROP_CHANCE;
    }

    private void loadBlacklistedMobs(){
        BLACKLISTED_MOBS.clear();
        List<String> blacklistedMobs = this.file.getStringList("blacklisted-mobs");
        for(String mobName : blacklistedMobs){
            try{
                EntityType type = EntityType.valueOf(mobName);
                BLACKLISTED_MOBS.add(type);
            } catch(IllegalArgumentException e){
                qLOGGER.error(Language.ERROR_MOB_NOT_FOUND.parse("mob", mobName));
            }
        }

    }

    public boolean isBlacklistedMob(EntityType type){
        return BLACKLISTED_MOBS.contains(type);
    }
}
