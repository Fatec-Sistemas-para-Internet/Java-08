import org.postgresql.core.SqlCommand;

import java.sql.*;

public class Main {
    public static void main( String[] args ) throws Exception {
        ConnectionClass db = ConnectionClass.getInstance();
        db.createTableEmployee();

        /*
        db.insertEmployee("Suzana", 4100.00, "Psicóloga");
        db.insertEmployee("Thiago", 2.00, "Bostejador");
        db.insertEmployee("João", 500.00, "Escravo MBM"); */
        db.insertEmployee("Pedro", 7000.00, "Anki CEO");


        Employee suzana = db.getEmployee("Suzana", "Psicóloga");
        suzana.printEmployee();

        /*
        Employee joao = db.getEmployee("João", "Escravo MBM");
        db.updateEmployee(joao, "João", 4000.00, "Efetivado");
        */

        Employee pedro = db.getEmployee("Pedro","Anki CEO");
        db.deleteEmployee(pedro);

    }
}
