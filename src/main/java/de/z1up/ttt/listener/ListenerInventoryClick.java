package de.z1up.ttt.listener;

import de.z1up.ttt.TTT;
import de.z1up.ttt.util.Data;
import de.z1up.ttt.util.o.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import sun.awt.IconInfo;

import java.awt.dnd.DropTarget;

public class ListenerInventoryClick implements Listener {

    public ListenerInventoryClick() {
        Bukkit.getPluginManager().registerEvents(this, TTT.getInstance());
    }

    @EventHandler
    public void onCall(final InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player whoClicked = (Player) event.getWhoClicked();

        if(Data.buildManager.canBuild(whoClicked)) {
            event.setCancelled(false);
            return;
        }

        if (Data.playerManager.isSpec(whoClicked)) {
            event.setCancelled(true);
            return;
        }

        if(Data.gameManager.inGame()) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) return;
        if(event.getInventory() == null) return;
        if(event.getSlotType() == null) return;
        if(event.getSlot() == -1 ) return;
        if(event.getClickedInventory() == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;

        if(event.getClickedInventory().getTitle().equals("§bMapvoting")) {
            onMapVote(event);
        }

    }

    private void onMapVote(InventoryClickEvent context) {

        System.out.println("cli");

        ItemStack itemStack = context.getCurrentItem();
        String display = itemStack.getItemMeta().getDisplayName();

        String mapName = display.replaceAll("§a", "");

        if(!Data.mapWrapper.existsName(mapName)) {
            return;
        }



        Map map = (Map) Data.mapWrapper.get(mapName);

        if(Data.voteManager.isVotePeriodActive()) {
            Data.voteManager.vote((Player) context.getWhoClicked(), map);
            ((Player) context.getWhoClicked()).closeInventory();
        }


    }

}
