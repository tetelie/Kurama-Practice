package fr.tetelie.practice;

import co.aikar.idb.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.tetelie.practice.event.JoinEvent;
import fr.tetelie.practice.event.QuitEvent;
import fr.tetelie.practice.mysql.PracticeDB;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public @Getter class Practice extends JavaPlugin {

    private @Getter static Practice instance;

    public Connection connection;
    public String prefix;
    public PracticeDB practiceDB = new PracticeDB();

    private String configPath;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "[" + this.getName() + "] ";
        configPath = getDataFolder() + "\\hikari.properties";
        saveResource("hikari.properties", false);
        registerEvent();
        registerCommand();
        // Database start
        setupHikariCP();
        setupDatabase();
        // Database end
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
        Arrays.asList(
                new JoinEvent(),
                new QuitEvent()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private void setupDatabase() {
        if (connection != null) {
            practiceDB.createPlayerManagerTable();
        } else {
            System.out.println(prefix+"WARNING enter valid database information ("+configPath+") \n You will not be able to access many features");
        }
    }

    private void setupHikariCP() {
        try {
            HikariConfig config = new HikariConfig(configPath);
            HikariDataSource ds = new HikariDataSource(config);
            String passwd = config.getDataSourceProperties().getProperty("password") == null ? "" : config.getDataSourceProperties().getProperty("password");
            Database db = BukkitDB.createHikariDatabase(this, config.getDataSourceProperties().getProperty("user"), passwd, config.getDataSourceProperties().getProperty("databaseName"), config.getDataSourceProperties().getProperty("serverName") + ":" + config.getDataSourceProperties().getProperty("portNumber"));
            DB.setGlobalDatabase(db);
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.out.println(prefix + "Error could not connect to SQL database.");
            e.printStackTrace();
        }
        System.out.println(prefix + "Successfully connected to the SQL database.");
    }

}
