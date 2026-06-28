package portfolioservice.server.http;

import portfolioservice.server.http.route.ControllerRouteRegistry;

public interface HttpController {
    String basePath();
    void registerRoutes(ControllerRouteRegistry routes);
}
