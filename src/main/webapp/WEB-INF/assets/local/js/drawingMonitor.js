(function($){
  /* global UserSocketManager */
  "use strict";
  var $winnerRowTemplate = $($("#winnerRowTemplate").html().trim()),
      poolSubscription = {},
      tierSubscription = {},
      socketManager = UserSocketManager.socketManager,
      drawingStartedCallback = function(){},
      initCallback = function(){},
      poolResultReceived = function(){},
      tierResultReceived = function(){},
      updateUI = function(){};

  drawingStartedCallback = function(response) {
    var data = (response && response.body) ? JSON.parse(response.body) : response;
    if (data.started) {
      // Make sure we are not registered twice
      tierSubscription.unsubscribe ? tierSubscription.unsubscribe() : $.noop();
      poolSubscription.unsubscribe ? poolSubscription.unsubscribe() : $.noop();

      // Now we can register for results
      tierSubscription = socketManager.subscribe("/topic/drawing/tier/winner", tierResultReceived);
      poolSubscription = socketManager.subscribe("/topic/drawing/pool/winner", poolResultReceived);
      updateUI();
    }
  };

  initCallback = function() {
    socketManager.off("connected", initCallback);

    if ($(".drawing-container").data("drawingInProgress") !== true) {
      tierSubscription = socketManager.subscribe("/topic/drawing/started", drawingStartedCallback);
    } else {
      drawingStartedCallback({started: true});
    }
  };

  poolResultReceived = function(response) {
    var data = JSON.parse(response.body),
        $pool = $("div[data-pool-id=" + data.poolId + "]"),
        $moneyDisplay = $(".pull-right", $pool),
        $drawResult = $(".money-draw-result", $moneyDisplay),
        $btn = $(".money-draw-btn", $pool),
        showResult = function(){};

    showResult = function() {
      $drawResult.animate({opacity: 1}, "slow");
    };

    $drawResult.css({opacity: 0, display: "inline"});
    $($moneyDisplay.find(".money-draw-winner")).text(data.result.userDisplayName);
    $($moneyDisplay.find(".money-draw-coin").text(data.result.amountWon));

    if ($btn.length > 0) {
      // Admin view
      $btn.fadeOut("slow", showResult);
    } else {
      // User view
      showResult();
    }
  };

  tierResultReceived = function(response) {
    var $row = $winnerRowTemplate.clone(),
        $item = {},
        $itemInfo = {},
        $prevRow = {},
        $small = $("small", $row),
        cols = 0,
        data = JSON.parse(response.body);

    $prevRow = $("tr[data-tier-id=" + data.tierId + "]");
    cols = $("td", $prevRow).length;

    // Get the item in the display and add the "won" class to it
    $item = $(
      "td[data-position=" + (data.result.itemDrawNumber) + "] .prize-item",
      $prevRow
    );
    $itemInfo = $("i", $item);
    $("img.gw-item-icon", $item).addClass("tier-won-item");

    // Add a row indicating who won
    $("td", $row).attr("colspan", cols).css({
      paddingBottom: 0,
      paddingTop: 0
    });
    $(".winner-name", $row).text(data.result.userDisplayName);
    $(".item-won-link", $row)
        .text($itemInfo.data("name"))
        .attr("href", "http://www.gw2spidy.com/item/" + $itemInfo.data("id"));
    $small.css({display: "block", height: 0});

    $row.insertAfter($prevRow).css({visibility: "visible"});
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