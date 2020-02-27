package fr.tetelie.practice.util;

import fr.tetelie.practice.Practice;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleApi {

    private PacketPlayOutWorldParticles packet;

    public ParticleApi(String type, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {
        float x = (float) loc.getX();
        float y = (float) loc.getY();
        float z = (float) loc.getZ();

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                type, x, y, z, xOffset, yOffset, zOffset, speed, count);

        this.packet = packet;
    }

    public void sendToAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(this.packet);
        }
    }

    public void sendToPlayer(Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(this.packet);
    }

}
