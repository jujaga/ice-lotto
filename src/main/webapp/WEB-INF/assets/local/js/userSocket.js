var UserSocketManager = (function() {
  /* global SocketManager */
  "use strict";
  var obj = {},
      endpoint = "/app";

  obj.socketManager = new SocketManager(endpoint);
  obj.socketManager.connect();

  obj.reconnect = function() {
    obj.socketManager.connect();
  };

  return obj;
}());