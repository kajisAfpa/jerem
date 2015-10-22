/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import classes.Commande;
import classes.LigneCommande;
import classes.Membre;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.ReqCommentaire;

/**
 *
 * @author kevin
 */
public class beanCommentaire {
    
    private int idCommentaire;
    private Membre membre;
    private beanLivre Livre;
    private String contenuCommenter;
    private int noteCommenter;
    private String dateCommenter;
    private String titreCommenter;

    public beanCommentaire() {
        this.Livre = new beanLivre();
        this.membre = new Membre();
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public beanLivre getLivre() {
        return Livre;
    }

    public void setLivre(beanLivre Livre) {
        this.Livre = Livre;
    }

 

    public String getContenuCommenter() {
        return contenuCommenter;
    }

    public void setContenuCommenter(String contenuCommenter) {
        this.contenuCommenter = contenuCommenter;
    }

    public int getNoteCommenter() {
        return noteCommenter;
    }

    public void setNoteCommenter(int noteCommenter) {
        this.noteCommenter = noteCommenter;
    }

    public String getDateCommenter() {
        return dateCommenter;
    }

    public void setDateCommenter(String dateCommenter) {
        this.dateCommenter = dateCommenter;
    }

    public String getTitreCommenter() {
        return titreCommenter;
    }

    public void setTitreCommenter(String titreCommenter) {
        this.titreCommenter = titreCommenter;
    }

    @Override
    public String toString() {
        return "beanCommentaire{" + "idCommentaire=" + idCommentaire + ", membre=" + membre + ", Livre=" + Livre + ", contenuCommenter=" + contenuCommenter + ", noteCommenter=" + noteCommenter + ", dateCommenter=" + dateCommenter + ", titreCommenter=" + titreCommenter + '}';
    }

    
    
    
    public Boolean checkCommentaire(int id, String idLivre) {
        
        Boolean check = false;
        
        ReqCommentaire req = new ReqCommentaire();
        try {
            for(Commande c : req.getCommentairValidator(id).getCommandes()) {
                for(LigneCommande l : c.getLigneCommande()) {
                    if(l.getLivreLigneCommande().getIdLivre().toString().equals(idLivre)) {
                        
                        check = true;
                    
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return check;
    }
    
    public Boolean checkAlreadyComment(int id, String idLivre) {
        
        Boolean check = false;
        
        ReqCommentaire req = new ReqCommentaire();
        try {
            for(beanCommentaire c : req.getListCommentaire(idLivre)) {
                if(c.membre.getIdMembre()== id) {
                    check = true;
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return check;
        
    }
    
    
    public static void main(String[] args) {
        
        beanCommentaire commentaire = new beanCommentaire();
        ReqCommentaire req = new ReqCommentaire();
        
//        
        if(commentaire.checkCommentaire(2, "9782070456963")) {
            System.out.println("vous avez bien acheté ce livre");
        } else {
            System.out.println("vous n'avez jamais acheté ce livre");
        }
        
        
        if(commentaire.checkAlreadyComment(2, "9782070456963")) {
            System.out.println("vous avez déjà commenté ce livre");
        } else {
            System.out.println("vous n'avez jamais commenté ce livre");
        }
        
        
        System.out.println("\t ------------ debut to String ---------------");
       
        try {
            System.out.println(req.getCommentairValidator(4).toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReqCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReqCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    
}
