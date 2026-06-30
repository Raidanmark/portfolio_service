package portfolioservice.service;

import lombok.AllArgsConstructor;
import portfolioservice.dto.portfolio.PortfolioCreateDto;
import portfolioservice.mapper.PortfolioMapper;
import portfolioservice.repository.PortfolioRepository;

@AllArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioRequestMapper;

    public void createPortfolio(PortfolioCreateDto dto) {
        portfolioRepository.save(portfolioRequestMapper.toEntity(dto));
    }
}
