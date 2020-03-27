<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/../vendor/autoload.php';
require_once "../service/AddressService.php";
$app = AppFactory::create();

$app->setBasePath("/slim/onlineShop/onlineShop/RestApi/AddressApi.php");
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
          $message['message']='Address Created Successfuly';
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
$app->get('/getAllCountries', function (Request $request, Response $response, array $args) {
		$db = new AddressService;
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
		$db = new AddressService;
		$states=$db->getAllStates($countryId);
		$response_data=array();
		$response_data['error']=false;
		$response_data['States']=$states;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(201);
}
});
$app->post('/getCitiesByStateId', function (Request $request, Response $response, array $args) {
  if(!haveEmptyParameters(array('stateId'),$response)){
      $request_data=$request->getParsedBody();
      $stateId=$request_data['stateId'];
		$db = new AddressService;
		$cities=$db->getCitiesByStateId($stateId);
		$response_data=array();
		$response_data['error']=false;
		$response_data['Cities']=$cities;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(201);
}
});
$app->run();
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
