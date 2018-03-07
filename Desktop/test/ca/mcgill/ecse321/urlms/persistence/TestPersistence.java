package ca.mcgill.ecse321.urlms.persistence;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.urlms.controller.*;
import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.FundingAccount;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.model.URLMS;

public class TestPersistence {
	URLMS urlms;

	@Before
	public void setUp() throws Exception {
		urlms = new URLMS();

		// create lab
		Lab l = new Lab("123456",urlms);

		// create staff
		Staff s1 = new Staff("Li", "Zhang", 0, l);
		s1.setRole(new Associate());

		Staff s2 = new Staff("Charles", "Huang", 1, l);
		s2.setRole(new Assistant());

		Staff s3 = new Staff("Gabe", "Tadesse", 2, l);
		s3.setRole(new Associate());

		// register staff to lab
		l.addStaff(s1);
		l.addStaff(s2);
		l.addStaff(s3);

		// manage lab
		urlms.setSingleLab(l);

	}

	@After
	public void tearDown() throws Exception {
		urlms.delete();
	}

	@Test
	public void test() throws FileNotFoundException {
		PersistenceXStream.initializeModelManager("output" + File.pathSeparator + "testP.xml");

		if (!PersistenceXStream.saveToXMLwithXStream(urlms))
			fail("could not save file.");

		// clear memory
		urlms.delete();

		urlms = (URLMS) PersistenceXStream.loadFromXMLwithXStream();
		if (urlms == null)
			fail("Could not load file.");

		// test case, check if the lab number is 1
		// assertEquals(1, urlms.getLabs().size());

		// check if 3 staffs are added
		assertEquals("Li", urlms.getSingleLab().getStaff().get(0).getFirstName());
		assertEquals("Charles", urlms.getSingleLab().getStaff().get(1).getFirstName());
		assertEquals("Gabe", urlms.getSingleLab().getStaff().get(2).getFirstName());

	}

}
