angular.module('market').controller('roleController', function ($scope, $http, $rootScope, $location) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.editRole = function () {
        console.log($rootScope.edituser);
        $http.post(contextPath + 'roleEdit', $rootScope.edituser).then(function succes (response) {
            if (response.data.value) {
                alert(response.data.value);
                $location.path('/users');
            }
        }, function error (response) {
            let me = response;
            console.log(me);
            alert(me.data.message);
        });
    }

    $scope.back = function () {
        $location.path('/users')
    }
});