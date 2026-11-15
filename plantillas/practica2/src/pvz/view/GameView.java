package pvz.view;

//solo los métodos de la vista

public interface GameView {
    void showGame();
    void showEndMessage();
    void showError(String message);
    void showMessage(String message);
    String[] getPrompt();//aqui pq es la entrada del player
}