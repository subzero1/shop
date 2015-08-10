
(function ($) {
function _getEvent(){
	if(window.event){
		return window.event;
	}
	var f = arguments.callee.caller;
	do{
		var e = f.arguments[0];
		if(e && (e.constructor === Event || e.constructor===MouseEvent || e.constructor===KeyboardEvent)){
			return e;
		}
	}while(f=f.caller);
}
	jQuery.fn.draggable = function (options) {
		var defaults = {cursor:"pointer", enable:true};
		var o = jQuery.extend(defaults, options);
		return this.each(function () {
			var z = $(this);
			var originalX = null;
			var originalY = null;
			var offset = null;
			z.css("cursor", o.cursor);
			if (!o.enable) {
				z.unbind("mousedown");
				z.bind("click", z.dblclick());
				z.unbind("dblclick");
				return;
			} else {
				z.bind("dblclick");
				z.unbind("click");
			}
			z.mousedown(function (e) {
				originalX = e.clientX;
				originalY = e.clientY;
				offset = z.offset();
				$("body").bind("mousemove", function (e) {
					z.offset({top:offset.top + e.clientY - originalY, left:offset.left + e.clientX - originalX});
				});
				$("body").bind("mouseup", function (e) {
					$("body").unbind("mousemove");
					$("body").unbind("mouseup");
				});
			});
		});
	};
})(jQuery);

