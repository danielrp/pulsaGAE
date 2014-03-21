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
<title>Personal</title>
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
					<h2>Personal Setting</h2>
					<form:form action="update" commandName="userinfo" methodParam="POST"
						class="niceform">
						
						<table>
							<tr>
								<td>Your Username</td>
								<td>:</td>
								<td><form:input path="username" readonly="true"/></td>
							</tr>
							
							<tr>
								<td>Name</td>
								<td>:</td>
								<td><form:input path="name" /></td>
							</tr>
							<tr>
								<td>Email</td>
								<td>:</td>
								<td><form:input path="email" /></td>
							</tr>
							<tr>
								<td>Application Password</td>
								<td>:</td>
								<td><form:password path="passwordApp" /></td>
							</tr>
							<tr>
								<td>Transaction Password</td>
								<td>:</td>
								<td><form:input path="password" /></td>
							</tr>
							<tr>
								<td colspan="3"><input type="submit" name="submit"
									id="submit" value="Save" /></td>
							</tr>
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