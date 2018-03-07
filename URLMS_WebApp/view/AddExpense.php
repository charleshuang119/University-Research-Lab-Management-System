<?php
$my_dir = dirname(__FILE__);
require_once $my_dir . '\..\controller\ManagerController.php';

if(!isset($_SESSION)){ //Set Session only once
    session_start();
    $_SESSION["expenseError"] = ""; //Initialize session variable for error messages
    $_SESSION["currURLMS"] = null;
}




$c = new ManagerController();
if(isset($_POST["add"])){//request.getParameter("insert") != null){ //If user selects the add button
    try {//call ManagerController addStaff, with the posted info from index.php
        if(!isset($_POST['Type'])) {
            throw new Exception("Must Select a type");
        } else {
        $c->addExpense($_POST['Desciption'],$_POST['Amount'],$_POST['Type'],$_POST['StaffLastName']);
        $_SESSION["expenseError"] = "";
        }
    } catch (Exception $e) { //if addStaff throws an exception
        $_SESSION["expenseError"] = $e->getMessage(); //set session variable to error message
    }
}

?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=AddExpensePage.php" /> <!-- Refreshes page after 2 second delay -->
	</head>
</html> 