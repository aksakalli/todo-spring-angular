(function () {
  'use strict';

  angular
    .module('todo')
    .factory('authInterceptor', authInterceptor);

  /** @ngInject */
  function authInterceptor(localStorageService, $q, $location, $log) {
    return {
      // Add authorization token to headers
      request: function (config) {
        config.headers = config.headers || {};
        var token = localStorageService.get('token');

        if (token && token.expires_at && token.expires_at > new Date().getTime()) {
          config.headers.Authorization = 'Bearer ' + token.access_token;
        }

        return config;
      },
      // Intercept 401s and redirect you to login
      responseError: function (response) {
        if (response.status === 401) {
          if ($location.path() != '/login') {
            localStorageService.set('redirect', $location.path());
          }

          $log.debug('server returned 401, token has expired');
          // remove any stale tokens
          localStorageService.remove('token');
          // redirect to login page

          //document.location = '/login';
          $location.path('/login');

          return $q.reject(response);
        } else {
          return $q.reject(response);
        }
      }
    };
  }
})();
