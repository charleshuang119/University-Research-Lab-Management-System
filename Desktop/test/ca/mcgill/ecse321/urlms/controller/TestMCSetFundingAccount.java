package ca.mcgill.ecse321.urlms.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.FundingAccount;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class TestMCSetFundingAccount {
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
	}

	@After
	public void tearDown() throws Exception {
		//delete URLMS
		URLMSApplication.getURLMS().delete();
	}

	@Test
	public void test() throws InvalidInputException {
		//check if the allocated amount is 0
		FundingAccount aFundingAccount=URLMSApplication.getURLMS().getSingleLab().getFundingAccount();
		assertEquals(0,aFundingAccount.getAllocatedAmount());
		
		//check if the allocated amount is set
		int amount=1000;
		mc.setFundingAccount(amount);
		assertEquals(1000,aFundingAccount.getAllocatedAmount());
	}

}
