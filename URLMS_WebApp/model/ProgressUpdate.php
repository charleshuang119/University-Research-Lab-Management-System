<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class ProgressUpdate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ProgressUpdate Attributes
  private $description;
  private $date;

  //ProgressUpdate Associations
  private $lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aDescription, $aDate, $aLab)
  {
    $this->description = $aDescription;
    $this->date = $aDate;
    $didAddLab = $this->setLab($aLab);
    if (!$didAddLab)
    {
      throw new Exception("Unable to create progressUpdate due to lab");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setDescription($aDescription)
  {
    $wasSet = false;
    $this->description = $aDescription;
    $wasSet = true;
    return $wasSet;
  }

  public function setDate($aDate)
  {
    $wasSet = false;
    $this->date = $aDate;
    $wasSet = true;
    return $wasSet;
  }

  public function getDescription()
  {
    return $this->description;
  }

  public function getDate()
  {
    return $this->date;
  }

  public function getLab()
  {
    return $this->lab;
  }

  public function setLab($aLab)
  {
    $wasSet = false;
    if ($aLab == null)
    {
      return $wasSet;
    }
    
    $existingLab = $this->lab;
    $this->lab = $aLab;
    if ($existingLab != null && $existingLab != $aLab)
    {
      $existingLab->removeProgressUpdate($this);
    }
    $this->lab->addProgressUpdate($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $placeholderLab = $this->lab;
    $this->lab = null;
    $placeholderLab->removeProgressUpdate($this);
  }

}
?>