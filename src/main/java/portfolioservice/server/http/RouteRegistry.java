package portfolioservice.server.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
public class RouteRegistry {

    private final Map<RouteKey, RouteHandler> staticRoutes;
    private final Map<RouteKey, RouteHandler> dynamicRoutes;

    public RouteRegistry(
            Map<RouteKey, RouteHandler> staticRoutes,
            Map<RouteKey, RouteHandler> dynamicRoutes
    ) {
        this.staticRoutes = Map.copyOf(staticRoutes);
        this.dynamicRoutes = Map.copyOf(dynamicRoutes);
    }
}
