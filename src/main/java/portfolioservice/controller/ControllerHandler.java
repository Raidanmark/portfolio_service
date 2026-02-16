package portfolioservice.controller;

import com.sun.net.httpserver.HttpExchange;
import portfolioservice.dto.Response;

@FunctionalInterface
public interface ControllerHandler {
    Response<?> handleRequest(HttpExchange request) throws Exception;
}
