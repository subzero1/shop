
(function ($) {
	function _hover(object, bgcolor, covertd) {
		if (covertd) {
			var tdbgcolor = [];
			var index = 0;
			$(object).children().each(function () {
				tdbgcolor[index] = $(this).css("background-color");
				index += 1;
			});
			$(object).hover(function () {
				$(object).children().each(function () {
					$(this).css("background-color", bgcolor);
				});
			}, function () {
				index = 0;
				$(object).children().each(function () {
					$(this).css("background-color", tdbgcolor[index]);
					index += 1;
				});
				$(object).css("background-color", oldbgcolor);
			});
		} else {
			var oldbgcolor = $(object).css("background-color");
			$(object).hover(function () {
				$(object).css("background-color", bgcolor);
			}, function () {
				$(object).css("background-color", oldbgcolor);
			});
		}
	}
	jQuery.fn.niceTable = function (options) {
		var defaults = {cursor:"pointer", oddbgcolor:"#eeffee", evenbgcolor:"white", hovercolor:"#F4C6D0", hoverable:true, covertd:false, nonfirsttr:true};
		var o = jQuery.extend(defaults, options);
		var index = 0;
		return this.each(function () {
			var e = $(this);
			$(this).css("cursor", o.cursor);
			e.find("tr:even").each(function () {
				if (!o.nonfirsttr || index !== 0) {
					$(this).css("background-color", o.evenbgcolor);
					if (o.hoverable) {
						_hover(this, o.hovercolor, o.covertd);
					}
				}
				index += 1;
			});
			e.find("tr:odd").each(function () {
				$(this).css("background-color", o.oddbgcolor);
				if (o.hoverable) {
					_hover(this, o.hovercolor, o.covertd);
				}
			});
		});
	};
})(jQuery);

