angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.functionRegistration = function () {
        $http.post(contextPath + 'registration', $scope.reguser).then(function success (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.mstMarketUser = {username: $scope.reguser.username, token: response.data.token};
                $localStorage.reguser = null;
                $location.path("/");
            }
        }, function error (response) {
            let me = response;
            console.log(me);
            alert(me.data.message);
        });
    }
});