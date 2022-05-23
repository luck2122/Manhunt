package de.kenjih.manhunt.listeners;

import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.utils.GameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassUseListener implements Listener {

    private final Manhunt plugin;
    private Player runner;

    public CompassUseListener(final Manhunt plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onCompasClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK ) return;
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() != Material.COMPASS || player.getInventory().getItemInOffHand().getType() != Material.COMPASS) return;
        switch (GameUtils.gameState){
            case LOBBY_STATE:

                break;
            case INGAME_STATE:
                if(plugin.runners.isEmpty()) return;
                double distance = player.getLocation().distance(plugin.runners.get(0).getLocation());
                player.sendMessage(ChatColor.GRAY + "Der Runner "+ ChatColor.RED + plugin.runners.get(0).getName() +
                        ChatColor.GRAY + " ist " + ChatColor.GOLD + Math.round(distance*100)/100 + ChatColor.GRAY + " Blöcke entfernt");
                break;
            case ENDING_STATE:

                break;

            default:
                break;
        }
    }
}