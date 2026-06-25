package portfolioservice.server.http;

public interface RouteHandler {
    AppHttpResponse handle(HttpRequestData request);
}
