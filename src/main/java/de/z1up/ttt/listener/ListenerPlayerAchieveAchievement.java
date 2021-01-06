package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.event.PlayerAchieveAchievementEvent;
import de.z1up.ttt.core.Core;
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

        if(!Core.wrapperPlayer.existsPlayer(player.getUniqueId())){
            return;
        }

        DBPlayer dbPlayer = (DBPlayer) Core.wrapperPlayer.get(player);

        if(dbPlayer.getAchievements().contains(achievement.getId())) {
            return;
        }

        player.sendMessage(Core.getPrefix() + "§7Du hast den Erfolg §a" + achievement.getName() + " §7erzielt§8!");

    }

}
