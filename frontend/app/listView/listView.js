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

	// TODO: This should be moved down into a TaskList service
	$http({
        url: 'http://localhost:8080/task',
        method: "GET"
    }).then(function(data) {
    	$scope.result = "SUCCESS";
     	$scope.taskList = data.data;
   	}, function(response) {
   		alert("Error encountered talking with the server");
   	});	

   	$scope.newTaskEntered = function() {

   		if ($scope.newTaskDesc.trim().length > 0) {

	   		// TODO: This should be moved down into a TaskList service
	   		var newTask = {
	   			description: $scope.newTaskDesc,
	   			completed: false
	   		}
	   		$scope.newTaskDesc = "";
			$http({
		        url: 'http://localhost:8080/task',
		        method: "POST",
		        data: JSON.stringify(newTask)
		    }).then(function(data) {
		    	newTask.id = data.data.id;
	    		$scope.taskList.push(newTask);	
	    		$scope.result = "Added task " + newTask.id + ".  New count = " + $scope.taskList.length;
		   	}, function(response) {
	   			alert("Error encountered talking with the server");
		   	});	
		}
    };

}]);