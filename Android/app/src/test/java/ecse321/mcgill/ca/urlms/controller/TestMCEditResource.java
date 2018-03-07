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
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCEditResource {
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
						
				//add a supply
				String Sname ="pen";
				int Squantity= 200;		
				mc.addSupply(Squantity, Sname);
				
				//add a equipment
				String Ename ="computer";
				int Equantity= 20;		
				mc.addSupply(Equantity, Ename);
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		//edit resource quantity
		Resource resource1=URLMSApplication.getURLMS().getSingleLab().getInventory().getResource(0);
		Resource resource2=URLMSApplication.getURLMS().getSingleLab().getInventory().getResource(1);
		mc.editResource(resource1, 100);
		mc.editResource(resource2, 10);
		
		//check the quantity
		assertEquals(100,resource1.getQuantity());
		assertEquals(10,resource2.getQuantity());
		
	}

}
