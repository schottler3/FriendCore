package friends.friendcoreplugin.speedcart;

import friends.friendcoreplugin.FriendCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class VillagerIntercept implements Listener {

    public VillagerIntercept(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    @EventHandler
    public void onTransform(EntityTransformEvent event) {
        if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.CURED)) {
            Villager villy = (Villager) event.getTransformedEntity();
            double rand = Math.random();
            if(rand < .1) {
                RailWorkerManager.createRailWorker(villy.getLocation(), FriendCorePlugin.getInstance());
                villy.remove();
            }
        }
    }
}

