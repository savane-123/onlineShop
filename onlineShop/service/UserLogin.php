  <?php
  /**
   *
   */
  class UserLogin
  {
  private $con;
    function __construct()
    {
     require_once "../Dbconfig/DBConnect.php";
      $db=new DBConnect;
      $this->con=$db->connect();
    }
    public function userLogin($email,$password){
      if($this->isEmailExist($email)){
        $hashed_password= $this->getUserPasswordByEmail($email);
        if(password_verify($password,$hashed_password)){
          return 201;

        }else {

          return 202;
        }
      }else {
        return 203;
      }

    }
    public function isEmailExist($email){
      $sql=$this->con->prepare("SELECT * FROM User WHERE Email=?");
      $sql->bind_param("s",$email);
      $sql->execute();
      $sql->store_result();
      return $sql->num_rows  > 0;
    }
    public function getUserPasswordByEmail($email)
  {
  $sql=$this->con->prepare("SELECT Password FROM User WHERE Email=? ");
  $sql->bind_param("s",$email);
  $sql->execute();
  $sql->bind_result($password);
  $sql->fetch();
  return $password;
  }
  public function getUserByEmail($email)
  {
  $sql=$this->con->prepare("SELECT user_id,first_name, LastName, Email, Phone,DateOfBirth, Password,DateOfregistration, DateOfUpdate, Gender, address_id, UserType
 FROM User WHERE Email=? ");
  $sql->bind_param("s",$email);
  $sql->execute();
  $sql->bind_result($id,$firstName,$lastName,$email,$phone,$dateOfBirth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType);
  $sql->fetch();
  $user=array();
  $user['id']=$id;
  $user['first name']=$firstName;
  $user['last name']=$lastName;
  $user['email']=$email;
  $user['phone']=$phone;
  $user['date of birth']=$dateOfBirth;
  $user['password']=$password;
  $user['date of registration']=$dateOfRegistration;
  $user['date of update']=$DateOfUpdate;
  $user['gender']=$gender;
  $user['AddressId']=$addressId;
  $user['user type']=$userType;
  return $user;
}

  }

   ?>
