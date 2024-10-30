package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DatabaseConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.UserModel;

public class UserController {

    // SQL queries
    private final String INSERT_USER = "INSERT INTO users (username, password, score, win, draw, lose) VALUES (?, ?, 0, 0, 0, 0)";
    private final String CHECK_USER = "SELECT userId FROM users WHERE username = ? LIMIT 1";
    private final String LOGIN_USER = "SELECT username, password, score FROM users WHERE username = ? AND password = ?";
    private final String GET_INFO_USER = "SELECT username, score, win, draw, lose FROM users WHERE username = ?";
    private final String UPDATE_USER = "UPDATE users SET score = ?, win = ?, draw = ?, lose = ? WHERE username = ?";
    private final String GET_NAME_AND_SCORE = "SELECT username, score FROM users WHERE username = ?";
    private final String UPDATE_HISTORY = "INSERT INTO history (userName1, userName2, resultMatch) VALUES (?, ?, ?)" ;
    // Database connection instance
    private final Connection con;

    public UserController() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public String register(String username, String password) {
        try {
            // Check if user already exists
            PreparedStatement p = con.prepareStatement(CHECK_USER);
            p.setString(1, username);
            ResultSet r = p.executeQuery();

            if (r.next()) {
                return "failed;User Already Exists";
            } else {
                r.close();
                p.close();
                // Insert new user
                p = con.prepareStatement(INSERT_USER);
                p.setString(1, username);
                p.setString(2, password);
                p.executeUpdate();
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success;";
    }

    public String login(String username, String password) {
        if (con == null) {
            System.out.println("connection that bai");
        }
        try {
            // Check user credentials
            PreparedStatement p = con.prepareStatement(LOGIN_USER);
            p.setString(1, username);
            p.setString(2, password);
            ResultSet r = p.executeQuery();

            if (r.next()) {
                float score = r.getFloat("score");
                return "success;" + username + ";" + score;
            } else {
                return "failed;Please enter the correct account password!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInfoUser(String username) {
        UserModel user = new UserModel();
        try {
            PreparedStatement p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            ResultSet r = p.executeQuery();

            if (r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
            }
            return "success;" + user.getUserName() + ";" + user.getScore() + ";" + user.getWin() + ";" + user.getDraw() + ";" + user.getLose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateHistory(String user1, String user2, String detail){
        try{
            PreparedStatement p = con.prepareStatement(UPDATE_HISTORY) ; 
            p.setString(1, user1);
            p.setString(2, user2);
            p.setString(3, detail);
            p.executeUpdate() ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateUser(UserModel user) {
        try {
            PreparedStatement p = con.prepareStatement(UPDATE_USER);
            p.setFloat(1, user.getScore());
            p.setInt(2, user.getWin());
            p.setInt(3, user.getDraw());
            p.setInt(4, user.getLose());
            p.setString(5, user.getUserName());

            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserModel getUser(String username) {
        UserModel user = new UserModel();
        try {
            PreparedStatement p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            ResultSet r = p.executeQuery();

            if (r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getHistory(String username) {
    StringBuilder resultHistory = new StringBuilder("success");
    String GET_HISTORY_QUERY = "SELECT userName1, userName2, resultMatch FROM history WHERE userName1 = ? OR userName2 = ?";
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm"); // Định dạng gốc trong database
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    try {
        PreparedStatement p = con.prepareStatement(GET_HISTORY_QUERY);
        p.setString(1, username);
        p.setString(2, username);
        ResultSet r = p.executeQuery();

        while (r.next()) {
            String player1 = r.getString("userName1");
            String player2 = r.getString("userName2");
            String resultMatch = r.getString("resultMatch");

            // Tách kết quả và thời gian từ resultMatch
            String[] resultParts = resultMatch.split("\\ ");
            String dateStr = resultParts[0];
            String result =  resultParts[1];
            String formattedDate = dateStr;

            // Định dạng lại ngày nếu có phần ngày
            if (!dateStr.isEmpty()) {
                try {
                    java.util.Date date = inputDateFormat.parse(dateStr);
                    formattedDate = outputDateFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Xác định đối thủ và kết quả dựa trên người chơi hiện tại
            String opponent = player1.equals(username) ? player2 : player1;
            if (!player1.equals(username)) {
                if(result.equals("thang")) {
                    result = "thua";
                } else if ( result.equals("thua")){
                    result = "thang";
                }
                else if(result.equals("hoa")){
                    result = "hoa";
                }
            } 
         
            // Thêm thông tin vào chuỗi kết quả với đối thủ, thời gian, kết quả cách nhau bằng dấu ";"
            resultHistory.append(";").append(opponent).append(";").append(formattedDate).append(";").append(result);
        }

        return resultHistory.toString();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public String getRank() {
        StringBuilder rankList = new StringBuilder("RANK;");
        String query = "SELECT username, score, win, draw, lose FROM users ORDER BY score DESC";

        try {
            PreparedStatement p = con.prepareStatement(query);
            ResultSet r = p.executeQuery();

            while (r.next()) {
                String username = r.getString("username");
                float score = r.getFloat("score");
                int win = r.getInt("win");
                int draw = r.getInt("draw");
                int lose = r.getInt("lose");

                // Append thông tin từng người chơi vào rankList
                rankList.append(username).append(":")
                        .append(score).append(":")
                        .append(win).append(":")
                        .append(draw).append(":")
                        .append(lose).append(";");
            }

            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed;" + e.getMessage();
        }

        return rankList.toString();
    }

    public String getRankWin() {
        StringBuilder rankList = new StringBuilder("RANKWIN;");
        String query = "SELECT username, score, win, draw, lose FROM users ORDER BY win DESC";

        try {
            PreparedStatement p = con.prepareStatement(query);
            ResultSet r = p.executeQuery();

            while (r.next()) {
                String username = r.getString("username");
                float score = r.getFloat("score");
                int win = r.getInt("win");
                int draw = r.getInt("draw");
                int lose = r.getInt("lose");

                // Append thông tin từng người chơi vào rankList
                rankList.append(username).append(":")
                        .append(score).append(":")
                        .append(win).append(":")
                        .append(draw).append(":")
                        .append(lose).append(";");
            }

            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed;" + e.getMessage();
        }

        return rankList.toString();
    }
}
