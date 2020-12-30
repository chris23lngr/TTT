package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.MessageAPI;
import de.z1up.ttt.util.UserAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.internal.runners.statements.RunAfters;

public class ListenerPlayerJoin implements Listener {

    public ListenerPlayerJoin() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        UserAPI.resetPlayer(player);

        if(Data.gameManager.inLobby()) {

            event.setJoinMessage(Data.getPrefix() + "§c" + player.getName()
                    + " §7ist dem Spiel beigetreten§8.");

            // inventory items
            player.getInventory().setItem(4, Data.ruleBook.getBook());
            player.getInventory().setItem(0,
                    new ItemBuilder(Material.REDSTONE_TORCH_ON, (short) 0)
                            .setDisplayName("§4§lEinstellungen").build());
            player.getInventory().setItem(8,
                    new ItemBuilder(Material.MAGMA_CREAM, (short) 0)
                            .setDisplayName("§8§lSpiel verlassen").build());


            // effects
            MessageAPI.sendTitle(player, 20, 40, 20, "§4TTT", "§a§lRenixinside.de");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);

            Data.sbManager.setScoreBoard(player, Data.gameManager.getGameState());

            return;
        }

        if(Data.gameManager.inGame() || Data.gameManager.inSavePhase()) {

            event.setJoinMessage(null);

            Data.playerManager.addSpectator(player);
            Data.playerManager.enterSpecMode(player);

            return;
        }

    }

}
