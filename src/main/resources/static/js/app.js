var stompClient = null;

var socketUrl = '/donkeybug_websocket';

var command = "stop";

var feedReady = true;

function connect() {
	var socket = new SockJS(socketUrl);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		console.log('Connected: ' + frame);
		var commandTimer = setTimeout(function run() {
            stompClient.send("/app/command", {}, command);
            setTimeout(run, 100);
        }, 100);
        var webcamTimer = setInterval(function run() {
            if (feedReady) {
                feedReady = false;
                getImage();
            }
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

$(function () {

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