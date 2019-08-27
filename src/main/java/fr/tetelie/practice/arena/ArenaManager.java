package fr.tetelie.practice.arena;

import fr.tetelie.practice.Practice;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.text.StrBuilder;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ArenaManager {

    @Getter static List<ArenaManager> all;

    static
    {
        all = new ArrayList<>();
    }

    private String name;
    private ArenaType arenaType;
    private Location loc1;
    private Location loc2;

    public ArenaManager(String name, ArenaType arenaType, Location loc1, Location loc2)
    {
        this.name = name;
        this.arenaType = arenaType;
        this.loc1 = loc1;
        this.loc2 = loc2;

        all.add(this);
    }

    public ArenaManager(String name, ArenaType arenaType)
    {
        this(name, arenaType, null, null);
    }

    public static ArenaManager getArena(String name)
    {
        return all.stream().filter(arenaManager -> arenaManager.name.equals(name)).findFirst().orElse(null);
    }

    public void save()
    {
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".type", this.getArenaType().toString());
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".pos1", getStringLocation(loc1));
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".pos2", getStringLocation(loc2));
    }

    private String getStringLocation(Location loc)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(loc.getX());
        stringBuilder.append(":");
        stringBuilder.append(loc.getY());
        stringBuilder.append(":");
        stringBuilder.append(loc.getZ());
        stringBuilder.append(":");
        stringBuilder.append(loc.getYaw());
        stringBuilder.append(":");
        stringBuilder.append(loc.getPitch());
        return stringBuilder.toString();
    }

}
