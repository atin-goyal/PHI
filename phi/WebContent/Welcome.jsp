<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Phi: Welcome</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/phi.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/phi.ico" >
<script src="jquery/jquery-1.8.3.js"></script>
<script src="jquery/jquery-ui.js"></script>
<script src="jquery/chosen.jquery.js"></script>
<link rel="stylesheet" href="jquery/chosen.css">
</head>

<script language = "Javascript">
function emailcheck(str) {

var at="@";
var dot=".";
var lat=str.indexOf(at);
var lstr=str.length;
var ldot=str.indexOf(dot);
if (str.indexOf(at)==-1){
alert("Invalid E-mail ID");
return false;
}

if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
alert("Invalid E-mail ID");
return false;
}

if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
alert("Invalid E-mail ID");
return false;
}

if (str.indexOf(at,(lat+1))!=-1){
alert("Invalid E-mail ID");
return false;
}

if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
alert("Invalid E-mail ID");
return false;
}

if (str.indexOf(dot,(lat+2))==-1){
alert("Invalid E-mail ID");
return false;
}

if (str.indexOf(" ")!=-1){
alert("Invalid E-mail ID");
return false;
}
return true ;
}

function ValidateEmail(){
var emailID=document.frm.email;

if ((emailID.value==null)||(emailID.value=="")){
alert("Please Enter your Email Address");
emailID.focus();
return false;
}
if (emailcheck(emailID.value)==false){
emailID.value="";
emailID.focus();
return false;
}
return true;
}

function updateText() {
	var selector=document.getElementById("selector");
	var value=selector.options[selector.selectedIndex].text;
  	document.frm.email_form.value = value;
}

$(function(){
	 $(".chzn-select").chosen({
		 no_results_text: "Oops, nothing found!"
		});
});

</script>

<body>
<div id="header">
PSR Hardware Info
</div>
<div id="main">
	<div align="center">
  	<br>
	<form name="frm" action="PhiWelcome" method="post" onSubmit="return ValidateEmail()">
    <br>
    <b>Please select your email ID to proceed</b>
    <input type="hidden" value="" id="email_form" name="email">
    <br>
    Authorised Users
    <br>
   	<select style="text-align:left" data-placeholder="Please select your email ID to proceed" class="chzn-select" id="selector" onChange=updateText() >
	<c:forEach items="${emails}" var="Emails">
 	<option style="text-align:left" value="${Emails}">${Emails}</option>
	</c:forEach>
	</select>
    <input type="submit" value="Submit">
   	</form>
   </div>
</div>
<div id="footer">
<marquee onmouseover="this.stop();" onmouseout="this.start();">
<script type="text/javascript">
var r_text = new Array ();
r_text[0] = "<b>Best viewed on 1680*1050 resolution.</b>";
r_text[1] = "<b>You can search for machines assigned to a user by inputting user:name or emailID in the search box.</b>";
r_text[2] = "<b>On Machines page, click on machine name for viewing Last User and Credentials</b>";
r_text[3] = "<b><big>&phi;</big></b> <a href=\"http://en.wikipedia.org/wiki/Golden_ratio\"> The Golden Ratio</a></b>";
r_text[4] = "<b>This site uses cookies</b>";
var i = Math.floor((r_text.length)*Math.random());
document.write(r_text[i]);
</script>
</marquee>
</div>
</body>
</html>
