'use strict';

angular.module('myApp.listView', ['ngRoute'])

.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider.when('/listView', {
    templateUrl: 'listView/listView.html',
    controller: 'ListViewCtrl'
  });
  // We'll be needing CORS to make REST calls to the server. 
  $httpProvider.defaults.useXDomain = true;
}])

.controller('ListViewCtrl', ['$scope', '$http', function($scope, $http) {
	$scope.result = 'Fetching data...';
	var authString = btoa('tom' + ':' + 'password');
	$http({
        url: 'http://localhost:8080/task',
        method: "GET"
        //data: JSON.stringify(requestData)
    }).then(function(data) {
     	$scope.result = data.data;
   	}, function(response) {
   		$scope.result = "ERROR";
   	});	
}]);