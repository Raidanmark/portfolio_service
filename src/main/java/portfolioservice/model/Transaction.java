package portfolioservice.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private Long portfolioId;
    private Enum transactionType;
    private LocalDateTime occuredAt;
    private BigInteger quantity;
    private Asset asset;
    private BigDecimal fees;

}
