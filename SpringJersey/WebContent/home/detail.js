var loginApp = angular.module("myPageApp", []);
loginApp.controller("myPageController", function($scope, $http, $window){
	
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
				 url: '../spring-rest/login/doLogin',
				 /* headers: {
				   'Content-Type': 'application/json'
				 }, */
				 data: dataObj,
			}
		
		$http(req).success(function(data, status, headers, config) {
			$scope.message = data.message;
			alert(data.message);
		}).error(function(data, status, headers, config) {
			alert(data.message);
		});
	}
	
	$scope.register = function Register(){
		$window.location.href = "../registration/register.html";
	}
});