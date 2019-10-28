package fr.tetelie.practice.util;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter //justin est nul

 public class FakePlayerManager {

    public static List<FakePlayerManager> all;

    static
    {
        all = new ArrayList<>();
    }


    public void remove() {

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy());

    }

    public FakePlayerManager update() {

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(ep.getId()));
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(ep));

        }

        return this;

    }

    @SuppressWarnings("deprecation")
    public FakePlayerManager look(byte yaw, byte pitch) {

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutEntityLook(ep.getId(), (byte) (yaw * 256F / 360F), pitch, false));

        return this;

    }

    public FakePlayerManager head(byte yaw) {

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(ep, (byte) yaw));


        return this;

    }


    public FakePlayerManager death() {

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityStatus(ep, (byte) 3));

        return this;

    }

    private String name;
    private Property property;
    private Location loc;
    public EntityPlayer ep = null;


    public FakePlayerManager(String name, Property property, Location loc )
    {
        this.name = name;
        this.property = property;
        this.loc = loc;
        all.add(this);


        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        if (property != null)
            profile.getProperties().put("textures", property);


        ep = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) loc.getWorld()).getHandle(),
                profile, new PlayerInteractManager(((CraftWorld) loc.getWorld()).getHandle()));

        ep.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        update();
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public Property getProperty() {
        return property;
    }



    public void setProperty(Property property) {
        this.property = property;
    }



    public Location getLoc() {
        return loc;
    }

    public static FakePlayerManager getPlayer(final String p) {
        for (final FakePlayerManager pl : all) {
            if (pl.getName().equals(p)) {
                return pl;
            }
        }
        return null;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }





}
