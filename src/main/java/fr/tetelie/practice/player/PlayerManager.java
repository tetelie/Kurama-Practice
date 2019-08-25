package fr.tetelie.practice.player;

import co.aikar.idb.DB;
import fr.tetelie.practice.Practice;
import fr.tetelie.practice.inventory.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public @Getter @Setter class PlayerManager {

    private @Getter static Map<UUID, PlayerManager> all;

    private UUID uuid;
    private String name;
    private int[] settings = new int[3];
    private int[] elos = new int[1];

    static
    {
        all = new HashMap<>();
    }

    public PlayerManager(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
        update();

        all.add(this);
    }

    private boolean exist()
    {
        return Practice.getInstance().practiceDB.existPlayerManager(uuid);
    }

    private void update(){
        if(!exist()) {
            Practice.getInstance().practiceDB.createPlayerManager(uuid, name); // insert new table
        }else{
            Practice.getInstance().practiceDB.updatePlayerManager(name, uuid); // update name in corresponding uuid table
            load(); // load all mysql data
        }
    }

    private void load(){
        try {
            settings = getSplitValue(DB.getFirstRow("SELECT settings FROM player_manager WHERE name=?", name).getString("settings"), ":");
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void save(){
        try{
            String settings = getStringValue(this.settings, ":");
            DB.executeUpdate("UPDATE player_manager SET settings=? WHERE name=?", settings, name);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private int[] getSplitValue(String string, String spliter)
    {
        String[] split = string.split(spliter);
        int[] board = new int[split.length];

        for(int i = 0; i <= split.length-1; i++)
        {
            board[i] = Integer.parseInt(split[i]);
        }
        return board;
    }

    private String getStringValue(int[] board, String spliter)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i <= board.length-1; i++)
        {
            stringBuilder.append(board[i]);
            if(i != board.length-1) stringBuilder.append(spliter);
        }
        return stringBuilder.toString();
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
        return all.get(uuid);
    }

    public void destroy()
    {
        save(); // save all local data in database
        all.remove(this);
    }

}
