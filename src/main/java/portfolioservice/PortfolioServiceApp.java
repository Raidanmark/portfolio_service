package portfolioservice;


import portfolioservice.server.http.PortfolioHttpServer;
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
                PortfolioHttpServer httpServer = new PortfolioHttpServer(portHttp);
                httpServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tcpThread.start();
        httpThread.start();

    }
}