package de.z1up.ttt.util;

import de.z1up.ttt.TTT;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;


public class MapResetter implements Listener {

    private HashMap<Location, Material> blocks;
    private boolean recording;

    public MapResetter() {
        init();
        record();
    }

    private void init() {
        blocks = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    public void record() {
        recording = true;
    }

    public void stop() {
        recording = false;
    }

    public void reset() {

        if(recording) {
            return;
        }

        for(Location location : blocks.keySet()){
            Material material = blocks.get(location);
            location.getWorld().getBlockAt(location).setType(material);
        }

    }

    public void onMapBlockBreak(BlockBreakEvent event) {

        if(!recording) {
            return;
        }

        if(TTT.core.getBuildManager().canBuild(event.getPlayer())) {
            return;
        }

        if(!event.isCancelled()) {
            Bukkit.broadcastMessage("DESTROYED " + event.getBlock().getType());

            Block block = event.getBlock();

            if(blocks.containsKey(block.getLocation())) {
                blocks.remove(block.getLocation());
            }

            blocks.put(block.getLocation(), block.getType());
        }
    }

    public void onMapBlockBreak(Block block) {

        if(!recording) {
            return;
        }

        if(blocks.containsKey(block.getLocation())) {
            blocks.remove(block.getLocation());
        }

        blocks.put(block.getLocation(), block.getType());
    }

    public void onMapBlockPlace(BlockPlaceEvent event) {

        if(!recording) {
            return;
        }

        Player player = event.getPlayer();

        if(TTT.core.getBuildManager().canBuild(player)) {
            return;
        }

        if(!event.isCancelled()) {
            Bukkit.broadcastMessage("PLACED " + event.getBlock().getType());

            Block block = event.getBlock();

            if(blocks.containsKey(block.getLocation())) {
                blocks.remove(block.getLocation());
            }

            blocks.put(block.getLocation(), block.getType());
        }
    }

    public void onMapBlockPlace(Block block) {

        if(!recording) {
            return;
        }

        if(blocks.containsKey(block.getLocation())) {
            blocks.remove(block.getLocation());
        }

        blocks.put(block.getLocation(), block.getType());
    }

}
