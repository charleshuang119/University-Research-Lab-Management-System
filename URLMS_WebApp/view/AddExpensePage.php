<?php include '../controller/ManagerController.php';
$my_dir = dirname(__FILE__);
require_once $my_dir . '/../persistence/PersistenceURLMS.php';
require_once $my_dir . '/../model/Staff.php';
require_once $my_dir . '/../model/URLMS.php';
require_once $my_dir . '/../model/Lab.php';
require_once $my_dir . '/../model/Associate.php';
require_once $my_dir . '/../model/Assistant.php';
require_once $my_dir . '/../model/Director.php';
require_once $my_dir . '/../model/Role.php';


if(!isset($_SESSION)){
	session_start();
	
}

$pm = new PersistenceURLMS();
$urlms = $pm->loadDataFromStore(); //Return a new URLMS with a Lab from memory or make a new one
$lab = $urlms->getSingleLab();

?>
<!DOCTYPE html>
<html>

<head>
  <style>
    .error {
      color: #FF0000;
    }
  </style>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-beta.1.css" type="text/css"> </head>

<body class="m-0">
  <nav class="navbar navbar-expand-md bg-secondary navbar-dark">
    <div class="container">
      <a class="navbar-brand" href="../index.php">URLMS</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="AddStaffPage.php">Staff</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="ProgressUpdatePage.php">Progress Updates</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="InventoryPage.php">Inventory</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Add Expenses</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="FundingAccountPage.php">Funding Account</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="py-5">
    <div class="container">
     <h1 class="" style="">Add Expense</h1>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <form action="AddExpense.php" method="POST">
          <span class="error">
                   <?php
                        //I.e. if error is set (the && is semantics due to add staff initialization to " "
                        if(isset ( $_SESSION["expenseError"])&& ! empty ( $_SESSION["expenseError"])){
                            echo " *Error: ". $_SESSION["expenseError"]; //Print Error message
                        }
                        ?>
            	</span> 
        	<fieldset>
          <div class="form-group"> <label>Staff member:</label> <br>
          <select name="StaffLastName">
            <?php
  			$count=0;
  			for ($count=0;$count<$lab->numberOfStaff();$count++){
  			    echo "<option value=".$lab->getStaff_index($count)->getLastName().">".$lab->getStaff_index($count)->getFirstName()." ".$lab->getStaff_index($count)->getLastName()."</option>";	
  		    }
  		    ?>
            </select><br></div>
            <div class="form-group"> <label>Description:</label>
              <input type="text" name="Desciption" class="form-control" placeholder="Please enter a brief desciption of the expense"> </div>
            <div class="form-group"> <label>Amount</label>
              <input type="number" name="Amount" step="0.01" class="form-control" placeholder="Please enter the cost of the expense"  min="1"> </div>
            <input type="radio" name="Type" value="Travel">Travel                         
            <input type="radio" name="Type" value="Salary">Salary 
            <input type="radio" name="Type" value="Resource">Resource 
            <input type="radio" name="Type" value="Other">Other <br><br>
            <button type="submit" name="add" class="btn btn-primary">Add Expense</button>
            </fieldset>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>

</html>