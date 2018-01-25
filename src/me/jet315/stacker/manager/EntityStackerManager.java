package me.jet315.stacker.manager;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.jet315.stacker.MobStacker;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jet on 24/01/2018.
 */
public class EntityStackerManager {

    private int timeout;
    private int mobStackRadius;
    private Set<EntityType> entitiesToStack;
    private ArrayList<LivingEntity> validEnity = new ArrayList<>();
    private ArrayList<LivingEntity> entitiesToMultiplyOnDeath = new ArrayList<>();
    private static final StateFlag ENTITY_FLAG = new StateFlag("entity-stacking", true);

    public EntityStackerManager(int timeout, int mobStackRadius, Set<EntityType> entitiesToStack) {
        this.timeout = timeout;
        this.mobStackRadius = mobStackRadius;
        this.entitiesToStack = entitiesToStack;
        startEntityClock();

    }

    private void startEntityClock(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MobStacker.getInstance(), new Runnable() {
            public void run() {
                // Iterate through all worlds
                for (World world : Bukkit.getServer().getWorlds()) {
                    // Iterate through all entities in this world (if not disabled)
                    if(MobStacker.getInstance().getMobStackerConfig().disabledWorlds.contains(world)) continue;
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if(!checkEntity(entity)) continue;

                        // Iterate through all entities in range
                        for (Entity nearby : entity.getNearbyEntities(mobStackRadius, mobStackRadius, mobStackRadius)) {

                            if(checkEntity(nearby)) {
                                MobStacker.getInstance().getStackEntity().stack(entity, (LivingEntity) nearby);
                            }
                        }
                    }
                }

            }
        }, 0L, timeout);

    }


    public ArrayList<LivingEntity> getEntitiesToMultiplyOnDeath() {
        return entitiesToMultiplyOnDeath;
    }

    private boolean checkEntity(Entity entity){
        if(!(entity instanceof LivingEntity)){
            return false;
        }
        if(!entity.isValid()){
            return false;
        }
        if (entity.getType() == EntityType.PLAYER) {
            return false;
        }
        if(!entitiesToStack.contains(entity.getType())){
            return false;
        }
        if(MobStacker.getInstance().getMobStackerConfig().worldguardEnabled) {
            ApplicableRegionSet region = WGBukkit.getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation());
            for(ProtectedRegion r : region.getRegions()){
                for(String s : MobStacker.getInstance().getMobStackerConfig().disabledRegions){
                    if(r.getId().equalsIgnoreCase(s)){
                        return false;
                    }
                }
            }
        }
        if(((LivingEntity) entity).isLeashed() && !MobStacker.getInstance().getMobStackerConfig().stackLeachedMobs){
            return false;
        }
        if(entity instanceof Tameable){
            if(!MobStacker.getInstance().getMobStackerConfig().stackTamedMobs){
                return false;
            }
        }
        if(MobStacker.getInstance().getMobStackerConfig().stackOnlySpawnerMobs){
            if (!validEnity.contains((LivingEntity) entity)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<LivingEntity> getValidEntity() {
        return this.validEnity;
    }
}
