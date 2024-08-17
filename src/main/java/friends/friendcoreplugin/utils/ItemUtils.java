package friends.friendcoreplugin.utils;

import friends.friendcoreplugin.tools.CustomTool;
import friends.friendcoreplugin.tools.Endings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemUtils {

    public enum ToolType {
        PICKAXE,
        SHOVEL,
        AXE,
        SWORD,
        HOE,
        NONE
    }

    public enum LogType {
        OAK_LOG,
        DARK_OAK_LOG,
        BIRCH_LOG,
        ACACIA_LOG,
        JUNGLE_LOG,
        SPRUCE_LOG,
        CHERRY_LOG,
        MANGROVE_LOG
    }

    private static final Set<Material> WOODEN_TOOLS = EnumSet.of(Material.WOODEN_PICKAXE, Material.WOODEN_SHOVEL, Material.WOODEN_AXE, Material.WOODEN_HOE, Material.WOODEN_SWORD);
    private static final Set<Material> STONE_TOOLS = EnumSet.of(Material.STONE_PICKAXE, Material.STONE_SHOVEL, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_SWORD);
    private static final Set<Material> IRON_TOOLS = EnumSet.of(Material.IRON_PICKAXE, Material.IRON_SHOVEL, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_SWORD);
    private static final Set<Material> GOLDEN_TOOLS = EnumSet.of(Material.GOLDEN_PICKAXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_AXE, Material.GOLDEN_HOE, Material.GOLDEN_SWORD);
    private static final Set<Material> DIAMOND_TOOLS = EnumSet.of(Material.DIAMOND_PICKAXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_SWORD);
    private static final Set<Material> NETHERITE_TOOLS = EnumSet.of(Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL, Material.NETHERITE_AXE, Material.NETHERITE_HOE, Material.NETHERITE_SWORD);

    public static void addLore(ItemStack item, String lore) {
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        List<String> loreList = meta.getLore();
        if (loreList == null) {
            loreList = new ArrayList<String>();
        }
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }

        Material material = item.getType();
        return switch (material) {
            case WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE, WOODEN_SHOVEL, STONE_SHOVEL,
                 IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL, NETHERITE_SHOVEL, WOODEN_HOE, STONE_HOE, IRON_HOE,
                 GOLDEN_HOE, DIAMOND_HOE, NETHERITE_HOE, WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD,
                 DIAMOND_SWORD, NETHERITE_SWORD -> true;
            default -> false;
        };
    }

    public static LogType getLogType(Material material) {
        return switch (material) {
            case OAK_LOG -> LogType.OAK_LOG;
            case DARK_OAK_LOG -> LogType.DARK_OAK_LOG;
            case BIRCH_LOG -> LogType.BIRCH_LOG;
            case ACACIA_LOG -> LogType.ACACIA_LOG;
            case JUNGLE_LOG -> LogType.JUNGLE_LOG;
            case SPRUCE_LOG -> LogType.SPRUCE_LOG;
            case CHERRY_LOG -> LogType.CHERRY_LOG;
            case MANGROVE_LOG -> LogType.MANGROVE_LOG;
            default -> null;
        };
    }

    public static void setIndicator(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        String toolName = data.get(CustomTool.getTypeKey(), PersistentDataType.STRING);
        int power = data.get(CustomTool.getPowerKey(), PersistentDataType.INTEGER);

        String ind;

        List<String> lore = meta.getLore();
        if(lore == null || lore.isEmpty()){

            lore = new ArrayList<String>();

            lore.add(ChatColor.RED + toolName);
        }

        if(power > 87){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.YELLOW + "█"
                    + ChatColor.GREEN + "█"
                    + ChatColor.DARK_GREEN + "█"
                    + ChatColor.BLUE + "█"
                    + ChatColor.DARK_BLUE + "█";
        }
        else if(power > 75){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.YELLOW + "█"
                    + ChatColor.GREEN + "█"
                    + ChatColor.DARK_GREEN + "█"
                    + ChatColor.BLUE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 62){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.YELLOW + "█"
                    + ChatColor.GREEN + "█"
                    + ChatColor.DARK_GREEN + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 50){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.YELLOW + "█"
                    + ChatColor.GREEN + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 37){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.YELLOW + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 25){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.RED + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 12){
            ind = ChatColor.DARK_RED + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else if(power > 0){
            ind = ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█"
                    + ChatColor.WHITE + "█";
        }
        else{
            return;
        }
        if(lore.size() >= 2)
            lore.set(1, ind);
        else
            lore.add(ind);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void damageTool(Player player, ItemStack item, int type){
        ItemMeta meta = item.getItemMeta();
        if(meta == null)
            return;

        List<String> lore = meta.getLore();
        if(lore == null)
            return;
        if(lore.isEmpty())
            return;

        Damageable damageablePick = (Damageable) meta;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        int currentPower = data.get(CustomTool.getPowerKey(), PersistentDataType.INTEGER);

        int toDeal = 100 - --currentPower;
        if(meta.hasEnchants()){
            if(meta.getEnchants().containsKey(Enchantment.UNBREAKING)){
                double level = meta.getEnchants().get(Enchantment.UNBREAKING);
                double baseToDeal = 1 /(level+1);
                toDeal = (int)(toDeal*baseToDeal);
            }
        }
        if(toDeal + damageablePick.getDamage() >= item.getType().getMaxDurability()) {
            killTool(lore, player, type);
            ItemStack resources = ItemUtils.exchangeTool(item, type);

            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            player.getInventory().remove(item);
            player.getWorld().dropItemNaturally(player.getLocation(), resources);
            int amountOfStick = (int) (Math.random() * 2);
            if(amountOfStick > 0)
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.STICK, amountOfStick));
            return;
        }
        else{
            damageablePick.setDamage(damageablePick.getDamage() + toDeal);
        }

        data.set(CustomTool.getPowerKey(), PersistentDataType.INTEGER, currentPower);
        item.setItemMeta(meta);

        setIndicator(item);
    }

    public static void killTool(List<String> lore, Player player, int type) {
        Msg.send(player, ChatColor.DARK_PURPLE + "The power on that tool has run out!");
        lore.clear();
        switch (type){
            case 0:
                Endings.pickaxe(player);
            case 1:
                Endings.shovel(player);
        }

    }

    public static ItemStack exchangeTool(ItemStack tool, int type){
        if (tool == null)
            return new ItemStack(Material.AIR);
        int amount = switch (type) {
            case 0 -> (int) (Math.random() * 3);
            case 1 -> (int) (Math.random() * 2);
            default -> 0;
        };
        Material toolType = tool.getType();
        if(amount <= 0)
            return new ItemStack(Material.AIR);
        if(WOODEN_TOOLS.contains(toolType)){
            return new ItemStack(Material.OAK_PLANKS, amount);
        }
        else if(STONE_TOOLS.contains(toolType)){
            return new ItemStack(Material.COBBLESTONE, amount);
        }
        else if(IRON_TOOLS.contains(toolType)){
            return new ItemStack(Material.IRON_INGOT, amount);
        }
        else if(GOLDEN_TOOLS.contains(toolType)){
            return new ItemStack(Material.GOLD_INGOT, amount);
        }
        else if(DIAMOND_TOOLS.contains(toolType)){
            return new ItemStack(Material.DIAMOND, amount);
        }
        else if(NETHERITE_TOOLS.contains(toolType)){
            return new ItemStack(Material.NETHERITE_INGOT, amount);
        }
        else
            return new ItemStack(Material.AIR);
    }

    public static ItemStack convertBlockToItemStack(Block block, ItemStack tool) {
        Collection<ItemStack> drops = block.getDrops(tool);
        if (!drops.isEmpty()) {
            return drops.iterator().next();
        }
        return null;
    }

    public static ToolType getToolType(ItemStack item) {
        Material type = item.getType();
        return switch (type) {
            case DIAMOND_PICKAXE, NETHERITE_PICKAXE, GOLDEN_PICKAXE, STONE_PICKAXE, WOODEN_PICKAXE, IRON_PICKAXE -> ToolType.PICKAXE;
            case DIAMOND_SHOVEL, NETHERITE_SHOVEL, GOLDEN_SHOVEL, STONE_SHOVEL, WOODEN_SHOVEL, IRON_SHOVEL -> ToolType.SHOVEL;
            case DIAMOND_AXE, NETHERITE_AXE, GOLDEN_AXE, STONE_AXE, WOODEN_AXE, IRON_AXE -> ToolType.AXE;
            case DIAMOND_SWORD, NETHERITE_SWORD, GOLDEN_SWORD, STONE_SWORD, WOODEN_SWORD, IRON_SWORD -> ToolType.SWORD;
            case DIAMOND_HOE, NETHERITE_HOE, GOLDEN_HOE, STONE_HOE, WOODEN_HOE, IRON_HOE -> ToolType.HOE;
            default -> ToolType.NONE;
        };
    }

    //Doesn't work with Concrete Powder
    public static boolean isShovelBlock(Block block){
        return switch (block.getType()) {
            case DIRT, GRAVEL, SAND, COARSE_DIRT, CLAY, DIRT_PATH, GRASS_BLOCK, FARMLAND, MUD, MUDDY_MANGROVE_ROOTS,
                 MYCELIUM, PODZOL, RED_SAND, ROOTED_DIRT, SNOW, SNOW_BLOCK, SOUL_SAND, SUSPICIOUS_GRAVEL,
                 SUSPICIOUS_SAND -> true;
            default -> false;
        };
    }
}