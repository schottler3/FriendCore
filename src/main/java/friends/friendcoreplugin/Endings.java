package friends.friendcoreplugin;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
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
        player.damage(4);
    }
}
