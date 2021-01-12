package de.z1up.ttt.util.o;

import de.z1up.ttt.TTT;
import de.z1up.ttt.manager.ManagerTeam;
import de.z1up.ttt.util.EntityModifier;
import de.z1up.ttt.util.ItemBuilder;
import de.z1up.ttt.util.uuid.UUIDFetcher;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeadBody {

    private UUID uuid;
    private ManagerTeam.Team team;
    private UUID killedBy;
    private Location location;
    private Entity entity;
    private boolean processed;

    public DeadBody(UUID uuid, ManagerTeam.Team team, UUID killedBy, Location location, boolean processed) {
        this.uuid = uuid;
        this.team = team;
        this.killedBy = killedBy;
        this.location = location;
        this.processed = processed;
    }

    public void spawn() {

        entity = location.getWorld().spawn(location, Zombie.class);
        EntityModifier modifier = new EntityModifier(entity, TTT.getInstance());
        modifier.modify().setDisplayName("§4Nicht identifiziert")
                .setNoAI(true).setCanDespawn(false)
                .setFireTicks(0)
                .setCanPickUpLoot(false)
                .setInvulnerable(true).build();
    }

    public boolean isProcessed() {
        return processed;
    }

    public void update() {

        despawn();

        String name = UUIDFetcher.getName(uuid);

        entity = location.getWorld().spawn(location, Zombie.class);
        EntityModifier modifier = new EntityModifier(entity, TTT.getInstance());
        modifier.modify().setDisplayName("§eName:" + name)
                .setNoAI(true).setCanDespawn(false)
                .setFireTicks(0)
                .setCanPickUpLoot(false)
                .setInvulnerable(true).build();

        Zombie zombie = location.getWorld().spawn(location, Zombie.class);

        ItemStack head = new ItemBuilder(name).build();
        zombie.getEquipment().setHelmet(head);

    }

    public void despawn() {
        entity.remove();
    }

    public void process() {
        this.processed = true;
    }

    public Entity getEntity() {
        return entity;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getKilledBy() {
        return killedBy;
    }

    public ManagerTeam.Team getTeam() {
        return team;
    }
}
