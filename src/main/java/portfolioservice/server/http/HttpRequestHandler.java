package portfolioservice.server.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final HttpRouter router;
    private final RouteRegistry routeRegistry;

    public HttpRequestHandler(RouteRegistry routeRegistry) {
        this.routeRegistry = routeRegistry;
        router = new HttpRouter(routeRegistry);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) {

        HttpRequest httpRequest = createHttpRequest(request);


        System.out.println("HTTP " + request.method().name() + " " + request.uri());

        HttpResponse appResponse = router.route(httpRequest);

        sendJson(
                context,
                appResponse.getStatus(),
                appResponse.getBody()
        );
    }

    // TODO json should be in another class
    private void sendJson(ChannelHandlerContext context, HttpResponseStatus status, String json) {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                status,
                Unpooled.wrappedBuffer(bytes)
        );

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);

        context.writeAndFlush(response);
    }

    private HttpRequest createHttpRequest(FullHttpRequest fullRequest) {
        QueryStringDecoder decoder = new QueryStringDecoder(fullRequest.uri());
        String path = decoder.path();

        return new HttpRequest(
                PortfolioHttpMethod.fromNetty(fullRequest.method()),
                path,
                fullRequest.content().toString(StandardCharsets.UTF_8),
                extractHeaders(fullRequest),
                extractQueryParams(fullRequest),
                Map.of()  // pathVariables
        );
    }
    // TODO move this to another class
    private Map<String, String> extractHeaders(FullHttpRequest request) {
        Map<String, String> headers = new HashMap<>();

        request.headers().forEach(entry ->
                headers.put(entry.getKey(), entry.getValue())
        );

        return headers;
    }
    private Map<String, String> extractQueryParams(FullHttpRequest request) {
        Map<String, String> queryParams = new HashMap<>();
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());

        decoder.parameters().forEach((key, values) -> {
            if (!values.isEmpty()) {
                queryParams.put(key, values.get(0));
            }
        });

        return queryParams;
    }
}
