package de.z1up.ttt.util;

import com.mojang.authlib.GameProfile;
import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.o.TTTPlayer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class FakeNameTags extends Reflections {

    private static void sendTraitorNameTag(Player player, Collection<Player> targets) {

        removePlayer((CraftPlayer) player, targets);
        addPlayer((CraftPlayer) player, "§4" + player.getName(), targets);

    }

    private static void sendDetectiveNameTag(Player player, Collection<Player> targets) {

        removePlayer((CraftPlayer) player, targets);
        addPlayer((CraftPlayer) player, "§9" + player.getName(), targets);

    }

    private static void sendInnocentNameTag(Player player, Collection<Player> targets) {

        removePlayer((CraftPlayer) player, targets);
        addPlayer((CraftPlayer) player, "§a" + player.getName(), targets);

    }


    private static void removePlayer(CraftPlayer craftPlayer, Collection<Player> targets) {

        final PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);

        GameProfile profile = craftPlayer.getProfile();
        WorldSettings.EnumGamemode gm = WorldSettings.EnumGamemode.valueOf(craftPlayer.getGameMode().toString());
        IChatBaseComponent display = new ChatMessage(craftPlayer.getDisplayName());

        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(profile, 1, gm, display);

        setValue(packet, "b", Arrays.asList(data));

        for (Player target : targets) {
            sendPacket(target, packet);
        }

    }

    private static void addPlayer(CraftPlayer craftPlayer, String tag, Collection<Player> targets) {

        final PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);

        GameProfile profile = craftPlayer.getProfile();
        WorldSettings.EnumGamemode gm = WorldSettings.EnumGamemode.valueOf(craftPlayer.getGameMode().toString());
        IChatBaseComponent display = new ChatMessage(tag);

        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(profile, 1, gm, display);

        setValue(packet, "b", Arrays.asList(data));


        for (Player target : targets) {
            sendPacket(target, packet);
        }

    }

    public static void sendNametags() {

        for(Player player : Bukkit.getOnlinePlayers()) {

            if(TTT.core.getPlayerManager().existsPlayer(player.getUniqueId())) {

                TTTPlayer tttPlayer = TTT.core.getPlayerManager().getTTTPlayer(player);
                ManagerTeam.Team team = tttPlayer.getTeam();

                if(team == ManagerTeam.Team.TRAITOR) {

                    Iterator targets = Bukkit.getOnlinePlayers().iterator();

                    Collection<Player> traitors = new ArrayList<>();
                    Collection<Player> others = new ArrayList<>();

                    while(targets.hasNext()) {

                        Player target = (Player) targets.next();

                        if(TTT.core.getPlayerManager().existsPlayer(target.getUniqueId())) {

                            ManagerTeam.Team targetTeam = TTT.core.getPlayerManager().getTTTPlayer(target).getTeam();

                            if(targetTeam == ManagerTeam.Team.TRAITOR) {

                                traitors.add(target);

                            } else {
                                others.add(target);
                            }
                        }
                    }

                    sendTraitorNameTag(player, traitors);
                    sendInnocentNameTag(player, others);

                } else if(team == ManagerTeam.Team.DETECTIVE) {

                    Collection players = Bukkit.getOnlinePlayers();
                    sendDetectiveNameTag(player, players);

                } else {
                    Collection players = Bukkit.getOnlinePlayers();
                    sendInnocentNameTag(player, players);
                }

            }

        }

    }


}
