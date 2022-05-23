package de.kenjih.manhunt.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Locale;

public final class LocationManager {
    private final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new FileUtils().file);

    public void setLocation(final Player player, final String name){
        yamlConfiguration.set(name.toUpperCase() + ".X", (player.getLocation().getBlockX() + 0.5));
        yamlConfiguration.set(name.toUpperCase() + ".Y", (player.getLocation().getBlockY() + 0.5));
        yamlConfiguration.set(name.toUpperCase() + ".Z", (player.getLocation().getBlockZ() + 0.5));
        yamlConfiguration.set(name.toUpperCase() + ".Yaw", player.getLocation().getYaw());
        yamlConfiguration.set(name.toUpperCase() + ".Pitch", player.getLocation().getPitch());
        try {
            yamlConfiguration.save(new FileUtils().file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Location getLocation(final String name){
        double X = yamlConfiguration.getDouble(name.toUpperCase() + ".X"),
                Y = yamlConfiguration.getDouble(name.toUpperCase() + ".Y"),
                Z = yamlConfiguration.getDouble(name.toUpperCase() + ".Z");
        float Yaw = (float) yamlConfiguration.getDouble(name.toUpperCase() + ".Yaw"),
                Pitch = (float) yamlConfiguration.getDouble(name.toUpperCase() + ".Pitch");
        World world = Bukkit.getWorld("world");
        return new Location(world, X, Y, Z, Yaw, Pitch);
    }
}