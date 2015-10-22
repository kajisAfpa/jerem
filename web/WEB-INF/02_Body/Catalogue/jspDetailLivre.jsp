<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="container">
    <img src="http://lorempixel.com/400/200/"> <br>

    <h3>Titre : ${detailLivre.titreLivre} <br></h3>
    <h4>Sous-titre : ${detailLivre.sousTitreLivre} <br></h4>


    Résume : ${detailLivre.resumeLivre}<br>
    Prix HT : ${detailLivre.prixHTLivre} €<br>
    valeur tva ${detailLivre.tvaLivre.valeurTauxTva} <br>

    <h3>Carateristiques détaillées</h3>

    <table class="table">
        <tr>
            <td>Auteur : </td>
            <td>${detailLivre.auteurLivre.nomAuteur} ${detailLivre.auteurLivre.prenomAuteur}</td>
        </tr>

        <tr>
            <td>Editeur : </td>
            <td>${detailLivre.editeurLivre.nomEditeur}</td>
        </tr>

        <tr>
            <td>Date de parution : </td>
            <td>${detailLivre.dateLivre}</td>
        </tr>

        <tr>

            <td>Numero d'ISBN : </td>
            <td>${detailLivre.idLivre}</td>

        </tr>

    </table>

    <%-- envoie au panier de l'article via son ID, je met un champ hidden panier pour que mon controller le detecte --%>
    <form method="get" action="controller">
        <input type="hidden" name="panier" value="${detailLivre.idLivre}" />
        <input class="btn btn-success" type="submit" name="sendToPanier" value="Ajouter" />
    </form>

    <h2 class="page-header text-center">Commentaire</h2>

    <form method="get" action="controller">
        <div class="form-group">
            <label for="contenu">Mon commentaire</label>
            <textarea id="contenu" class="form-control" rows="3" name="contenu"></textarea>
        </div>
        <input type="hidden" name="detailLivre" />
        <button name="comentSend" type="submit" class="btn btn-default">Submit</button>
    </form>
    <c:if test="${comentSuccess != null}">
        <div class="alert alert-success">envoie commentaire avec succes</div>
    </c:if>


    <h4>nom prenom :</h4> 

    <blockquote>Lorem ipsum dolor sit amet, consectetur 
        adipiscing elit, sed do eiusmod tempor incididunt ut labore et 
        dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
        ullamco laboris nisi ut aliquip ex ea commodo  </blockquote>
</div>
