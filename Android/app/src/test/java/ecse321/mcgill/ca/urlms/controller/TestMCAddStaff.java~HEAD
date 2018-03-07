package ecse321.mcgill.ca.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Role;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCAddStaff {
	private ManagerController mc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// load urlms 
		PersistenceXStream.initializeModelManager("output" + File.pathSeparator + "testMC.xml");		
		URLMSApplication.load();
	}

	@Before
	public void setUp() throws Exception {
		// create a new manger controller and create a lab to check if we can add staff through the
		// controller
		mc = new ManagerController();
		mc.createLab();
		mc.loadLab();
	}

	@Test
	public void test() throws InvalidInputException {
		
		// check if the staff is empty in the new lab
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getStaff().size());
		
		
		// add staffs in the lab
		String firstName1 = "Andi Camille";
		String lastName1 = "Batie";
		String role1 = "Assistant";
		
		String firstName2 = "Charles";
		String lastName2 = "Huang";
		String role2 = "Associate";
		
		String firstName3 = "Li";
		String lastName3 = "Zhang";
		String role3 = "Director";
		
		
		mc.addStaff(firstName1, lastName1, role1);
		mc.addStaff(firstName2, lastName2, role2);
		mc.addStaff(firstName3, lastName3, role3);
		
		//check the staff number is 3
		assertEquals(3, URLMSApplication.getURLMS().getSingleLab().getStaff().size());
		
		checkResultStaff(firstName1, lastName1,"Assistant",0);
		checkResultStaff(firstName2, lastName2,"Associate",1);
		checkResultStaff(firstName3, lastName3,"Director",2);

	}

	private void checkResultStaff(String firstName, String lastName, String role,int staffIndex) {
		
		Staff aStaff=URLMSApplication.getURLMS().getSingleLab().getStaff(staffIndex);
		//check the first name
		assertEquals(firstName, aStaff.getFirstName());
		//check the last name
		assertEquals(lastName, aStaff.getLastName());
		//check the role
		if (role=="Assistant") {
			assertTrue(aStaff.getRole() instanceof Assistant);
		}
		else if(role=="Associate") {
			assertTrue(aStaff.getRole() instanceof Associate);
		}
		else if (role=="Director") {
			assertTrue(aStaff.getRole() instanceof Director);
		}
	}

	@After
	public void tearDown() throws Exception {
		//delete URLMS
		URLMSApplication.getURLMS().delete();
	}

}
