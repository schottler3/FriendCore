package friends.friendcoreplugin.tools.types;

import friends.friendcoreplugin.tools.CustomTool;
import friends.friendcoreplugin.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SwiftShovel extends CustomTool {

    public SwiftShovel(ItemStack item){
        super(item);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(getTypeKey(), PersistentDataType.STRING, "Swift Shovel");
        data.set(getPowerKey(), PersistentDataType.INTEGER, 100);
        item.setItemMeta(meta);
        ItemUtils.setIndicator(item);
    }
}

