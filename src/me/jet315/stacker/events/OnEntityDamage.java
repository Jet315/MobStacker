package me.jet315.stacker.events;

import me.jet315.stacker.MobStacker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Jet on 25/01/2018.
 */
public class OnEntityDamage implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player) && e.getEntity() instanceof LivingEntity){
                if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                    if(e.getEntity().getFallDistance() > 15 && e.getFinalDamage() >= 20){
                        if(MobStacker.getInstance().getMobStackerConfig().mobsToStack.contains(e.getEntityType()) && MobStacker.getInstance().getMobStackerConfig().killMobStackOnFall)
                        MobStacker.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().add((LivingEntity) e.getEntity());
                    }

            }
            //Check if cause is Fall Damage
            //If so remove stack
        }
    }
}
