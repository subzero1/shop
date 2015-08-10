
$(function () {
	var now = new Date();
	$(".datepickerhelp").each(function () {
		var target = $(this).attr("target");
		var targets = target.split(" ");
		var appendstr = "";
		$(targets).each(function (key, value) {
			value = $.trim(value);
			var modulestr = "<input id=\"last[id]\" style=\"padding:0;\" type=\"button\" value=\" [lastvalue] \" class=\"datepickerbutton\" target=\"last " + value + "\"/>";
			modulestr += "&nbsp;<input id=\"current[id]\" style=\"padding:0;\" type=\"button\" value=\" [currentvalue] \" class=\"datepickerbutton\" target=\"current " + value + "\"/>";
			modulestr += "&nbsp;<input id=\"next[id]\" style=\"padding:0;\" type=\"button\" value=\" [nextvalue] \" class=\"datepickerbutton\" target=\"next " + value + "\"/>";
			switch (value) {
			  case "day":
				modulestr = modulestr.replace("[lastvalue]", "\u4e0a\u4e00\u5929");
				modulestr = modulestr.replace("[currentvalue]", "\u4eca  \u5929");
				modulestr = modulestr.replace("[nextvalue]", "\u4e0b\u4e00\u5929");
			  case "week":
				modulestr = modulestr.replace("[lastvalue]", "\u4e0a\u4e00\u5468");
				modulestr = modulestr.replace("[currentvalue]", "\u672c  \u5468");
				modulestr = modulestr.replace("[nextvalue]", "\u4e0b\u4e00\u5468");
				break;
			  case "month":
				modulestr = modulestr.replace("[lastvalue]", "\u4e0a\u4e00\u6708");
				modulestr = modulestr.replace("[currentvalue]", "\u672c  \u6708");
				modulestr = modulestr.replace("[nextvalue]", "\u4e0b\u4e00\u6708");
				break;
			  case "season":
				modulestr = modulestr.replace("[lastvalue]", "\u4e0a\u5b63\u5ea6");
				modulestr = modulestr.replace("[currentvalue]", "\u672c\u5b63\u5ea6");
				modulestr = modulestr.replace("[nextvalue]", "\u4e0b\u5b63\u5ea6");
				break;
			  case "year":
				modulestr = modulestr.replace("[lastvalue]", "\u4e0a\u4e00\u5e74");
				modulestr = modulestr.replace("[currentvalue]", "\u672c  \u5e74");
				modulestr = modulestr.replace("[nextvalue]", "\u4e0b\u4e00\u5e74");
				break;
			  default:
				return true;
			}
			appendstr += modulestr + "   ";
		});
		$(this).empty();
		appendstr = $.trim(appendstr).replace(/   /g,"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		$(this).append(appendstr);
		if ($("#" + $(this).attr("datepicker2")).val() >= (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate())) {
			$(".datepickerbutton[target^='next']").attr("disabled","disabled");
		}
	});
	$(".datepickerbutton").click(function () {
		var date0 = $.trim($("#" + $(this).parent().attr("datepicker1")).val()) === "" ? (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate()) : $("#" + $(this).parent().attr("datepicker1")).val();
		var date1 = (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate());
		var date2 = $.trim($("#" + $(this).parent().attr("datepicker2")).val()) === "" ? (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate()) : $("#" + $(this).parent().attr("datepicker2")).val();
		var target = $(this).attr("target");
		var prefix = target.split(" ")[0];
		var word = target.split(" ")[1];
		var date = "";
		var flag = 0;
		if (prefix === "next") {
			date = date2;
			flag = 1;
		} else if (prefix === "current") {
			date = date1;
		} else if (prefix === "last") {
			date = date0;
			flag = -1;
		} else {
			alert(prefix);
			return false;
		}
		if (word === "year") {
			$("#" + $(this).parent().attr("datepicker1")).val(parseInt(date.split("-")[0]) + flag + "-01-01");
			$("#" + $(this).parent().attr("datepicker2")).val(parseInt(date.split("-")[0]) + flag + "-12-31");
		} else if (word === "season") {
			var year = parseInt(date.split("-")[0]);
			var season = parseInt((date.split("-")[1] - 1) / 3) + 1;
			season += flag;
			if (season > 4) {
				year = year + 1;
				season = 1;
			} else if (season < 1) {
				year = year - 1;
				season = 4;
			}
			var end = 31;
			if (season === 2 || season === 3) {
				end = 30;
			}
			var startmonth = ((season * 3 - 2) < 10 ? "0" : "") + (season * 3 - 2);
			var endmonth = (season * 3 < 10 ? "0" : "") + season * 3;
			$("#" + $(this).parent().attr("datepicker1")).val(year + "-" + startmonth + "-01");
			$("#" + $(this).parent().attr("datepicker2")).val(year + "-" + endmonth + "-"+end);
		} else if (word === "month") {
			var year = date.split("-")[0];
			var month = date.split("-")[1];
			month -= -flag;
			if (month > 12) {
				year = parseInt(year) + 1;
				month = parseInt(month) - 12;
			} else if (month < 1) {
				year = parseInt(year) - 1;
				month = parseInt(month) + 12;
			}
			var end = 30;
			if (month === 1 || month === 3 || month === 5 || month === 7 || month === 8 || month === 10 || month === 12) {
				end = 31;
			} else if (month === 2) {
				end = 28;
				if (year % 4 === 0)	{
					end = 29;
					if (year % 100 === 0){
						end = 28;
						if (year % 400 === 0){
							end = 29;
						}
					}
				}		
			}
			month = (month < 10 ? "0" : "") + month;
			$("#" + $(this).parent().attr("datepicker1")).val(year + "-" + month + "-01");
			$("#" + $(this).parent().attr("datepicker2")).val(year + "-" + month + "-"+end);
		} else if (word === "week") {
			var datetmp1 = new Date(date.split("-")[0],date.split("-")[1]-1,date.split("-")[2]);
			datetmp1 = new Date(datetmp1.getTime() - 86400000 * ((datetmp1.getDay() - 1) < 0 ? datetmp1.getDay() + 6 : datetmp1.getDay() - 1));
			datetmp1 = new Date(datetmp1.getTime() + flag * 86400000 * 7);
			var datetmp2 = new Date(datetmp1.getTime() + 86400000 * 6);
			$("#" + $(this).parent().attr("datepicker1")).val(datetmp1.getFullYear() + "-" + (datetmp1.getMonth() < 9 ? "0" : "") + (datetmp1.getMonth() + 1) + "-" + (datetmp1.getDate() < 10 ? "0" : "") + datetmp1.getDate());
			$("#" + $(this).parent().attr("datepicker2")).val(datetmp2.getFullYear() + "-" + (datetmp2.getMonth() < 9 ? "0" : "") + (datetmp2.getMonth() + 1) + "-" + (datetmp2.getDate() < 10 ? "0" : "") + datetmp2.getDate());
		} else if (word === "day") {
			var datetmp = new Date(date.split("-")[0],date.split("-")[1]-1,date.split("-")[2]);
			datetmp = new Date(datetmp.getTime() + flag * 86400000);
			$("#" + $(this).parent().attr("datepicker1")).val(datetmp.getFullYear() + "-" + (datetmp.getMonth() < 9 ? "0" : "") + (datetmp.getMonth() + 1) + "-" + (datetmp.getDate() < 10 ? "0" : "") + datetmp.getDate());
			$("#" + $(this).parent().attr("datepicker2")).val(datetmp.getFullYear() + "-" + (datetmp.getMonth() < 9 ? "0" : "") + (datetmp.getMonth() + 1) + "-" + (datetmp.getDate() < 10 ? "0" : "") + datetmp.getDate());
		} else {
			alert(word);
			return false;
		}
		$("#" + $(this).parent().attr("clickbutton")).click();
	});
});

