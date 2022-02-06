import java.sql.*;
import java.util.*;

public class DataBase {

    private Connection connection = null;
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/SimbirSoft";
    static final String USER = "postgres";
    static final String PASS = "root";

    protected void connectDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            // System.out.println("Connected to data base");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insert(Map<String, Integer> map) {
        Set<String> wordsSet = map.keySet();
        List<Integer> wordsCount = new ArrayList<>();

        for (String s : wordsSet) {
            wordsCount.add(map.get(s));
        }
        int index = 0;
        String query;
        Statement statement;
        try {
            statement = connection.createStatement();

            String queryDelete = "DELETE FROM word";
            statement.executeUpdate(queryDelete);
            for (String word :
                    wordsSet) {
                query =
                        "INSERT INTO word (word, count)" +
                                "VALUES ('" + word + "', " + wordsCount.get(index++) + ");";
                statement.executeUpdate(query);
            }
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    protected void select() {
        try {
            Statement statement = connection.createStatement();
            String querySelect = "SELECT word, count FROM word";
            ResultSet resultSet = statement.executeQuery(querySelect);
            while (resultSet.next()) {
                System.out.println(String.format("%-25s-%4d",
                        resultSet.getString("word"),
                        resultSet.getInt("count")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}