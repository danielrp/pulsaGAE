<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Transaksi</title>
<link rel="stylesheet" type="text/css" href="/style.css" />
<link rel="stylesheet" type="text/css" href="/jquery-ui-1.9.2.custom.min.css" />
<script type="text/javascript" src="/clockp.js"></script>
<script type="text/javascript" src="/clockh.js"></script>
<script type="text/javascript" src="/jquery-1.6.4.min.js"></script>
<link type="text/css" href="/datepicker/jquery.datepick.css"
	rel="stylesheet">
	<script type="text/javascript" src="/datepicker/jquery.datepick.js"></script>
	<script type="text/javascript" src="/jquery.zclip.js"></script>
	<script type="text/javascript" src="/ddaccordion.js"></script>
	<script type="text/javascript" src="/jconfirmaction.jquery.js"></script>
	<script type="text/javascript" src="/jquery-ui-1.9.2.custom.min.js"></script>

	<script type="text/javascript">
		function copytoYm() {

			///var result = new Object();

			//result.total = $("#ym").html();
			//result.totalDeductions = $("#txtTotalDeductions").val();

			//result.netTotal = result.total - result.totalDeductions;
			//$('#NetTotal').html(result.netTotal);
			$('#ym').val(
					$('#product').val() + "." + $('#number').val() + "."
							+ $('#password').val());
		}
		//function showDate(date) {
		//	alert('The date chosen is ' + date);
		//}
		var allproduct;
		$(document)
				.ready(
						function() {
							$('.ask').jConfirmAction();
							$('.datepicker').datepick({
								dateFormat : 'dd/mm/yyyy'
							//onSelect: showDate
							});
							//$( "#transactionDate" ).datepicker();
							//attach with the id od deductions
							//$("#product").bind("change", copytoYm);
							//alert $('#procuct');

							//	$("#txtBox1").keypress(function() {
							//		 $("#txtBox2").val($(this).val());
							//}
							$("#product").keyup(function() {
								copytoYm();
								//	alert('Handler for .change() called.');
							});
							$("#number").keyup(function() {
								copytoYm();
								//	alert('Handler for .change() called.');
							});
							$("#custList")
									.change(
											function() {
												custList = $("#custList").val()
														.split(';');
												if (custList[0] != "") {
													$("#custName").val(
															custList[0]);
													numList = custList[1]
															.split('~');
													var options = $("#numList");
													options.html("");
													i = 0;
													options.append($(
															"<option />").text(
															"- Choose -"));
													$
															.each(
																	numList,
																	function() {
																		//	alert('asdddd');
																		val = numList[i++]
																				.split('|');
																		if (!(typeof (val[1]) === "undefined")) {
																			//val=numList[i].split('|');
																			// alert('assd');
																			options
																					.append($(
																							"<option />")
																							.val(
																									val[1])
																							.text(
																									val[0]
																											+ "-"
																											+ val[1]));
																		}
																	});

												}

												//	alert('Handler for .change() called.');
											});

							$("#numList").change(function() {
								$("#number").val($("#numList").val());
								copytoYm();
							});

							$.getJSON('/product/allproduct', function(json) {
								$("#product").autocomplete({
									source : json,
									select : function(event, ui) {
										$("#product").val(ui.item.value);
										$("#kredit").val(ui.item.cost);
										$("#price").val(ui.item.price);

										return false;
									}
								});
							});
							//alert ("abc : "+allproduct);
							//autocomplete product

						});

		$(document).ready(function() {

			$('#copy-dynamic').zclip({
				path : '/ZeroClipboard.swf',
				copy : function() {
					//alert('asd');
					return 'abc';
				}
			});
			$('a.copy-dynamic').click(function() {

			})
			copytoYm();

		});
		$(document).ready(function() {

			$("a#copy-callbacks").zclip({
				path : '/ZeroClipboard.swf',
				copy : $('#callback-paragraph').text(),
				beforeCopy : function() {
					$('#callback-paragraph').css('background', 'yellow');
					$(this).css('color', 'orange');
				},
				afterCopy : function() {
					$('#callback-paragraph').css('background', 'green');
					$(this).css('color', 'purple');
					$(this).next('.check').show();
				}
			});

		});
	</script>



	<style type="text/css">
body {
	font-family: arial, sans-serif;
	font-size: 9pt;
}

.my_clip_button {
	text-align: center;
	border: 1px solid black;
	background-color: #ccc;
	cursor: default;
	font-size: 9pt;
}

.my_clip_button.hover {
	background-color: #eee;
}

.my_clip_button.active {
	background-color: #aaa;
}
</style>



	<link rel="stylesheet" type="text/css" media="all"
		href="/niceforms-default.css" />
