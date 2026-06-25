package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;

public enum PortfolioHttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH;

    public static PortfolioHttpMethod fromNetty(HttpMethod method) {
        return PortfolioHttpMethod.valueOf(method.name());
    }
}
