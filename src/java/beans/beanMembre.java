package beans;

import cntSQL.MaConnexion;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class beanMembre implements Serializable {

// 1°/ ATTRIBUT-----------------------------------------------------------------  
    private int idMembre;
    private String mailMembre;
    private String mdpMembre;
    private String nomMembre;
    private String prenomMembre;
    private String dateMembre;
    private int typeMembre;
    private String telMembre;
    private String portMembre;
    private ArrayList<beanAdresse> adresseFac = new ArrayList();
    private ArrayList<beanAdresse> adresseLiv = new ArrayList();

// 2°/ CONSTRUCTOR-------------------------------------------------------------- 
    public beanMembre() {
    }

    public beanMembre(String mdpMembre, String nomMembre, String mailMembre) {
        this.mdpMembre = mdpMembre;
        this.nomMembre = nomMembre;
        this.mailMembre = mailMembre;
    }

// 3°/ METHODE------------------------------------------------------------------
    //METHODE POUR INSCRIRE UN MEMBRE DANS LA BDD
    public void creationMembre() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "{call ProcCreationMembre(?,?,?,?,?,?,?,?,?)}";
        CallableStatement cs = cnt.createCallable(req);
        cs.setString(1, this.nomMembre);
        cs.setString(2, this.prenomMembre);
        cs.setString(3, this.dateMembre);
        cs.setInt(4, this.typeMembre);
        cs.setString(5, this.mailMembre);
        cs.setString(6, this.mdpMembre);
        cs.setString(7, this.telMembre);
        cs.setString(8, this.portMembre);
        cs.registerOutParameter(9, java.sql.Types.INTEGER);
        cs.execute();
        this.idMembre = cs.getInt(9);
        cs.close();
        cnt.close();
    }

    //METHODE D'IMPORTATION DES DONNEES DU MEMBRE DE LA BDD
    public beanMembre importMembre(int idMembre) throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "SELECT m.idMembre, "
                + "nomMembre, "
                + "prenomMembre, "
                + "mdpMembre, "
                + "typeMembre, "
                + "dateNaissanceMembre, "
                + "mailMembre, "
                + "telMembre, "
                + "portMembre, "
                + "actifMembre, "
                + "a.idAdresse, "
                + "nomAdresse, "
                + "prenomAdresse, "
                + "typeAdresse, "
                + "libelleAdresse, "
                + "rueAdresse, "
                + "cpAdresse, "
                + "villeAdresse, "
                + "paysAdresse, "
                + "actifAdresse "
                + "FROM Membre as m "
                + "LEFT JOIN Situation as s "
                + "ON m.idMembre = s.idMembre "
                + "LEFT JOIN Adresse as a "
                + "ON a.idAdresse = s.idAdresse "
                + "WHERE m.idMembre = " + idMembre + " ";
        PreparedStatement stm = cnt.createStatement(req);
        ResultSet rs = stm.executeQuery();
        int init = 0;
        while (rs.next()) {
            beanAdresse a = new beanAdresse();
            if (init == 0) {
                this.idMembre = idMembre;
                this.nomMembre = rs.getString("nomMembre");
                this.prenomMembre = rs.getString("prenomMembre");
                this.typeMembre = rs.getInt("typeMembre");
                this.dateMembre = rs.getString("dateNaissanceMembre");
                this.mailMembre = rs.getString("mailMembre");
                this.telMembre = rs.getString("telMembre");
                this.portMembre = rs.getString("portMembre");
                this.mdpMembre = rs.getString("mdpMembre");
            }
            if (rs.getInt("actifAdresse") != 0) {
                a.setIdAdresse(rs.getInt("idAdresse"));
                a.setNomAdresse(rs.getString("nomAdresse"));
                a.setPrenomAdresse(rs.getString("prenomAdresse"));
                a.setTypeAdresse(rs.getString("typeAdresse"));
                a.setLibelleAdresse(rs.getString("libelleAdresse"));
                a.setRueAdresse(rs.getString("rueAdresse"));
                a.setCpAdresse(rs.getString("cpAdresse"));
                a.setVilleAdresse(rs.getString("villeAdresse"));
                a.setPaysAdresse(rs.getString("paysAdresse"));
                this.addAdresse(a);
            }
            init++;
        }
        rs.close();
        stm.close();
        cnt.close();
        return this;
    }

    //METHODE QUI MODIFIE LES DONNEES DE L'OBJET MEMBRE
    public void updateMembre(String mdp, String nom, String prenom, String type, String date, String mail, String tel, String port) {
        this.mdpMembre = mdp;
        this.nomMembre = nom;
        this.prenomMembre = prenom;
        this.typeMembre = Type(type);
        this.dateMembre = date;
        this.mailMembre = mail;
        this.telMembre = tel;
        this.portMembre = port;
    }

    //METHODE QUI MODIFIE LES DONNEES DU MEMBRE DANS LA BDD
    public void modifierMembre() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "UPDATE Membre "
                + "SET nomMembre = '" + this.nomMembre + "'"
                + ", prenomMembre =  '" + this.prenomMembre + "'"
                + ", typeMembre = " + this.typeMembre + " "
                + ", dateNaissanceMembre = '" + this.dateMembre + "'"
                + ", mailMembre =  '" + this.mailMembre + "'"
                + ", telMembre =  '" + this.telMembre + "'"
                + ", portMembre =  '" + this.portMembre + "'"
                + ", mdpMembre =  '" + this.mdpMembre + "'"
                + " WHERE idMembre = " + this.idMembre + " ";
        PreparedStatement stm = cnt.createStatement(req);
        stm.executeUpdate();
        stm.close();
        cnt.close();
    }

    //METHODE QUI "SUPPRIME" LE MEMBRE DE LA BDD
    public void supprimerMembre() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "UPDATE Membre "
                + "SET actifMembre = 0 "
                + "WHERE idMembre = " + this.idMembre;
        PreparedStatement stm = cnt.createStatement(req);
        stm.executeUpdate();
        stm.close();
        cnt.close();
    }

    //METHODE QUI TRANSFORME LE TYPE INT EN STRING
    public String Type() {
        if (this.typeMembre == 1) {
            return "par";
        }
        return "pro";
    }

    //METHODE QUI TRANSFORME LE TYPE STRING EN INT
    public int Type(String type) {
        if (type.equalsIgnoreCase("Particulier")) {
            return 1;
        }
        return 2;
    }

    //METHODE POUR LES ADDRESSES------------------------------------------------
    //METHODE POUR LIER UN MEMBRE AVEC UNE ADRESSE DANS LA BDD
    public void insertAdresse(beanAdresse a) throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "INSERT INTO Situation(idMembre,idAdresse) "
                + "VALUES(" + this.idMembre + " , " + a.getIdAdresse() + ") ";
        PreparedStatement stm = cnt.createStatement(req);
        stm.executeUpdate();
        stm.close();
        cnt.close();
        this.addAdresse(a);
    }

    //METHODE POUR LIER UNE OBJET  ADRESSE A UN MEMBRE
    public void addAdresse(beanAdresse a) {
        if (a.Type().equalsIgnoreCase("fac")) {
            this.adresseFac.add(a);
        } else {
            this.adresseLiv.add(a);
        }
    }

    //METHODE POUR COUPER LA LIAISON ENTRE L'OBJET MEMBRE ET ADRESSE
    public void supAdresse(beanAdresse a) {
        if (adresseFac.contains(a)) {
            adresseFac.remove(a);
        } else if (adresseLiv.contains(a)) {
            adresseLiv.remove(a);
        }
    }

    //METHODE POUR TROUVER L'OBJET ADRESSE PAR RAPPORT A SON ID
    public beanAdresse getAdresse(int idAdresse) {
        ArrayList<beanAdresse> b = new ArrayList();
        b.addAll(adresseFac);
        b.addAll(adresseLiv);
        for (beanAdresse a : b) {
            if (idAdresse == a.getIdAdresse()) {
                return a;
            }
        }
        return null;
    }

    //METHODE POUR LES COMMANDES------------------------------------------------
