package de.kenjih.manhunt.utils;

import de.kenjih.coreapi.CoreAPI;
import de.kenjih.manhunt.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.entity.Player;

import java.util.Random;

public class CheckLocations {



    public static void checkForVillage(){
        Location villageLocation = Bukkit.getWorld("world")
                .locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.VILLAGE, 10000, true);

        if(villageLocation != null)
            Bukkit.getWorld("world").setSpawnLocation(villageLocation);
        else{
            villageLocation = Bukkit.getWorld("world")
                    .locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.VILLAGE, 100000, true);

            Bukkit.getWorld("world").setSpawnLocation(villageLocation);
            //TODO überprüfen ob der Spieler wirklich dort spawned
            //player.teleport(villageLocation);
        }
    }

    public static Location checkForRuinedPortal(){
        Location ruinedPortalLocation = Bukkit.getWorld("world")
                .locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.RUINED_PORTAL, 10000, true);

        if(ruinedPortalLocation != null)
            Bukkit.getWorld("world").setSpawnLocation(ruinedPortalLocation);
        else{
            ruinedPortalLocation = Bukkit.getWorld("world")
                    .locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.RUINED_PORTAL, 100000, true);

            Bukkit.getWorld("world").setSpawnLocation(ruinedPortalLocation);

            //TODO überprüfen ob der Spieler wirklich dort spawned
            //player.teleport(ruinedPortalLocation);
        }
        return ruinedPortalLocation;
    }

    public static Location setHunterLocation(Location hunterLocation, final Player runner){
        Random random = new Random();
        //TODO überprügen ob das richtig ist
        int distance = CoreAPI.mysqlHandler.getInteger(runner.getUniqueId(), "manhunt", "distance");
        if(random.nextDouble() < 0.5){
            if(random.nextDouble() < 0.5)
                hunterLocation.setX(hunterLocation.getX() - distance);
            else
                hunterLocation.setX(hunterLocation.getX() + distance);
        }else
        if(random.nextDouble() < 0.5)
            hunterLocation.setZ(hunterLocation.getZ() + distance);
        else
            hunterLocation.setZ(hunterLocation.getZ() - distance);

        hunterLocation = hunterLocation.getWorld().getHighestBlockAt(hunterLocation).getLocation();
        return hunterLocation;
    }
}