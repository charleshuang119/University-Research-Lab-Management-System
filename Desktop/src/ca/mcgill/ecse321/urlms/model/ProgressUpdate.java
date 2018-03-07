/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.sql.Date;

// line 65 "../../../../../URLMS model.ump"
public class ProgressUpdate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ProgressUpdate Attributes
  private String description;
  private String firstName;
  private String lastName;
  private Date date;

  //ProgressUpdate Associations
  private Lab lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ProgressUpdate(String aDescription, String aFirstName, String aLastName, Date aDate, Lab aLab)
  {
    description = aDescription;
    firstName = aFirstName;
    lastName = aLastName;
    date = aDate;
    boolean didAddLab = setLab(aLab);
    if (!didAddLab)
    {
      throw new RuntimeException("Unable to create progressUpdate due to lab");
    }
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

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public Date getDate()
  {
    return date;
  }

  public Lab getLab()
  {
    return lab;
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
      existingLab.removeProgressUpdate(this);
    }
    lab.addProgressUpdate(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Lab placeholderLab = lab;
    this.lab = null;
    placeholderLab.removeProgressUpdate(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lab = "+(getLab()!=null?Integer.toHexString(System.identityHashCode(getLab())):"null")
     + outputString;
  }
}