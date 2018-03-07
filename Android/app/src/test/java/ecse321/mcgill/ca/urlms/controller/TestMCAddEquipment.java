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
import ca.mcgill.ecse321.urlms.model.Equipment;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCAddEquipment {
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
		
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		// check if the resource is empty in the new lab
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getInventory().getResources().size());
		
		//add a equipment	
		String name ="Computer";
		int quantity= 20;			
		mc.addEquipement(quantity, name);
		
		// check if the number of resource is 1 in the lab
		assertEquals(1, URLMSApplication.getURLMS().getSingleLab().getInventory().getResources().size());
		
		Resource aEquipment= URLMSApplication.getURLMS().getSingleLab().getInventory().getResource(0);
		//check quantity
		assertEquals(quantity, aEquipment.getQuantity());
		//check name
		assertEquals(name, aEquipment.getName());
		
	}

}
