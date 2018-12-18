package game.player.playfields.playfield.block;

/**
 * @author Remote Access Tetris aka RAT
 */

public enum TypesOfBlocks {
    LINEBLOCK, INVERSELBLOCK, LBLOCK, SQUAREBLOCK, LEFTNBLOCK, TBLOCK, ZBLOCK;

    // LINEBLOCK { 1, 1, 1, 1}
    // INVERSELBLOCK { 1, 0, 0 , 0 }
    //               { 1, 1, 1, 0 }
    // LBLOCK { 0, 0, 1, 0 }
    //        { 1, 1, 1, 0 }
    // SQUAREBLOCK { 1, 1, 0, 0 }
    //             { 1, 1, 0, 0 }
    // LEFTNBLOCK { 0, 1, 1, 0 }
    //            { 1, 1, 0, 0 }
    // TBLOCK { 0, 1, 0, 0 }
    //        { 1, 1, 1, 0}
    // ZBLOCK { 1, 1, 0, 0 }
    //        { 0, 1, 1, 0 }
}
