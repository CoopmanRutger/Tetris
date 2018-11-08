"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let game = null;
let gameRun = false;
let gameLoop;

const area = makeMatrix(12, 20);
const player = {
    pos: {
        x: 0,
        y: 0
    },
    matrix: null,
    score: 0
};
const move = 1;
const colors = [
    null,
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Jz34T+Ifv3kGIOojBWYBgFkNlgADfTUeTEygjQLC3Iz3Li8G5sanGIauq5gPYwlTdvAtpMLwC6AORlkKjEA2bUYBvDxsYDN+PTpDwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Bb19cYRCW0EGNRrLCgBQXvH3/lQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQAfwJNr7nKi7AAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgKD1y/D+Ifn7sCIOklQ2YBgFkNlgADSwpK2VkBGkW4udjuLp9GzY1OMW0Pb3AehhjurrBtpMLwC6AORlkKjEA2bUYBvBzcYPN+PjtKwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Br69fYxDV1EKNRrLCgBQXvPv4iQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQBReJAxJHOTqwAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Ly04z+IfnXhJoOYgTqYBgFkNlgADfTG5TMygjQL8/EzXD90CpsanGKadmZgPYzFiyaCbScXgF0AczLIVGIAsmsxDODj4gGb8enbFwYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwD3tx+yCCiKo8ajWSFASkuePvpIwN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBvl5MXVeEacQAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg+D8l5T+I3vf0G4OTNBeYBgFkNlgADTi3L2NkBGsWFmfYd+k+NjU4xZz0FMF6GPdWRoFtJxeAXQB3sp4iUeYguxbTAH5hiCEf3zIwgNggGg2gGIDsBZC/cGlCNoOwC7DYitMADBcQEQr4vUCkARdefmUwEOdGjUaywoAkL7x9yYCe6FASEtgF6ACqiQFEgwA01eL2AiVhADKVGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZfmAthLgBlZwBvBonjT09XegAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg2Fvy6T+IvvnlGIM6jxWYBgFkNlgADWTN8GBkBGnmE+FiOP1gDzY1OMVMFVzAehinZewA204uALsA5mSQqcQAZNdiGMAtwAw24+uHvwwgNohGBygGIHsB5AJcmpANIegCbLbiNADdBSSHAbkGPPh0hUGBTwc1GskKA1Jc8OnNNwb0RIeSkLClA5gmEA0CMANweoHYQMRqAEiQGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZcmAthLgBlZwBQ3ZP3OaGtaAAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgeJXf8h9EH/n4ksGGXxxMgwAyGyyABoIWTGZkBGnmExdj2HbrMjY1OMW81HTBehjXJeSCbScXgF0AczLIVGIAsmsxDGAXEQSb8fPNewYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwDrnx8w6DDL4IajWSFASku+PTyFQN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBUFZC9raUyoQAAAABJRU5ErkJggg==",
    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAw0lEQVQ4T2NkYGBg+Hln0n8QfeLSGwYLPREwDQLIbLAAGrAPamJkBGlm4+RjOHTyHjY1OMXszJXAehgPrqsD204uALsA5mSQqcQAZNdiGsDOAzHj5xcGBhAbRKMBFAOQvQB2AQ5NyGYQdgEWW3EagOECIgIBvxeINODK7bcMOqrCqNFIVhiQ4oVf3z8xoCc6lISELR3ANIFoEIAZgNsLlIQByFRiACjwYADsBVjGIEYzTA08M4EECGUomH9hLoS5AJSdASaukfnTt+kFAAAAAElFTkSuQmCC"
];

document.addEventListener("DOMContentLoaded", init);

function init() {

    eb.onopen = function(){
        initialize("momom");
    };
    tetris(player);
}


function initialize(lol ) {

    eb.registerHandler("tetris.game.BattleField", function (error, message) {
        if (error) {
            console.log(error)
        }
        console.log("manuele handler:", message.body);
    });
    eb.send("tetris.game.BattleField", lol, function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body);
    });
    eb.registerHandler("tetris.game.test", function (error, message) {
        console.log(message.body);
        game = message.body;
    });
}

function tetris(player) {
    const player1 = document.getElementById("player1");
    const player2 = document.getElementById("player2");
    const context = player1.getContext("2d");
    const context2 = player2.getContext("2d");
    context.scale(20, 20);
    context2.scale(20, 20);

    let dropInter = 100;
    let time = 0;
    let update = function () {
        time++;
        if (time >= dropInter) {
            playerDrop();
            time = 0;
        }
        draw(context,context2);
    };
    gameRun = false;
    playerReset(player);
    draw(context, context2);
    gameOver(context, context2);
    document.addEventListener('keydown', function (e) {
        if (e.keyCode === 37) {
            playerMove(-move);
        }
        else if (e.keyCode === 39) {
            playerMove(+move);
        }
        else if (e.keyCode === 40) {
            // console.log(player.pos);
            if (gameRun) {
                playerDrop();
            }
        }
        else if (e.keyCode === 38) {
            playerRotate(-move);
        }
    });
    addEventHandler("#start_game","click",startGame );


}


function makeMatrix(width, height){
    const matrix = [];
    while (height--) {
        matrix.push(new Array(width).fill(0));
    }
    return matrix;
}

