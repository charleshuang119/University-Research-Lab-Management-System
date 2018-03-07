package ca.mcgill.ecse321.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCCreateLab {
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
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		//create a lab with password
		mc.createLab("123456");
		
		//check lab with password
		assertEquals("123456",URLMSApplication.getURLMS().getSingleLab().getRootPassword());
	}

}
