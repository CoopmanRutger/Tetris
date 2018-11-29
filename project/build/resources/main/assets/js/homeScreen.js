"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);


function init() {
    console.log("home screen");
    eb.onopen = function () {
        f();
    };

}

function f() {
    let username = document.querySelector("main header p span").innerHTML;

    eb.send("tetris-21.socket.homescreen", username , function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body);
    });

    eb.registerHandler("tetris-21.socket.homescreen.playerinfo", function (error, message) {
        if (error) {
            console.log(error);
        }
        let playerInfo = JSON.parse(message.body);

        console.log(playerInfo);
    });


}