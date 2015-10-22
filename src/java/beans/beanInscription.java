package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class beanInscription implements Serializable {

    public beanInscription() {
    }

//Tableau de int qui renvoie une liste d'erreur
    public List<Integer> checkInt(String nom, String prenom, String mail, String tel, String port, String mdp, String mdp2, String dateN) {

        List<Integer> errorform = new ArrayList();

        //----------NOM--------//
        if (nom.trim().isEmpty()) {
            errorform.add(1);
        }else if (nom.length() < 3) {
            errorform.add(2);
        }

        //----------MAIL--------//
        //This regular expression implements the official RFC 2822 standard for email addresses :
        String regeXmail = "\\b(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\\b";

        if (!mail.matches(regeXmail)) {
            errorform.add(3);
        }

        //----------- Mot de passe --------//
        if (mdp.trim().isEmpty()) {
            errorform.add(4);
        }else if (mdp.length() < 6) {
            errorform.add(5);
        }

        //L'utilisateur saisi 2 fois le mot de passe et on vérifie que c'est le même
        if (!mdp2.equals(mdp)) {
            errorform.add(6);
        }

        //----------- Prenom --------//
        if (!prenom.trim().isEmpty() && prenom != null) {
            if (prenom.length() < 2) {
                errorform.add(7);
            }
        }

        //----------- Portable --------//
        //!port.matches("(0)(6|7)[0-9]{8}")
        //!port.matches("[0-9]{10}(^$)") || 
        if (!port.trim().isEmpty() && port != null) {
            if (!port.matches("[0-9]{10}")) {
                errorform.add(8);
            }
        }

        //----------- Tel --------//
        //Si le champs téléphone a été saisi, on rentre dans le 2 eme if et on vérifie si le numéro est composé de 10 chiffres
        //Si le champs n'a pas été saisi donc null, on peut quand même enregistré l'inscrit
        if (!tel.trim().isEmpty() && tel != null) {
            if (!tel.matches("[0-9]{10}")) {
                errorform.add(9);
            }
        }

        //Si la date de naissance a été saisie, on vérifie qu'elle est bien au format sql
        if (!dateN.trim().isEmpty() && dateN != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date date;
            try {
                date = dateFormat.parse(dateN);
            } catch (ParseException ex) {
                dateN = null;
                errorform.add(10);
            }
        }

        return errorform;
    }

    public String erreurMsg(List<Integer> list) {
        String erreur = "";
        for (Integer i : list) {
            switch (i) {
                case 1:
                    erreur = "Veuillez saisir un nom correct. <br>";
                    break;
                case 2:
                    erreur += "Le nom doit être composé au minimum de 3 caractères. <br>";
                    break;
                case 3:
                    erreur += "Veuillez saisir un email correct. <br>";
                    break;
                case 4:
                    erreur += "Veuillez saisir un mot de passe correct. <br>";
                    break;
                case 5:
                    erreur += "La longueur du mot de passe doit être supérieur à 6 caractères. <br>";
                    break;
                case 6:
                    erreur += "Veuillez saisir 2 fois le même mot de passe. <br>";
                    break;
                case 7:
                    erreur += "Veuillez saisir un prenom avec au minimum 2 caractères. <br>";
                    break;
                case 8:
                    erreur += "Veuillez saisir un numéro téléphone portable composé de 10 chiffres consecutifs. <br>";
                    break;
                case 9:
                    erreur += "Veuillez saisir un numéro téléphone composé de 10 chiffres consecutifs. <br>";
                    break;
                case 10:
                    erreur += "Veuillez saisir une date de naissance au format : aaaa-mm-jj. <br>";
                    break;
                default:
                    erreur += "Informations saisies non-valides <br>";
                    break;

            }
        }
        return erreur;
    }

}
