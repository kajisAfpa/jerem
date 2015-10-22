<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<h1 class="page-header text-center">Mon compte</h1>

<div class="container">
    <form METHOD='POST' ACTION='controller'> 

        Type : <select class='form-control' name='type' >
            <option ${par}> Particulier
            <option ${pro}> Professionnel
        </select><br>

        <table class="table table-striped">

            <tr class="row">                
                <td class="col-lg-4">Nom :</td>
                <td class="col-lg-5"><input type='text' name='nom' value='${membre.nomMembre}'/></td>
            </tr>

            <tr class="row">               
                <td class="col-lg-4">Prenom :</td>
                <td class="col-lg-5"><input type='text' name='prenom' value='${membre.prenomMembre}'/></td>
            </tr>

            <tr class="row">
                <td class="col-lg-4">Adresse Email :</td>
                <td class="col-lg-5"><input type='text' name='email' value='${membre.mailMembre}'/></td>
            </tr>

            <tr class="row">       
                <td class="col-lg-4">Mot de passe :</td>
                <td class="col-lg-5"><input type='text' name='mdp' value='${membre.mdpMembre}'/></td>
            </tr>

            <tr class="row">    
                <td class="col-lg-4">Telephone fixe : </td>
                <td class="col-lg-5"><input type='text' name='tel' value='${membre.telMembre}'/></td>     
            </tr>

            <tr class="row">         
                <td class="col-lg-4">Portable :</td>
                <td class="col-lg-5"><input type='text' name='port' value='${membre.portMembre}'/></td>
            </tr>

            <tr class="row">       
                <td class="col-lg-4">Date de naissance :</td>
                <td class="col-lg-5"><input type='text' name='date' value='${membre.dateMembre}'/></td>
            </tr>

        </table>
        <input type='submit' name='modifMembre' value='Modifier' class="btn btn-default"><br>
        <input type='submit' name='suppMembre' value='Supprimer' class="btn btn-default"><br>
        <a href='controller?corps=adresse'>Adresse</a>
    </form>                                
</div>