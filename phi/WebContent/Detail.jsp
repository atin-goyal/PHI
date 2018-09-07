<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phi: Detail</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
</head>
<script>
function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

function change_credentials(email,name,u1,p1)
{
	if (u1!=null && u1!="" && p1!=null && p1!="")
	  {
		var params = new Array();
		params["action"] = 'change_credentials';
		params["name"] = name;
		params["email"] = email;
		params["username"] = u1;
		params["password"] = p1;
		post('${pageContext.request.contextPath}/PhiAssign', params);
	  }
	else
		alert("Invalid Value");
}
</script>
<body>
<div id="header">
<a class=logo href="PhiSearch">PSR Hardware Info</a>
</div>
<div id="main">
	<br><br>
	<c:if test="${not empty servers}">
		<table align="center" border="1" cellpadding="1" cellspacing="0">
			<thead>
					<tr><th scope="col">Server Name</th>
					<th scope="col">Last User</th>
					<th scope="col">User Name</th>
					<th scope="col">Password</th>
					<th scope="col">Action</th>
			</thead>
			<tbody> 
				<c:forEach items="${servers}" var="Servers">
				<tr>
	        	<td>${Servers.server_name}</td>
	        	<td>${Servers.luser}</td>
	        	<td>
	        	<c:choose>
	      		<c:when test="${Servers.puser=='unassigned'}">
	      		<textarea id="username" rows="1" cols="8">${Servers.username}</textarea>
	      		</c:when>
	      		<c:otherwise>
	      		${Servers.username}
	      		</c:otherwise>
	      		</c:choose>
	        	</td>
	        	<td>
	        	<c:choose>
	      		<c:when test="${Servers.puser=='unassigned'}">
	      		<textarea id="password" rows="1" cols="10">${Servers.pwd}</textarea>
	      		</c:when>
	      		<c:otherwise>
	      		${Servers.pwd}
	      		</c:otherwise>
	      		</c:choose>
	        	</td>
	        	<c:choose>
	      		<c:when test="${Servers.puser=='unassigned'}">
	      		<td> 
	      		<input type="submit" id="Submit" value="Submit" onclick="change_credentials('${email}','${Servers.server_name}',username.value,password.value)">
	      		</td>
	      		</c:when>
	      		</c:choose>
	    		</tr>
				</c:forEach>
				</tbody>
				</table>
			</c:if>
	<c:if test="${not empty nas}">
		<center><p><b>NAS Machines</b></p></center>
		<table align="center" border="1" cellpadding="1" cellspacing="0">
			<thead>
					<tr><th scope="col">NAS Name</th>
					<th scope="col">Share Name</th>
					<th scope="col">Server Name</th>
					<th scope="col">Mount Path</th>
					<th scope="col">Site</th>
					<th scope="col">Total Disk Space</th>
					<th scope="col">Free space</th>
					<th scope="col">Status</th>
					<th scope="col">Primary User</th>
					<th scope="col">Project</th>
					<th scope="col">Comments</th>
					</tr>
			</thead>
			<tbody> 
				<c:forEach items="${nas}" var="nas">
				<tr>
	        	<td><a href="PhiDetail?name=${nas.nas_name}">${nas.nas_name}</a></td>
	        	<td>${nas.share_name}</td>
	        	<td>${nas.server_name}</td>
	        	<td>${nas.mount_path}</td>
	        	<td>${nas.site}</td>
	        	<td>${nas.dspace_total}</td>
	        	<td>${nas.dspace_free}</td>
	        	<td>${nas.status}</td>
	        	<td>
	        		<c:choose>
	      			<c:when test="${nas.puser=='unassigned'}">unassigned
	      			</c:when>
	      			<c:otherwise>
	      			<a href="mailto:${nas.puser}?subject=Regarding ${nas.nas_name}">${nas.puser_name}</a>
	      			</c:otherwise>
					</c:choose>
				</td>
	        	<td>${nas.pproject}</td>
	        	<td>${nas.comments}</td>
	    		</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>
</div>

<div id="footer">
    <marquee loop="-1" scrollamount="4" width="100%">Best viewed on 1680*1050 resolution.</marquee>
</div>
        	

</body>
</html>