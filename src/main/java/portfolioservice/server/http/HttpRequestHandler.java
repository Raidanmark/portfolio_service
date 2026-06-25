package portfolioservice.server.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final HttpRouter router;
    private final RouteRegistry routeRegistry;

    public HttpRequestHandler(RouteRegistry routeRegistry) {
        this.routeRegistry = routeRegistry;
        router = new HttpRouter(routeRegistry);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) {
        String uri = request.uri();
        String body = request.content().toString(StandardCharsets.UTF_8);

        System.out.println("HTTP " + request.method().name() + " " + uri);

        AppHttpResponse appResponse = router.route(
                request.method(),
                uri,
                body
        );

        // TODO json should be in another class
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
}
