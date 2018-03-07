<?php
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
require_once $my_dir . '/../persistence/PersistenceURLMS.php';
class PersistenceURLMSTest extends PHPUnit_Framework_TestCase
{
	protected $pm;
	protected function setUp()
	{
		$this->pm = new PersistenceURLMS();
		
	}
	protected function tearDown()
	{
	}
	public function testPersistence()
	{
	    setUp();
		// 1. Create test data
	    $urlms = new URLMS();
	    $lab = new Lab();
	    $urlms -> setSingleLab($lab);
	    $lab = $urlms->getSingleLab();
	   
	    //Create Staff
	    $s1 = new Staff("Frank","Miller",$lab);
	    $a1 = new Associate();
	    $s1->setRole($a1);
	    
	    $s2 = new Staff("Allan","Moore",$lab);
	    $s2->setRole(new Assistant());
	    
	    $s3 = new Staff("Steve","Ditko",$lab);
	    $s3->setRole(new Director());
	    
	    
	    //Register staff to lab
	    $lab->addStudent($s1);
	    $lab->addStudent($s2);
	    $lab->addStudent($s3);
	    
		// 2. Write all of the data
	    $this->pm->writeDataToStore($rm);
	   
		// 3. Clear the data from memory
		$urlms->delete();
	
		// 4. Load it back in
		$lab = $this->pm->loadDataFromStore();
		// 5. Check that we got it back
		$this->assertEquals(3, count($lab->getStaff())); //Check 3 staff members to lab
		
		$myStaff = $rm->getStaff_index(0); //get first staff member
		$this->assertEquals("Frank", $myStaff->getFirstName()); //Check if first name matches
		$this->assertEquals("Miller", $myStaff->getLastname()); //Check if last name matches
		$this->assertEquals(new Associate(),$myStaff->getRole()); //Check if Role matches  
		
		
		$myStaff = $rm->getStaff_index(1); //get second staff member
		$this->assertEquals("Allan", $myStaff->getFirstName()); //Check if first name matches
		$this->assertEquals("Moore", $myStaff->getLastname()); //Check if last name matches
		$this->assertEquals(new Assistant(),$myStaff->getRole()); //Check if Role matches  
		
		
		$myStaff = $rm->getStaff_index(2); //get third staff member
		$this->assertEquals("Frank", $myStaff->getFirstName()); //Check if first name matches
		$this->assertEquals("Miller", $myStaff->getLastname()); //Check if last name matches
		$this->assertEquals(new Director(),$myStaff->getRole()); //Check if Role matches  
		
		
		tearDown();
	}
}
?>