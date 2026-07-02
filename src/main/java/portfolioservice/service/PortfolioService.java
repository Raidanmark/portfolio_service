package portfolioservice.service;

import lombok.AllArgsConstructor;
import portfolioservice.dto.portfolio.PortfolioCreateDto;
import portfolioservice.dto.portfolio.PortfolioDto;
import portfolioservice.mapper.PortfolioMapper;
import portfolioservice.repository.PortfolioRepository;

@AllArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioRequestMapper;

    public PortfolioDto createPortfolio(PortfolioCreateDto dto) {
       return portfolioRequestMapper.toPortfolioDto(portfolioRepository.save(portfolioRequestMapper.toEntity(dto)));
    }
}
