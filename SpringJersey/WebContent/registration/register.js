var loginApp = angular.module("registrationApp", []);
loginApp.controller("registrationController", function($scope, $http, $window){
	
	$scope.register = function register(){
		if(!($scope.name)){
			alert("Please fill name");
			return;
		}
		if(!($scope.username)){
			alert("Please fill username");
			return;
		}
		if(!($scope.password)){
			alert("Please fill password");
			return;
		}
		if(!($scope.email)){
			alert("Please fill email");
			return;
		}
		var dataObj = {};
		dataObj["name"] = $scope.name;
		dataObj["userName"] = $scope.username;
		dataObj["password"] = $scope.password;
		dataObj["email"] = $scope.email;
		dataObj["phone"] = $scope.phone;
		dataObj["country"] = $scope.country;
		dataObj["link"] = $scope.link;
		
		var req = {
				 method: 'POST',
				 url: '../rest/register/register-user',
				 /* headers: {
				   'Content-Type': 'application/json'
				 }, */
				 data: dataObj,
			}
		
		$http(req).success(function(data, status, headers, config) {
			$scope.message = data;
			alert(data);
			$window.location.href = "../login/login.html";
		}).error(function(data, status, headers, config) {
			alert(data);
		});
	}
});