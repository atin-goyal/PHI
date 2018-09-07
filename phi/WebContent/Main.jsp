<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phi: Machines</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<script src="jquery/jquery-1.8.3.js"></script>
<script src="jquery/jquery-ui.js"></script>
<script src="jquery/chosen.jquery.js"></script>
<link rel="stylesheet" href="jquery/chosen.css">
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

function unassign(email,name)
{
var r = confirm("Are you sure? Press OK to confirm");
if (r==true)
  {
	//window.location.href="${pageContext.request.contextPath}/PhiAssign?action=unassign&name="+ name + "&email=" + email;
	var params = new Array();
	params["action"] = 'unassign';
	params["name"] = name;
	params["email"] = email;
	post('${pageContext.request.contextPath}/PhiAssign', params);
  }
}

function unassign_nas(email,nas_name,share_name)
{
var r = confirm("Are you sure? Press OK to confirm");
if (r==true)
  {
	//window.location.href="${pageContext.request.contextPath}/PhiAssign?action=unassign_nas&nas_name="+ nas_name + "&share_name="+ share_name+ "&email=" + email; 
	var params = new Array();
	params["action"] = 'unassign_nas';
	params["nas_name"] = nas_name;
	params["share_name"] = share_name;
	params["email"] = email;
	post('${pageContext.request.contextPath}/PhiAssign', params);
  }
}

function assign_nas(email,nas_name,share_name,c1)
{
	var project=prompt("Please enter the project name");
	if(nas_name==window.nas && share_name==window.share)
		var text=window.comments1;
	else
		var text=c1;
	if (project!=null && project!="")
	  {
		//window.location.href="${pageContext.request.contextPath}/PhiAssign?action=assign_nas&nas_name="+ nas_name + "&share_name="+ share_name+ "&email=" + email + "&project=" + project + "&comments=" + text;
		var params = new Array();
		params["action"] = 'assign_nas';
		params["nas_name"] = nas_name;
		params["share_name"] = share_name;
		params["email"] = email;
		params["project"] = project;
		params["comments"] = text;
		post('${pageContext.request.contextPath}/PhiAssign', params);
	  }
}
function assign(email,name,c1,d1)
{
	var project=prompt("Please enter the project name");
	if(name==window.server)
		var text=window.comments;
	else
		var text=c1;
	if(name==window.server_d)
		var text_d=window.drive;
	else
		var text_d=d1;
	if (project!=null && project!="")
	  {
		var params = new Array();
		params["action"] = 'assign';
		params["name"] = name;
		params["email"] = email;
		params["project"] = project;
		params["comments"] = text;
		params["drive"] = text_d;
		post('${pageContext.request.contextPath}/PhiAssign', params);
	  }
	else
		alert("Invalid Value");
}

$(function(){
	 $(".chzn-select").chosen({
		 no_results_text: "Oops, nothing found!"
		});
});
function comments(e,name)
{
	window.comments=e.value;
	window.server=name;
}
function drive(e,name)
{
	window.drive=e.value;
	window.server_d=name;
}
function comments1(e,name,share)
{
	window.comments1=e.value;
	window.nas=name;
	window.share=share;
}

</script>
 <body>
