package de.kenjih.manhunt.listeners;

import de.kenjih.coreapi.CoreAPI;
import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.role.Role;
import de.kenjih.manhunt.utils.CheckLocations;
import de.kenjih.manhunt.utils.GameUtils;
import de.kenjih.manhunt.utils.LocationManager;
import org.apache.logging.log4j.core.Core;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Random;

public class ConnectionListener implements Listener {
    private final Manhunt plugin;
    private final LocationManager locationManager = new LocationManager();
    private Player runner;
    private boolean villageSpawn;
    private boolean ruinedSpawn;
    private Location ruinedPortalLocation;

    public ConnectionListener(final Manhunt plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        try{
            Player player = event.getPlayer();
            event.setJoinMessage(ChatColor.GRAY + "Der Spieler " + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " hat das Spiel betreten");
            switch (GameUtils.gameState){
                case LOBBY_STATE:
                    if(plugin.runners.isEmpty()){
                        try{
                            String village = CoreAPI.mysqlHandler.getString(runner.getUniqueId(), "manhunt", "village");
                            villageSpawn = Boolean.parseBoolean(village);
                            String ruinedPortal = CoreAPI.mysqlHandler.getString(runner.getUniqueId(), "manhunt", "ruinportal");
                            ruinedSpawn = Boolean.parseBoolean(ruinedPortal);
                            setSpawnNearestStructure(player);
                            runner.sendMessage(ChatColor.GRAY + "Bei " + ChatColor.GREEN + ruinedPortalLocation.getX() + ", " +ruinedPortalLocation.getY() +
                                    ", " + ruinedPortalLocation.getX() + ChatColor.GRAY + " befindet sich ein Ruined Portal");
                        }catch (Exception e){
                            villageSpawn = false;
                            ruinedSpawn = false;
                            e.printStackTrace();
                        }

                        locationManager.setLocation(runner, "hunterLocation");
                        runner = player;
                        plugin.runners.add(runner);
                        plugin.getRoleManager().addPlayerToRole(player, Role.RUNNER);
                    }else{
                       teleportHunter(player);
                       plugin.getRoleManager().addPlayerToRole(player, Role.HUNTER);
                    }
                    Role playerRole = plugin.getRoleManager().getPlayerRole(player);
                    player.setDisplayName(playerRole.getChatColor() + playerRole.getName() + "§r | " + player.getName());
                    break;
                case ENDING_STATE:

                    break;
                case INGAME_STATE:

                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void teleportHunter(final Player player){
        plugin.hunters.add(player);
        Location hunterLocation = locationManager.getLocation("hunterLocation");
        plugin.getRoleManager().addPlayerToRole(player, Role.HUNTER);
        player.setCompassTarget(runner.getLocation());
        player.teleport(CheckLocations.setHunterLocation(hunterLocation, runner));
    }

    private void setSpawnNearestStructure(final Player player){
        if(villageSpawn && ruinedSpawn){
            CheckLocations.checkForVillage();
            ruinedPortalLocation = CheckLocations.checkForRuinedPortal();
        }else if(villageSpawn){
            CheckLocations.checkForVillage();
        }else if(ruinedSpawn){
            ruinedPortalLocation = CheckLocations.checkForRuinedPortal();
        }
    }


}