/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.util.*;
import java.sql.Date;

// line 20 "../../../../../URLMS model.ump"
public class Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Staff Attributes
  private String firstName;
  private String lastName;
  private String password;
  private int iD;

  //Staff Associations
  private Role role;
  private List<Expense> expenses;
  private List<ProgressUpdate> progressUpdates;
  private Lab lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(String aFirstName, String aLastName, int aID, Lab aLab)
  {
    firstName = aFirstName;
    lastName = aLastName;
    password = "";
    iD = aID;
    expenses = new ArrayList<Expense>();
    progressUpdates = new ArrayList<ProgressUpdate>();
    boolean didAddLab = setLab(aLab);
    if (!didAddLab)
    {
      throw new RuntimeException("Unable to create staff due to lab");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setID(int aID)
  {
    boolean wasSet = false;
    iD = aID;
    wasSet = true;
    return wasSet;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getPassword()
  {
    return password;
  }

  public int getID()
  {
    return iD;
  }

  public Role getRole()
  {
    return role;
  }

  public boolean hasRole()
  {
    boolean has = role != null;
    return has;
  }

  public Expense getExpense(int index)
  {
    Expense aExpense = expenses.get(index);
    return aExpense;
  }

  public List<Expense> getExpenses()
  {
    List<Expense> newExpenses = Collections.unmodifiableList(expenses);
    return newExpenses;
  }

  public int numberOfExpenses()
  {
    int number = expenses.size();
    return number;
  }

  public boolean hasExpenses()
  {
    boolean has = expenses.size() > 0;
    return has;
  }

  public int indexOfExpense(Expense aExpense)
  {
    int index = expenses.indexOf(aExpense);
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

  public Lab getLab()
  {
    return lab;
  }

  public boolean setRole(Role aNewRole)
  {
    boolean wasSet = false;
    role = aNewRole;
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfExpenses()
  {
    return 0;
  }

  public boolean addExpense(Expense aExpense)
  {
    boolean wasAdded = false;
    if (expenses.contains(aExpense)) { return false; }
    expenses.add(aExpense);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeExpense(Expense aExpense)
  {
    boolean wasRemoved = false;
    if (expenses.contains(aExpense))
    {
      expenses.remove(aExpense);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addExpenseAt(Expense aExpense, int index)
  {  
    boolean wasAdded = false;
    if(addExpense(aExpense))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExpenses()) { index = numberOfExpenses() - 1; }
      expenses.remove(aExpense);
      expenses.add(index, aExpense);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveExpenseAt(Expense aExpense, int index)
  {
    boolean wasAdded = false;
    if(expenses.contains(aExpense))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExpenses()) { index = numberOfExpenses() - 1; }
      expenses.remove(aExpense);
      expenses.add(index, aExpense);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addExpenseAt(aExpense, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfProgressUpdates()
  {
    return 0;
  }

  public boolean addProgressUpdate(ProgressUpdate aProgressUpdate)
  {
    boolean wasAdded = false;
    if (progressUpdates.contains(aProgressUpdate)) { return false; }
    progressUpdates.add(aProgressUpdate);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProgressUpdate(ProgressUpdate aProgressUpdate)
  {
    boolean wasRemoved = false;
    if (progressUpdates.contains(aProgressUpdate))
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

  public boolean setLab(Lab aLab)
  {
    boolean wasSet = false;
    if (aLab == null)
    {
      return wasSet;
    }

    Lab existingLab = lab;
    lab = aLab;
    if (existingLab != null && !existingLab.equals(aLab))
    {
      existingLab.removeStaff(this);
    }
    lab.addStaff(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    role = null;
    expenses.clear();
    progressUpdates.clear();
    Lab placeholderLab = lab;
    this.lab = null;
    placeholderLab.removeStaff(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "iD" + ":" + getID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role = "+(getRole()!=null?Integer.toHexString(System.identityHashCode(getRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "lab = "+(getLab()!=null?Integer.toHexString(System.identityHashCode(getLab())):"null")
     + outputString;
  }
}