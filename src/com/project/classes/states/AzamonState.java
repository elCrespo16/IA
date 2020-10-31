package src.com.project.classes.states;

import src.com.project.classes.boards.AzamonBoard;

public interface AzamonState {
    AzamonState updateBoard(AzamonBoard azamonBoard);
}
