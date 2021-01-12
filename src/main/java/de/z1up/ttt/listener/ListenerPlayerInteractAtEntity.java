package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.DeadBody;
import de.z1up.ttt.util.o.TTTPlayer;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;

public class ListenerPlayerInteractAtEntity implements Listener {

    public ListenerPlayerInteractAtEntity() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerInteractAtEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(!(entity instanceof Zombie)) {
            System.out.println("NO ZOMBIE");
            return;
        }

        if(player.getItemInHand() == null) {
            return;
        }

        if(player.getItemInHand().getType() != Material.STICK) {
            return;
        }

        Zombie zombie = (Zombie) entity;

        ArrayList<DeadBody> bodies = TTT.core.getPlayerManager().getDeadBodies();

        DeadBody deadBody = null;

        for (DeadBody body : bodies) {
            Entity bodiyEntity = body.getEntity();
            if(bodiyEntity.getEntityId() == zombie.getEntityId()) {
                deadBody = body;
            }
        }

        if(deadBody == null) {
            System.out.println("DEAD BODY NOT FOUND");
            return;
        }

        TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);

        if(!deadBody.isProcessed()) {

            if(tttPlayer.hasTeam()) {

                if(tttPlayer.getTeam() == ManagerTeam.Team.DETECTIVE) {

                    deadBody.process();
                    deadBody.update();
                    String name = UUIDFetcher.getName(deadBody.getUuid());
                    Bukkit.broadcastMessage(Messages.PREFIX + "§aDie Leiche von §c" + name + " §awurde gefunden!");

                } else {
                    player.sendMessage(Messages.DEAD_BODY_NOT_PROCESSED);

                }

            }
            return;
        }

        player.sendMessage(Messages.DEAD_BODY_OF + UUIDFetcher.getName(deadBody.getUuid()));
        player.sendMessage(Messages.DEAD_BODY_WAS + deadBody.getTeam().toString());

    }

}
