package de.kenjih.manhunt.listeners;

import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.role.Role;
import de.kenjih.manhunt.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Locale;

public class GameEndListener implements Listener {

    private final Manhunt plugin;
    private final Role runnerRole = Role.RUNNER;
    private Role victimRole;
    private Role killerRole;

    public GameEndListener(final Manhunt plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        switch (GameUtils.gameState){
            case LOBBY_STATE:

                break;
            case INGAME_STATE:
                if(event.getEntity().getType() == EntityType.ENDER_DRAGON){
                    GameUtils.gameState = GameUtils.GameState.ENDING_STATE;
                    plugin.getTimer().setRunning(false);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Der " +runnerRole.getChatColor() + runnerRole.getName()+ ChatColor.GREEN +" hat das Spiel gewonnen");
                }
                break;
            case ENDING_STATE:

                break;
            default:
                break;
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        switch (GameUtils.gameState){
            case LOBBY_STATE:

                break;
            case INGAME_STATE:
                Player victim = event.getEntity();
                Player killer = victim.getKiller();
                if(victimRole == Role.RUNNER){
                    GameUtils.gameState = GameUtils.GameState.ENDING_STATE;
                    sendDeathMessage(victim, killer);
                    plugin.getTimer().setRunning(false);
                    Bukkit.broadcastMessage(ChatColor.RED + " Die " + Role.HUNTER.getChatColor() + Role.HUNTER.getName() + ChatColor.RED + " haben das Spiel gewonnen");
                    Location deathLocation = victim.getLocation();
                    Bukkit.getWorld(victim.getWorld().toString()).setSpawnLocation(deathLocation);
                    victim.setGameMode(GameMode.SPECTATOR);
                }else{
                    sendDeathMessage(victim, killer);
                }
                victim.kickPlayer(ChatColor.RED +  "Du bist gestorben");
                break;
            case ENDING_STATE:

                break;
            default:
                break;
        }

    }

/*    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        switch (GameUtils.gameState){
            case LOBBY_STATE:

                break;
            case INGAME_STATE:
                if(!(event.getEntity() instanceof Player)) return;
                Player player = ((Player) event.getEntity()).getPlayer();
                Player killer = player.getKiller();
                victimRole = plugin.getRoleManager().getPlayerRole(player);
                if (player.getHealth() <= 0){
                    if(victimRole == Role.RUNNER){
                        event.setCancelled(true);
                        player.setGameMode(GameMode.SPECTATOR);
                        plugin.getTimer().setRunning(false);
                        sendDeathMessage(player, killer);
                        Bukkit.broadcastMessage(ChatColor.RED + " Die " + Role.HUNTER.getChatColor() + Role.HUNTER.getName() + ChatColor.RED + " haben das Spiel gewonnen");
                    }
                    else{
                        sendDeathMessage(player, killer);
                    }
                }
                break;
            case ENDING_STATE:

                break;
            default:
                break;
        }
    }*/

    private void sendDeathMessage(final Player victim, final Player killer){
        EntityDamageEvent ede = victim.getLastDamageCause();
        victimRole = plugin.getRoleManager().getPlayerRole(victim);
        if(killer != null){
            killerRole = plugin.getRoleManager().getPlayerRole(killer);
            Bukkit.broadcastMessage(ChatColor.RED + "Der Runner " + victimRole.getChatColor() + victim.getName() + ChatColor.RED
                    + " wurde vom Hunter " + killerRole.getChatColor() + killer.getName() + ChatColor.RED + " getötet");
        }else
            Bukkit.broadcastMessage(ChatColor.RED + "Der " + victimRole.getChatColor() + victimRole.getName() + " " + victim.getName() + ChatColor.RED + " ist an " +
                    ChatColor.YELLOW + ede.getCause().toString().toLowerCase(Locale.ROOT) + ChatColor.RED + " gestorben");
    }
}