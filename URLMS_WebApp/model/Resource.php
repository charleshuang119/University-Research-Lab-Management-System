<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class Resource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Resource Attributes
  private $quantity;
  private $name;

  //Resource Associations
  private $inventory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aQuantity, $aName, $aInventory)
  {
    $this->quantity = $aQuantity;
    $this->name = $aName;
    $didAddInventory = $this->setInventory($aInventory);
    if (!$didAddInventory)
    {
      throw new Exception("Unable to create resource due to inventory");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setQuantity($aQuantity)
  {
    $wasSet = false;
    $this->quantity = $aQuantity;
    $wasSet = true;
    return $wasSet;
  }

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function getQuantity()
  {
    return $this->quantity;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getInventory()
  {
    return $this->inventory;
  }

  public function setInventory($aInventory)
  {
    $wasSet = false;
    if ($aInventory == null)
    {
      return $wasSet;
    }
    
    $existingInventory = $this->inventory;
    $this->inventory = $aInventory;
    if ($existingInventory != null && $existingInventory != $aInventory)
    {
      $existingInventory->removeResource($this);
    }
    $this->inventory->addResource($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $placeholderInventory = $this->inventory;
    $this->inventory = null;
    $placeholderInventory->removeResource($this);
  }

}
?>