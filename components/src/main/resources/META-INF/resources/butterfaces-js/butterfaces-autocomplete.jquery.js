/**
 * jQuery-Plugin "butterHandleAutoComplete" for text autocomplete tag. Initializes auto complete functionality to
 * text component.
 *
 * How to use:
 * jQuery("#selector")._butterAutoCompleteInit();
 */
(function ($) {
    // extend jQuery --------------------------------------------------------------------

    $.fn._butterAutoCompleteInit = function () {
        return this.each(function () {
            var $autocomplete = $(this);
            var $input = $autocomplete.prev();

            $input.keyup(function () {
                var testOnEvent = function (data) {
                    var $autocomplete2 = $(document.getElementById($autocomplete.attr('id')));
                    if (data.status === 'success') {
                        if ($input.data('data-test') === undefined) {
                            if ($autocomplete2.has('li').size() > 0) {
                                $autocomplete2.css('display', 'inline');
                                $autocomplete2.highlight($input.val(), "search-highlighted");
                                $autocomplete2.find('li').on("click", function () {
                                    $input.val($(this).attr("data-select-value")).change();
                                    $input.data('data-test', 'true');
                                    $input.keyup();
                                    $input.focus();
                                });
                            } else {
                                $autocomplete2.css('display', 'none');
                            }
                        } else {
                            $autocomplete2.css('display', 'none');
                            $input.removeData('data-test');
                        }
                    }
                };

                jsf.ajax.request(this, 'autocomplete', {
                    'javax.faces.behavior.event': 'autocomplete',
                    render: $autocomplete.attr('id'),
                    params: $input.val(),
                    onevent: testOnEvent
                });
            });

            $input.blur(function () {
                window.setTimeout(function () {
                    var $autocomplete2 = $(document.getElementById($autocomplete.attr('id')));
                    $autocomplete2.css('display', 'none');
                }, 100);
            });

        });
    };

}(jQuery));