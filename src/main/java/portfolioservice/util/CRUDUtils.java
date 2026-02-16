package portfolioservice.util;

import portfolioservice.model.Portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {

    public static List<Portfolio> createPortfolio(Long ownerId) {
        List<Portfolio> portfolios = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
