"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    getFactionFromDB();
    setTitle();
    addEventHandler("#clanbutton", "click", switchPage);
    addEventHandler("#factionbutton", "click", switchPage);
    addEventHandler("#kingdom", "click", goToKingdom);
}

function getFactionFromDB() {
    // TODO: get faction from database.
}

function setTitle() {
    if (isHidden("#faction")) {
        select("title").innerHTML = "clan page";
    } else if (isHidden("#clan")) {
        select("title").innerHTML = "faction page";
    } else {
        select("title").innerHTML = "error";
    }
}

function switchPage() {
    if (isHidden("#clan")) {
        show("#clan");
        hide("#faction");
        setTitle()
    } else {
        hide("#clan");
        show("#faction");
        setTitle()
    }
}

function goToKingdom() {
    location.href = "#  ";
}