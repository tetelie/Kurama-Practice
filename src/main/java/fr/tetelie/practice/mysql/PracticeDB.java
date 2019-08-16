package fr.tetelie.practice.mysql;

import co.aikar.idb.DbStatement;
import fr.tetelie.practice.Practice;

import java.sql.SQLException;

public class PracticeDB {

    public boolean createPlayerManagerTable(DbStatement stm)
    {
        String player_manager = "CREATE TABLE player_manager ("
                + "ID INT(64) NOT NULL AUTO_INCREMENT,"
                + "player_name VARCHAR(16) NOT NULL,"
                + "player_uuid VARCHAR(36) NOT NULL,"
                + "settings VARCHAR(5) DEFAULT '0:0:0',"
                + "elos VARCHAR(32) DEFAULT '1000',"
                + "PRIMARY KEY (`ID`))";
        try {
            stm.query(player_manager);
            System.out.println(Practice.getInstance().prefix+"SUCESS create player_manager table.");
            return true;
        } catch (SQLException e)
        {
            System.out.println(Practice.getInstance().prefix+"ERROR while creating player_manager table.");
            e.printStackTrace();
        }
        return false;
    }

}
