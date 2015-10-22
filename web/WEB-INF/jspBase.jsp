<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/bootstrap-3.3.5-dist/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap-3.3.5-dist/css/bootstrap.css"  type="text/css" media="all">
        <link rel="stylesheet" href="pickadate.js-3.5.6/lib/themes/default.css"/>
        <link rel="stylesheet" href="pickadate.js-3.5.6/lib/themes/default.date.css"/>
        <title>Kajis Librairie</title>
    </head>
    <body>
        <jsp:include page='${head}' flush='true' />
        <hr>
        <jsp:include page='${corps}' flush='true'/>
        <hr>
        <jsp:include page='${foot}' flush='true'/>
    </body>
</html>
