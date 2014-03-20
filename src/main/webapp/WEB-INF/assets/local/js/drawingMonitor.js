(function($){
  /* global UserSocketManager */
  "use strict";
  var drawingSubscription = {},
      socketManager = UserSocketManager.socketManager,
      drawingStartedCallback = function(){},
      initCallback = function(){},
      tierResultReceived = function(){},
      updateUI = function(){};

  drawingStartedCallback = function(response) {
    var data = (response && response.body) ? JSON.parse(response.body) : response;
    if (data.started) {
      drawingSubscription.unsubscribe ? drawingSubscription.unsubscribe() : $.noop();
      drawingSubscription = socketManager.subscribe("/topic/drawing/tier/winner", tierResultReceived);
      updateUI();
    }
  };

  initCallback = function() {
    socketManager.off("connected", initCallback);

    if ($(".drawing-container").data("drawingInProgress") !== true) {
      drawingSubscription = socketManager.subscribe("/topic/drawing/started", drawingStartedCallback);
    } else {
      drawingStartedCallback({started: true});
    }
  };

  tierResultReceived = function(response) {
    var result = JSON.parse(response.body);
    $(
      "div[data-pool-id=" + result.poolId + "] " +
        "tr[data-tier-id=" + result.tierId + "] " +
        "td[data-position=" + (result.itemNumber + 1) + "] img.gw-item-icon"
    ).addClass("tier-won-item");
  };

  updateUI = function() {
    var $drawingHeader = $("#drawingHeader");

    // TODO: do something more attention grabbing so the user notices it
    $drawingHeader.find(".lead").html("Drawing underway");

    // TODO: create a "filler.png" for the spots that do not have prizes
    $(".add-item-btn")
        .animate({opacity: 0}, "slow")
        .parent()
        .css("opacity", 0)
        .html("<img src='filler.png' class='prize-item'>");
  };

  if (socketManager.connected) {
    initCallback();
  } else {
    socketManager.on("connected", initCallback);
  }
}(jQuery));