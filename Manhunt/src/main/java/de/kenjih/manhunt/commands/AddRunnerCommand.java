package de.kenjih.manhunt.commands;

import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class AddRunnerCommand implements CommandExecutor {

    private final Manhunt plugin;

    public AddRunnerCommand(final Manhunt plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = ((Player) sender).getPlayer();
        if(!player.isOp() || player.hasPermission("manhunt.OP")){
            player.sendMessage(ChatColor.RED + "Dazu hast du keine Berechtigung");
            return false;
        }
        if(args.length != 2){
            player.sendMessage(ChatColor.RED + "Bitte benutze " + ChatColor.YELLOW + "/rank <NAME> <HUNTER | RUNNER>");
            return false;
        }
        try {
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.YELLOW + args[0] + ChatColor.RED + " wurde nicht gefunden");
                return false;
            }

            switch (args[1].toLowerCase(Locale.ROOT)){
                case "hunter":
                    plugin.getRoleManager().removePlayerFromRole(target);
                    plugin.getRoleManager().addPlayerToRole(target, Role.HUNTER);
                    break;
                case "runner":
                    plugin.getRoleManager().removePlayerFromRole(target);
                    plugin.getRoleManager().addPlayerToRole(target, Role.RUNNER);
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Bitte benutze " + ChatColor.YELLOW + "/rank <NAME> <HUNTER | RUNNER>");
                    break;
            }
        }catch (Exception e){
         e.printStackTrace();
        }
        return false;
    }
}