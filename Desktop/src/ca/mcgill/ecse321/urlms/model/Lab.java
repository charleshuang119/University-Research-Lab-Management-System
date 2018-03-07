/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.util.*;
import java.sql.Date;

// line 9 "../../../../../URLMS model.ump"
public class Lab
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Lab Attributes
  private String rootPassword;

  //Lab Associations
  private List<Staff> staff;
  private List<ProgressUpdate> progressUpdates;
  private FundingAccount fundingAccount;
  private Inventory inventory;
  private Director director;
  private URLMS uRLMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Lab(String aRootPassword, FundingAccount aFundingAccount, Inventory aInventory, URLMS aURLMS)
  {
    rootPassword = aRootPassword;
    staff = new ArrayList<Staff>();
    progressUpdates = new ArrayList<ProgressUpdate>();
    if (aFundingAccount == null || aFundingAccount.getLab() != null)
    {
      throw new RuntimeException("Unable to create Lab due to aFundingAccount");
    }
    fundingAccount = aFundingAccount;
    if (aInventory == null || aInventory.getLab() != null)
    {
      throw new RuntimeException("Unable to create Lab due to aInventory");
    }
    inventory = aInventory;
    boolean didAddURLMS = setURLMS(aURLMS);
    if (!didAddURLMS)
    {
      throw new RuntimeException("Unable to create singleLab due to uRLMS");
    }
  }

  public Lab(String aRootPassword, URLMS aURLMS)
  {
    rootPassword = aRootPassword;
    staff = new ArrayList<Staff>();
    progressUpdates = new ArrayList<ProgressUpdate>();
    fundingAccount = new FundingAccount(this);
    inventory = new Inventory(this);
    boolean didAddURLMS = setURLMS(aURLMS);
    if (!didAddURLMS)
    {
      throw new RuntimeException("Unable to create singleLab due to uRLMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRootPassword(String aRootPassword)
  {
    boolean wasSet = false;
    rootPassword = aRootPassword;
    wasSet = true;
    return wasSet;
  }

  public String getRootPassword()
  {
    return rootPassword;
  }

  public Staff getStaff(int index)
  {
    Staff aStaff = staff.get(index);
    return aStaff;
  }

  public List<Staff> getStaff()
  {
    List<Staff> newStaff = Collections.unmodifiableList(staff);
    return newStaff;
  }

  public int numberOfStaff()
  {
    int number = staff.size();
    return number;
  }

  public boolean hasStaff()
  {
    boolean has = staff.size() > 0;
    return has;
  }

  public int indexOfStaff(Staff aStaff)
  {
    int index = staff.indexOf(aStaff);
    return index;
  }

  public ProgressUpdate getProgressUpdate(int index)
  {
    ProgressUpdate aProgressUpdate = progressUpdates.get(index);
    return aProgressUpdate;
  }

  public List<ProgressUpdate> getProgressUpdates()
  {
    List<ProgressUpdate> newProgressUpdates = Collections.unmodifiableList(progressUpdates);
    return newProgressUpdates;
  }

  public int numberOfProgressUpdates()
  {
    int number = progressUpdates.size();
    return number;
  }

  public boolean hasProgressUpdates()
  {
    boolean has = progressUpdates.size() > 0;
    return has;
  }

  public int indexOfProgressUpdate(ProgressUpdate aProgressUpdate)
  {
    int index = progressUpdates.indexOf(aProgressUpdate);
    return index;
  }

  public FundingAccount getFundingAccount()
  {
    return fundingAccount;
  }

  public Inventory getInventory()
  {
    return inventory;
  }

  public Director getDirector()
  {
    return director;
  }

  public boolean hasDirector()
  {
    boolean has = director != null;
    return has;
  }

  public URLMS getURLMS()
  {
    return uRLMS;
  }

  public static int minimumNumberOfStaff()
  {
    return 0;
  }

  public Staff addStaff(String aFirstName, String aLastName, int aID)
  {
    return new Staff(aFirstName, aLastName, aID, this);
  }

  public boolean addStaff(Staff aStaff)
  {
    boolean wasAdded = false;
    if (staff.contains(aStaff)) { return false; }
    Lab existingLab = aStaff.getLab();
    boolean isNewLab = existingLab != null && !this.equals(existingLab);
    if (isNewLab)
    {
      aStaff.setLab(this);
    }
    else
    {
      staff.add(aStaff);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStaff(Staff aStaff)
  {
    boolean wasRemoved = false;
    //Unable to remove aStaff, as it must always have a lab
    if (!this.equals(aStaff.getLab()))
    {
      staff.remove(aStaff);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStaffAt(Staff aStaff, int index)
  {  
    boolean wasAdded = false;
    if(addStaff(aStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStaff()) { index = numberOfStaff() - 1; }
      staff.remove(aStaff);
      staff.add(index, aStaff);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStaffAt(Staff aStaff, int index)
  {
    boolean wasAdded = false;
    if(staff.contains(aStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStaff()) { index = numberOfStaff() - 1; }
      staff.remove(aStaff);
      staff.add(index, aStaff);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStaffAt(aStaff, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfProgressUpdates()
  {
    return 0;
  }

  public ProgressUpdate addProgressUpdate(String aDescription, String aFirstName, String aLastName, Date aDate)
  {
    return new ProgressUpdate(aDescription, aFirstName, aLastName, aDate, this);
  }

  public boolean addProgressUpdate(ProgressUpdate aProgressUpdate)
  {
    boolean wasAdded = false;
    if (progressUpdates.contains(aProgressUpdate)) { return false; }
    Lab existingLab = aProgressUpdate.getLab();
    boolean isNewLab = existingLab != null && !this.equals(existingLab);
    if (isNewLab)
    {
      aProgressUpdate.setLab(this);
    }
    else
    {
      progressUpdates.add(aProgressUpdate);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProgressUpdate(ProgressUpdate aProgressUpdate)
  {
    boolean wasRemoved = false;
    //Unable to remove aProgressUpdate, as it must always have a lab
    if (!this.equals(aProgressUpdate.getLab()))
    {
      progressUpdates.remove(aProgressUpdate);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addProgressUpdateAt(ProgressUpdate aProgressUpdate, int index)
  {  
    boolean wasAdded = false;
    if(addProgressUpdate(aProgressUpdate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProgressUpdates()) { index = numberOfProgressUpdates() - 1; }
      progressUpdates.remove(aProgressUpdate);
      progressUpdates.add(index, aProgressUpdate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProgressUpdateAt(ProgressUpdate aProgressUpdate, int index)
  {
    boolean wasAdded = false;
    if(progressUpdates.contains(aProgressUpdate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProgressUpdates()) { index = numberOfProgressUpdates() - 1; }
      progressUpdates.remove(aProgressUpdate);
      progressUpdates.add(index, aProgressUpdate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProgressUpdateAt(aProgressUpdate, index);
    }
    return wasAdded;
  }

  public boolean setDirector(Director aNewDirector)
  {
    boolean wasSet = false;
    director = aNewDirector;
    wasSet = true;
    return wasSet;
  }

  public boolean setURLMS(URLMS aNewURLMS)
  {
    boolean wasSet = false;
    if (aNewURLMS == null)
    {
      //Unable to setURLMS to null, as singleLab must always be associated to a uRLMS
      return wasSet;
    }
    
    Lab existingSingleLab = aNewURLMS.getSingleLab();
    if (existingSingleLab != null && !equals(existingSingleLab))
    {
      //Unable to setURLMS, the current uRLMS already has a singleLab, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    URLMS anOldURLMS = uRLMS;
    uRLMS = aNewURLMS;
    uRLMS.setSingleLab(this);

    if (anOldURLMS != null)
    {
      anOldURLMS.setSingleLab(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (staff.size() > 0)
    {
      Staff aStaff = staff.get(staff.size() - 1);
      aStaff.delete();
      staff.remove(aStaff);
    }
    
    while (progressUpdates.size() > 0)
    {
      ProgressUpdate aProgressUpdate = progressUpdates.get(progressUpdates.size() - 1);
      aProgressUpdate.delete();
      progressUpdates.remove(aProgressUpdate);
    }
    
    FundingAccount existingFundingAccount = fundingAccount;
    fundingAccount = null;
    if (existingFundingAccount != null)
    {
      existingFundingAccount.delete();
    }
    Inventory existingInventory = inventory;
    inventory = null;
    if (existingInventory != null)
    {
      existingInventory.delete();
    }
    director = null;
    URLMS existingURLMS = uRLMS;
    uRLMS = null;
    if (existingURLMS != null)
    {
      existingURLMS.setSingleLab(null);
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "rootPassword" + ":" + getRootPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fundingAccount = "+(getFundingAccount()!=null?Integer.toHexString(System.identityHashCode(getFundingAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "inventory = "+(getInventory()!=null?Integer.toHexString(System.identityHashCode(getInventory())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "director = "+(getDirector()!=null?Integer.toHexString(System.identityHashCode(getDirector())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "uRLMS = "+(getURLMS()!=null?Integer.toHexString(System.identityHashCode(getURLMS())):"null")
     + outputString;
  }
}