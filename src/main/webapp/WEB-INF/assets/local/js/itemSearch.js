(function($) {
  /** jsHint config **/
  /* global AdminSocketManager */
  "use strict";

  var $addItemModal = $("#addItemModal"),
      $addButton = $(".btn-primary", $addItemModal),
      $itemCountBox = $("#itemCount", $addItemModal),
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

  // When the Bootstrap hidden event is fired, clear the form.
  $addItemModal.on("hidden.bs.modal", function() {
    $itemSearchBox.val("");
    $itemSearchResultWell.collapse("hide").html("");
    $itemCountBox.val("");
  });

  // Submits the typed search term when 500ms have elapsed since the
  // last key was pressed.
  $itemSearchBox.on("keyup", function() {
    if (timer > -1) {
      clearTimeout(timer);
    }

    timer = setTimeout(lookupItem, 500);
  });

  $addButton.on("click", function() {
    var subscription,
        itemCount = $itemCountBox.val(),
        details = {};
    $addButton.button("loading");

    details.itemId = $(".bg-success", $addItemModal).data("itemId");
    details.tierId = $addItemModal.data("tierId");
    details.tierPosition = $addItemModal.data("position");
    details.poolId = $addItemModal.data("poolId");
    details.count = (itemCount.length) > 0 ? parseInt(itemCount) : 1;

    subscription = socketManager.subscribe("/topic/admin/drawing/item/add", function(response) {
      // When we receive a response back after submitting an item to be added
      // this is where we clean up and add the item to the view.
      var data = JSON.parse(response.body);
      subscription.unsubscribe();
      $addButton.button("reset");

      if (data.successful) {
        $addItemModal.modal("hide");
        $.ajax({
          url: "/prizeitem/" + details.tierId + "/" + details.tierPosition,
          dataType: "html",
          success: function(data, status, jqXHR) {
            var dest =
              [
                "div[data-pool-id=", details.poolId , "] ",
                "tr[data-tier-id=", details.tierId, "] ",
                "td[data-position=", details.tierPosition, "]"
              ];
            $(dest.join("")).html(data);
          }
        });
      } else {
        alert(data.message);
      }
    });

    // Submit the item to be added.
    socketManager.send("/ws/admin/drawing/item/add", {}, JSON.stringify(details));
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

    // Add all search results to the search result display area.
    for (i = 0; i < j; i += 1) {
      $node = $($itemSearchResultTemplate.html().trim());

      $node.data("itemId", results[i].data_id).on("click", click);
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
    // TODO: determine if paged results (that Spidy returns) will be a problem
  };

  doSearch = function(text) {
    socketManager.send("/ws/admin/item/search", {}, JSON.stringify({term: text}));
  };

  subscribeCallback = function() {
    socketManager.subscribe('/topic/admin/item/search/result', function(response) {
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
  }
}(jQuery));