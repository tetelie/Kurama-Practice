package fr.tetelie.practice.leaderboard;

import co.aikar.idb.DB;
import fr.tetelie.practice.Practice;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.player.PlayerManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {

    Top[] top = new Top[99];
    Top global;

    public static int getRowNumber(final String table) {
        try {
            final PreparedStatement sts = Practice.getInstance().connection.prepareStatement("select count(*) from " + table);
            final ResultSet rs = sts.executeQuery();
            if (rs.next()) {
                return rs.getInt("count(*)");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Error while getting row numbers");
    }

   public Map<String, int[]> getTopElo() {
       final Map<String, int[]> top_elo = new HashMap<>();
       try {
           final PreparedStatement sts = Practice.getInstance().connection.prepareStatement("SELECT * FROM player_manager");
           final ResultSet rs = sts.executeQuery();
           //final ResultSetMetaData resultSetMetaData = rs.getMetaData();
           if (getRowNumber("player_manager") == 0) return null;
           if (rs.next()) {
               for (int i = 1; i <= getRowNumber("player_manager"); ++i) {
                   int[] elos = PlayerManager.getSplitValue(DB.getFirstRow("SELECT elos FROM player_manager WHERE ID=?", i).getString("elos"), ":");
                   String player_name = DB.getFirstRow("SELECT name FROM player_manager WHERE ID=?", i).getString("name");
                   top_elo.put(player_name, elos);
               }
               return top_elo;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       throw new NullPointerException("The player does not have any information in the table");
   }

   public void refresh()
   {
       Map<String, int[]> map = getTopElo();
       for(Ladder ladder : Practice.getInstance().ladders)
       {
           top[ladder.id()] = new Top(ladder.id(), map);
       }

       global = new Top(map);
   }

    public Top[] getTop() {
        return top;
    }

    public Top getGlobal() {
        return global;
    }

    public Leaderboard()
    {

    }



}
