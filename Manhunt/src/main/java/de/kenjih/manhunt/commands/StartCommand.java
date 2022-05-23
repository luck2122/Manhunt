package de.kenjih.manhunt.commands;

import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.timer.Timer;
import de.kenjih.manhunt.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    private final Manhunt plugin;
    private final Timer timer;

    public StartCommand(final Manhunt plugin){
        this.plugin = plugin;
        this.timer = plugin.getTimer();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = ((Player) sender).getPlayer();

        if(!player.isOp() || !player.hasPermission("manhunt.OP")) {
            player.sendMessage(ChatColor.RED + "Dafür hast du keine Berechtigung");
            return false;
        }
        GameUtils.gameState = GameUtils.GameState.INGAME_STATE;
        Bukkit.broadcastMessage(ChatColor.GREEN + "Das Spiel hat gestartet");
        timer.setRunning(true);


        return false;
    }
}