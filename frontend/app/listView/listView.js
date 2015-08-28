'use strict';

angular.module('myApp.listView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/listView', {
    templateUrl: 'listView/listView.html',
    controller: 'ListViewCtrl'
  });
}])

.controller('ListViewCtrl', [function() {

}]);