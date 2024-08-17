package friends.friendcoreplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;


public class AnvilIntercept implements Listener {

    public AnvilIntercept() {
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack item = anvil.getItem(0);
        ItemStack secondItem = anvil.getItem(1);

        if (item == null || secondItem == null) {
            return;
        }

        if(ItemUtils.isTool(item) && secondItem.isSimilar(new ItemStack(Material.TNT))){
            ItemStack clonedItem = item.clone();
            ExplosivePick pick = new ExplosivePick(clonedItem);
            ItemStack result = pick.getItem();
            event.setResult(result);
            anvil.setRepairCost(10);
        }
    }
}
