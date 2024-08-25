package friends.friendcoreplugin.tools;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class Endings {

    public static void pickaxe(Player player) {
        Particle particle = Particle.EXPLOSION;
        int count = 5;
        double offsetX = 0.0;
        double offsetY = 2;
        double offsetZ = 0.0;
        double speed = 5;
        Location local = player.getLocation();

        player.getWorld().spawnParticle(particle, local, count, offsetX, offsetY, offsetZ, speed);

        player.playEffect(EntityEffect.HURT_EXPLOSION);
        player.playEffect(local, Effect.EXTINGUISH, null);
        player.damage(10);
    }

    public static void shovel(Player player) {
        Particle particle = Particle.CRIT;
        int count = 5;
        double offsetX = 0.0;
        double offsetY = 2;
        double offsetZ = 0.0;
        double speed = 1;
        Location local = player.getLocation();

        player.getWorld().spawnParticle(particle, local, count, offsetX, offsetY, offsetZ, speed);

        player.playEffect(EntityEffect.HONEY_BLOCK_FALL_PARTICLES);
        player.playEffect(local, Effect.COPPER_WAX_OFF, null);
    }

    public static void axe(Player player) {
        Particle particle = Particle.CRIT;
        int count = 5;
        double offsetX = 0.0;
        double offsetY = 2;
        double offsetZ = 0.0;
        double speed = 1;
        Location local = player.getLocation();

        player.getWorld().spawnParticle(particle, local, count, offsetX, offsetY, offsetZ, speed);

        player.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
        player.playEffect(local, Effect.ANVIL_BREAK, null);
        player.damage(6);
    }

    public static void sword(Player player) {
        Particle particle = Particle.EXPLOSION;
        int count = 5;
        double offsetX = 0.0;
        double offsetY = 2;
        double offsetZ = 0.0;
        double speed = 1;
        Location local = player.getLocation();
        World world = local.getWorld();
        if(world == null) return;

        world.spawnParticle(particle, local, count, offsetX, offsetY, offsetZ, speed);

        world.strikeLightning(local);

        player.playEffect(local, Effect.ANVIL_BREAK, null);
        player.playEffect(local, Effect.SMOKE, null);
        player.damage(6);
    }
}
