package me.jet315.stacker.commands.defaultcommands;

import me.jet315.stacker.MobStacker;
import me.jet315.stacker.commands.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class KillStack extends CommandExecutor {

    public KillStack() {

        setCommand("killstack");
        setPermission("mobstacker.killstack");
        setLength(1);
        setPlayer();
        setUsage("/mobstacker killstack");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(MobStacker.getInstance().getEntityStacker().getInstantKillPlayers().contains(sender.getName())){
            MobStacker.getInstance().getEntityStacker().getInstantKillPlayers().remove(sender.getName());
            sender.sendMessage(ChatColor.GREEN + "KillStacks " + ChatColor.RED + "DISABLED");
        }else{
            MobStacker.getInstance().getEntityStacker().getInstantKillPlayers().add(sender.getName());
            sender.sendMessage(ChatColor.GREEN + "KillStacks " + ChatColor.GREEN + "ENABLED");
        }
    }
}