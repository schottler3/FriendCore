package friends.friendcoreplugin;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public abstract class CustomTool{
    protected ItemStack item;

    private static final NamespacedKey powerKey = new NamespacedKey(FriendCorePlugin.getInstance(), "power");
    private static final NamespacedKey typeKey = new NamespacedKey(FriendCorePlugin.getInstance(), "type");

    public CustomTool(ItemStack item){
        this.item = item;
    };

    public ItemStack getItem() {
        return item;
    }

    public static NamespacedKey getTypeKey() {
        return typeKey;
    }

    public static NamespacedKey getPowerKey() {
        return powerKey;
    }
}