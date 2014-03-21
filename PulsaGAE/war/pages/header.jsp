<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="header">
	<div class="logo">
		<a href="#"><img src="/images/logo.gif" alt="" title="" border="0" /></a>
	</div>

	<div class="right_header">
		Welcome   <sec:authorize ifAllGranted="ROLE_USER">
               <sec:authentication property="name"/>
            </sec:authorize> | <a href="/logout" class="logout">Logout</a>
	</div>
	<div id="clock_a"></div>
</div>