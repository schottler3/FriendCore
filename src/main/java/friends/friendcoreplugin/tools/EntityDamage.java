package friends.friendcoreplugin.tools;

import friends.friendcoreplugin.FriendCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EntityDamage implements Listener {

    public EntityDamage() {
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if(damager instanceof Player player){
            ItemStack weapon = player.getInventory().getItemInMainHand();
            ItemMeta meta = weapon.getItemMeta();
            if(meta == null) return;
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if(data.has(CustomTool.getTypeKey())){
                if(data.get(CustomTool.getTypeKey(), PersistentDataType.STRING).equals("Lighting Sword")) {
                    Specials.sword(event.getEntity(),player,weapon);
                }
            }
        }
    }
}
