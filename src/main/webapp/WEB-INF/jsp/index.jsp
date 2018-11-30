<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>

	<!-- Styles -->
	<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/css/main.css" rel="stylesheet">

    <!-- Scripts -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="../resources/js/app.js"></script>

</head>
<body>

	<H1>DonkeyBug</H1>
    <br />

    <img id="WebcamFeed" src="" />
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

</body>

</html>
