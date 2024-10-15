package run;

import controller.SocketHandler;
import view.ConnectServer;
import view.HomeView;
import view.InfoPlayerView;
import view.LoginView;
import view.MessageView;
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
    public static HomeView homeView;
    public static RankView rankView;
    public static RankWinView rankWinView;
    public static InfoPlayerView infoPlayerView;
     public static MessageView messageView;


    // controller 
    public static SocketHandler socketHandler;

    public ClientRun() {
        socketHandler = new SocketHandler();
//        initScene();
        openScene(SceneName.CONNECTSERVER);
    }

//    public void initScene() {
//        connectServer = new ConnectServer();
//    }

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
                 case INFOPLAYER:
                    infoPlayerView = new InfoPlayerView();
                    infoPlayerView.setVisible(true);
                     case MESSAGEVIEW:
                    messageView = new MessageView();
                    messageView.setVisible(true);
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
                 case INFOPLAYER:
                    infoPlayerView.dispose();
                    break;
                case MESSAGEVIEW:
                    messageView.dispose();
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
        infoPlayerView.dispose();
        messageView.dispose();
    }

    public static void main(String[] args) {
        new ClientRun();
    }
}
