package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;
import de.z1up.ttt.interfaces.Manager;
import de.z1up.ttt.mysql.wrapper.WrapperSpawn;
import de.z1up.ttt.util.o.DBPlayer;
import de.z1up.ttt.util.o.StatsHologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.NumberFormat;


public class StatsManager extends WrapperSpawn implements Manager {

    private Location holoLocation;

    public StatsManager() {
        super(Core.sql);
        load();
    }

    @Override
    public void load() {
        init();
    }

    @Override
    public void init() {

    }

    public void setHolo(Player player) {

        if(!TTT.core.getPlayerManager().exists(player)) {
            return;
        }

        DBPlayer dbPlayer = (DBPlayer) TTT.core.getPlayerManager().get(player);

        int kills = dbPlayer.getKills();
        int deaths = dbPlayer.getDeaths();
        int wins = dbPlayer.getWins();
        int looses = dbPlayer.getLooses();
        int karma = dbPlayer.getKarma();

        double kd = (double)kills / (double)deaths;
        NumberFormat n = NumberFormat.getInstance();
        n.setMaximumFractionDigits(2);

        String[] lines = {"§8│ §7Deine §cStats §8│",
                "§7Ranking §8■ §c0",
                "§7Kills §8■ §c" + kills,
                "§7Tode §8■ §c" + deaths,
                "§7Karma §8■ §c" + karma,
                "§7K/D §8■ §c" + n.format(kd),
                "§7Spiele gewonnen §8■ §c" + wins,
                "§7Spiele verloren §8■ §c" + looses};


        Location location = TTT.core.getLocationManager().getHoloLocation();

        StatsHologram hologram = new StatsHologram(player, location, lines);
        hologram.build();
        hologram.show();

    }
}
