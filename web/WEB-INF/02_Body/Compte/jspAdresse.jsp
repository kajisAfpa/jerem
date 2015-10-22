<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Adresse de Facturation :<br><ul>
    <c:forEach var="a" items="${adresse}">
        <li><a href='controller?corps=adresse&modif=${a.idAdresse}'>${a.libelleAdresse}</a><br></li>
        </c:forEach>
</ul>

<hr>
Adresse de Livraison :<br><ul>
    <c:forEach var="a" items="${adresseP}">
        <li> <a href='controller?corps=adresse&modif=${a.idAdresse}'>${a.libelleAdresse}</a><br></li>
        </c:forEach>
</ul>

<form METHOD='POST' ACTION='controller'>
    <input type='submit' name='creation' value='Nouvelle Adresse'/>
</form>