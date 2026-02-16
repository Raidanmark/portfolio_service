package portfolioservice.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerRouter implements HttpHandler{
    private final Map<String, ControllerHandler> routes = new HashMap<>();

    public ControllerRouter(PortfolioController portfolioController) {
        Map<String, ControllerHandler> routes = new HashMap<>();

        //register routes
        routes.put("POST /portfolios", portfolioController::createPortfolio);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String key = String.format("%s %s",
                exchange.getRequestMethod(),
                exchange.getRequestURI().getPath()
        );

        ControllerHandler handler = routes.get(key);

        if (handler == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

    }
}
