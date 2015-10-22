package servlets;

import beans.beanAdresse;
import beans.beanCommentaire;
import beans.beanInscription;
import beans.beanLogin;
import beans.beanMembre;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sql.ReqCommentaire;
import sql.ReqLivre;
import sql.ReqTheme;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class controller extends HttpServlet {

    private Cookie getCookie(Cookie[] c, String name) {
        if (c != null) {
            for (Cookie t : c) {
                if (t.getName().equals(name)) {
                    return t;
                }
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

//ADRESSE DE BASE---------------------------------------------------------------
        ServletContext app = this.getServletContext();
        HttpSession session = request.getSession();
        String url = "/WEB-INF/jspBase.jsp";

        request.setAttribute("head", "/WEB-INF/01_Header/jspHeader.jsp");
        request.setAttribute("corps", "/WEB-INF/02_Body/Catalogue/jspCatalog.jsp");
        request.setAttribute("foot", "/WEB-INF/03_Footer/jspFooter.jsp");

        //S'IL SE DECO, LA SESSION DEVIENT VIDE
        if ("deco".equals(request.getParameter("menu"))) {
            session.setAttribute("user", null);
            request.setAttribute("decont", "");
        }

        //PREND LE MEMBRE DE LA SESSION ET LE MET DANS UN OBJET BEANMEMBRE
        beanMembre m = (beanMembre) session.getAttribute("user");

//INSCRITPION-------------------------------------------------------------------
        //S'IL CLIQUE SUR INSCRIPTION DANS LE MENU, AFFICHE LE MENU D'INSCRIPTION
        //DANS LE CORPS
        if ("inscrire".equals(request.getParameter("corps"))) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspInscription.jsp");
        }

        if (request.getParameter("inscription") != null) {
            beanInscription inscription = (beanInscription) app.getAttribute("Inscription");
            if (inscription == null) {
                inscription = new beanInscription();
                app.setAttribute("Inscription", inscription);
            }

            List<Integer> errorform = inscription.checkInt(request.getParameter("nom"),
                    request.getParameter("prenom"),
                    request.getParameter("mail"),
                    request.getParameter("port"),
                    request.getParameter("tel"),
                    request.getParameter("mdp"),
                    request.getParameter("mdp2"),
                    request.getParameter("date"));

            //"if" est considéré comme un boolean
            //on doit donc rajouter .isEmpty afin de transformer cette condition en boolean
            //Si la liste renvoyé est vide, le boolean sera true et on va pouvoir inscrire un membre
            if (errorform.isEmpty()) {
                request.setAttribute("corps", "/WEB-INF/02_Body/jspSucces.jsp");
                m = new beanMembre();
                m.updateMembre(request.getParameter("mdp"),
                        request.getParameter("nom"),
                        request.getParameter("prenom"),
                        request.getParameter("type"),
                        request.getParameter("date"),
                        request.getParameter("mail"),
                        request.getParameter("tel"),
                        request.getParameter("port"));
                try {
                    m.creationMembre();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("Problème à l'inscription : " + ex);
                    request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                    request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
                }
                request.setAttribute("nom", m.getNomMembre());
                session.setAttribute("user", m);
            } else {
                request.setAttribute("corps", "/WEB-INF/jspInscription.jsp");
                request.setAttribute("msg", "Informations saisies invalides !!!");
                request.setAttribute("error", inscription.erreurMsg(errorform));
            }
        }

//METHODE POUR LE LOGIN---------------------------------------------------------
        //S'IL CLIQUE SUR CONNEXION DANS LE MENU, AFFICHE LE MENU DE CONNEXION
        //DANS LE CORPS
        if ("cnt".equals(request.getParameter("corps"))) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspLogin.jsp");
        }

        //S'IL CLIQUE SUR "SE CONNECTER" DANS LA PAGE LOGIN
        if (request.getParameter("cnt") != null) {

            //ON CREE UN OBJET LOGIN POUR CHECKER LES USER ET MDP DE L'UTILISATEUR
            beanLogin login = (beanLogin) app.getAttribute("login");

            //ON UTILISE LE MEME OBJET LOGIN POUR TOUTE L'APPLICATION, POUR NE PAS
            //EN RECREE UN A CHAQUE FOIS
            if (login == null) {
                login = new beanLogin();
                app.setAttribute("login", login);
            }

            //ON CREE UN INT QUI NOUS INDIQUE LA VALIDITER DU MAIL ET DU MDP
            //ON UTILISE LA METHODE CHECK DE BEANLOGIN POUR AVOIR LA VALEUR
            try {
                int id = login.check(request.getParameter("email"), request.getParameter("mdp"));
                //SI L'ID EST SUPERIEUR A 0, CELA VEUT DIRE QUE LE MAIL ET LE MDP SONT
                //VALIDE
                if (id > 0) {
                    m = new beanMembre();
                    //ON VA CREE UN OBJET MEMBRE VIA L'ID RECUPERER.
                    //L'ID RECUPERER EST L'ID DU MEMBRE DANS NOTRE BASE SQL
                    try {
                        m.importMembre(id);
                    } catch (ClassNotFoundException | SQLException ex) {
                        System.out.println("Problème à l'importation des données utilisateur : " + ex);
                        request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                        request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
                    }
                    //ON REAFFICHE LE CATALOGUE 
                    //request.setAttribute("corps", "/WEB-INF/02_Body/Catalogue/jspCatalog.jsp");

                    //ON CREE UNE SESSION AVEC EN VALEUR L'OBJET MEMBRE QUI S'EST 
                    //CONNECTE
                    session.setAttribute("user", m);

                    //ON SUPPRIME LE COOKIE D'ESSAIE
                    Cookie c = new Cookie("try", "");
                    c.setMaxAge(0);
                    response.addCookie(c);

                    //SI LE MAIL ET MDP NE SONT PAS VALIDE
                } else {
                    //ON REAFFICHE LA PAGE LOGIN
                    request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspLogin.jsp");

                    //ON VA CREE/CHERCHER LE COOKIE D'ESSAIE
                    Cookie c = getCookie(request.getCookies(), "try");
                    if (c == null) {
                        c = new Cookie("try", "");
                    }

                    //ON AUGMENTE D'UN LA VALEUR DU COOKIE D'ESSAIE
                    c.setValue(c.getValue() + "*");

                    //EN FONCTION DE L'ID, ON AFFICHE LE MSG D'ERREUR CORRESPONDANT
                    switch (id) {
                        case -1:
                            request.setAttribute("msg", "Utilisateur/Mot de passe invalide. Il vous reste " + (3 - c.getValue().length()) + " tentatives.");
                            break;
                        case -2:
                            request.setAttribute("msg", "Utilisateur vide. Il vous reste " + (3 - c.getValue().length()) + " tentatives.");
                            break;
                        case -3:
                            request.setAttribute("msg", "Mot de passe vide. Il vous reste " + (3 - c.getValue().length()) + " tentatives.");
                            break;
                    }

                    //ON REAFFICHE LE MAIL TAPER AVANT
                    request.setAttribute("user", request.getParameter("email"));

                    //SI LA VALEUR DU COOKIE D'ESSAIE EST SUPERIEUR A 3
                    //ON AFFICHE FATALERROR ET ON BLOQUE L'UTILISATEUR PENDANT 2 MIN
                    if (c.getValue().length() > 2) {
                        request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspTry.jsp");
                        request.setAttribute("msg", "Nombre de tentatives maximum, veuillez attendre 2 minutes");
                        c.setMaxAge(60 * 2);
                    }
                    response.addCookie(c);

                }
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème au login : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
        }

//AFFICHAGE COMPTE--------------------------------------------------------------
        if (request.getParameter("modifMembre") != null) {
            m.updateMembre(request.getParameter("mdp"), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("type"), request.getParameter("date"), request.getParameter("email"), request.getParameter("tel"), request.getParameter("port"));
            try {
                m.modifierMembre();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème à la modification d'un utilisateur : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
            request.setAttribute("nom", m.getNomMembre());
        }

        if ("compte".equals(request.getParameter("corps"))) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspCompte.jsp");
            request.setAttribute("membre", m);
            request.setAttribute(m.Type(), "selected");
        }

        if (request.getParameter("suppMembre") != null) {
            try {
                m.supprimerMembre();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème à la suppression des données utilisateur : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
            session.setAttribute("user", null);
        }

//AFFICHAGE ADRESSE-------------------------------------------------------------   
        if ("adresse".equals(request.getParameter("corps"))) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspAdresse.jsp");
            request.setAttribute("adresse", m.getAdresseFac());
            request.setAttribute("adresseP", m.getAdresseLiv());

            if (request.getParameter("modif") != null) {
                request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspModificationAdresse.jsp");
                beanAdresse a = (beanAdresse) m.getAdresse(Integer.parseInt(request.getParameter("modif")));
                session.setAttribute("adresse", a);
                request.setAttribute("adresse", a);
                request.setAttribute(a.Type(), "selected");
            }
        }

        if (request.getParameter("modifierAdresse") != null) {
            beanAdresse a = (beanAdresse) session.getAttribute("adresse");
            m.supAdresse(a);
            a.updateAdresse(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("type"), request.getParameter("libelle"), request.getParameter("rue"), request.getParameter("cp"), request.getParameter("ville"), request.getParameter("pays"));
            try {
                a.modifierAdresse();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème à la modificaiton d'adresse : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
            m.addAdresse(a);
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspAdresse.jsp");
            request.setAttribute("adresse", m.getAdresseFac());
            request.setAttribute("adresseP", m.getAdresseLiv());
            session.setAttribute("adresse", null);
        }

        if (request.getParameter("supprimerAdresse") != null) {
            beanAdresse a = (beanAdresse) session.getAttribute("adresse");
            try {
                a.supprimerAdresse();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème à la suppression d'adresse : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
            m.supAdresse(a);
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspAdresse.jsp");
            request.setAttribute("adresse", m.getAdresseFac());
            request.setAttribute("adresseP", m.getAdresseLiv());
            session.setAttribute("adresse", null);
        }

        if (request.getParameter("creation") != null) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspCreationAdresse.jsp");
        }

        if (request.getParameter("valider") != null) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Compte/jspCompte.jsp");
            request.setAttribute("membre", m);
            beanAdresse a = new beanAdresse();
            a.updateAdresse(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("type"), request.getParameter("libelle"), request.getParameter("rue"), request.getParameter("cp"), request.getParameter("ville"), request.getParameter("pays"));
            try {
                a.creationAdresse();
                m.insertAdresse(a);
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Problème à l'insertion d'un adresse dans un membre : " + ex);
                request.setAttribute("corps", "/WEB-INF/02_Body/jspError.jsp");
                request.setAttribute("error", "Problème d'accès à la base de données. Merci de réessayer ultérieurement. ");
            }
        }

//AFFICHAGE HEADER--------------------------------------------------------------
        request.setAttribute("cnt", m);

//AFFICHAGE CATALOGUE-----------------------------------------------------------
        //S'IL CLIQUE SUR CATALOGUE DANS LE MENU, AFFICHE LE CATALOGUE DANS LE 
        //CORPS
        if ("cata".equals(request.getParameter("corps")) || "/WEB-INF/02_Body/Catalogue/jspCatalog.jsp".equals(request.getAttribute("corps"))) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Catalogue/jspCatalog.jsp");

//              instance ma methode sql listant tout mes livre
            ReqLivre reqListeLivre = new ReqLivre();

//              les bornes des pages
            int nbr_article = 10;
            int depart = 0;

//              nombre de page
            int nbrPage = 0;
            try {
                nbrPage = reqListeLivre.nbrTotalArticle() / nbr_article;
//                  envoie a ma jsp variable nbre page
                request.setAttribute("nbrPage", nbrPage);
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

//              si url page existe:
            if (request.getParameter("page") != null) {
//                  article de depart est valeur get.page - 1 * nbr_article
                depart = (Integer.parseInt(request.getParameter("page")) - 1) * nbr_article;
            }

//              envoie variable a ma jsp, pour indiquer que la page pointe vers tel argument
            request.setAttribute("page", "catalog");

//              affichage sur ma jsp
//              envoie de ma liste ds ma jsp via la metode request. nom de ma variable 'listeLivre'
            request.setAttribute("listeLivre", null);
            try {
                request.setAttribute("listeLivre", reqListeLivre.getListeLivre(depart, nbr_article));
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

//              on refait ce processus pour chaque cas d'affichage (affichage par sous theme, par search...)
            if (request.getParameter("categorie") != null) {

                request.setAttribute("page", "catalog&categorie=" + request.getParameter("categorie"));

//                  calcul nombre page
                try {
                    nbrPage = reqListeLivre.nbrTotalArticleByCat(request.getParameter("categorie")) / nbr_article;
                    request.setAttribute("nbr_article_trouve", reqListeLivre.nbrTotalArticleByCat(request.getParameter("categorie")));
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
//                  envoie a ma jsp variable nbre page
                request.setAttribute("nbrPage", nbrPage);

//                  envoie de ma liste d'article a ma jsp
                try {
                    request.setAttribute("listeLivre", reqListeLivre.getListeLivreByCat(depart, nbr_article, "" + request.getParameter("categorie")));
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (request.getParameter("search") != null) {

                request.setAttribute("page", "catalog&search=" + request.getParameter("search"));

                try {
                    nbrPage = reqListeLivre.nbrTotalArticleBySearch(request.getParameter("search")) / nbr_article;
                    request.setAttribute("nbr_article_trouve", reqListeLivre.nbrTotalArticleBySearch(request.getParameter("search")));
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }

                //envoie a ma jsp variable nbre page
                request.setAttribute("nbrPage", nbrPage);

                //envoie de ma liste d'article a ma jsp
                try {
                    request.setAttribute("listeLivre", reqListeLivre.getListeLivreBySearch(request.getParameter("search"), depart, nbr_article));
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

//          requete de ma liste de theme
            ReqTheme reqListTheme = new ReqTheme();

//          envoie a ma jsp liste theme
            try {
                request.setAttribute("ListeTheme", reqListTheme.getTheme());
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

        }

    //AFFICHAGE DETAIL LIVRE
        //page du detail du livre
        if (request.getParameter("detailLivre") != null) {
            request.setAttribute("corps", "/WEB-INF/02_Body/Catalogue/jspDetailLivre.jsp");
            //instance ma methode sql prenant unlivre selon num isbn
            ReqLivre reqLivreDetail = new ReqLivre();

            //envoie de mon livre ds ma jsp via la metode request. nom de ma variable 'detailLivre'
            request.setAttribute("detailLivre", null);
            try {
                request.setAttribute("detailLivre", reqLivreDetail.getLivre(request.getParameter("detailLivre")));
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

            //envoie affichage commentaire
            ReqCommentaire reqComLivre = new ReqCommentaire();
            String detailLivre = request.getParameter("detailLivre");
            try {
                request.setAttribute("listeCommentaire", reqComLivre.getListCommentaire(detailLivre));
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

            if (request.getParameter("comentSend") != null) {
                request.setAttribute("comentSuccess", null);
            }

        }
        
        request.getRequestDispatcher("/WEB-INF/jspBase.jsp").include(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
