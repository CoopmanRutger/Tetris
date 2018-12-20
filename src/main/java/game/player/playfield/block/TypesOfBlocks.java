package game.player.playfield.block;

public enum TypesOfBlocks {
    lineBlock, inverseLBlock, lBlock, squareBlock, leftNBlock, tBlock, zBlock, specialBlock

    // lineblock { 1, 1, 1, 1}
    // inverseLBlock { 1, 0, 0 , 0 }
    //               { 1, 1, 1, 0 }
    // lBlock { 0, 0, 1, 0 }
    //        { 1, 1, 1, 0 }
    // squareBlock { 1, 1, 0, 0 }
    //             { 1, 1, 0, 0 }
    // leftNBlock { 0, 1, 1, 0 }
    //            { 1, 1, 0, 0 }
    // tBlock { 0, 1, 0, 0 }
    //        { 1, 1, 1, 0}
    // zBlock { 1, 1, 0, 0 }
    //        { 0, 1, 1, 0 }
}