function makePiece(type) {
    if (type === "t") {
        return [
            [0, 0, 0],
            [5, 5, 5],
            [0, 5, 0]
        ];
    }
    else if (type === "o") {
        return [
            [7, 7],
            [7, 7]
        ];
    }
    else if (type === "l") {
        return [
            [0, 4, 0],
            [0, 4, 0],
            [0, 4, 4]
        ];
    }
    else if (type === "j") {
        return [
            [0, 1, 0],
            [0, 1, 0],
            [1, 1, 0]
        ];
    }
    else if (type === "i") {
        return [
            [0, 2, 0, 0],
            [0, 2, 0, 0],
            [0, 2, 0, 0],
            [0, 2, 0, 0]
        ];
    }
    else if (type === "s") {
        return [
            [0, 3, 3],
            [3, 3, 0],
            [0, 0, 0]
        ];
    }
    else if (type === "z") {
        return [
            [6, 6, 0],
            [0, 6, 6],
            [0, 0, 0]
        ];
    } else if (type === "b") {
        return [
            [7, 0, 0, 0],
            [0, 7, 0, 0],
            [0, 7, 0, 0],
            [7, 0, 0, 0]
        ];
    }

}

function points() {
    let rowCount = 1;
    outer:for (let y = area.length - 1; y > 0; --y) {
        for (let x = 0; x < area[y].length; ++x) {
            if (area[y][x] === 0) {
                continue outer;
            }
        }
        const row = area.splice(y, 1)[0].fill(0);
        area.unshift(row);
        ++y;
        player.score += rowCount * 100;
        rowCount *= 2;
    }
}

function collide(area, player) {
    const [m, o] = [player.matrix, player.pos];
    for (let y = 0; y < m.length; ++y) {
        for (let x = 0; x < m[y].length; ++x) {
            if (m[y][x] !== 0 && (area[y + o.y] && area[y + o.y][x + o.x]) !== 0) {
                return true;
            }
        }
    }
    return false;
}

function drawMatrix(matrix, offset, context, context2) {
    matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value !== 0) {
                // context.fillStyle=colors[value];
                // context.fillRect(x+offset.x,y+offset.y,1,1);
                let imgTag = document.createElement("IMG");
                imgTag.src = colors[value];
                context.drawImage(imgTag, x + offset.x, y + offset.y, 1, 1);
                context2.drawImage(imgTag, x + offset.x, y + offset.y, 1, 1);
            }
        });
    });
}

function merge(area, player) {
    player.matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value !== 0) {
                area[y + player.pos.y][x + player.pos.x] = value;
            }
        });
    });
}

function rotate(matrix, dir) {
    for (let y = 0; y < matrix.length; ++y) {
        for (let x = 0; x < y; ++x) {
            [
                matrix[x][y],
                matrix[y][x]
            ] = [
                matrix[y][x],
                matrix[x][y],
            ]
        }
    }
    if (dir > 0) {
        matrix.forEach(row => row.reverse());
    }
    else {
        matrix.reverse();
    }
}

function playerReset(player) {
    console.log(player);
    const pieces = "ijlostzb";
    player.matrix = makePiece(pieces[Math.floor(Math.random() * pieces.length)]);
    console.log(player.matrix);
    player.pos.y = 0;
    player.pos.x = (Math.floor(area[0].length / 2)) - (Math.floor(player.matrix[0].length / 2));
    if (collide(area, player)) {
        area.forEach(row => row.fill(0));
        player.score = 0;
        gameRun = false;
    }
}

function playerDrop(player, context, context2) {
    player.pos.y++;
    if (collide(area, player)) {
        player.pos.y--;
        merge(area, player);
        points();
        playerReset(player);
        updateScore(context, context2);
    }
}

function playerMove(dir) {
    player.pos.x += dir;
    if (collide(area, player)) {
        player.pos.x -= dir;
    }
}

function playerRotate(dir) {
    const pos = player.pos.x;
    let offset = 1;
    rotate(player.matrix, dir);
    while (collide(area, player)) {
        player.pos.x += offset;
        offset = -(offset + (offset > 0 ? 1 : -1));
        if (offset > player.matrix[0].length) {
            rotate(player.matrix, -dir);
            player.pos.x = pos;
            return;
        }
    }
}

function draw(context,context2) {
    context.clearRect(0, 0, player1.width, player1.height);
    context.fillStyle = "#000000";
    context.fillRect(0, 0, player2.width, player2.height);
    context2.clearRect(0, 0, player2.width, player2.height);
    context2.fillStyle = "#000000";
    context2.fillRect(0, 0, player2.width, player2.height);
    updateScore(context, context2);
    drawMatrix(area, {x: 0, y: 0}, context, context2);
    drawMatrix(player.matrix, player.pos, context, context2);
}

function updateScore(context, context2) {
    context.font = "bold 1px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "left";
    context.textBaseline = "top";
    context.fillText("Score:" + player.score, 0.2, 0);
    context2.font = "bold 1px Comic Sans MS";
    context2.fillStyle = "#ffffff";
    context2.textAlign = "left";
    context2.textBaseline = "top";
    context2.fillText("Score:" + player.score, 0.2, 0);
}

function gameOver(context, context2) {
    clearInterval(gameLoop);
    context.font = "2px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "center";
    context.textBaseline = "middle";
    context2.font = "2px Comic Sans MS";
    context2.fillStyle = "#ffffff";
    context2.textAlign = "center";
    context2.textBaseline = "middle";
    context.fillText("Game Over", (player1.width / 20) / 2, (player1.width / 20) / 2);
    context2.fillText("Game Over", (player2.width / 20) / 2, (player2.width / 20) / 2);
    document.getElementById("start_game").disabled = false;
}




function startGame (player) {
    gameRun = true;
    playerReset(player);
    // console.log(player.pos);
    gameLoop = setInterval(function () {
        if (gameRun) {
            update();
        }
        else {
            gameOver(context, context2);
        }
    }, 10);
    this.disabled = true;
}