"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

function init() {
    playername();
    eb.onopen = function () {
        getGoldFromDB();
    };
}

function playername() {
    select('header h4 span').innerHTML = sessionStorage.getItem('Username');
}



function getGoldFromDB() {
    let UserId = parseInt(sessionStorage.getItem('UserId'));

    eb.send("tetris-21.socket.gold", UserId , function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body)
    });

    eb.registerHandler("tetris-21.socket.gold.get", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body.gold[0]);

        select("header p span").innerHTML = message.body.gold[0];

    });
}