</head>
<body>

	<div id="main_container">
		<jsp:include page="header.jsp" />
		<div class="main_content">
			<jsp:include page="menutop.jsp" />
			<div class="center_content">

				<div class="content">
					<h2>Transaction</h2>
					<table id="rounded-corner"
						summary="Balance">
						<thead>
							<tr>
								<!-- <th scope="col" class="rounded-company"></th> -->
								<th scope="col" class="rounded">No</th>
								<th scope="col" class="rounded">Trans Date</th>
								<th scope="col" class="rounded">Product</th>
								<th scope="col" class="rounded">Customer</th>
								<th scope="col" class="rounded">Number</th>
								<th scope="col" class="rounded">Price</th>
								<th scope="col" class="rounded">Paid</th>
								<th scope="col" class="rounded">Debet/Kredit</th>
								<th scope="col" class="rounded">Balance</th>
								<th scope="col" class="rounded">Profit</th>
								<th scope="col" class="rounded">Edit</th>
								<th scope="col" class="rounded-q4">Delete</th>
							</tr>
						</thead>
						<!-- 	<tfoot>
							<tr>
								<td colspan="5" class="rounded-foot-left"><em>Lorem
										ipsum dolor sit amet, consectetur adipisicing elit, sed do
										eiusmod tempor incididunt ut.</em></td>
								<td class="rounded-foot-right">&nbsp;</td>
							</tr>
						</tfoot> -->
						<tbody>
							<form action="list" name="filter">
								<input type="hidden" name="p" value="${page.current}" />
								Search Name : <input type="text" name="search"
									value="${page.search}" /> Status <select name="pay"
									onchange="filter.submit()">
									<option value="a"
										<c:if test="${page.paid eq 'a'}"> selected </c:if>>All</option>
									<option value="n"
										<c:if test="${page.paid eq 'n'}"> selected </c:if>>Not
										Paid</option>
									<option value="p"
										<c:if test="${page.paid eq 'p'}"> selected </c:if>>Paid</option>
								</select> <input type="submit" value="search" />
							</form>
							<form action="download" name="download">
								<input type="submit" value="Download Excel" />
							</form>
							<form action="email" name="email">
								<input type="submit" value="Email Back up" />
							</form>
							<form action="view" name="view" target="_BLANK">
								<input type="submit" value="Print" />
							</form>
							<c:forEach items="${listBalance}" begin="${page.begin}"
								end="${page.end}" var="balance" varStatus="status">
								<tr>
									<td>${balance.urut}</td>
									<td><fmt:formatDate value="${balance.transactionDate}"
											pattern="yyyy-MMM-dd" /></td>
									<td><c:if test="${balance.type eq 'Sell'}">${balance.product}</c:if></td>
									<td><c:choose>
											<c:when test="${balance.type eq 'Sell'}">${balance.custName}</c:when>
											<c:otherwise>Top Up Saldo</c:otherwise>
										</c:choose></td>
									<td><c:if test="${balance.type eq 'Sell'}">${balance.number}</c:if></td>
									<td><fmt:formatNumber value="${balance.price}"
											type="number" /></td>
									<c:choose>
										<c:when test="${balance.paid eq 'p'}">
											<td>Paid</td>
										</c:when>
										<c:otherwise>
											<td style="background-color: lime;">Not Paid</td>
										</c:otherwise>
									</c:choose>
									<td><fmt:formatNumber
											value="${balance.debet-balance.kredit}" type="number" /></td>
									<td><fmt:formatNumber value="${balance.balance}"
											type="number" /></td>
									<td><c:if test="${balance.type eq 'Sell'}">
											<fmt:formatNumber value="${balance.profit}" type="number" />
										</c:if></td>
									<td><a href="/transaksi/detail?id=${balance.id.id}"><img
											src="/images/user_edit.png" alt="" title="" border="0" /></a></td>
									<td><a href="/transaksi/remove?id=${balance.id.id}"
										class="ask"><img src="/images/trash.png" alt="" title=""
											border="0" /></a></td>
								</tr>

							</c:forEach>

							<tr>
								<td colspan="12" align="center"><b>Your Balance : <fmt:formatNumber
											value="${page.balance}" type="number" /> | Total Debet : <fmt:formatNumber
											value="${page.debet}" type="number" /> | Total Kredit : <fmt:formatNumber
											value="${page.kredit}" type="number" /> | Total Profit : <fmt:formatNumber
											value="${page.profit}" type="number" /> | Account Receivable : <fmt:formatNumber
											value="${page.receivable}" type="number" />| Account Payable : <fmt:formatNumber
											value="${page.payable}" type="number" /></b></td>
							</tr>

						</tbody>
					</table>

					<!-- <a href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Add
							new item</strong><span class="bt_green_r"></span></a> <a href="#"
						class="bt_blue"><span class="bt_blue_lft"></span><strong>View
							all items from category</strong><span class="bt_blue_r"></span></a> <a
						href="#" class="bt_red"><span class="bt_red_lft"></span><strong>Delete
							items</strong><span class="bt_red_r"></span></a>
 					-->
					<c:if test="${page.total gt 1}">
						<div class="pagination">
							<c:choose>
								<c:when test="${page.current eq 1}">
									<span class="disabled"><< prev</span>
								</c:when>
								<c:otherwise>
									<a
										href="list?p=<c:out value="${page.current-1}"/>&pay=${page.paid}&search=${page.search}"><<
										prev</a>
								</c:otherwise>
							</c:choose>
							<c:forEach var="i" begin="1" end="${page.total}" step="1"
								varStatus="status">
								<c:choose>
									<c:when test="${page.current eq i}">
										<span class="current"><c:out value="${i}" /></span>
									</c:when>
									<c:otherwise>
										<a
											href="list?p=<c:out value="${i}" />&pay=${page.paid}&search=${page.search}"><c:out
												value="${i}" /></a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${page.current eq page.total}">
									<span class="disabled">next >></span>
								</c:when>
								<c:otherwise>
									<a
										href="list?p=<c:out value="${page.current+1}"/>&pay=${page.paid}&search=${page.search}">next
										>></a>
								</c:otherwise>
							</c:choose>

						</div>
					</c:if>
					<table width="100%">
						<tr valign="top">
							<td width="60%">
								<h2>Transaksi</h2> <form:form action="sell"
									commandName="sellForm" methodParam="POST" class="niceform">
									<form:hidden path="id" />
									<form:hidden path="type" />
									<table>
										<tr>
											<td>Nomor</td>
											<td>:</td>
											<td><form:input path="urut" /></td>
										</tr>
										<tr>
											<td>Transaction Date</td>
											<td>:</td>
											<td><form:input path="transactionDate"
													class="datepicker" /></td>
										</tr>
										<tr>
											<td>Product</td>
											<td>:</td>
											<td><form:input path="product" /></td>
										</tr>
										<tr>
											<td>Customer</td>
											<td>:</td>
											<td><form:input path="custName" /></td>
											<td><select name"custList" id="custList">
													<option value=";">- Choose One -</option>
													<c:forEach items="${listCust}" var="customer"
														varStatus="status">
														<option
															value="${customer.name};<c:forEach items="${customer.contacts}" var="contact">
															${contact.desc}|${contact.number}~
														</c:forEach>">${customer.name}</option>
													</c:forEach>
											</select></td>
										</tr>
										<tr>
											<td>Number</td>
											<td>:</td>
											<td><form:input path="number" /></td>
											<td><select id="numList" name="numList">
													<option>- Choose -</option>
											</select></td>
										</tr>
										<tr>
											<td>Cost</td>
											<td>:</td>
											<td><form:input path="kredit" /></td>
										</tr>
										<tr>
											<td>Price</td>
											<td>:</td>
											<td><form:input path="price" /></td>
										</tr>
										<tr>
											<td>Status</td>
											<td>:</td>
											<td><form:select path="status">
													<form:option value="s">Sent</form:option>
													<form:option value="p">Pending</form:option>
													<form:option value="r">Received</form:option>
												</form:select></td>
										</tr>
										<tr>
											<td>Paid</td>
											<td>:</td>
											<td><form:select path="paid">
													<form:option value="n">Not Paid</form:option>
													<form:option value="p">Paid</form:option>
												</form:select></td>
										</tr>

										<tr>
											<td colspan="3"><input type="submit" name="submit"
												id="submit" value="Save" /></td>
										</tr>
										<tr>
											<td colspan=3><input type="text" name="ym" id="ym"
												value="." /> <input type="hidden" name="password"
												id="password" value="${page.password}" /> <!-- <a id="copy-dynamic" class="copy-dynamic" href="#">copy</a></td> -->
										</tr>

									</table>
								</form:form>
							</td>
							<td width="40%">
								<h2>Top Up</h2> <form:form action="buy" commandName="buyForm"
									methodParam="POST" class="niceform">
									<form:hidden path="id" />
									<form:hidden path="type" />

									<table>
										<tr>
											<td>Nomor</td>
											<td>:</td>
											<td><form:input path="urut" /></td>
										</tr>
										<tr>
											<td>Transaction Date</td>
											<td>:</td>
											<td><form:input path="transactionDate"
													class="datepicker" /></td>
										</tr>
										<tr>
											<td>Saldo Masuk</td>
											<td>:</td>
											<td><form:input path="debet" /></td>
										</tr>
										<tr>
											<td>Price</td>
											<td>:</td>
											<td><form:input path="price" /></td>
										</tr>
										<tr>
											<td>Status</td>
											<td>:</td>
											<td><form:select path="status">
													<form:option value="s">Sent</form:option>
													<form:option value="p">Pending</form:option>
													<form:option value="r">Received</form:option>
												</form:select></td>
										</tr>
										<tr>
											<td>Paid</td>
											<td>:</td>
											<td><form:select path="paid">
													<form:option value="n">Not Paid</form:option>
													<form:option value="p">Paid</form:option>
												</form:select></td>
										</tr>

										<tr>
											<td colspan="3"><input type="submit" name="submit"
												id="submit" value="Save" /></td>
										</tr>


									</table>
								</form:form>
							</td>
						</tr>
					</table>





				</div>
				<!-- end of right content-->


			</div>
			<!--end of center content -->




			<div class="clear"></div>
		</div>
		<!--end of main content-->


		<jsp:include page="footer.jsp" />

	</div>
</body>
</html>