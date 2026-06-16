package portfolioservice.server.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import portfolioservice.protocol.Command;
import portfolioservice.protocol.CommandDispatcher;
import portfolioservice.protocol.CommandParser;
import portfolioservice.protocol.ResponseWriter;

import java.net.ProtocolException;

public class PortfolioTcpServerHandler extends ChannelInboundHandlerAdapter {

    private final CommandParser parser = new CommandParser();
    private final CommandDispatcher dispatcher = new CommandDispatcher();
    private final ResponseWriter responseWriter = new ResponseWriter();

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) throws ProtocolException {
        try {
            Command command = parser.parse((String) message);
            String result = dispatcher.dispatch(command);
            String response = responseWriter.ok(result);

            context.writeAndFlush(response);
        } catch (ProtocolException e) {
            String response = responseWriter.error(
                    "INVALID_COMMAND",
                    e.getMessage()
            );
            context.writeAndFlush(response);

        } catch (Exception e) {
            String response = responseWriter.error(
                    "INTERNAL_ERROR",
                    "Unexpected server error"
            );

            context.writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();

        context.writeAndFlush(
                responseWriter.error("INTERNAL_ERROR", "Unexpected server error")
        ).addListener(future -> context.close());
    }
}
