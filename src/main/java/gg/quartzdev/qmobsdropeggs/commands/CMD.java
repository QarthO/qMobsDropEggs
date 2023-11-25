package gg.quartzdev.qmobsdropeggs.commands;

import gg.quartzdev.qmobsdropeggs.qMobsDropEggs;
import gg.quartzdev.qmobsdropeggs.util.Language;
import gg.quartzdev.qmobsdropeggs.util.qUtil;
import org.bukkit.command.CommandSender;

public class CMD  extends qCMD{

    public CMD(String name, String label) {
        super(name, label);
        this.permissionGroup = "qmbde.admin";
        this.permissionNode = "qmbde.command.version";
    }

    @Override
    public boolean logic(CommandSender sender, String[] args) {
        qUtil.sendMessage(sender, Language.PLUGIN_INFO.parse("version", qMobsDropEggs.getInstance().getPluginMeta().getVersion()));
        return false;
    }
}
