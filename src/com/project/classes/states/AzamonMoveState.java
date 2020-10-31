package src.com.project.classes.states;

import src.com.project.classes.boards.AzamonBoard;

public class AzamonMoveState implements AzamonState {
    public final int pack;
    public final int offer;

    public AzamonMoveState(int pack, int offer) {
        this.pack=pack;
        this.offer=offer;
    }

    @Override
    public AzamonState updateBoard(AzamonBoard azamonBoard) {
        AzamonState oldState = new AzamonMoveState(pack,azamonBoard.getAssignedOffer(pack));
        azamonBoard.__move__(pack,offer);
        return oldState;
    }
}
