import java.net.URI;
import java.sql.*;
import java.util.UUID;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.core.SqlCommand;

public class ConnectionClass {

    private ConnectionClass() throws Exception { getConnection(); }
    private static ConnectionClass instance;
    private Connection connection;

    public static ConnectionClass getInstance() throws Exception {
        if(instance == null) instance = new ConnectionClass();

        return instance;
    }

    private void getConnection() throws Exception {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DATABASE_URL");

        URI uri = new URI(url);

        String username = uri.getUserInfo().split(":")[0];
        String password = uri.getUserInfo().split(":")[1];

        String dbUrl = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

        try{
            System.out.println("✅ Connection established!");
            this.connection = DriverManager.getConnection(dbUrl, username, password);
        }
        catch (SQLException e) {
            throw new RuntimeException("❌ Error while getting connection: " + e);
        }

    }

    public boolean createTableEmployee() throws Exception {
        try(Statement stmt = this.connection.createStatement()){
            ResultSet query = stmt.executeQuery("SELECT 1 FROM pg_tables WHERE tablename = 'employee'");
            if(query.next()) return false;

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS employee" +
                            "(id UUID PRIMARY KEY DEFAULT gen_random_uuid(), " +
                            "name VARCHAR(100) NOT NULL , " +
                            "salary DECIMAL(10, 2) NOT NULL, " +
                            "role VARCHAR(30) NOT NULL)"
            );
            System.out.println("✅ Table 'employee' created!");
            System.out.println("=================");
            System.out.println("🏗️ Structure:");
            System.out.println("id | name | salary | role");
            return true;
        }catch(Exception e){
            System.out.println("❌ Error while creating table: " + e);
            return false;
        }
    }

    public void insertEmployee(String name, Double salary, String role){
        String query = "INSERT INTO employee (name, salary, role) VALUES (?, ?, ?)";

        try(PreparedStatement pstmt = this.connection.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setDouble(2, salary);
            pstmt.setString(3, role);

            pstmt.executeUpdate();
            System.out.println("👨‍💼 New employee created!");
        }
        catch(Exception e){
            System.out.println("❌ Error while inserting data: " + e);
        }
    }

    public Employee getEmployee(String name, String role) throws Exception {
        String query = "SELECT * FROM employee WHERE name = ? and role = ?";

        try(PreparedStatement pstmt = this.connection.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setString(2, role);

            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                System.out.println("👎 There are no users with these information!");
                return null;
            }

            Employee employee = new Employee(
                    rs.getObject("id", UUID.class),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    rs.getString("role")
            );

            System.out.println("🔎 Fetched employee: " + employee);

            return employee;
        }
        catch(Exception e){
            System.out.println("❌ Error while getting employee: " + e);
            return null;
        }
    }

    public boolean updateEmployee(Employee employee, String newName, double newSalary, String newRole){
        String query = "UPDATE employee SET name = ?, salary = ?, role = ? WHERE id = ?";

        try(PreparedStatement pstmt = this.connection.prepareStatement(query)){
            pstmt.setString(1, newName);
            pstmt.setDouble(2, newSalary);
            pstmt.setString(3, newRole);
            pstmt.setObject(4, employee.getId());

            int updatedLines = pstmt.executeUpdate();

            if(updatedLines <= 0){
                System.out.println("👎 No employee with this ID!");
                return false;
            }

            System.out.println("📝 Employee edited!");
            return true;
        }
        catch(Exception e){
            System.out.println("❌ Error while updating employee: " + e);
            return false;
        }
    }

    public boolean deleteEmployee(Employee employee) {
        String query = "DELETE FROM employee WHERE id = ?";

        try(PreparedStatement pstmt = this.connection.prepareStatement(query)){
            pstmt.setObject(1, employee.getId());

            int updatedLines = pstmt.executeUpdate();

            if(updatedLines <= 0){
                System.out.println("👎 No employee with this ID!");
                return false;
            }

            System.out.println("🗑️ Employee deleted!");
            return true;
        }
        catch(Exception e){
            System.out.println("❌ Error while deleting employee: " + e);
            return false;
        }
    }

}