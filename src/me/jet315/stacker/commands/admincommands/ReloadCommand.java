package me.jet315.stacker.commands.admincommands;

import me.jet315.stacker.MobStacker;
import me.jet315.stacker.commands.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Jet on 26/01/2018.
 */
public class ReloadCommand extends CommandExecutor {

    public ReloadCommand() {

        setCommand("reload");
        setPermission("mobstacker.admin");
        setLength(1);
        setBoth();
        setUsage("/mobstacker reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        long startTime = System.currentTimeMillis();
        MobStacker.getInstance().reloadConfig();
        MobStacker.getInstance().getMobStackerConfig().reloadConfig();
        long endtime = System.currentTimeMillis() - startTime;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&6MobStacker&7] &aConfiguration file successfully reloaded"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&oReload took: &6" + endtime + "ms"));
    }
}