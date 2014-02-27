(function($) {
  /** jsHint config **/
  /* global AdminSocketManager */
  "use strict";

  var $addButton = $("#addItemModal .btn-primary"),
      $itemSearchBox = $("#itemSearchBox"),
      $itemSearchResultWell = $("#itemSearchResultWell"),
      $itemSearchResultTemplate = $("#itemSearchResultTemplate"),
      $spinner = $itemSearchBox.siblings(".input-group-addon"),
      displayResult = function(a){},
      doSearch = function(a){},
      lookupItem = function(){},
      socketManager = AdminSocketManager.socketManager,
      subscribeCallback = function(){},
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

  doSearch = function(text) {
    var callback = function() {
      doSearch(text);
      socketManager.off("connected", callback);
    };

    if (!socketManager.connected) {
      socketManager.on("connected", callback);
      AdminSocketManager.reconnect();
    } else {
      socketManager.send("/ws/app/item/search", {}, JSON.stringify({term: text}));
    }
  };

  subscribeCallback = function() {
    socketManager.subscribe('/topic/item/search/result', function(response) {
      var data = JSON.parse(response.body);
      $spinner.spin(false);

      if (data.content && data.content.result) {
        displayResult([data.content.result]);
      } else if (data.content && data.content.results) {
        displayResult(data.content.results);
      }
    });
  };

  if (socketManager.connected) {
    subscribeCallback();
  } else {
    socketManager.on("connected", subscribeCallback);
    AdminSocketManager.reconnect();
  }
}(jQuery));