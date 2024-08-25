package friends.friendcoreplugin.commands.utils;

import friends.friendcoreplugin.FriendCorePlugin;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeathsUtils implements Listener {

    static Inventory inven = Bukkit.createInventory(null,54,"Deaths");

    public DeathsUtils(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    public static void populateInven(){
        inven.clear();
        int players = Bukkit.getWhitelistedPlayers().size();
        if(players >= 1){
            List<Tuple<OfflinePlayer, Integer>> deaths = new ArrayList<>();
            for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()){
                deaths.add(new Tuple<>(p, p.getStatistic (Statistic.DEATHS)));
            }
            deaths.sort(Comparator.comparing((Tuple<OfflinePlayer, Integer> tuple) -> tuple.y).reversed());
            for(Tuple<OfflinePlayer, Integer> death : deaths){
                inven.addItem(createSkullItem(death.x, death.y));
            }
        }
    }

    public static Inventory getDeaths(){
        populateInven();
        return inven;
    }

    public static ItemStack createSkullItem(OfflinePlayer player, int deaths) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if(meta == null)
            return null;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + player.getName() + ": " + deaths);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getClickedInventory().equals(inven)){
            e.setCancelled(true);
        }
    }
}
