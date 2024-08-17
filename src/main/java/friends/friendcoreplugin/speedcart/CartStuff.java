package friends.friendcoreplugin.speedcart;

import friends.friendcoreplugin.FriendCorePlugin;
import friends.friendcoreplugin.utils.Items;
import friends.friendcoreplugin.utils.Msg;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Objects;

public class CartStuff implements Listener {

    private final NamespacedKey key = new NamespacedKey(FriendCorePlugin.getInstance(), "cart");
    private static double cartCost;

    public CartStuff() {
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
        cartCost = 16;
    }

    public static void buyMinecart(Player player){
        Items.give(player, Items.getCartToken());
    }

    public static void setCartCost(double cartCost){
        CartStuff.cartCost = cartCost;
    }

    public static double getCartCost(){
        return cartCost;
    }

    @EventHandler
    public void damageEntity(VehicleDamageEvent event){
        Entity d = event.getAttacker();
        if(d instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.PAPER)) {
                if (Objects.requireNonNull(item.getItemMeta()).hasEnchants()) {
                    Vehicle ent = event.getVehicle();
                    if(ent instanceof Minecart cart) {
                        cart.setCustomNameVisible(true);
                        cart.setCustomName("§bSpeed Cart");

                        PersistentDataContainer data = cart.getPersistentDataContainer();
                        if(data.get(key, PersistentDataType.BOOLEAN) != null){
                            Msg.send(player, "&cThat's already a speed cart!");
                            return;
                        }
                        else{
                            data.set(key, PersistentDataType.BOOLEAN, true);
                        }

                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void vehicleEnter(VehicleEnterEvent event){
        Vehicle vehicle = event.getVehicle();
        Entity ent = event.getEntered();
        if(ent instanceof Player player && vehicle instanceof Minecart){
            Msg.send(player, "&aLook up and left click to speed up.");
            Msg.send(player, "&cLook down and left click to slow down.");
            PersistentDataContainer data = vehicle.getPersistentDataContainer();
            if(data.get(key, PersistentDataType.BOOLEAN) != null){
                int maxCartSpeed = 10;
                ((Minecart) vehicle).setMaxSpeed(maxCartSpeed);
            }
        }
    }

    @EventHandler
    public void vehicleBreak(VehicleDestroyEvent event){
        Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Minecart cart){
            Collection<Entity> nearbyEntities = Objects.requireNonNull(cart.getLocation().getWorld()).getNearbyEntities(cart.getLocation(), 2, 2, 2);
            for(Entity entity : nearbyEntities) {
                if(entity instanceof Minecart){
                    PersistentDataContainer data = cart.getPersistentDataContainer();
                    if(data.get(key, PersistentDataType.BOOLEAN) == null){
                        return;
                    }
                    else{
                        if(Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))){
                            Location local = cart.getLocation();
                            Objects.requireNonNull(local.getWorld()).dropItemNaturally(local,Items.getCartToken());
                            cart.setCustomName(null);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void vehicleExit(VehicleExitEvent event){
        LivingEntity ent = event.getExited();
        if(ent instanceof Player player && event.getVehicle() instanceof Minecart){
            Location current = player.getLocation();
            player.teleport(current.subtract(event.getVehicle().getVelocity().multiply(3)));
        }
    }

    @EventHandler
    public void leftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isInsideVehicle() && player.getVehicle() instanceof Minecart vehicle) {
            Block block = vehicle.getLocation().getBlock();
            if (block.getType().equals(Material.RAIL) || block.getType().equals(Material.POWERED_RAIL)) {
                if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    Vector dir = player.getLocation().getDirection();
                    if(dir.getY() > 0)
                        vehicle.setVelocity(dir.multiply(2));
                    else
                        vehicle.setVelocity(dir.multiply(.5));
                }
            }
        }
    }
}
