package me.jet315.stacker.events;

import me.jet315.stacker.MobStacker;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Created by Jet on 25/01/2018.
 */
public class OnEntitySpawn implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (MobStacker.getInstance().getMobStackerConfig().mobsToStack.contains(e.getEntityType())) {
                MobStacker.getInstance().getEntityStacker().getValidEntity().add(e.getEntity());
            }
        }
        if(e.getEntity().getType() == EntityType.SLIME){
            Slime slime = (Slime) e.getEntity();
            //valid display name
            if(MobStacker.getInstance().getStackEntity().parseAmount(slime.getCustomName()) != -1){
                slime.setSize(4);
            }
        }
    }
}
