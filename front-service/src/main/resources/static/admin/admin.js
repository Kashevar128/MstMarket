angular.module('market').controller('adminController', function ($scope, $http) {
    $scope.getListProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products/listProducts')
            .then(function (response) {
                $scope.tableProducts = response.data.productMyDtos;
                console.log(response.data.productMyDtos)
            });
    }

    $scope.getListProducts();


});