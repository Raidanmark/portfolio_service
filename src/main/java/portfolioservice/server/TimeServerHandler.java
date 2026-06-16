package portfolioservice.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private static final long millis = 1000L;
    private static final long maxLongAmount = 2208988800L;
    private static final int byteBufAmount = 4;

    @Override
    public void channelActive (final ChannelHandlerContext context) {
        final ByteBuf time = context.alloc().buffer(byteBufAmount);
        time.writeInt((int) (System.currentTimeMillis() / millis + maxLongAmount));

        final ChannelFuture channelFuture = context.writeAndFlush(time);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert channelFuture == future;
                context.close();
            }
        });
    }
}
