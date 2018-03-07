<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/
$my_dir = dirname(__FILE__);
require_once $my_dir . '/Resource.php';
class Equipment extends Resource
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aQuantity, $aName, $aInventory)
  {
    parent::__construct($aQuantity, $aName, $aInventory);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    parent::delete();
  }

}
?>