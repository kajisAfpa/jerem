<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container">

    <c:if test="${(param.search != null) || (param.categorie != null)}">
        <c:if test="${nbr_article_trouve != 0}">
            <div class="alert alert-success text-center">Nombre d'article trouvé: ${nbr_article_trouve}</div>
        </c:if>
        <c:if test="${nbr_article_trouve == 0}">
            <div class="alert alert-danger text-center">Nombre d'article trouvé: ${nbr_article_trouve}</div>
        </c:if>
    </c:if>

    <c:if test="${param.commenter != null}">
        <div class="alert alert-info">${retour_comment}</div>
    </c:if>


    <form class="formSearch"  method="controller">
        <div class="input-group input-group-lg">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-search"></span>
            </span>
            <input class="form-control" type="text" placeholder="Entrez votre recherche" name="search">
        </div>

        <input type="hidden" name="catalog" value=""/>

        <button type="submit" name="recherche" class="btn btn-default">Recherche</button>
    </form>






    <div class="row">

        <%-- sommaire cote --%>

        <div class="col-md-2">

            <ul class="sommaire nav nav-pills nav-stacked ">

                <c:forEach var="theme" items="${ListeTheme}">
                    <li class="theme dropdown">
                        <a aria-expanded="false" class="dropdown-toggle" data-toggle="dropdown" href="#">
                            ${theme.nomTheme} <span class="caret"></span>
                        </a>

                        <ul class="dropdown-menu">

                            <c:forEach var="soustheme" items="${theme.getSousThemeTheme()}">

                                <li><a href="controller?catalog&categorie=${soustheme.getNomSousTheme()}">${soustheme.getNomSousTheme()}</a></li>

                            </c:forEach>

                        </ul>

                    </li>
                </c:forEach>
            </ul>



        </div>



        <%-- article catalogue --%>

        <div class="col-md-10">



            <table class="table table-striped">



                <c:forEach var="livre" items="${listeLivre}">

                    <%-- resume du livre en mode substring --%>
                    <c:set var="resume" value="${livre.resumeLivre}"/>
                    <c:set var="cutResume" value="${fn:substring(resume, 0, 150)}" />


                    <tr class="livreGrille">

                    <div class="row">

                        <td class="col-md-2"> <img src="http://lorempixel.com/400/200/"></td>
                        <td class="col-md-6 contenu">

                            <h4 class="title text-center text-primary">${livre.titreLivre} <small> - ${livre.sousTitreLivre}</small></h4>
                            <p>ecrit par: ${livre.auteurLivre.nomAuteur} ${livre.auteurLivre.prenomAuteur} </p>
                            <p class="resume">Résumé: ${cutResume}...</p>
                            <p class="text-center">
                                <c:forEach var="motCle" items="${livre.motsClefs}">
                                    <a href="">${motCle.nomMotsClefs}</a>
                                </c:forEach>
                            </p>

                        </td>
                        <td class="shop col-md-2">


                            <p class="price">prix : ${livre.prixHTLivre}€ HT </p>

                            <c:if test="${livre.quantiteLivre > 200}">
                                <p class="text-success">En stock</p>
                            </c:if>

                            <c:if test="${livre.quantiteLivre <= 200}">
                                <p class="text-danger">Plus en stock</p>
                            </c:if>
                            <a href="controller?sendToPanier=${livre.idLivre}"><button class="btn btn-success">Add   <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span></button></a>
                            <a href="controller?detailLivre=${livre.idLivre}"><button class="btn btn-default"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span></button></a>


                        </td>

                    </div>

                    </tr>


                </c:forEach>




            </table>

        </div>
    </div>



    <nav class="text-center">
        <ul class="pagination">

            <c:forEach varStatus="i" begin="1" end="${nbrPage + 1}">

                <c:if test="${nbrPage >= 1}">
                    <li><a href="controller?${page}&page=${i.index}">${i.index}</a></li>  
                    </c:if>

            </c:forEach>


        </ul>
    </nav>



</div>




