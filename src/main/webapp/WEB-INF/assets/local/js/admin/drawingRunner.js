(function($){
  /* global AdminSocketManager */
  "use strict";
  var $endBtn = $("#endBtn"),
      $startBtn = $("#startBtn"),
      socketManager = AdminSocketManager.socketManager;

  $endBtn.on("click", function() {
    $endBtn.toggleClass("disabled");
    // TODO: send end event
  });

  $startBtn.on("click", function() {
    var data = {};

    $startBtn.toggleClass("disabled");
    $endBtn.toggleClass("disabled");
    $(".draw-btn").removeClass("disabled invisible");
    $(".deposit-btn").remove();
    $(".money-draw-btn").removeClass("hidden disabled");

    data.drawingId = parseInt($(".drawing-container").data("drawingId"), 10);
    socketManager.send("/ws/admin/drawing/start", {}, JSON.stringify(data));
  });

  $(".draw-btn").on("click", function() {
    var $this = $(this),
        data = {};

    $this.toggleClass("disabled").text("Drawn");

    data.tierId = $this.closest(".prize-pool-tier-row").data("tierId");
    data.poolId = $this.closest(".prize-pool").data("poolId");
    socketManager.send("/ws/admin/drawing/draw/tier", {}, JSON.stringify(data));

    console.dir(data);
  });

  $(".money-draw-btn").on("click", function() {
    var $this = $(this);

    $this.toggleClass("disabled").text("Drawn");
    // TODO: do the actual drawing
  });
}(jQuery));