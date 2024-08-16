package friends.friendcoreplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.List;

public class ExplosivePick extends ItemStack implements Listener {

    public ExplosivePick() {
        Bukkit.getPluginManager().registerEvents(this, FriendCorePlugin.getInstance());
    }

    private static final NamespacedKey key = new NamespacedKey(FriendCorePlugin.getInstance(), "ExplosivePick");

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack item = anvil.getItem(0);
        ItemStack secondItem = anvil.getItem(1);

        if (secondItem == null || !ItemUtils.isPickaxe(item) || !secondItem.isSimilar(new ItemStack(Material.TNT))) {
            return;
        }

        if(item.getItemMeta().hasLore()){
            return;
        }

        ItemStack result = setUpPick(item);
        if(result == null)
            return;

        event.setResult(result);

        anvil.setRepairCost(10);

        int amount = secondItem.getAmount();
        if (amount > 1) {
            Player player = (Player) event.getViewers().getFirst();
            ItemStack inHand = secondItem.clone();
            inHand.setAmount(amount - 1);
            secondItem.setAmount(1);
            player.getWorld().dropItemNaturally(player.getLocation(), inHand);
        }
    }

    public NamespacedKey getKey() {
        return key;
    }

    private ItemStack setUpPick(ItemStack item){
        ItemStack result = new ItemStack(item);
        ItemUtils.addLore(result, ChatColor.DARK_RED + "Explosive");
        ItemUtils.addLore(result,
                ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.GREEN + "█"
                        + ChatColor.DARK_GREEN + "█"
                        + ChatColor.BLUE + "█"
                        + ChatColor.DARK_BLUE + "█");


        ItemMeta meta = result.getItemMeta();
        if(meta == null)
            return null;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.INTEGER, 100);
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        result.setItemMeta(meta);

        return result;
    }

    public int getPickLevel(ItemStack item){
        if(item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if(meta == null)
            return 0;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(data.has(key)){
            if(data.get(key, PersistentDataType.INTEGER) != null){
                return data.get(key, PersistentDataType.INTEGER);
            }
        }
        return 0;
    }

    private ItemStack convertBlockToItemStack(Block block, ItemStack tool) {
        Collection<ItemStack> drops = block.getDrops(tool);
        if (!drops.isEmpty()) {
            return drops.iterator().next();
        }
        return null;
    }

    public void killPick(List<String> lore, Player player) {
        Msg.send(player, ChatColor.DARK_PURPLE + "The power on that tool has run out!");
        lore.clear();
        doExplosion(player);
    }

    private void doExplosion(Player player) {
        Particle particle = Particle.EXPLOSION;
        int count = 5;
        double offsetX = 0.0;
        double offsetY = 2;
        double offsetZ = 0.0;
        double speed = 5;
        Location local = player.getLocation();

        player.getWorld().spawnParticle(particle, local, count, offsetX, offsetY, offsetZ, speed);

        player.playEffect(EntityEffect.HURT_EXPLOSION);
        player.playEffect(local, Effect.EXTINGUISH, null);
        player.damage(4);
    }

    public void damagePick(ItemStack pick, Player player){
        ItemMeta meta = pick.getItemMeta();
        if(meta == null)
            return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(data.get(key,PersistentDataType.INTEGER) != null){

            int dura = data.get(key,PersistentDataType.INTEGER);
            dura--;
            data.set(key, PersistentDataType.INTEGER, dura);

            List<String> lore = meta.getLore();
            if(lore == null)
                return;
            if(lore.isEmpty())
                return;

            Damageable damageablePick = (Damageable) meta;
            int toDeal = 100 - dura;
            if(meta.hasEnchants()){
                if(meta.getEnchants().containsKey(Enchantment.UNBREAKING)){
                    double level = meta.getEnchants().get(Enchantment.UNBREAKING);
                    double baseToDeal = 1 /(level+1);
                    int finalToDeal = (int)(toDeal*baseToDeal);
                    Msg.send(player, String.valueOf(finalToDeal));
                    damageablePick.setDamage(damageablePick.getDamage() + finalToDeal);
                }
                else {
                    Msg.send(player, String.valueOf(toDeal));
                    damageablePick.setDamage(damageablePick.getDamage() + toDeal);
                }
            }
            if(toDeal + damageablePick.getDamage() >= pick.getType().getMaxDurability()) {
                killPick(lore, player);
                return;
            }

            if(dura > 87){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.GREEN + "█"
                        + ChatColor.DARK_GREEN + "█"
                        + ChatColor.BLUE + "█"
                        + ChatColor.DARK_BLUE + "█");
            }
            else if(dura > 75){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.GREEN + "█"
                        + ChatColor.DARK_GREEN + "█"
                        + ChatColor.BLUE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 62){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.GREEN + "█"
                        + ChatColor.DARK_GREEN + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 50){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.GREEN + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 37){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.YELLOW + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 25){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.RED + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 12){
                lore.set(1, ChatColor.DARK_RED + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else if(dura > 0){
                lore.set(1, ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█"
                        + ChatColor.WHITE + "█");
            }
            else{
                killPick(lore, player);
            }
            meta.setLore(lore);
            pick.setItemMeta(meta);
        }
    }

    private void mine3x3(Location center, ItemStack tool, Player player) {
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        int tooMany = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = center.getWorld().getBlockAt(centerX + x, centerY + y, centerZ + z);
                    if(block.getBreakSpeed(player) < .03) {
                        tooMany++;
                        continue;
                    }
                    ItemStack drop = convertBlockToItemStack(block, tool);
                    if (drop != null) {
                        center.getWorld().dropItemNaturally(block.getLocation(), drop);
                    }
                    block.setType(Material.AIR);
                }
            }
        }
        if(tooMany > 1)
            Msg.send(player, ChatColor.GRAY + String.valueOf(tooMany) + " blocks were too hard!");
        else if(tooMany == 1)
            Msg.send(player, ChatColor.GRAY + "1 block was too hard!");
        damagePick(tool, player);
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (getPickLevel(tool) > 0) {
            Block block = event.getBlock();
            Location local = block.getLocation();
            mine3x3(local, tool, player);
        }
    }
}

