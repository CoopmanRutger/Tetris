"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        getFactionFromDB();
    };
}

function getFactionFromDB() {
    eb.registerHandler("tetris.game.faction.get", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message);
    });
    eb.send("tetris.game.faction.get",  "faction",function (error, reply) {
        if (error) {
            console.log(error);
        }
        faction = reply.body;
        console.log(faction);
    });
}