<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/../vendor/autoload.php';
require_once "../service/UserRegister.php";
$app = AppFactory::create();

$app->setBasePath("/slim/onlineShop/onlineShop/RestApi/RegisterApi.php");
  $app->post('/createUser', function (Request $request, Response $response, array $args) {
  if(!haveEmptyParameters(array('Firstname','LastName','Email','Phone','DateOfBirth','Password','Gender'),$response)){
      $request_data=$request->getParsedBody();
      $firstName=$request_data['Firstname'];
      $lastName=$request_data['LastName'];
      $email=$request_data['Email'];
      $phone=$request_data['Phone'];
      $dateOfBarth=str_replace("/", "-", $request_data['DateOfBirth']);
      $password=$request_data['Password'];
      $gender=$request_data['Gender'];
      $addressId=null;
      $userType=Null;
      $dateOfRegistration=date("Y-m-d");
      $DateOfUpdate=null;
          $db=new UserRigister;
          $pass=password_hash($password,PASSWORD_DEFAULT);
          $active=true;
          $rs=$db->createUser($firstName,$lastName,$email,$phone,$dateOfBarth,$pass,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType);
          if($rs==200){
            $message=array();
            $message['error']=false;
            $message['message']='User Created Successfuly';
            $response->getBody()->write(json_encode($message));
            return $response
                          ->withHeader('Content_type','application/json')
                          ->withStatus(201);
          }
          elseif($rs==404){
            $message=array();
            $message['error']=true;
            $message['message']='some error is occurred'.$dateOfBarth;
            $response->getBody()->write(json_encode($message));
            return $response
                          ->withHeader('Content_type','application/json')
                          ->withStatus(422);
          }
          elseif($rs==405){
            $message=array();
            $message['error']=true;
            $message['messae']='User is Exist';
            $response->getBody()->write(json_encode($message));
            return $response
                          ->withHeader('Content_type','application/json')
                          ->withStatus(404);
          }

    }else{
    return $response
                  ->withHeader('Content_type','application/json')
                  ->withStatus(422);
  }
});
$app->post('/updateUser', function (Request $request, Response $response, array $args) {
if(!haveEmptyParameters(array('Firstname','LastName','Email','Phone','DateOfBirth','Password','DateOfregistration','DateOfUpdate','Gender','AddressId','UserType'),$response)){
    $request_data=$request->getParsedBody();
    $firstName=$request_data['Firstname'];
    $lastName=$request_data['LastName'];
    $email=$request_data['Email'];
    $phone=$request_data['Phone'];
    $dateOfBarth=$request_data['DateOfBirth'];
    //$password=$request_data['Password'];
    $dateOfRegistration=$request_data['DateOfregistration'];
    $DateOfUpdate=$request_data['DateOfUpdate'];
    $gender=$request_data['Gender'];
    $addressId=$request_data['AddressId'];
    $userType=$request_data['UserType'];
        $db=new UserRigister;
      //  $pass=password_hash($password,PASSWORD_DEFAULT);
        $active=true;
        $rs=$db->updateUser($firstName,$lastName,$email,$phone,$dateOfBarth,$password,$dateOfRegistration,$DateOfUpdate,$gender,$addressId,$userType);
        if($rs==201){
          $message=array();
          $message['error']=false;
          $message['message']='User Updated Successfuly';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(201);
        }
        elseif($rs==404){
          $message=array();
          $message['error']=true;
          $message['messae']='some eroor is occurred';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(422);
        }
        elseif($rs==405){
          $message=array();
          $message['error']=true;
          $message['messae']='User is Not  Exist';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(404);
        }

  }else{

  return $response
                ->withHeader('Content_type','application/json')
                ->withStatus(422);
}
});
$app->post('/deleteUser', function (Request $request, Response $response, array $args) {
    $request_data=$request->getParsedBody();
    $email=$request_data['email'];
    //$email=$str = ltrim($email, '?');
        $db=new UserRigister;
        $rs=$db->deleteUser($email);
        if($rs==201){
          $message=array();
          $message['error']=false;
          $message['message']='User Deleted Successfuly';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(201);
        }
        elseif($rs==404){
          $message=array();
          $message['error']=true;
          $message['message']='some eroor is occurred';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(422);
        }
        elseif($rs==405){
          $message=array();
          $message['error']=true;
          $message['message']='User is Not  Exist';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(404);
        }
});
$app->get('/getAll', function (Request $request, Response $response, array $args) {
		$db = new UserRigister;
		$users=$db->getAll();
		$response_data=array();
		$response_data['error']=false;
		$response_data['users']=$users;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(201);
});
$app->post('/addAddress', function (Request $request, Response $response, array $args) {
if(!haveEmptyParameters(array('email','address','longitude','lagitude','city','state','country'),$response)){
    $request_data=$request->getParsedBody();
    $email=$request_data['email'];
    $address=$request_data['address'];
    $longitude=$request_data['longitude'];
    $lagitude=$request_data['lagitude'];
    $city=$request_data['city'];
    $state=$request_data['state'];
    $country=$request_data['country'];
        $db=new UserRigister;
        $rs=$db->createAddress($email,$address,$longitude,$lagitude,$city,$state,$country);
        if($rs==200){
          $message=array();
          $message['error']=false;
          $message['message']='User Created Successfuly';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(201);
        }
        elseif($rs==404){
          $message=array();
          $message['error']=true;
          $message['message']='some error is occurred'.$dateOfBarth;
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(422);
        }
        elseif($rs==405){
          $message=array();
          $message['error']=true;
          $message['messae']='User is Not Exist';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(404);
        }

  }else{
  return $response
                ->withHeader('Content_type','application/json')
                ->withStatus(422);
}
});
 function haveEmptyParameters($required_params,$response){
  $error=false;
  $error_params='';
  $request_params=$_REQUEST;
  foreach ($required_params as $param) {
    if(!isset($request_params[$param]) || strlen($request_params[$param])<=0){
    $error=true;
    $error_params .=$param .',';
  }
}
  if ($error) {
    $error_detail=array();
    $error_detail['error']=true;
    $error_detail['message']='require param :'.substr($error_params,0,-1).' are missing or empty';
    $response->getBody()->write(json_encode($error_detail));
  }

  return $error;
}
$app->get('/getAllCountries', function (Request $request, Response $response, array $args) {
		$db = new UserRigister;
		$countries=$db->getAllCountries();
		$response_data=array();
		$response_data['error']=false;
		$response_data['countries']=$countries;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(201);
});
$app->post('/getAllStates', function (Request $request, Response $response, array $args) {
  if(!haveEmptyParameters(array('CountryId'),$response)){
      $request_data=$request->getParsedBody();
      $countryId=$request_data['CountryId'];
		$db = new UserRigister;
		$states=$db->getAllStates($countryId,$lastName,$countryId);
		$response_data=array();
		$response_data['error']=false;
		$response_data['States']=$states;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(201);
}
});
$app->get('/verifyEmail', function (Request $request, Response $response, array $args) {
  $app = AppFactory::create();
    $verifyEmail =$request->getQueryParams()['token'];
    $db = new UserRigister;
    $result=$db->updateVerifyStatus($verifyEmail);
    if ($result==200) {
      $response_data=array();
      //$response_data['error']=false;
      $response_data['message']="SUCCESS";
      $response->getBody()->write(json_encode($response_data));
      return $response
                    ->withHeader('Content-type','application/json')
                    ->withStatus(200);
    }elseif ($result==404)
     {
      $response_data=array();
      //$response_data['error']=true;
      $response_data['message']="ERROR ".$verifyEmail;
      $response->getBody()->write(json_encode($response_data));
      return $response
                    ->withHeader('Content-type','application/json')
                    ->withStatus(400);
    }else {
      $response_data=array();
      //$response_data['error']=true;
      $response_data['message']="ERROR ".$verifyEmail;
      $response->getBody()->write(json_encode($response_data));
      return $response
                    ->withHeader('Content-type','application/json')
                    ->withStatus(500);
    }

});
$app->post('/sendResetOtp', function (Request $request, Response $response, array $args) {
    $request_data=$request->getParsedBody();
    $email=$request_data['email'];
    //$email=$str = ltrim($email, '?');
        $db=new UserRigister;
        $rs=$db->sendOtp($email);
        if($rs==200){
          $message=array();
          $message['error']=false;
          $message['message']='The otp has been sent';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(201);
        }
        elseif($rs==400){
          $message=array();
          $message['error']=true;
          $message['message']='some eroor is occurred';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(422);
        }
        elseif($rs==404){
          $message=array();
          $message['error']=true;
          $message['message']='User is Not  Exist';
          $response->getBody()->write(json_encode($message));
          return $response
                        ->withHeader('Content_type','application/json')
                        ->withStatus(404);
        }
});
$app->run();
