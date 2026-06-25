package portfolioservice;


import portfolioservice.server.http.HttpServer;
import portfolioservice.server.http.RouteRegistry;
import portfolioservice.server.http.RouteRegistryBuilder;
import portfolioservice.server.tcp.PortfolioTcpServer;

public class PortfolioServiceApp {
    public static void main(String[] args) throws Exception {

        int portHttp = 8080;
        int portTcp = 9000;

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
                HttpServer httpServer = new HttpServer(portHttp);
                httpServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tcpThread.start();
        httpThread.start();

    }
}