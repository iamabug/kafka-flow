function connect() {
    var ws = new WebSocket("ws://localhost:12345/ws/kafka/dummy")
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
    }
    ws.onmessage = function(e) {
       // alert(e.data)
       $('#messages').val(e.data)
        //document.getElementById("messages").value = e.data
    }
}