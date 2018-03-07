<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class FundingAccount
{
    
    //------------------------
    // MEMBER VARIABLES
    //------------------------
    
    //FundingAccount Attributes
    private $allocatedAmount;
    private $remainingAmount;
    
    //FundingAccount Associations
    private $expenses;
    private $lab;
    
    //------------------------
    // CONSTRUCTOR
    //------------------------
    
    public function __construct($aLab = null)
    {
        if (func_num_args() == 0) { return; }
        
        $this->expenses = array();
        if ($aLab == null || $aLab->getFundingAccount() != null)
        {
            throw new Exception("Unable to create FundingAccount due to aLab");
        }
        $this->lab = $aLab;
    }
    public static function newInstance($aInventoryForLab, $aURLMSForLab)
    {
        $thisInstance = new FundingAccount();
        $this->expenses = array();
        $thisInstance->lab = new Lab($thisInstance, $aInventoryForLab, $aURLMSForLab);
        return $thisInstance;
    }
    
    //------------------------
    // INTERFACE
    //------------------------
    
    public function setAllocatedAmount($aAllocatedAmount)
    {
        $wasSet = false;
        $this->allocatedAmount = $aAllocatedAmount;
        $wasSet = true;
        return $wasSet;
    }
    
    public function setRemainingAmount($aRemainingAmount)
    {
        $wasSet = false;
        $this->remainingAmount = $aRemainingAmount;
        $wasSet = true;
        return $wasSet;
    }
    
    public function getAllocatedAmount()
    {
        return $this->allocatedAmount;
    }
    
    public function getRemainingAmount()
    {
        return $this->remainingAmount;
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
    
    public function getLab()
    {
        return $this->lab;
    }
    
    public static function minimumNumberOfExpenses()
    {
        return 0;
    }
    
    public function addExpenseVia($aDescription, $aAmount, $aDate)
    {
        return new Expense($aDescription, $aAmount, $aDate, $this);
    }
    
    public function addExpense($aExpense)
    {
        $wasAdded = false;
        if ($this->indexOfExpense($aExpense) !== -1) { return false; }
        $existingFundingAccount = $aExpense->getFundingAccount();
        $isNewFundingAccount = $existingFundingAccount != null && $this !== $existingFundingAccount;
        if ($isNewFundingAccount)
        {
            $aExpense->setFundingAccount($this);
        }
        else
        {
            $this->expenses[] = $aExpense;
        }
        $wasAdded = true;
        return $wasAdded;
    }
    
    public function removeExpense($aExpense)
    {
        $wasRemoved = false;
        //Unable to remove aExpense, as it must always have a fundingAccount
        if ($this !== $aExpense->getFundingAccount())
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
    
    public function equals($compareTo)
    {
        return $this == $compareTo;
    }
    
    public function delete()
    {
        foreach ($this->expenses as $aExpense)
        {
            $aExpense->delete();
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