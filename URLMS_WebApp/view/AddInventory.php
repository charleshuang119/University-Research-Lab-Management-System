<?php
$my_dir = dirname(__FILE__);
require_once $my_dir . '\..\controller\ManagerController.php';

if(!isset($_SESSION)){ //Set Session only once
    session_start();
    $_SESSION["inventoryError"] = ""; //Initialize session variable for error messages
    $_SESSION["currURLMS"] = null;
}




$c = new ManagerController(); 
if(isset($_POST["insert"])){//request.getParameter("insert") != null){ //If user selects the add button
    try {//call ManagerController addStaff, with the posted info from index.php
        $c->addResource($_POST['Name'],$_POST['AddQuantity'],$_POST['type']);
    	$_SESSION["inventoryError"] = "";
    } catch (Exception $e) { //if addStaff throws an exception
    	$_SESSION["inventoryError"] = $e->getMessage(); //set session variable to error message
    }
}
else if (isset($_POST["remove"])){//request.getParameter("remove") != null){ //If user selects the remove button
    try{
        $c ->removeResource($_POST['id'], $_POST['RemoveName']);
        $_SESSION["inventoryError"] = "";
    } catch (Exception $e){
        $_SESSION["inventoryError"] = $e -> getMessage();
    }
}
    
else if (isset($_POST["edit"])){//request.getParameter("remove") != null){ //If user selects the remove button
     try{
          $c ->editResource($_POST['Inventory'],$_POST['EditQuantity']);
          $_SESSION["inventoryError"] = "";
      } catch (Exception $e){
          $_SESSION["inventoryError"] = $e -> getMessage();
      }
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=InventoryPage.php" /> <!-- Refreshes page after 2 second delay -->
	</head>
</html> 