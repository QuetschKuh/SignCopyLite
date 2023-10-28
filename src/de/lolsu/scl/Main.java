package de.lolsu.scl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public static String prefix = ChatColor.AQUA + "[SignCopyLite] " + ChatColor.WHITE;
    public static ItemStack sclItem = new ItemStack(Material.STICK);
    public static HashMap<Player, String[]> clipboard = new HashMap<>();

    public void onEnable() {
        // Create item
        ItemMeta sclMeta = sclItem.getItemMeta();
        sclMeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "SCL Tool");
        sclMeta.setLore(Arrays.asList(ChatColor.GOLD + "Left Click" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Copy Sign Text", ChatColor.GOLD + "Right Click" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Paste Sign Text"));
        sclMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sclItem.setItemMeta(sclMeta);
        sclItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

        // Register command and event
        getCommand("signcopylite").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }

}
