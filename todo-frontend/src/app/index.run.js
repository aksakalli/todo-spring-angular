(function() {
  'use strict';

  angular
    .module('todo')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log, $rootScope, Auth) {
    var deregistrationCallback = $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {
      $rootScope.toState = toState;
      $rootScope.toStateParams = toStateParams;

      Auth.authorize();
    });

    $rootScope.$on('$destroy', deregistrationCallback);
    $log.debug('runBlock end');
  }

})();
