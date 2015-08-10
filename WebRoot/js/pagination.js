
$(function () {
	var pageNum = $("#pageNum").val();
	var totalPages = $("#totalPages").val();
	var pagesNearBy = "";
	for (var i = pageNum - 3 > 1 ? pageNum - 3 : 1, j = 0; j != 10; i++, j++) {
		if (i > totalPages) {
			break;
		}
		if (i != pageNum) {
			pagesNearBy += "<a style='color: black;text-decoration:none;' href='javascript:turnToPage(" + i + ")'>[" + i + "]</a>";
		} else {
			pagesNearBy += "<a style='font-size:16px;font-weight: bolder;color: red;text-decoration:none;'>[" + i + "]</a>";
		}
	}
	$("#pagesNearBy").html(pagesNearBy);
	$("#paginationButton").click(function () {
		var paginationInput = $("#paginationInput").val();
		if (paginationInput !== "" && !isNaN(paginationInput)) {
			var num = parseInt(paginationInput);
			turnToPage(num > $("#totalPages").val() ? $("#totalPages").val() : num < 1 ? 1 : num);
		} else {
			$("#paginationInput").val("");
			alert("\u8bf7\u8f93\u5165\u4e00\u4e2a\u6570\u5b57!");
			$("#paginationInput").focus();
		}
	});
	$("#paginationInput").keyup(function (e) {
		if (e.which === 13) {
			$("#paginationButton").click();
		}
	});
	$("#pageRowSize").change(function () {
		$("#submitbutton").click();
	});
});
function turnToPage(page) {
	$("#pageNum").val(page);
	$("#searchForm").submit();
}

