package me.jet315.stacker;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.jet315.stacker.commands.CommandHandler;
import me.jet315.stacker.events.OnEntityDamage;
import me.jet315.stacker.events.OnEntityDeath;
import me.jet315.stacker.events.OnEntitySpawn;
import me.jet315.stacker.events.OnSlimeSplit;
import me.jet315.stacker.manager.EntityStackerManager;
import me.jet315.stacker.manager.StackEntity;
import me.jet315.stacker.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jet on 24/01/2018.
 */
public class MobStacker extends JavaPlugin{

    private static MobStacker mobStacker;
    private EntityStackerManager entityStacker;
    private StackEntity stackEntity;
    private Config config;


    @Override
    public void onEnable() {

        mobStacker = this;

        createConfig();
        config = new Config(this.getConfig());


        entityStacker = new EntityStackerManager(config.stackRadius,config.mobsToStack);
        stackEntity = new StackEntity(config);

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new OnEntityDeath(), this);
        Bukkit.getPluginManager().registerEvents(new OnEntityDamage(), this);

        //World Guard
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            config.worldguardEnabled = false;
        }

        //Register commands
        getCommand("mobstacker").setExecutor(new CommandHandler());

    }


    @Override
    public void onDisable(){

    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    public static MobStacker getInstance() {
        return mobStacker;
    }


    public Config getMobStackerConfig() {
        return config;
   }

    public StackEntity getStackEntity() {
        return stackEntity;
    }

    public EntityStackerManager getEntityStacker() {
        return entityStacker;
    }
}
