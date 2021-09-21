(function highlightActiveNavbarItem() {
    $('a[href$="' + location.pathname + '"]').parent().addClass('active');
})();
