package uz.gita.numberpuzzle.utils;

import java.util.ArrayList;

public class IsSolvingPuzzle {


    public boolean isSolvable(ArrayList<Integer> puzzle) {
        puzzle.add(0);
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.size());
        int row = 0; 
        int blankRow = 0; 

        for (int i = 0; i < puzzle.size(); i++) {
            if (i % gridWidth == 0) { 
                row++;
            }
            if (puzzle.get(i) == 0) { 
                blankRow = row;
                continue;
            }
            for (int j = i + 1; j < puzzle.size(); j++) {
                if (puzzle.get(i) > puzzle.get(i) && puzzle.get(i) != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) {
            if (blankRow % 2 == 0) {
                return parity % 2 == 0;
            } else {
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }
}
