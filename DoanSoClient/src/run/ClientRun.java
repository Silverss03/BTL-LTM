package run;

import controller.SocketHandler;
import view.ConnectServer;
import view.HomeView;
import view.LoginView;
import view.RegisterView;

public class ClientRun {
    public enum SceneName {
        CONNECTSERVER,
        LOGIN,
        REGISTER,
        HOMEVIEW,
        INFOPLAYER,
        MESSAGEVIEW,
        GAMEVIEW
    }

    // scenes
    public static ConnectServer connectServer;
    public static LoginView loginView;
    public static RegisterView registerView;
    public static HomeView homeView;

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
    }

    public static void main(String[] args) {
        new ClientRun();
    }
}
