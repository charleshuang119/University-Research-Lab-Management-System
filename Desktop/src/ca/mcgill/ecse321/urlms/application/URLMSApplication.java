package ca.mcgill.ecse321.urlms.application;

import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.model.URLMS;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;
import ca.mcgill.ecse321.urlms.view.AddExpensePage;
import ca.mcgill.ecse321.urlms.view.ConfirmRootPage;
import ca.mcgill.ecse321.urlms.view.CreateLabRoot;
import ca.mcgill.ecse321.urlms.view.ProgressUpdatePage;
import ca.mcgill.ecse321.urlms.view.FundingPage;
import ca.mcgill.ecse321.urlms.view.LogInPage;
import ca.mcgill.ecse321.urlms.view.LogPasswordPage;
import ca.mcgill.ecse321.urlms.view.NewPasswordPage;
import ca.mcgill.ecse321.urlms.view.ResourcePage;
import ca.mcgill.ecse321.urlms.view.RootLogIn;
import ca.mcgill.ecse321.urlms.view.SetFundingPage;
import ca.mcgill.ecse321.urlms.view.StaffPage;
import ca.mcgill.ecse321.urlms.view.WelcomeAdmin;

public class URLMSApplication {
	private static URLMS urlms;
	private static String filename = PersistenceXStream.getFilename();
	public static ManagerController mc;

	public static WelcomeAdmin w;
	public static StaffPage sp;
	public static ProgressUpdatePage dp;
	public static ResourcePage rp;
	public static FundingPage fp;
	public static AddExpensePage ep;
	public static ConfirmRootPage crp;
	public static CreateLabRoot clr;
	public static LogInPage lip;
	public static LogPasswordPage lpp;
	public static NewPasswordPage npp;
	public static RootLogIn rl;
	public static SetFundingPage sfp;
	

	public static Staff currentStaff = null;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				PersistenceXStream.initializeModelManager(PersistenceXStream.getFilename());
				load();
				if (URLMSApplication.getURLMS() != null && URLMSApplication.getURLMS().hasSingleLab()) {
					mc = new ManagerController();
					try {
						mc.loadLab();
					} catch (InvalidInputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lip = new LogInPage();
					lip.setVisible(true);
					//w = new WelcomeAdmin();
					//w.setVisible(true);
					
				}
				else { // if no lab exists, create one and set the UI visible
					mc = new ManagerController();
					clr = new CreateLabRoot();
					clr.setVisible(true);
				}

			}
		});
	}

	public static URLMS getURLMS() {
		if (urlms == null) {
			urlms = load();
		}
		return urlms;
	}

	public static void save() {
		PersistenceXStream.saveToXMLwithXStream(urlms);
	}

	public static URLMS load() {
		urlms = (URLMS) PersistenceXStream.loadFromXMLwithXStream();

		if (urlms == null) {
			urlms = new URLMS();
		}
		// PersistenceXStream.loadFromXMLwithXStream();
		save();
		urlms = (URLMS) PersistenceXStream.loadFromXMLwithXStream();
		return urlms;
	}

	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}
