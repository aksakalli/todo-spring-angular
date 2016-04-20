(function() {
  'use strict';

  angular
    .module('todo')
    .factory('Account', Account);


  /** @ngInject */
  function Account($resource) {
    return $resource('api/account', {}, {
      'get': {
        method: 'GET',
        params: {},
        isArray: false,
        interceptor: {
          response: function(response) {
            // expose response
            // TODO: implement roles in backend!
            return response;
          }
        },
        transformResponse: function(response) {
          var jsonData = angular.fromJson(response);
          jsonData.roles = ['ROLE_USER'];
          return jsonData;
        }
      }
    });
  }
})();
