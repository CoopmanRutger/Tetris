"use strict";
/* global EventBus */

document.addEventListener("DOMContentLoaded", init);

let eb = new EventBus("http://localhost:8080/tetris/game");


function init() {
    console.log("home screen");
    f;

}


function f() {
    let sendMessage = function (val) {
        eb.send("tetris.game.homeScreen", {user : document.getElementById("play").value, content : val},
            function(err, reply) {
                console.log("message broadcasted: " + JSON.stringify(reply));

            });
    };
    eb.onopen = function() {
        eb.registerHandler("tetris.game.homeScreen", function (error, message) {
            if (error) {
                console.log("error: " + JSON.stringify(error));
            };
            console.log("message: " + JSON.stringify(message.body));
            let newMessage = document.createElement("li");
            newMessage.innerHTML = JSON.stringify(message.body);
            messagelist.prepend(newMessage);
        });
    };

}