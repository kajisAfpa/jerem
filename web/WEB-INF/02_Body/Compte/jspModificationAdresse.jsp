<form METHOD='POST' ACTION='controller'>
    Type :          <select class="form-control" name="type" >
                            <option ${liv}> Livraison
                            <option ${fac}> Facturation
                    </select> <br>
    Libelle :       <input type='text' name='libelle' value='${adresse.getLibelleAdresse()}'/> <br>
    Nom :           <input type='text' name='nom' value='${adresse.getNomAdresse()}'/> <br>
    Prenom :        <input type='text' name='prenom' value='${adresse.getPrenomAdresse()}'/> <br>
    Rue :           <input type='text' name='rue' value='${adresse.getRueAdresse()}'/> <br>
    Code Postal :   <input type='text' name='cp' value='${adresse.getCpAdresse()}'/> <br>
    Ville :         <input type='text' name='ville' value='${adresse.getVilleAdresse()}'/> <br>
    Pays :          <input type='text' name='pays' value='${adresse.getPaysAdresse()}'/> <br>
    <input type='submit' name='modifierAdresse' value='Modifier'><br>
    <input type='submit' name='supprimerAdresse' value='Supprimer'><br>
</form> 