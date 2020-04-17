<?php
require_once("../include/mail/PHPMailerAutoload.php");
   class SendMail {
                  public function sendConformEmail($email,$verifyEmail){
                    $mail = new PHPMailer;
                   //$mail->SMTPDebug = 3;
                   $mail->isSMTP();
                   $mail->Host = 'smtp.gmail.com';
                   $mail->SMTPAuth = true;
                   $mail->Username = 'savane787@gmail.com';
                   $mail->Password ='savane@123';
                   $mail->SMTPSecure = 'tls';
                   $mail->Port = 587;
                   $mail->setFrom('savane787@gmail.com', 'onlineShop');
                   $mail->addAddress($email);
                   $mail->addReplyTo('savane787@gmail.com');
                   $mail->isHTML(true);
                   $mail->Subject = 'Get Password';
                   
                   $mail->Body= 'This last step to register you have register with email :<br> <font size="3" color="blue">'
                   .$email.' </font><br> <a href="http://192.168.64.2/slim/onlineShop/TestVerify.html?token='
                   .$verifyEmail.'">click here to confirm</a>';
                   $mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
                 if(!$mail->send()){
                   return false;
                 }else{
                      return true;
                 }
                   }
                  }


?>
