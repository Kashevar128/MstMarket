(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.mstMarketUser) {
            try {
                let jwt = $localStorage.mstMarketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                $localStorage.visibleAdmin = true;
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.marchMarketUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.mstMarketUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.mstMarketUser.token;
            }
        }
        if (!$localStorage.mstMarketGuestCartId) {
            $http.get('http://localhost:5555/cart/api/v1/cart/generate_id')
                .then(function (response) {
                    $localStorage.mstMarketGuestCartId = response.data.value;
                });
        }
    }
})();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    console.log(response.data);
                    $localStorage.visibleAdmin = response.data.visibleAdminButton;
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.mstMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('/');
                }
            }, function errorCallback(response) {
                let me = response;
                console.log(me)
                alert(me.data.message)
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $localStorage.visibleAdmin = false;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.mstMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isVisibleAdmin = function () {
        return $localStorage.visibleAdmin;
    }

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.mstMarketUser) {
            return true;
        } else {
            return false;
        }
    };
});
