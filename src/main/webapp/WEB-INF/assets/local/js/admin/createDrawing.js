(function($) {
  /* global AdminSocketManager */
  "use strict";

  var $createDrawingScheduledDate = $("#createDrawingScheduledDate"),
      $createDrawingBtn = $("#createDrawingBtn"),
      socketManager = AdminSocketManager.socketManager,
      submit = function(){};

  // Setup the datetimepicker on the form control
  $createDrawingScheduledDate.datetimepicker({
    autoclose: true,
    format: "yyyy-mm-dd @ hh:ii -0800"
  });

  $createDrawingBtn.on("click", function() {
    var callback = function() {
      socketManager.off("connected", callback);
      submit();
    };

    $createDrawingBtn.button("loading");

    if (socketManager.connected) {
      submit();
    } else {
      socketManager.on("connected", callback);
      AdminSocketManager.reconnect();
    }
  });

  submit = function() {
    var msg = {},
        subscription = {};
    msg.date = $createDrawingScheduledDate.children("input").val();

    subscription = socketManager.subscribe("/topic/drawing/created", function(response) {
      subscription.unsubscribe();
      window.location.href = window.location;
      // TODO: maybe not be lazy?
    });

    socketManager.send("/ws/app/drawing/create", {}, JSON.stringify(msg));
  };
}(jQuery));
