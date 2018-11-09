"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        console.log("entered1");
        getFactionFromDB("fun");
    };
}

function getFactionFromDB(lol) {
    console.log("entered2");
    eb.registerHandler("tetris.game.faction", function (error, message) {
        console.log("entered3");
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
        console.log("entered4");
        console.log(message.body);
        faction = message.body;
    });

    // TODO: get faction from database.
}