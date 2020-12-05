// Name:        Foysal Ahmed

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import exceptions.InvalidGridSizeException;
import java.awt.EventQueue;
import javax.swing.UIManager;

public class PathFindingOnSquaredGrid extends JFrame {

    private JPanel contentPane;
    private JPanel gridPanel;

    private int gridSize;
    private int startNode;
    private int endNode;
    private double blockRatio = 0.4; //controls blocked blocks, up to 1
    private HashMap<Integer, Integer> blockedPath = new HashMap<Integer, Integer>();

    private double[][] grid_euc;
    private double[][] grid_che;
    private double[][] grid_man;

    private JLabel lblRandomizedBlockPoints;
    private JSeparator separator;
    private JLabel lblEnterStartNode;
    private JLabel lblEnterEndNode;
    private JTextField startNodeField;
    private JTextField endNodeField;
    private JSeparator separator_1;
    private JButton btnNewButton;
    private JButton cheNewButton;
    private JButton manNewButton;

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    private boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = StdRandom.bernoulli(p);
            }
        }
        return a;
    }

    /**
     * performs euclidean based calculations
     */
    private void euclideanCal() {
        // initialize grid
        grid_euc = new double[gridSize * gridSize][gridSize * gridSize];

        // EUCLIDEAN CALCULATION
        for (int node = 0; node < gridSize * gridSize; node++) {
            int row = node / gridSize;
            int col = node % gridSize;

            // find neighbors
            // for prev row
            if (row - 1 >= 0) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_euc[node][target] = Integer.MAX_VALUE;
                        grid_euc[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = Math.sqrt(Math.pow((col - 1) - col, 2) + Math.pow((row - 1) - row, 2));
                        grid_euc[node][target] = dist;
                        grid_euc[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_euc[node][target] = Integer.MAX_VALUE;
                        grid_euc[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = Math.sqrt(Math.pow((col + 1) - col, 2) + Math.pow((row - 1) - row, 2));
                        grid_euc[node][target] = dist;
                        grid_euc[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row - 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_euc[node][target] = Integer.MAX_VALUE;
                    grid_euc[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = Math.sqrt(Math.pow((col) - col, 2) + Math.pow((row - 1) - row, 2));
                    grid_euc[node][target] = dist;
                    grid_euc[target][node] = dist;
                }
            }

            // for next row
            if (row + 1 < gridSize) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_euc[node][target] = Integer.MAX_VALUE;
                        grid_euc[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = Math.sqrt(Math.pow((col - 1) - col, 2) + Math.pow((row + 1) - row, 2));
                        grid_euc[node][target] = dist;
                        grid_euc[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_euc[node][target] = Integer.MAX_VALUE;
                        grid_euc[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = Math.sqrt(Math.pow((col + 1) - col, 2) + Math.pow((row + 1) - row, 2));
                        grid_euc[node][target] = dist;
                        grid_euc[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row + 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_euc[node][target] = Integer.MAX_VALUE;
                    grid_euc[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = Math.sqrt(Math.pow((col) - col, 2) + Math.pow((row + 1) - row, 2));
                    grid_euc[node][target] = dist;
                    grid_euc[target][node] = dist;
                }
            }

            // for curr row
            if (col - 1 >= 0) {
                // get target node
                int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row) * gridSize;
                // check if target node exists in the blockedPath hashmap
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_euc[node][target] = Integer.MAX_VALUE;
                    grid_euc[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = Math.sqrt(Math.pow((col - 1) - col, 2) + Math.pow((row) - row, 2));
                    grid_euc[node][target] = dist;
                    grid_euc[target][node] = dist;
                }
            }
            if (col + 1 < gridSize) {
                int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_euc[node][target] = Integer.MAX_VALUE;
                    grid_euc[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = Math.sqrt(Math.pow((col + 1) - col, 2) + Math.pow((row) - row, 2));
                    grid_euc[node][target] = dist;
                    grid_euc[target][node] = dist;
                }
            }
            // for curr col
            int target = (col) - ((col) / gridSize) * gridSize + (row) * gridSize;
            if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                grid_euc[node][target] = Integer.MAX_VALUE;
                grid_euc[target][node] = Integer.MAX_VALUE;
            } else {
                double dist = Math.sqrt(Math.pow((col) - col, 2) + Math.pow((row) - row, 2));
                grid_euc[node][target] = dist;
                grid_euc[target][node] = dist;
            }
        }
    }

    /**
     * performs chebyshev based calculations
     */
    private void chebyshevCal() {
        // initialize grid
        grid_che = new double[gridSize * gridSize][gridSize * gridSize];

        // EUCLIDEAN CALCULATION
        for (int node = 0; node < gridSize * gridSize; node++) {

            int row = node / gridSize;
            int col = node % gridSize;

            // find neighbors
            // for prev row
            if (row - 1 >= 0) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_che[node][target] = Integer.MAX_VALUE;
                        grid_che[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 1;
                        grid_che[node][target] = dist;
                        grid_che[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_che[node][target] = Integer.MAX_VALUE;
                        grid_che[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 1;
                        grid_che[node][target] = dist;
                        grid_che[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row - 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_che[node][target] = Integer.MAX_VALUE;
                    grid_che[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_che[node][target] = dist;
                    grid_che[target][node] = dist;
                }
            }

            // for next row
            if (row + 1 < gridSize) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_che[node][target] = Integer.MAX_VALUE;
                        grid_che[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 1;
                        grid_che[node][target] = dist;
                        grid_che[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_che[node][target] = Integer.MAX_VALUE;
                        grid_che[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 1;
                        grid_che[node][target] = dist;
                        grid_che[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row + 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_che[node][target] = Integer.MAX_VALUE;
                    grid_che[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_che[node][target] = dist;
                    grid_che[target][node] = dist;
                }
            }

            // for curr row
            if (col - 1 >= 0) {
                // get target node
                int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row) * gridSize;
                // check if target node exists in the blockedPath hashmap
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_che[node][target] = Integer.MAX_VALUE;
                    grid_che[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_che[node][target] = dist;
                    grid_che[target][node] = dist;
                }
            }
            if (col + 1 < gridSize) {
                int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_che[node][target] = Integer.MAX_VALUE;
                    grid_che[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_che[node][target] = dist;
                    grid_che[target][node] = dist;
                }
            }
            // for curr col
            int target = (col) - ((col) / gridSize) * gridSize + (row) * gridSize;
            if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                grid_che[node][target] = Integer.MAX_VALUE;
                grid_che[target][node] = Integer.MAX_VALUE;
            } else {
                double dist = 1;
                grid_che[node][target] = dist;
                grid_che[target][node] = dist;

            }
        }
        //add quotes

    }

    /**
     * performs manhattan based calculations
     */
    private void manhattanCal() {
        // initialize grid
        grid_man = new double[gridSize * gridSize][gridSize * gridSize];

        // EUCLIDEAN CALCULATION
        for (int node = 0; node < gridSize * gridSize; node++) {
            int row = node / gridSize;
            int col = node % gridSize;

            // find neighbors
            // for prev row
            if (row - 1 >= 0) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_man[node][target] = Integer.MAX_VALUE;
                        grid_man[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 2;
                        grid_man[node][target] = dist;
                        grid_man[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row - 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_man[node][target] = Integer.MAX_VALUE;
                        grid_man[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 2;
                        grid_man[node][target] = dist;
                        grid_man[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row - 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_man[node][target] = Integer.MAX_VALUE;
                    grid_man[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_man[node][target] = dist;
                    grid_man[target][node] = dist;
                }
            }

            // for next row
            if (row + 1 < gridSize) {
                if (col - 1 >= 0) {
                    // get target node
                    int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    // check if target node exists in the blockedPath hashmap
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_man[node][target] = Integer.MAX_VALUE;
                        grid_man[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 2;
                        grid_man[node][target] = dist;
                        grid_man[target][node] = dist;
                    }
                }
                if (col + 1 < gridSize) {
                    int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row + 1) * gridSize;
                    if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                        grid_man[node][target] = Integer.MAX_VALUE;
                        grid_man[target][node] = Integer.MAX_VALUE;
                    } else {
                        double dist = 2;
                        grid_man[node][target] = dist;
                        grid_man[target][node] = dist;
                    }
                }
                // for curr col
                int target = (col) - ((col) / gridSize) * gridSize + (row + 1) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_man[node][target] = Integer.MAX_VALUE;
                    grid_man[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_man[node][target] = dist;
                    grid_man[target][node] = dist;
                }
            }

            // for curr row
            if (col - 1 >= 0) {
                // get target node
                int target = (col - 1) - ((col - 1) / gridSize) * gridSize + (row) * gridSize;
                // check if target node exists in the blockedPath hashmap
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_man[node][target] = Integer.MAX_VALUE;
                    grid_man[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_man[node][target] = dist;
                    grid_man[target][node] = dist;
                }
            }
            if (col + 1 < gridSize) {
                int target = (col + 1) - ((col + 1) / gridSize) * gridSize + (row) * gridSize;
                if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                    grid_man[node][target] = Integer.MAX_VALUE;
                    grid_man[target][node] = Integer.MAX_VALUE;
                } else {
                    double dist = 1;
                    grid_man[node][target] = dist;
                    grid_man[target][node] = dist;
                }
            }
            // for curr col
            int target = (col) - ((col) / gridSize) * gridSize + (row) * gridSize;
            if (blockedPath.containsKey(target) || blockedPath.containsKey(node)) {
                grid_man[node][target] = Integer.MAX_VALUE;
                grid_man[target][node] = Integer.MAX_VALUE;
            } else {
                double dist = 1;
                grid_man[node][target] = dist;
                grid_man[target][node] = dist;
            }
        }
    }

    /**
     * draws preview grid
     */
    private void drawPreviewGrid(Graphics g) {
        try {
            int boxWidth = (gridPanel.getWidth()) / gridSize;
            int offset = (gridPanel.getWidth()) - (boxWidth * gridSize);

            gridPanel.setSize(boxWidth * gridSize, boxWidth * gridSize);

            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    int gridPosition = subIndex - (subIndex / gridSize) * gridSize + (index * gridSize);
                    g.setColor(Color.BLACK);
                    g.fillRect(boxWidth * subIndex, boxWidth * index, boxWidth, boxWidth);
                    if (blockedPath.containsKey(gridPosition)) {
                        g.setColor(Color.RED);
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
    }

    /**
     * performs grid calculations
     */
    private void formulateGrid() {
        try {
            // get randomized bool grid
            boolean[][] randomGrid = random(gridSize, blockRatio);

            // find total num of blockages and add to blockedPath hashmap with value set to max int value (consider infinity)
			/*int totalBlocks = (int)Math.ceil(blockRatio * gridSize * gridSize);
             Random rand = new Random();
             while(totalBlocks > 0) {
             int block = rand.nextInt(gridSize * gridSize);
             if(!blockedPath.containsKey(block)) {
             blockedPath.put(block, Integer.MAX_VALUE);
             totalBlocks --;
             }
             }*/
            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    if (randomGrid[index][subIndex]) {
                        blockedPath.put(index * gridSize + subIndex, Integer.MAX_VALUE);
                    }
                }
            }

            // get start and end node
            Random rand = new Random();
            startNode = rand.nextInt(gridSize * gridSize);
            while (blockedPath.containsKey(startNode)) {
                startNode++;
                if (startNode >= gridSize * gridSize) {
                    startNode = 0;
                }
            }
            endNode = rand.nextInt(gridSize * gridSize);
            while (blockedPath.containsKey(endNode)) {
                endNode++;
                if (endNode >= gridSize * gridSize) {
                    endNode = 0;
                }
            }
            if (startNode == endNode) {
                endNode++;
            }

            startNodeField.setText(((startNode / gridSize) + 1) + "," + (startNode - (startNode / gridSize) * gridSize + 1));
            endNodeField.setText(((endNode / gridSize) + 1) + "," + (endNode - (endNode / gridSize) * gridSize + 1));

            // initial calculations
            euclideanCal();
            chebyshevCal();
            manhattanCal();
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    /**
     * creates the frame
     *
     * @param gridSize
     */
    public PathFindingOnSquaredGrid(int gridSize) {
        setTitle("Grid Preview");
        this.gridSize = gridSize;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 479);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);

        gridPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // draw preview grid
                drawPreviewGrid(g);
            }
        };
        gridPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        gridPanel.setBounds(92, 45, 250, 250);
        layeredPane.add(gridPanel);

        lblRandomizedBlockPoints = new JLabel("Randomized Block Points");
        lblRandomizedBlockPoints.setHorizontalAlignment(SwingConstants.CENTER);
        lblRandomizedBlockPoints.setFont(new Font("Calibri", Font.BOLD, 16));
        lblRandomizedBlockPoints.setBounds(92, 20, 250, 14);
        layeredPane.add(lblRandomizedBlockPoints);

        separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setBounds(92, 306, 250, 2);
        layeredPane.add(separator);

        lblEnterStartNode = new JLabel("Start Node");
        lblEnterStartNode.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterStartNode.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblEnterStartNode.setBounds(112, 318, 95, 17);
        layeredPane.add(lblEnterStartNode);

        lblEnterEndNode = new JLabel("End Node");
        lblEnterEndNode.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterEndNode.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblEnterEndNode.setBounds(112, 346, 95, 17);
        layeredPane.add(lblEnterEndNode);

        startNodeField = new JTextField();
        //startNodeField.setEnabled(false);
        //startNodeField.setEditable(false);
        startNodeField.setBounds(213, 316, 86, 20);
        layeredPane.add(startNodeField);
        startNodeField.setColumns(10);

        endNodeField = new JTextField();
        //endNodeField.setEnabled(false);
        //endNodeField.setEditable(false);
        endNodeField.setColumns(10);
        endNodeField.setBounds(213, 344, 86, 20);
        layeredPane.add(endNodeField);

        separator_1 = new JSeparator();
        separator_1.setForeground(Color.BLACK);
        separator_1.setBounds(92, 374, 250, 2);
        layeredPane.add(separator_1);

        btnNewButton = new JButton("Euclidean Path");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String startNodeString = startNodeField.getText();
                String[] startSplits = startNodeString.split(",");
                String endNodeString = endNodeField.getText();
                String[] endSplits = endNodeString.split(",");
                try {
                    if (startSplits.length != 2 || endSplits.length != 2) {
                        throw new InvalidGridSizeException("Invalid Entry for Start / End Node");
                    }
                    int startX = Integer.parseInt(startSplits[0]);
                    int startY = Integer.parseInt(startSplits[1]);
                    int endX = Integer.parseInt(endSplits[0]);
                    int endY = Integer.parseInt(endSplits[1]);

                    startX--;
                    startY--;
                    endX--;
                    endY--;

                    startNode = startX * gridSize + startY;
                    endNode = endX * gridSize + endY;

                    // check whether in range
                    if (startNode < 0 || startNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("Start Node must be within the Range of 1 - " + gridSize * gridSize);
                    } else if (endNode < 0 || endNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("End Node must be within the Range of 1 - " + gridSize * gridSize);
                    }

                    // check wether both nodes are not same
                    if (startNode == endNode) {
                        throw new InvalidGridSizeException("Start Node cannot be same as the End Node");
                    }

                    // check whether start or end node are not the blocked path itself
                    if (blockedPath.containsKey(startNode)) {
                        throw new InvalidGridSizeException("Start Node cannot be among the Blocked Nodes");
                    } else if (blockedPath.containsKey(endNode)) {
                        throw new InvalidGridSizeException("End Node cannot be among the Blocked Nodes");
                    } else {
                        Boolean stuck = false;

                        gridPanel.repaint();
                        Dijkstra algoEuc = new Dijkstra(grid_euc, startNode, endNode, blockedPath);
                        algoEuc.evaluate();
                        double pathTravelledEuc = 0;
                        HashMap<Integer, Integer> parentPathEuc = null;
                        pathTravelledEuc = algoEuc.fetchDistance();
                        parentPathEuc = algoEuc.fetchPath();

                        if (pathTravelledEuc == -1) {
                            stuck = stuck || true;
                        }

                        // show result window
                        if (!stuck) {
                            EucGridDialog result = new EucGridDialog(gridSize, startNode, endNode, parentPathEuc, pathTravelledEuc, blockedPath);
                            result.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "No Path possible from the mentioned source to target. Please select different nodes.");
                        }
                    }
                } catch (NumberFormatException Ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Start/End Node. " + Ex.getMessage());
                } catch (InvalidGridSizeException Ex) {
                    JOptionPane.showMessageDialog(null, Ex.getMessage());
                }
            }
        });
        btnNewButton.setBounds(269, 387, 110, 23);
        layeredPane.add(btnNewButton);

        cheNewButton = new JButton("Chebyshev Path");
        cheNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String startNodeString = startNodeField.getText();
                String[] startSplits = startNodeString.split(",");
                String endNodeString = endNodeField.getText();
                String[] endSplits = endNodeString.split(",");
                try {
                    if (startSplits.length != 2 || endSplits.length != 2) {
                        throw new InvalidGridSizeException("Invalid Entry for Start / End Node");
                    }
                    int startX = Integer.parseInt(startSplits[0]);
                    int startY = Integer.parseInt(startSplits[1]);
                    int endX = Integer.parseInt(endSplits[0]);
                    int endY = Integer.parseInt(endSplits[1]);

                    startX--;
                    startY--;
                    endX--;
                    endY--;

                    startNode = startX * gridSize + startY;
                    endNode = endX * gridSize + endY;

                    // check whether in range
                    if (startNode < 0 || startNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("Start Node must be within the Range of 1 - " + gridSize * gridSize);
                    } else if (endNode < 0 || endNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("End Node must be within the Range of 1 - " + gridSize * gridSize);
                    }

                    // check wether both nodes are not same
                    if (startNode == endNode) {
                        throw new InvalidGridSizeException("Start Node cannot be same as the End Node");
                    }

                    // check whether start or end node are not the blocked path itself
                    if (blockedPath.containsKey(startNode)) {
                        throw new InvalidGridSizeException("Start Node cannot be among the Blocked Nodes");
                    } else if (blockedPath.containsKey(endNode)) {
                        throw new InvalidGridSizeException("End Node cannot be among the Blocked Nodes");
                    } else {
                        Boolean stuck = false;

                        gridPanel.repaint();
                        Dijkstra algoChe = new Dijkstra(grid_che, startNode, endNode, blockedPath);
                        algoChe.evaluate();
                        double pathTravelledChe = 0;
                        HashMap<Integer, Integer> parentPathChe = null;
                        pathTravelledChe = algoChe.fetchDistance();
                        parentPathChe = algoChe.fetchPath();

                        if (pathTravelledChe == -1) {
                            stuck = stuck || true;
                        }

                        // show result window
                        if (!stuck) {
                            CheGridDialog result = new CheGridDialog(gridSize, startNode, endNode, parentPathChe, pathTravelledChe, blockedPath);
                            result.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "No Path possible from the mentioned source to target. Please select different nodes.");
                        }
                    }
                } catch (NumberFormatException Ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Start/End Node. " + Ex.getMessage());
                } catch (InvalidGridSizeException Ex) {
                    JOptionPane.showMessageDialog(null, Ex.getMessage());
                }
            }
        });
        cheNewButton.setBounds(147, 387, 120, 23);
        layeredPane.add(cheNewButton);

        manNewButton = new JButton("Manhattan Path");
        manNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                Stopwatch timerFlow = new Stopwatch();
                String startNodeString = startNodeField.getText();
                String[] startSplits = startNodeString.split(",");
                String endNodeString = endNodeField.getText();
                String[] endSplits = endNodeString.split(",");
                try {
                    if (startSplits.length != 2 || endSplits.length != 2) {
                        throw new InvalidGridSizeException("Invalid Entry for Start / End Node");
                    }
                    int startX = Integer.parseInt(startSplits[0]);
                    int startY = Integer.parseInt(startSplits[1]);
                    int endX = Integer.parseInt(endSplits[0]);
                    int endY = Integer.parseInt(endSplits[1]);

                    startX--;
                    startY--;
                    endX--;
                    endY--;

                    startNode = startX * gridSize + startY;
                    endNode = endX * gridSize + endY;

                    // check whether in range
                    if (startNode < 0 || startNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("Start Node must be within the Range of 1 - " + gridSize * gridSize);
                    } else if (endNode < 0 || endNode > gridSize * gridSize) {
                        throw new InvalidGridSizeException("End Node must be within the Range of 1 - " + gridSize * gridSize);
                    }

                    // check wether both nodes are not same
                    if (startNode == endNode) {
                        throw new InvalidGridSizeException("Start Node cannot be same as the End Node");
                    }

                    // check whether start or end node are not the blocked path itself
                    if (blockedPath.containsKey(startNode)) {
                        throw new InvalidGridSizeException("Start Node cannot be among the Blocked Nodes");
                    } else if (blockedPath.containsKey(endNode)) {
                        throw new InvalidGridSizeException("End Node cannot be among the Blocked Nodes");
                    } else {
                        Boolean stuck = false;

                        gridPanel.repaint();
                        Dijkstra algoMan = new Dijkstra(grid_man, startNode, endNode, blockedPath);
                        algoMan.evaluate();
                        double pathTravelledMan = 0;
                        HashMap<Integer, Integer> parentPathMan = null;
                        pathTravelledMan = algoMan.fetchDistance();
                        parentPathMan = algoMan.fetchPath();

                        if (pathTravelledMan == -1) {
                            stuck = stuck || true;
                        }

                        // show result window
                        if (!stuck) {
                            ManGridDialog result = new ManGridDialog(gridSize, startNode, endNode, parentPathMan, pathTravelledMan, blockedPath);
                            result.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "No Path possible from the mentioned source to target. Please select different nodes.");
                        }
                    }
                } catch (NumberFormatException Ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Start/End Node. " + Ex.getMessage());
                } catch (InvalidGridSizeException Ex) {
                    JOptionPane.showMessageDialog(null, Ex.getMessage());
                }

                System.out.println("Elapsed time = " + timerFlow.elapsedTime());
                System.out.println("Start node: " + (startNode / gridSize + 1) + "," + (startNode - (startNode / gridSize) * gridSize + 1));
                System.out.println("End node: " + (endNode / gridSize + 1) + "," + (endNode - (endNode / gridSize) * gridSize + 1));

            }
        });
        manNewButton.setBounds(35, 387, 110, 23);
        layeredPane.add(manNewButton);

        formulateGrid();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // set the L&F to system default
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    SizeDialog window = new SizeDialog();
                    //window.frmShortestPathFinder.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
