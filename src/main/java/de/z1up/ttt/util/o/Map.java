package de.z1up.ttt.util.o;

import de.z1up.ttt.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Map {

    private int id;
    private String name;
    private Material itemMat;
    private HashMap<RatingType, Integer> ratings;
    private Collection<Spawn> spawns;
    private int votes;

    public Map(int id, String name, Material itemMat, HashMap<RatingType, Integer> ratings, @Nullable Collection<Spawn> spawns) {
        this.id = id;
        this.name = name;
        this.itemMat = itemMat;
        this.ratings = ratings;
        if(spawns == null) {
            this.spawns = new ArrayList<>();
        } else {
            this.spawns = spawns;
        }
        if(ratings == null) {
            this.ratings = new HashMap<>();
        } else {
            this.ratings = ratings;
        }
        fillRatings();
        votes = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<RatingType, Integer> getRatings() {
        return ratings;
    }

    public Material getItemMat() {
        return itemMat;
    }

    public void addSpawn(Spawn e) {
        spawns.add(e);
    }

    public void removeSpawn(Spawn e) {
        spawns.remove(e);
    }

    public Collection<Spawn> getSpawns() {
        return spawns;
    }

    public enum RatingType {
        VERY_BAD, BAD, NEUTRAL, GOOD, VERY_GOOD
    }

    private void fillRatings() {
        if(ratings.get(RatingType.VERY_BAD) == null) {
            ratings.put(RatingType.VERY_BAD, 0);
        }
        if(ratings.get(RatingType.BAD) == null) {
            ratings.put(RatingType.BAD, 0);
        }
        if(ratings.get(RatingType.NEUTRAL) == null) {
            ratings.put(RatingType.NEUTRAL, 0);
        }
        if(ratings.get(RatingType.GOOD) == null) {
            ratings.put(RatingType.GOOD, 0);
        }
        if(ratings.get(RatingType.VERY_GOOD) == null) {
            ratings.put(RatingType.VERY_GOOD, 0);
        }
    }

    public int getVotes() {
        return votes;
    }

    public void addVote() {
        votes = votes + 1;
    }

    public void removeVote() {
        votes = votes - 1;
    }

    public void setSpawns(Collection<Spawn> spawns) {
        this.spawns = spawns;
    }
}
