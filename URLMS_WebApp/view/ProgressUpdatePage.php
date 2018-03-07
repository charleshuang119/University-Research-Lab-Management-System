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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
  <nav class="navbar navbar-expand-md bg-secondary navbar-dark" style="">
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
            <a class="nav-link" href="FundingAccountPage.php">Funding Account</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <div class="row">
          	<form action="AddProgressUpdate.php" method="POST"> <!-- Need to Update the form to the proper action, AddProgressUpdate.php -->
          		<span class="error">
                    <?php 
                        
                        //I.e. if error is set (the && is semantics due to add staff initialization to " "
                    if(isset ($_SESSION ['errorProgress'])&& !empty ($_SESSION['errorProgress'])){
                        $mess = " \t ".$_SESSION["errorProgress"]; //Print Error message
                        echo "<div class=\"col-md-12 text-danger border border-danger bg-dark text-capitalize\" id=\"errorz\">";
                        echo "  <p class=\"\"><b>Error</b> <br> $mess </p>";
                        echo "</div>";
                        $_SESSION['errorProgress'] = ""; //Reset error Messages
                    }
                    ?>
            	</span> 
          		<fieldset>
                      <div class="form-group">
                      <select class="form-control" id="sel1" name="staff">
                        <option value="default">Select Staff Member</option>
                        <?php 
                        //Display all staff members in dropdown button
                        $count=0;
                        for ($count=0;$count< $lab->numberOfStaff();$count++){ //for each staff member
                            $first = $lab->getStaff_index($count)->getFirstName();
                            $last = $lab->getStaff_index($count)->getLastName();
                            $name = $first." ".$last;
                            $counter = $count+1; //For 1 based display
                            echo "<option value=".$count.">".$counter.": \t".$name."</option>";
                            //value passed to AddProgressUpdate.php is the staff select w/ value of index of staff array
                        }
                        ?>
                      </select>
                    </div>
                    <textarea name="message" rows="10" cols="70" placeholder="Please type the progress update here..."></textarea>
                    <br>
                    <input type="submit" name="insert" value="Add Progress Update" class="btn btn-primary"> 
             	</fieldset>
             </form>     
          </div>
          <br>
          <div class="row">
            <div class="col-md-12">
              <table class="table" id = "tabList">
                <thead>
                  <tr>
                  	<th></th><!-- This column holds view buttons and does not require a header -->
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                  <?php
                    $count2=0;
                        if($lab->hasProgressUpdates()){
              				
              				$messageList = array(); //array for use when displaying messages to side view
              				//for each progress update
              				for ($count2=0;$count2< $lab->numberOfProgressUpdates();$count2++){
              					$prog=$lab->getProgressUpdate_index($count2);
              					$messy=$prog->getDescription();
              					$messageList[$count2]=$messy;
              					//name is written in form "index.firstname lastname;"
              					$full = strstr($messy,":",true); //Search string for substring
              					$index = strstr($full,".",true);
              					$name = strstr($full,".",false);
              					$name = substr($name,1);
              					$first = strstr($name," ",true);
              					$last = strstr($name," "); 
              					$role = strstr($messy,";",true);
              					$role = strstr($role,":",false);
              					$role = substr($role,1);
              					
              				    echo "<tr>";
              				    echo "<td>";
              				    //Create view buttons
              				    $form = '<form action="ProgressUpdatePage.php" method="GET">';
                                $form = $form.' <input type="submit" name = "Row'.$count2.'" value = "View" ';
                                $form = $form.'class = "btn btn-outline-primary"></form>'; 
              					echo $form."</td>";
              					//create other table entries
                                echo"<td>".$first."</td><td>".$last."</td><td>".$role."</td>";              					/*if($lab->getStaff_index($index)->getRole()==$Director) {
              					    echo"<td> Director </td> ";
              					}
              					else if($lab->getStaff_index($index)->getRole()==$Associate) {
              					    echo "<td> Associate </td>";
              					}
              					else if($lab->getStaff_index($index)->getRole()==$Assistant){
              					    echo "<td> Assistant </td>";
              					}*/
              					echo"<td>".$prog->getDate()."</td>";//Echo the date
              					echo "</tr>";
              				}
                        }
          			?>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <textarea rows="20" cols="70" readonly>
<?php 
         	  $default  = "When a progress update in the table is selected, it will appear here.";
              $bool = 0; //boolean for seeing when to display default message
         	  //Note: variable scope for php is within file, thus variables are recognized beyond <?php tags
         	  for($j=0;$j<$count2;$j++){ //for each progress update
         	      $tag = 'Row'.$j;
         	      if(isset($_GET[$tag])){ //check if view button has been submitted 
         	          echo $messageList[$j];
         	          $bool = 1;
         	          break;
         	      }
         	  }
         	  if($bool==0){ //If no view button submitted
         	      echo $default; //Optional
         	  }
?> 	 
		  </textarea>
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