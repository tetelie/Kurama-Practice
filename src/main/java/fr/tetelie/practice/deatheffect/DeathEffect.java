package fr.tetelie.practice.deatheffect;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class DeathEffect {

    public abstract int id();

    public abstract void invoke(Player player, Location location);

}
