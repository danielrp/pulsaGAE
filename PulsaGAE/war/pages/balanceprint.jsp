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

<script type="text/javascript" src="/jquery-1.6.4.min.js"></script>
<link type="text/css" href="/datepicker/jquery.datepick.css"
	rel="stylesheet">
	<script type="text/javascript" src="/datepicker/jquery.datepick.js"></script>
	<script type="text/javascript" src="/jquery.zclip.js"></script>
	<script type="text/javascript" src="/ddaccordion.js"></script>
	<script type="text/javascript" src="/jconfirmaction.jquery.js"></script>
	<script type="text/javascript" src="/jquery.printElement.min.js"></script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#simplePrint").click(function() {
								printElem({});
							});
							$("#PrintinPopup").click(function() {
								printElem({
									printMode : 'popup'
								});
							});
							$("#ChangeTitle")
									.click(
											function() {
												printElem({
													pageTitle : 'thisWillBeTheNameInThePrintersLog.html'
												});
											});
							$("#PopupandLeaveopen").click(function() {
								printElem({
									leaveOpen : true,
									printMode : 'popup'
								});
							});
							$("#stripCSS").click(function() {
								printElem({
									overrideElementCSS : true
								});
							});
							$("#externalCSS")
									.click(
											function() {
												printElem({
													overrideElementCSS : [ 'http://assets3.github.com/stylesheets/bundle.css?180c214baeba2d8a1194e7b48ea7581cfee3e505' ]
												});
											});
						});
		function printElem(options) {
			$('#toPrint').printElement(options);
		}
	</script>
</head>
<body>



	<input type="button" value="Print" id="simplePrint" />
	<div id="toPrint">
		<table>
			<thead>
				<tr>
					<!-- <th scope="col" class="rounded-company"></th> -->
					<th scope="col">No</th>
					<th scope="col">Trans Date</th>
					<th scope="col">Product</th>
					<th scope="col">Customer</th>
					<th scope="col">Number</th>
					<th scope="col">Price</th>


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


				<c:set var="counter" value="${0}" />
				<c:set var="total" value="${0}" />
				<c:forEach items="${listBalance}" var="balance" varStatus="status">
					<c:set var="counter" value="${counter+1}" />
					<c:set var="total" value="${total+balance.price}" />
					<tr>
						<td>${counter}</td>
						<td><fmt:formatDate value="${balance.transactionDate}"
								pattern="yyyy-MMM-dd" /></td>
						<td><c:if test="${balance.type eq 'Sell'}">${balance.product}</c:if></td>
						<td><c:choose>
								<c:when test="${balance.type eq 'Sell'}">${balance.custName}</c:when>
								<c:otherwise>Top Up Saldo</c:otherwise>
							</c:choose></td>
						<td><c:if test="${balance.type eq 'Sell'}">${balance.number}</c:if></td>
						<td align="right"><fmt:formatNumber value="${balance.price}"
								type="number" /></td>
					</tr>

				</c:forEach>
				<tr>
					<td colspan="5" align="center"><b>Total</b></td>

					<td align="right"><b><fmt:formatNumber value="${total}"
								type="number" /></b></td>



				</tr>


			</tbody>
		</table>
	</div>
	<!-- <a href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Add
							new item</strong><span class="bt_green_r"></span></a> <a href="#"
						class="bt_blue"><span class="bt_blue_lft"></span><strong>View
							all items from category</strong><span class="bt_blue_r"></span></a> <a
						href="#" class="bt_red"><span class="bt_red_lft"></span><strong>Delete
							items</strong><span class="bt_red_r"></span></a>
 					-->













</body>
</html>