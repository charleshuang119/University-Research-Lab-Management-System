/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;

// line 72 "../../../../../URLMS model.ump"
public abstract class Resource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Resource Attributes
  private int quantity;
  private String name;

  //Resource Associations
  private Inventory inventory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Resource(int aQuantity, String aName, Inventory aInventory)
  {
    quantity = aQuantity;
    name = aName;
    boolean didAddInventory = setInventory(aInventory);
    if (!didAddInventory)
    {
      throw new RuntimeException("Unable to create resource due to inventory");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public String getName()
  {
    return name;
  }

  public Inventory getInventory()
  {
    return inventory;
  }

  public boolean setInventory(Inventory aInventory)
  {
    boolean wasSet = false;
    if (aInventory == null)
    {
      return wasSet;
    }

    Inventory existingInventory = inventory;
    inventory = aInventory;
    if (existingInventory != null && !existingInventory.equals(aInventory))
    {
      existingInventory.removeResource(this);
    }
    inventory.addResource(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Inventory placeholderInventory = inventory;
    this.inventory = null;
    placeholderInventory.removeResource(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "inventory = "+(getInventory()!=null?Integer.toHexString(System.identityHashCode(getInventory())):"null")
     + outputString;
  }
}