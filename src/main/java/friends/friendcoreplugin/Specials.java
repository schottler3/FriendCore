package friends.friendcoreplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Specials {

    public static void pickaxe(Location center, Player player, ItemStack item) {
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        int tooMany = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = center.getWorld().getBlockAt(centerX + x, centerY + y, centerZ + z);
                    if(block.getBreakSpeed(player) < .03) {
                        tooMany++;
                        continue;
                    }
                    ItemStack drop = ItemUtils.convertBlockToItemStack(block, item);
                    if (drop != null) {
                        center.getWorld().dropItemNaturally(block.getLocation(), drop);
                    }
                    block.setType(Material.AIR);
                }
            }
        }
        if(tooMany > 1)
            Msg.send(player, ChatColor.GRAY + String.valueOf(tooMany) + " blocks were too hard!");
        else if(tooMany == 1)
            Msg.send(player, ChatColor.GRAY + "1 block was too hard!");
        ItemUtils.damageTool(player, item, 0);
    }
}
