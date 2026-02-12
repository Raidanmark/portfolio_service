package portfolioservice.model;

import java.math.BigDecimal;
import java.util.List;

public class Portfolio {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private List<Transaction> transactions;
    private BigDecimal cashBalance;
}
