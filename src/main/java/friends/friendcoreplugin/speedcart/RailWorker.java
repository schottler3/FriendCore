package friends.friendcoreplugin.speedcart;

import friends.friendcoreplugin.FriendCorePlugin;
import friends.friendcoreplugin.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RailWorker {

    private Villager villager;
    private static final Inventory shop = setUpRailShop();
    private static final NamespacedKey key = new NamespacedKey(FriendCorePlugin.getInstance(), "RailWorker");

    public RailWorker(Location location, JavaPlugin plugin) {
        World world = location.getWorld();
        if (world == null) {
            FriendCorePlugin.throwError("RailWorker Constructor");
            return;
        }
        this.villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        this.villager.setVillagerType(Villager.Type.PLAINS);
        this.villager.setVillagerExperience(1);
        this.villager.setProfession(Villager.Profession.WEAPONSMITH);
        this.villager.setCustomName(ChatColor.DARK_GRAY + "Rail Worker");
        this.villager.setCustomNameVisible(true);
        PersistentDataContainer data = villager.getPersistentDataContainer();
        data.set(key, PersistentDataType.BOOLEAN, Boolean.TRUE);
    }

    public Villager getVillager() {
        return villager;
    }

    public static NamespacedKey getKey(){
        return key;
    }

    public static Inventory getShop() {
        return shop;
    }

    private static Inventory setUpRailShop() {
        Inventory railShop = GUIStuff.getGUI(27, "Rail Worker's Store");
        ItemStack item = Items.getCartToken();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.clear();
        lore.add(ChatColor.GREEN + "Price: " + CartStuff.getCartCost());
        meta.setLore(lore);
        item.setItemMeta(meta);
        railShop.setItem(13, item);
        return railShop;
    }
}