var appModule = angular.module('myApp', []);

appModule
		.controller(
				'MainCtrl',
				[
						'mainService',
						'$scope',
						'$http',
						'$window',
						function(mainService, $scope, $http, $window) {
							$scope.greeting = 'Welcome to the JSON Web Token / AngularJR / Spring example!';
							$scope.token = null;
							$scope.error = null;
							$scope.roleUser = false;
							$scope.roleAdmin = false;
							$scope.roleFoo = false;
//							if(localStorage.getItem("username")!=null || localStorage.getItem("username")!=null){
//								$window.location.href = "/viewemployee.html";
//							}

							$scope.login = function() {
								$scope.error = null;
								
								$http.post('/login/', {
									email : $scope.userName,
									password : $scope.password
								}).then(function successCallback(response) {
									console.log(response.data);
									$scope.token =response.data.token;
									$http.defaults.headers.common.Authorization = 'Bearer '
											+ response.data.token;
									$scope.checkRoles();
									localStorage.setItem(
											"session",
											 JSON.stringify(response.data));
									console.log(JSON.parse(localStorage.getItem("session")));
									$window.location.href = "/livechat.html";
									
								}, function errorCallback(response) {
									console.log(response.data);

									$scope.error = response.data.error;
									$scope.userName = '';
								});
								
								
								
								
								
								
								
								
								
								
								
								
//								mainService
//										.login($scope.userName, $scope.password)
//										.then(
//												function(token) {
//													$scope.token = token;
//													$http.defaults.headers.common.Authorization = 'Bearer '
//															+ token;
//													$scope.checkRoles();
//													localStorage.setItem(
//															"username",
//															$scope.userName);
//													$window.location.href = "/viewemployee.html";
//												}, function(error) {
//													alert(error);
//													$scope.error = error
//													$scope.userName = '';
//												});
							}

							$scope.checkRoles = function() {
								mainService.hasRole('user').then(
										function(user) {
											$scope.roleUser = user
										});
								mainService.hasRole('admin').then(
										function(admin) {
											$scope.roleAdmin = admin
										});
								mainService.hasRole('foo').then(function(foo) {
									$scope.roleFoo = foo
								});
							}

							$scope.logout = function() {
								$scope.userName = '';
								$scope.token = null;
								$http.defaults.headers.common.Authorization = '';
								localStorage.removeItem("session");
							}

							$scope.loggedIn = function() {
								return $scope.token !== null;
							}
						} ]);

appModule.service('mainService', function($http) {
	return {
		login : function(username, password) {
			alert(username);
			return $http.post('/login/', {
				email : username,
				password : password
			}).then(function successCallback(response) {
				alert(response.data.token);
				return response.data.token;
			}, function errorCallback(response) {
				return response;
			});

		},

		hasRole : function(role) {
			return $http.get('/api/role/' + role).then(function(response) {
				console.log(response.data);
				console.log(response);
				return true;
			});
		}
	};
});
