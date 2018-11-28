"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        getFactionFromDB();
    };
}

function getFactionFromDB() {


    // eb.send("tetris.infobackend.faction.info", "rutger", function (error, reply) {
    //     if (error) {
    //         console.log(error)
    //     }
    //     console.log(reply.body);
    // });

    // eb.registerHandler("tetris.infoBackend.faction.get", function (error, message) {
    //     if (error) {
    //         console.log(error);
    //     }
    //     console.log(message);
    // });
}