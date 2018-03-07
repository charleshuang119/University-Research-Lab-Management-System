package ca.mcgill.ecse321.urlms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.urlms.controller.TestMCAddEquipment;
import ca.mcgill.ecse321.urlms.controller.TestMCAddExpense;
import ca.mcgill.ecse321.urlms.controller.TestMCAddStaff;
import ca.mcgill.ecse321.urlms.controller.TestMCAddSupply;
import ca.mcgill.ecse321.urlms.controller.TestMCCreateLab;
import ca.mcgill.ecse321.urlms.controller.TestMCCreateProgressUpdate;
import ca.mcgill.ecse321.urlms.controller.TestMCEditResource;
import ca.mcgill.ecse321.urlms.controller.TestMCRemoveResource;
import ca.mcgill.ecse321.urlms.controller.TestMCRemoveStaff;
import ca.mcgill.ecse321.urlms.persistence.TestPersistence;

@RunWith(Suite.class)
@SuiteClasses({ TestMCAddStaff.class, TestPersistence.class,TestMCAddEquipment.class,TestMCAddExpense.class,
	TestMCAddSupply.class,TestMCCreateLab.class,TestMCCreateProgressUpdate.class,TestMCEditResource.class,
	TestMCRemoveStaff.class,TestMCRemoveResource.class })
public class AllTests {

}
