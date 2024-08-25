package friends.friendcoreplugin.tools;

import friends.friendcoreplugin.FriendCorePlugin;
import friends.friendcoreplugin.tools.types.ExplosivePick;
import friends.friendcoreplugin.tools.types.SharpAxe;
import friends.friendcoreplugin.tools.types.SwiftShovel;
import friends.friendcoreplugin.commands.utils.ItemUtils;
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

        switch (ItemUtils.getToolType(item)){
            case PICKAXE -> {
                if(!secondItem.getType().equals(Material.TNT)) return;

                ItemStack clonedItem = item.clone();
                ExplosivePick pick = new ExplosivePick(clonedItem);
                ItemStack result = pick.getItem();

                event.setResult(result);
                anvil.setRepairCost(10);
            }
            case SHOVEL -> {
                if(!secondItem.getType().equals(Material.HONEYCOMB)) return;

                ItemStack clonedItem = item.clone();
                SwiftShovel shovel = new SwiftShovel(clonedItem);
                ItemStack result = shovel.getItem();

                event.setResult(result);
                anvil.setRepairCost(10);
            }
            case AXE -> {
                if(!secondItem.getType().equals(Material.DIAMOND)) return;

                ItemStack clonedItem = item.clone();
                SharpAxe axe = new SharpAxe(clonedItem);
                ItemStack result = axe.getItem();

                event.setResult(result);
                anvil.setRepairCost(10);
            }
        }
    }
}
