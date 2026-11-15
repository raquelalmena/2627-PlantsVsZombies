package pvz.view;

public interface GameView {
    void showGame();
    void showEndMessage();
    void showError(String message);
    void showMessage(String message);
    String[] getPrompt();
}