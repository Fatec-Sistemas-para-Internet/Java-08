import java.net.URI;
import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionClass {

    public static Connection getConnection() throws Exception {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DATABASE_URL");

        URI uri = new URI(url);

        String username = uri.getUserInfo().split(":")[0];
        String password = uri.getUserInfo().split(":")[1];

        String dbUrl = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

        try(){

            System.out.println("✅ Connection established!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void createTable() throws Exception {
        Connection db = ConnectionClass.getConnection();

        try(db; Statement stmt = db.createStatement();){
            stmt.executeUpdate(
                    "CREATE TABLE employee" +
                            "(id INTEGER PRIMARY KEY " +
                            "nome VARCHAR(100) NOT NULL , " +
                            "salary DECIMAL(10, 2) NOT NULL, " +
                            "role VARCHAR(30) NOT NULL)"
            );
            System.out.println("✅ Table created!");
        }catch(Exception e){
            System.out.println("❌ Error while creating table: " + e);
        }
    }

}