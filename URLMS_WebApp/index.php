<?php

$my_dir = dirname(__FILE__);
require_once './persistence/PersistenceURLMS.php';
require_once './model/Staff.php';
require_once './model/URLMS.php';
require_once './model/Lab.php';
require_once './model/Associate.php';
require_once './model/Assistant.php';
require_once './model/Director.php';
require_once './model/Role.php';
include './controller/ManagerController.php';

if(!isset($_SESSION["currURLMS"])){ //!=nll
    $pm = new PersistenceURLMS();
    $urlms = $pm->loadDataFromStore(); //Return a new URLMS with a Lab from memory or make a new one
    $lab = $urlms->getSingleLab();
    $_SESSION["persistence"] = $pm;
    $_SESSION["currURLMS"] = $urlms;
    $_SESSION["currLab"] = $lab;
    $_SESSION["currStaff"] = $lab->getStaff();
    $_SESSION["staffLastName"]=array();
    $_SESSION["staffFirstName"]=array();
    $_SESSION["numOfStaff"]=$lab->numberOfStaff();
    $ct1=0;
    $ct2=0;
    
    
    foreach($_SESSION["currStaff"] as $stf){
        $_SESSION["staffLastName"][$ct1]=$stf->getLastName();
        $ct1++;
    }
    
    foreach($_SESSION["currStaff"] as $stf){
        $_SESSION["staffFirstName"][$ct2]=$stf->getFirstName();
        $ct2++;
    }
}
?>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-beta.1.css" type="text/css">
  <title>URLMS</title>
  <meta name="description" content="For all of your managerial needs">
</head>
<body>
  <div class="py-5 h-25">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="text-center text-primary display-4" id="main-title">University Research Lab Management System</h1><br>
          <center><img src="https://www.freelogoservices.com/api/main/images/1j+ojl1KOMkX9WyofBe43D6kjfCFrxZKnhfEwXs1M3EMoAJtlSEvgfto...P8..."></center>
        </div>
      </div>
    </div>
  </div>
  <nav class="navbar navbar-expand-md bg-primary navbar-dark">
    <div class="container">
      <h3 style="color:white;">Home</h3>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
      <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
        <ul class="navbar-nav">
          <li class="nav-item"><a href="AddStaffPage.php">Staff</a></li>
          <li class="nav-item"><a href="ProgressUpdatePage.php">Progress Updates</a></li>
          <li class="nav-item"><a href="InventoryPage.php">Inventory</a></li>
          <li class="nav-item"><a href="AddExpensePagephp">Add Expenses</a></li>
          <li class="nav-item"><a href="FundingAccountPage.php">Funding Account</a></li>
        </ul>
        <!-- <a class="btn navbar-btn btn-primary ml-2 text-white"><i class="fa d-inline fa-lg fa-user-circle-o"></i> Sign in</a> -->
      </div>
    </div>
  </nav>
  
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-3" style="">
          <div class="card" style="">
            <div class="card-header"><b>Staff List</b></div>
            <div class="card-body">
              <h5 contenteditable="true">View &amp; Edit Staff</h5><br>
              <!--  <h6 class="text-muted">Subtitle</h6>-->
              <a class="btn btn-primary" href="/P11/URLMS_WebApp/view/AddStaffPage.php" id="staff_Button">View</a>
            </div>
          </div>
        </div>
        <div class="col-md-3" style="">
          <div class="card">
            <div class="card-header"><b>Progress Updates</b></div>
            <div class="card-body">
              <h5>View &amp; Create<br>Progress Updates</h5>
              <!--  <h6 class="text-muted">Subtitle</h6>-->
              <a class="btn btn-primary" href="/P11/URLMS_WebApp/view/ProgressUpdatePage.php" id="Prog_Button">View <!-- TODO: update href -->
                <br>
              </a>
            </div>
          </div>
        </div>
        <div class="col-md-3" style="">
          <div class="card">
            <div class="card-header"><b>Inventory</b></div>
            <div class="card-body">
              <h5>Add or Remove &amp;<br>Edit Resources</h5>
              <!--  <h6 class="text-muted">Subtitle</h6>-->
             <a class="btn btn-primary" href="/P11/URLMS_WebApp/view/InventoryPage.php" id="staff_Button">View</a>
            </div>
          </div>
        </div>
        <div class="col-md-3" style="">
          <div class="card">
            <div class="card-header"><b>Finances</b></div>
            <div class="card-body">
              <h5>Add an Expense or View Funding Account</h5>
              <!--  <h6 class="text-muted">Subtitle</h6>-->
              <a class="btn btn-primary" href="/P11/URLMS_WebApp/view/AddExpensePage.php" id="staff_Button">Add Expense</a>
              <a class="btn btn-primary" href="/P11/URLMS_WebApp/view/FundingAccountPage.php" id="staff_Button">View</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>

</html>