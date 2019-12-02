var ws
var timer
function connect() {
    $('#start').prop("disabled", true)
    $('#stop').show()
    $('#messages').val('')
    $('#messages')
    ws = new WebSocket("ws://localhost:12345/ws/kafka/dummy")
    msg = {
        "type": "KAFKA_CONSUME",
        "data": {
            "bootstrap-server": $('#brokers').val(),
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
        data = JSON.parse(e.data).data
        messages = data.messages
        for (var i=0; i<data.total; i++){
            var m = messages[i]
            $('#messages').val($('#messages').val() + m.value + '\n')
        }
        $('#messages').scrollTop($('#messages')[0].scrollHeight) 
    }
}

function reset() {
    $('#start').prop("disabled", false)
    $('#stop').hide()
    ws.send(JSON.stringify({"type": "STOP_CONSUME"}))
    clearTimeout(timer)
}

function get_topics() {
    
}