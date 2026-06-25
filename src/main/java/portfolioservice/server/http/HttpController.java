package portfolioservice.server.http;

public interface HttpController {
    String basePath();
    void registerRoutes(ControllerRouteRegistry routes);
}
