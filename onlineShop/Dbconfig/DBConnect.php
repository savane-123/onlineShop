<?php

class DBConnect
{
private $con;

public function connect(){
  $host="localhost";
  $user="root";
  $password="";
  $name="test";
  $this->con=new mysqli($host,$user,$password,$name);
if (mysqli_connect_errno()) {
  echo "failure to connect ".mysqli_connect_error();
return null;
}
return $this->con;
echo "database connected";
}
}
 ?>
