(function() {
  'use strict';

  angular
    .module('todo')
    .factory('Thing', Thing);


  /** @ngInject */
  function Thing($resource) {
    return $resource('/api/things/:id', {
      id: '@id'
    }, {
      query: {
        method: 'get',
        isArray: true,
        cancellable: true
      },
      update: {
        method: 'PUT'
      }
    });
  }
})();
