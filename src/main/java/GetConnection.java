import java.sql.*;

public class GetConnection {

    public static void main(String[] args) throws SQLException {
        GetConnection getConnection = new GetConnection();
        getConnection.save();
    }

    private final String HOST = "jdbc:postgresql://localhost:5433/postgres";
    private final String USENAME = "user";
    private final String PASSWORD = "password";

    private Connection connection;

    Offer offer = new Offer();

    public GetConnection() {
        try {
            connection = DriverManager.getConnection(HOST, USENAME, PASSWORD);
            System.out.println("Connect is workig");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    PreparedStatement preparedStatement = null;


    // auto increment !! to not include the id (or the exact name) column:
    String query = "INSERT INTO offer (name, brand, color, price, initialPrice, description, articleId, shippingCosts)"
            + " VALUES (?,?,?,?,?,?,?,?)";


    public void save() throws SQLException {

        preparedStatement = getConnection().prepareStatement(query);

        preparedStatement.setString(1, offer.getName());
        preparedStatement.setString(2, offer.getBrand());
        preparedStatement.setString(3, offer.getColor());
        preparedStatement.setString(4, offer.getPrice());
        preparedStatement.setString(5, offer.getInitialPrice());
        preparedStatement.setString(6, offer.getDescription());
        preparedStatement.setString(7, offer.getArticleId());
        preparedStatement.setString(8, offer.getShippingCosts());
        preparedStatement.execute();
    }
}