//4°/ GETTER AND SETTER--------------------------------------------------------- 
    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getTypeMembre() {
        return typeMembre;
    }

    public void setTypeMembre(int typeMembre) {
        this.typeMembre = typeMembre;
    }

    public String getMdpMembre() {
        return mdpMembre;
    }

    public void setMdpMembre(String mdpMembre) {
        this.mdpMembre = mdpMembre;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public String getDateMembre() {
        return dateMembre;
    }

    public void setDateMembre(String dateMembre) {
        this.dateMembre = dateMembre;
    }

    public String getMailMembre() {
        return mailMembre;
    }

    public void setMailMembre(String mailMembre) {
        this.mailMembre = mailMembre;
    }

    public String getTelMembre() {
        return telMembre;
    }

    public void setTelMembre(String telMembre) {
        this.telMembre = telMembre;
    }

    public String getPortMembre() {
        return portMembre;
    }

    public void setPortMembre(String portMembre) {
        this.portMembre = portMembre;
    }

    public ArrayList getAdresseLiv() {
        return adresseLiv;
    }

    public void setAdresseFac(ArrayList adresseFac) {
        this.adresseFac = adresseFac;
    }

    public ArrayList getAdresseFac() {
        return adresseFac;
    }

    public void setAdresseLiv(ArrayList adresseLiv) {
        this.adresseLiv = adresseLiv;
    }

}
