/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;

// line 78 "../../../../../URLMS model.ump"
public class Supply extends Resource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Supply(int aQuantity, String aName, Inventory aInventory)
  {
    super(aQuantity, aName, aInventory);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 81 "../../../../../URLMS model.ump"
   public String toString(){
    return "Supply";
  }

}