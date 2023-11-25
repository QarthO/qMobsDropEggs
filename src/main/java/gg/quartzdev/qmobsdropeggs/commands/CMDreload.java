package gg.quartzdev.qmobsdropeggs.commands;

import gg.quartzdev.qmobsdropeggs.qMobsDropEggs;
import gg.quartzdev.qmobsdropeggs.util.Language;
import gg.quartzdev.qmobsdropeggs.util.qUtil;
import org.bukkit.command.CommandSender;

public class CMDreload extends qCMD{

    public CMDreload(String name, String label) {
        super(name, label);
        this.permissionGroup = "qmbde.admin";
        this.permissionNode = "qmbde.command.reload";
    }

    @Override
    public boolean logic(CommandSender sender, String[] args) {
        qMobsDropEggs.config.reload();
        qUtil.sendMessage(sender, Language.RELOAD_COMPLETE);
        return false;
    }
}
