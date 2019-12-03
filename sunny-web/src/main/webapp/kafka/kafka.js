var ws
var timer
function connect() {
    $('#start').prop("disabled", true)
    $('#stop').show()
    $('#messages').val('')
    $('#messages')
    ws = new WebSocket("ws://localhost:12345/ws/kafka/dummy")
    msg = {
        "type": "CMD_START_CONSUME",
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
            case "RESULT_LIST_ROPICS":
            
        }
        
    }
}

function reset() {
    $('#start').prop("disabled", false)
    $('#stop').hide()
    ws.send(JSON.stringify({"type": "CMD_STOP_CONSUME"}))
    clearTimeout(timer)
}

function get_topics() {
    //ws=new WebSocket("ws://localhost:12345/ws/kafka/dummy")
   // ws.send(JSON.stringify({"type": "CMD_LIST_TOPICS"}))
}