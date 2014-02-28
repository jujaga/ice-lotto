/**
 * <p>Provides a simple interface to a STOMP server over a websocket via SockJS.
 * It includes a set of events that can be subscribed to.</p>
 *
 * <p>Subscribable events:</p>
 *
 * <ul>
 *   <li><code>connected</code>: will be called with a Stomp.js "frame" as an argument</li>
 *   <li><code>disconnected</code></li>
 * </ul>
 *
 * <p>To subscribe to an event, use the <code>on(event, callback)</code> method,
 * where "event" is an event name (string) and callback is a function to be
 * invoked. Event names not in the above list will be silently ignored.</p>
 *
 * <p>To unsubscribe from an event, use the <code>off(event, callback)</code>
 * method, where "event" is the event name and "callback" is the function
 * that would have been invoked.</p>
 *
 * <p>To open the connection to the server, use the <code>connect(endpoint)</code>
 * method, where "endpoint" is a STOMP endpoint.</p>
 *
 * <p>To subscribe to STOMP messages, use the <code>subscribe(topic, callback)</code>
 * method, where "topic" is the STOMP message topic and "callback" is the
 * function to be invoked. This function will receive the standard Stomp.js
 * parameter set.</p>
 *
 * <p>To send messages, use the <code>send(destination, headers, body)</code>
 * method. This method is the same as the Stomp.js send method.</p>
 */
var SocketManager = function() {
  /* global SockJS,Stomp */
  "use strict";
  var obj = {},
      eventNames = ["connected", "disconnected"],
      events = {},
      stompClient = {};

  if (!(this instanceof SocketManager)) {
    return new SocketManager();
  }

  obj.connected = false;

  obj.connect = function(endpoint) {
    var socket = {};
    if (obj.connected) {
      return;
    }

    socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
      obj.connected = true;
      events.fireEvent("connected", frame);

      socket.onclose = function() {
        obj.connected = false;
        events.fireEvent("disconnected");
      };
    });
  };

  obj.on = function(eventName, callback) {
    if (eventNames.indexOf(eventName) === -1) {
      return;
    }
    events.addEventListener(eventName, callback);
  };

  obj.off = function(eventName, callback) {
    if (eventNames.indexOf(eventName) === -1) {
      return;
    }
    events.delEventListener(eventName, callback);
  };

  obj.send = function(destination, headers, body) {
    return stompClient.send(destination, headers, body);
  };

  obj.subscribe = function(topic, callback) {
    return stompClient.subscribe(topic, callback);
  };

  events.addEventListener = function(eventName, listener) {
    if (!events.hasOwnProperty(eventName)) {
      events[eventName] = [];
    }

    if (listener) {
      events[eventName].push(listener);
    }
  };
  events.delEventListener = function(eventName, listener) {
    var i;
    if (events.hasOwnProperty(eventName) && events[eventName].indexOf(listener)) {
      i = events[eventName].indexOf(listener);
      events[eventName].splice(i, 1);
    }
  };
  events.fireEvent = function(eventName, arg) {
    var i, j;

    if (!events.hasOwnProperty(eventName) || !Array.isArray(events[eventName])) {
      return;
    }

    for (i = 0, j = events[eventName].length; i < j; i += 1) {
      events[eventName][i].call(obj, eventName, arg);
    }
  };

  return obj;
};