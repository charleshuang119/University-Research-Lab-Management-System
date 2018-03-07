<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class Staff
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextIdNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Staff Attributes
  private $firstName;
  private $lastName;

  //Autounique Attributes
  private $idNumber;

  //Staff Associations
  public $role; //until PHP 5.3 (setAccessible)
  private $expenses;
  private $progressUpdates;
  private $lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aFirstName, $aLastName, $aLab)
  {
    $this->firstName = $aFirstName;
    $this->lastName = $aLastName;
    $this->idNumber = self::$nextIdNumber++;
    $this->expenses = array();
    $this->progressUpdates = array();
    $didAddLab = $this->setLab($aLab);
    if (!$didAddLab)
    {
      throw new Exception("Unable to create staff due to lab");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setFirstName($aFirstName)
  {
    $wasSet = false;
    $this->firstName = $aFirstName;
    $wasSet = true;
    return $wasSet;
  }

  public function setLastName($aLastName)
  {
    $wasSet = false;
    $this->lastName = $aLastName;
    $wasSet = true;
    return $wasSet;
  }

  public function getFirstName()
  {
    return $this->firstName;
  }

  public function getLastName()
  {
    return $this->lastName;
  }

  public function getIdNumber()
  {
    return $this->idNumber;
  }

  public function getRole()
  {
    return $this->role;
  }

  public function hasRole()
  {
    $has = $this->role != null;
    return $has;
  }

  public function getExpense_index($index)
  {
    $aExpense = $this->expenses[$index];
    return $aExpense;
  }

  public function getExpenses()
  {
    $newExpenses = $this->expenses;
    return $newExpenses;
  }

  public function numberOfExpenses()
  {
    $number = count($this->expenses);
    return $number;
  }

  public function hasExpenses()
  {
    $has = $this->numberOfExpenses() > 0;
    return $has;
  }

  public function indexOfExpense($aExpense)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->expenses as $expense)
    {
      if ($expense->equals($aExpense))
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

  public function getLab()
  {
    return $this->lab;
  }

  public function setRole($aNewRole)
  {
    $wasSet = false;
    $this->role = $aNewRole;
    $wasSet = true;
    return $wasSet;
  }

  public static function minimumNumberOfExpenses()
  {
    return 0;
  }

  public function addExpense($aExpense)
  {
    $wasAdded = false;
    if ($this->indexOfExpense($aExpense) !== -1) { return false; }
    $this->expenses[] = $aExpense;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeExpense($aExpense)
  {
    $wasRemoved = false;
    if ($this->indexOfExpense($aExpense) != -1)
    {
      unset($this->expenses[$this->indexOfExpense($aExpense)]);
      $this->expenses = array_values($this->expenses);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addExpenseAt($aExpense, $index)
  {  
    $wasAdded = false;
    if($this->addExpense($aExpense))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfExpenses()) { $index = $this->numberOfExpenses() - 1; }
      array_splice($this->expenses, $this->indexOfExpense($aExpense), 1);
      array_splice($this->expenses, $index, 0, array($aExpense));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveExpenseAt($aExpense, $index)
  {
    $wasAdded = false;
    if($this->indexOfExpense($aExpense) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfExpenses()) { $index = $this->numberOfExpenses() - 1; }
      array_splice($this->expenses, $this->indexOfExpense($aExpense), 1);
      array_splice($this->expenses, $index, 0, array($aExpense));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addExpenseAt($aExpense, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfProgressUpdates()
  {
    return 0;
  }

  public function addProgressUpdate($aProgressUpdate)
  {
    $wasAdded = false;
    if ($this->indexOfProgressUpdate($aProgressUpdate) !== -1) { return false; }
    $this->progressUpdates[] = $aProgressUpdate;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeProgressUpdate($aProgressUpdate)
  {
    $wasRemoved = false;
    if ($this->indexOfProgressUpdate($aProgressUpdate) != -1)
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
      $existingLab->removeStaff($this);
    }
    $this->lab->addStaff($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->role = null;
    $this->expenses = array();
    $this->progressUpdates = array();
    $placeholderLab = $this->lab;
    $this->lab = null;
    $placeholderLab->removeStaff($this);
  }

}
?>