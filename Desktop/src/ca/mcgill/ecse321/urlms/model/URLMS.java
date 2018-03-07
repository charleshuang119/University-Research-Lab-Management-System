/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse321.urlms.model;

// line 3 "../../../../../URLMS model.ump"
public class URLMS
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //URLMS Associations
  private Lab singleLab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public URLMS()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public Lab getSingleLab()
  {
    return singleLab;
  }

  public boolean hasSingleLab()
  {
    boolean has = singleLab != null;
    return has;
  }

  public boolean setSingleLab(Lab aNewSingleLab)
  {
    boolean wasSet = false;
    if (singleLab != null && !singleLab.equals(aNewSingleLab) && equals(singleLab.getURLMS()))
    {
      //Unable to setSingleLab, as existing singleLab would become an orphan
      return wasSet;
    }

    singleLab = aNewSingleLab;
    URLMS anOldURLMS = aNewSingleLab != null ? aNewSingleLab.getURLMS() : null;

    if (!this.equals(anOldURLMS))
    {
      if (anOldURLMS != null)
      {
        anOldURLMS.singleLab = null;
      }
      if (singleLab != null)
      {
        singleLab.setURLMS(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Lab existingSingleLab = singleLab;
    singleLab = null;
    if (existingSingleLab != null)
    {
      existingSingleLab.delete();
      existingSingleLab.setURLMS(null);
    }
  }

}