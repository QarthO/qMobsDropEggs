package gg.quartzdev.qmobsdropeggs;

import gg.quartzdev.qmobsdropeggs.listeners.EntityDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class qMobsDropEggs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(){
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
    }
}
