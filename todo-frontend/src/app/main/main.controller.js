(function() {
  'use strict';

  angular
    .module('todo')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController(Thing) {
    var vm = this;

    vm.things = Thing.query();
    vm.addNew = addNew;
    vm.update = update;
    vm.remove = remove;

    function addNew() {
      var thing = new Thing({
        title: '',
        content: ''
      });
      thing.$save(function() {
        //vm.things.push(t);

        //silly workaround
        vm.things = Thing.query();
      });
    }

    function update(thing) {
      Thing.update(thing);
    }

    function remove(thing) {
      thing = new Thing(thing);
      thing.$remove(function() {
        for (var i = 0; i < vm.things.length; i++) {
          if (vm.things[i].id == thing.id) {
            vm.things.splice(i, 1);
          }
        }

      });

    }

  }
})();
