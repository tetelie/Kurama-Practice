package fr.tetelie.practice;

import co.aikar.idb.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class Practice extends JavaPlugin {

    private @Getter static Practice instance;

    public Connection connection;
    public String prefix;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "["+this.getName()+"] ";
        saveResource("hikari.properties", false);
        registerEvent();
        registerCommand();
        setupHikariCP();
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
    }

    private void registerCommand() {

    }

    private void registerEvent() {

    }

    private void setupHikariCP()
    {
        String configPath = getDataFolder()+"\\hikari.properties";
        try {
            HikariConfig config = new HikariConfig(configPath);
            HikariDataSource ds = new HikariDataSource(config);
            String passwd = config.getDataSourceProperties().getProperty("password") == null ? "" : config.getDataSourceProperties().getProperty("password");
            Database db = BukkitDB.createHikariDatabase(this, config.getDataSourceProperties().getProperty("user"), passwd, config.getDataSourceProperties().getProperty("databaseName"), config.getDataSourceProperties().getProperty("serverName")+":"+config.getDataSourceProperties().getProperty("portNumber"));
            DB.setGlobalDatabase(db);
            connection = ds.getConnection();
        } catch (SQLException e)
        {
            System.out.println(prefix+"Error could not connect to SQL database.");
            e.printStackTrace();
        }
        System.out.println(prefix+"Successfully connected to the SQL database.");
    }

}
