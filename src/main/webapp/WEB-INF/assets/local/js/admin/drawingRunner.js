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

    // Deal with the item draw buttons
    $(".prize-pool-tier-row").each(function() {
      var $this = $(this);
      if ($this.data("entrants") === 0 ||
          $this.find("i").length === 0) // Each valid prize item will have an info node
      {
        $(".draw-btn", $this).remove();
      }
    });
    $(".draw-btn").removeClass("disabled invisible");

    // Deal with the money draw buttons
    $(".deposit-btn").remove();
    $(".money-draw-btn").each(function() {
      var $this = $(this);

      if (parseInt($this.siblings(".pool-pot").text(), 10) === 0) {
        $this.remove();
      } else {
        $this.removeClass("hidden disabled");
      }
    });

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
    var $this = $(this),
        $drawing = $(".drawing-container"),
        $pool = $($this.closest(".prize-pool")),
        msg = {};

    msg.drawingId = $drawing.data("drawingId");
    msg.poolName = ($pool.hasClass("small-pool")) ? "small" : "large";

    $this.toggleClass("disabled").text("Drawing");
    socketManager.send("/ws/admin/drawing/draw/pool/money", {}, JSON.stringify(msg));
  });
}(jQuery));