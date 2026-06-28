package portfolioservice;

import lombok.extern.slf4j.Slf4j;
import portfolioservice.config.AppConfig;

@Slf4j
public class PortfolioServiceApp {

    public static void main(String[] args) {

        AppConfig config = new AppConfig();
        log.info("Starting Portfolio Service");
        config.start();
    }
}