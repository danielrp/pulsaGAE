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
<title>Customer</title>
<link rel="stylesheet" type="text/css" href="/style.css" />
<script type="text/javascript" src="/clockp.js"></script>
<script type="text/javascript" src="/clockh.js"></script>
<script type="text/javascript" src="/jquery.min.js"></script>
<script type="text/javascript" src="/ddaccordion.js"></script>
<script type="text/javascript">
	ddaccordion.init({
		headerclass : "submenuheader", //Shared CSS class name of headers group
		contentclass : "submenu", //Shared CSS class name of contents group
		revealtype : "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
		mouseoverdelay : 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
		collapseprev : true, //Collapse previous content (so only one open at any time)? true/false 
		defaultexpanded : [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
		onemustopen : false, //Specify whether at least one header should be open always (so never all headers closed)
		animatedefault : false, //Should contents open by default be animated into view?
		persiststate : true, //persist state of opened contents within browser session?
		toggleclass : [ "", "" ], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
		togglehtml : [ "suffix",
				"<img src='images/plus.gif' class='statusicon' />",
				"<img src='images/minus.gif' class='statusicon' />" ], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
		animatespeed : "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
		oninit : function(headers, expandedindices) { //custom code to run when headers have initalized
			//do nothing
		},
		onopenclose : function(header, index, state, isuseractivated) { //custom code to run whenever a header is opened or closed
			//do nothing
		}
	})
</script>

<script type="text/javascript" src="/jconfirmaction.jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.ask').jConfirmAction();
		$('#name').focus();
	});
</script>


<link rel="stylesheet" type="text/css" media="all"
	href="/niceforms-default.css" />
<script type="text/javascript">
	var i = 1;
	$(document).ready(function() {

		//iterate through each textboxes and add keyup
		//handler to trigger sum event
		//alert ('test1');
		//initial();
		i = $('#size').val();
		//alert ('test2');
	});

	//function intial(){
	//alert ($('#size').val());
	//}

	$(function() {
		$("#addRow")
				.click(
						function() {

							//if (i > 4) {
							//		alert("Only 5 textboxes allow");
							//		return false;
							//	}

							row = '<tr><td>'
									+ '<input id="contacts'+i+'.id" name="contacts['+i+'].id" type="hidden" value="" /> '
									+ '<input id="contacts'+i+'.desc" name="contacts['+i+'].desc" type="text" value="" /></td><td>'
									+ '<input id="contacts'+i+'.number" name="contacts['+i+'].number" type="text" value="" /> '
									//		'<td><input type="text" size="22" name="no['+i+']"/></td>'+
									//'<td colspan="2"><input  type="text" id="account" maxlength="15" style="text-align:center" size="79" name="account['+i+']"/></td>'
									//+ '<td align="left"><input class="hitung" type="text" style="text-align:right" size="42" name="amount['+i+']"/></td>'
									//+ '<td><button type="button" class="btnbg2" id="del">-</button></td>'
									+ '</td><td><button type="button" class="btnbg2" id="del">-</button></td></tr>';
							$(row).insertBefore("#myTable");
							i++;
							//		initial();
						});
	});
	$("#del").live('click', function() {

		if (i == 0) {
			alert("No more textbox to remove");
			return false;
		}

		i--;

		$(this).parent().parent().remove();
		//	initial();
	});
</script>
</head>
<body>
	<div id="main_container">
		<jsp:include page="header.jsp" />
		<div class="main_content">
			<jsp:include page="menutop.jsp" />
			<div class="center_content">

				<div class="content">
					<h2>Customer List</h2>
					<form action="download" name="download">
						<input type="submit" value="Download Excel" />
					</form>
					<table id="rounded-corner"
						summary="2007 Major IT Companies' Profit">
						<thead>
							<tr>
								<!-- <th scope="col" class="rounded-company"></th> -->
								<th scope="col" class="rounded">Name</th>
								<th scope="col" class="rounded">Description</th>
								<th scope="col" class="rounded">Number</th>
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
							<c:forEach items="${listCustomer}" var="customer" begin="${page.begin}"
								end="${page.end}" varStatus="status">
								<tr>

									<!-- <td>${customer.id}</td> -->
									<td>${customer.name}</td>
									<td>${customer.desc}</td>
									<td><c:forEach items="${customer.contacts}" var="contact"
											varStatus="status">
					${contact.provider} ${contact.number} ;
										</c:forEach></td>

									<td><a href="/customer/detail?id=${customer.id.id}"><img
											src="/images/user_edit.png" alt="" title="" border="0" /></a></td>
									<td><a href="/customer/remove?id=${customer.id.id}"
										class="ask"><img src="/images/trash.png" alt="" title=""
											border="0" /></a></td>
								</tr>

							</c:forEach>



						</tbody>
					</table>

					


					<c:if test="${page.total gt 1}">
						<div class="pagination">
							<c:choose>
								<c:when test="${page.current eq 1}">
									<span class="disabled"><< prev</span>
								</c:when>
								<c:otherwise>
									<a href="list?p=<c:out value="${page.current-1}"/>"><< prev</a>
								</c:otherwise>
							</c:choose>
							<c:forEach var="i" begin="1" end="${page.total}" step="1"
								varStatus="status">
								<c:choose>
									<c:when test="${page.current eq i}">
										<span class="current"><c:out value="${i}" /></span>
									</c:when>
									<c:otherwise>
										 <a href="list?p=<c:out value="${i}" />"><c:out value="${i}" /></a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${page.current eq page.total}">
									<span class="disabled">next >></span>
								</c:when>
								<c:otherwise>
									<a href="list?p=<c:out value="${page.current+1}"/>">next >></a>
								</c:otherwise>
							</c:choose>
							
						</div>
					</c:if>


					<h2>Customer Add/Edit</h2>

					
							<form:form action="add" commandName="customer" methodParam="POST" class="niceform">
								<form:hidden path="id" />
								<table>
									<tr>
										<td>Name</td>
									<td>:</td>
									<td><form:input path="name" /></td>
									</tr>
									<tr>
										<td>Desc</td>
									<td>:</td>
									<td><form:input path="desc" /></td>
										
									
									</tr>
								
							
								<input type="hidden" name="size" id="size"
									value="${fn:length(customer.contacts)+1}" />
									<tr><td colspan="3">
									<table border="0">
									<tr>

										<td>Description</td>
										<td>Number</td>
										<td><input type="button" class="btnbg2" value="+"
											id="addRow" title="tambah baris" /></td>
									</tr>
									<c:forEach items="${customer.contacts}" varStatus="i">
										<tr>
											<td><form:hidden path="contacts[${i.index}].id" /> <form:input
													path="contacts[${i.index}].desc" type="text" /></td>
											<td><form:input path="contacts[${i.index}].number"
													type="text" /></td>
											<td><button type="button" class="btnbg2" id="del">-</button></td>
										</tr>
									</c:forEach>
									<tr id="myTable">
									</tr>
								</table>
									</td></tr>
								
								
								<tr><td colspan="3"><input type="submit" name="submit" id="submit" value="Save" /></td></tr>
									
								
								</table>
							</form:form>

						


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