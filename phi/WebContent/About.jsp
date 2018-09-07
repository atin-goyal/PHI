<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<title>About</title>
</head>
<body>
<div id="header" >
<a class=logo href="PhiSearch">PSR Hardware Info</a>
</div>
<br><br>
<div id="content">
<h1>PHI</h1>
<h3>V2.4</h3>
<ul>
<li>Added ability to change credentials</li>
<li>Now user gets mail when a machine assigned to them is unassigned</li>
</ul>
<h3>V2.3</h3>
<ul>
<li>Resolved NAS unassign issue</li>
</ul>
<h3>V2.2</h3>
<ul>
<li>Resolved DB Cursor leak issue (hopefully!)</li>
<li>Switched to prepared statement in setRow for better performance</li>
<li>Backend DB update (APEX sync)</li>
</ul>
<h3>V2.1</h3>
<ul>
<li>Switched to BoneCP instead of dbcp to improve response times and stability</li>
</ul>
<h3>V2.0</h3>
<ul>
<li>Major release!!</li>
<li>Now updates total and free disk space for all linux servers every 24 hours and on every assignment</li>
<li>Uses DB Connection pooling. Should be faster</li>
<li>Results are now ordered by server name</li>
</ul>
<h3>V1.2</h3>
<ul>
<li>Better simple searches. Search using any 3 letters!</li>
<li>Comments handling improved</li>
<li>Custom query page for advanced users</li>
</ul>
<h3>V1.1</h3>
<ul>
<li>bugfixes on Home page (Simple Searches)</li>
<li>Code and comments cleanup in delegates</li>
</ul>
<h3>V1 (First Release Version)</h3>
<ul>
<li>Complete makeover of Home Page!</li>
<li>Machines page now shows free machines first if any</li>
<li>Better Search functionality (Multiple filters, case insensitive)</li>
<li>Faster login! (Somewhat :) reduced reqd jdbc connections)</li>
<li>Better email ID select on Login Page</li>
<li>Bug fix for null sessions</li>
<li>Assign and Unassign now redirects to home page</li>
<li>Updated jdbc driver</li>
<li>About and change log page</li>
<li>Better comments handling</li>
</ul>
Created By <a href="mailto:atin.goyal@oracle.com?subject=Regarding PHI">Atin Goyal</a>
</div>
<div id="footer">
<marquee onmouseover="this.stop();" onmouseout="this.start();" >
<SCRIPT LANGUAGE="JAVASCRIPT">
var r_text = new Array ();
r_text[0] = "Best viewed on 1680*1050 resolution.";
r_text[1] = "You can search for machines assigned to a user by inputting user:name or emailID in the search box.";
r_text[2] = "On Machines page, click on machine name for viewing Last User and Credentials";
r_text[3] = "<b><big>&phi;</big></b> <a href=\"http://en.wikipedia.org/wiki/Golden_ratio\"> The Golden Ratio</a></b>";
r_text[4] = "Click  <a href=\"About.jsp\" >here</a> to know about PHI ";
var i = Math.floor((r_text.length)*Math.random());
   document.write(r_text[i]);
</SCRIPT>
</marquee>
</div>
</body>
</html>