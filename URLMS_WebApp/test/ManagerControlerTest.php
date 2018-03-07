<?php declare(strict_types=1);
$my_dir = dirname(__FILE__); 

require_once $my_dir . '/../model/URLMS.php';
require_once $my_dir . '/../model/Lab.php';
require_once $my_dir . '/../model/FundingAccount.php';
require_once $my_dir . '/../model/Inventory.php';
require_once $my_dir . '/../model/Lab.php';
require_once $my_dir . '/../model/Staff.php';
require_once $my_dir . '/../model/Role.php';
require_once $my_dir . '/../model/Associate.php';
require_once $my_dir . '/../model/Assistant.php';
require_once $my_dir . '/../model/Director.php';
require_once 'PHPUnit/Autoload.php';
//use PHPUnit_Framework_Testcase;
//ini_set('include_path', '/usr/lib/php:.');
require_once $my_dir . '/../persistence/PersistenceURLMS.php';

class ManagerControllerTest extends PHPUnit_Framework_TestCase
{
	protected $pm;
	protected $urlms;
	protected $lab;


	/**
	 * {@inheritDoc}
	 * @see PHPUnit_Framework_TestCase::setUp()
	 */
	protected function setUp()
	{
		$this->pm = new PersistenceURLMS();
		$this->urlms = new URLMS();
		
		$this->lab = new Lab();
		$urlms -> setSingleLab($lab);
		$lab = $urlms->getSingleLab();
		
		/*$_SESSION["persistence"] = $this->pm;
		$_SESSION["currURLMS"] = $this->urlms;
		$_SESSION["currLab"] = $this->lab;
		$_SESSION["currStaff"] = $this->lab->getStaff();
		$_SESSION["currInventory"] = $this->lab->getInventory();*/
		$mc = new ManagerController();
		
	}

	/**
	 * {@inheritDoc}
	 * @see PHPUnit_Framework_TestCase::tearDown()
	 */
	protected function tearDown()
	{
	    $this->urlms->delete();
	    $this->lab=null;
	    $this->urlms=null;
        //pm
	}

	public function mainTester(){
	    setUp();
	    
	    testAddStaff();
	    
	    tearDown();
	}
	
	/**
	 * @test
	 */
	public function testAddStaff(){
	    //setUp();
/* 	    // 1. Create test data
	    $urlms = new URLMS();
	    
	    $this->lab = new Lab();
	    $urlms -> setSingleLab($this->lab);
	    $this->lab = $urlms->getSingleLab(); */
	    
	    $firstName1 = "Frank";
	    $lastName1 = "Miller";
	    $role1="Director";
	    
	    $firstName2 = "Allan";
	    $lastName2 = "Moore";
	    $role2 = "Assistant";
	    
	    $firstName3 = "Steve";
	    $lastName3 = "Ditko";
	    $role3 = "Associate";

	    
	    //Register staff to lab
	    $mc->addStaffMember($firstName1,$lastName1,$role1);
	    $mc->addStaffMember($firstName2,$lastName2,$role2);
	    $mc->addStaffMember($firstName3,$lastName3,$role3);
	    

	    
	    // 5. Check that we got it back
	    $this->assertEquals(3, count($_SESSION["currLab"]->getStaff())); //Check 3 staff members to lab
	    
	    
	    $myStaff = $_SESSION["currLab"]->getStaff_index(0); //get first staff member
	    $this->assertEquals("Frank", $myStaff->getFirstName()); //Check if first name matches
	    $this->assertEquals("Miller", $myStaff->getLastname()); //Check if last name matches
	    $this->assertEquals(new Director(),$myStaff->getRole()); //Check if Role matches
	    
	    
	    $myStaff = $_SESSION["currLab"]->getStaff_index(1); //get second staff member
	    $this->assertEquals("Allan", $myStaff->getFirstName()); //Check if first name matches
	    $this->assertEquals("Moore", $myStaff->getLastname()); //Check if last name matches
	    $this->assertEquals(new Assistant(),$myStaff->getRole()); //Check if Role matches
	    
	    $myStaff = $_SESSION["currLab"]->getStaff_index(2); //get third staff member
	    $this->assertEquals("Steve", $myStaff->getFirstName()); //Check if first name matches
	    $this->assertEquals("Ditko", $myStaff->getLastname()); //Check if last name matches
	    $this->assertEquals(new Associate(),$myStaff->getRole()); //Check if Role matches
	    
	    
	    tearDown();
	}
	
	/**
	 * @test
	 * @depends testAddStaff
	 */
    public function testRemoveStaff(){
        testAddStaff();

        $staff = $_SESSION["currLab"]->getStaff();
        $staffCount = count($staff); //Initial number of staff
        for($i=0; $i<$staffCount-1; $i++){ //For each existing staffmember
            $mc->removeStaffMember($staff[$i],"","");
            $this->assertEquals($staffCount-($i+1), count( $_SESSION["currLab"]->getStaff()));
        }
            $mc->removeStaffMember(NULL,"Steve","Ditko");
            $this->assertEquals(0, count($_SESSION["currLab"]->getStaff()));
        
    }

