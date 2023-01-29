
angular.module('market').controller('productsController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/api/v1/products';
    $scope.loadProducts = function (page = 1) {
        $http({
            url:  contextPath + '/forAdmin',
            method: 'GET',
            params: {
                p: page,
                title_part: $scope.filter ? $scope.filter.title_part : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.generatePagesList($scope.productsPage.totalPages);
        });
    };

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.createNewProduct = function () {
        $location.path('/registrationProduct')
    }

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/' + id).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.back = function () {
        $location.path('/admin')
    }

    $scope.loadProducts();
});