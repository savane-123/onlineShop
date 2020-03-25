<?php
class AddressService
{
  private $con;
  function __construct()
  {
     //require_once dirname(__FILE__).'../Dbconfig/DBConnect.php';
     require_once "../Dbconfig/DBConnect.php";
     $db=new DBConnect;
     $this->con=$db->connect();
  }
  public function createAddress($email,$address,$longitude,$lagitude,$city,$state,$country){
    if ($this->isEmailExist($email)) {
      $userId = $this->getUserIdByEmail($email);
    $sql=$this->con->prepare("INSERT INTO user_address (a_address, a_longitude, a_lagitude, city, state, country, user_id)
     VALUES ( ?, ?, ?, ?, ?, ?, ?)");
    $sql->bind_param("sssssss",$address,$longitude,$lagitude,$city,$state,$country,$userId);
    if($sql->execute()){
      return 200;
    }
    else{
      return 404;
     }
  }else {
    return 405;
  }
  }
      public function getAllCountries(){
      $sql=$this->con->prepare("SELECT * FROM Country");
      $sql->execute();
      $sql->bind_result($countryId,$countryName);
        $countries=array();
      while($sql->fetch()){
        $country=array();
        $country['id']=$countryId;
        $country['Country name']=$countryName;
      array_push($countries,$country);
      }
        return $countries;
      }
      public function getAllStates($countryId){
      $sql=$this->con->prepare("SELECT * FROM State WHERE CountryId=?");
        $sql->bind_param("s",$countryId);
      $sql->execute();
      $sql->bind_result($stateId,$stateName,$countryId);
        $states=array();
      while($sql->fetch()){
        $state=array();
        $state['State id']=$stateId;
        $state['State name']=$stateName;
        $state['Country id']=$countryId;
      array_push($states,$state);
      }
        return $states;
      }

      public function getCitiesByStateId($stateId){
      $sql=$this->con->prepare("SELECT * FROM City WHERE 	StateId=?");
        $sql->bind_param("s",$stateId);
      $sql->execute();
      $sql->bind_result($cityId,$cityName,$stateId,$countryId);
        $cities=array();
      while($sql->fetch()){
        $city=array();
        $city[' id']=$cityId;
        $city['City name']=$cityName;
        $city['State id']=$stateId;
        $city['Country id']=$countryId;
      array_push($cities,$city);
      }
        return $cities;
      }
      }



  ?>
