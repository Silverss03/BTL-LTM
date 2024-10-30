package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import run.ClientRun;

public class RankView extends JFrame {

    private JTable rankTable;
    private DefaultTableModel tableModel;
    private BackgroundPanel backgroundPanel;  // Khai báo biến instance cho BackgroundPanel

    public RankView() {
        setupFrame();
        initTable();
    }

    private void setupFrame() {
        setTitle("Bảng Xếp Hạng");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Tạo topPanel cho tiêu đề
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Bảng Xếp Hạng Người Chơi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#3d6a9f"));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        topPanel.add(titleLabel, BorderLayout.CENTER);
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Tạo panel cho nút và cấu hình các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Đặt trong suốt
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0)); // Thêm khoảng cách cho bottom

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        homeButton.setBackground(Color.decode("#3d6a9f"));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(e -> dispose());

        JButton winRankingButton = new JButton("XH theo trận thắng");
        winRankingButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        winRankingButton.setBackground(Color.decode("#3d6a9f"));
        winRankingButton.setForeground(Color.WHITE);
        winRankingButton.setFocusPainted(false);
        winRankingButton.setBorderPainted(false);
        winRankingButton.addActionListener(e -> {
            ClientRun.socketHandler.getRankWin();
            dispose();
        });

        JButton historyButton = new JButton("Lịch Sử Chơi");
        historyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        historyButton.setBackground(Color.decode("#3d6a9f"));
        historyButton.setForeground(Color.WHITE);
        historyButton.setFocusPainted(false);
        historyButton.setBorderPainted(false);
        historyButton.addActionListener(e -> {
            String username = "Silver";
            ClientRun.socketHandler.getHistory(username);
        });

        // Thêm các nút vào buttonPanel
        buttonPanel.add(homeButton);
        buttonPanel.add(winRankingButton);
        buttonPanel.add(historyButton);

        // Thêm buttonPanel vào backgroundPanel ở phía nam
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm bảng vào BackgroundPanel
        initTable(); // Gọi phương thức initTable để khởi tạo bảng
    }

    private void initTable() {
        String[] columnNames = {"Thứ hạng", "Tên Người Chơi", "Điểm", "Thắng", "Hòa", "Thua"};

        tableModel = new DefaultTableModel(columnNames, 0);
        rankTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Không cho phép chỉnh sửa ô
            }
        };

        // Thiết lập màu nền với độ trong suốt cho bảng
        rankTable.setBackground(new Color(255, 255, 255, 76)); // Màu trắng, alpha = 76 (~0.3)
        rankTable.setOpaque(false);  // Đảm bảo bảng trong suốt
        rankTable.setRowHeight(25);
        rankTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rankTable.setGridColor(new Color(0, 0, 0, 0)); // Bỏ màu lưới (grid) của bảng

        // Thiết lập màu header
        rankTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        rankTable.getTableHeader().setBackground(rankTable.getBackground()); // Đặt nền của header giống màu nền bảng
        rankTable.getTableHeader().setForeground(Color.BLACK); // Đặt màu chữ là màu đen

        // Thiết lập căn giữa các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < rankTable.getColumnCount(); i++) {
            rankTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Thiết lập JScrollPane với độ trong suốt và kích thước cố định
        JScrollPane scrollPane = new JScrollPane(rankTable);
        scrollPane.setPreferredSize(new Dimension(800, rankTable.getRowHeight() * tableModel.getRowCount() + 40)); // Chiều ngang bảng 800px
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Đảm bảo vùng viewport trong suốt
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Bỏ border của JScrollPane

        // Panel bao quanh bảng và căn giữa
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);  // Đảm bảo panel trong suốt
        tablePanel.setPreferredSize(new Dimension(800, 400)); // Đặt chiều ngang của panel 800px
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel ngoài cùng để căn giữa bảng
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);  // Đảm bảo panel ngoài cùng trong suốt
        centerPanel.add(tablePanel);

        // Thêm bảng vào BackgroundPanel
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
    }

    public void updateRankDisplay(String rankData) {
        tableModel.setRowCount(0);

        String[] data = rankData.split(";");
        for (int i = 0; i < data.length; i++) {
            String[] userData = data[i].split(":");
            if (userData.length >= 5) {
                Object[] row = {
                    i,
                    userData[0],
                    Float.parseFloat(userData[1]),
                    Integer.parseInt(userData[2]),
                    Integer.parseInt(userData[3]),
                    Integer.parseInt(userData[4])
                };
                tableModel.addRow(row);
            }
        }
    }

    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            URL imagePath = getClass().getResource("/assets/history.jpg");
            if (imagePath != null) {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } else {
                System.err.println("Ảnh không tìm thấy. Kiểm tra đường dẫn của bạn.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
