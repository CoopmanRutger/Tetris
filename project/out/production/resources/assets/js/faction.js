"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        getFactionFromDB("fun");
    };
}

function getFactionFromDB(lol) {
    eb.registerHandler("tetris.game.faction", function (error, message) {
        if (error) {
            console.log(error)
        }
        console.log(message);
        console.log("manuele handler:", message.body);
    });
    eb.send("tetris.game.faction", lol, function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body);
    });
    eb.registerHandler("tetris.game.test", function (error, message) {
        console.log(message.body);
        faction = message.body;
    });

    // TODO: get faction from database.
}