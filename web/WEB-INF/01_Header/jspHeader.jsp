<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="header">
    <div class="container">
        <ul class="menu-bar nav nav-tabs">
            <li class="text-center active"><a class="menu-item " href="controller" >Acceuil</a></li>
            <li class="text-center"><a class="menu-item " href="controller?corps=cata" >Catalogue</a></li>
                    
                <%-- si il n'est pas connecté : --%>
                <c:if test="${cnt == null}">
                    <li class="text-center"><a class="menu-item " href="controller?corps=cnt" >Connexion</a></li>
                    <li class="text-center"><a class="menu-item " href="controller?corps=inscrire" >Inscription</a></li>
                </c:if>

                <%-- si il est déjà connecté : --%>
                <c:if test="${cnt != null}">
                    <li class="text-center"><a class="menu-item " href="controller?menu=deco" >Deconnexion</a></li>
                    <li class="text-center"><a class="menu-item " href="controller?corps=compte" >Mon Compte</a></li>
                </c:if>

        </ul>

        <c:if test="${decont != null}">
            <div style="margin: 50px" class="alert alert-warning text-center"> Vous avez été déconnecté !</div>
        </c:if>
    </div>
</div>
 
