/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.util.*;

import ca.mcgill.ecse321.urlms.model.Expense.Type;

import java.sql.Date;

// line 92 "../../../../../URLMS model.ump"
public class FundingAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FundingAccount Attributes
  private float allocatedAmount;
  private float remainingAmount;

  //FundingAccount Associations
  private List<Expense> expenses;
  private Lab lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FundingAccount(Lab aLab)
  {
    expenses = new ArrayList<Expense>();
    if (aLab == null || aLab.getFundingAccount() != null)
    {
      throw new RuntimeException("Unable to create FundingAccount due to aLab");
    }
    lab = aLab;
  }

  public FundingAccount(String aRootPasswordForLab, Inventory aInventoryForLab, URLMS aURLMSForLab)
  {
    expenses = new ArrayList<Expense>();
    lab = new Lab(aRootPasswordForLab, this, aInventoryForLab, aURLMSForLab);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAllocatedAmount(float aAllocatedAmount)
  {
    boolean wasSet = false;
    allocatedAmount = aAllocatedAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemainingAmount(float aRemainingAmount)
  {
    boolean wasSet = false;
    remainingAmount = aRemainingAmount;
    wasSet = true;
    return wasSet;
  }

  public float getAllocatedAmount()
  {
    return allocatedAmount;
  }

//  public float getRemainingAmount()
//  {
//    return remainingAmount;
//  }

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

  public Lab getLab()
  {
    return lab;
  }

  public static int minimumNumberOfExpenses()
  {
    return 0;
  }

  public Expense addExpense(String aDescription, float aAmount, Date aDate)
  {
    return new Expense(aDescription, aAmount, aDate, this);
  }

  public boolean addExpense(Expense aExpense)
  {
    boolean wasAdded = false;
    if (expenses.contains(aExpense)) { return false; }
    FundingAccount existingFundingAccount = aExpense.getFundingAccount();
    boolean isNewFundingAccount = existingFundingAccount != null && !this.equals(existingFundingAccount);
    if (isNewFundingAccount)
    {
      aExpense.setFundingAccount(this);
    }
    else
    {
      expenses.add(aExpense);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeExpense(Expense aExpense)
  {
    boolean wasRemoved = false;
    //Unable to remove aExpense, as it must always have a fundingAccount
    if (!this.equals(aExpense.getFundingAccount()))
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

  public void delete()
  {
    for(int i=expenses.size(); i > 0; i--)
    {
      Expense aExpense = expenses.get(i - 1);
      aExpense.delete();
    }
    Lab existingLab = lab;
    lab = null;
    if (existingLab != null)
    {
      existingLab.delete();
    }
  }


  /**
   * @Override
   */
  // line 100 "../../../../../URLMS model.ump"
   public float getRemainingAmount(){
    float total = 0;
  	for (Expense e: expenses) {
  		total += e.getAmount();
  	}
  	return allocatedAmount - total;
  }

  // line 108 "../../../../../URLMS model.ump"
   public float getTravelExpensesTotal(){
    float total = 0;
  	for (Expense e: expenses) {
  		if (e.getType() == Type.TRAVEL) {
  			total += e.getAmount();
  		}
  	}
  	return total;
  }

  // line 118 "../../../../../URLMS model.ump"
   public float getSalaryExpensesTotal(){
    float total = 0;
  	for (Expense e: expenses) {
  		if (e.getType() == Type.SALARY) {
  			total += e.getAmount();
  		}
  	}
  	return total;
  }

  // line 128 "../../../../../URLMS model.ump"
   public float getResourceExpensesTotal(){
    float total = 0;
  	for (Expense e: expenses) {
  		if (e.getType() == Type.RESOURCE) {
  			total += e.getAmount();
  		}
  	}
  	return total;
  }

  // line 138 "../../../../../URLMS model.ump"
   public float getOtherExpensesTotal(){
    float total = 0;
  	for (Expense e: expenses) {
  		if (e.getType() == Type.OTHER) {
  			total += e.getAmount();
  		}
  	}
  	return total;
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "allocatedAmount" + ":" + getAllocatedAmount()+ "," +
            "remainingAmount" + ":" + getRemainingAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lab = "+(getLab()!=null?Integer.toHexString(System.identityHashCode(getLab())):"null")
     + outputString;
  }
}