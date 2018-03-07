<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-8053e45 modeling language!*/

class Expense
{
    
    //------------------------
    // MEMBER VARIABLES
    //------------------------
    
    //Expense Attributes
    private $description;
    private $amount;
    private $date;
    
    //Expense State Machines
    private static $TypeTRAVEL = 1;
    private static $TypeSALARY = 2;
    private static $TypeRESOURCE = 3;
    private static $TypeOTHER = 4;
    private $type;
    
    //Expense Associations
    private $fundingAccount;
    
    //------------------------
    // CONSTRUCTOR
    //------------------------
    
    public function __construct($aDescription, $aAmount, $aDate, $aFundingAccount)
    {
        $this->description = $aDescription;
        $this->amount = $aAmount;
        $this->date = $aDate;
        $didAddFundingAccount = $this->setFundingAccount($aFundingAccount);
        if (!$didAddFundingAccount)
        {
            throw new Exception("Unable to create expense due to fundingAccount");
        }
        $this->setType(self::$TypeOTHER); //by default expenses are accounted to Other
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
    
    public function setAmount($aAmount)
    {
        $wasSet = false;
        $this->amount = $aAmount;
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
    
    public function getAmount()
    {
        return $this->amount;
    }
    
    public function getDate()
    {
        return $this->date;
    }
    
    public function getTypeFullName()
    {
        $answer = $this->getType();
        return $answer;
    }
    
    public function getType()
    {
        if ($this->type == self::$TypeTRAVEL) { return "TypeTRAVEL"; }
        elseif ($this->type == self::$TypeSALARY) { return "TypeSALARY"; }
        elseif ($this->type == self::$TypeRESOURCE) { return "TypeRESOURCE"; }
        elseif ($this->type == self::$TypeOTHER) { return "TypeOTHER"; }
        return null;
    }
    
    public function setType($aType)
    {
        if ($aType == "TypeTRAVEL" || $aType == self::$TypeTRAVEL)
        {
            $this->type = self::$TypeTRAVEL;
            return true;
        }
        elseif ($aType == "TypeSALARY" || $aType == self::$TypeSALARY)
        {
            $this->type = self::$TypeSALARY;
            return true;
        }
        elseif ($aType == "TypeRESOURCE" || $aType == self::$TypeRESOURCE)
        {
            $this->type = self::$TypeRESOURCE;
            return true;
        }
        elseif ($aType == "TypeOTHER" || $aType == self::$TypeOTHER)
        {
            $this->type = self::$TypeOTHER;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public function getFundingAccount()
    {
        return $this->fundingAccount;
    }
    
    public function setFundingAccount($aFundingAccount)
    {
        $wasSet = false;
        if ($aFundingAccount == null)
        {
            return $wasSet;
        }
        
        $existingFundingAccount = $this->fundingAccount;
        $this->fundingAccount = $aFundingAccount;
        if ($existingFundingAccount != null && $existingFundingAccount != $aFundingAccount)
        {
            $existingFundingAccount->removeExpense($this);
        }
        $this->fundingAccount->addExpense($this);
        $wasSet = true;
        return $wasSet;
    }
    
    public function equals($compareTo)
    {
        return $this == $compareTo;
    }
    
    public function delete()
    {
        $placeholderFundingAccount = $this->fundingAccount;
        $this->fundingAccount = null;
        $placeholderFundingAccount->removeExpense($this);
    }
    
}

?>