package fr.tetelie.practice.util;

import fr.tetelie.practice.Practice;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public @Getter @Setter class LocationHelper {

    @Getter static List<LocationHelper> all;

    static {
        all = new ArrayList<>();
    }

    private String name;
    private Location location;

    public LocationHelper(String name, Location location) {
        this.name = name;
        this.location = location;

        all.add(this);
    }

    public LocationHelper(String name) {
        this(name, null);
    }

    public void save() {
        if(location == null) return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getWorld().getName());
        stringBuilder.append(":");
        stringBuilder.append(location.getX());
        stringBuilder.append(":");
        stringBuilder.append(location.getY());
        stringBuilder.append(":");
        stringBuilder.append(location.getZ());
        stringBuilder.append(":");
        stringBuilder.append(location.getYaw());
        stringBuilder.append(":");
        stringBuilder.append(location.getPitch());
        if(stringBuilder.toString().equals(Practice.getInstance().locationConfig.get("locations." + name))) return;
        Practice.getInstance().locationConfig.set("locations." + name, stringBuilder.toString());
    }

    public boolean load() {
        if (Practice.getInstance().locationConfig.get("locations." + name) == null) return false;
        System.out.println(Practice.getInstance().locationConfig.get("locations."+name));
        String[] part = Practice.getInstance().locationConfig.getString("locations." + name).split(":");
        this.location = new Location(Bukkit.getWorld(part[0]), Double.parseDouble(part[1]), Double.parseDouble(part[2]), Double.parseDouble(part[3]), Float.parseFloat(part[4]), Float.parseFloat(part[5]));
        return true;
    }

    public static LocationHelper getLocationHelper(String name) {
        return all.stream().filter(locationHelper -> locationHelper.getName().equals(name)).findFirst().orElse(null);
    }

}
