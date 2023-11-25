package gg.quartzdev.qmobsdropeggs;

import gg.quartzdev.qmobsdropeggs.commands.CommandManager;
import gg.quartzdev.qmobsdropeggs.listeners.EntityDeathListener;
import gg.quartzdev.qmobsdropeggs.util.qLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

public final class qMobsDropEggs extends JavaPlugin {

    public static qMobsDropEggs instance;
    public static qLogger logger;

    public static qConfig config;

    public static qMobsDropEggs getInstance(){
        return instance;
    }

    public static qLogger getLOGGER(){
        return logger;
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = new qLogger();
        config = new qConfig();

        int pluginId = 20381; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        new CommandManager(this.getPluginMeta().getName().toLowerCase());

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
