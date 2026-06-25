package portfolioservice.server.http;

public class ControllerRouteRegistry {
    private final String basePath;
    private final RouteRegistryBuilder builder;

    public ControllerRouteRegistry(String basePath, RouteRegistryBuilder builder) {
        this.basePath = normalizeBasePath(basePath);
        this.builder = builder;
    }

    public void get(String path, RouteHandler handler) {
        builder.get(fullPath(path), handler);
    }

    public void post(String path, RouteHandler handler) {
        builder.post(fullPath(path), handler);
    }

    public void put(String path, RouteHandler handler) {
        builder.put(fullPath(path), handler);
    }

    public void delete(String path, RouteHandler handler) {
        builder.delete(fullPath(path), handler);
    }

    private String fullPath(String path) {
        if (path == null || path.isBlank() || path.equals("/")) {
            return basePath;
        }

        if (path.startsWith("/")) {
            return basePath + path;
        }

        return basePath + "/" + path;
    }

    private String normalizeBasePath(String basePath) {
        if (basePath == null || basePath.isBlank() || basePath.equals("/")) {
            return "";
        }

        if (!basePath.startsWith("/")) {
            return "/" + basePath;
        }

        return basePath;
    }
}
