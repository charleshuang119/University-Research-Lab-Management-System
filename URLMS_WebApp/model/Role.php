<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Role Attributes
  private $salary;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {
    $this->salary = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setSalary($aSalary)
  {
    $wasSet = false;
    $this->salary = $aSalary;
    $wasSet = true;
    return $wasSet;
  }

  public function getSalary()
  {
    return $this->salary;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>