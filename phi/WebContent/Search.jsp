<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phi: Home</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<script src="jquery/jquery-1.8.3.js"></script>
<script src="jquery/jquery-ui.js"></script>
<script src="jquery/chosen.jquery.js"></script>
<link rel="stylesheet" href="jquery/chosen.css">
</head>
<script type="text/javascript">
$(function(){
	 $(".chosen-select").chosen({
		no_results_text: "Oops, nothing found!",
		max_selected_options: 4
	 });
	 $('select optgroup option').click(function () {
		    var len = $(this).parent().find(':selected').length;
		    if (len > 1) {
		        alert('only 1 allowed');
		        $(this).prop('selected', false);
		    }
		});
});

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    
    var form = document.createElement("form");
    form.setAttribute("acceptcharset", "ISO-8859-1");
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
	var params = new Array();
	params["action"] = 'unassign_nas';
	params["nas_name"] = nas_name;
	params["share_name"] = share_name;
	params["email"] = email;
	post('${pageContext.request.contextPath}/PhiAssign', params);
  }
}

function search_name()
{
	var search=document.getElementById("SearchString_name");
	var chrome=navigator.userAgent.search("Chrome");
	if (chrome != -1)
	{ event.preventDefault(); }
	if (search.value!=null && search.value!="" && search.value.length>=3)
	  {
		var params = new Array();
		params["SearchString"] = 'name:' + search.value;
		post('${pageContext.request.contextPath}/PhiSearch', params);
		return true;
	  }
	else
		{
			alert("Enter min 3 chars");
			return false;
		}
		
}

function search_user()
{
	var search=document.getElementById("SearchString_user");
	var chrome=navigator.userAgent.search("Chrome");
	if (chrome != -1)
	{ event.preventDefault(); }
	if (search.value!=null && search.value!="" && search.value.length>=3)
	  {
		var params = new Array();
		params["SearchString"] = 'user:' + search.value;
		post('${pageContext.request.contextPath}/PhiSearch', params);
		return true;
	  }
	else
		{
			alert("Enter min 3 chars");
			return false;
		}
}


