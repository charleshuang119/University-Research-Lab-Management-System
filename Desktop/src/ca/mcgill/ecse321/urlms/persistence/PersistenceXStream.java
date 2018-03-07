package ca.mcgill.ecse321.urlms.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Equipment;
import ca.mcgill.ecse321.urlms.model.Expense;
import ca.mcgill.ecse321.urlms.model.FundingAccount;
import ca.mcgill.ecse321.urlms.model.Inventory;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.model.Role;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.model.Supply;
import ca.mcgill.ecse321.urlms.model.URLMS;

public abstract class PersistenceXStream {

	private static XStream xstream = new XStream();
	private static String filename = "data.xml";

	public static URLMS initializeModelManager(String fileName) {
		// Initialization for persistence
		URLMS urlms;
		setFilename(fileName);
		setAlias("urlms", URLMS.class);
		setAlias("assistant", Assistant.class);
		setAlias("associate", Associate.class);
		setAlias("director", Director.class);
		setAlias("equipment", Equipment.class);
		setAlias("expense", Expense.class);
		setAlias("fundingAccount", FundingAccount.class);
		setAlias("inventory", Inventory.class);
		setAlias("lab", Lab.class);
		setAlias("progressUpdate", ProgressUpdate.class);
		setAlias("resource", Resource.class);
		setAlias("role", Role.class);
		setAlias("staff", Staff.class);
		setAlias("supply", Supply.class);

		// load model if exists, create otherwise
		File file = new File(fileName);
		if (file.exists()) {
			urlms = (URLMS) loadFromXMLwithXStream();
		}
		else {
			try {
				// file.mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			urlms = new URLMS();
			saveToXMLwithXStream(urlms);
		}
		return urlms;

	}

	public static boolean saveToXMLwithXStream(Object obj) {
		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); // save our xml file

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Object loadFromXMLwithXStream() {
		xstream.setMode(XStream.ID_REFERENCES);
		try {
			FileReader fileReader = new FileReader(filename); // load our xml file
			return xstream.fromXML(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}

	public static String getFilename() {
		return filename;
	}

}
