"use strict";

/**********************************/
/*----- Hide & Show handling -----*/

/**********************************/
function hide(selector) {
    let hide = select(selector);
    if (!hide.classList.contains("hidden")) {
        hide.classList.add("hidden");
    }
}

function show(selector) {
    let show = select(selector);
    if (show.classList.contains("hidden")) {
        show.classList.remove("hidden");
    }
}

function hideAndShow(toHide, toShow) {
    hide(toHide);
    show(toShow);
}


/**************************************/
/*----- Add EventHandler utility -----*/

/**************************************/
function addEventHandler(selector, event, handler) {
    let items = document.querySelectorAll(selector);
    let itemsLength = items.length;
    let amount = 0;

    for (let i = 0; i < itemsLength; i++) {
        items[i].addEventListener(event, handler);
        amount++;
    }

    return amount;
}

/**************************************/
/*------- Open and close modal -------*/

/**************************************/
function closeModal() {
    select("#modaldiv").style.display = "none";
}

function openModal() {
    select("#modaldiv").style.display = "block";
}

/*******************************/
/*----- Utility Functions -----*/

/*******************************/
function createElement(tag) {
    return document.createElement(tag);
}

function select(selector) {
    return document.querySelector(selector);
}

function isHidden(item) {
    return select(item).classList.contains("hidden");
}