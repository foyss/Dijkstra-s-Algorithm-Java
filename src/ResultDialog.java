// Name:        Foysal Ahmed

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ResultDialog extends JFrame {

    private JPanel contentPane;
    private JPanel EucPanel;
    private JPanel ChePanel;
    private JPanel ManPanel;

    private int gridSize;
    private int startNode;
    private int endNode;
    private double eucPathLength;
    private double chePathLength;
    private double manPathLength;
    private HashMap<Integer, Integer> parentPathEuc;
    private HashMap<Integer, Integer> parentPathChe;
    private HashMap<Integer, Integer> parentPathMan;
    private HashMap<Integer, Integer> blockedPath;

    private JLabel lblTotalDistanceTravelled;
    private JLabel eucDistLabel;
    private JLabel lblTotalSteps;
    private JLabel eucStepsLabel;
    private JLabel cheDistLabel;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel cheStepsLabel;
    private JLabel manDistLabel;
    private JLabel label_5;
    private JLabel label_6;
    private JLabel manStepsLabel;

    private void paintEucPanel(Graphics g) {
        try {
            int boxWidth = (EucPanel.getWidth()) / gridSize;
            int offset = (EucPanel.getWidth()) - (boxWidth * gridSize);

            EucPanel.setSize(boxWidth * gridSize, boxWidth * gridSize);

            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    int gridPosition = subIndex - (subIndex / gridSize) * gridSize + (index * gridSize);
                    g.setColor(Color.BLACK);
                    g.fillRect(boxWidth * subIndex, boxWidth * index, boxWidth, boxWidth);
                    if (blockedPath.containsKey(gridPosition)) {
                        g.setColor(Color.RED);
                    } else if (parentPathEuc.containsKey(gridPosition)) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect((boxWidth * subIndex) + 1, (boxWidth * index) + 1, boxWidth - 1, boxWidth - 1);

                    // also draw dot or cross for start or end node
                    ClassLoader loader = getClass().getClassLoader();
                    File startImgFile = new File("resources/circle.png");
                    if (gridPosition == startNode) {
                        Image startImg = ImageIO.read(startImgFile);
                        g.drawImage(startImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                    File endImgFile = new File("resources/multiply.png");
                    if (gridPosition == endNode) {
                        Image endImg = ImageIO.read(endImgFile);
                        g.drawImage(endImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                }
            }
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("####0.00");
        eucDistLabel.setText(df.format(this.eucPathLength));
        eucStepsLabel.setText(Integer.toString(parentPathEuc.size() - 1));
    }

    private void paintChePanel(Graphics g) {
        try {
            int boxWidth = (ChePanel.getWidth()) / gridSize;
            int offset = (ChePanel.getWidth()) - (boxWidth * gridSize);

            ChePanel.setSize(boxWidth * gridSize, boxWidth * gridSize);

            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    int gridPosition = subIndex - (subIndex / gridSize) * gridSize + (index * gridSize);
                    g.setColor(Color.BLACK);
                    g.fillRect(boxWidth * subIndex, boxWidth * index, boxWidth, boxWidth);
                    if (blockedPath.containsKey(gridPosition)) {
                        g.setColor(Color.RED);
                    } else if (parentPathChe.containsKey(gridPosition)) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect((boxWidth * subIndex) + 1, (boxWidth * index) + 1, boxWidth - 1, boxWidth - 1);

                    // also draw dot or cross for start or end node
                    ClassLoader loader = getClass().getClassLoader();
                    File startImgFile = new File("resources/circle.png");
                    if (gridPosition == startNode) {
                        Image startImg = ImageIO.read(startImgFile);
                        g.drawImage(startImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                    File endImgFile = new File("resources/multiply.png");
                    if (gridPosition == endNode) {
                        Image endImg = ImageIO.read(endImgFile);
                        g.drawImage(endImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("####0.00");
            cheDistLabel.setText(df.format(this.chePathLength));
            cheStepsLabel.setText(Integer.toString(parentPathChe.size() - 1));
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    private void paintManPanel(Graphics g) {
        try {
            int boxWidth = (ManPanel.getWidth()) / gridSize;
            int offset = (ManPanel.getWidth()) - (boxWidth * gridSize);

            ManPanel.setSize(boxWidth * gridSize, boxWidth * gridSize);

            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    int gridPosition = subIndex - (subIndex / gridSize) * gridSize + (index * gridSize);
                    g.setColor(Color.BLACK);
                    g.fillRect(boxWidth * subIndex, boxWidth * index, boxWidth, boxWidth);
                    if (blockedPath.containsKey(gridPosition)) {
                        g.setColor(Color.RED);
                    } else if (parentPathMan.containsKey(gridPosition)) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect((boxWidth * subIndex) + 1, (boxWidth * index) + 1, boxWidth - 1, boxWidth - 1);

                    // also draw dot or cross for start or end node
                    ClassLoader loader = getClass().getClassLoader();
                    File startImgFile = new File("resources/circle.png");
                    if (gridPosition == startNode) {
                        Image startImg = ImageIO.read(startImgFile);
                        g.drawImage(startImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                    File endImgFile = new File("resources/multiply.png");
                    if (gridPosition == endNode) {
                        Image endImg = ImageIO.read(endImgFile);
                        g.drawImage(endImg, (boxWidth * subIndex) + 4, (boxWidth * index) + 4, boxWidth - 8, boxWidth - 8, null);
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("####0.00");
            manDistLabel.setText(df.format(this.manPathLength));
            manStepsLabel.setText(Integer.toString(parentPathMan.size() - 1));
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    /**
     * Create the frame.
     */
    public ResultDialog(int gridSize, int startNode, int endNode, HashMap<Integer, Integer> parentPathEuc, double eucPathLength, HashMap<Integer, Integer> parentPathChe, double chePathLength, HashMap<Integer, Integer> parentPathMan, double manPathLength, HashMap<Integer, Integer> blockedPath) {
        setResizable(false);
        this.gridSize = gridSize;
        this.startNode = startNode;
        this.endNode = endNode;
        this.parentPathEuc = parentPathEuc;
        this.blockedPath = blockedPath;
        this.eucPathLength = eucPathLength;
        this.parentPathChe = parentPathChe;
        this.chePathLength = chePathLength;
        this.parentPathMan = parentPathMan;
        this.manPathLength = manPathLength;

        setTitle("Resultant Paths");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 835, 430);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);

        EucPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                paintEucPanel(g);
            }
        };
        EucPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        EucPanel.setBounds(20, 55, 250, 250);
        layeredPane.add(EucPanel);

        ChePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                paintChePanel(g);
            }
        };
        ChePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        ChePanel.setBounds(280, 55, 250, 250);
        layeredPane.add(ChePanel);

        ManPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                paintManPanel(g);
            }
        };
        ManPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        ManPanel.setBounds(540, 55, 250, 250);
        layeredPane.add(ManPanel);

        JLabel lblNewLabel = new JLabel("Euclidean Distance");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        lblNewLabel.setBounds(20, 32, 250, 14);
        layeredPane.add(lblNewLabel);

        JLabel lblChebyshevDistance = new JLabel("Chebyshev Distance");
        lblChebyshevDistance.setHorizontalAlignment(SwingConstants.CENTER);
        lblChebyshevDistance.setFont(new Font("Calibri", Font.BOLD, 16));
        lblChebyshevDistance.setBounds(280, 30, 250, 14);
        layeredPane.add(lblChebyshevDistance);

        JLabel lblManhattanDistance = new JLabel("Manhattan Distance");
        lblManhattanDistance.setHorizontalAlignment(SwingConstants.CENTER);
        lblManhattanDistance.setFont(new Font("Calibri", Font.BOLD, 16));
        lblManhattanDistance.setBounds(540, 30, 250, 14);
        layeredPane.add(lblManhattanDistance);

        lblTotalDistanceTravelled = new JLabel("Total Cost");
        lblTotalDistanceTravelled.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalDistanceTravelled.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblTotalDistanceTravelled.setBounds(30, 316, 136, 17);
        layeredPane.add(lblTotalDistanceTravelled);

        eucDistLabel = new JLabel("New label");
        eucDistLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        eucDistLabel.setBounds(176, 316, 80, 14);
        layeredPane.add(eucDistLabel);

        lblTotalSteps = new JLabel("Total Steps");
        lblTotalSteps.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalSteps.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblTotalSteps.setBounds(30, 332, 136, 17);
        layeredPane.add(lblTotalSteps);

        eucStepsLabel = new JLabel("New label");
        eucStepsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        eucStepsLabel.setBounds(176, 332, 80, 14);
        layeredPane.add(eucStepsLabel);

        cheDistLabel = new JLabel("New label");
        cheDistLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        cheDistLabel.setBounds(436, 316, 80, 14);
        layeredPane.add(cheDistLabel);

        label_1 = new JLabel("Total Cost");
        label_1.setHorizontalAlignment(SwingConstants.RIGHT);
        label_1.setFont(new Font("Calibri", Font.PLAIN, 14));
        label_1.setBounds(290, 316, 136, 17);
        layeredPane.add(label_1);

        label_2 = new JLabel("Total Steps");
        label_2.setHorizontalAlignment(SwingConstants.RIGHT);
        label_2.setFont(new Font("Calibri", Font.PLAIN, 14));
        label_2.setBounds(290, 332, 136, 17);
        layeredPane.add(label_2);

        cheStepsLabel = new JLabel("New label");
        cheStepsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        cheStepsLabel.setBounds(436, 332, 80, 14);
        layeredPane.add(cheStepsLabel);

        manDistLabel = new JLabel("New label");
        manDistLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        manDistLabel.setBounds(696, 316, 80, 14);
        layeredPane.add(manDistLabel);

        label_5 = new JLabel("Total Cost");
        label_5.setHorizontalAlignment(SwingConstants.RIGHT);
        label_5.setFont(new Font("Calibri", Font.PLAIN, 14));
        label_5.setBounds(550, 316, 136, 17);
        layeredPane.add(label_5);

        label_6 = new JLabel("Total Steps");
        label_6.setHorizontalAlignment(SwingConstants.RIGHT);
        label_6.setFont(new Font("Calibri", Font.PLAIN, 14));
        label_6.setBounds(550, 332, 136, 17);
        layeredPane.add(label_6);

        manStepsLabel = new JLabel("New label");
        manStepsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        manStepsLabel.setBounds(696, 332, 80, 14);
        layeredPane.add(manStepsLabel);
    }
}
