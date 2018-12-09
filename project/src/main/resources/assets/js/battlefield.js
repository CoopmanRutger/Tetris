"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");
// let eb = new EventBus("http://172.31.27.58:8080/tetris/infoBackend");
let game = {
    gameRun: false, gameRun2: false, gameLoop: null, countdown: null, timer: 180, speed: 50,
    area: makeMatrix(12, 20),
    area2: makeMatrix(12, 20),
    context: player1.getContext("2d"),
    context2: player2.getContext("2d"),
    fieldPlayer: {name: null, pos: {x: 0, y: 0}, matrix: null, score: 0},
    fieldPlayer2: {name: null, pos: {x: 0, y: 0}, matrix: null, score: 0},
    colors: [
        null,
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Jz34T+Ifv3kGIOojBWYBgFkNlgADfTUeTEygjQLC3Iz3Li8G5sanGIauq5gPYwlTdvAtpMLwC6AORlkKjEA2bUYBvDxsYDN+PTpDwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Bb19cYRCW0EGNRrLCgBQXvH3/lQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQAfwJNr7nKi7AAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgKD1y/D+Ifn7sCIOklQ2YBgFkNlgADSwpK2VkBGkW4udjuLp9GzY1OMW0Pb3AehhjurrBtpMLwC6AORlkKjEA2bUYBvBzcYPN+PjtKwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Br69fYxDV1EKNRrLCgBQXvPv4iQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQBReJAxJHOTqwAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Ly04z+IfnXhJoOYgTqYBgFkNlgADfTG5TMygjQL8/EzXD90CpsanGKadmZgPYzFiyaCbScXgF0AczLIVGIAsmsxDODj4gGb8enbFwYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwD3tx+yCCiKo8ajWSFASkuePvpIwN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBvl5MXVeEacQAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg+D8l5T+I3vf0G4OTNBeYBgFkNlgADTi3L2NkBGsWFmfYd+k+NjU4xZz0FMF6GPdWRoFtJxeAXQB3sp4iUeYguxbTAH5hiCEf3zIwgNggGg2gGIDsBZC/cGlCNoOwC7DYitMADBcQEQr4vUCkARdefmUwEOdGjUaywoAkL7x9yYCe6FASEtgF6ACqiQFEgwA01eL2AiVhADKVGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZfmAthLgBlZwBvBonjT09XegAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg2Fvy6T+IvvnlGIM6jxWYBgFkNlgADWTN8GBkBGnmE+FiOP1gDzY1OMVMFVzAehinZewA204uALsA5mSQqcQAZNdiGMAtwAw24+uHvwwgNohGBygGIHsB5AJcmpANIegCbLbiNADdBSSHAbkGPPh0hUGBTwc1GskKA1Jc8OnNNwb0RIeSkLClA5gmEA0CMANweoHYQMRqAEiQGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZcmAthLgBlZwBQ3ZP3OaGtaAAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgeJXf8h9EH/n4ksGGXxxMgwAyGyyABoIWTGZkBGnmExdj2HbrMjY1OMW81HTBehjXJeSCbScXgF0AczLIVGIAsmsxDGAXEQSb8fPNewYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwDrnx8w6DDL4IajWSFASku+PTyFQN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBUFZC9raUyoQAAAABJRU5ErkJggg==",
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAw0lEQVQ4T2NkYGBg+Hln0n8QfeLSGwYLPREwDQLIbLAAGrAPamJkBGlm4+RjOHTyHjY1OMXszJXAehgPrqsD204uALsA5mSQqcQAZNdiGsDOAzHj5xcGBhAbRKMBFAOQvQB2AQ5NyGYQdgEWW3EagOECIgIBvxeINODK7bcMOqrCqNFIVhiQ4oVf3z8xoCc6lISELR3ANIFoEIAZgNsLlIQByFRiACjwYADsBVjGIEYzTA08M4EECGUomH9hLoS5AJSdASaukfnTt+kFAAAAAElFTkSuQmCC"
    ]
};

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        initialize();
    };
    backgroundStuff();
    startGame();
    countdown(game.timer);
    addEventHandler("#openmodal", "click", openModal);
    addEventHandler("body", "click", onModalClose);
    addEventHandler("#keepplaying", "click", keepPlaying);
}


function setPlayer1(player) {
    console.log(player);
    console.log(player.hero);


    select('#player1name').innerHTML =  sessionStorage.getItem("PlayerName");
    console.log(select('#player1name').innerHTML);


    console.log()
}
function setPlayer2(player) {
    console.log(player)
}

function setEvents(events) {
    console.log(events);
}

