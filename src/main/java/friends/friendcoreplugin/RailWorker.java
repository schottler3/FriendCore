package friends.friendcoreplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RailWorker {

    private Villager villager;
    private static Inventory shop = null;

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
        this.villager.setMetadata("RailWorker", new FixedMetadataValue(plugin, true));
        shop = setUpRailShop();
    }

    public Villager getVillager() {
        return villager;
    }

    public static Inventory getShop() {
        return shop;
    }

    private Inventory setUpRailShop() {
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