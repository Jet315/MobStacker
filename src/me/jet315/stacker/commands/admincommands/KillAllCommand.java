package me.jet315.stacker.commands.admincommands;

import me.jet315.stacker.MobStacker;
import me.jet315.stacker.commands.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

/**
 * Created by Jet on 26/01/2018.
 */
public class KillAllCommand extends CommandExecutor {

    public KillAllCommand() {

        setCommand("killall");
        setPermission("mobstacker.admin");
        setLength(1);
        setBoth();
        setUsage("/mobstacker killall");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        long startTime = System.currentTimeMillis();
        int mobKillerCounter = 0;
        for (World world : Bukkit.getServer().getWorlds()) {
            // Iterate through all entities in this world (if not disabled)
            if(MobStacker.getInstance().getMobStackerConfig().disabledWorlds.contains(world)) continue;
            for (LivingEntity entity : world.getLivingEntities()) {
                if(!(entity instanceof LivingEntity)) continue;
                if(!entity.isValid()) continue;
                if (entity.getType() == EntityType.PLAYER) continue;
                if(entity.getType() == EntityType.PLAYER) continue;
                if(entity.isLeashed()) continue;
                if(entity instanceof Tameable) continue;
                if(entity.getType() == EntityType.ARMOR_STAND) continue;
                MobStacker.getInstance().getStackEntity().unstackAll(entity);
                mobKillerCounter++;
            }
        }
        long endtime = System.currentTimeMillis() - startTime;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&6MobStacker&7] &aAll mobs were successfully removed\n&6" + mobKillerCounter +" Living Entities removed\n&b&oKillAll took: &6" + endtime + "ms"));
    }
}