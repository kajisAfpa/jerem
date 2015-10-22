package beans;

import cntSQL.MaConnexion;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class beanLogin implements Serializable {

    public beanLogin() {
    }

    public int check(String user, String mdp) throws ClassNotFoundException, SQLException {
        int id = -1;
        if (user == null) {
            return -2;
        }
        if (mdp == null) {
            return -3;
        }
        if (user.trim().isEmpty()) {
            return -2;
        }
        if (mdp.trim().isEmpty()) {
            return -3;
        }
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "SELECT * FROM Membre "
                + "WHERE mailMembre = '" + user + "' "
                + "AND mdpMembre = '" + mdp + "' "
                + "AND actifMembre = 1 ";
        PreparedStatement stm = cnt.createStatement(req);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("idMembre");
        }
        rs.close();
        stm.close();
        cnt.close();
        return id;
    }

}
