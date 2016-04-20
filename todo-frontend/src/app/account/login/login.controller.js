(function () {
  'use strict';

  angular
    .module('todo')
    .controller('LoginController', LoginController);

  /** @ngInject */
  function LoginController(Auth, $state, $log) {
    var vm = this;

    vm.submit = submit;
    vm.username = '';
    vm.password = '';
    vm.errorMsg = false;

    alreadyLoggedInCheck();

    function alreadyLoggedInCheck() {
      $log.debug('in login controller');
      Auth.isLoggedInAsync(function (loggedIn) {
        if (loggedIn) {
          $log.debug('User already logged in, redirecting to home page');
          $state.go('home');
        }
      });

    }

    function submit(event) {
      event.preventDefault();
      Auth.login({
          username: vm.username,
          password: vm.password
        })
        .then(function () {
          $state.go('home');
          vm.errorMsg = '';
        })
        .catch(function () {
          vm.errorMsg = 'The username or password is incorrect.';
        });
    }

  }
})();
