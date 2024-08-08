package friends.friendcoreplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class NPCInteract implements Listener {

    static Inventory railShop;

    public NPCInteract(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
        setUpRailShop();
    };

    @EventHandler
    public void playerInteraction(PlayerInteractEntityEvent event){
        if(event.getHand().equals(EquipmentSlot.HAND)) {
            Player player = event.getPlayer();
            Entity entity = event.getRightClicked();

            if(Objects.equals(entity.getCustomName(), "Rail Worker")){
                player.openInventory(railShop);
            }
        }
    }

    public static Inventory getRailShop(){
        return railShop;
    }

    private void setUpRailShop(){
        railShop = GUIStuff.getGUI(27, "Rail Worker's Store");
        ItemStack item = Items.getCartToken();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.clear();
        lore.add(ChatColor.GREEN + "Price: " + CartStuff.getCartCost());
        meta.setLore(lore);
        item.setItemMeta(meta);
        railShop.setItem(13, item);
    }
}
