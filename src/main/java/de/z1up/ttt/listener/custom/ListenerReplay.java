package de.z1up.ttt.listener.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerReplay implements Listener {

    @EventHandler
    public void onCall(final AsyncPlayerChatEvent event) {

        /*
        String text = "[%time%] %playername% >> %message%";
        text = text.replaceAll("%time%", Data.replayFile.getTime());
        text = text.replaceAll("%playername%", player.getName());
        text = text.replaceAll("%message%", message);
        Data.replayFile.addLine(text);
        */

    }

}
