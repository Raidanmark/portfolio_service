package portfolioservice.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import portfolioservice.dto.portfolio.PortfolioCreateDto;
import portfolioservice.dto.portfolio.PortfolioDto;
import portfolioservice.entity.Portfolio;
import portfolioservice.repository.PortfolioRepository;
import portfolioservice.server.http.HttpRequest;

import java.util.Currency;

@Slf4j
@AllArgsConstructor
public class PortfolioMapper {

    private final JsonMapper jsonMapper;

    public Portfolio toEntity(PortfolioCreateDto portfolioCreateDto) {
        log.info("Mapping PortfolioCreateDto to Portfolio entity started");
        Currency baseCurrency = toCurrency(portfolioCreateDto.baseCurrency());

        return Portfolio.create(portfolioCreateDto.name(), baseCurrency);
    }

    public PortfolioCreateDto toCreateDto(HttpRequest request) {
        log.info("Mapping HttpRequest to PortfolioCreateDto: {} started", request.getBody());
        return jsonMapper.read(
                request.getBody(),
                PortfolioCreateDto.class
        );
    }

//    public PortfolioDto toDto() {
//        // Implementation for mapping Portfolio entity to PortfolioDto
//    }

    private Currency toCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            return null;
        }

        return Currency.getInstance(
                currencyCode.trim().toUpperCase()
        );
    }
}
