package view;

import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import run.ClientRun;

public class HomeView extends javax.swing.JFrame {

    String statusCompetitor = "";

    public void getUserOnline() {
        // Implement logic to get online users if needed
    }

    public HomeView() {
        initComponents();
    }

    public void setStatusCompetitor(String status) {
        statusCompetitor = status;
    }

    public void setListUser(Vector vdata, Vector vheader) {
        tblUser.setModel(new DefaultTableModel(vdata, vheader));
    }

    public void resetTblUser() {
        DefaultTableModel dtm = (DefaultTableModel) tblUser.getModel();
        dtm.setRowCount(0);
    }

    public void setUsername(String username) {
        lbUsername.setText(username);
    }

    public void setUserScore(float score) {
        lbScore.setText("Tổng điểm: " + score);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        // Khởi tạo các thành phần
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCreateRoom = new javax.swing.JButton();
        btnRank = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        lbScore = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        // Thiết lập JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // Thiết lập panel chính không dùng layout manager
        jPanel1.setLayout(null);

        // Background
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/background.jpg")));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 800, 700); // Chiếm toàn bộ khung trái

        // Nút "Chơi"
        btnCreateRoom.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnCreateRoom.setText("Chơi");
        btnCreateRoom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCreateRoom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateRoom.addActionListener(evt -> btnCreateRoomActionPerformed(evt));
        jPanel1.add(btnCreateRoom);
        btnCreateRoom.setBounds(448, 264, 200, 50);

        // Nút "Bảng xếp hạng"
        btnRank.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnRank.setText("Bảng xếp hạng");
        btnRank.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRank.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRank.addActionListener(evt -> btnRankingActionPerformed(evt) );
        jPanel1.add(btnRank);
        btnRank.setBounds(448, 354, 200, 50);

        // Nút "Đăng xuất"
        btnLogOut.setBackground(new java.awt.Color(255, 51, 51));
        btnLogOut.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Đăng xuất");
        btnLogOut.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addActionListener(evt -> btnLogOutActionPerformed(evt));
        jPanel1.add(btnLogOut);
        btnLogOut.setBounds(448, 433, 200, 50);

        // Avatar và thông tin người chơi
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar.png")));
        lbUsername.setText("Player:");
        lbScore.setText("Tổng điểm:");

        // Thiết lập layout cho panel thông tin người chơi
        jPanel3.setLayout(null);
        jPanel3.add(jLabel3);
        jLabel3.setBounds(10, 10, 88, 88);
        jPanel3.add(lbUsername);
        lbUsername.setBounds(110, 10, 150, 30);
        jPanel3.add(lbScore);
        lbScore.setBounds(110, 50, 150, 30);

        // Đặt panel người chơi vào giao diện chính
        jPanel3.setBounds(810, 10, 280, 100);
        jPanel1.add(jPanel3);

        // Cài đặt bảng người chơi
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {{null}},
            new String[] {"Danh sách người chơi"}
        ));
        tblUser.setShowGrid(false);
        tblUser.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblUser.setBorder(null);

        // Cài đặt JScrollPane cho bảng
        jScrollPane.setViewportView(tblUser);
        jScrollPane.setBounds(810, 120, 280, 500);
        jScrollPane.setBorder(null);
        jPanel1.add(jScrollPane);

        // Nút Refresh
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 24));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnRefresh.setBounds(810, 630, 280, 40);
        jPanel1.add(btnRefresh);

        // Thêm panel chính vào JFrame
        getContentPane().add(jPanel1);
    }

    // Xử lý sự kiện khi bấm nút "Chơi"
    private void btnCreateRoomActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 người chơi!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            ClientRun.socketHandler.checkStatusUser(userSelected);
            switch (statusCompetitor) {
                case "ONLINE" -> ClientRun.socketHandler.inviteToPlay(userSelected);
                case "OFFLINE" -> JOptionPane.showMessageDialog(this, "Người chơi này đang offline.", "ERROR", JOptionPane.ERROR_MESSAGE);
                case "INGAME" -> JOptionPane.showMessageDialog(this, "Người chơi này đang trong game.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xử lý sự kiện khi bấm nút Refresh
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        ClientRun.socketHandler.getListOnline();
    }

    // Xử lý sự kiện khi bấm nút Đăng xuất
    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất không?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new LoginView().setVisible(true);
            this.dispose();
            ClientRun.socketHandler.logout();
        }
    }
    private void btnRankingActionPerformed(java.awt.event.ActionEvent evt) {
    // Mở hộp thoại hoặc giao diện mới hiển thị bảng xếp hạng
     ClientRun.socketHandler.getRank();
}

    // Khai báo các biến
    private javax.swing.JButton btnCreateRoom;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnRank;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTable tblUser;
}
