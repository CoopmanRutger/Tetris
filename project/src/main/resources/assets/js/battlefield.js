"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");
let game = null;
let gameRun = false;
let gameRun2 = false;
let gameLoop;

const area = makeMatrix(12, 20);
const area2 = makeMatrix(12, 20);
const context = player1.getContext("2d");
const context2 = player2.getContext("2d");

let player = {
    pos: {
        x: 0,
        y: 0
    },
    matrix: null,
    score: 0
};
let fieldPlayer2 = {
    pos: {
        x: 0,
        y: 0
    },
    matrix: null,
    score: 0
};

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

    eb.onopen = function () {
        initialize("momom");
    };
    backgroundStuff();
    startGame();

}


function initialize(lol) {

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

function backgroundStuff() {
    const player1 = document.getElementById("player1");
    const player2 = document.getElementById("player2");
    context.scale(20, 20);
    context2.scale(20, 20);

    update(player, context, area);
    update(fieldPlayer2, context2, area2);

    playerReset();
    draw(player, context, area);
    draw(fieldPlayer2, context2, area);

    const move = 1;
    document.addEventListener('keydown', function (e) {
        if (e.keyCode === 37 ) {
            playerMove(player, -move, area);
        }
        else if (e.keyCode === 81) {
            playerMove(fieldPlayer2, -move, area2);
        }
        else if (e.keyCode === 39) {
            playerMove(player, +move, area);
        }
        else if (e.keyCode === 68) {
            playerMove(fieldPlayer2, +move, area2);
        }
        else if (e.keyCode === 40) {
                playerDrop(player,context ,area);
        }
        else if (e.keyCode === 83) {
                playerDrop(fieldPlayer2,context2, area2);
        }
        else if (e.keyCode === 38) {
            playerRotate(player, -move, area);
        }
        else if (e.keyCode === 90) {
            playerRotate(fieldPlayer2, -move, area2);
        }
    });
}

function startGame() {
    gameRun = true;
    gameRun2 = true;

    playerReset();
    gameLoop = setInterval(function () {
        console.log(gameRun, gameRun2);
        if (gameRun && gameRun2) {
            update(player, context, area);
            update(fieldPlayer2, context2, area2)
        }
        else if (!gameRun){
            console.log("gamerun :" + gameRun );
            gameOver(context2);
            youWon(context);
        }
        else if (!gameRun2){
            console.log("gamerun2 :" + gameRun );
            gameOver(context);
            youWon(context2);
        }
    }, 10);

}

function update(player, context, area) {
    let time = 0;
    let dropInter = 100;
    time++;

    if (time >= dropInter) {
        playerDrop(player,context, area);
        time = 0;
    }
    draw(player, context, area);

}

function makeMatrix(width, height) {
    const matrix = [];
    while (height--) {
        matrix.push(new Array(width).fill(0));
    }
    return matrix;
}

function points(player, area) {
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

function collide(player, area) {
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


function merge(player, area) {
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

function playerReset() {
    makePieces(player, area);
    makePieces(fieldPlayer2, area2);
}


function makePieces(player, area) {
    let pieces = "ijlostzb";
    player.matrix = makePiece(pieces[Math.floor(Math.random() * pieces.length)]);
    player.pos.y = 0;
    player.pos.x = (Math.floor(area[0].length / 2)) - (Math.floor(player.matrix[0].length / 2));
    collidefunction(player,area);
    if (collide(player, area)) {
        area.forEach(row => row.fill(0));
        player.score = 0;
        console.log(player);
        if (player === ""){
            gameRun = false;
        } else {
            gameRun2 = false;
        }
    }

}
function collidefunction(player, area) {

}

function playerDrop(player, context, area) {
    player.pos.y++;
    if (collide(player, area)) {
        player.pos.y--;
        merge(player, area);
        points(player, area);
        playerReset();
        updateScore(player, context);
    }
}

function playerMove(player,dir, area) {
    player.pos.x += dir;
    if (collide(player, area)) {
        player.pos.x -= dir;
    }
}


function playerRotate(player, dir, area) {
    const pos = player.pos.x;
    let offset = 1;
    rotate(player.matrix, dir);
    while (collide(player, area)) {
        player.pos.x += offset;
        offset = -(offset + (offset > 0 ? 1 : -1));
        if (offset > player.matrix[0].length) {
            rotate(player.matrix, -dir);
            player.pos.x = pos;
            return;
        }
    }
}

function draw(player, context, area) {
    context.clearRect(0, 0, player1.width, player1.height);
    context.fillStyle = "#000000";
    context.fillRect(0, 0, player2.width, player2.height);

    updateScore(player, context);
    drawMatrix(area, {x: 0, y: 0}, context);
    if (player.matrix != null){
        drawMatrix(player.matrix, player.pos, context);
    }
}

function updateScore(player, context) {
    context.font = "bold 1px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "left";
    context.textBaseline = "top";
    context.fillText("Score:" + player.score, 0.2, 0);
}


function gameOver(context) {
    clearInterval(gameLoop);
    context.font = "2px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "center";
    context.textBaseline = "middle";
    context.fillText("Game Over", (player1.width / 20) / 2, (player1.width / 20) / 2);
}
function youWon(context) {
    context.font = "2px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "center";
    context.textBaseline = "middle";
    context.fillText("YOU WON!", (player2.width / 20) / 2, (player2.width / 20) / 2);
}

function drawMatrix(matrix, offset, context) {
    matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value !== 0) {
                // context.fillStyle=colors[value];
                // context.fillRect(x+offset.x,y+offset.y,1,1);
                let imgTag = document.createElement("IMG");
                imgTag.src = colors[value];
                context.drawImage(imgTag, x + offset.x, y + offset.y, 1, 1);
            }
        });
    });
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


// ---------------------------- afteller----------------------------------
// var timer = new Timer();
// timer.start({precision: 'seconds', startValues: {seconds: 90}, target: {seconds: 120}});
// $('#startValuesAndTargetExample .values').html(timer.getTimeValues().toString());
// timer.addEventListener('secondsUpdated', function (e) {
//     $('#startValuesAndTargetExample .values').html(timer.getTimeValues().toString());
//     $('#startValuesAndTargetExample .progress_bar').html($('#startValuesAndTargetExample .progress_bar').html() + '.');
// });
// timer.addEventListener('targetAchieved', function (e) {
//     $('#startValuesAndTargetExample .progress_bar').html('COMPLETE!!');
// });
//
//
// HTML
// <div id="startValuesAndTargetExample">
//     <div class="values"></div>
//     <div class="progress_bar">.</div>
// </div>