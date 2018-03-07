/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.sql.Date;

// line 149 "../../../../../URLMS model.ump"
public class Expense
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Expense Attributes
  private String description;
  private float amount;
  private Date date;

  //Expense State Machines
  public enum Type { TRAVEL, SALARY, RESOURCE, OTHER }
  private Type type;

  //Expense Associations
  private FundingAccount fundingAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Expense(String aDescription, float aAmount, Date aDate, FundingAccount aFundingAccount)
  {
    description = aDescription;
    amount = aAmount;
    date = aDate;
    boolean didAddFundingAccount = setFundingAccount(aFundingAccount);
    if (!didAddFundingAccount)
    {
      throw new RuntimeException("Unable to create expense due to fundingAccount");
    }
    setType(Type.TRAVEL);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setAmount(float aAmount)
  {
    boolean wasSet = false;
    amount = aAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public float getAmount()
  {
    return amount;
  }

  public Date getDate()
  {
    return date;
  }

  public String getTypeFullName()
  {
    String answer = type.toString();
    return answer;
  }

  public Type getType()
  {
    return type;
  }

  public boolean setType(Type aType)
  {
    type = aType;
    return true;
  }

  public FundingAccount getFundingAccount()
  {
    return fundingAccount;
  }

  public boolean setFundingAccount(FundingAccount aFundingAccount)
  {
    boolean wasSet = false;
    if (aFundingAccount == null)
    {
      return wasSet;
    }

    FundingAccount existingFundingAccount = fundingAccount;
    fundingAccount = aFundingAccount;
    if (existingFundingAccount != null && !existingFundingAccount.equals(aFundingAccount))
    {
      existingFundingAccount.removeExpense(this);
    }
    fundingAccount.addExpense(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FundingAccount placeholderFundingAccount = fundingAccount;
    this.fundingAccount = null;
    placeholderFundingAccount.removeExpense(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "amount" + ":" + getAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fundingAccount = "+(getFundingAccount()!=null?Integer.toHexString(System.identityHashCode(getFundingAccount())):"null")
     + outputString;
  }
}