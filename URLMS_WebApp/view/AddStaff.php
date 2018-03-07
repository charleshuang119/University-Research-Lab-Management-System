<?php
$my_dir = dirname(__FILE__);
require_once $my_dir . '\..\controller\ManagerController.php';

if(!isset($_SESSION)){ //Set Session only once
    session_start();
    $_SESSION["errorStaffName"] = ""; //Initialize session variable for error messages
    $_SESSION["currURLMS"] = null;
    $_SESSION["errorRemove"]="";
}




$c = new ManagerController(); 
if(isset($_POST["insert"])){//request.getParameter("insert") != null){ //If user selects the add button
    try {//call ManagerController addStaff, with the posted info from index.php
    	$c->addStaffMember($_POST['firstname'],$_POST['lastname'],$_POST['role']);
    	$_SESSION["errorStaffName"] = "";
    } catch (Exception $e) { //if addStaff throws an exception
    	$_SESSION["errorStaffName"] = $e->getMessage(); //set session variable to error message
    }
}
else if (isset($_POST["remove"])){//request.getParameter("remove") != null){ //If user selects the remove button
    try{
        $c ->removeStaffMember($_POST['id'], $_POST['firstname'], $_POST['lastname']);
    } catch (Exception $e){
        $_SESSION["errorRemove"] = $e -> getMessage();
    }
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=AddStaffPage.php" /> <!-- Refreshes page after 2 second delay -->
	</head>
</html> 
