package ca.mcgill.ecse321.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCCreateProgressUpdate {
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
		
		//add a new staff
		String sFirstName="Charles";
		String sLastName="Huang";
		String sRole="Director";
		mc.addStaff(sFirstName, sLastName, sRole);
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		
		// check if the progressUpdate is empty in the new lab
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getProgressUpdates().size());
		
		String content1="Staff Charles has cleaned the washroom";
		String content2="aaaaaaaa";
		Staff aStaff=URLMSApplication.getURLMS().getSingleLab().getStaff(0);
		//add progressUpdate
		mc.createProgressUpdate(content1, aStaff);
		mc.createProgressUpdate(content2, aStaff);
		
		// check if the number of progressUpdate is 2
		assertEquals(2, URLMSApplication.getURLMS().getSingleLab().getProgressUpdates().size());
		
		//check description
		assertEquals(content1,aStaff.getProgressUpdate(0).getDescription());
		assertEquals(content2,aStaff.getProgressUpdate(1).getDescription());
		
	}

}
