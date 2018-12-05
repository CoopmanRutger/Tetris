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
    let username = "Rutger";
    //louis of Rutger --> capslock

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
    sessionStorage.setItem('HeroNr', player.heroNr);
    sessionStorage.setItem('heroLvl', player.heroLvl);
    sessionStorage.setItem('heroExp', player.heroExp);

    // sessionStorage.removeItem('username')
    printPlayerName();
}


function printPlayerName(){
    console.log(sessionStorage.getItem('PlayerName'));
    let playerName = sessionStorage.getItem('PlayerName');
    document.querySelector("main header p").innerHTML = playerName;
    document.querySelector("main header p").style.color = "white";
}