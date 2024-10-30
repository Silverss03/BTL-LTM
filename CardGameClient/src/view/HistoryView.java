package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.border.EmptyBorder;

public class HistoryView extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private BackgroundPanel backgroundPanel;

    public HistoryView() {
        setupFrame();
        initTable();
        setLocationRelativeTo(null); // Căn giữa màn hình
    }

    private void setupFrame() {
        setTitle("Lịch Sử Chơi Game");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
        
        JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setOpaque(false);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    JLabel titleLabel = new JLabel("Lịch sử người chơi", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
    titleLabel.setForeground(Color.decode("#3d6a9f"));
    titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

    topPanel.add(titleLabel, BorderLayout.CENTER);
    backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Tạo panel cho nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0)); // Tăng cách bottom lên 40px

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        homeButton.setBackground(Color.decode("#3d6a9f"));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(homeButton); // Thêm nút vào buttonPanel
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); // Đặt buttonPanel ở dưới cùng
    }

    private void initTable() {
        String[] columnNames = {"Đối thủ", "Thời gian", "Kết quả"};

        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };

        historyTable.setBackground(new Color(255, 255, 255, 76));
        historyTable.setOpaque(false);
        historyTable.setRowHeight(25);
        historyTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        historyTable.setGridColor(new Color(0, 0, 0, 0));

        historyTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        historyTable.getTableHeader().setBackground(historyTable.getBackground());
        historyTable.getTableHeader().setForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < historyTable.getColumnCount(); i++) {
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Tạo tiêu đề cho bảng
        JLabel titleLabel = new JLabel("Lịch Sử Chơi Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // Màu chữ tiêu đề
        titleLabel.setOpaque(false); // Để tiêu đề trong suốt

        // Panel bao quanh bảng
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.add(titleLabel, BorderLayout.NORTH); // Đặt tiêu đề ở trên cùng
        tablePanel.add(historyTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(historyTable, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(tablePanel);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER); // Thêm bảng vào BackgroundPanel
    }

    public void updateHistoryDisplay(String history) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        String[] lines = history.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String[] matchDetails = parts[0].replace("Match with ", "").split(" on ");
                String opponent = matchDetails[0].trim();
                String date = matchDetails.length > 1 ? matchDetails[1].trim() : "N/A";
                String result = parts[1].trim();
                
                // Đảm bảo chỉ thêm kết quả là "thắng" hoặc "thua" vào cột resultMatch
                if (result.equals("Thắng") || result.equals("Thua")) {
                    tableModel.addRow(new Object[]{opponent, date, result});
                }
            }
        }

        // Cập nhật chiều cao của bảng dựa trên số lượng hàng
        int rowCount = tableModel.getRowCount();
        int tableHeight = rowCount * historyTable.getRowHeight();
        historyTable.setPreferredSize(new Dimension(600, tableHeight)); // Chiều ngang bảng là 600px
    }

    public void onReceiveHistory(String received) {
        StringBuilder historyDisplay = new StringBuilder();
        String[] data = received.split(";");

        if (data.length > 0 && data[0].equals("success")) {
            for (int i = 1; i < data.length; i += 3) {
                if (i + 2 < data.length) {
                    String opponent = data[i];
                    String date = data[i + 1];
                    String result = data[i + 2];
                    historyDisplay.append("Match with ").append(opponent)
                                  .append(" on ").append(date).append(": ")
                                  .append(result.equals("thắng") ? "Thắng" : "Thua").append("\n");
                }
            }
        } else {
            historyDisplay.append("No game history found for this user.");
        }
        updateHistoryDisplay(historyDisplay.toString());
    }

    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/history.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Không thể tải ảnh từ đường dẫn: " + "/assets/history.jpg");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistoryView historyView = new HistoryView();
            historyView.setVisible(true);

            // Test dữ liệu nhận về (giả lập)
            String testData = "success;Opponent1;12/10/2024;thắng;Opponent2;15/10/2024;thua;Opponent3;18/10/2024;thắng";
            historyView.onReceiveHistory(testData);
        });
    }
}
