(function($){
  "use strict";
  /* global AdminSocketManager */
  var $depositModal = $("#depositModal"),
      $depositBtn = $("#depositBtn"),
      $depositUser = $("#depositUser"),
      $depositAmount = $("#depositAmount"),
      $depositTier = $("#depositTier"),
      $depositEntryBtn = $("#depositEntryBtn"),
      $smallPoolPot = $("#smallPoolPot"),
      $largePoolPot = $("#largePoolPot"),
      drawing = $(".drawing-container").data("drawingId"),
      searchSubscription = {},
      socketManager = AdminSocketManager.socketManager,
      doSearch = function(){},
      searchResultHandler = function(){},
      subscribeSearch = function(){},
      typeaheadCallback = function(){},
      typeaheadSource = function(){};

  // This is the "add entry" button on the drawing page.
  $depositBtn.on("click", function() {
    $depositModal.data({
      drawingId: drawing
    })
    .modal({
      backdrop: "static",
      show: true
    });
  });

  // This is the "submit" button on the entry form.
  $depositEntryBtn.on("click", function() {
    var msg = {},
        subscription = {};

    $depositEntryBtn.button("loading");

    msg.gw2DisplayName = $depositUser.val();
    msg.amount = ($depositAmount.val().length > 0) ?
        parseInt($depositAmount.val(), 10) : 1;
    msg.drawingId = $depositModal.data("drawingId");
    msg.tierId = parseInt($depositTier.val(), 10);

    subscription = socketManager.subscribe(
        "/topic/admin/drawing/deposit/added",
        function(response) {
          var $tr = $("tr[data-tier-id=" + msg.tierId + "]"),
              data = JSON.parse(response.body),
              entrants = 0;
          subscription.unsubscribe();

          $depositEntryBtn.button("reset");

          $depositModal.modal("hide");
          $depositUser.val("");
          $depositAmount.val("");

          // TODO: check for -1 value as an error
          $smallPoolPot.html(data.smallPoolTotal);
          $largePoolPot.html(data.largePoolTotal);

          entrants = $tr.data("entrants");
          $tr.data("entrants", entrants + 1)
             .attr("data-entrants", entrants + 1);
        }
    );

    socketManager.send("/ws/admin/drawing/deposit", {}, JSON.stringify(msg));
  });

  $depositAmount.on("change", function() {
    var tier;
    if (parseInt($depositAmount.val(), 10) <= 20) {
      tier = $("option", $depositTier)[$depositAmount.val() - 1].value;
      $depositTier.val(tier);
    }
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
    socketManager.send("/ws/admin/user/search", {}, JSON.stringify(msg));
  };

  searchResultHandler = function(response) {
    var data = JSON.parse(response.body);

    if (typeaheadCallback) {
      typeaheadCallback(data);
    }
  };

  subscribeSearch = function() {
    searchSubscription = socketManager.subscribe(
        "/topic/admin/user/search/result",
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