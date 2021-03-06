var loginApp = angular.module("loginApp", []);
loginApp.controller("loginController", function($scope, $http, $window){
	
	$scope.checkIfEnterKeyWasPressed = function(event){
	    if (event.charCode == 13)
	        $scope.login();
	}
	
	$scope.login = function login(){
		if(!($scope.username)){
			alert("Please fill username");
			return;
		}
		if(!($scope.password)){
			alert("Please fill password");
			return;
		}
		
		var dataObj = {};
		dataObj["userName"] = $scope.username;
		dataObj["password"]= $scope.password;
		
		var req = {
				 method: 'POST',
				 url: '../rest/login/do-login',
				 /* headers: {
				   'Content-Type': 'application/json'
				 }, */
				 data: dataObj,
			}
		
		$http(req).success(function(data, status, headers, config) {
			$scope.message = data;
			alert(data);
		}).error(function(data, status, headers, config) {
			alert(data);
		});
	}
	
	$scope.register = function Register(){
		$window.location.href = "../registration/register.html";
	}
});