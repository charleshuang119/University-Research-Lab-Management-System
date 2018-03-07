package ecse321.mcgill.ca.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCRemoveStaff {
	private ManagerController mc;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// load urlms
		PersistenceXStream.initializeModelManager("output" + File.pathSeparator + "testMC.xml");		
		URLMSApplication.load();
	}

	@Before
	public void setUp() throws Exception {
		// create a new manger controller and create a lab
		mc = new ManagerController();
		mc.createLab();
		mc.loadLab();
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
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		//check if the staff number in the lab is 3
		assertEquals(3, URLMSApplication.getURLMS().getSingleLab().getStaff().size());
		
		Staff Staff1=URLMSApplication.getURLMS().getSingleLab().getStaff(0);
		Staff Staff2=URLMSApplication.getURLMS().getSingleLab().getStaff(1);
		Staff Staff3=URLMSApplication.getURLMS().getSingleLab().getStaff(2);
		
		//test if we can remove all three staffs
		mc.removeStaff(Staff1);
		mc.removeStaff(Staff2);
		mc.removeStaff(Staff3);
		//after remove a staff, check if there is 0 staff in the lab
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getStaff().size());			
	}

}
