package friends.friendcoreplugin.commands.utils;

import friends.friendcoreplugin.FriendCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
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

    static Inventory inven;

    public DeathsUtils(){
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    public static Inventory populateInven(){
        inven = Bukkit.createInventory(null,54,"Deaths");

        int players = Bukkit.getWhitelistedPlayers().size();
        if(players >= 1){
            List<Tuple<Player, Integer>> deaths = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()){
                deaths.add(new Tuple<>(p, p.getStatistic (Statistic.DEATHS)));
            }
            deaths.sort(Comparator.comparing((Tuple<Player, Integer> tuple) -> tuple.y).reversed());
            for(Tuple<Player, Integer> death : deaths){
                inven.addItem(createSkullItem(death.x, death.y));
            }
        }
        return inven;
    }

    public static ItemStack createSkullItem(Player player, int deaths) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + player.getName() + ": " + deaths);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory().equals(inven)){
            e.setCancelled(true);
        }
    }
}
