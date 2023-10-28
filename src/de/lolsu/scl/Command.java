package de.lolsu.scl;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) { sender.sendMessage(Main.prefix + "You may only run this command as a player"); return false; }
        if(!sender.hasPermission("SignCopyLite")) { sender.sendMessage(ChatColor.RED + "No permission!"); return true; }

        Player p = (Player) sender;

        // Give tool
        if(!p.getInventory().contains(Main.sclItem)) p.getInventory().addItem(Main.sclItem);
        p.sendMessage(Main.prefix + "Given SCL Tool");

        // Set clipboard
        String[] text = String.join(" ", args).split("\\\\n");
        if(!text[0].isEmpty()) {
            if(text.length > 4) { p.sendMessage(Main.prefix + "Only 4 lines of text are allowed"); return false; }

            p.sendMessage(Main.prefix + "Copied text:");
            String[] textNew = new String[text.length];
            for(int i = 0; i < text.length; i++) {
                textNew[i] = ChatColor.translateAlternateColorCodes('&', text[i]);
                p.sendMessage(Main.prefix + textNew[i]);
            }
            Main.clipboard.put(p, textNew);

            // Player feedback
            p.playSound(p.getLocation(), Sound.CAT_MEOW, 0.5f, 1f);
            return true;
        } else {
            return false;
        }

    }

}
