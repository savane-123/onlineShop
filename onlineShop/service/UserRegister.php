<?php
class UserRigister
{
  private $con;

  function __construct()
  {
     //require_once dirname(__FILE__).'../Dbconfig/DBConnect.php';
     require_once "../Dbconfig/DBConnect.php";
    require_once "../service/SendEmail.php";
    require_once "../service/sendopt.php";
     $db=new DBConnect;
     $this->con=$db->connect();
  }

public function createUser($firstName,$lastName,$email,$phone,$dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType){
  if (!$this->isEmailExist($email)) {
     $verifyEmail=$this->generateString(30);
     $send=new SendMail;
      $sendResult=$send->sendConformEmail($email,$verifyEmail);
      if ($sendResult) {
    $status=0;
  $sql=$this->con->prepare("INSERT INTO User(first_name, LastName, Email,Phone,DateOfBirth,Password,DateOfregistration, DateOfUpdate,Gender,verify_email,verify_status,address_id,UserType)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
  $sql->bind_param("sssssssssssss",$firstName,$lastName,$email,$phone,
  $dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$verifyEmail,$status,$addressId,$userType);
  if($sql->execute()){
    return 200;
  }
  else{
    return 404;
   }
 }else {
   return 404;
 }
}else {
  return 405;
}
}

public function isEmailExist($email){
  $sql=$this->con->prepare("SELECT * FROM User WHERE Email=?");
  $sql->bind_param("s",$email);
  $sql->execute();
  $sql->store_result();
  return $sql->num_rows  > 0;
}

public function updateUser($firstName,$lastName,$email,$phone,$dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType){
if ($this->isEmailExist($email)) {
  $sql=$this->con->prepare("UPDATE User SET first_name=?,LastName=?,Email,Phone=?,DateOfBirth=?,Password=?,DateOfregistration=?,DateOfUpdate=?,Gender=?,verify_email,verify_status,address_id=?,UserType=?) WHERE Email=?");
  $sql->bind_param("sssssssssss",$firstName,$lastName,$email,$phone,$dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType);
  if ($sql->execute()) {
    return 201;
  }else {
    return 404;
  }
}else {
  return 405;
}
}
public function deleteUser($email){
  if ($this->isEmailExist($email)) {
    $sql1=$this->con->prepare("SELECT UserId FROM User WHERE Email=?");
    $sql1->bind_param("s",$email);
    $sql1->execute();
    $sql1->bind_result($id);
    $sql1->fetch();
    require_once "../Dbconfig/DBConnect.php";
    $db=new DBConnect;
    $conn=$db->connect();
    $query="DELETE FROM User WHERE UserId=$id";
    $ex=mysqli_query($conn,$query);
    if ($ex) {

      return 201;
    }else {
      return 404;
    }
  }else {
    return 405;
  }

}
public function getAll(){
$sql=$this->con->prepare("SELECT UserId,first_name, LastName, Email, Phone,DateOfBirth,Password,DateOfregistration, DateOfUpdate, Gender,verify_email,verify_status, address_id, UserType FROM User");
$sql->execute();
$sql->bind_result($id,$firstName,$lastName,$email,$phone,$dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$verifyEmail,$verifyStatus,$addressId,$userType);
  $users=array();
while($sql->fetch()){
  $user=array();
  $user['id']=$id;
  $user['first name']=$firstName;
  $user['last name']=$lastName;
  $user['email']=$email;
  $user['phone']=$phone;
  $user['date of birth']=$dateOfBarth;
  $user['password']=$password;
  $user['date of registration']=$dateOfRegistration;
  $user['date of update']=$DateOfUpdate;
  $user['gender']=$gender;
  $user['verify email']=$verifyEmail;
  $user['verify status']=$verifyStatus;
  $user['AddressId']=$addressId;
  $user['user type']=$userType;
array_push($users,$user);
}
  return $users;
}
public function getUserIdByEmail($email)
{
      $sql=$this->con->prepare("SELECT user_id FROM User WHERE Email=? ");
      $sql->bind_param("s",$email);
      $sql->execute();
      $sql->bind_result($userId);
      $sql->fetch();
      return $userId;
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
  if ($this->isCountryIdExist($countryId)) {
    $counId = $this->getStateBycountryIdl($countryId);
$sql=$this->con->prepare("SELECT * FROM State WHERE CountryId=?");
$sql->execute();
$sql->bind_result($stateId,$stateName,$countryId);
  $States=array();
while($sql->fetch()){
  $state=array();
  $country['State id']=$stateId;
  $country['State name']=$countryName;
  $country['Country id']=$countryId;
array_push($States,$state);
}
  return $States;
}
}

public function getVerifyEmail($verifyEmail)
{
  $sql=$this->con->prepare("SELECT * FROM User WHERE verify_email=?");
  $sql->bind_param("s",$verifyEmail);
  $sql->execute();
  $sql->store_result();
  return $sql->num_rows  > 0;
}
public function updateVerifyStatus($verifyEmail)
{
  if ($this->getVerifyEmail($verifyEmail)) {
$sql=$this->con->prepare("UPDATE User SET verify_email=?,verify_status=? WHERE verify_email =?");
$status=true;
$updateValue=Null;
$sql->bind_param("sss",$updateValue,$status,$verifyEmail);
if($sql->execute()){
  return 200;
}else {
  return 404;
}
  }else {
    return 500;
  }
}
public function sendOtp($email){
  if ($this->isVerify($email)) {
  $send=new sendOtp;
  $sendResult=$send->conformPassword($email);
    if($sendResult){
      return 200;
    }else{
      return 400;
    }
}else {
  return 404;
}
}
public function isVerify($email)
{
  $status=true;
  $sql1=$this->con->prepare("SELECT * FROM User WHERE Email=? AND verify_status=?");
  $sql1->bind_param("ss",$email,$status);
  $sql1->execute();
  $sql1->store_result();
  return $sql1->num_rows  > 0;
}
public function generateString() {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randomString = '';

    for ($i = 0; $i < 40; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }

    return $randomString;
}
}
  ?>
