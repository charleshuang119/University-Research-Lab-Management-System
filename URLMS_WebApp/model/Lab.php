<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class Lab
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Lab Associations
  private $staff;
  private $progressUpdates;
  private $fundingAccount;
  private $inventory;
  public $director; //until PHP 5.3 (setAccessible)
  private $uRLMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aFundingAccount = null, $aInventory = null, $aURLMS = null)
  {
    if (func_num_args() == 0) {
       // $this->staff = array();
        return; 
    }

    $this->staff = array();
    $this->progressUpdates = array();
    if ($aFundingAccount == null || $aFundingAccount->getLab() != null)
    {
      throw new Exception("Unable to create Lab due to aFundingAccount");
    }
    $this->fundingAccount = $aFundingAccount;
    if ($aInventory == null || $aInventory->getLab() != null)
    {
      throw new Exception("Unable to create Lab due to aInventory");
    }
    $this->inventory = $aInventory;
    $didAddURLMS = $this->setURLMS($aURLMS);
    if (!$didAddURLMS)
    {
      throw new Exception("Unable to create singleLab due to uRLMS");
    }
  }
  public static function newInstance($aURLMS)
  {
    $thisInstance = new Lab();
    $this->staff = array();
    $this->progressUpdates = array();
    $thisInstance->fundingAccount = new FundingAccount($thisInstance);
    $thisInstance->inventory = new Inventory($thisInstance);$this->uRLMSs = array();
    $this->uRLMSs[] = $aURLMS;
    return $thisInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getStaff_index($index)
  {
    $aStaff = $this->staff[$index];
    return $aStaff;
  }

  public function getStaff()
  {
    $newStaff = $this->staff;
    return $newStaff;
  }

  public function numberOfStaff()
  {
    $number = count($this->staff);
    return $number;
  }

  public function hasStaff()
  {
    $has = $this->numberOfStaff() > 0;
    return $has;
  }

  public function indexOfStaff($aStaff)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->staff as $staff)
    {
      if ($staff->equals($aStaff))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getProgressUpdate_index($index)
  {
    $aProgressUpdate = $this->progressUpdates[$index];
    return $aProgressUpdate;
  }

  public function getProgressUpdates()
  {
    $newProgressUpdates = $this->progressUpdates;
    return $newProgressUpdates;
  }

  public function numberOfProgressUpdates()
  {
    $number = count($this->progressUpdates);
    return $number;
  }

  public function hasProgressUpdates()
  {
    $has = $this->numberOfProgressUpdates() > 0;
    return $has;
  }

  public function indexOfProgressUpdate($aProgressUpdate)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->progressUpdates as $progressUpdate)
    {
      if ($progressUpdate->equals($aProgressUpdate))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getFundingAccount()
  {
    return $this->fundingAccount;
  }

  public function getInventory()
  {
    return $this->inventory;
  }

  public function getDirector()
  {
    return $this->director;
  }

  public function hasDirector()
  {
    $has = $this->director != null;
    return $has;
  }

  public function getURLMS()
  {
    return $this->uRLMS;
  }

  public static function minimumNumberOfStaff()
  {
    return 0;
  }

  public function addStaffVia($aFirstName, $aLastName)
  {
    return new Staff($aFirstName, $aLastName, $this);
  }

  public function addStaff($aStaff)
  {
    $wasAdded = false;
    if ($this->indexOfStaff($aStaff) !== -1) { return false; }
    $existingLab = $aStaff->getLab();
    $isNewLab = $existingLab != null && $this !== $existingLab;
    if ($isNewLab)
    {
      $aStaff->setLab($this);
    }
    else
    {
      $this->staff[] = $aStaff;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeStaff($aStaff)
  {
    $wasRemoved = false;
    //Unable to remove aStaff, as it must always have a lab
    if ($this !== $aStaff->getLab())
    {
      unset($this->staff[$this->indexOfStaff($aStaff)]);
      $this->staff = array_values($this->staff);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addStaffAt($aStaff, $index)
  {  
    $wasAdded = false;
    if($this->addStaff($aStaff))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStaff()) { $index = $this->numberOfStaff() - 1; }
      array_splice($this->staff, $this->indexOfStaff($aStaff), 1);
      array_splice($this->staff, $index, 0, array($aStaff));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveStaffAt($aStaff, $index)
  {
    $wasAdded = false;
    if($this->indexOfStaff($aStaff) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfStaff()) { $index = $this->numberOfStaff() - 1; }
      array_splice($this->staff, $this->indexOfStaff($aStaff), 1);
      array_splice($this->staff, $index, 0, array($aStaff));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addStaffAt($aStaff, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfProgressUpdates()
  {
    return 0;
  }

  public function addProgressUpdateVia($aDescription, $aDate)
  {
    return new ProgressUpdate($aDescription, $aDate, $this);
  }

  public function addProgressUpdate($aProgressUpdate)
  {
    $wasAdded = false;
    if ($this->indexOfProgressUpdate($aProgressUpdate) !== -1) { return false; }
    $existingLab = $aProgressUpdate->getLab();
    $isNewLab = $existingLab != null && $this !== $existingLab;
    if ($isNewLab)
    {
      $aProgressUpdate->setLab($this);
    }
    else
    {
      $this->progressUpdates[] = $aProgressUpdate;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeProgressUpdate($aProgressUpdate)
  {
    $wasRemoved = false;
    //Unable to remove aProgressUpdate, as it must always have a lab
    if ($this !== $aProgressUpdate->getLab())
    {
      unset($this->progressUpdates[$this->indexOfProgressUpdate($aProgressUpdate)]);
      $this->progressUpdates = array_values($this->progressUpdates);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addProgressUpdateAt($aProgressUpdate, $index)
  {  
    $wasAdded = false;
    if($this->addProgressUpdate($aProgressUpdate))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfProgressUpdates()) { $index = $this->numberOfProgressUpdates() - 1; }
      array_splice($this->progressUpdates, $this->indexOfProgressUpdate($aProgressUpdate), 1);
      array_splice($this->progressUpdates, $index, 0, array($aProgressUpdate));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveProgressUpdateAt($aProgressUpdate, $index)
  {
    $wasAdded = false;
    if($this->indexOfProgressUpdate($aProgressUpdate) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfProgressUpdates()) { $index = $this->numberOfProgressUpdates() - 1; }
      array_splice($this->progressUpdates, $this->indexOfProgressUpdate($aProgressUpdate), 1);
      array_splice($this->progressUpdates, $index, 0, array($aProgressUpdate));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addProgressUpdateAt($aProgressUpdate, $index);
    }
    return $wasAdded;
  }

  public function setDirector($aNewDirector)
  {
    $wasSet = false;
    $this->director = $aNewDirector;
    $wasSet = true;
    return $wasSet;
  }

  public function setURLMS($aNewURLMS)
  {
    $wasSet = false;
    if ($aNewURLMS == null)
    {
      //Unable to setURLMS to null, as singleLab must always be associated to a uRLMS
      return $wasSet;
    }
    
    $existingSingleLab = $aNewURLMS->getSingleLab();
    if ($existingSingleLab != null && $this != $existingSingleLab)
    {
      //Unable to setURLMS, the current uRLMS already has a singleLab, which would be orphaned if it were re-assigned
      return $wasSet;
    }
    
    $anOldURLMS = $this->uRLMS;
    $this->uRLMS = $aNewURLMS;
    $this->uRLMS->setSingleLab($this);
    
    if ($anOldURLMS != null)
    {
      $anOldURLMS->setSingleLab(null);
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
    while (count($this->staff) > 0)
    {
      $aStaff = $this->staff[count($this->staff) - 1];
      $aStaff->delete();
      unset($this->staff[$this->indexOfStaff($aStaff)]);
      $this->staff = array_values($this->staff);
    }
    
    while (count($this->progressUpdates) > 0)
    {
      $aProgressUpdate = $this->progressUpdates[count($this->progressUpdates) - 1];
      $aProgressUpdate->delete();
      unset($this->progressUpdates[$this->indexOfProgressUpdate($aProgressUpdate)]);
      $this->progressUpdates = array_values($this->progressUpdates);
    }
    
    $existingFundingAccount = $this->fundingAccount;
    $this->fundingAccount = null;
    if ($existingFundingAccount != null)
    {
      $existingFundingAccount->delete();
    }
    $existingInventory = $this->inventory;
    $this->inventory = null;
    if ($existingInventory != null)
    {
      $existingInventory->delete();
    }
    $this->director = null;
    $existingURLMS = $this->uRLMS;
    $this->uRLMS = null;
    if ($existingURLMS != null)
    {
      $existingURLMS->setSingleLab(null);
    }
  }

}
?>