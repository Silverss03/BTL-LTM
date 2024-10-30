package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InfoPlayerView extends JFrame {

    private JTable infoTable;
    private DefaultTableModel tableModel;
    private JButton okButton;

    public InfoPlayerView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Thông tin người chơi");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(400, 500);

        // Thêm ảnh nền
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/assets/infoview.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 400, 500);

        // Tạo bảng
        tableModel = new DefaultTableModel(0, 2);
        infoTable = new JTable(tableModel);
        infoTable.setTableHeader(null); // Bỏ dòng tiêu đề
        infoTable.setOpaque(false);
        infoTable.setBackground(new Color(255, 255, 255, 200)); // Đặt nền mờ
        infoTable.setShowGrid(false); // Hiển thị đường viền
        infoTable.setIntercellSpacing(new Dimension(5, 5)); // Loại bỏ không gian giữa các ô

        // Giữ chiều cao mỗi dòng
        infoTable.setRowHeight(35); // Chiều cao mỗi dòng là 35

        // Căn giữa nội dung trong các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        infoTable.setDefaultRenderer(Object.class, centerRenderer);

        // Cài đặt chiều rộng cho các cột
        infoTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Chiều rộng cột 1
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Chiều rộng cột 2

        // Sử dụng JScrollPane
        JScrollPane scrollPane = new JScrollPane(infoTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền của JScrollPane
        scrollPane.setPreferredSize(new Dimension(200, 150)); // Thay đổi chiều ngang của JScrollPane

        // Tạo nút OK
        okButton = new JButton("OK");
        okButton.setBackground(new Color(61, 106, 159)); // Đặt màu nền của nút
        okButton.setForeground(Color.WHITE); // Đặt màu chữ của nút
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> dispose());

        // Tạo tiêu đề và căn giữa
        JLabel titleLabel = new JLabel("Thông tin người chơi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Color.decode("#3d6a9f"));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel chứa UI
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Thêm khoảng cách giữa tiêu đề và bảng
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20)); // Thêm khoảng cách 20px
        contentPanel.add(scrollPane);
        contentPanel.add(okButton);
        contentPanel.setBounds(50, 50, 300, 400);

        // Sử dụng JLayeredPane để xếp chồng các thành phần lên ảnh nền
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 500));
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(contentPanel, JLayeredPane.PALETTE_LAYER);

        setContentPane(layeredPane);
        setLocationRelativeTo(null);
    }

    public void setInfoUser(String username, String score, String win, String draw, String lose, String status) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        tableModel.addRow(new Object[]{"Username", username});
        tableModel.addRow(new Object[]{"Score", score});
        tableModel.addRow(new Object[]{"Win", win});
        tableModel.addRow(new Object[]{"Draw", draw});
        tableModel.addRow(new Object[]{"Lose", lose});
        tableModel.addRow(new Object[]{"Status", status});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InfoPlayerView infoPlayerView = new InfoPlayerView();
            infoPlayerView.setVisible(true);

            // Test dữ liệu
            infoPlayerView.setInfoUser("Player1", "1500", "10", "5", "3", "Active");
        });
    }
}
