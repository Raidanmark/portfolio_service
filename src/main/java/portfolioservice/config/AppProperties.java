package portfolioservice.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private final Properties properties = new Properties();

    public AppProperties() {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                throw new IllegalStateException("application.properties not found");
            }

            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load application.properties", exception);
        }
    }

    public int httpPort() {
        return getInt("http.port");
    }

    public int tcpPort() {
        return getInt("tcp.port");
    }

    public String dbUrl() {
        return getString("db.url");
    }

    public String dbUsername() {
        return getString("db.username");
    }

    public String dbPassword() {
        return getString("db.password");
    }

    private String getString(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing property: " + key);
        }

        return value;
    }

    private int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

}
