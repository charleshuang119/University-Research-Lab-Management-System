<?php
if(!isset($_SESSION)){ //Set Session only once
    session_start();
}

class PersistenceURLMS {
	private $filename;
	
	function __construct($filename = 'data.txt') {
		$this->filename = $filename;
	}
	
    public static function isData(){
    }
	function loadDataFromStore() {
	    $urlms = new URLMS(); //Instantiate URLMS every time
		if (file_exists($this->filename)){ //If file exists
			$str = file_get_contents($this->filename);
			if(strlen($str)>1){ //If file is not empty
			    $currlab = unserialize($str); //load lab object
			    $urlms->setSingleLab($currlab); //set urlms current lab
			    return $urlms;
			}
		} //else if the file doesn't exist or is empty
		else {
		    $fa =new FundingAccount();
		    $iv =new Inventory();
		    $currlab = new Lab($fa,$iv,$urlms);		    
		    $urlms->setSingleLab($currlab);		    
		    return $urlms;
		}
		
	}
	
	function writeDataToStore($rm) { //Write to RM or rather 
		$str = serialize($rm);
		file_put_contents($this->filename, $str);
	}
}
?>