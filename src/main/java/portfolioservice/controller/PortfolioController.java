package portfolioservice.controller;

import portfolioservice.server.http.HttpResponse;
import portfolioservice.server.http.route.ControllerRouteRegistry;
import portfolioservice.server.http.HttpController;
import portfolioservice.server.http.HttpRequest;

public class PortfolioController implements HttpController {

    @Override
    public String basePath() {
        return "/portfolios";
    }

    @Override
    public void registerRoutes(ControllerRouteRegistry routes) {
        routes.post("/", this::createPortfolio);
        routes.get("/{portfolioId}/summary", this::getSummary);
        routes.get("/{portfolioId}/history", this::getHistory);
    }

    public HttpResponse createPortfolio(HttpRequest request) {
        return HttpResponse.ok("{\"status\":\"OK\",\"type\":\"PORTFOLIO_CREATED\"}");
    }

    public HttpResponse getSummary(HttpRequest request) {
        String portfolioId = request.pathVariable("portfolioId");

        return HttpResponse.ok(
                "{\"status\":\"OK\",\"type\":\"SUMMARY\",\"portfolioId\":" + portfolioId + "}"
        );
    }

    public HttpResponse getHistory(HttpRequest request) {
        String portfolioId = request.pathVariable("portfolioId");

        return HttpResponse.ok(
                "{\"status\":\"OK\",\"type\":\"HISTORY\",\"portfolioId\":" + portfolioId + "}"
        );
    }
}
