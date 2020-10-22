package src.com.project.state;

public class AzamonSwapState implements AzamonState {
    public final int first;
    public final int second;

    public AzamonSwapState(int first, int second) {
        this.first=first;
        this.second=second;
    }

    @Override
    public AzamonState updateBoard(AzamonBoard azamonBoard) {
        int offerFirst = azamonBoard.getAssignedOffer(first);
        int offerSecond = azamonBoard.getAssignedOffer(second);
        azamonBoard.__move__(first,offerSecond);
        azamonBoard.__move__(second,offerFirst);
        return this;
    }
}
