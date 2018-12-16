<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
	<!-- Styles -->
	<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.5.0/css/all.css' integrity='sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU' crossorigin='anonymous'>

    <!-- Scripts -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="js/app.js"></script>
</head>
<body>
    <img class="imageContainer" id="WebcamFeed" src="" />

    <div class="moveBlock">
        <div class="moveButton">
            <i id="forward" class='fas fa-arrow-up arrow'></i>
        </div>
        <div class="moveButton">
            <i id="back" class='fas fa-arrow-down arrow'></i>
        </div>
    </div>

    <div class="rotateBlock">
        <div class="rotateButton">
            <i id="left" class='fas fa-arrow-left arrow'></i>
        </div>
        <div class="rotateButton">
            <i id="right" class='fas fa-arrow-right arrow'></i>
        </div>
    </div>

</body>

</html>
