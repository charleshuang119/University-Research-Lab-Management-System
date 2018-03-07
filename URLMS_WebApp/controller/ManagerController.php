<?php

if(!isset($_SESSION)){ //Set Session only once
    session_start();
}

$my_dir = dirname(__FILE__);
require_once $my_dir . '/../persistence/PersistenceURLMS.php';
require_once $my_dir . '/../model/Staff.php';
require_once $my_dir . '/../model/URLMS.php';
require_once $my_dir . '/../model/Lab.php';
require_once $my_dir . '/../model/Associate.php';
require_once $my_dir . '/../model/Assistant.php';
require_once $my_dir . '/../model/Director.php';
require_once $my_dir . '/../model/Role.php';
require_once $my_dir . '/../model/Equipment.php';
require_once $my_dir . '/../model/Expense.php';
require_once $my_dir . '/../model/FundingAccount.php';
require_once $my_dir . '/../model/Inventory.php';
require_once $my_dir . '/../model/ProgressUpdate.php';
require_once $my_dir . '/../model/Resource.php';
require_once $my_dir . '/../model/Supply.php';




class ManagerController
{
	public function __construct()
	{ //On construction

	    $pm = new PersistenceURLMS();
	    $urlms = $pm->loadDataFromStore(); //Return a new URLMS with a Lab from memory or make a new one
	    $lab = $urlms->getSingleLab();
	    $_SESSION["persistence"] = $pm;
	    $_SESSION["currURLMS"] = $urlms;
	    $_SESSION["currLab"] = $lab;
	    $_SESSION["currStaff"] = $lab->getStaff();
	    $_SESSION["currInventory"] = $lab->getInventory();
	    //$_SESSION["currResources"] =  $_SESSION["currInventory"]->getResources();

	}
	
    /*
     * Inputs are first name, last name, role
     * Inputs received via Submit button from index
     * 
     */
	public function addStaffMember($FirstName, $LastName, $Role) {
		//Instantiate local Variables
	    $Fname = $FirstName;
	    $Lname = $LastName;
	    $role = $Role;
	    
	    //1. First Validate inputs
	    
		if ($Fname == null || strlen($Fname) == 0) {//if empty first name field
		    if ($Lname == null || strlen($Lname) == 0) {//if empty first and last name field
		        throw new Exception("Both First and Last name cannot be empty!");
		    } else{//just empty first name field
			    throw new Exception("First name cannot be empty!");
			}
		} 
		elseif($Lname == null || strlen($Lname) == 0){ //empty last name
		    throw new Exception("Last name cannot be empty!");
		}
		elseif(is_numeric($Fname) || is_numeric($Lname)){ //Numbers in name
		    throw new Exception("Name cannot include numbers");
		}
		else { //Proper names
		   
			// 2. Load all of the data
                //Moved to constructor

			// 3. Add the new Staff Member

			/*array_push($_SESSION["staffLastName"],$LastName);
			if(isset($_SESSION["currLab"])){
			    echo "curr lab is set at least";
			    echo serialize($_SESSION["currLab"]);
			}*/
			    
			$newStaff = new Staff($Fname,$Lname,$_SESSION["currLab"]);//Create new staff object
			//Assign Roles
			if(strcmp($role, "Director")==0){ 
			    if($_SESSION["currLab"]->hasDirector()){
			        throw new Exception("Lab already has a director associated to it");
			    }else{
			        $d = new Director();
			        $newStaff->setRole($d);
			        $_SESSION["currLab"]->setDirector($newStaff);
			    }
			}else if(strcmp($role, "Associate")==0){
			    $a1 = new Associate();
			    $newStaff->setRole($a1);
			}else if(strcmp($role, "Assistant")==0){
			    $a2 = new Assistant();
			    $newStaff->setRole($a2);
			}
			
			$_SESSION["currLab"]->addStaff($newStaff);
			
			// 4. Write all of the data
			$_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
		}
	}
	/*
	 * Takes as input Index number, first name, and last name
	 * Inputs passed via "remove staff" submit button on index page
	 */
	public function removeStaffMember($indexNo,$FirstName, $LastName){
	    $Director = new Director();
	    if($indexNo!=null && !is_numeric($indexNo)){
	        throw new Exception("Index number must be a number");
	    }else if($_SESSION["currLab"]->numberOfStaff()==0){
	        throw new Exception("No staff member exists in this lab");
	    }else if($indexNo!=null && is_numeric($indexNo)){ //If valid index number suplied
	        $staffer = $_SESSION["currLab"]->getStaff_index($indexNo);//Get staff object from current lab
	        if($staffer->getRole()==$Director){	            
	            $_SESSION["currLab"]->setDirector(null);
	        }
	        $staffer->delete();
	    }else{//no index number supplied at all
	        
	        $staffList = $_SESSION["currLab"]->getStaff(); //Get staff list from name
	        $counter = 0;
	        $removeList = array(); //An array of the staff objects to be removed
	        foreach($staffList as $staffer){ //For each staff member     	
	            if ($staffer->getFirstName() == $FirstName && $staffer->getLastName() == $LastName ){
	               	$removeList[$counter] = $staffer; //Add to removeList iff first and last name matches
	               	if($staffer->getRole()==$Director){
	               	    $_SESSION["currLab"]->setDirector(null);
	               	}
	                $counter++;
	            }
	        }
	        if($counter == 1){ //Unique staffmember with name
	            $removeList[0]->delete();
	        } elseif($counter>1){  //I.e. More than 1 person with the same name
	           throw new Exception("Multiple staff members with the same Name, Please input index No in order to remove correct staff member"); 
	        } elseif($counter == 0){ //No staff members with that name
	            throw new Exception("No staff member exists with that name, Either check name input or input index No.");
	        }
	    }
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
	}
	
	
	/*
	 * Inputs are  name, quantity, type
	 * Inputs received via Submit button from inventoryPage
	 *
	 */
	public function addResource($name, $quantity, $type) {
 	    //Instantiate local Variables
	    $resourcesList = $_SESSION["currLab"]->getInventory()->getResources();
        
	    if($_SESSION["currInventory"]->numberOfResources()>1&&$resourcesList!=null){
	        foreach ($resourcesList as $re ){
	            if($re->getName()==$name) {
	                throw new Exception("The resource already exists in the inventory!");
	            }
	          }     
	    }
	    
	    //1. First Validate inputs
	    if ($name == null || strlen($name) == 0) {//if empty  name field
	        
	        throw new Exception("Name of inventory cannot be empty!");
	        
	    }	   

	    else if($quantity == null || strlen($quantity)==0){ //Numbers in name
	        
	        throw new Exception("Quantity cannot be empty!");
	        
	    }

	    else { 

	        
	        //Check resource type
	        if(strcmp($type, "Supply")==0){
	           //create new resource according to different type
	            $newSupply = new Supply($quantity,$name,$_SESSION["currInventory"]);

	        }else if(strcmp($type, "Equipment")==0){	          
	            $newEquipment = new Equipment($quantity,$name,$_SESSION["currInventory"]);//Create new staff object
	        }else{
	            throw new Exception("Must select a Type");
	        }
	        // 4. Write all of the data
	        $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
	    }
	}
	

