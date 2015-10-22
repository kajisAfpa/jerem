<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="pickadate.js-3.5.6/lib/themes/default.css">
        <link rel="stylesheet" href="pickadate.js-3.5.6/lib/themes/default.date.css">
       <style>

            #submit {
                margin-bottom:20px; 
            }

            .champObl {
                font-weight: bold; 
                font-size:18px;
            }
            
            form {
                width: 50%;
                margin: auto;
            }

        </style>
    </head>


    <body>

        <div class="container">

            <h1 class="page-header text-center">Bienvenue sur la page de création de compte</h1>

            <c:if test="${error != null}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form METHOD='GET' ACTION='controller'>
                
                <div class="form-group">
                <label for="Type">Type : 
                    <select class='form-control' name='type' >
                            <option> Particulier
                            <option> Professionnel
                    </select>
                    </label>
                </div>
                
                <div class="form-group">
                    <label for="Nom">Nom<span class="champObl">*</span></label>
                    <input class="form-control" id="Nom" placeholder="Veuillez entrer un nom"  NAME='nom'  type="text">
                </div>


                <div class="form-group">
                    <label for="Prenom">Prenom</label>
                    <input class="form-control" id="Prenom" placeholder="Veuillez entrer un prénom"  NAME='prenom'  type="text">
                </div>


                <div class="form-group">
                    <label for="Mail">Mail<span class="champObl">*</span></label>
                    <input class="form-control" id="Mail" placeholder="Veuillez entrer un email"  NAME='mail'  type="text">
                </div>


                <div class="form-group">
                    <label for="Password">Mot de passe<span class="champObl">*</span></label>
                    <input class="form-control" id="exampleInputPassword1" placeholder="Veuillez entrer un mot de passe" NAME='mdp' type="password">
                </div>

                <div class="form-group">
                    <label for="exampleInputPassword1">Confirmation mot de passe<span class="champObl">*</span></label>
                    <input class="form-control" id="exampleInputPassword1" placeholder="Veuillez entrer le même mot de passe" NAME='mdp2' type="password">
                </div>

                <div class="form-group">
                    <label for="Nom">Telephone portable</label>
                    <input class="form-control" id="Nom" placeholder="Veuillez entrer un numéro de téléphone portable"  NAME='port'  type="text">
                </div>   

                <div class="form-group">
                    <label for="Nom">Telephone</label>
                    <input class="form-control"  id="Nom" placeholder="Veuillez entrer un numéro de téléphone"  NAME='tel'  type="text">
                </div>



                <div class="form-group">
                    <label for="Nom">Date de naissance</label>
                    <input class="form-control datepicker" id="Nom" placeholder="Veuillez entrer une date de naissance (format : yyyy-mm-jj)"  NAME="date"  type="date">
                </div>
                

                    
               


                <div class="form-group">
                    <button id="submit" type="submit" class="btn btn-default" NAME='inscription'>Valider</button><br>

                    <span class="champObl">*</span> champs obligatoire
            </form>



        </div>




    </body>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="pickadate.js-3.5.6/lib/picker.js"></script>
    <script src="pickadate.js-3.5.6/lib/picker.date.js"></script>
    <script src="pickadate.js-3.5.6/lib/legacy.js"></script>

    <script type="text/javascript">

        $( document ).ready(function() {
    
    
    $('.datepicker').pickadate({
  monthsFull: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
  weekdaysShort: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
  today: 'aujourd\'hui',
  clear: 'effacer',
  close: 'fermer',
  format: 'yyyy-mm-dd',
  max: 2015-12-31,
  selectYears: 100,
  selectMonths: true,
  
})
    
});

    </script>
    
