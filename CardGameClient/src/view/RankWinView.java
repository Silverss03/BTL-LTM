package view;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import run.ClientRun;

public class RankWinView extends JFrame {

    private JTable rankTable;
    private DefaultTableModel tableModel;

    public RankWinView() {
        setupFrame();  // Cấu hình JFrame
        initTable();   // Khởi tạo bảng xếp hạng
    }

    // Cấu hình các thuộc tính của JFrame
    private void setupFrame() {
        setTitle("Bảng Xếp Hạng");

        // Thiết lập full màn hình và căn giữa cửa sổ
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Đặt màu nền của JFrame là màu trắng
        getContentPane().setBackground(Color.WHITE);

        // Tạo panel chứa nút và tiêu đề
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);  // Nền trắng cho panel
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));  // Cách các cạnh 10px

        // Tạo nút Home
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setBackground(Color.decode("#3d6a9f"));  // Nền màu xanh #3d6a9f
        homeButton.setForeground(Color.WHITE);  // Chữ trắng
        homeButton.setFocusPainted(false);  // Bỏ viền khi chọn
        homeButton.setBorderPainted(false);  // Bỏ viền nút
        homeButton.addActionListener(e -> dispose());  // Đóng cửa sổ

        // Tạo nút BXH theo trận thắng
        JButton winRankingButton = new JButton("Xếp hạng theo điểm");
        winRankingButton.setFont(new Font("Arial", Font.BOLD, 14));
        winRankingButton.setBackground(Color.decode("#3d6a9f"));  // Nền màu xanh #3d6a9f
        winRankingButton.setForeground(Color.WHITE);  // Chữ trắng
        winRankingButton.setFocusPainted(false);  // Bỏ viền khi chọn
        winRankingButton.setBorderPainted(false);  // Bỏ viền nút
        winRankingButton.addActionListener(e -> {
            ClientRun.socketHandler.getRank();
            dispose();
        });

        // Panel chứa các nút, đặt ở trên cùng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);  // Nền trắng cho panel
        buttonPanel.add(homeButton);
        buttonPanel.add(winRankingButton);  // Thêm nút BXH vào panel

        // Thêm buttonPanel vào topPanel
        topPanel.add(buttonPanel, BorderLayout.NORTH);

        // Thêm tiêu đề cho bảng xếp hạng
        JLabel titleLabel = new JLabel("Bảng Xếp Hạng Người Chơi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#3d6a9f"));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); 

        // Thêm tiêu đề vào topPanel
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Thêm topPanel vào JFrame
        add(topPanel, BorderLayout.NORTH);
    }

    // Khởi tạo bảng và JScrollPane để hiển thị dữ liệu
    private void initTable() {
        String[] columnNames = {"Xếp hạng", "Tên Người Chơi", "Điểm", "Thắng", "Hòa", "Thua"};

        tableModel = new DefaultTableModel(columnNames, 0);
        rankTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Không cho phép chỉnh sửa ô
            }
        };

        rankTable.setRowHeight(25);
        rankTable.setFont(new Font("Arial", Font.PLAIN, 12));
        rankTable.setBackground(Color.WHITE);
        rankTable.setGridColor(Color.LIGHT_GRAY);
        rankTable.setBorder(new LineBorder(Color.DARK_GRAY, 1));

        rankTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        rankTable.getTableHeader().setBackground(Color.decode("#3d6a9f"));
        rankTable.getTableHeader().setForeground(Color.WHITE);

        rankTable.setShowVerticalLines(false);
        rankTable.setShowHorizontalLines(false);
        rankTable.setIntercellSpacing(new Dimension(0, 0));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < rankTable.getColumnCount(); i++) {
            rankTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        rankTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        rankTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        rankTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        rankTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        rankTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        rankTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(rankTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
         
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.8), 600));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(tablePanel);
        add(centerPanel, BorderLayout.CENTER);
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
}
