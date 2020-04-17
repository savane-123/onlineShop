<?php
require_once("../include/mail/PHPMailerAutoload.php");
	$response=array();
	if($_SERVER['REQUEST_METHOD']=='POST' and isset($_POST['status1'])){
		$status=$_POST['status1'];
		switch ($status) {
			case 'Send Password':
			if(isset($_POST['Email'])){
				require_once("include/DbOperation.php");
						$Email = $_POST['Email'];
						$db=new DbOperation();
		          $Password=$db-> getPasword($Email);
							if ($Password==1){
								$response['error']=true;
								$response['message']="Email is Not Exist";

				}else {
					$mail = new PHPMailer;
					//$mail->SMTPDebug = 3;
					$mail->isSMTP();
					$mail->Host = 'smtp.gmail.com';
					$mail->SMTPAuth = true;
					$mail->Username = 'savane787@gmail.com';
					$mail->Password = 'savane@123';
					$mail->SMTPSecure = 'tls';
					$mail->Port = 587;
					$mail->setFrom('savane787@gmail.com', 'Mazadi');
					$mail->addAddress($Email);
					$mail->addReplyTo('savane787@gmail.com');
					$mail->isHTML(true);
					$mail->Subject = 'Get Password';
					$mail->Body    = '
					<a href="https://ibb.co/RyJYFyL"><img src="https://i.ibb.co/RyJYFyL/mazadi.png" alt="mazadi" border="0"></a>
					<br>
					The  Account Password  is <font size="3" color="blue"> '.$Password.' </font> plase dont Forget Agane';
					$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
				if(!$mail->send()){
					$response['error']=true;
					$response['message']="Required fild are missing". $mail->ErrorInfo;
				}else{

						$response['error']=false;
					 $response['message']="We Sent password to ".$Email;
				}
					}
				}else {
					$response['error']=true;
					$response['message']="Email fild are missing";
				}

				break;

			default:
			if(isset($_POST['Email'])){
		        $number=mt_rand();
						$Email = $_POST['Email'];
		        $mail = new PHPMailer;
		        //$mail->SMTPDebug = 3;
		        $mail->isSMTP();
		        $mail->Host = 'smtp.gmail.com';
		        $mail->SMTPAuth = true;
		        $mail->Username = 'mazadiproject1993@gmail.com';
		        $mail->Password = 'hosam7asko77';
		        $mail->SMTPSecure = 'tls';
		        $mail->Port = 587;
		        $mail->setFrom('mazadiproject1993@gmail.com', 'Mazadi');
		        $mail->addAddress($Email);
		        $mail->addReplyTo('mazadiproject1993@gmail.com');
		        $mail->isHTML(true);
		        $mail->Subject = 'Confirm Account ';
		        $mail->Body    = '
						<a href="https://ibb.co/RyJYFyL"><img src="https://i.ibb.co/RyJYFyL/mazadi.png" alt="mazadi" border="0"></a>
						The Confirm Account number is <font size="3" color="blue"> '.$number.' </font> plase copy the number';
		        $mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
			    if(!$mail->send()){
		        $response['error']=true;
		        $response['message']="Required fild are missing". $mail->ErrorInfo;
			    }else{

		          $response['error']=false;
		         $response['Opt']=$number;
			    }
				}
				break;
		}
	}else
	{
	$response['error']=true;
	$response['message']="invalid Request";

	}
	echo json_encode($response);

?>
