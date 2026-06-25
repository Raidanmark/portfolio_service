package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpRouter {
    private final RouteRegistry routeRegistry;

    public HttpRouter(RouteRegistry routeRegistry) {
        this.routeRegistry = routeRegistry;
    }

    public AppHttpResponse route(HttpMethod method, String uri, String body) {
        String path = normalizePath(uri);

        RouteHandler staticHandler = routeRegistry.getStaticRoutes().get(new RouteKey(method, path));;

        if (staticHandler != null) {
            HttpRequestData request = new HttpRequestData(
                    method,
                    path,
                    body,
                    Map.of()
            );

            return staticHandler.handle(request);
        }

        for (Map.Entry<RouteKey, RouteHandler> entry : routeRegistry.getDynamicRoutes().entrySet()) {
            RouteKey routeKey = entry.getKey();

            if (!routeKey.getMethod().equals(method)) {
                continue;
            }

            Map<String, String> pathVariables = matchDynamicPath(routeKey.getPath(), path);

            if (pathVariables != null) {
                HttpRequestData request = new HttpRequestData(
                        method,
                        path,
                        body,
                        pathVariables
                );

                return entry.getValue().handle(request);
            }
        }
// TODO make exception for this
        // TODO too much static data
        return AppHttpResponse.error(
                HttpResponseStatus.NOT_FOUND,
                "{\"status\":\"ERROR\",\"code\":\"NOT_FOUND\",\"message\":\"Route not found\"}"
        );
    }
//TODO registration should be implemented in another way. Every controller should register path from its class and have to create mech for this

    private String normalizePath(String uri) {
        int queryIndex = uri.indexOf('?');

        if (queryIndex >= 0) {
            return uri.substring(0, queryIndex);
        }

        return uri;
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
