<?php 
session_start();
$opt=$_SESSION["opt"];
echo $opt;
if($_SERVER['REQUEST_METHOD']=='POST' and isset($_POST['opt'])){
if (empty($_POST['opt'])) {
	echo "place write in all feild";
}else{
	$opt1=$_POST['opt'];
	if($opt1==$opt){
		 header("Location:updatePass.php");
   			exit;
	}else{
		echo "opt is not match";
	}

}
}
?>
  <form action="#" method="post" class="from" name="form1" onsubmit="return validate()">
   <p>Opt:</p><input type="number" name="opt" id="opt"/><br/>
   <br/>
    <input type="submit" name="submit" value="SUBMIT"/> 
    <input type="reset" name="cancel" value="CANCEL" />
      <br />
      
      <a href="createaccount1.php">
        <input type="button" value="CREATE ACCOUNT"/>
        <footer class="footer-basic-centered">

      <p class="footer-company-motto">Belem design <br/>
                 Email: belemedesign@gmail.com <br/>
         Telephone: 00225 05 01 30 60/07 30 60 40  
        </p>

       
      <p class="footer-company-name">Croyez nous vous faite la difference  &copy; 2018</p>

    </footer>
      </a>
  </form>
