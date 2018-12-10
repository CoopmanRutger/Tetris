"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);


function init() {
    eb.onopen = function () {
        f();
    };

}

function f() {
    let username = JSON.parse(sessionStorage.getItem('login')).username;
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
        SetPlayerInSession(playerInfo)
    });
}

function SetPlayerInSession(player) {
    console.log(player);
    sessionStorage.setItem('Username', player.username);
    sessionStorage.setItem('PlayerId', player.playerId);
    sessionStorage.setItem('PlayerName', player.playerName);
    sessionStorage.setItem('UserId', player.userId);
    printPlayerName();
}

function printPlayerName(){
    document.querySelector("main header p").innerHTML = 'Welkom ' + sessionStorage.getItem('Username');
    document.querySelector("main header p").style.color = "white";
}