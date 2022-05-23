package de.kenjih.manhunt.role;

import de.kenjih.manhunt.Manhunt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleManager {

    private Manhunt plugin;
    private HashMap<String, Role> playerRoles;

    public RoleManager(final Manhunt plugin){
        this.plugin = plugin;
        playerRoles = new HashMap<>();
    }

    public void removePlayerFromRole(final Player player){
        playerRoles.remove(player.getName());
    }

    public void addPlayerToRole(final Player player, final Role role){
        playerRoles.put(player.getName(), role);
    }

    public Role getPlayerRole(final Player player){
        return playerRoles.get(player.getName());
    }
}