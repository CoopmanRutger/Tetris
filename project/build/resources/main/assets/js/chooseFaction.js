"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
    };
}

function chooseFaction(e) {
    e.preventDefault();
    faction = e.target.alt;
    sendFactionToServer();
}

function sendFactionToServer() {
    eb.send("tetris-21.socket.faction.choose", faction, function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });
}
