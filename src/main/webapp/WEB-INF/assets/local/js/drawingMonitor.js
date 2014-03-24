(function($){
  /* global UserSocketManager */
  "use strict";
  var $winnerRowTemplate = $($("#winnerRowTemplate").html().trim()),
      drawingSubscription = {},
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
    var $item = {},
        $itemInfo = {},
        $prevRow = {},
        $small = $("small", $winnerRowTemplate),
        cols = 0,
        data = JSON.parse(response.body);

    $prevRow = $("tr[data-tier-id=" + data.tierId + "]");
    cols = $("td", $prevRow).length;

    // Get the item in the display and add the "won" class to it
    $item = $(
      "td[data-position=" + (data.result.drawNumber) + "] .prize-item",
      $prevRow
    );
    $itemInfo = $("i", $item);
    $("img.gw-item-icon", $item).addClass("tier-won-item");

    // Add a row indicating who won
    $("td", $winnerRowTemplate).attr("colspan", cols).css({
      paddingBottom: 0,
      paddingTop: 0
    });
    $(".winner-name", $winnerRowTemplate).append(data.result.userDisplayName);
    $(".item-won-link", $winnerRowTemplate)
        .append($itemInfo.data("name"))
        .attr("href", "http://www.gw2spidy.com/item/" + $itemInfo.data("id"));
    $small.css({display: "block", height: 0});

    $winnerRowTemplate.insertAfter($prevRow).css({visibility: "visible"});
    $small.animate({height: $prevRow.outerHeight() * 0.22}, "slow");
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