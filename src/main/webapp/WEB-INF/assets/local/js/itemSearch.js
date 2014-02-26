(function($) {
  /** jsHint config **/
  /* global Stomp,SockJS */
  "use strict";

  var $addButton = $("#addItemModal .btn-primary"),
      $itemSearchBox = $("#itemSearchBox"),
      $itemSearchResultWell = $("#itemSearchResultWell"),
      $itemSearchResultTemplate = $("#itemSearchResultTemplate"),
      $spinner = $itemSearchBox.siblings(".input-group-addon"),
      connected = false,
      displayResult = function(a){},
      doSearch = function(a){},
      lookupItem = function(){},
      socket = {},
      stompClient = {},
      stompConnect = function(a){},
      timer = -1;

  // Submits the typed search term when 500ms have elapsed since the
  // last key was pressed.
  $itemSearchBox.on("keyup", function() {
    if (timer > -1) {
      clearTimeout(timer);
    }

    timer = setTimeout(lookupItem, 500);
  });

  // Handles populating the search results template with the data
  // returned from a search.
  displayResult = function(results) {
    var $node = {},
        $dl = {},
        i, j = results.length,
        click = function(){};

    if (results.length === 0) {
      return;
    }

    click = function() {
      $(".bg-success", $itemSearchResultWell).toggleClass("bg-success");
      $(this).toggleClass("bg-success");

      if ($addButton.hasClass("disabled")) {
        $addButton.removeClass("disabled");
      }
    };

    $itemSearchResultWell.children().fadeOut("slow").remove();

    for (i = 0; i < j; i += 1) {
      $node = $($itemSearchResultTemplate.html().trim());

      $node.data("id", results[i].data_id).on("click", click);
      $(".media-object", $node).attr("src", results[i].img);
      $(".media-heading", $node).text(results[i].name);

      $dl = $("dl", $node);
      $dl.append("<dt>Min Level</dt><dd>" + results[i].restriction_level + "</dd>");
      $dl.append("<dt>Rarity Level</dt><dd>" + results[i].rarity + "</dd>");

      $itemSearchResultWell.append($node);
    }

    if ($itemSearchResultWell.is(":hidden")) {
      $itemSearchResultWell
          .collapse("show");
    }
  };

  // Validates the input and submits it if valid. Also shows and hides the
  // search result well depending on the result of the validation.
  lookupItem = function() {
    var text = $itemSearchBox.val();

    if (text.length === 0) {
      if ($itemSearchResultWell.is(":visible")) {
        $(".media", $itemSearchResultWell).fadeOut("slow");
        $itemSearchResultWell.collapse("hide");

        if (!$addButton.hasClass("disabled")) {
          $addButton.addClass("disabled");
        }
      }
      return;
    }

    $spinner.spin("small");
    doSearch(text);
    // TODO: determine if paged results (that Spidy returns) will be
  };

  stompConnect = function(searchTerm) {
    if (connected) {
      return;
    }
    socket = new SockJS("/item/search");
    stompClient = Stomp.over(socket);

    // Connect to the remote server and subscribe to the search result service.
    stompClient.connect({}, function(frame) {
      connected = true;

      stompClient.subscribe('/wsresponse/item/search/result', function(response){
        var data = JSON.parse(response.body);
        $spinner.spin(false);

        if (data.content && data.content.result) {
          displayResult([data.content.result]);
        } else if (data.content && data.content.results) {
          displayResult(data.content.results);
        }
      });

      if (searchTerm) {
        doSearch(searchTerm);
      }
    });

    // Bind to the socket's onclose event so that we can update our
    // connection status.
    socket.onclose = function() {
      connected = false;
    };
  };

  doSearch = function(text) {
    if (!connected) {
      stompConnect(text);
    } else {
      stompClient.send("/ws/item/search", {}, JSON.stringify({term: text}));
    }
  };

  stompConnect();
}(jQuery));