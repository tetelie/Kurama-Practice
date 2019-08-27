package fr.tetelie.practice.ladder;

import fr.tetelie.practice.Practice;
import org.bukkit.Material;

public abstract class Ladder {

    public abstract String name();
    public abstract String displayName();
    public abstract Material material();
    public abstract byte data();

    // Kit editor
    public abstract boolean isAlterable();
    public abstract boolean additionalInventory();

}
