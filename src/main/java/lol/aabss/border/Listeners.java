package lol.aabss.border;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class Listeners implements Listener {
    @EventHandler
    public void onEntityMove(EntityMoveEvent e) {
        LivingEntity mob = Border.borderMob;
        if (e.getEntity() == mob) {
            WorldBorder border = e.getEntity().getWorld().getWorldBorder();
            border.setCenter(e.getEntity().getLocation());
            int size = Integer.parseInt(Brdr.getConfig("border-size"));
            border.setSize(size, 1);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        LivingEntity mob = Border.borderMob;
        if (e.getEntity() == mob) {
            WorldBorder border = e.getEntity().getWorld().getWorldBorder();
            border.setCenter(e.getEntity().getLocation());
            int size = Integer.parseInt(Brdr.getConfig("border-size"));
            border.setSize(size);
            mob.setHealth(Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity mob = Border.borderMob;
        if (e.getEntity() == mob) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        if (!p.getWorld().getWorldBorder().isInside(p.getLocation())) {
            p.setMetadata("diedFromBorder", new FixedMetadataValue(Border.getPlugin(Border.class), true));
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.hasMetadata("diedFromBorder") && p.getMetadata("diedFromBorder").get(0).asBoolean()) {
            e.setRespawnLocation(Border.borderMob.getLocation());
            p.removeMetadata("diedFromBorder", Border.getPlugin(Border.class));
        }
    }
}
