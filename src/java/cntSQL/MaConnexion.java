package cntSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MaConnexion {

    private Connection cnt;

    private MaConnexion() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String user = "sa";
        String mdp = "sa";
        String url = "jdbc:sqlserver://localhost:1433; "
                + "databaseName=Librairie";
        cnt = DriverManager.getConnection(url, user, mdp);
    }

    public static synchronized MaConnexion getInstance() throws ClassNotFoundException, SQLException {
        return new MaConnexion();
    }

    public Statement createStatement() throws SQLException {
        return cnt.createStatement();
    }

    public PreparedStatement createStatement(String req) throws SQLException {
        return cnt.prepareStatement(req);
    }

    public CallableStatement createCallable(String req) throws SQLException {
        return cnt.prepareCall(req);
    }

    public void close() throws SQLException {
        if (cnt != null) {
            cnt.close();
        }
    }

    @Override
    public void finalize() {
        if (cnt != null) {
            try {
                cnt.close();
            } catch (SQLException ex) {

            }

        }

    }
}
