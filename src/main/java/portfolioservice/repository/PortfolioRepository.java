package portfolioservice.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import portfolioservice.database.DatabaseConnectionProvider;
import portfolioservice.entity.Portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Currency;

@Slf4j
@AllArgsConstructor
public class PortfolioRepository {
    private final DatabaseConnectionProvider databaseConnectionProvider;

    public Portfolio save(Portfolio portfolio) {
        String sql = "INSERT INTO portfolios (id, name, currency, created_at) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = databaseConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, portfolio.getId());
            statement.setString(2, portfolio.getName());

            Currency baseCurrency = portfolio.getCurrency();

            if (baseCurrency == null) {
                statement.setString(3, null);
            } else {
                statement.setString(3, portfolio.getCurrency().getCurrencyCode());
            }

            statement.setTimestamp(4, Timestamp.valueOf(portfolio.getCreatedAt()));
            int affectedRowns = statement.executeUpdate();
            log.info("Portfolio saved: id = {}, affected rows = {}", portfolio.getId(), affectedRowns);

        return portfolio;
        } catch (SQLException exception) {
            log.error("Failed to save portfolio: id = {}, error = {}", portfolio.getId(), exception.getMessage());
            throw new RuntimeException("Failed to save portfolio", exception);
        }
    }
}
