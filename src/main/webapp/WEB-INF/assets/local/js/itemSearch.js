(function($) {
  "use strict";

  var $addButton = $("#addItemModal .btn-primary"),
      $itemSearchBox = $("#itemSearchBox"),
      $itemSearchResultWell = $("#itemSearchResultWell"),
      $itemSearchResultTemplate = $("#itemSearchResultTemplate"),
      $spinner = $itemSearchBox.siblings(".input-group-addon"),
      displayResult = function(){},
      lookupItem = function(){},
      timer = -1;

  $itemSearchBox.on("keyup", function() {
    if (timer > -1) {
      clearTimeout(timer);
    }

    timer = setTimeout(lookupItem, 500);
  });

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

    $.ajax({
      url: "spidy/item-search/" + encodeURIComponent(text),
      dataType: "json",
      success: function(data, status, jqXHR) {
        // TODO: determine if paged results (that Spidy returns) will be
        // problematic here.
        displayResult(data.results);
      },
      beforeSend: function(jqXHR, settings) {
        $spinner.spin("small");
      },
      complete: function(jqXHR, status) {
        $spinner.spin(false);
      }
    });
  };
}(jQuery));