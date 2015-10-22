<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container">

    <h2 class="page-header">Connexion</h2>

    <form class="formConnection" method="controller">
        <div class="input-group input-group-lg">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-envelope"></span>
            </span>
            <input class="form-control" type="text" placeholder="Votre adresse e-mail" name="email" value="${user}">
        </div>

        <div class="input-group input-group-lg">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-lock"></span>
            </span>
            <input class="form-control" id="pass1" type="password" placeholder="Votre mot de passe" name="mdp">
        </div> 

        <div class="checkbox">
            <label>
                <input name="remember" type="checkbox"> Se souvenir de moi
            </label>
        </div>

        <button type="submit" name="cnt" class="btn btn-default">Valider</button>

    </form>

    <br>

    <c:if test="${msg!=null}">
        <div class="alert alert-danger text-center">${msg}</div>
    </c:if>        


</div>
