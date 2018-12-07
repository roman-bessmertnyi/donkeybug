var stompClient = null;

var socketUrl = '/donkeybug_websocket';

/*function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}*/

function connect() {
	var socket = new SockJS(socketUrl);
	stompClient = Stomp.over(socket);
	stompClient.connect({},
	function (frame) {
		//setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/webcam', function (webcamDTO) {
			showImage(JSON.parse(webcamDTO.body).image);
		});
		stompClient.send("/app/webcam", {}, "ready");
	},
	() => {
		reconnect(socketUrl, successCallback);
	});
}

function reconnect(socketUrl, successCallback) {
    let connected = false;
    let reconInv = setInterval(() => {
        socket = new SockJS(socketUrl);
        stompClient = Stomp.over(socket);
        stompClient.connect({}, (frame) => {
            clearInterval(reconInv);
            connected = true;
            successCallback();
        }, () => {
            if (connected) {
                reconnect(socketUrl, successCallback);
            }
        });
    }, 1000);
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendCommand(command) {
	stompClient.send("/app/command", {}, command);
}

function showImage(image) {
	$('#WebcamFeed').attr('src', `data:image/png;base64,${image}`);
	console.log("recieved image");
	stompClient.send("/app/webcam", {}, "ready");
}

$(function () {
	/*$("form").on('submit', function (e) {
		e.preventDefault();
	});*/
	connect();
    $( "#forward" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        sendCommand("forward");
    });
    $( "#forward" ).on('touchend mouseup', function() {
        sendCommand("stop");
    });

    $( "#left" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        sendCommand("left");
    });
    $( "#left" ).on('touchend mouseup', function() {
        sendCommand("stop");
    });

    $( "#stop" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        sendCommand("stop");
    });

    $( "#right" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        sendCommand("right");
    });
    $( "#right" ).on('touchend mouseup', function() {
        sendCommand("stop");
    });

    $( "#back" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        sendCommand("back");
    });
    $( "#back" ).on('touchend mouseup', function() {
        sendCommand("stop");
    });
});