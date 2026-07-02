package portfolioservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Portfolio {
    private final UUID id;
    private final String name;
    private final Currency currency;
    private final LocalDateTime createdAt;

    public static Portfolio create(String name, Currency baseCurrency) {
        return new Portfolio(
                UUID.randomUUID(),
                name,
                baseCurrency,
                LocalDateTime.now()
        );
    }
}
