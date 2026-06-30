package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import portfolioservice.server.http.route.PortfolioHttpMethod;
import portfolioservice.server.http.route.RouteKey;
import portfolioservice.server.http.route.RouteRegistry;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class HttpRouter {
    private final RouteRegistry routeRegistry;


    public HttpResponse route(HttpRequest request) {

        log.info("Request routing started for {} {}", request.getMethod(), request.getPath());
        RouteHandler staticHandler = routeRegistry.getStaticRoutes().
                get(new RouteKey(request.getMethod(), request.getPath()));

        log.info("Checking if static route exists for {} {}", request.getMethod(), request.getPath());
        if (isStaticRoute(request.getMethod(), request.getPath())) {
            log.info("Static route found for {} {}", request.getMethod(), request.getPath());
            return staticHandler.handle(request);
        }

        log.info("No static route found for {} {}, checking dynamic routes", request.getMethod(), request.getPath());
        for (Map.Entry<RouteKey, RouteHandler> entry : routeRegistry.getDynamicRoutes().entrySet()) {
            RouteKey routeKey = entry.getKey();

            if (!routeKey.getMethod().equals(request.getMethod())) {
                continue;
            }

            Map<String, String> pathVariables = matchDynamicPath(routeKey.getPath(), request.getPath());
            request.setPathVariables(pathVariables);

            if (pathVariables == null) {
                continue;
            }

            log.info("Dynamic route found for {} {}", request.getMethod(), request.getPath());
            return entry.getValue().handle(request);
        }

        log.info("No route found for {} {}", request.getMethod(), request.getPath());
        // TODO make exception for this
        return HttpResponse.error(
                HttpResponseStatus.NOT_FOUND,
                "{\"status\":\"ERROR\",\"code\":\"NOT_FOUND\",\"message\":\"Route not found\"}"
        );
    }

    private boolean isStaticRoute(PortfolioHttpMethod method, String path) {
        return routeRegistry.getStaticRoutes().containsKey(new RouteKey(method, path));
    }

//TODO maybe I can find some lib for this
    private Map<String, String> matchDynamicPath(String routePattern, String actualPath) {
        String[] routeParts = routePattern.split("/");
        String[] actualParts = actualPath.split("/");

        if (routeParts.length != actualParts.length) {
            return null;
        }

        Map<String, String> variables = new HashMap<>();

        for (int i = 0; i < routeParts.length; i++) {
            String routePart = routeParts[i];
            String actualPart = actualParts[i];

            if (routePart.startsWith("{") && routePart.endsWith("}")) {
                String variableName = routePart.substring(1, routePart.length() - 1);

                if (actualPart.isBlank()) {
                    return null;
                }

                variables.put(variableName, actualPart);
                continue;
            }

            if (!routePart.equals(actualPart)) {
                return null;
            }
        }

        return variables;
    }
}
