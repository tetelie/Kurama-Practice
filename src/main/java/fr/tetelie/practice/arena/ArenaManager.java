package fr.tetelie.practice.arena;

import fr.tetelie.practice.Practice;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    public ArenaManager(String name)
    {
        this(name, null, null, null);
    }

    public static ArenaManager getArena(String name)
    {
        return all.stream().filter(arenaManager -> arenaManager.name.equals(name)).findFirst().orElse(null);
    }

    public void save()
    {
        if(arenaType == null || loc1 == null || loc2 == null) return;
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".type", this.getArenaType().toString());
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".pos1", getStringLocation(loc1));
        Practice.getInstance().arenaConfig.set("arena."+this.getName()+".pos2", getStringLocation(loc2));
    }

    private String getStringLocation(Location loc)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(loc.getWorld().getName());
        stringBuilder.append(":");
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

    public void load()
    {
        if(Practice.getInstance().arenaConfig.getString("arena."+this.getName()+".type") == null) return;
        arenaType = ArenaType.valueOf(Practice.getInstance().arenaConfig.getString("arena."+this.getName()+".type").toUpperCase());
        loc1 = getSplitLocation(1);
        loc2 = getSplitLocation(2);
    }

    private Location getSplitLocation(int pos)
    {
        if(Practice.getInstance().arenaConfig.getString("arena."+this.getName()+".pos"+pos) == null) return  null;
        String[] split = Practice.getInstance().arenaConfig.getString("arena."+this.getName()+".pos"+pos).split(":");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static ArenaManager getRandomArena(ArenaType arenaType)
    {
        List<ArenaManager> availableArena = all.stream().filter(arenaManager -> arenaManager.getArenaType() == arenaType).collect(Collectors.toList());
        Collections.shuffle(availableArena);
        return availableArena.get(0);
    }

}
