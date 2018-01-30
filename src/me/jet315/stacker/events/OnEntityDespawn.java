package me.jet315.stacker.events;

import me.jet315.stacker.MobStacker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

/**
 * Created by Jet on 30/01/2018.
 */
public class OnEntityDespawn implements Listener{

    @EventHandler
    public void onDespawnEvent(ItemDespawnEvent e){
        if(e.getEntity() instanceof LivingEntity){
            if (MobStacker.getInstance().getEntityStacker().getValidEntity().contains(e.getEntity())) {
                MobStacker.getInstance().getEntityStacker().getValidEntity().remove(e.getEntity());
            }
        }
    }
}
