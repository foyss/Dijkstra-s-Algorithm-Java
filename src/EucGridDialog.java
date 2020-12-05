// Name:        Foysal Ahmed

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class EucGridDialog extends JFrame {

    private JPanel contentPane;
    private JPanel resultPanel;
    private JLabel costLabel;
    private JLabel stepsLabel;

    private int gridSize;
    private int startNode;
    private int endNode;
    private double pathLength;
    private HashMap<Integer, Integer> parentPath;
    private HashMap<Integer, Integer> blockedPath;

    private void paintPanel(Graphics g) {
        try {
            int boxWidth = (resultPanel.getWidth()) / gridSize;
            int offset = (resultPanel.getWidth()) - (boxWidth * gridSize);

            resultPanel.setSize(boxWidth * gridSize, boxWidth * gridSize);
            resultPanel.getBounds().getMinX();
            resultPanel.setBounds((int) resultPanel.getBounds().getMinX() + offset / 2, (int) resultPanel.getBounds().getMinY() + offset / 2, boxWidth * gridSize, boxWidth * gridSize);

            for (int index = 0; index < gridSize; index++) {
                for (int subIndex = 0; subIndex < gridSize; subIndex++) {
                    int gridPosition = subIndex - (subIndex / gridSize) * gridSize + (index * gridSize);
                    g.setColor(Color.BLACK);
                    g.fillRect(boxWidth * subIndex, boxWidth * index, boxWidth, boxWidth);
                    if (blockedPath.containsKey(gridPosition)) {
                        g.setColor(Color.RED);
                    } else if (parentPath.containsKey(gridPosition)) {
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
            costLabel.setText(df.format(this.pathLength));
            stepsLabel.setText(Integer.toString(parentPath.size() - 1));
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    /**
     * Create the frame.
     */
    public EucGridDialog(int gridSize, int startNode, int endNode, HashMap<Integer, Integer> parentPath, double pathLength, HashMap<Integer, Integer> blockedPath) {
        this.gridSize = gridSize;
        this.startNode = startNode;
        this.endNode = endNode;
        this.parentPath = parentPath;
        this.pathLength = pathLength;
        this.blockedPath = blockedPath;

        setTitle("Euclidean Path");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 389, 451);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);

        resultPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                paintPanel(g);
            }
        };
        resultPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        resultPanel.setBounds(58, 64, 250, 250);
        layeredPane.add(resultPanel);

        JLabel lblEuclideanPath = new JLabel("Euclidean Path");
        lblEuclideanPath.setHorizontalAlignment(SwingConstants.CENTER);
        lblEuclideanPath.setFont(new Font("Calibri", Font.BOLD, 16));
        lblEuclideanPath.setBounds(58, 26, 250, 14);
        layeredPane.add(lblEuclideanPath);

        JSeparator separator = new JSeparator();
        separator.setBounds(58, 51, 250, 2);
        layeredPane.add(separator);

        JLabel lblTotalCost = new JLabel("Total Cost");
        lblTotalCost.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalCost.setBounds(58, 336, 129, 14);
        layeredPane.add(lblTotalCost);

        JLabel lblTotalSteps = new JLabel("Total Steps");
        lblTotalSteps.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalSteps.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblTotalSteps.setBounds(58, 350, 129, 14);
        layeredPane.add(lblTotalSteps);

        costLabel = new JLabel("Total Cost");
        costLabel.setHorizontalAlignment(SwingConstants.LEFT);
        costLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        costLabel.setBounds(197, 336, 129, 14);
        layeredPane.add(costLabel);

        stepsLabel = new JLabel("Total Steps");
        stepsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        stepsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        stepsLabel.setBounds(197, 350, 129, 14);
        layeredPane.add(stepsLabel);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(58, 323, 250, 2);
        layeredPane.add(separator_1);
    }
}
