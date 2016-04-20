(function() {
  'use strict';

  angular
    .module('todo')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'main',
        data: {
          roles: ['ROLE_USER']
        }
      })
      .state('login', {
        url: '/login',
        templateUrl: 'app/account/login/login.html',
        controller: 'LoginController',
        controllerAs: 'login'
      })
      .state('profile', {
        url: '/profile',
        templateUrl: 'app/account/profile/profile.html',
        controller: 'ProfileController',
        controllerAs: 'profile',
        data: {
          roles: ['ROLE_USER']
        }
      });

    $urlRouterProvider.otherwise('/');
  }

})();
