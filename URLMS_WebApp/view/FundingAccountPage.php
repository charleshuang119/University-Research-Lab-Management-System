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
$fundingAccount = $lab->getFundingAccount();
$allocatedAmount = $fundingAccount->getAllocatedAmount();
$usedAmount=0;
$travelAmount=0;
$salaryAmount=0;
$resourceAmount=0;
$otherAmount=0;
$count=0;
for ($count=0;$count< $fundingAccount->numberOfExpenses();$count++){
    $aExpense=$fundingAccount->getExpense_index($count);
    $usedAmount+=$aExpense->getAmount();
}
$remainingAmount = $allocatedAmount-$usedAmount;
for ($count=0;$count< $fundingAccount->numberOfExpenses();$count++){
    $aExpense=$fundingAccount->getExpense_index($count);
    if($aExpense-> getType()=="TypeTRAVEL") {
        $travelAmount+=$aExpense->getAmount();
    }
    else if($aExpense-> getType()=="TypeSALARY") {
        $salaryAmount+=$aExpense->getAmount();
    }
    else if($aExpense-> getType()=="TypeRESOURCE"){
        $resourceAmount+=$aExpense->getAmount();
    }
    else if($aExpense-> getType()=="TypeOTHER"){
        $otherAmount+=$aExpense->getAmount();
    }
}

?>


<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-beta.1.css" type="text/css"> </head>

<body>
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
            <a class="nav-link" href="AddExpensePage.php">Add Expenses</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Funding Account</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
           
        
  <div class="py-5">
    <div class="container">
      <div class="row">

        <div class="col-md-6">
          Allocated Amount: <?php echo $allocatedAmount?><br><br>
          Remaining Amount: <?php echo $remainingAmount?>
        </div>
              <form action="setAllocatedAmount.php" method="POST">
          <span class="error">
                   <?php
                        //I.e. if error is set (the && is semantics due to add staff initialization to " "
                        if(isset ( $_SESSION["fundingError"])&& ! empty ( $_SESSION["fundingError"])){
                            echo " *Error: ". $_SESSION["fundingError"]; //Print Error message
                        }
                        ?>
            	</span> 
        	<fieldset>
          <div class="form-group"> <label>Set Allocated Amount:</label> <br>
          <input type="number" name="AllocatedAmount" step=0.01 class="form-control" min = 0> </div>
          <input type="submit" name ="set" value="Set" class="btn btn-outline-primary"><br><br>
          </fieldset>
  </form>
        <div class="col-md-6">
          <div class="card">
            <div class="card-header"> Breakdown</div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">Travel: <?php echo $travelAmount ?> </div>
                <div class="col-md-6">Resource: <?php echo $resourceAmount ?></div>
              </div>
              <div class="row">
                <div class="col-md-6">Salary: <?php echo $salaryAmount ?></div>
                <div class="col-md-6">Other: <?php echo $otherAmount ?></div>
              </div>
            </div>
          </div>
        </div>
      </div><br>
      <div class="row">
        <div class="col-md-12" style="">
          <table class="table">
            <thead>
              <tr>
                <th>Decription</th>
                <th>Amount</th>
                <th>Type</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
            <?php
          				$count=0;
          				for ($count=0;$count< $fundingAccount->numberOfExpenses();$count++){
          				    $aExpense=$fundingAccount->getExpense_index($count);
          					echo "<tr>";
          					echo"<td>".$aExpense-> getDescription()."</td><td>".$aExpense->getAmount()."</td>";
          					if($aExpense-> getType()=="TypeTRAVEL") {
          					    echo"<td> Travel </td> ";
          					}
          					else if($aExpense-> getType()=="TypeSALARY") {
          					    echo "<td> Salary </td>";
          					}
          					else if($aExpense-> getType()=="TypeRESOURCE"){
          					    echo "<td> Resource </td>";
          					}
          					else if($aExpense-> getType()=="TypeOTHER"){
          					    echo "<td> Other </td>";
          					}
          					echo"<td>".$aExpense-> getDate()."</td>";
          				}
          	?>             
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
  <div id="UMS_TOOLTIP" style="position: absolute; cursor: pointer; z-index: 2147483647; background: transparent; top: -100000px; left: -100000px;"></div>
</body>

</html>