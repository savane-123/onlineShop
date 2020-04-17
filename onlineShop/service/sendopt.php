<?php

class sendOtp{
    function conformPassword($email){
			$mail = new PHPMailer;
         $number=mt_rand();
					//$mail->SMTPDebug = 3;
					$mail->isSMTP();
					$mail->Host = 'smtp.gmail.com';
					$mail->SMTPAuth = true;
					$mail->Username = 'savane787@gmail.com';
					$mail->Password = 'savane@123';
					$mail->SMTPSecure = 'tls';
					$mail->Port = 587;
					$mail->setFrom('savane787@gmail.com', 'onlineShop');
					$mail->addAddress($email);
					$mail->addReplyTo('savane787@gmail.com');
					$mail->isHTML(true);
					$mail->Subject = 'Reset Password';
					$mail->Body ='</br><hr><br>
						 The  Opt  is <font size="5" color="blue"> '.$number.' </font> plase dont Forget Agane <br>
						 <font size="2" color="red"> This project design by mohammed savane</font>';
					$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
					if($mail->send()){
							      return true;
					}else{
						      return false;
					}

    }
}
 ?>