function setGamePlay(infoBackend) {

    setPlayer1(infoBackend.players[0]);
    setPlayer2(infoBackend.players[1]);
    setEvents(infoBackend.events);

    // console.log(infoBackend);



    // game.fieldPlayer.name = players[0].name;
    // select('#player1name').innerHTML = players[0].name;
    // game.fieldPlayer2.name = players[1].name;
    // select('#player2name').innerHTML = players[1].name;
    // select('#scoreplayer1').innerHTML = 0;
    // select('#scoreplayer2').innerHTML = 0;
    // let linesp2 = 6;
    // let linesp1 = 2;
    // setWidth(linesp1, "#abilty1p1");
    // setWidth(linesp1, "#abilty2p1");
    // setWidth(linesp2, "#abilty1p2");
    // setWidth(linesp2, "#abilty2p2");
    // console.log(players[0].hero.abilitySet[0]);
    // select('#abilty1p1').innerHTML = players[0].hero.abilitySet[0].name + " <img src=\"../../assets/media/1.png\" "
    //     + "class='key' title='key1' alt='key1'>";
    // select('#abilty2p1').innerHTML = players[0].hero.abilitySet[1].name + " <img src=\"../../assets/media/2.png\" " +
    //     "class='key' title='key2' alt='key2'>";
    // select('#abilty1p2').innerHTML = players[1].hero.abilitySet[0].name + " <img src=\"../../assets/media/9.png\" " +
    //     "class='key' title='key9' alt='key9'>";
    // select('#abilty2p2').innerHTML = players[1].hero.abilitySet[1].name + " <img src=\"../../assets/media/0.png\" " +
    //     "class='key' title='key0' alt='key0'>";
    // select("#heroimgplayer1").innerHTML = '<img src="../../assets/media/' + players[0].hero.name + '.png">';
    // select("#heroimgplayer2").innerHTML = '<img src="../../assets/media/' + players[1].hero.name + '.png">';

    // console.log(infoBackend);

    // console.log(game.fieldPlayer2.name);

}

function setWidth(lines, id) {
    let multiply = 0 ;
    let maxVal = 0;
    if (id === "#abilty1p1" || id === "#abilty1p2") {
        multiply = 10;
        maxVal = 10
    } else if (id === "#abilty2p1" || id === "#abilty2p2") {
        multiply = 5;
        maxVal = 20;
    }
    if(0 <= lines < maxVal) {
        select(id).style.backgroundSize = lines * multiply + "%";
    } else {
        select(id).style.width = 100 + "%";
    }
}

function updateGame(updateStuff) {
    document.querySelector('#scoreplayer1').innerHTML = 0;
    document.querySelector('#scoreplayer2').innerHTML = 0;
    // todo
}

function registers() {
    eb.registerHandler("tetris-21.socket.BattleField", function (error, message) {
        if (error) {
            console.log(error)
        }
        console.log(message);

    });

    eb.send("tetris-21.socket.gamestart", "Im ready!", function (error, reply) {
        if (error) {
            console.log(error)
        }
    });
    eb.registerHandler("tetris-21.socket.game", function (error, message) {
        setGamePlay(JSON.parse(message.body));
    });

}

function initialize() {
    registers();
}

function backgroundStuff() {
    const player1 = document.getElementById("player1");
    const player2 = document.getElementById("player2");
    game.context.scale(20, 20);
    game.context2.scale(20, 20);

    draw(game.fieldPlayer, game.context, game.area);
    draw(game.fieldPlayer2, game.context2, game.area2);

    nextBlock(game.fieldPlayer.name);
    nextBlock(game.fieldPlayer2.name);
    draw(game.fieldPlayer, game.context, game.area);
    draw(game.fieldPlayer2, game.context2, game.area);
    const move = 1;


    document.addEventListener('keydown', function (e) {
        //links Q
        if (e.keyCode === 81) {
            playerMove(game.fieldPlayer, -move, game.area);
        }
        // draai Z
        else if (e.keyCode === 90) {
            playerRotate(game.fieldPlayer, -move, game.area);
        }
        // Beneden S
        else if (e.keyCode === 83) {
            playerDrop(game.fieldPlayer, game.context, game.area);
        }
        // rechts D
        else if (e.keyCode === 68) {
            playerMove(game.fieldPlayer, +move, game.area);
        }
    });

    document.addEventListener('keydown', function (e) {
        // links
        if (e.keyCode === 37) {
            playerMove(game.fieldPlayer2, -move, game.area2);
        }
        // rechts
        else if (e.keyCode === 39) {
            playerMove(game.fieldPlayer2, +move, game.area2);
        }
        // beneden
        else if (e.keyCode === 40) {
            playerDrop(game.fieldPlayer2, game.context2, game.area2);
        }
        // draaien
        else if (e.keyCode === 38) {
            playerRotate(game.fieldPlayer2, -move, game.area2);
        }

        // todo
        // eb.send("tetris.infoBackend.updateGame", "update!", function (error, reply) {
        //     if (error) {
        //         console.log(error)
        //     }
        // });
    })
}

