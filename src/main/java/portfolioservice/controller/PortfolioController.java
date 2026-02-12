package portfolioservice.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import portfolioservice.service.PortfolioService;

public class PortfolioController implements HttpHandler {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @Override
    public void handle(HttpExchange exchange) {

    }
}
