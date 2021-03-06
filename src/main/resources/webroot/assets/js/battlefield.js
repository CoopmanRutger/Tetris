"use strict";

/* global EventBus */
//let eb = new EventBus("http://localhost:8021/tetris-21/socket");
let eb = new EventBus("http://172.21.22.52:48200/tetris-21/socket");
// let eb = new EventBus("http://192.168.0.251:8021/tetris-21/socket");
let game = {
    gameRun: false, gameRun2: false, gameLoop: null, countdown: null, timer: 180, speed: 50, evenement:false,
    grid: makeMatrixAZeroMatrix(1, 1), context: player1.getContext("2d"),
    grid2: makeMatrixAZeroMatrix(1, 1), context2: player2.getContext("2d"),
    fieldPlayer: {name: null, pos: {x: 0, y: 0}, matrix: null, score: 0, width:12,height:20,
        ability1:null, ability2:null, startvalue1:0, startvalue2:0, showField:true},
    fieldPlayer2: {name: null, pos: {x: 0, y: 0}, matrix: null, score: 0,width:12,height:20,
        ability1:null, ability2:null, startvalue1:0, startvalue2:0, showField:true},
    color: null
};

document.addEventListener("DOMContentLoaded", init);

let paused = false;

function init() {
    eb.onopen = function () {
        initialize();
    };
    //countdown(game.timer);
    addEventHandler("#openmodal", "click", openModal);
    addEventHandler("body", "click", onModalClose);
    addEventHandler("#keepplaying", "click", keepPlaying);
    document.querySelector("#pause").addEventListener("click", pause);
    document.querySelector("#resume").addEventListener("click", resume);
}

function initialize() {
    registers();
}

function registers() {
    eb.send("tetris-21.socket.gamestart", "Im ready!", function (error) {
        if (error) {
            console.log(error)
        }
    });

    eb.registerHandler("tetris-21.socket.game", function (error, message) {
        setGamePlay(JSON.parse(message.body));
        let gameInfo = JSON.parse(message.body);
        // console.log(gameInfo);

        let playfieldInfo = gameInfo.players[0].playfields;
        let firstName = gameInfo.players[0].name;
        let secName = gameInfo.players[1].name;

        giveMatrixNumbers(playfieldInfo, firstName, secName);
        backgroundStuff();
    });

    eb.registerHandler("tetris-21.socket.BattleField", function (error, message) {
        if (error) {
            console.log(error)
        }
        console.log(message);
    });

    eb.registerHandler("tetris-21.socket.battleField.timer", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body);
        let info = JSON.parse(message.body);
        let timeInSeconds = info.timeInSeconds;
        displayTimer(timeInSeconds);
    });
}

function displayTimer(timeInSeconds) {
    let minutes = parseInt((timeInSeconds % 3600) / 60);
    let seconds = parseInt((timeInSeconds % 3600) % 60);

    if (minutes < 10){
        minutes = "0" + minutes;
    }

    if (seconds < 10){
        seconds = "0" + seconds;
    }

    document.querySelector('#time').innerHTML = minutes + ":" + seconds;
}

function giveMatrixNumbers(playfields, firstPlayerName, secPlayerName) {
    let playfieldheight = playfields[firstPlayerName].playfield.length;
    let playfieldWidth = playfields[firstPlayerName].playfield[0].length;
    let playfield2Height = playfields[secPlayerName].playfield.length;
    let playfield2Width = playfields[secPlayerName].playfield[0].length;

    game.grid = makeMatrixAZeroMatrix(playfieldWidth, playfieldheight);
    game.grid2 = makeMatrixAZeroMatrix(playfield2Width, playfield2Height);
}

function drawMatrix(matrix, offset, context) {
      matrix.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value !== 0) {
                context.fillStyle = game.color;
                context.fillRect(x + offset.x, y + offset.y, 1, 1);
                let imgTag = document.createElement("IMG");
                imgTag.src = game.color;
                context.drawImage(imgTag, x + offset.x, y + offset.y, 1, 1);
            }
        });
    });
}

function setGamePlay(infoBackend) {

    console.log(infoBackend);
    setRightPlayer(infoBackend.players[0]);
    setLeftPlayer(infoBackend.players[1]);

    setEvents(infoBackend.events);
}

