package portfolioservice.controller;

import lombok.AllArgsConstructor;
import portfolioservice.dto.portfolio.PortfolioCreateDto;
import portfolioservice.dto.portfolio.PortfolioDto;
import portfolioservice.mapper.PortfolioMapper;
import portfolioservice.server.http.HttpResponse;
import portfolioservice.server.http.route.ControllerRouteRegistry;
import portfolioservice.server.http.HttpController;
import portfolioservice.server.http.HttpRequest;
import portfolioservice.service.PortfolioService;

@AllArgsConstructor
public class PortfolioController implements HttpController {

    private final PortfolioService portfolioService;
    private final PortfolioMapper portfolioMapper;

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

    public PortfolioDto createPortfolio(HttpRequest request) {
        PortfolioCreateDto createDto = portfolioMapper.toCreateDto(request);
        //TODO : Implement validation for the PortfolioCreateDto

        return portfolioService.createPortfolio(createDto);
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
