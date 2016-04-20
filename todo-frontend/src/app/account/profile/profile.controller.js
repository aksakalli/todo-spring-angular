(function() {
  'use strict';

  angular
    .module('todo')
    .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController(Auth) {
    var vm = this;

    vm.currentUser = Auth.getCurrentUser();

  }
})();