function setRightPlayer(player) {
    // console.log(player);

    game.fieldPlayer2.name = sessionStorage.getItem("PlayerName");
    select('#player2name').innerHTML = game.fieldPlayer2.name;
    game.fieldPlayer2.ability1 = player.hero.abilitySet[0].name;
    select('#abilty1p2').innerHTML = game.fieldPlayer2.ability1 + " <img src=\"assets/media/9.png\" "
        + "class='key' title='key9' alt='key9'>";
    game.fieldPlayer2.ability2 = player.hero.abilitySet[1].name;
    select('#abilty2p2').innerHTML = game.fieldPlayer2.ability2 + " <img src=\"assets/media/0.png\" " +
        "class='key' title='key0' alt='key0'>";
    select("#heroimgplayer2").innerHTML = '<img src="assets/media/' + player.hero.name + '.png">';

    game.fieldPlayer2.startvalue1 = player.hero.abilitySet[0].startValue;
    game.fieldPlayer2.startvalue2 = player.hero.abilitySet[1].startValue;
}

function setLeftPlayer(player) {
    console.log(player);

    game.fieldPlayer.name = 'User2';
    select('#player1name').innerHTML = 'User2';
    game.fieldPlayer.ability1 = player.hero.abilitySet[0].name;
    select('#abilty1p1').innerHTML = game.fieldPlayer.ability1 + " <img src=\"assets/media/1.png\" " +
        "class='key' title='key1' alt='key1'>";
    game.fieldPlayer.ability2 = player.hero.abilitySet[1].name;
    select('#abilty2p1').innerHTML = game.fieldPlayer.ability2 + " <img src=\"assets/media/2.png\" " +
        "class='key' title='key2' alt='key2'>";
    select("#heroimgplayer1").innerHTML = '<img src="assets/media/' + player.hero.name + '.png">';

    game.fieldPlayer.startvalue1 = player.hero.abilitySet[0].startValue;
    game.fieldPlayer.startvalue2 = player.hero.abilitySet[1].startValue;
}

function setEvents(events) {
    // console.log(events);
    // console.log(events.events);
    // console.log(events.events[0].name);
    // console.log(events.events[1].name);
}

function preparation(context, fieldPlayer, area) {
    context.scale(20, 20);
    nextBlock(fieldPlayer.name);
    draw(fieldPlayer, context, area);
}

function backgroundStuff() {

    preparation(game.context, game.fieldPlayer, game.grid);
    preparation(game.context2, game.fieldPlayer2, game.grid2);
    startGame();

    const move = 1;
    document.addEventListener('keydown', function (e) {
        //links Q
        if (e.keyCode === 81) {
            playerMove(game.fieldPlayer, -move, game.grid);
        }
        // draai Z
        else if (e.keyCode === 90) {
            playerRotate(game.fieldPlayer, game.grid);
        }
        // Beneden S
        else if (e.keyCode === 83) {
            playerDrop(game.fieldPlayer, game.context, game.grid);
        }
        // rechts D
        else if (e.keyCode === 68) {
            playerMove(game.fieldPlayer, +move, game.grid);
        }
        //  1
        else if (e.keyCode === 49) {
            abilities(game.fieldPlayer.name, game.fieldPlayer2.name, game.fieldPlayer.ability1);
        }
        // 2
        else if (e.keyCode === 50) {
            abilities(game.fieldPlayer.name, game.fieldPlayer2.name, game.fieldPlayer.ability2);
        }
    });

    document.addEventListener('keydown', function (e) {
        // links
        if (e.keyCode === 37) {
            playerMove(game.fieldPlayer2, -move, game.grid2);
        }
        // rechts
        else if (e.keyCode === 39) {
            playerMove(game.fieldPlayer2, +move, game.grid2);
        }
        // beneden
        else if (e.keyCode === 40) {
            playerDrop(game.fieldPlayer2, game.context2, game.grid2);
        }
        // draaien
        else if (e.keyCode === 38) {
            playerRotate(game.fieldPlayer2, game.grid2);
        }
        //  9
        else if (e.keyCode === 57) {
            abilities(game.fieldPlayer2.name, game.fieldPlayer.name, game.fieldPlayer2.ability1);
            //todo
        }
        // 0
        else if (e.keyCode === 48) {
            abilities(game.fieldPlayer2.name, game.fieldPlayer.name, game.fieldPlayer2.ability2);
            //todo
        }
        // E
        else if (game.speed === 42 || game.speed === 26 || game.speed === 10 || game.speed === 2) {
            if (!game.evenement){
                evenements("tornado");
                game.evenement = true;
            }
        }
        // R
        else if (game.speed === 48 || game.speed === 30 || game.speed === 15 || game.speed === 5) {
            if (game.evenement){
                evenements("abilityReset");
                game.evenement = false;
            }
        }
    })
}

