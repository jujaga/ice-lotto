var AdminSocketManager = (function() {
  /* global SocketManager */
  "use strict";
  var obj = {},
      endpoint = "/app";

  obj.socketManager = new SocketManager();
  obj.socketManager.connect(endpoint);

  obj.reconnect = function() {
    obj.socketManager.connect(endpoint);
  };

  return obj;
}());
