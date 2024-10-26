package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import model.Card;
import run.ClientRun ; 
import helper.* ; 
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.table.DefaultTableModel;

public class GameView extends javax.swing.JFrame {
    String competitor = "";
    CountDownTimer waitingClientTimer;
    boolean answer = false;
    Random random = new Random();
    ArrayList<Card> hiddenCard;
    ArrayList<Boolean> cardFlipped;  // To track if each card is flipped
    Boolean yourTurn = false ;
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    int cardWidth = 100;
    int cardHeight = 154;
    int selectedCardIndex = -1;  // To track selected card
    int player1Score = 0 ; 
    int player2Score = 0 ;
    int maxCards = 3 ; 
    int opponentCards = 3 ; 

    JButton hitButton = new JButton("Rút");
    JButton stayButton = new JButton("Bỏ");
    JButton yesButton = new JButton("Có") ;
    JButton noButton = new JButton("Không") ;
    JButton btnStart = new JButton("Chơi") ;
    JLabel lbWaiting = new JLabel("Đợi chủ phòng bắt đầu....");
    JPanel panelPlayAgain = new JPanel() ; 
    JLabel lbResult = new JLabel("Bạn muốn chơi lại không?")  ;
    JLabel infoPlayer = new JLabel("Đang chơi game cùng:") ;
    JLabel lbWaitingTimer = new JLabel();
    JPanel player1Panel = new JPanel();
    JPanel player2Panel = new JPanel();
    JPanel startPanel = new JPanel() ;
    JLabel nameLabel = new JLabel("Bạn");
    JLabel scoreLabel = new JLabel("Điểm: " + player1Score);
    JLabel player2NameLabel = new JLabel("");
    JLabel player2ScoreLabel = new JLabel("Điểm: " + player2Score);
    JLabel playerTurn = new JLabel("Đợi người chơi khác rút bài") ;
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image hiddenCardImg = new ImageIcon(getClass().getResource("/run/cards/BACK.png")).getImage();
            for (int i = 0; i < 12; i++) {
                int cardX ;
                int cardY ;
                if(i < 6){
                    cardX = 40 + 140 * i;
                    cardY = 40;
                }
                else{
                    cardX = 40 + 140 * (i - 6) ;
                    cardY = 214 ;
                }
                // Highlight the selected card
                if (i == selectedCardIndex) {
                    g.setColor(Color.YELLOW);  // Highlight color
                    g.fillRect(cardX - 5, cardY - 5, cardWidth + 10, cardHeight + 10);  // Draw highlighted border
                }

                // Draw the front of the card if flipped, otherwise draw the back
                if (cardFlipped.get(i)) {
                    Image frontCardImg = new ImageIcon(getClass().getResource(hiddenCard.get(i).getImagePath())).getImage();
                    g.drawImage(frontCardImg, cardX, cardY, cardWidth, cardHeight, null);
                } else {
                    g.drawImage(hiddenCardImg, cardX, cardY, cardWidth, cardHeight, null);
                }
            }
        }
    };
    
    public GameView() {
        setupFrame() ; 
//        frame.add(panelPlayAgain) ; 
//        frame.add(lbResult) ;
//        frame.add(infoLayer) ; 
//        frame.add(lbWaitingTimer) ; 
//
//        //panelPlayAgain.setBorder(javax.swing.BorderFactory.createTitledBorder("Question?"));
//        
//        gamePanel.repaint();
        player2Panel.setVisible(false);
        gamePanel.setVisible(false);
        player1Panel.setVisible(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(GameView.this, "Thoát game sẽ bị tính là thua, bạn vẫn muốn thoát?", "LEAVE GAME", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                    ClientRun.socketHandler.leaveGame(competitor);
                    ClientRun.socketHandler.setRoomIdPresent(null);
                    dispose();
                } 
            }
        });
    }

    public void setupFrame(){
        setTitle("Card Game") ; 
        setSize(1000, 800) ;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
         
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        
        // Add spacing and alignment for the components (optional, for better layout control)
        infoPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbWaiting.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel in the desired order
        startPanel.add(infoPlayer);  // Top
        startPanel.add(Box.createVerticalStrut(10)); // Optional spacing between components
        startPanel.add(lbWaiting);  // Middle
        startPanel.add(Box.createVerticalStrut(10)); // Optional spacing
        startPanel.add(btnStart);  // Bottom
        startPanel.setBounds(350, 200, 300, 150);
        add(startPanel) ;
        
        lbResult.setBounds(450, 10, 300, 100);
        panelPlayAgain.setBounds(500, 400, 300, 100);
        panelPlayAgain.add(lbWaitingTimer) ;
        panelPlayAgain.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPlayAgain.add(yesButton) ; 
        panelPlayAgain.add(noButton) ;
        add(lbResult ) ; 
        add(panelPlayAgain) ; 
        
        gamePanel.setLayout(null);
        gamePanel.setBackground(new Color(53, 101, 77));
        add(gamePanel, BorderLayout.CENTER) ;
  
        ImageIcon playerIcon = new ImageIcon(getClass().getResource("/run/blank-user.png"));
        Image image = playerIcon.getImage();
        Image scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
        player1Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel iconLabel = new JLabel(scaledIcon);
        player1Panel.add(iconLabel);
        player1Panel.add(Box.createVerticalStrut(5));
        player1Panel.add(nameLabel);
        player1Panel.add(Box.createVerticalStrut(5));
        player1Panel.add(scoreLabel);
        add(player1Panel, BorderLayout.SOUTH) ;
        
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
        player2Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel player2IconLabel = new JLabel(scaledIcon);
        
        player2Panel.add(player2IconLabel);
        player2Panel.add(Box.createVerticalStrut(5));
        player2Panel.add(player2NameLabel);
        player2Panel.add(Box.createVerticalStrut(5));
        player2Panel.add(player2ScoreLabel);
        add(player2Panel, BorderLayout.NORTH) ;
        
        hitButton.setBounds(400, 440, 80, 30);
        stayButton.setBounds(500, 440, 80, 30);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        gamePanel.add(hitButton) ; 
        gamePanel.add(stayButton) ;
        playerTurn.setBounds(400, 10, 200, 30);
        playerTurn.setForeground(Color.white);
        gamePanel.add(playerTurn) ; 
        
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < 12; i++) {
                    int cardX ;
                    int cardY ;
                    if(i < 6){
                        cardX = 40 + 140 * i;
                        cardY = 40;
                    }
                    else{
                        cardX = 40 + 140 * (i - 6) ;
                        cardY = 214 ;
                    }
                    if (e.getX() >= cardX && e.getX() <= cardX + cardWidth && e.getY() >= cardY && e.getY() <= cardY + cardHeight && maxCards > 0 && cardFlipped.get(i) == false && yourTurn) {
                        selectedCardIndex = i;
                        hitButton.setEnabled(true);
                        stayButton.setEnabled(true);
                        gamePanel.repaint();  // Refresh to show the highlight
                        break;
                    }
                }
            }
        });

        hitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hitButtonActionPerformed(evt);
            }
        });

        stayButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        });
        

    }
    public void setDeck(String deck) {
        String[] splitted = deck.split(" ") ;
        hiddenCard = new ArrayList<>();
        cardFlipped = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) {
            String[] arr = splitted[i].split("-") ; 
            Card card = new Card(arr[0], arr[1]) ;
            hiddenCard.add(card);
            cardFlipped.add(false);  // Initialize each card as unflipped
        }
    }
    
    public void setWaitingRoom () {
        gamePanel.setVisible(false);
        btnStart.setVisible(false);
        lbWaiting.setText("Đợi người chơi khác...");
        waitingReplyClient();
    }
    
    public void showAskPlayAgain (String msg) {
        panelPlayAgain.setVisible(true);
        player1Panel.setVisible(false);
        player2Panel.setVisible(false);
        lbResult.setText(msg);
        lbResult.setVisible(true);
    }
    
    public void hideAskPlayAgain () {
        panelPlayAgain.setVisible(false);
    }
    
    public void setInfoPlayer (String username) {
        competitor = username;
        lbResult.setVisible(false);
        panelPlayAgain.setVisible(false);
        player2NameLabel.setText(username);
        infoPlayer.setText("Đang chơi game cùng " + username);
    }
    
    public void setStateHostRoom () {
        answer = false;
        btnStart.setVisible(true);
        lbWaiting.setVisible(false);
        lbResult.setVisible(false);
        panelPlayAgain.setVisible(false) ;
        yourTurn = true ; 
        playerTurn.setText("Lượt của bạn, hãy rút bài!");
    }
    
    public void setStateUserInvited () {
        answer = false;
        btnStart.setVisible(false);
        lbWaiting.setVisible(true);
    }
     
    public void setStartGame () {
        answer = false;
        maxCards = 3 ; 
        opponentCards = 3 ; 
        player1Score = 0 ; 
        player2Score = 0 ;
        startPanel.setVisible(false);
        lbResult.setVisible(false);
        panelPlayAgain.setVisible(false);
        gamePanel.setVisible(true);
        player1Panel.setVisible(true);
        player2Panel.setVisible(true);
    }
    
    public void waitingReplyClient () {
        waitingClientTimer = new CountDownTimer(10);
        waitingClientTimer.setTimerCallBack(
                null,
                (Callable) () -> {
                    lbWaitingTimer.setText("" + CustomDateTimeFormatter.secondsToMinutes(waitingClientTimer.getCurrentTick()));
                    if (lbWaitingTimer.getText().equals("00:00") && answer == false) {
                        hideAskPlayAgain();
                    }
                    return null;
                },
                1
        );
    }
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        ClientRun.socketHandler.startGame(competitor);
    }
    
    private void hitButtonActionPerformed(java.awt.event.ActionEvent evt){
        if (selectedCardIndex != -1) {
            cardFlipped.set(selectedCardIndex, true);  // Mark the card as flipped
            gamePanel.repaint();  // Refresh the panel to flip the card

            Card cardDrawn = hiddenCard.get(selectedCardIndex) ; 
            int score = cardDrawn.getValue() ; 
            player1Score +=  score ; 
            scoreLabel.setText("Điểm " + player1Score);
            yourTurn = false ; 
            playerTurn.setText("Đợi người chơi khác rút bài");
            gamePanel.repaint();

            System.out.println("Card " + (selectedCardIndex + 1) + " flipped.");

            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
            ClientRun.socketHandler.cardFlipped(competitor, selectedCardIndex, score) ; 
            selectedCardIndex = -1;  // Reset selection
            maxCards = maxCards - 1; 
            if(opponentCards == 0 ){
                // Schedule a task to run after 2 seconds
                scheduler.schedule(() -> {
                    ClientRun.socketHandler.submitResult(competitor, player1Score);
                    // After the task, shut down the scheduler
                    scheduler.shutdown();
                }, 2, TimeUnit.SECONDS);
            }
        }
    }
    
    public void flipCard(int cardIndex, int point){
        cardFlipped.set(cardIndex, true);  // Mark the card as flipped
        opponentCards -= 1 ;
        player2Score += point ; 
        player2ScoreLabel.setText("Điểm " + player2Score);
        yourTurn = true ; 
        playerTurn.setText("Lượt của bạn, hãy rút bài!");
        gamePanel.repaint() ;
        if(maxCards == 0){
            scheduler.schedule(() -> {
                ClientRun.socketHandler.submitResult(competitor, player1Score);
                // After the task, shut down the scheduler
                scheduler.shutdown();
            }, 2, TimeUnit.SECONDS);
        } 
    }
    
    public void afterSubmit() {
        gamePanel.setVisible(false);
        lbWaiting.setVisible(true);
        player1Panel.setVisible(false);
        player2Panel.setVisible(false);
        lbWaiting.setText("Waiting result from server...");
        
    }
} 

