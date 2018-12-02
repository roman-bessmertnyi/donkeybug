var stompClient = null;

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
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/webcam', function (webcamDTO) {
            showImage(JSON.parse(webcamDTO.body).image);
        });
    });
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
}

$(function () {
    /*$("form").on('submit', function (e) {
        e.preventDefault();
    });*/
    connect();
    $( "#forward" )
        .mousedown(function() { sendCommand("forward"); })
        .mouseup(function() { sendCommand("stop"); });
    $( "#left" )
        .mousedown(function() { sendCommand("left"); })
        .mouseup(function() { sendCommand("stop"); });
    $( "#stop" ).click(function() { sendCommand("stop"); });
    $( "#right" )
        .mousedown(function() { sendCommand("right"); })
        .mouseup(function() { sendCommand("stop"); });
    $( "#back" )
        .mousedown(function() { sendCommand("back"); })
        .mouseup(function() { sendCommand("stop"); });
});