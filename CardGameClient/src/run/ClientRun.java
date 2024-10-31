package run;

import com.formdev.flatlaf.FlatLightLaf;
import controller.SocketHandler;
import javax.swing.UIManager;
import view.ConnectServer;
import view.GameView;
import view.HomeView;
import view.InfoPlayerView;
import view.LoginView;
import view.MessageView;
import view.RankView;
import view.RankWinView;
import view.RegisterView;
import view.HistoryView;

public class ClientRun {

    public enum SceneName {
        CONNECTSERVER,
        LOGIN,
        REGISTER,
        HOMEVIEW,
        INFOPLAYER,
        MESSAGEVIEW,
        RANKVIEW,
        RANKWINVIEW,
        GAMEVIEW,
        HISTORYVIEW
    }

    // scenes
    public static ConnectServer connectServer;
    public static LoginView loginView;
    public static RegisterView registerView;
    public static RankView rankView;
    public static RankWinView rankWinView;
    public static HomeView homeView;
    public static GameView gameView;
    public static InfoPlayerView infoPlayerView;
    public static MessageView messageView;
    public static HistoryView historyView;

    // controller 
    public static SocketHandler socketHandler;

    public ClientRun() {
        socketHandler = new SocketHandler();
        initScene();
        openScene(SceneName.CONNECTSERVER);
    }

    public void initScene() {
        connectServer = new ConnectServer();
        loginView = new LoginView();
        registerView = new RegisterView();
        rankView = new RankView();
        rankWinView = new RankWinView();
        homeView = new HomeView();
        infoPlayerView = new InfoPlayerView();
        messageView = new MessageView();
        gameView = new GameView();
        historyView = new HistoryView();

    }

    public static void openScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        connectServer = new ConnectServer();
                        connectServer.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case LOGIN:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        loginView = new LoginView();
                        loginView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case REGISTER:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        registerView = new RegisterView();
                        registerView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case HOMEVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        homeView = new HomeView();
                        homeView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case INFOPLAYER:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        infoPlayerView = new InfoPlayerView();
                        infoPlayerView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case MESSAGEVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        messageView = new MessageView();
                        messageView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case RANKVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        rankView = new RankView();
                        rankView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case RANKWINVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        rankWinView = new RankWinView();
                        rankWinView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case GAMEVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        gameView = new GameView();
                        gameView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                case HISTORYVIEW:
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        historyView = new HistoryView();
                        historyView.setVisible(true);
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    connectServer.dispose();
                    break;
                case LOGIN:
                    loginView.dispose();
                    break;
                case REGISTER:
                    registerView.dispose();
                    break;
                case HOMEVIEW:
                    homeView.dispose();
                    break;
                case INFOPLAYER:
                    infoPlayerView.dispose();
                    break;
                case MESSAGEVIEW:
                    messageView.dispose();
                    break;
                case RANKVIEW:
                    if (rankView != null) {
                        rankView.dispose();
                    }
                    break;
                case RANKWINVIEW:
                    if (rankWinView != null) {
                        rankWinView.dispose();
                    }
                    break;
                case GAMEVIEW:
                    gameView.dispose();
                    break;
                case HISTORYVIEW:
                    if (historyView != null) {
                        historyView.dispose();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeAllScene() {
        connectServer.dispose();
        loginView.dispose();
        registerView.dispose();
        homeView.dispose();
        messageView.dispose();
        infoPlayerView.dispose();
        rankView.dispose();
        rankWinView.dispose();
        historyView.dispose();
    }

    public static void main(String[] args) {
        new ClientRun();
    }
}
