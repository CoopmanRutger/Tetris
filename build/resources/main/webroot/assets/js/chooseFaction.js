"use strict";

/* global EventBus */
//let eb = new EventBus("http://localhost:8021/tetris-21/socket");
//let eb = new EventBus("http://172.31.27.98:8021/tetris-21/socket");
let eb = new EventBus("http://172.21.22.52:48200/tetris-21/socket");


document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
    };
}

function getUserId() {
    return sessionStorage.getItem('UserId');
}

function getFactionId(factionName) {

    if (factionName === "dark green") {
        return 1;
    } else if (factionName === "dark red") {
        return 2;
    } else if (factionName === "dark blue") {
        return 3;
    } else if (factionName === "dark yellow") {
        return 4;
    }
}

function chooseFaction(e) {
    e.preventDefault();

    let faction = e.target.alt;
    console.log(faction);
    sessionStorage.setItem('FactionName', faction);
    sendFactionToServer(getFactionId(faction), getUserId());

    if (sessionStorage.getItem('FactionName') !== "dark") {
        window.location.href = "faction_clan.html";
    }
}

function sendFactionToServer(faction, user) {
    let object = {factionId: faction, userId: user};
    eb.send("tetris-21.socket.faction.choose", JSON.stringify(object), function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });
}
