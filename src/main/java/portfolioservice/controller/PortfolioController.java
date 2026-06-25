package portfolioservice.controller;

import lombok.AllArgsConstructor;
import portfolioservice.server.http.AppHttpResponse;
import portfolioservice.server.http.ControllerRouteRegistry;
import portfolioservice.server.http.HttpController;
import portfolioservice.server.http.HttpRequestData;
import portfolioservice.service.PortfolioService;

@AllArgsConstructor
public class PortfolioController implements HttpController {
    private final PortfolioService portfolioService;

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

    public AppHttpResponse createPortfolio(HttpRequestData request) {
        return AppHttpResponse.ok("{\"status\":\"OK\",\"type\":\"PORTFOLIO_CREATED\"}");
    }

    public AppHttpResponse getSummary(HttpRequestData request) {
        String portfolioId = request.pathVariable("portfolioId");

        return AppHttpResponse.ok(
                "{\"status\":\"OK\",\"type\":\"SUMMARY\",\"portfolioId\":" + portfolioId + "}"
        );
    }

    public AppHttpResponse getHistory(HttpRequestData request) {
        String portfolioId = request.pathVariable("portfolioId");

        return AppHttpResponse.ok(
                "{\"status\":\"OK\",\"type\":\"HISTORY\",\"portfolioId\":" + portfolioId + "}"
        );
    }
}
