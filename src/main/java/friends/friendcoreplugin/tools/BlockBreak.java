package friends.friendcoreplugin.tools;

import friends.friendcoreplugin.FriendCorePlugin;
import friends.friendcoreplugin.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockBreak implements Listener {

    public BlockBreak() {
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location local = event.getBlock().getLocation();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(CustomTool.getTypeKey())) {
            if(data.get(CustomTool.getTypeKey(), PersistentDataType.STRING).equals("Explosive Pick")) {
                Specials.pickaxe(local,player,item);
            }
            else if(data.get(CustomTool.getTypeKey(), PersistentDataType.STRING).equals("Swift Shovel")) {
                Specials.shovel(event.getBlock(),player,item, 0);
                ItemUtils.damageTool(player, item, 1);
            }
            else if(data.get(CustomTool.getTypeKey(), PersistentDataType.STRING).equals("Sharp Axe")) {
                Specials.axe(event.getBlock(), player, item);
            }
        }
    }
}