function evenements(string) {
    select("main header div h1").innerHTML = string;
    let object = JSON.stringify({evenement: string,
        playerName1: game.fieldPlayer.name,
        playerName2: game.fieldPlayer2.name
    });
    eb.send("tetris-21.socket.battleField.evenements", object, function (error, reply) {
        if (error) {
            console.log(error)
        }
        let info = JSON.parse(reply.body);
        game.grid = info[game.fieldPlayer.name];
        game.grid2 = info[game.fieldPlayer2.name]
    });

    setTimeout(() => {
        select("main header div h1").innerHTML = "";
    }, 2000)
}

function abilities(attacker, victim , string) {
    let object = JSON.stringify({attacker:attacker ,victim: victim, ability:string});
    console.log(string);
    eb.send("tetris-21.socket.battleField.abilities", object, function (error, reply) {
        if (error) {
            console.log(error)
        }

        let abilityBoolean = JSON.parse(reply.body);
        doAbility(string,attacker,victim, abilityBoolean)
    })
}

function doAbility(string,attacker,victim , abilityBoolean) {

    console.log(abilityBoolean);

    switch (string){
        case "Joker":
            if (abilityBoolean){
                let ctx = null;
                if (attacker === game.fieldPlayer.name){
                    game.fieldPlayer2.showField = false;
                    ctx = game.context2;
                }
                if (attacker === game.fieldPlayer2.name){
                    game.fieldPlayer.showField = false;
                    ctx = game.context;
                }
                ctx.beginPath();
                ctx.rect(0, 0, game.fieldPlayer.width, game.fieldPlayer.height);
                ctx.fillStyle = "white";
                ctx.fill();
                setTimeout(() => {
                    game.fieldPlayer2.showField = true;
                    game.fieldPlayer.showField = true;
                }, 5000);
            }

            break;
        case "CheerCrowd":
            if (abilityBoolean){

            }
            break;
    }

}

function startGame() {
    game.gameRun = true;
    game.gameRun2 = true;

    let number = 0;
    game.gameLoop = setInterval(function () {
        number++;
        if ((number % game.speed === 0)) {
            playerDrop(game.fieldPlayer, game.context, game.grid);
            playerDrop(game.fieldPlayer2, game.context2, game.grid2)
        }
        if (!game.gameRun) {
            youLose(game.context);
            youWon(game.context2);
        } else if (!game.gameRun2) {
            youLose(game.context2);
            youWon(game.context);
        } else if (game.gameRun && game.gameRun2) {
            if (game.fieldPlayer.showField){
                draw(game.fieldPlayer, game.context, game.grid);
            }
            if (game.fieldPlayer2.showField){
                draw(game.fieldPlayer2, game.context2, game.grid2);
            }
        }
    }, 10);
}

