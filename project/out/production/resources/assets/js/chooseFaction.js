"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
    eb.onopen = function () {
        eb.registerHandler("tetris.game.homescreen", function (error, message) {
            if (error) {
                console.log(error);
            }
            console.log(message.body);
        })
    };
}

function chooseFaction(e) {
    e.preventDefault();
    faction = e.target.alt;
    sendFactionToServer();
}

function sendFactionToServer() {
    eb.send("tetris.game.faction.choose", faction, function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    })
}
