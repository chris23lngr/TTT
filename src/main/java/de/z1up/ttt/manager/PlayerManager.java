package de.z1up.ttt.manager;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PlayerManager {

    private ArrayList<Player> alives;
    private ArrayList<Player> traitors;
    private ArrayList<Player> detectives;
    private ArrayList<Player> innos;
    private ArrayList<Player> specs;

    public PlayerManager() {
        init();
    }

    void init() {
        alives = new ArrayList<>();
        traitors = new ArrayList<>();
        detectives = new ArrayList<>();
        innos = new ArrayList<>();
        specs = new ArrayList<>();
    }

    public boolean isInno(Player player) {
        return innos.contains(player);
    }

    public boolean isDetective(Player player) {
        return detectives.contains(player);
    }

    public boolean isTraitor(Player player) {
        return traitors.contains(player);
    }

    public boolean isSpec(Player player) {
        return specs.contains(player);
    }

    public void addSpectator(Player player) {
        specs.add(player);
    }

    public void enterSpecMode(Player player) {
        hideSpecFromPlayers(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(TTT.getInstance(), () -> {

            //player.teleport(Spawns.getLobby());
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setFoodLevel(20);
            player.setHealth(20.0D);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.getInventory().setItem(0,
                    new ItemBuilder(Material.COMPASS, (short) 0)
                    .setDisplayName("§9§lLebende Spieler").build());
            player.getInventory().setItem(8,
                    new ItemBuilder(Material.MAGMA_CREAM, (short) 0)
                    .setDisplayName("§8§lSpiel verlassen").build());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                    99999999, 1, false, false));

        }, 10L);

    }

    public void hideSpecFromPlayers(Player player) {
        alives.forEach(alive -> alive.hidePlayer(player));
    }

    public ArrayList<Player> getSpecs() {
        return specs;
    }
}
