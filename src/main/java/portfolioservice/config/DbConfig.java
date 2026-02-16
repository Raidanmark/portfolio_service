package portfolioservice.config;

public class DbConfig {
    public static String getUrl() { return mustGet("DB_URL"); }
    public static String getUsername() { return mustGet("DB_USERNAME"); }
    public static String getPassword() { return mustGet("DB_PASSWORD"); }

    private static String mustGet(String key) {
        String value = System.getenv(key);
        if (value == null) {
            throw new RuntimeException("Environment variable " + key + " is required but not set.");
        }
        return value;
    }
}
