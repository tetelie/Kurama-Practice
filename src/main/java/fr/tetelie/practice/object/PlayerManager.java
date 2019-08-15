package fr.tetelie.practice.object;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.ladder.NoDebuff;
import fr.tetelie.practice.uti.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public @Getter @Setter class PlayerManager {

    private @Getter static List<PlayerManager> all;

    private UUID uuid;

    static
    {
        all = new ArrayList<>();
    }

    public PlayerManager(UUID uuid)
    {
        this.uuid = uuid;

        all.add(this);
    }

    public void send(Kit kit)
    {
        Player player = Bukkit.getPlayer(uuid);
        player.getInventory().clear();
        player.getInventory().setContents(kit.content());
        player.getInventory().setArmorContents(kit.armor());
        player.updateInventory();
    }

    public static PlayerManager getPlayerManager(UUID uuid)
    {
        return all.stream().filter(target -> target.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void destroy()
    {
        all.remove(this);
    }

}
