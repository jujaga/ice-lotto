(function($) {
  "use strict";

  var $itemSearchBox = $("#itemSearchBox"),
      $itemSearchResultWell = $("#itemSearchResultWell"),
      displayResult = function(){},
      lookupItem = function(){},
      timer = -1;

  $itemSearchBox.on("keyup", function() {
    if (timer > -1) {
      clearTimeout(timer);
    }

    timer = setTimeout(lookupItem, 500);
  });

  displayResult = function(result) {
    $itemSearchResultWell.html("<img src='" + result.img + "'>");

    if ($itemSearchResultWell.is(":hidden")) {
      $itemSearchResultWell
          .collapse("show");
    }
  };

  lookupItem = function() {
    var text = $itemSearchBox.val();

    if (text.length === 0) {
      if ($itemSearchResultWell.is(":visible")) {
        $itemSearchResultWell.collapse("hide");//.removeClass("in");
      }
      return;
    }

    $.ajax({
      url: "spidy/item-search/" + encodeURIComponent(text),
      dataType: "json",
      success: function(data, status, jqXHR) {
        displayResult(data.results[0]);
      }
    });
  };
}(jQuery));