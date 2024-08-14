package friends.friendcoreplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static boolean isPickaxe(ItemStack item) {
        if (item == null) {
            return false;
        }

        Material itemType = item.getType();
        return itemType == Material.WOODEN_PICKAXE ||
                itemType == Material.STONE_PICKAXE ||
                itemType == Material.IRON_PICKAXE ||
                itemType == Material.GOLDEN_PICKAXE ||
                itemType == Material.DIAMOND_PICKAXE ||
                itemType == Material.NETHERITE_PICKAXE;
    }

    public static void addLore(ItemStack item, String lore) {
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        List<String> loreList = meta.getLore();
        if (loreList == null) {
            loreList = new ArrayList<String>();
        }
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
}