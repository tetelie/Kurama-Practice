package fr.tetelie.practice.deatheffect.deatheffects;

import fr.tetelie.practice.deatheffect.DeathEffect;
import fr.tetelie.practice.util.ParticleApi;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SmokeEffect extends DeathEffect {

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void invoke(Player player, Location location) {
        ParticleApi particleApi = new ParticleApi("largesmoke", location, 0.1f, 0.1f, 0.1f, 0.08f, 40);
        player.playSound(location, Sound.FIREWORK_LARGE_BLAST, 1f, 1f);
        particleApi.sendToPlayer(player);
    }
}
