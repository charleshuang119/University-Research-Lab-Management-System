<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class URLMS
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //URLMS Associations
  public $singleLab; //until PHP 5.3 (setAccessible)

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public function getSingleLab()
  {
    return $this->singleLab;
  }

  public function hasSingleLab()
  {
    $has = $this->singleLab != null;
    return $has;
  }

  public function setSingleLab($aNewSingleLab)
  {
    $wasSet = false;
    if ($this->singleLab != null && $this->singleLab != $aNewSingleLab && $this == $this->singleLab->getURLMS())
    {
      //Unable to setSingleLab, as existing singleLab would become an orphan
      return $wasSet;
    }
    
    $this->singleLab = $aNewSingleLab;
    $anOldURLMS = $aNewSingleLab != null ? $aNewSingleLab->getURLMS() : null;
    
    if ($this != $anOldURLMS)
    {
      if ($anOldURLMS != null)
      {
        $anOldURLMS->singleLab = null;
      }
      if ($this->singleLab != null)
      {
        $this->singleLab->setURLMS($this);
      }
    }
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    
    if ($this->singleLab != null)
    {
        $this->singleLab->delete();
        $this->singleLab = null;
    }
    
  }

}
?>