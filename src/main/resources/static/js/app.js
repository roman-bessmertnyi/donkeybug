var stompClient = null;

var socketUrl = '/donkeybug_websocket';

var command = "stop";

function connect() {
	var socket = new SockJS(socketUrl);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/webcam', function (webcamDTO) {
			showImage(JSON.parse(webcamDTO.body).image);
		});
		stompClient.subscribe('/topic/odometry', function (odometry) {
            showOdometry(JSON.parse(odometry.body));
        });
		stompClient.send("/app/webcam", {}, "ready");
		var commandTimer = setTimeout(function run() {
            stompClient.send("/app/command", {}, command);
            setTimeout(run, 100);
        }, 100);
	});
}

function reconnect(socketUrl) {
    let reconInv = setInterval(() => {
        connect();
        clearInterval(reconInv);
    }, 100);
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	//setConnected(false);
	console.log("Disconnected");
	reconnect(socketUrl);
}

/*function sendCommand() {
    if (command != 'stop') {
        stompClient.send("/app/command", {}, command);
    }
}*/

function showOdometry(odometry) {
    //console.log(odometry);
	$('#odometry-x').text(odometry.x);
	$('#odometry-y').text(odometry.y);
	$('#odometry-z').text(odometry.z);
}

function showImage(image) {
    stompClient.send("/app/webcam", {}, "ready");
	$('#WebcamFeed').attr('src', `data:image/jpeg;base64,${image}`);
}

$(function () {
	/*$("form").on('submit', function (e) {
		e.preventDefault();
	});*/
	connect();
    $( "#forward" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        //sendCommand("forward");
        command = "forward";
    });
    $( "#forward" ).on('touchend mouseup', function() {
        //sendCommand("stop");
        command = "stop";
    });

    $( "#left" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        //sendCommand("left");
        command = "left";
    });
    $( "#left" ).on('touchend mouseup', function() {
        //sendCommand("stop");
        command = "stop";
    });

    $( "#stop" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        //sendCommand("stop");
        command = "stop";
    });

    $( "#right" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        //sendCommand("right");
        command = "right";
    });
    $( "#right" ).on('touchend mouseup', function() {
        //sendCommand("stop");
        command = "stop";
    });

    $( "#back" ).on('touchstart mousedown', function(e) {
        e.preventDefault();
        //sendCommand("back");
        command = "back";
    });
    $( "#back" ).on('touchend mouseup', function() {
        //sendCommand("stop");
        command = "stop";
    });
});