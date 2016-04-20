(function() {
  'use strict';

  angular
    .module('todo')
    .factory('Auth', Auth);

  /** @ngInject */
  function Auth(Account, Base64, $log, $http, localStorageService, $state, $q, $rootScope, $location) {
    var currentUser = {};
    if (localStorageService.get('token')) {
      currentUser = Account.get();
    }

    var isUserAllowed = function(roles) {
      return roles.filter(function(n) {
        return currentUser.roles.indexOf(n) != -1
      }).length == roles.length;
    };

    return {
      login: function(credentials, callback) {
        var cb = callback || angular.noop;
        var deferred = $q.defer();

        var data = 'username=' + credentials.username +
          '&password=' + credentials.password + '&grant_type=password&scope=read%20write&' +
          'client_secret=123456&client_id=clientapp';

        $http.post('/oauth/token', data, {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'Accept': 'application/json',
              'Authorization': 'Basic ' + Base64.encode('clientapp:123456')
            }
          })
          .success(function(response) {
            var expiredAt = new Date();
            expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
            response.expires_at = expiredAt.getTime();
            localStorageService.set('token', response);
            currentUser = Account.get();
            deferred.resolve(response);
            return cb();
          })
          .error(function(err) {
            this.logout();
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      },
      logout: function() {
        currentUser = {};
        // logout from the server
        $http.post('api/logout').then(function() {
          localStorageService.clearAll();
        });
      },
      getCurrentUser: function() {
        return currentUser;
      },
      /**
       * Waits for currentUser to resolve before checking if user is logged in
       */
      isLoggedInAsync: function(cb) {
        $log.debug('isLoggedInAsync called');
        if (!localStorageService.get('token')) {
          // if the token is expired, remove all resources
          currentUser = {};
          cb(false);
        } else if (currentUser.hasOwnProperty('$promise')) {
          $log.debug('isLoggedInAsync need to resolve promise');
          currentUser.$promise.then(function() {
            cb(true);
          }).catch(function() {
            cb(false);
          });
        } else if (currentUser.hasOwnProperty('roles')) {
          cb(true);
        } else {
          cb(false);
        }
      },
      authorize: function() {
        this.isLoggedInAsync(function(loggedIn) {
          if ($rootScope.toState.data.roles && $rootScope.toState.data.roles.length > 0) {
            if (!loggedIn) {
              $log.debug('Not logged in, redirecting to login page');
              // user is not authenticated. show the state they wanted before you
              // send them to the signin state, so you can return them when you're done
              $rootScope.returnToState = $rootScope.toState;
              $rootScope.returnToStateParams = $rootScope.toStateParams;
              localStorageService.set('redirect', $location.path());
              // now, send them to the signin state so they can log in
              $location.path('/login');
              //TODO: fix state problem
              //$state.transitionTo('login');
            } else if (!isUserAllowed($rootScope.toState.data.roles)) {
              // user is signed in but not authorized for desired state
              $state.go('accessdenied');
            }
          }
        });
      }

      // changePassword: function(newPassword, callback) {
      //   var cb = callback || angular.noop;
      //
      //   return Password.save(newPassword, function() {
      //     return cb();
      //   }, function(err) {
      //     return cb(err);
      //   }).$promise;
      // }

    };
  }
})();
