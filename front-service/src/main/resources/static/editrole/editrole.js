angular.module('market').controller('roleController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.editRole = function () {
        console.log($rootScope.edituser);
        $http.post(contextPath + 'roleEdit', $rootScope.edituser).then(function succes (response) {
            alert(response.data);
            $location.path('/users');
        }, function error (response) {
            let me = response;
            console.log(me);
            alert(me.data.message);
        });
    }
});