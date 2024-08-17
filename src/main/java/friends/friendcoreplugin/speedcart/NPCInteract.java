package friends.friendcoreplugin.speedcart;

import friends.friendcoreplugin.FriendCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NPCInteract implements Listener {

    public NPCInteract(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    @EventHandler
    public void playerInteraction(PlayerInteractEntityEvent event){
        if(event.getHand().equals(EquipmentSlot.HAND)) {
            Player player = event.getPlayer();
            Entity entity = event.getRightClicked();

            if(entity instanceof Villager villager) {
                if (RailWorkerManager.isRailWorker(villager)) {
                    player.openInventory(RailWorker.getShop());
                    event.setCancelled(true);
                }
            }
        }
    }
}