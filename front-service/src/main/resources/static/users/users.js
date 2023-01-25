
angular.module('market').controller('usersController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.loadUsers = function (page = 1) {
        $http({
            url: contextPath + 'listUsers',
            method: 'GET',
            params: {
                p: page,
                title_part: $scope.filter ? $scope.filter.title_part : null
            }
        }).then(function (response) {
            $scope.usersPage = response.data;
            $scope.generatePagesList($scope.usersPage.totalPages);
        });
    };

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadUsers();
});