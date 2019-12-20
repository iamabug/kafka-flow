var ws
var timer
function consume() {
    $('#start').hide()
    $('#stop').show()
    $('#messages').val('')
    $('#messages')
    ws = new WebSocket("ws://" + window.location.host + "/ws/kafka")
    msg = {
        "type": "CMD_START_CONSUME",
        "data": {
            "cluster": $('#cluster').find(":selected").text(),
            "topic": $('#topic').val(),
            "offset": $("input[name='offset']:checked").val()
        }
    }

    ws.onopen = function(e) {
        ws.send(JSON.stringify(msg))
        heartbeat()
    }

    function heartbeat() {
        ws.send(JSON.stringify({"type": "PING"}))
        timer = setTimeout(heartbeat, 1000)
    }

    ws.onmessage = function(e) {
        try {
            data = JSON.parse(e.data)
        } catch(e) {
            return
        }
        switch (data.type) {
            case "MESSAGES_CONSUMED":
            messages = data.data.messages
            for (var i=0; i<data.data.total; i++){
                var m = messages[i]
                $('#messages').val($('#messages').val() + m.value + '\n')
            }
            $('#messages').scrollTop($('#messages')[0].scrollHeight)

        }

    }
}

function stop() {
    $('#start').show()
    $('#stop').hide()
    ws.send(JSON.stringify({"type": "CMD_STOP_CONSUME"}))
    clearTimeout(timer)
    ws.close()
}

function get_topics() {
    //ws=new WebSocket("ws://localhost:12345/ws/kafka/dummy")
   // ws.send(JSON.stringify({"type": "CMD_LIST_TOPICS"}))
}

$(document).ready(function() {
    $("#stop").hide();
});