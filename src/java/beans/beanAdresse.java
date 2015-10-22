package beans;

import cntSQL.MaConnexion;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class beanAdresse implements Serializable {

// 1°/ ATTRIBUT-----------------------------------------------------------------
    private int idAdresse;
    private String nomAdresse;
    private String prenomAdresse;
    private String typeAdresse;
    private String libelleAdresse;
    private String rueAdresse;
    private String cpAdresse;
    private String villeAdresse;
    private String paysAdresse;

// 2°/ CONSTRUCTOR-------------------------------------------------------------- 
    public beanAdresse() {
    }

// 3°/ METHODE------------------------------------------------------------------
    //METHODE POUR CREER UNE ADRESSE DANS LA BDD
    public void creationAdresse() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "{call ProcCreationAdresse(?,?,?,?,?,?,?,?,?)}";
        CallableStatement cs = cnt.createCallable(req);
        cs.setString(1, this.getNomAdresse());
        cs.setString(2, this.getPrenomAdresse());
        cs.setString(3, this.getTypeAdresse());
        cs.setString(4, this.getLibelleAdresse());
        cs.setString(5, this.getRueAdresse());
        cs.setString(6, this.getCpAdresse());
        cs.setString(7, this.getVilleAdresse());
        cs.setString(8, this.getPaysAdresse());
        cs.registerOutParameter(9, java.sql.Types.INTEGER);
        cs.execute();
        this.setIdAdresse(cs.getInt(9));
        cs.close();
        cnt.close();

    }

    //METHODE QUI MODIFIE LES DONNEES DE L'OBJET ADRESSE
    public void updateAdresse(String nomAdresse, String prenomAdresse, String typeAdresse, String libelleAdresse, String rueAdresse, String cpAdresse, String villeAdresse, String paysAdresse) {
        this.nomAdresse = nomAdresse;
        this.prenomAdresse = prenomAdresse;
        this.typeAdresse = typeAdresse;
        this.libelleAdresse = libelleAdresse;
        this.rueAdresse = rueAdresse;
        this.cpAdresse = cpAdresse;
        this.villeAdresse = villeAdresse;
        this.paysAdresse = paysAdresse;
    }

    //METHODE QUI MODIFIE LES DONNEES DE L'ADRESSE DANS LA BDD
    public void modifierAdresse() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "UPDATE Adresse "
                + "SET nomAdresse = '" + this.getNomAdresse() + "'"
                + ", prenomAdresse =  '" + this.getPrenomAdresse() + "'"
                + ", typeAdresse =  '" + this.getTypeAdresse() + "'"
                + ", libelleAdresse = '" + this.getLibelleAdresse() + "'"
                + ", rueAdresse =  '" + this.getRueAdresse() + "'"
                + ", cpAdresse =  '" + this.getCpAdresse() + "'"
                + ", villeAdresse =  '" + this.getVilleAdresse() + "'"
                + ", paysAdresse =  '" + this.getPaysAdresse() + "'"
                + " WHERE idAdresse = " + this.getIdAdresse();
        PreparedStatement stm = cnt.createStatement(req);
        stm.executeUpdate();
        stm.close();
        cnt.close();
    }

    //METHODE QUI "SUPPRIME" L'ADRESSE DE LA BDD
    public void supprimerAdresse() throws ClassNotFoundException, SQLException {
        MaConnexion cnt = MaConnexion.getInstance();
        String req = "UPDATE Adresse "
                + "SET actifAdresse = 0 "
                + "WHERE idAdresse = " + this.getIdAdresse();
        PreparedStatement stm = cnt.createStatement(req);
        stm.executeUpdate();
        stm.close();
        cnt.close();
    }

//4°/ GETTER AND SETTER---------------------------------------------------------    
    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getNomAdresse() {
        return nomAdresse;
    }

    public void setNomAdresse(String nomAdresse) {
        this.nomAdresse = nomAdresse;
    }

    public String getPrenomAdresse() {
        return prenomAdresse;
    }

    public void setPrenomAdresse(String prenomAdresse) {
        this.prenomAdresse = prenomAdresse;
    }

    public String getTypeAdresse() {
        return typeAdresse;
    }

    public void setTypeAdresse(String typeAdresse) {
        this.typeAdresse = typeAdresse;
    }

    public String getLibelleAdresse() {
        return libelleAdresse;
    }

    public void setLibelleAdresse(String libelleAdresse) {
        this.libelleAdresse = libelleAdresse;
    }

    public String getRueAdresse() {
        return rueAdresse;
    }

    public void setRueAdresse(String rueAdresse) {
        this.rueAdresse = rueAdresse;
    }

    public String getCpAdresse() {
        return cpAdresse;
    }

    public void setCpAdresse(String cpAdresse) {
        this.cpAdresse = cpAdresse;
    }

    public String getVilleAdresse() {
        return villeAdresse;
    }

    public void setVilleAdresse(String villeAdresse) {
        this.villeAdresse = villeAdresse;
    }

    public String getPaysAdresse() {
        return paysAdresse;
    }

    public void setPaysAdresse(String paysAdresse) {
        this.paysAdresse = paysAdresse;
    }

    public String Type() {
        if (this.typeAdresse.equalsIgnoreCase("Livraison")) {
            return "liv";
        }
        return "fac";
    }

}
