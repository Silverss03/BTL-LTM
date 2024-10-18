package view;

import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import run.ClientRun;

public class HomeView extends javax.swing.JFrame {

    String statusCompetitor = "";

    public void getUserOnline() {

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnCreateRoom = new javax.swing.JButton();
        btnRank = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        lbScore = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 51));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 97));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 64)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LUCKY CARDS");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(80, 10, 432, 85);

        btnCreateRoom.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCreateRoom.setText("Chơi");
        btnCreateRoom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCreateRoom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateRoomActionPerformed(evt);
            }
        });
        jPanel1.add(btnCreateRoom);
        btnCreateRoom.setBounds(180, 250, 200, 50);

        btnRank.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRank.setText("Bảng xếp hạng");
        btnRank.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRank.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnRank);
        btnRank.setBounds(180, 340, 200, 50);

        btnLogOut.setBackground(new java.awt.Color(255, 51, 51));
        btnLogOut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Đăng xuất");
        btnLogOut.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogOut);
        btnLogOut.setBounds(180, 430, 200, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/background.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 600, 600);

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Danh sách người chơi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane.setViewportView(tblUser);

        jPanel1.add(jScrollPane);
        jScrollPane.setBounds(600, 100, 200, 460);

        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel1.add(btnRefresh);
        btnRefresh.setBounds(600, 560, 200, 40);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar.png"))); // NOI18N

        lbUsername.setText("Player:");

        lbScore.setText("Tổng điểm:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbUsername)
                    .addComponent(lbScore))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbScore)))
                .addContainerGap())
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(600, 0, 172, 100);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateRoomActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "Hãy chọn 1 người chơi!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));

            // check user online/in game
            ClientRun.socketHandler.checkStatusUser(userSelected);
            switch (statusCompetitor) {
                case "ONLINE" ->
                    ClientRun.socketHandler.inviteToPlay(userSelected);
                case "OFFLINE" ->
                    JOptionPane.showMessageDialog(HomeView.this, "Người chơi này đang offline.", "ERROR", JOptionPane.ERROR_MESSAGE);
                case "INGAME" ->
                    JOptionPane.showMessageDialog(HomeView.this, "Người chơi này đang trong game.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCreateRoomActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        ClientRun.socketHandler.getListOnline();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất không?",
                "Đăng xuất",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // Quay về LoginView
            new LoginView().setVisible(true);
            this.dispose(); // Đóng cửa sổ HomeView
            ClientRun.socketHandler.logout();
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateRoom;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnRank;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}