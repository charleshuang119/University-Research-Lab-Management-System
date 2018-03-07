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
import ca.mcgill.ecse321.urlms.model.Supply;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCRemoveResource {
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
		String name ="pen";
		int quantity= 200;
		mc.addSupply(quantity, name);
		
		//add an equipment	
		String name1 ="Computer";
		int quantity1= 20;			
		mc.addEquipement(quantity1, name1);
	}

	@After
	public void tearDown() throws Exception {
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		//check if the resource number in the lab is 3
		assertEquals(2, URLMSApplication.getURLMS().getSingleLab().getInventory().getResources().size());
		
		//remove resources
		Resource resource1=URLMSApplication.getURLMS().getSingleLab().getInventory().getResource(0);
		Resource resource2=URLMSApplication.getURLMS().getSingleLab().getInventory().getResource(1);
		mc.removeResource(resource1);
		mc.removeResource(resource2);
		
		//check if the resource number in the lab is 3
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getInventory().getResources().size());
	}

}
