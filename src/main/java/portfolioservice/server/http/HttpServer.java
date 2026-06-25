package portfolioservice.server.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServer {
    private final int port;
    private final RouteRegistry routeRegistry;

    public HttpServer(int port) {
        this.port = port;
        RouteRegistryBuilder routeRegistryBuilder = new RouteRegistryBuilder();
        this.routeRegistry = routeRegistryBuilder.build();
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
                        protected void initChannel(SocketChannel channel) {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                            pipeline.addLast(new HttpRequestHandler(routeRegistry));
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();

            System.out.println("HTTP server started on port " + port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
