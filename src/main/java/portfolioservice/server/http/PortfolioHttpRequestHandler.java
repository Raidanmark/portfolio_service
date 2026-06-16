package portfolioservice.server.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class PortfolioHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) {
        String method = request.method().name();
        String uri = request.uri();
        String body = request.content().toString(StandardCharsets.UTF_8);

        System.out.println("HTTP " + method + " " + uri);
        System.out.println("Body: " + body);

        if (request.method().equals(HttpMethod.GET) && uri.equals("/ping")) {
            sendJson(context, HttpResponseStatus.OK, "{\"status\":\"OK\",\"type\":\"PONG\"}");
            return;
        }

        sendJson(context, HttpResponseStatus.NOT_FOUND, "{\"status\":\"ERROR\",\"message\":\"Not found\"}");
    }

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