<div id="header">
<a class=logo href="PhiSearch">PSR Hardware Info</a>
</div>
<div id="content">
    <center><p><b> ${message} </b></p></center>
    <c:choose>
      			<c:when test="${message=='No Results'}">
      			</c:when>
    <c:otherwise>
		<c:if test="${not empty servers}">
		<c:if test="${free=='yes'}">
		<center><p><b>Free Machines</b></p></center>
		<table align="center" border="1" cellpadding="1" cellspacing="0">
			<thead>
					<tr><th scope="col">Server Name</th>
					<th scope="col">OS</th>
					<th scope="col">Processor</th>
					<th scope="col">RAM</th>
					<th scope="col">Site</th>
					<th scope="col">Server Type</th>
					<th scope="col">Monitored Drive</th>
					<th scope="col">Total Disk Space</th>
					<th scope="col">Free space</th>
					<th scope="col">Status</th>
					<th scope="col">Primary User</th>
					<th scope="col">Project</th>
					<th scope="col">Manager</th>
					<th scope="col">Comments</th>
					<th scope="col">Action</th>
					</tr>
			</thead>
			<tbody> 
				<c:forEach items="${servers}" var="Servers">
				<tr>
	        	<c:if test="${Servers.puser=='unassigned'}">
	        	<td><a href="PhiDetail?action=server_detail&name=${Servers.server_name}">${Servers.server_name}</a></td>
	        	<td>${Servers.os}</td>
	        	<td nowrap>${Servers.processor}</td>
	        	<td nowrap>${Servers.ram}</td>
	        	<td>${Servers.site}</td>
	        	<td nowrap>${Servers.server_type}</td>
	        	<td>
      			<textarea id="comments" rows="1" cols="5" onchange="drive(this,'${Servers.server_name}')">${Servers.drive}</textarea>
				</td>
	        	<td>${Servers.dspace_total}</td>
	        	<td>${Servers.dspace_free}</td>
	        	<td>
	        	<c:choose>
	        	<c:when test="${Servers.status=='DN'}"><font color="red">${Servers.status}</font>
	        	</c:when>
	        	<c:otherwise>
	        	${Servers.status}
	        	</c:otherwise>
	        	</c:choose>
	        	</td>
	        	<td nowrap>
	        	unassigned
	      		</td>
	        	<td>${Servers.pproject}</td>
	        	<td nowrap><a href="mailto:${Servers.manager}?subject=Regarding ${Servers.server_name}">${Servers.manager_name}</a></td>
	        	<td>
	        	<textarea id="comments" rows="2" cols="40" onchange="comments(this,'${Servers.server_name}')">${Servers.comments}</textarea>
	      		</td>
	         	<td>
	         	<input type="submit" id="Assign" value="Assign" onclick="assign('${email}','${Servers.server_name}','${Servers.comments}','${Servers.drive}')">
	      		</td>
				</c:if>
	    		</tr>
				</c:forEach>
				</tbody>
				</table>
		</c:if><br><br>
		
		<c:if test="${unfree=='yes'}">
		<center><p><b>Used Machines</b></p></center>
		<table align="center" border="1" cellpadding="1" cellspacing="0">
			<thead>
					<tr><th scope="col">Server Name</th>
					<th scope="col">OS</th>
					<th scope="col">Processor</th>
					<th scope="col">RAM</th>
					<th scope="col">Site</th>
					<th scope="col">Server Type</th>
					<th scope="col">Monitored Drive</th>
					<th scope="col">Total Disk Space</th>
					<th scope="col">Free space</th>
					<th scope="col">Status</th>
					<th scope="col">Primary User</th>
					<th scope="col">Project</th>
					<th scope="col">Manager</th>
					<th scope="col">Comments</th>
					<th scope="col">Action</th>
					</tr>
			</thead>
			<tbody> 
				<c:forEach items="${servers}" var="Servers">
				<tr>
	        	<c:if test="${Servers.puser!='unassigned'}">
	        	<td><a href="PhiDetail?action=server_detail&name=${Servers.server_name}">${Servers.server_name}</a></td>
	        	<td nowrap>${Servers.os}</td>
	        	<td nowrap>${Servers.processor}</td>
	        	<td nowrap>${Servers.ram}</td>
	        	<td>${Servers.site}</td>
	        	<td nowrap>${Servers.server_type}</td>
	        	<td>${Servers.drive}</td>
	        	<td>${Servers.dspace_total}</td>
	        	<td>${Servers.dspace_free}</td>
	        	<td><c:choose>
	        	<c:when test="${Servers.status=='DN'}"><font color="red">${Servers.status}</font>
	        	</c:when>
	        	<c:otherwise>
	        	${Servers.status}
	        	</c:otherwise>
	        	</c:choose>
	        	</td>
	        	<td nowrap><c:choose>
	      			<c:when test="${Servers.puser=='unassigned'}">unassigned
	      			</c:when>
	      			<c:otherwise>
	      			<a href="mailto:${Servers.puser}?subject=Regarding ${Servers.server_name}">${Servers.puser_name}</a>
	      			</c:otherwise>
				</c:choose></td>
	        	<td>${Servers.pproject}</td>
	        	<td nowrap><a href="mailto:${Servers.manager}?subject=Regarding ${Servers.server_name}">${Servers.manager_name}</a></td>
	        	<td><c:choose>
	      			<c:when test="${Servers.puser=='unassigned'}">
	      			<textarea id="comments" rows="2" cols="40" >${Servers.comments}</textarea>
	      			</c:when>
	      			<c:otherwise>
	      			${Servers.comments}
	      			</c:otherwise>
				</c:choose></td>
	         	<td><c:choose>
	      			<c:when test="${Servers.puser=='unassigned'}"><input type="submit" value="Assign" onclick="assign('${email}','${Servers.server_name}')">
	      			</c:when>
	      			<c:otherwise>
	      			<c:choose>
	      			<c:when test="${Servers.puser==email}"><input type="submit" value="Unassign" onclick="unassign('${email}','${Servers.server_name}')">
	      			</c:when>
	      			<c:otherwise>
	      			</c:otherwise>
	      			</c:choose>
	      			</c:otherwise>
				</c:choose></td>
				</c:if>
	    		</tr>
				</c:forEach>
				</tbody>
				</table>
				</c:if>
		</c:if>
		
		<br><br><br>

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
					<th scope="col">Action</th>
					</tr>
			</thead>
			<tbody> 
				<c:forEach items="${nas}" var="nas">
				<tr>
	        	<td><a href="PhiDetail?action=nas_detail&nas_name=${nas.nas_name}&share_name=${nas.share_name}">${nas.nas_name}</a></td>
	        	<td>${nas.share_name}</td>
	        	<td>${nas.server_name}</td>
	        	<td>${nas.mount_path}</td>
	        	<td>${nas.site}</td>
	        	<td>${nas.dspace_total}</td>
	        	<td>${nas.dspace_free}</td>
	        	<td>${nas.status}</td>
	        	<td nowrap>
	        		<c:choose>
	      			<c:when test="${nas.puser=='unassigned'}">unassigned
	      			</c:when>
	      			<c:otherwise>
	      			<a href="mailto:${nas.puser}?subject=Regarding ${nas.nas_name}">${nas.puser_name}</a>
	      			</c:otherwise>
					</c:choose>
				</td>
	        	<td>${nas.pproject}</td>
	        	<td>
	        	<c:choose>
	      			<c:when test="${nas.puser=='unassigned'}">
	      			<textarea id="comments1" rows="2" cols="40" onchange="comments1(this,'${nas.nas_name}','${nas.share_name}')">${nas.comments}</textarea>
	      			</c:when>
	      			<c:otherwise>
	      			${nas.comments}
	      			</c:otherwise>
				</c:choose></td>
	         	<td><c:choose>
	      			<c:when test="${nas.puser=='unassigned'}"><input type="submit" id="Assign_nas" value="Assign" onclick="assign_nas('${email}','${nas.nas_name}','${nas.share_name}','${nas.comments}')">
	      			</c:when>
	      			<c:otherwise>
	      			<c:choose>
	      			<c:when test="${nas.puser==email}"><input type="submit" value="Unassign" onclick="unassign_nas('${email}','${nas.nas_name}','${nas.share_name}')">
	      			</c:when>
	      			<c:otherwise>
	      			</c:otherwise>
	      			</c:choose>
	      			</c:otherwise>
				</c:choose></td>
	    		</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>
	
	</c:otherwise>
	</c:choose>
</div>
<marquee bgcolor="#65659C" onmouseover="this.stop();" onmouseout="this.start();">
<b><font color="#FFF">
<script type="text/javascript">
var r_text = new Array ();
r_text[0] = "<b>Best viewed on 1680*1050 resolution.</b>";
r_text[1] = "<b>You can search for machines assigned to a user by inputting user:name or emailID in the search box.</b>";
r_text[2] = "<b>On Machines page, click on machine name for viewing Last User and Credentials</b>";
r_text[3] = "<b><big>&phi;</big></b> <a href=\"http://en.wikipedia.org/wiki/Golden_ratio\"> The Golden Ratio</a></b>";
r_text[4] = "<b>Click   <a href=\"About.jsp\" >here</a>  to know about PHI</b>";
var i = Math.floor((r_text.length)*Math.random());
   document.write(r_text[i]);
</script>
</font></b>
</marquee>
</body>
</html>
