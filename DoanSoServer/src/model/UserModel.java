package model;

public class UserModel {
    private int userId;
    private String userName;
    private String password;
    private float score;
    private int win;
    private int draw;
    private int lose;

    public UserModel() {
    }

    public UserModel(int userId, String userName, String password, float score, int win, int draw, int lose) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }
    
    
}