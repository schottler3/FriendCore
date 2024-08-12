package friends.friendcoreplugin;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

public class RailWorkerManager {

    public static void createRailWorker(Location location, JavaPlugin plugin) {
        new RailWorker(location, plugin);
    }

    public static boolean isRailWorker(Villager villager) {
        return villager.hasMetadata("RailWorker");
    }
}