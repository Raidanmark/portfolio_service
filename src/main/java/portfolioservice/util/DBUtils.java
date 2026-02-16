package portfolioservice.util;

import portfolioservice.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        return connection= DriverManager.getConnection(
                DbConfig.getUrl(),
                DbConfig.getUsername(),
                DbConfig.getPassword());
    }
}
