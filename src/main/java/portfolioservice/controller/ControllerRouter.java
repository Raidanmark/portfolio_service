package portfolioservice.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import portfolioservice.dto.Response;
import portfolioservice.mapper.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        try {
            Response response = handler.handleRequest(exchange);
            sendJsonResponse(exchange, response);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1);
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, Response response) throws IOException {
        byte[] responseBytes = JsonMapper.toJsonBytes(response.body());
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(response.statusCode(), responseBytes.length);
        try (var os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
