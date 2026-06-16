package portfolioservice.server.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

public class PortfolioTcpServer {

    private int port;

    public PortfolioTcpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        MultiThreadIoEventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        MultiThreadIoEventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline= channel.pipeline();

                            channel.pipeline().addLast((new LineBasedFrameDecoder(4096)));
                            pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                            pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                            channel.pipeline().addLast(new PortfolioTcpServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //Bind and start to accept incoming connections
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            System.out.println("Portfolio server started on port " + port);

            channelFuture.channel().closeFuture().sync();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

        }
    }
}
