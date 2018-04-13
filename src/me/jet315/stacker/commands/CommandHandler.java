package me.jet315.stacker.commands;

import me.jet315.stacker.commands.admincommands.KillAllCommand;
import me.jet315.stacker.commands.admincommands.ReloadCommand;
import me.jet315.stacker.commands.defaultcommands.KillStack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jet on 26/01/2018.
 */
public class CommandHandler implements org.bukkit.command.CommandExecutor {

    private Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();


    public CommandHandler() {
        commands.put("reload", new ReloadCommand());
        commands.put("killall", new KillAllCommand());
        commands.put("killstack", new KillStack());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (cmd.getName().equalsIgnoreCase("mobstacker")) {
            if (args.length == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("&7&m----------&r &6MobStacker &aCommands &7&m----------\n");
                if(sender.hasPermission("mobstacker.admin")){
                    stringBuilder.append(ChatColor.translateAlternateColorCodes('&',"&a&l/MS reload &e- &aUsed to reload the configuration file\n"));
                    stringBuilder.append(ChatColor.translateAlternateColorCodes('&',"&a&l/MS killall &e- &aUsed to kill all living entities (Apart from Tamed, Leashed & those in a disabled world)\n"));
                    stringBuilder.append(ChatColor.translateAlternateColorCodes('&',"&a&l/MS killstack &e- &aUsed to kill the entire mob stack when killed\n"));
                }
                stringBuilder.append("&7&m----------&r &aCreated by  Jet315 (Spigot) &7&m----------");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', stringBuilder.toString()));

                return true;
            }


            if (args[0] != null) {
                String name = args[0].toLowerCase();
                if (commands.containsKey(name)) {
                    final CommandExecutor command = commands.get(name);

                    if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                        return true;
                    }

                    if (!command.isBoth()) {
                        if (command.isConsole() && sender instanceof Player) {
                            sender.sendMessage(ChatColor.RED + "Only console can use that command!");
                            return true;
                        }
                        if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
                            sender.sendMessage(ChatColor.RED + "Only players can use that command!");
                            return true;
                        }
                    }

                    if (command.getLength() > args.length) {
                        sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
                        return true;
                    }

                    command.execute(sender, args);
                }
            }
        }
        return true;
    }
}
