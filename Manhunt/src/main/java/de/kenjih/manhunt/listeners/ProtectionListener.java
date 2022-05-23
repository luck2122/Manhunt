package de.kenjih.manhunt.listeners;

import com.mojang.datafixers.types.templates.List;
import de.kenjih.manhunt.Manhunt;
import de.kenjih.manhunt.role.Role;
import de.kenjih.manhunt.utils.GameUtils;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ProtectionListener implements Listener {

    private final Manhunt plugin;
    private final Random random = new Random();
    private final ItemStack pearl = new ItemStack(Material.ENDER_PEARL);

    public ProtectionListener(final Manhunt plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void breaK(BlockBreakEvent event) {
        switch (GameUtils.gameState) {
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                event.setCancelled(false);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void place(BlockPlaceEvent event) {
        switch (GameUtils.gameState) {
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                event.setCancelled(false);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void food(FoodLevelChangeEvent event) {
        switch (GameUtils.gameState) {
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                event.setCancelled(false);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        switch (GameUtils.gameState) {
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                event.setCancelled(false);
                break;
            default:
                break;
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityHit(EntityDamageByEntityEvent event){
        switch (GameUtils.gameState){
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                if(!(event.getDamager() instanceof Player) && !(event.getEntity() instanceof Player)){
                    if(event.getDamager() instanceof Player && event.getEntity().getType() == EntityType.ENDER_DRAGON){
                        Player damager = (Player) event.getDamager();
                        Role damagerRole = plugin.getRoleManager().getPlayerRole(damager);
                        if(damagerRole == Role.HUNTER)
                            event.setCancelled(true);
                    }
                }

                Player damager = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();

                Role damagerRole = plugin.getRoleManager().getPlayerRole(damager);
                Role victimRole = plugin.getRoleManager().getPlayerRole(victim);

                if(damagerRole == victimRole)
                    event.setCancelled(true);
                break;
            default:
                break;
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        switch (GameUtils.gameState){
            case LOBBY_STATE:
                if(player.isOp() || player.hasPermission("manhunt.OP")) return;
                event.setCancelled(true);
                break;
            case INGAME_STATE:
            case ENDING_STATE:
                event.setCancelled(false);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onPiglinTrade(PiglinBarterEvent event){
        switch (GameUtils.gameState){
            case LOBBY_STATE:
            case ENDING_STATE:
                event.setCancelled(true);
                break;
            case INGAME_STATE:
                Entity piglin = event.getEntity();
                if(event.getOutcome().get(0).getType() == Material.ENDER_PEARL) return;
                if(random.nextDouble() <= 0.5){
                    event.getOutcome().clear();
                    if(random.nextDouble() <= 0.25){
                       dropPearls(4, piglin);
                    }else if(random.nextDouble() <= 0.30){
                        dropPearls(3, piglin);
                    }else if(random.nextDouble() <= 0.55){
                        dropPearls(2, piglin);
                    }else{
                        dropPearls(1, piglin);
                    }
                }
                break;
            default:
                break;
        }
    }


    private void dropPearls(final int amaount, final Entity piglin){
        pearl.setAmount(amaount);
        Bukkit.getWorld(piglin.getWorld().getName()).dropItem(piglin.getLocation(), pearl);
    }

    /*@EventHandler
    public void weather
    must connect with the settings

     */
}