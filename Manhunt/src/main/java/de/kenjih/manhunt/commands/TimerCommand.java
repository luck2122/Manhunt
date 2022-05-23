package de.kenjih.manhunt.commands;

import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TimerCommand implements CommandExecutor {

    private final Manhunt plugin;
    private final Timer timer;

    public TimerCommand(final Manhunt plugin){
        this.plugin = plugin;
        this.timer = plugin.getTimer();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(!player.isOp() || !player.hasPermission("manhunt.OP")) return false;

        if(args.length <= 0){
            player.sendMessage(ChatColor.RED + "Bitte benutze " + ChatColor.YELLOW + "/timer <STOP | RESUME | RESET");
            return false;
        }
        switch (args[0].toLowerCase(Locale.ROOT)){
            case "pause":
                timer.setRunning(false);
                break;
            case "resume":
                timer.setRunning(true);
                break;
            case "reset":
                timer.resetTimer();
                break;
            default:
                player.sendMessage(ChatColor.RED + "Bitte benutze " + ChatColor.YELLOW + "/timer <STOP | RESUME | RESET");
                break;
        }

        return false;
    }
}