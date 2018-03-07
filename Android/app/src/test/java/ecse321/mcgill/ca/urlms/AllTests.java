package ecse321.mcgill.ca.urlms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ecse321.mcgill.ca.urlms.controller.TestMCAddEquipment;
import ecse321.mcgill.ca.urlms.controller.TestMCAddExpense;
import ecse321.mcgill.ca.urlms.controller.TestMCAddStaff;
import ecse321.mcgill.ca.urlms.controller.TestMCAddSupply;
import ecse321.mcgill.ca.urlms.controller.TestMCCreateProgressUpdate;
import ecse321.mcgill.ca.urlms.controller.TestMCEditResource;
import ecse321.mcgill.ca.urlms.controller.TestMCRemoveResource;
import ecse321.mcgill.ca.urlms.controller.TestMCRemoveStaff;
import ecse321.mcgill.ca.urlms.persistence.TestPersistence;
@RunWith(Suite.class)
@SuiteClasses({ TestMCAddStaff.class, TestPersistence.class,TestMCAddEquipment.class,TestMCAddExpense.class,
	TestMCAddSupply.class,TestMCCreateProgressUpdate.class,TestMCEditResource.class,
	TestMCRemoveStaff.class,TestMCRemoveResource.class})
public class AllTests {

}
