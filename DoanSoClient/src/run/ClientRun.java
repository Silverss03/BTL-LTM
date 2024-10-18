package run;

import controller.SocketHandler;
import view.ConnectServer;
import view.GameView;
import view.HomeView;
import view.LoginView;
import view.RankView;
import view.RankWinView;
import view.RegisterView;

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
        GAMEVIEW
    }

    // scenes
    public static ConnectServer connectServer;
    public static LoginView loginView;
    public static RegisterView registerView;
 public static RankView rankView;
    public static RankWinView rankWinView;
    public static HomeView homeView;
    public static GameView gameView;

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
        gameView = new GameView();
    }

    public static void openScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    connectServer = new ConnectServer();
                    connectServer.setVisible(true);
                    break;
                case LOGIN:
                    loginView = new LoginView();
                    loginView.setVisible(true);
                    break;
                case REGISTER:
                    registerView = new RegisterView();
                    registerView.setVisible(true);
                    break;
                case HOMEVIEW:
                    homeView = new HomeView();
                    homeView.setVisible(true);
                    break;
                case RANKVIEW:
                    rankView = new RankView();
                    rankView.setVisible(true);
                    break;
                case RANKWINVIEW:
                    rankWinView = new RankWinView();
                    rankWinView.setVisible(true);
                    break;
                case GAMEVIEW:
                    gameView = new GameView();
                    gameView.setVisible(true);
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
                case RANKVIEW:
                    if (rankView != null) rankView.dispose();
                    break;
                case RANKWINVIEW:
                    if (rankWinView != null) rankWinView.dispose();
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
        rankView.dispose();
        rankWinView.dispose();
    }

    public static void main(String[] args) {
        new ClientRun();
    }
}
