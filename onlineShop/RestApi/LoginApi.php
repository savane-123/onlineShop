<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/../vendor/autoload.php';
require_once "../service/UserLogin.php";
$app = AppFactory::create();

$app->setBasePath("/slim/onlineShop/onlineShop/RestApi/LoginApi.php");
$app->post('/userLogin', function (Request $request, Response $response, array $args) {
	if (!haveEmptyParameters(array('email','password'),$response)) {
		$request_data=$request->getParsedBody();
		$email=$request_data['email'];
		$password=$request_data['password'];
		//$pass=password_hash($password,PASSWORD_DEFAULT);
		$db = new UserLogin;
		$result=$db->userLogin($email,$password);
		if($result == 201){
		$user=$db->getUserByEmail($email);
		$response_data=array();
		$response_data['error']=false;
		$response_data['message']='User Login Successfully';
		$response_data['user']=$user;
		$response->getBody()->write(json_encode($response_data));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(200);
	}elseif ($result == 202) {

		$message=array();
		$message['error']=true;
		$message['message']='User Not Exist ';
		$response->getBody()->write(json_encode($message));
		return $response
									->withHeader('Content-type','application/json')
									->withStatus(200);
		}elseif ($result == 203) {
			$message=array();
			$message['error']=true;
			$message['message']='Invalid credential';
			$response->getBody()->write(json_encode($message));
			return $response
										->withHeader('Content-type','application/json')
										->withStatus(200);
	}
  return $response
                ->withHeader('Content-type','application/json')
                ->withStatus(200);
	}
  return $response
                ->withHeader('Content-type','application/json')
                ->withStatus(404);
});
$app->get('/test/{name}', function (Request $request, Response $response, array $args) {
  $name = $args['name'];
  $response->getBody()->write("Hello, $name");
  return $response;
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


$app->run();
