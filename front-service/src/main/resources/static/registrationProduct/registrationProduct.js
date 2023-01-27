angular.module('market').controller('registrationProductController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/products';

    $scope.functionRegistrationProduct = function () {
        $http.post(contextPath, $scope.regproduct).then(function success (response) {
            alert(response.data.value);
            $location.path('/products');
        }, function error (response) {
            let me = response;
            console.log(me);
            alert(me.data.message);
        });
    }

    $scope.back = function () {
        $location.path('/products')
    }
});