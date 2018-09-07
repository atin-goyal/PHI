<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phi: Custom</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<script src="jquery/jquery-1.8.3.js"></script>
<script src="jquery/jquery-ui.js"></script>
<script src="jquery/chosen.jquery.js"></script>
<link rel="stylesheet" href="jquery/chosen.css">
<title>Custom Machine Query</title>
</head>
<script type="text/javascript">
var myList=${results};
function buildHtmlTable() {
	var columns = addAllColumnHeaders(myList);
    for (var i = 0 ; i < myList.length ; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
            var cellValue = myList[i][columns[colIndex]];

            if (cellValue == null) { cellValue = ""; }

            row$.append($('<td/>').html(cellValue));
        }
        $("#excelDataTable").append(row$);
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(myList)
{
    var columnSet = [];
    var headerTr$ = $('<tr/>');

    for (var i = 0 ; i < myList.length ; i++) {
        var rowHash = myList[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1){
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    $("#excelDataTable").append(headerTr$);

    return columnSet;
}
</script>

<body  onLoad="buildHtmlTable()">
<div id="header" >
<a class=logo href="PhiSearch">PSR Hardware Info</a>
</div>
<div id="content">
<p>Enter Custom query below</p>
<br>
<form name="frm_custom" action="PhiCustom" method="post">
Select <input type="text" name="columns" size="100" > from servers  <input type="text" name="clause" size="200" >
<input type="submit" value="Submit"></form>
<br><br><br>
<div align="center">
<p>${message}</p>
<table id="excelDataTable"  align="center" border="1" cellpadding="1" cellspacing="0">
 </table>
 </div>
 <br><br><br><br>
 <p><b>Instructions for use</b></p>
 <ol>
 <li>The query given above is executed as is on the DB thus the column names and syntax have to be correct</li>
 <li> Example query: Select <b>puser,count(*) as "no of machines"</b>  from servers <b> group by puser</b> </li>
 <li>Column names in DB are as follows</li></ol>
	<table border="1" cellpadding="1" cellspacing="0">
	<tr><td>Server Name</td><td>SERVER_NAME</td></tr>
	<tr><td>OS</td><td>OS</td></tr>
	<tr><td>Processor</td><td>PROCESSOR</td></tr>
	<tr><td>RAM</td><td>RAM</td></tr>
	<tr><td>Site</td><td>SITE</td></tr>
	<tr><td>Total Disk Space</td><td>DSPACE_TOTAL</td></tr>
	<tr><td>Free space</td><td>DSPACE_FREE</td></tr>
	<tr><td>Status</td><td>STATUS</td></tr>
	<tr><td>Server Type</td><td>SERVER_TYPE</td></tr>
	<tr><td>Primary User</td><td>PUSER</td></tr>
	<tr><td>Project</td><td>PPROJECT</td></tr>
	<tr><td>Manager</td><td>MANAGER</td></tr>
	<tr><td>Comments</td><td>COMMENTS</td></tr>
	<tr><td>Last User</td><td>LUSER</td></tr>
	<tr><td>Username</td><td>USERNAME</td></tr>
	<tr><td>Password</td><td>PWD</td></tr>
	</table>
</div>
<marquee bgcolor="#65659C">
<b><font color="#FFF"><SCRIPT LANGUAGE="JAVASCRIPT">
var r_text = new Array ();
r_text[0] = "Best viewed on 1680*1050 resolution.";
r_text[1] = "You can search for machines assigned to a user by inputting user:name or emailID in the search box.";
r_text[2] = "On Machines page, click on machine name for viewing Last User and Credentials";
r_text[3] = "<b><big>&phi;</big></b> <a href=\"http://en.wikipedia.org/wiki/Golden_ratio\"> The Golden Ratio</a></b>";
r_text[4] = "Click   <a href=\"About.jsp\" >here</a>  to know about PHI ";
var i = Math.floor((r_text.length)*Math.random());
   document.write(r_text[i]);
</SCRIPT>
</font></b>
</marquee>
</body>
</html>