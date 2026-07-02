package portfolioservice.dto.portfolio;

import java.time.LocalDateTime;
import java.util.UUID;

public record PortfolioDto (
        UUID id,
        String name,
        String baseCurrency,
        LocalDateTime createdAt

){
}
