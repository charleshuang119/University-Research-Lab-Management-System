package ca.mcgill.ecse321.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCSetPassWord {
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
		mc.createLab("123456");
		mc.loadLab();
				
		String firstName1 = "Andi Camille";
		String lastName1 = "Batie";
		String role1 = "Assistant";
		mc.addStaff(firstName1, lastName1, role1);
	}

	@After
	public void tearDown() throws Exception {
		//delete URLMS
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() {
		//check if the staff doesn't have a password
		Staff aStaff=URLMSApplication.getURLMS().getSingleLab().getStaff(0);
		assertEquals(aStaff.getPassword().length(),0);
		String passWord="1234";
		mc.setPassword(aStaff,passWord);
		assertEquals(aStaff.getPassword(),passWord);
	}

}