</script>
<body>
<div id="header" >
<a class=logo href="PhiSearch">PSR Hardware Info</a>
</div>
<div align="center">
	<p>${message1}</p>
          <em>Advanced Server Search</em>
          <br>
          <form name="frm_adv" action="PhiSearch" method="get">
          <select name="SearchString" style="text-align:left;width:350px;" data-placeholder="Select Filters" class="chosen-select" multiple ">
            <option style="text-align:left;" value=""></option>
            <optgroup label="OS" style="text-align:left;">
              <option style="text-align:left;" value="OS:Linux">Linux</option>
              <option style="text-align:left;" value="OS:Windows">Windows</option>
              <option style="text-align:left;" value="OS:Solaris">Solaris</option>
              <option style="text-align:left;" value="OS:AIX">AIX</option>
            </optgroup>
            <optgroup style="text-align:left;" label="Server Type">
              <option style="text-align:left;" value="ST:Appserver 4 core">Appserver 4 core</option>
              <option style="text-align:left;" value="ST:Appserver 8 core">Appserver 8 core</option>
              <option style="text-align:left;" value="ST:Appserver 12 core">Appserver 12 core</option>
              <option style="text-align:left;" value="ST:Appserver Exalogic">Appserver Exalogic</option>
              <option style="text-align:left;" value="ST:Appserver Exalytics">Appserver Exalytics</option>
              <option style="text-align:left;" value="ST:Appserver Solaris">Appserver Solaris</option>
              <option style="text-align:left;" value="ST:Appserver AIX">Appserver AIX</option>
              <option style="text-align:left;" value="ST:Appserver Windows">Appserver Windows</option>
              <option style="text-align:left;" value="ST:OATS Controller">OATS Controller</option>
              <option style="text-align:left;" value="ST:DB">Database</option>
              <option style="text-align:left;" value="NAS:Drives">NAS Drives</option>
             </optgroup>
            <optgroup style="text-align:left;" label="SITE">
              <option style="text-align:left;" value="site:UCF">UCF</option>
              <option style="text-align:left;" value="site:RWS">RWS</option>
            </optgroup>
            <optgroup style="text-align:left;" label="User">
              <option style="text-align:left;" value="user:unassigned">unassigned</option>
            </optgroup>
          </select>
         <input type="submit" value="Search">
         </form>
		<br><br>
		
	<p>${message}</p>
	<br>
	
	<div id="content1">
		<b>My Machines</b>
			<c:if test="${not empty servers}">
		<table align="center" border="1" cellpadding="1" cellspacing="0" width="70%">
			<thead>
					<tr>
					<th scope="col">Server Name</th>
					<th scope="col">OS</th>
					<th scope="col">Processor</th>
					<th scope="col">RAM</th>
					<th scope="col">Site</th>
					<th scope="col">Server Type</th>
					<th scope="col">Monitored Drive</th>
					<th scope="col">Total Disk Space</th>
					<th scope="col">Free space</th>
					<th scope="col">Status</th>
					<th scope="col">Project</th>
					<th scope="col">Comments</th>
					<th scope="col">Action</th>
					</tr>
			</thead>
			<tbody> 
				<c:forEach items="${servers}" var="Servers">
				<tr>
	        	<td><a href="PhiDetail?action=server_detail&name=${Servers.server_name}">${Servers.server_name}</a></td>
	        	<td>${Servers.os}</td>
	        	<td nowrap>${Servers.processor}</td>
	        	<td nowrap>${Servers.ram}</td>
	        	<td>${Servers.site}</td>
	        	<td nowrap>${Servers.server_type}</td>
	        	<td nowrap>${Servers.drive}</td>
	        	<td nowrap>${Servers.dspace_total}</td>
	        	<td nowrap>${Servers.dspace_free}</td>
	        	<td><c:choose>
	        	<c:when test="${Servers.status=='DN'}"><font color="red">${Servers.status}</font>
	        	</c:when>
	        	<c:otherwise>
	        	${Servers.status}
	        	</c:otherwise>
	        	</c:choose>
	        	</td>
	        	<td>${Servers.pproject}</td>
	        	<td width="20%">${Servers.comments}</td>
	        	<td><c:choose>
	      			<c:when test="${Servers.puser==email}"><input type="submit" value="Unassign" onclick="unassign('${email}','${Servers.server_name}')">
	      			</c:when>
	      			<c:otherwise>
	      			</c:otherwise>
	      			</c:choose>
	      			</td>
	    		</tr>
				</c:forEach>
				</tbody>
				</table>
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
	         	<td>${nas.comments}</td>
	         	<td><c:choose>
	      			<c:when test="${nas.puser==email}"><input type="submit" value="Unassign" onclick="unassign_nas('${email}','${nas.nas_name}','${nas.share_name}')">
	      			</c:when>
	      			<c:otherwise>
	      			</c:otherwise>
	      			</c:choose>
	      			</td>
	    		</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>

	</div>

<br><br>
</div>

<div id="navAlpha">
<b> Quick Links</b><br>
<a href="PhiSearch?SearchString=all%3Amachines">* ALL Machines </a> <br>
<a href="PhiSearch?SearchString=site%3ARWS">* RWS Machines</a> <br>
<a href="PhiSearch?SearchString=site%3AUCF">* UCF Machines</a> <br>
<a href="PhiSearch?SearchString=ST%3AOATS+Controller">* OATS Controllers</a> <br>
<a href="PhiSearch?SearchString=user%3Aunassigned">* Free Machines</a>  <br>
<a href="PhiSearch?SearchString=NAS%3Adrives">* NAS Drives</a>  <br>
<a href="PhiSearch?SearchString=user%3A${email}">* My Machines</a>  <br>
<a href="PhiCustom">* Custom Query</a>  <br>
</div>
<div id="navBeta">
 	<b>Simple Searches</b><br><br>
 	<b>Server Name Search</b>
 	
    <form action="#" onsubmit="return search_name()">
    <input type="text" id="SearchString_name" size="20" >
    <input type="submit" value="Search">
    </form>
    <br>
    <b>Assigned to User search</b>
    <form action="#" onsubmit="return search_user()">
    <input type="text" id="SearchString_user" size="20" >
    <input type="submit" value="Search">
    </form>
    <br>
</div>

<div id="footer">
<marquee onmouseover="this.stop();" onmouseout="this.start();">
<script type="text/javascript">
var r_text = new Array ();
r_text[0] = "<b>Total No of Machines: ${no_of_servers}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Free Machines: ${no_of_free_servers}</b>";
r_text[1] = "<b>If server status is DN, PHI is not able to login to that box. Check if the credentials listed in PHI are correct</b>";
r_text[1] = "<b>Contact <a href=\"mailto:atin.goyal@oracle.com\" >admin</a> for any issues</b>";
var i = Math.floor((r_text.length)*Math.random());
document.write(r_text[i]);
</script>
</marquee>
</div>

</body>
</html>