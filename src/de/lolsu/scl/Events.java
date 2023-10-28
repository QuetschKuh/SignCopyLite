package de.lolsu.scl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        // Return if conditions
        if(!player.hasPermission("SignCopyLite")) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || !player.getItemInHand().equals(Main.sclItem)) return;
        if(block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN && block.getType() != Material.SIGN) return;
        event.setCancelled(true);

        // Get sign block and save to clipboard
        Sign sign = (Sign) block.getState();
        Main.clipboard.put(player, sign.getLines());

        // Player feedback
        player.playSound(player.getLocation(), Sound.CAT_MEOW, 0.5f, 1f);
        player.sendMessage(Main.prefix + "Copied text:");
        for(String line : sign.getLines())
            player.sendMessage(Main.prefix + line);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        // Return if conditions
        if(!player.hasPermission("SignCopyLite")) return;
        if(!player.getItemInHand().equals(Main.sclItem)) return;
        if(event.getBlock().getType() != Material.SIGN_POST && event.getBlock().getType() != Material.WALL_SIGN && event.getBlock().getType() != Material.SIGN) return;
        event.setCancelled(true);

        // Return if no text copied
        if(!Main.clipboard.containsKey(player)) { player.sendMessage(Main.prefix + "You haven't copied any text yet!"); return; }

        // Paste text from clipboard
        Sign sign = (Sign) event.getBlock().getState();
        String[] text = Main.clipboard.get(player);
        for(int i = 0; i < text.length; i++) {
            sign.setLine(i, text[i]);
        }
        sign.update();

        // Player feedback
        player.playSound(player.getLocation(), Sound.CAT_MEOW, 0.5f, 1f);
        player.sendMessage(Main.prefix + "Pasted text!");
    }

}