	public function removeResource($index, $name) {
	    $resourcesList = $_SESSION["currLab"]->getInventory()->getResources();
	    $counter = 0;
	    if($index==null && $name==null ) {
	        throw new Exception("Name of the resource cannot be empty!");
	    }
	    else if ($index!=null&&$index>$_SESSION["currLab"]->getInventory()->numberOfResources()){
	        throw new Exception("Index number doesn't exist! ");
	    }
	    else if ($_SESSION["currLab"]->getInventory()->numberOfResources()==0){
	        throw new Exception("No resource exists with that name in the inventory! ");
	    }
	    else if($index!=null&&$index>=0&&$index<=$_SESSION["currLab"]->getInventory()->numberOfResources()){
	        $aResource = $_SESSION["currInventory"]->getResource_index($index);
	        $aResource->delete();        
	    }
	    else {
	     
	        foreach($resourcesList as $aResource){
	            if($aResource->getName()==$name) {
	                $aResource->delete();
	                $counter++;
	            }
	        }
	        if($counter==0) {
	            throw new Exception("No resource exists with that name in the inventory!");
	        }
	    }
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
	}
	
	public function editResource($name, $quantity) {
	    $resourcesList = $_SESSION["currLab"]->getInventory()->getResources();
	    $counter = 0;
	    if($_SESSION["currInventory"]->numberOfResources()==0&&$resourcesList==null){
	       throw new Exception("No resource exists in the inventory!");  
	    }
	    else {
	        foreach($resourcesList as $aResource){
	            if($aResource->getName()==$name){
	                $aResource->setQuantity($quantity);
	            }
	        }
	    }
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);	    
	}
	
	public function addExpense($description,$amount,$type,$staffLastName) {
	    $fundingAccount = $_SESSION["currLab"]->getFundingAccount();
	    $staffList = $_SESSION["currLab"]->getStaff(); 
	    if ($description == null || strlen($description) == 0) {//if empty  name field
	        
	        throw new Exception("Description cannot be empty!");
	        
	    }
	    
	    else if($amount == null || strlen($amount)==0){ //Numbers in name
	        
	        throw new Exception("Amount cannot be empty!");
	        
	    }
	    
	    else{
	        $date = date("m/d/Y",time());
	        $aExpense=new Expense($description,$amount,$date,$fundingAccount);
	        if($type=="Travel"){
	           $aExpense->setType("TypeTRAVEL") ;
	        }
	        else if($type =="Salary"){
	            $aExpense->setType("TypeSALARY");
	        }
	        else if($type =="Resource"){
	            $aExpense->setType("TypeRESOURCE");
	        }
	        else if($type == "Other"){
	            $aExpense->setType("TypeOTHER");
	        }
	        foreach($staffList as $aStaff){
	            if($aStaff->getLastName()==$staffLastName){
	                $aStaff->addExpense($aExpense);
	            }
	        }

	    }
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
	}
	
	public function setAllocatedAmount($amount){
	    $fundingAccount = $_SESSION["currLab"]->getFundingAccount();
	    $fundingAccount->setAllocatedAmount($amount);
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);
	}

	public function addProgress($staff,$Message){ //$staff is index of staff array
	    $date = date("m/d/Y", time());//Format date into m/d/Y based of UNIX time
	    $progStaff = $_SESSION["currLab"]->getStaff_index($staff);
	    $first = $progStaff->getFirstName();
	    $last = $progStaff->getLastName();
	    $role = $progStaff->getRole();
	    $role = get_class($role);
	    $progStaffName = $first." ".$last.": ";
	    $message = $staff.".".$progStaffName.$role.";\n".$date."\n \n".$Message; //To Be placed inside of the view page so that the view shows staff member as well as message
	    new ProgressUpdate($message,$date,$_SESSION["currLab"]);
	    $_SESSION["persistence"]->writeDataToStore($_SESSION["currLab"]);//Save to persistence
	}
	

}
 
?>
