package friends.friendcoreplugin.speedcart;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class RailWorkerManager {

    public static void createRailWorker(Location location, JavaPlugin plugin) {
        new RailWorker(location, plugin);
    }

    public static boolean isRailWorker(Villager villager) {
        PersistentDataContainer data = villager.getPersistentDataContainer();
        return data.has(RailWorker.getKey());
    }
}