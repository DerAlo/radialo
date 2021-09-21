$(function () {
  //$('[data-toggle="tooltip"]').tooltip();

  $('[data-toggle="sidebar-offcanvas"]').click(function () {
    $(".sidebar-offcanvas").toggleClass("open");
  });

  var toggleAffix = function (scrollElement, element, topOffset) {
    if (scrollElement >= topOffset) {
      element.addClass("affix");
    } else {
      element.removeClass("affix");
    }
  };

  $(window).on("scroll resize", function () {
    let scrollPosition = $(this).scrollTop();

    $('[data-spy="affix"]').each(function () {
      toggleAffix(scrollPosition, $(this), $(this).attr('data-offset'));
    });
  });
});

// .form-control and dropdown handling. Plain jQuery implementation, add similar behaviour with your js framework, e.g. Angular
$(function () {
  // .form-control input label behaves like a placeholder (dropdown-toggle has specific behaviors)
  $("body").on("focus", ".form-control:not(.dropdown-toggle)", function () {
    $("label[for=" + $(this).attr("id") + "]").addClass("selected");
  });

  $("body").on("blur", ".form-control:not(.dropdown-toggle)", function () {
    if (this.value === "") {
      $("label[for=" + $(this).attr("id") + "]").removeClass("selected");
    }
  });

  $(".form-control").each(function () {
    if (this.value !== "") {
      $("label[for=" + $(this).attr("id") + "]").addClass("selected");
    }
  });

  // dropdown-toggle initial setup
  $(".form-group-dropdown").each(function () {
    $input = $(this).find(".form-group-dropdown-value");
    $input.prop("readonly", true);
    if ($input.val() !== "") {
      $(this).find("label").addClass("selected");
    }
  });

  // dropdown-toggle item selection
  $(".form-group-dropdown").on("click", ".dropdown-menu li", function (evt) {
    $value = $(evt.target).text();
    $label = $(this).closest(".form-group-dropdown").find("label");
    if ($value === "") {
      $label.removeClass("selected");
    } else {
      $label.addClass("selected");
    }
    $(this)
      .closest(".form-group-dropdown")
      .find(".form-group-dropdown-value")
      .val($value);
  });
});
