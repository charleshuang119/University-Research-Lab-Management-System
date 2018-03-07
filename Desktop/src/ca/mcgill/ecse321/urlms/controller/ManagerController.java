package ca.mcgill.ecse321.urlms.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Equipment;
import ca.mcgill.ecse321.urlms.model.Expense;
import ca.mcgill.ecse321.urlms.model.Expense.Type;
import ca.mcgill.ecse321.urlms.model.Inventory;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.model.Staff;
import ca.mcgill.ecse321.urlms.model.Supply;
import ca.mcgill.ecse321.urlms.model.URLMS;
import ca.mcgill.ecse321.urlms.persistence.PersistenceXStream;

public class ManagerController {
	private URLMS urlms;
	private Lab singleLab;

	public ManagerController() {
		PersistenceXStream.initializeModelManager(PersistenceXStream.getFilename());
		// try {
		// loadLab();
		// } catch (InvalidInputException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * Method to add a User to the system
	 * 
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @throws InvalidInputException
	 */
	public void addStaff(String firstName, String lastName, String role) throws InvalidInputException {
		String error = "";
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try {
			if (role == "Director") {
				if (singleLab.hasDirector()) {
					throw new InvalidInputException("The lab already has a director");
				}
				else {
					Staff staff = new Staff(firstName, lastName, createID(), singleLab);
					createDirector(firstName, lastName, staff, singleLab);
				}
			}
			else if (role == "Assistant") {
				Staff staff = new Staff(firstName, lastName, createID(), singleLab);

				Assistant assistant = new Assistant();
				staff.setRole(assistant);
				singleLab.addStaff(staff);
			}
			else if (role == "Associate") {
				Staff staff = new Staff(firstName, lastName, createID(), singleLab);

				Associate associate = new Associate();
				staff.setRole(associate);
				singleLab.addStaff(staff);
			}

		} catch (RuntimeException e) {
			throw new InvalidInputException(error);
		}

	}

	private int createID() {
		int iD = (int) (Math.random() * 100000);
		while (iD == 0) {
			iD = (int) (Math.random() * 100000);
		}
		int size = URLMSApplication.getURLMS().getSingleLab().getStaff().size();
		for (int i = 0; i < size; i++) {
			if (URLMSApplication.getURLMS().getSingleLab().getStaff().get(i).getID() == iD) {
				iD = (int) (Math.random() * 100000);
				i = 0;
			}
		}

		return iD;

	}

	/**
	 * This method removes a staff from the lab
	 * 
	 * @param aStaff staff to remove
	 */
	public void removeStaff(Staff aStaff) throws InvalidInputException {
		String error = "";

		try {
			// singleLab.removeStaff(aStaff);
			if (aStaff.getRole() instanceof Director) {
				singleLab.setDirector(null);
			}
			aStaff.getRole().delete();
			aStaff.delete();
		} catch (RuntimeException e) {
			throw new InvalidInputException(error);
		}
	}

	/**
	 * This method changes the role of a staff member
	 * 
	 * @param aStaff
	 * @param role
	 * @throws InvalidInputException
	 */
	public void editStaffRole(Staff aStaff, String role) throws InvalidInputException {
		String error = "";

		if (role == "Director" && singleLab.hasDirector()) {
			error = "The lab already has a director";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		if (error.length() == 0) {
			try {
				if (role == "Director") {
					aStaff.getRole().delete();
					aStaff.setRole(new Director());
				}
				else if (role == "Assistant") {
					aStaff.getRole().delete();
					aStaff.setRole(new Assistant());
				}
				else if (role == "Associate") {
					aStaff.getRole().delete();
					aStaff.setRole(new Associate());
				}
			} catch (RuntimeException e) {
				throw new InvalidInputException(error);
			}
		}

	}

	public void setPassword(Staff aStaff, String password) {
		String error = "";

		if (password.length() == 0) {
			error = "There needs to be a password";
		}
		if (error.length() == 0) {
			aStaff.setPassword(password);
		}
	}

	/**
	 * This methods adds an equipement to the inventory
	 * 
	 * @param quantity of the equipment to be added
	 * @param name of the added equipment
	 * @throws InvalidInputException
	 */
	public void addEquipement(int quantity, String name) throws InvalidInputException {
		String error = "";
		Inventory currentInventory = singleLab.getInventory();

		List<Resource> resources = currentInventory.getResources();

		for (Resource resource : resources) {
			if (resource.getName().equals(name)) {
				error = "An equipment with that name already exists.";
			}
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		if (error.isEmpty()) {
			try {
				Resource newEquipment = new Equipment(quantity, name, currentInventory);
				currentInventory.addResource(newEquipment);
			} catch (RuntimeException e) {
				throw new InvalidInputException(error);
			}
		}
	}

	public boolean isFundingAccountSet() {
		if (singleLab.getFundingAccount().getAllocatedAmount() > 0) {
			return true;
		}
		return false;
	}

	public void setFundingAccount(int amount) throws InvalidInputException {
		String error = "";
		if (amount <= 0) {
			error = "The amount needs to be positive";
		}
		if (singleLab.getFundingAccount().getAllocatedAmount() > 0) {
			error = "The funding account has already been set";
		}
		if (error.length() == 0) {
			singleLab.getFundingAccount().setAllocatedAmount(amount);
		}
		else {
			throw new InvalidInputException(error);
		}
	}

	public void addFundingAccount(int amount) throws InvalidInputException {
		String error = "";
		if (amount <= 0) {
			error = "The amount needs to be positive";
		}
		if (error.length() == 0) {
			singleLab.getFundingAccount()
					.setAllocatedAmount(singleLab.getFundingAccount().getAllocatedAmount() + amount);
		}
		else {
			throw new InvalidInputException(error);
		}
	}

	/**
	 * This method adds a supply to the inventory
	 * 
	 * @param quantity of the supply
	 * @param name of the supply
	 * @throws InvalidInputException
	 */
	public void addSupply(int quantity, String name) throws InvalidInputException {
		String error = "";
		Inventory currentInventory = singleLab.getInventory();

		List<Resource> resources = currentInventory.getResources();

		for (Resource resource : resources) {
			// System.out.println(resource.getName());
			// System.out.println("my resource " + name);
			if ((resource.getName()).equals(name)) {
				error = "A supply with that name already exists.";
			}
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		if (error.isEmpty()) {
			try {
				Resource newSupply = new Supply(quantity, name, currentInventory);
				currentInventory.addResource(newSupply);
			} catch (RuntimeException e) {
				throw new InvalidInputException(error);
			}
		}
	}

	/**
	 * This method edits the quantity of a given resource
	 * 
	 * @param resource
	 * @param newQty
	 * @throws InvalidInputException
	 */
	public void editResource(Resource resource, int newQty) throws InvalidInputException {
		String error = "";
		if (newQty < 1) {
			error = "The quantity must be more than 0";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		else if (error.isEmpty()) {
			resource.setQuantity(newQty);
		}
	}

	/**
	 * This method will remove a resource from the system
	 * 
	 * @param aResource
	 * @throws InvalidInputException
	 */
	public void removeResource(Resource aResource) throws InvalidInputException {
		String error = "";

		try {
			singleLab.getInventory().removeResource(aResource);
			aResource.delete();
		} catch (RuntimeException e) {
			throw new InvalidInputException(error);
		}
	}

	/**
	 * This method will create a director and set the lab's director to this specific director
	 * 
	 * @param firstName
	 * @param lastName
	 * @param staff
	 * @param lab
	 * @throws InvalidInputException
	 */
	public void createDirector(String firstName, String lastName, Staff staff, Lab lab) throws InvalidInputException {
		String error = "";
		if (lab.hasDirector()) {
			error = "You already have a Director";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			Director director = new Director();
			staff.setRole(director);
			lab.addStaff(staff);
			lab.setDirector(director);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	/**
	 * This method creates and adds a progress update to a specific staff member. it also adds the
	 * progress update to the lab
	 * 
	 * @param content
	 * @param aStaff
	 * @throws InvalidInputException
	 */
	public void createProgressUpdate(String content, Staff aStaff) throws InvalidInputException {
		String error = "";
		if (content.length() == 0) {
			error = "The progress update is empty";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		else {
			try {
				Date now = new Date(Calendar.getInstance().getTimeInMillis());
				ProgressUpdate progress = new ProgressUpdate(content, aStaff.getFirstName(), aStaff.getLastName(), now,
						singleLab);
				aStaff.addProgressUpdate(progress);
			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	public void addExpense(Staff aStaff, String description, float value, String type) throws InvalidInputException {
		String error = "";
		if (description.length() == 0) {
			error = "The description cannot be empty";
		}
		if (type.length() == 0) {
			error = "The expense needs to be categorized";
		}
		if (value < 0) {
			error = "The amount cannot be negative";
		}
		if (error.length() == 0) {
			try {
				Date now = new Date(Calendar.getInstance().getTimeInMillis());
				Expense e = new Expense(description, value, now, singleLab.getFundingAccount());
				if (type == "Travel") {
					e.setType(Type.TRAVEL);
				}
				else if (type == "Salary") {
					e.setType(Type.SALARY);
				}
				else if (type == "Resource") {
					e.setType(Type.RESOURCE);
				}
				else if (type == "Other") {
					e.setType(Type.OTHER);
				}

				aStaff.addExpense(e);
			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	/**
	 * This method creates a lab and adds it to the URLMS application
	 * 
	 * @throws InvalidInputException
	 */
	public void createLab(String password) throws InvalidInputException {
		URLMS urlms = URLMSApplication.getURLMS();
		Lab lab = new Lab(password, urlms);
		singleLab = lab;
		urlms.setSingleLab(lab);
	}

	/**
	 * Method saves the URLMS to the persistence
	 */
	public void saveLab() {
		URLMSApplication.save();
	}

	public Lab loadLab() throws InvalidInputException {
    String error = "";
    if (!URLMSApplication.getURLMS().hasSingleLab()) {
      error = "The current URLMS does not have labs";
      throw new InvalidInputException(error.trim());
    }
    try {
      singleLab = URLMSApplication.getURLMS().getSingleLab();
      return singleLab;
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }
}
