package portfolioservice.controller;

import com.sun.net.httpserver.HttpExchange;
import portfolioservice.dto.Response;
import portfolioservice.service.PortfolioService;

import java.io.IOException;

public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public Response createPortfolio(HttpExchange exchange) throws IOException {
        return Response.ok(portfolioService);
    }
}
