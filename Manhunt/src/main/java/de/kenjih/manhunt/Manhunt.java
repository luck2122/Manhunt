package de.kenjih.manhunt;

import de.kenjih.manhunt.commands.AddRunnerCommand;
import de.kenjih.manhunt.commands.StartCommand;
import de.kenjih.manhunt.commands.TimerCommand;
import de.kenjih.manhunt.listeners.CompassUseListener;
import de.kenjih.manhunt.listeners.ConnectionListener;
import de.kenjih.manhunt.listeners.GameEndListener;
import de.kenjih.manhunt.listeners.ProtectionListener;
import de.kenjih.manhunt.role.RoleManager;
import de.kenjih.manhunt.timer.Timer;
import de.kenjih.manhunt.utils.FileUtils;
import de.kenjih.manhunt.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Manhunt extends JavaPlugin {

    public final List<Player> runners = new ArrayList<>();
    public final List<Player> hunters = new ArrayList<>();
    private final RoleManager roleManager = new RoleManager(this);
    private Timer timer;


    @Override
    public void onEnable() {
        new FileUtils().createFiles();

        //TODO Einstellung damit Hunter sich am Anfang nicht bewegen können
        GameUtils.gameState  = GameUtils.GameState.LOBBY_STATE;

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new ProtectionListener(this), this);
        pluginManager.registerEvents(new CompassUseListener(this), this);
        pluginManager.registerEvents(new GameEndListener(this), this);
        this.timer = new Timer(this);
        getCommand("timer").setExecutor(new TimerCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("rank").setExecutor(new AddRunnerCommand(this));
    }

    public Timer getTimer(){
        return this.timer;
    }

    public RoleManager getRoleManager(){
        return this.roleManager;
    }

}
