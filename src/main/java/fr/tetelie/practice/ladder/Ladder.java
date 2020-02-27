package fr.tetelie.practice.ladder;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.arena.ArenaType;
import org.bukkit.Material;

public abstract class Ladder {

    public abstract String name();
    public abstract String displayName();
    public abstract Material material();
    public abstract byte data();
    public abstract ArenaType arenaType();
    public abstract int id();

    // Kit editor
    public abstract boolean isAlterable();
    public abstract boolean additionalInventory();

    public static Ladder getLadder(String displayName)
    {
        return Practice.getInstance().ladders.stream().filter(ladder -> ladder.displayName().equals(displayName)).findFirst().orElse(null);
    }

}
