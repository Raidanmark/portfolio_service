package portfolioservice.config;

import lombok.extern.slf4j.Slf4j;
import portfolioservice.controller.PortfolioController;
import portfolioservice.database.DatabaseConnectionProvider;
import portfolioservice.di.DiContainer;
import portfolioservice.mapper.JsonMapper;
import portfolioservice.mapper.PortfolioMapper;
import portfolioservice.repository.PortfolioRepository;
import portfolioservice.server.http.HttpServer;
import portfolioservice.server.http.route.RouteRegistry;
import portfolioservice.server.http.route.RouteRegistryBuilder;
import portfolioservice.server.tcp.PortfolioTcpServer;
import portfolioservice.service.PortfolioService;

@Slf4j
public class AppConfig {

//TODO: Make ports configurable via environment variables or configuration files
    private final static int portHttp = 8080;
    private final static int portTcp = 9000;

    public void start() {
        DiContainer container = new DiContainer();

        log.info("Bean registration started");
        registerBeans(container);

        log.info("Route registry creation started");
        RouteRegistry routeRegistry = createRouteRegistry(container);
        log.info("Route registry created");

        log.info("Starting servers");
        startServers(routeRegistry);
    }

    private void registerBeans(DiContainer container) {
        container.register(PortfolioService.class);
        container.register(PortfolioController.class);
        container.register(PortfolioRepository.class);
        container.register(DatabaseConnectionProvider.class);
        container.register(PortfolioMapper.class);
        container.register(JsonMapper.class);
        log.info("Beans registered");
    }

    private RouteRegistry createRouteRegistry(DiContainer container) {
        PortfolioController controller = container.get(PortfolioController.class);
        return new RouteRegistryBuilder()
                .registerController(controller)
                .build();
    }

   private void startServers(RouteRegistry routeRegistry) {

        Thread tcpThread = new Thread(() -> {
           try {
               PortfolioTcpServer tcpServer = new PortfolioTcpServer(portTcp);
               tcpServer.run();
           } catch (Exception e) {
               e.printStackTrace();
           }
       });

       Thread httpThread = new Thread(() -> {
           try {
               HttpServer httpServer = new HttpServer(portHttp, routeRegistry);
               httpServer.run();
           } catch (Exception e) {
               e.printStackTrace();
           }
       });

       tcpThread.start();
       log.info("TCP server started on port " + portTcp);
       httpThread.start();
       log.info("HTTP server started on port " + portHttp);
   }
}
