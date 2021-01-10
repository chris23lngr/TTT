package de.z1up.ttt.listener.custom;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerAchieveAchievementEvent;
import de.z1up.ttt.util.Messages;
import de.z1up.ttt.util.o.Achievement;
import de.z1up.ttt.util.o.DBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerPlayerAchieveAchievement implements Listener {

    public ListenerPlayerAchieveAchievement() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final PlayerAchieveAchievementEvent event) {

        Achievement achievement = event.getAchievement();
        Player player = event.getPlayer();

        if(!TTT.core.getPlayerManager().exists(player)){
            return;
        }

        DBPlayer dbPlayer = (DBPlayer) TTT.core.getPlayerManager().get(player.getUniqueId());

        if(dbPlayer.getAchievements().contains(achievement.getId())) {
            return;
        }

        player.sendMessage(Messages.ACHIEVEMENT_ACHIEVED + achievement.getName());

    }

}
