<?php
session_start();
if($_SERVER['REQUEST_METHOD']=='POST' and isset($_POST['password']) and isset($_POST['password1'])){
if (empty($_POST['password']) or empty($_POST['password1'])) {
	echo "place write in all feild";
}
   else{
	$password=$_POST['password'];
	$password1=$_POST['password1'];
  $username=$_SESSION["username"];
  $email=$_SESSION["email"];
	if($password==$password1) {
    $pass=sha1($password);
		$con=mysqli_connect('localhost','root','');
    $select=mysqli_select_db($con,'test');
    $query="UPDATE login SET password='$pass' WHERE username='$username' and email='$email'";
    $ex=mysqli_query($con,$query);
    session_unset();
    if($ex) {
    	          header("location:test.php");
    }
    else
     {
    	   echo "password not update";

    }
	  }
  else
	{
		echo "passwords is not match";
	}

}
}
 ?>
