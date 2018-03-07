<?php
$my_dir = dirname(__FILE__);
require_once $my_dir . '\..\controller\ManagerController.php';

if(!isset($_SESSION)){ //Set Session only once
    session_start();
    $_SESSION["fundingError"] = ""; //Initialize session variable for error messages
    $_SESSION["currURLMS"] = null;
}




$c = new ManagerController();
if(isset($_POST["set"])){//request.getParameter("insert") != null){ //If user selects the add button
    try {//call ManagerController addStaff, with the posted info from index.php       
            $c->setAllocatedAmount($_POST['AllocatedAmount']);
            $_SESSION["fundingError"] = "";      
    } catch (Exception $e) { //if addStaff throws an exception
        $_SESSION["fundingError"] = $e->getMessage(); //set session variable to error message
    }
}

?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/P11/URLMS_WebApp/view/FundingAccountPage.php" /> <!-- Refreshes page after 2 second delay -->
	</head>
</html> 