package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public  class RouteRegistryBuilder {
    private final Map<RouteKey, RouteHandler> staticRoutes = new HashMap<>();
    private final Map<RouteKey, RouteHandler> dynamicRoutes = new HashMap<>();

    public RouteRegistryBuilder registerController(HttpController controller) {
        ControllerRouteRegistry controllerRoutes =
                new ControllerRouteRegistry(controller.basePath(), this);

        controller.registerRoutes(controllerRoutes);

        return this;
    }

    public void get(String path, RouteHandler handler) {
        register(HttpMethod.GET, path, handler);
    }

    public void post(String path, RouteHandler handler) {
        register(HttpMethod.POST, path, handler);
    }

    public void put(String path, RouteHandler handler) {
        register(HttpMethod.PUT, path, handler);
    }

    public void delete(String path, RouteHandler handler) {
        register(HttpMethod.DELETE, path, handler);
    }

    private void register(HttpMethod method, String path, RouteHandler handler) {
        RouteKey routeKey = new RouteKey(method, path);

        if (path.contains("{")) {
            dynamicRoutes.put(routeKey, handler);
        } else {
            staticRoutes.put(routeKey, handler);
        }
    }

    public RouteRegistry build() {
        return new RouteRegistry(staticRoutes, dynamicRoutes);
    }
}