function countdown(durationInSeconds) {
    let timer = durationInSeconds, minutes, seconds;

    game.countdown = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + (seconds) : seconds;

        document.querySelector("#time").textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = durationInSeconds;
        }

        if (timer === 0) {
            document.querySelector("#time").innerHTML = "00:00";
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

function nextBlock(playerName) {
    if (game.fieldPlayer.name === playerName) {
        makePieces(game.fieldPlayer, game.grid);
    }
    if (game.fieldPlayer2.name === playerName) {
        makePieces(game.fieldPlayer2, game.grid2);
    }
}

function addScoreToPlayer(player, info) {
    if (player.name === game.fieldPlayer2.name ){
        select('#scoreplayer2').innerHTML = info.score;
        player.score = info.score;
        select('#linesPlayer2 p span').innerHTML = info.lines;

    } else if(player.name === game.fieldPlayer.name){
        select('#scoreplayer1').innerHTML = info.score;
        player.score = info.score;
        select('#linesPlayer1 p span').innerHTML = info.lines;
    }
}

function abilityBars(player, abilityPoints){
    let points = abilityPoints;
    if(player.name === game.fieldPlayer.name ){
        setWidth(points, "#abilty1p1");
        setWidth(points, "#abilty2p1");
    } else {
        setWidth(points, "#abilty1p2");
        setWidth(points, "#abilty2p2");
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

function merge(player, grid) {
    let object = JSON.stringify({player:player ,grid: grid});
    eb.send("tetris-21.socket.battleField.blockOnField", object, function (error, reply) {
        if (error) {
            console.log(error);
        }
        let playfield = JSON.parse(reply.body);
        if (player.name === sessionStorage.getItem("PlayerName")){
            game.grid2 = playfield;
        } else {
            game.grid = playfield;
        }
    });
}

function rotate(player, area) {
    let object = JSON.stringify({matrix: player.matrix, playerName:player.name});
    eb.send("tetris-21.socket.battleField.rotate", object, function (error, reply) {
            if (error) {
                console.log(error);
            }
            let rotatedBlock = JSON.parse(reply.body);
            player.matrix = rotatedBlock.block;
            collide2(player, area);
    })

}

function collide2(player, area){
    while (collide(player, area)){
        player.pos.x += offset;
        offset = -(offset + (offset > 0 ? 1 : -1));
        if (offset > player.matrix[0].length) {
            player.pos.x = 5;
                return;
            }
    }
}

function makePieces(player, area) {
    let object = JSON.stringify({playername: player.name});
    eb.send("tetris-21.socket.battleField.getNewBlock", object, function (error, reply) {
        if (error) {
            console.log(error)
        }
        let info = JSON.parse(reply.body);

        player.matrix = info.block;
        player.pos.y = 0;
        player.pos.x = 5;

        update(player, info);
        collidefunction(player, area);
    });
}

function update(player, info) {
    game.color = info.color;
    let score = info.score;
    let points = info.points;
    let lines = info.lines;
    let gameSpeed = info.gameSpeed;
    console.log(gameSpeed);

    game.speed = gameSpeed;
    let info1 = {score: score, points: points, lines: lines};
    addScoreToPlayer(player, info1);
    abilityBars(player, points);
}


function draw(player, context, area) {
    context.clearRect(0, 0, player.width, player.height);
    context.fillStyle = "#000000";
    context.fillRect(0 , 0, player.width, player.height);
    drawMatrix(area, {x: 0, y: 0}, context);
    if (player.matrix != null) {
        drawMatrix(player.matrix, player.pos, context);
    }
}

function collidefunction(player, area) {
    if (collide(player, area)) {
        area.forEach(row => row.fill(0));
        // player.score = 0;
        if (player.name === "player1") {
            game.gameRun = false;
        } else if (player.name === "player2") {
            game.gameRun2 = false;
        }
    }
}

function playerDrop(player, context, area) {
    if (!paused) {
        player.pos.y++;
        if (collide(player, area)) {
            player.pos.y--;
            merge(player, area);
            nextBlock(player.name);
        }
    }
}

function playerMove(player, dir, area) {
    if (!paused) {
        player.pos.x += dir;
        if (collide(player, area)) {
            player.pos.x -= dir;
        }
    }
}

function playerRotate(player, area) {
    if (!paused) {
        const pos = player.pos.x;
        let offset = 1;
        player.matrix = rotate(player, area);
    }
}

function setWidth(lines, id) {
    let multiply = 0 ;
    let maxVal = 0;
    if (id === "#abilty1p1") {
        maxVal = game.fieldPlayer.startvalue1;
        multiply = 100/maxVal;
    } if (id === "#abilty1p2"){
        maxVal = game.fieldPlayer2.startvalue1;
        multiply = 100/maxVal;
    } else if (id === "#abilty2p1") {
        maxVal = game.fieldPlayer.startvalue2;
        multiply = 100/maxVal;
    } if (id === "#abilty2p2") {
        maxVal = game.fieldPlayer2.startvalue2;
        multiply = 100/maxVal;
    }

    if(0 <= lines < maxVal) {
        select(id).style.backgroundSize = lines * multiply + "%";
    } else {
        select(id).style.width = 100 + "%";
    }
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

function makeMatrixAZeroMatrix(width, height) {
    const matrix = [];
    while (height--) {
        matrix.push(new Array(width).fill(0));
    }
    return matrix;
}
