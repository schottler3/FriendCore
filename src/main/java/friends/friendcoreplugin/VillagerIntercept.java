package friends.friendcoreplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class VillagerIntercept implements Listener {

    public VillagerIntercept(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    };

    @EventHandler
    public void onCure(EntityTransformEvent event) {
        if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.CURED)) {
            System.out.println("Worked!");
            double rand = Math.random();
            Villager villy = (Villager) event.getTransformedEntities().get(0);
            villy.setAdult();
            villy.setProfession(Villager.Profession.TOOLSMITH);
            villy.setCustomName("Rail Worker");
            villy.setCustomNameVisible(true);
        }
    }

}


