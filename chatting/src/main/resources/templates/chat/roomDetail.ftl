<!doctype html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-6">
            <h4>{{room.name}}</h4>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-info btn-sm" href="/chat/room" @click="sendMessage('EXIT', '')">채팅방 나가기</a>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage('TALK', message)">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage('TALK', message)">보내기</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
            {{message.sender}} - {{message.message}}</a>
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    // websocket & stomp initialize
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            sender: '',
            message: '',
            messages: []
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.sender = localStorage.getItem('wschat.sender');
            this.findRoom();
            this.connect();
        },
        methods: {
            findRoom: function() {
                axios.get('/chat/room/'+this.roomId).then(response => { this.room = response.data; });
            },
            sendMessage: function(type, message) {
                ws.send("/pub/chat/message", {}, JSON.stringify({type:type, roomId:this.roomId, sender:this.sender, message:message}));
                this.message = '';
            },
            recvMessage: function(recv) {
                if (recv.type=='ENTER') {
                    this.messages.unshift({"type":recv.type,"sender":'[입장]',"message":recv.message});
                } else if (recv.type=='EXIT') {
                    this.messages.unshift({"type":recv.type,"sender":'[퇴장]',"message":recv.message});
                } else {
                    this.messages.unshift({"type":recv.type,"sender":recv.sender,"message":recv.message});
                }
            },
            connect: function () {
                ws.connect({}, function(frame) {
                    ws.subscribe("/sub/chat/room/"+vm.$data.roomId, function(message) {
                        var recv = JSON.parse(message.body);
                        vm.recvMessage(recv);
                    });
                    ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
                }, function(error) {
                    alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
                    location.href="/chat/room";
                });
            }
        }
    });
</script>
</body>
</html>