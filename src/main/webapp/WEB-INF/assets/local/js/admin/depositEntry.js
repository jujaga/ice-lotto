(function($){
  "use strict";
  /* global AdminSocketManager */
  var $depositModal = $("#depositModal"),
      $depositUser = $("#depositUser"),
      $depositAmount = $("#depositAmount"),
      $depositEntryBtn = $("#depositEntryBtn"),
      searchSubscription = {},
      socketManager = AdminSocketManager.socketManager,
      doSearch = function(){},
      searchResultHandler = function(){},
      subscribeSearch = function(){},
      typeaheadCallback = function(){},
      typeaheadSource = function(){};

  $depositEntryBtn.on("click", function() {
    var msg = {},
        subscription = {};

    $depositEntryBtn.button("loading");

    msg.gw2DisplayName = $depositUser.val();
    msg.amount = ($depositAmount.val().length > 1) ?
        parseInt($depositAmount.val(), 10) : 1;
    msg.drawingId = $depositModal.data("drawingId");
    msg.poolId = $depositModal.data("poolId");

    subscription = socketManager.subscribe(
        "/topic/drawing/deposit/added",
        function(response) {
          subscription.unsubscribe();

          $depositEntryBtn.button("reset");

          if (parseInt(response.body, 10) === -1) {
            alert("Deposit was not successful. Try again?");
            return;
          }

          $depositModal.modal("hide");
          $depositUser.val("");
          $depositAmount.val("");

          $($depositModal.data("poolPotDisplay")).html(response.body);
        }
    );

    socketManager.send("/ws/app/drawing/deposit", {}, JSON.stringify(msg));
  });

  typeaheadSource = function(query, cb) {
    typeaheadCallback = cb;
    doSearch(query);
  };

  $depositUser.typeahead(null, {
    displayKey: "gw2DisplayName",
    source: typeaheadSource,
    templates: {
      suggestion: function(datum) {
        var i, j, result = "<dl>";

        result += "<dt>" + datum.gw2DisplayName + "</dt>"
        for (i = 0, j = datum.tokens.length; i < j; i += 1) {
          result += "<dd>" + datum.tokens[i] + "</dd>";
        }
        result += "</dl>";

        return result;
      }
    }
  });

  doSearch = function(term) {
    var msg = {};
    msg.name = term;
    socketManager.send("/ws/app/user/search", {}, JSON.stringify(msg));
  };

  searchResultHandler = function(response) {
    var data = JSON.parse(response.body);

    if (typeaheadCallback) {
      typeaheadCallback(data);
    }
  };

  subscribeSearch = function() {
    searchSubscription = socketManager.subscribe(
        "/topic/user/search/result",
        searchResultHandler
    );
    socketManager.off("connected", subscribeSearch);
  };

  if (socketManager.connected) {
    subscribeSearch();
  } else {
    socketManager.on("connected", subscribeSearch);
  }
}(jQuery));