function startGame() {
    game.gameRun = true;
    game.gameRun2 = true;
    nextBlock(game.fieldPlayer.name);
    nextBlock(game.fieldPlayer2.name);

    let number = 0;
    game.gameLoop = setInterval(function () {
        number++;
        if ((number % game.speed === 0)) {
            playerDrop(game.fieldPlayer, game.context, game.area);
            playerDrop(game.fieldPlayer2, game.context2, game.area2)
        }
        if (game.gameRun && game.gameRun2) {
            draw(game.fieldPlayer, game.context, game.area);
            draw(game.fieldPlayer2, game.context2, game.area2)
        }
        else if (!game.gameRun) {
            youLose(game.context);
            youWon(game.context2);
        }
        else if (!game.gameRun2) {
            youLose(game.context2);
            youWon(game.context);
        }
    }, 10);
}

function countdown(durationInSeconds) {
    let timer = durationInSeconds, minutes, seconds;

    game.countdown = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + (seconds - 1) : seconds;

        document.querySelector("#time").textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = durationInSeconds;
        }

        if (timer === 0) {
            if (game.fieldPlayer.score < game.fieldPlayer2.score) {
                youLose(game.context);
                youWon(game.context2);
            }
            else if (game.fieldPlayer.score > game.fieldPlayer2.score) {
                youLose(game.context2);
                youWon(game.context);

            } else if (game.fieldPlayer.score === game.fieldPlayer2.score) {
                Tie();
            }
        }
    }, 1000);
}


function nextBlock(player) {
    if (game.fieldPlayer.name === player) {
        makePieces(game.fieldPlayer, game.area);
    }
    if (game.fieldPlayer2.name === player) {
        makePieces(game.fieldPlayer2, game.area2);
    }
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
        // player.score += rowCount * 100;
        let addScore = rowCount * 100;
        rowCount *= 2;

        let object = JSON.parse('{ "score":"' + addScore + '","player":"' + player.name + '"}');
        console.log(object);
        eb.send("tetris.infoBackend.updateGame", object, function (error, reply) {
            if (error) {
                console.log(error)
            }
            console.log(reply.body);

        });
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
            [matrix[x][y], matrix[y][x]] = [matrix[y][x], matrix[x][y]]
        }
    }
    if (dir > 0) {
        matrix.forEach(row => row.reverse());
    } else {
        matrix.reverse();
    }
}

function makePieces(player, area) {
    let pieces = "ijlostzb";
    player.matrix = makePiece(pieces[Math.floor(Math.random() * pieces.length)]);
    player.pos.y = 0;
    player.pos.x = (Math.floor(area[0].length / 2)) - (Math.floor(player.matrix[0].length / 2));
    collidefunction(player, area);
}

function collidefunction(player, area) {
    if (collide(player, area)) {
        area.forEach(row => row.fill(0));
        player.score = 0;
        if (player.name === "player1") {
            game.gameRun = false;
        } else if (player.name === "player2") {
            game.gameRun2 = false;
        }
    }
}

function playerDrop(player, context, area) {
    player.pos.y++;
    if (collide(player, area)) {
        player.pos.y--;
        merge(player, area);
        points(player, area);
        nextBlock(player.name);
        updateScore(player, context);
    }
}

function playerMove(player, dir, area) {
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
    if (player.matrix != null) {
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

function resultscore(context) {
    clearInterval(game.gameLoop);
    clearInterval(game.countdown);
    context.font = "2px Comic Sans MS";
    context.fillStyle = "#ffffff";
    context.textAlign = "center";
    context.textBaseline = "middle";
}

function youLose(context) {
    resultscore(context);
    context.fillText("YOU LOSE!", (player1.width / 20) / 2, (player1.width / 20) / 2);
}

function youWon(context) {
    resultscore(context);
    context.fillText("YOU WON!", (player1.width / 20) / 2, (player1.width / 20) / 2);
}

function Tie() {
    resultscore(game.context);
    resultscore(game.context2);
    game.context.fillText("DRAW", (player1.width / 20) / 2, (player1.width / 20) / 2);
    game.context2.fillText("DRAW", (player2.width / 20) / 2, (player2.width / 20) / 2);
}

function drawMatrix(matrix, offset, context) {
    matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value !== 0) {
                context.fillStyle = game.colors[value];
                context.fillRect(x + offset.x, y + offset.y, 1, 1);
                let imgTag = document.createElement("IMG");
                imgTag.src = game.colors[value];
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
        // TODO: eigen block draai niet correct
    }
}