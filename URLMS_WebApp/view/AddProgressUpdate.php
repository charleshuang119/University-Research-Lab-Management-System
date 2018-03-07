<?php
$my_dir = dirname(__FILE__);
require_once $my_dir . '\..\controller\ManagerController.php';

if(!isset($_SESSION)){ //Set Session only once
    session_start();
    $_SESSION["errorProgress"] = ""; //Initialize session variable for error messages
    $_SESSION["currURLMS"] = null;
}




$c = new ManagerController();
if(isset($_POST["insert"])){//If user selects the add button
    try {//call ManagerController addStaff, with the posted info from progressupdatepage.php
        $message = trim($_POST['message']);
        if(!isset($_POST['message'])||$message==''){ //message is empty or whitespace
            throw new Exception("Must provide a progress update message!");
        }else if(!isset($_POST['staff'])||$_POST['staff'] == "default"){//Staff member not selected
            throw new Exception("Must select a staff member associated with message");
        }else{//Valid Inputs
            $c->addProgress($_POST['staff'],$_POST['message']);
            $_SESSION["errorProgress"] = "";
        }
    } catch (Exception $e) { //if addStaff throws an exception
        $_SESSION["errorProgress"] = $e->getMessage(); //set session variable to error message
    }
}

?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=ProgressUpdatePage.php" /> <!-- Refreshes page after 2 second delay -->
	</head>
</html> 
