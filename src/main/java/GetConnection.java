import java.sql.*;

public class GetConnection {

    private final String HOST = "jdbc:postgresql://localhost:5433/postgres";
    private final String USENAME = "user";
    private final String PASSWORD = "password";

    private Connection connection;

    public GetConnection() {
        try {
            connection = DriverManager.getConnection(HOST, USENAME, PASSWORD);
            // System.out.println("Connect is workig");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    PreparedStatement preparedStatement = null;

    String query = "INSERT INTO offerdb (name, brand, color, price, initialPrice, description, articleId, shippingCosts)"
            + " VALUES (?,?,?,?,?,?,?,?)";

    public void saveToBd() throws SQLException {

        preparedStatement = connection.prepareStatement(query);
        Db db = new Db();
        preparedStatement.setString(1, db.getName());
        preparedStatement.setString(2, db.getBrand());
        preparedStatement.setString(3, db.getColor());
        preparedStatement.setString(4, db.getPrice());
        preparedStatement.setString(5, db.getInitialPrice());
        preparedStatement.setString(6, db.getDescription());
        preparedStatement.setString(7, db.getArticleId());
        preparedStatement.setString(8, db.getShippingCosts());

        preparedStatement.executeUpdate();
    }

}

