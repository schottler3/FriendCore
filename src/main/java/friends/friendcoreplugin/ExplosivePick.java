package friends.friendcoreplugin;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ExplosivePick extends CustomTool {

    public ExplosivePick(ItemStack item){
        super(item);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(getTypeKey(), PersistentDataType.STRING, "Explosive Pick");
        data.set(getPowerKey(), PersistentDataType.INTEGER, 100);
        item.setItemMeta(meta);
        ItemUtils.setIndicator(item);
    }
}

