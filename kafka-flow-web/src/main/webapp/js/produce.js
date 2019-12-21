var ws
var timer
function produce() {
    if ($('#topic').val() == '') {
        alert('topic 不可为空！')
        return
    }
    if ($('#messages').val() == '') {
        alert('输入消息不可为空！')
        return
    }
    ws = new WebSocket("ws://" + window.location.host + "/ws/kafka")
    msg = {
        "type": "CMD_PRODUCE",
        "data": {
            "cluster": $('#cluster').find(":selected").text(),
            "topic": $('#topic').val(),
            "messages": $('#messages').val()
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
            case "PRODUCE_DONE":
            clearTimeout(timer)

        }

    }
}

function clear() {
    alert("clear")
    $('#messages').val('')
}