<?php
include '../controller/ManagerController.php';
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
$inventory=$lab->getInventory();

?>


<!DOCTYPE html>
<html>

<head>
    <style>
    .sub-entry {
      width: 50%;
      float: left;
    }
     .error {
            color: #FF0000;
        }
       <!-- .grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
        }
        .staff-list {
            grid-column: 1;
        }
        .edit-staff {
            grid-column: 2;
        }-->
		.wrap{ 
			width: 100%;
			overflow:auto;
		}
		.fleft{
			float:left;
			width:50%;
		}
		.fright{
			float:right;
			width:50%;
		}
			
        table {
    		font-family: arial, sans-serif;
   			border-collapse: collapse;
   			width: 100%;
		}

		td, th {
   			 border: 1px solid #dddddd;
   			 text-align: left;
   			 padding: 8px;
		}
		tr:nth-child(even) {
    		background-color: #dddddd;
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
            <a class="nav-link" href="#">Inventory</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="AddExpensePage.php">Add Expenses</a>
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
      <div class="row w-100 h-100 p-0 m-0">
        <div class="col-md-6">
          <h1 class="" style="">Resources List</h1>
          <div class="py-5 w-100" style="">
            <div class="container">
              <div class="row">
                <div class="col-md-12 h-100">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>Index</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Type</th>
                      </tr>
                    </thead>
                    <tbody>
  				 <?php
  				$count=0;
  				for ($count=0;$count< $inventory->numberOfResources();$count++){
  					echo "<tr>";
  					echo"<td>".$count."</td><td>".$inventory->getResource_index($count)->getName()."</td><td>".$inventory->getResource_index($count)->getQuantity()."</td>";
  					if($inventory->getResource_index($count)instanceof Supply) {
  					    echo"<td> Supply </td> ";
  					}
  					else if($inventory->getResource_index($count)instanceof Equipment) {
  					    echo "<td> Equipment </td>";
  					}
  					
  				}
  				?>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <h1 class="">Add &amp; Remove &amp; Edit Resource</h1>
          <div class="fleft">
            <form action="AddInventory.php" method="POST">
              <!--<h3>Add or Remove a staff member:</h3>-->
          		<span class="error">
                   <?php
                        $v = 0;
                        
                        //I.e. if error is set (the && is semantics due to add staff initialization to " "
                        if(isset ( $_SESSION["inventoryError"])&& ! empty ( $_SESSION["inventoryError"])){
                            echo " *Error: ". $_SESSION["inventoryError"]; //Print Error message
                        }
                        ?>
            	</span> 
              <fieldset>
                    Name:<br>
              		<input  type="text" name="Name"><br>
                    Quantity:<br>
                    <input type="number" name="AddQuantity" min="1" ><br>
                    Type:<br>
                    <input type="radio" name="type" value="Supply">Supply<br>
                    <input type="radio" name="type" value="Equipment">Equipment<br>
                    <input type="submit" name="insert" value="Add" class="btn btn-outline-primary"><br><br>
                    Name:<br>
              		<input  type="text" name="RemoveName"><br>
                     Index No. (Optional for removal): <br>
            		<input type="number" name = "id" min="0" ><br>
            		<input type="submit" name ="remove" value="Remove" class="btn btn-outline-primary"><br><br>
                    Inventory: <br>
                    <select name="Inventory">
                    <?php
  				      $count=0;
  				      for ($count=0;$count< $inventory->numberOfResources();$count++){
  				          echo "<option value=".$inventory->getResource_index($count)->getName().">".$inventory->getResource_index($count)->getName()."</option>";	
  				      }
  				    ?>
 					 </select><br>
                   Quantity:<br>
                    <input type="number" name="EditQuantity" min="1" ><br>
                    <input type="submit" name ="edit" value="Edit" class="btn btn-outline-primary">
           		</fieldset>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>

</html>