package portfolioservice.server.http;

public interface RouteHandler {
    HttpResponse handle(HttpRequest request);
}