    /**
     * @test
     * @depends testAddStaff
     */    
    public function testAddProgressUpdates(){
        testAddStaff();

        $this->assertEquals(0, $_SESSION["currLab"]->numberOfProgressUpdates());
        
        $testMessage = "This is a tester Update";
        
        $mc->addProgress(0,$testMessage);

        $this->assertEquals(1, $_SESSION["currLab"]->numberOfProgressUpdates());
        
        $update = $_SESSION["currLab"]->getProgressUpdate_index(0);
        $this->assertEquals($testMessage,$update->getDescription());
        
        $testMessage2 = "Multiple Messages Per Staff Member";
        $mc->addProgress(1,$testMessage2);
        
        $this->assertEquals(2, $_SESSION["currLab"]->numberOfProgressUpdates());
        
        $update = $_SESSION["currLab"]->getProgressUpdate_index(1);
        $this->assertEquals($testMessage2,$update->getDescription());
    }
    
    /**
     * @test
     */
    public function testAddResource(){
        
        $this->assertEquals(0,$_SESSION["currLab"]->getInventory()->numberOfResources());
        
        $mc->addResource("testSupply",5,"Supply");
        $this->assertEquals(1,$_SESSION["currLab"]->getInventory()->numberOfResources());
        
        $mc->addResource("testEquipment",5,"Equipment");
        $this->assertEquals(2,$_SESSION["currLab"]->getInventory()->numberOfResources());
        
        $resources = $_SESSION["currLab"]->getInventory()->getResources();
        $currResource = $resources[0];
        $this->assertEquals("testSupply",$currResource->getName());
        $this->assertEquals(5,$currResource->getQuantity());
        
        $currResource = $resources[1];
        $this->assertEquals("testEquipment",$currResource->getName());
        $this->assertEquals(5,$currResource->getQuantity());
    }
    
    /**
     * @test
     * @depends testAddResource
     */
    public function testEditResource(){
        testAddResources();
        $this->assertEquals(2,$_SESSION["currLab"]->getInventory()->numberOfResources());
        
        $resource=$_SESSION["currLab"]->getInventory()->getResource_index(0);
        $resourceName = $resource->getName();
        
        $this->assertEquals(5,$resource->getQuantity()); //Double check resource was set to quantity of 5
        $mc->editResource($resourceName,1); //edit it to 1
        $resource=$_SESSION["currLab"]->getInventory()->getResource_index(0);
        $this->assertEquals(1,$resource->getQuantity()); //Check it was properly edited
        
    }
    
    
    /**
     * @test
     * @depends testAddResource
     */
    public function testRemoveResource(){
        testAddResource();
        $this->assertEquals(2,$_SESSION["currLab"]->getInventory()->numberOfResources());
        $mc->removeResource(1,"");
        $this->assertEquals(1,$_SESSION["currLab"]->getInventory()->numberOfResources());
        $mc->removeResource(NULL,"testSupply");
        $this->assertEquals(1,$_SESSION["currLab"]->getInventory()->numberOfResources());
        
    }
    
    /**
     * @test
     * @depends testAddStaff
     */
    public function addExpense(){
        testAddStaff();
        $this->assertEquals(0, $_SESSION["currLab"]->getFundingAccount()->numberOfExpenses());
        $lastName = $_SESSION["currLab"]->getStaff_index(0)->getLastName();
        $description1="Travel to USA";
        $amount1=5000;
        $type1="Travel";
        
        $description2="Salary for Charles";
        $amount2=200000;
        $type2="Salary";
        
        $description3="Bought a computer";
        $amount3=1100;
        $type3="Resource";
        
        $description4="Maintenance";
        $amount4=2000;
        $type4="Supplies";
        
        $mc->addExpense($description1, $amount1, $type1,$lastName);
        $mc->addExpense($description2, $amount2, $type2,$lastName);
        $mc->addExpense($description3, $amount3, $type3,$lastName);
        $mc->addExpense($description4, $amount4, $type4,$lastName);
        
        $this->assertEquals(0, $_SESSION["currLab"]->getFundingAccount()->numberOfExpenses());
        $expenseList=$_SESSION["currLab"]->getFundingAccount()->getExpenses();
        
        searchStaff($lastName);
        
        $this->assertEquals($description1,$expenseList[0]->getDescription());
        $this->assertEquals($amount1,$expenseList[0]->getAmmount());
        $this->assertEquals($type1,$expenseList[0]->getType());
        
        $this->assertEquals($description2,$expenseList[1]->getDescription());
        $this->assertEquals($amount2,$expenseList[1]->getAmmount());
        $this->assertEquals($type2,$expenseList[1]->getType());
        
        $this->assertEquals($description3,$expenseList[2]->getDescription());
        $this->assertEquals($amount3,$expenseList[2]->getAmmount());
        $this->assertEquals($type3,$expenseList[2]->getType());
        
        $this->assertEquals($description4,$expenseList[3]->getDescription());
        $this->assertEquals($amount4,$expenseList[3]->getAmmount());
        $this->assertEquals($type4,$expenseList[3]->getType());
        
    }
        
    public function searchStaff($LastName){
        $staffList = $_SESSION["currLab"]->getStaff();
        foreach($staffList as $aStaff){
            if($aStaff->getLastName()==$staffLastName){
                $this->assertTrue($aStaff->hasExpenses());
            }
        }
    }
}
?>