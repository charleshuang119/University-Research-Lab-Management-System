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
$Director = new Director();
$Assistant = new Assistant();
$Associate = new Associate();

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
            <a class="nav-link" href="#">Staff</a>
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
          <h1 class="" style="">Staff List</h1>
          <div class="py-5 w-100" style="">
            <div class="container">
              <div class="row">
                <div class="col-md-12 h-100">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>index</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                      </tr>
                    </thead>
                    <tbody>
  					<?php
          				$count=0;
          				for ($count=0;$count< $lab->numberOfStaff();$count++){
          					echo "<tr>";
          					echo"<td>".$count."</td><td>".$lab->getStaff_index($count)->getFirstName()."</td><td>".$lab->getStaff_index($count)->getLastName()."</td>";
          					if($lab->getStaff_index($count)->getRole()==$Director) {
          					    echo"<td> Director </td> ";
          					}
          					else if($lab->getStaff_index($count)->getRole()==$Associate) {
          					    echo "<td> Associate </td>";
          					}
          					else if($lab->getStaff_index($count)->getRole()==$Assistant){
          					    echo "<td> Assistant </td>";
          					}
          					echo "</tr>";
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
          <h1 class="">Add &amp; Remove Staff</h1>
          <div class="fleft">
            <form action="AddStaff.php" method="POST">
              <!--<h3>Add or Remove a staff member:</h3>-->
          		<span class="error">
                    <?php 
                    $v = 0;
                        
                        //I.e. if error is set (the && is semantics due to add staff initialization to " "
                    if(isset ($_SESSION ['errorStaffName'])&& ! empty ($_SESSION['errorStaffName'])){
                        $mess = " \t ".$_SESSION["errorStaffName"]; //Print Error message
                        echo "<div class=\"col-md-12 text-danger border border-danger bg-dark text-capitalize\" id=\"errorz\">";
                        echo "  <p class=\"\"><b>Error</b> <br> $mess </p>";
                        echo "</div>";
                        $_SESSION['errorStaffName'] = ""; //Reset error Messages
                    }
                    ?>
            	</span> 
              <fieldset>
              	<legend></legend> <b>First Name:</b>
                <br>
                <input type="text" name="firstname" class="form-control">
                <br> <b>Last Name:</b>
                <br>
                <input type="text" name="lastname" class="form-control">
                <br>
                <b>Role:</b>
                <br>
                <input type="radio" name="role" value="Director" >Director
                <br>
                <input type="radio" name="role" value="Associate">Associate
                <br>
                <input type="radio" name="role" value="Assistant">Assistant
                <br><br>
                <input type="submit" name="insert" value="Add" class="btn btn-outline-primary">
                <br>
                <br> <b>Index No.</b> <i>(Optional for removal)</i>
                <br>
                <input type="text" name="id" class="form-control"><br>
                <input type="submit" name="remove" value="Remove" class="btn btn-outline-primary">
                <br>
                
           		</fieldset>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>

</html>