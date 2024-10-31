package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import run.ClientRun;

public class MessageView extends JFrame {
    private JTextField tfMessage;
    private JButton btnSend;
    private JScrollPane jScrollPane1;
    private JPanel contentPanel; // Sử dụng JPanel thay vì JTextPane
    private JLabel infoUserChat;
    private JButton btnLeaveChat;
    private JLabel backgroundLabel;
    private String userChat = "";

    public MessageView() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        // Close window event
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(MessageView.this,
                        "Bạn muốn rời khỏi cuộc trò chuyện?", "Rời?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    ClientRun.socketHandler.leaveChat(userChat);
                    dispose();
                }
            }
        });
    }

    private void initComponents() {
        tfMessage = new JTextField();
        btnSend = new JButton();
        contentPanel = new JPanel(); // Khởi tạo JPanel
        jScrollPane1 = new JScrollPane(contentPanel); // Sử dụng JPanel cho JScrollPane
        infoUserChat = new JLabel();
        btnLeaveChat = new JButton();

        // Set background image
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/assets/infoview.jpg"));
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(10, 60, 350, 500);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        // tfMessage setup
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    eventSendMessage();
                }
            }
        });

        // btnSend setup
        btnSend.setText("Gửi");
        btnSend.setBackground(Color.decode("#3d6a9f"));
        btnSend.setForeground(Color.WHITE);
        btnSend.setPreferredSize(new Dimension(70, 30));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventSendMessage();
            }
        });

        // contentPanel setup
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout
        contentPanel.setOpaque(false); // Để JPanel trong suốt

        // infoUserChat setup
        infoUserChat.setFont(new Font("Tahoma", Font.BOLD, 18));
        infoUserChat.setText("Trò chuyện với:");
        infoUserChat.setForeground(Color.decode("#3d6a9f"));
        
        // btnLeaveChat setup
        btnLeaveChat.setBackground(Color.decode("#3d6a9f"));
        btnLeaveChat.setForeground(Color.WHITE);
        btnLeaveChat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLeaveChat.setText("Rời đi");
        btnLeaveChat.setPreferredSize(new Dimension(70, 30));
        btnLeaveChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ClientRun.socketHandler.leaveChat(userChat);
                dispose();
            }
        });

        // Layout setup
        setLayout(null);
        infoUserChat.setBounds(10, 10, 400, 36);
        btnLeaveChat.setBounds(250, 10, 100, 36);
        tfMessage.setBounds(40, 500, 280, 40);
        btnSend.setBounds(250, 480, 100, 40);

        // Add components
        add(infoUserChat);
        add(btnLeaveChat);
        add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Set up JScrollPane to contain contentPanel
        jScrollPane1.setBounds(10, 20, 330, 350); // Kích thước và vị trí
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Hiển thị thanh cuộn khi cần

        // Add JScrollPane to backgroundLabel
        backgroundLabel.add(jScrollPane1);
        jScrollPane1.setViewportView(contentPanel); // Đặt contentPanel vào JScrollPane

        add(tfMessage);
//        add(btnSend);

        setSize(375, 600);
        setLocationRelativeTo(null);
    }

    public void setInfoUserChat(String username) {
        userChat = username;
        infoUserChat.setText("Trò chuyện với: " + username);
    }

    public void setContentChat(String chat) {
        // Tạo một JLabel cho tin nhắn
        JLabel messageLabel = new JLabel(chat);
        messageLabel.setBackground(new Color(240, 240, 240, 200)); // Màu nền với độ trong suốt
        messageLabel.setOpaque(true); // Để JLabel có nền
        messageLabel.setPreferredSize(new Dimension(250, 30)); // Kích thước tối đa
         messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Thêm tin nhắn vào contentPanel
        contentPanel.add(Box.createVerticalStrut(5)); // Khoảng cách trên
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createVerticalStrut(5)); // Khoảng cách dưới

        // Cập nhật giao diện
        contentPanel.revalidate();
        contentPanel.repaint();

        // Cuộn xuống cuối khi thêm tin nhắn mới
        JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public void eventSendMessage() {
        String message = tfMessage.getText().trim();
        if (!message.equals("")) {
            ClientRun.socketHandler.sendMessage(userChat, message);
//            setContentChat("Bạn: " + message); // Hiển thị tin nhắn trong chat
            tfMessage.setText("");
        }
        tfMessage.grabFocus();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MessageView().setVisible(true);
            }
        });
    }
}