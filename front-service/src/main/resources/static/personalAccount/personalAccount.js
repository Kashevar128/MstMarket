angular.module('market').controller('personalAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/';
    $scope.upUser = {username: null, password: null, confirmPassword: null, email: null};

    $scope.functionRegistration = function () {
        $http.post(contextPath + 'registration', $scope.reguser).then(function success (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.mstMarketUser = {username: $scope.reguser.username, token: response.data.token};
                $scope.reguser = null;
                alert('Привет ' + $localStorage.mstMarketUser.username + '!')
                $location.path("/");
            }
        }, function error (response) {
            let me = response;
            console.log(me);
            alert(me.data.message);
        });
    }

    $scope.getDataUser = function () {
        $scope.upUser.username = $localStorage.mstMarketUser.username;
    }

    $scope.getDataUser();
});