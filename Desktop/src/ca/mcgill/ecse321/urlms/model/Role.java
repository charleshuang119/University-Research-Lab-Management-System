/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;

// line 31 "../../../../../URLMS model.ump"
public abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Role Attributes
  private int salary;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role()
  {
    salary = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSalary(int aSalary)
  {
    boolean wasSet = false;
    salary = aSalary;
    wasSet = true;
    return wasSet;
  }

  public int getSalary()
  {
    return salary;
  }

  public void delete()
  {}


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "salary" + ":" + getSalary()+ "]"
     + outputString;
  }
}