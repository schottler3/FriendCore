package friends.friendcoreplugin.tools;

import friends.friendcoreplugin.commands.utils.ItemUtils;
import friends.friendcoreplugin.utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
                    if(block.getType().equals(Material.AIR)) {
                        continue;
                    }
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

    public static void shovel(Block block, Player player, ItemStack item, int depth) {
        if(depth > 5) return;
        if(ItemUtils.isShovelBlock(block)){
            depth++;
            ItemStack drop = ItemUtils.convertBlockToItemStack(block, item);
            if (drop != null) {
                player.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
            block.setType(Material.AIR);
            World world = player.getWorld();
            Location currentBlock = block.getLocation();

            int x = currentBlock.getBlockX();
            int y = currentBlock.getBlockY();
            int z = currentBlock.getBlockZ();

            shovel(world.getBlockAt(x, y+1, z), player, item, depth);
            shovel(world.getBlockAt(x, y-1, z), player, item, depth);
            shovel(world.getBlockAt(x+1, y, z), player, item, depth);
            shovel(world.getBlockAt(x-1, y, z), player, item, depth);
            shovel(world.getBlockAt(x, y, z+1), player, item, depth);
            shovel(world.getBlockAt(x, y, z-1), player, item, depth);
        }
    }

    public static void axe(Block block, Player player, ItemStack item) {
        ItemUtils.LogType logType = ItemUtils.getLogType(block.getType());
        /*
        switch (logType){
            case OAK_LOG -> {

            }
            case DARK_OAK_LOG -> {

            }
            case BIRCH_LOG -> {

            }
            case ACACIA_LOG -> {

            }
            case JUNGLE_LOG -> {

            }
            case SPRUCE_LOG -> {

            }
            case CHERRY_LOG -> {

            }
            case MANGROVE_LOG -> {

            }
            case null -> {
            }
        }
        */
    }
}
