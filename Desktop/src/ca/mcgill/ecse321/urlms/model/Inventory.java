/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;
import java.util.*;

// line 61 "../../../../../URLMS model.ump"
public class Inventory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Inventory Associations
  private List<Resource> resources;
  private Lab lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Inventory(Lab aLab)
  {
    resources = new ArrayList<Resource>();
    if (aLab == null || aLab.getInventory() != null)
    {
      throw new RuntimeException("Unable to create Inventory due to aLab");
    }
    lab = aLab;
  }

  public Inventory(String aRootPasswordForLab, FundingAccount aFundingAccountForLab, URLMS aURLMSForLab)
  {
    resources = new ArrayList<Resource>();
    lab = new Lab(aRootPasswordForLab, aFundingAccountForLab, this, aURLMSForLab);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Resource getResource(int index)
  {
    Resource aResource = resources.get(index);
    return aResource;
  }

  public List<Resource> getResources()
  {
    List<Resource> newResources = Collections.unmodifiableList(resources);
    return newResources;
  }

  public int numberOfResources()
  {
    int number = resources.size();
    return number;
  }

  public boolean hasResources()
  {
    boolean has = resources.size() > 0;
    return has;
  }

  public int indexOfResource(Resource aResource)
  {
    int index = resources.indexOf(aResource);
    return index;
  }

  public Lab getLab()
  {
    return lab;
  }

  public static int minimumNumberOfResources()
  {
    return 0;
  }

//  public Resource addResource(int aQuantity, String aName)
//  {
//    return new Resource(aQuantity, aName, this);
//  }

  public boolean addResource(Resource aResource)
  {
    boolean wasAdded = false;
    if (resources.contains(aResource)) { return false; }
    Inventory existingInventory = aResource.getInventory();
    boolean isNewInventory = existingInventory != null && !this.equals(existingInventory);
    if (isNewInventory)
    {
      aResource.setInventory(this);
    }
    else
    {
      resources.add(aResource);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeResource(Resource aResource)
  {
    boolean wasRemoved = false;
    //Unable to remove aResource, as it must always have a inventory
    if (!this.equals(aResource.getInventory()))
    {
      resources.remove(aResource);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addResourceAt(Resource aResource, int index)
  {  
    boolean wasAdded = false;
    if(addResource(aResource))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfResources()) { index = numberOfResources() - 1; }
      resources.remove(aResource);
      resources.add(index, aResource);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveResourceAt(Resource aResource, int index)
  {
    boolean wasAdded = false;
    if(resources.contains(aResource))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfResources()) { index = numberOfResources() - 1; }
      resources.remove(aResource);
      resources.add(index, aResource);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addResourceAt(aResource, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (resources.size() > 0)
    {
      Resource aResource = resources.get(resources.size() - 1);
      aResource.delete();
      resources.remove(aResource);
    }
    
    Lab existingLab = lab;
    lab = null;
    if (existingLab != null)
    {
      existingLab.delete();
    }
  }

}