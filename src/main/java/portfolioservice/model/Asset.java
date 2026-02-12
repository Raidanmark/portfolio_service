package portfolioservice.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Asset {
    private Long id;
    private Enum assetType;
    private BigDecimal price;
    private Currency currency;
    private String identifier;
}
