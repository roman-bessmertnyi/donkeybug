var stompClient = null;

var socketUrl = '/donkeybug_websocket';

var command = "stop";

var feedReady = true;

var ctx;
var ctxReady = false;

function connect() {
	var socket = new SockJS(socketUrl);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/odometry', function (odometry) {
            showOdometry(JSON.parse(odometry.body));
        });
		var commandTimer = setTimeout(function run() {
            stompClient.send("/app/command", {}, command);
            setTimeout(run, 100);
        }, 100);
        var webcamTimer = setTimeout(function run() {
            if (feedReady) {
                feedReady = false;
                getImage();
            }
            setTimeout(run, 33);
        }, 33);
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

function getImage() {
    var oReq = new XMLHttpRequest();
    oReq.open("GET", "/webcam", true);
    oReq.responseType = "arraybuffer";

    oReq.onload = function(oEvent) {
        var blob = new Blob([oReq.response], {type: "image/jpeg"});

        var urlCreator = window.URL || window.webkitURL;
        var imageUrl = urlCreator.createObjectURL(blob);
        document.querySelector("#WebcamFeed").src = imageUrl;
        urlCreator.revokeObjectURL(blob);
        feedReady = true;
    };
    oReq.send();
}

function showOdometry(odometry) {

    if (ctxReady) {
        ctx.fillStyle = "#000000";
        var x = (odometry.x*30)|0;
        var y = (odometry.z*30)|0;
        ctx.fillRect(x+99, y+99, 2, 2);
    }
    //console.log(odometry);
	$('#odometry-x').text(odometry.x);
	$('#odometry-y').text(odometry.y);
	$('#odometry-z').text(odometry.z);
}

$(function () {
	/*$("form").on('submit', function (e) {
		e.preventDefault();
	});*/

	var canvas = document.getElementById("pathCanvas");
    ctx = canvas.getContext("2d");
    ctxReady = true

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