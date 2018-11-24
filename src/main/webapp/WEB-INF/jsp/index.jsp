<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>

	<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- Styles -->
    <link href="../resources/css/main.css" rel="stylesheet">

</head>
<body>

	<H1>DonkeyBug</H1>
    <br />

    <a href="forward">FORWARD</a>


    <br />
    <br />
    <a href="left">LEFT</a>
    <a href="index">STOP</a>
    <a href="right">RIGHT</a>

    <br />
    <br />

    <a href="back">BACK</a>

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
