<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class Inventory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Inventory Associations
  private $resources;
  private $lab;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aLab = null)
  {
    if (func_num_args() == 0) { return; }

    $this->resources = array();
    if ($aLab == null || $aLab->getInventory() != null)
    {
      throw new Exception("Unable to create Inventory due to aLab");
    }
    $this->lab = $aLab;
  }
  public static function newInstance($aFundingAccountForLab, $aURLMSForLab)
  {
    $thisInstance = new Inventory();
    $this->resources = array();
    $thisInstance->lab = new Lab($aFundingAccountForLab, $thisInstance, $aURLMSForLab);
    return $thisInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getResource_index($index)
  {
    $aResource = $this->resources[$index];
    return $aResource;
  }

  public function getResources()
  {
    $newResources = $this->resources;
    return $newResources;
  }

  public function numberOfResources()
  {
    $number = count($this->resources);
    return $number;
  }

  public function hasResources()
  {
    $has = $this->numberOfResources() > 0;
    return $has;
  }

  public function indexOfResource($aResource)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->resources as $resource)
    {
      if ($resource->equals($aResource))
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

  public static function minimumNumberOfResources()
  {
    return 0;
  }

  public function addResourceVia($aQuantity, $aName)
  {
    return new Resource($aQuantity, $aName, $this);
  }

  public function addResource($aResource)
  {
    $wasAdded = false;
    if ($this->indexOfResource($aResource) !== -1) { return false; }
    $existingInventory = $aResource->getInventory();
    $isNewInventory = $existingInventory != null && $this !== $existingInventory;
    if ($isNewInventory)
    {
      $aResource->setInventory($this);
    }
    else
    {
      $this->resources[] = $aResource;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeResource($aResource)
  {
    $wasRemoved = false;
    //Unable to remove aResource, as it must always have a inventory
    if ($this !== $aResource->getInventory())
    {
      unset($this->resources[$this->indexOfResource($aResource)]);
      $this->resources = array_values($this->resources);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addResourceAt($aResource, $index)
  {  
    $wasAdded = false;
    if($this->addResource($aResource))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfResources()) { $index = $this->numberOfResources() - 1; }
      array_splice($this->resources, $this->indexOfResource($aResource), 1);
      array_splice($this->resources, $index, 0, array($aResource));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveResourceAt($aResource, $index)
  {
    $wasAdded = false;
    if($this->indexOfResource($aResource) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfResources()) { $index = $this->numberOfResources() - 1; }
      array_splice($this->resources, $this->indexOfResource($aResource), 1);
      array_splice($this->resources, $index, 0, array($aResource));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addResourceAt($aResource, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    while (count($this->resources) > 0)
    {
      $aResource = $this->resources[count($this->resources) - 1];
      $aResource->delete();
      unset($this->resources[$this->indexOfResource($aResource)]);
      $this->resources = array_values($this->resources);
    }
    
    $existingLab = $this->lab;
    $this->lab = null;
    if ($existingLab != null)
    {
      $existingLab->delete();
    }
  }

}
?>