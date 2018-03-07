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
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Expense;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCAddExpense {
	private ManagerController mc;
	enum Type { TRAVEL, SALARY, RESOURCE, OTHER }; 
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
		

		Staff aStaff=URLMSApplication.getURLMS().getSingleLab().getStaff(0);
		String description1="Travel to USA";
		int amount1=5000;
		String type1="Travel";
		
		String description2="Salary for Charles";
		int amount2=200000;
		String type2="Salary";
		
		String description3="Bought a computer";
		int amount3=1100;
		String type3="Resource";
		
		String description4="Maintenance";
		int amount4=2000;
		String type4="Other";
		
		// check if the Expense is empty in the new lab
		assertEquals(0, URLMSApplication.getURLMS().getSingleLab().getFundingAccount().getExpenses().size());
		
		//add expenses
		mc.addExpense(aStaff, description1, amount1, type1);
		mc.addExpense(aStaff, description2, amount2, type2);
		mc.addExpense(aStaff, description3, amount3, type3);
		mc.addExpense(aStaff, description4, amount4, type4);
		
		//check expenses
		checkResultExpense(description1,amount1,type1,0);
		checkResultExpense(description2,amount2,type2,1);
		checkResultExpense(description3,amount3,type3,2);
		checkResultExpense(description4,amount4,type4,3);
		
	}
	
	private void checkResultExpense(String description, int amount, String type,int expenseIndex) {
		
		Expense aExpense=URLMSApplication.getURLMS().getSingleLab().getFundingAccount().getExpense(expenseIndex);
		
		//check description
		assertEquals(description,aExpense.getDescription());
		
		//check amount
		assertEquals(amount,aExpense.getAmount());
		
		//check type
		if(type=="Travel") {
		assertEquals("TRAVEL",(aExpense.getType().toString()));
		}
		if(type=="Salary") {
			assertEquals("SALARY",(aExpense.getType().toString()));
			}
		if(type=="Resource") {
			assertEquals("RESOURCE",(aExpense.getType().toString()));
			}
		if(type=="Other") {
			assertEquals("OTHER",(aExpense.getType().toString()));
			}
	}

}
