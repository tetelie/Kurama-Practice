package fr.tetelie.practice;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.tetelie.practice.object.PlayerManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class Practice extends JavaPlugin {

    private @Getter static Practice instance;

    public Connection connection;

    @Override
    public void onEnable() {
        instance = this;
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
            connection = ds.getConnection();
        } catch (SQLException e)
        {
            System.out.println("[Practice] Error could not connect to SQL database.");
            e.printStackTrace();
        }
        System.out.println("[Practice] Successfully connected to the SQL database.");
    }

}
