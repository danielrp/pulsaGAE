<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="menu">
	<ul>
		<!-- <li><a  href="index.html">Admin Home 
	
		
	
		</a></li> -->
		<li><a <c:if test="${(fn:endsWith(pageContext.request.servletPath, 'customer.jsp')) 
			|| (fn:endsWith(pageContext.request.servletPath, 'product.jsp'))}">class="current"</c:if>
		 href="/customer/list">Master<!--[if IE 7]><!--></a>
		<!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
			<ul>
				<li><a href="/customer/list" title="">Customer</a></li>
				<li><a href="/product/list" title="">Product</a></li>
			</ul> <!--[if lte IE 6]></td></tr></table></a><![endif]--></li>
		<li ><a  
		<c:if test="${fn:endsWith(pageContext.request.servletPath, 'balance.jsp')}">class="current"</c:if> 
		href="/transaksi/list?p=0">Transaksi<!--[if IE 7]><!--></a></li>
		<li ><a  <c:if test="${fn:endsWith(pageContext.request.servletPath, 'userinfo.jsp')}">class="current"</c:if> href="/personal/setting">Personal Setting<!--[if IE 7]><!--></a></li>
		<li ><a  <c:if test="${fn:endsWith(pageContext.request.servletPath, 'maintenance.jsp')}">class="current"</c:if> href="/maintenance/maintenance">Maintenance<!--[if IE 7]><!--></a></li>
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
		<li ><a  <c:if test="${fn:endsWith(pageContext.request.servletPath, 'user.jsp')}">class="current"</c:if> href="/admin/user/list">User<!--[if IE 7]><!--></a></li>
		</sec:authorize>
		
	</ul>
</div>
