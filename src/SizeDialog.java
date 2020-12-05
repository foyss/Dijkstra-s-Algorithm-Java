// Name:        Foysal Ahmed

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import exceptions.InvalidGridSizeException;

public class SizeDialog {

    private JFrame frmShortestPathFinder;
    private JTextField gridSizeField;

    /**
     * Create the application.
     */
    public SizeDialog() {
        initialize();

        String gridSizeStr = "4-12";
        int gridSize = 0;
        try {
            String[] splits = gridSizeStr.split("-");
            int startRange, endRange;
            if (splits.length != 2) {
                throw new InvalidGridSizeException("Please mentione the range as 1-12");
            } else {
                String startRangeStr = splits[0].trim();
                startRange = Integer.parseInt(startRangeStr);
                String endRangeStr = splits[1].trim();
                endRange = Integer.parseInt(endRangeStr);

                Random rand = new Random();
                gridSize = rand.nextInt(endRange - startRange); //controls grid size
                gridSize += startRange;

                if (gridSize < 2) {
                    gridSize = 3;
                }
            }

            if (gridSize < 2) {
                throw new InvalidGridSizeException("Grid size must be greater than 1.");
            } else {
                PathFindingOnSquaredGrid grid = new PathFindingOnSquaredGrid(gridSize);
                grid.setVisible(true);
            }
        } catch (NumberFormatException Ex) {
            JOptionPane.showMessageDialog(null, "Invalid Grid Size. " + Ex.getMessage());
        } catch (InvalidGridSizeException Ex) {
            JOptionPane.showMessageDialog(null, Ex.getMessage());
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmShortestPathFinder = new JFrame();
        frmShortestPathFinder.setTitle("Shortest Path Finder Simulator");
        frmShortestPathFinder.setResizable(false);
        frmShortestPathFinder.setBounds(100, 100, 450, 245);
        frmShortestPathFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmShortestPathFinder.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Shortest Path Finder Simulator");
        lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(97, 37, 229, 23);
        frmShortestPathFinder.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setBounds(97, 71, 229, 2);
        frmShortestPathFinder.getContentPane().add(separator);

        JLabel lblEnterGridSize = new JLabel("Enter Grid Size Range");
        lblEnterGridSize.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblEnterGridSize.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEnterGridSize.setBounds(108, 95, 122, 17);
        frmShortestPathFinder.getContentPane().add(lblEnterGridSize);

        gridSizeField = new JTextField();
        gridSizeField.setFont(new Font("Calibri", Font.PLAIN, 14));
        gridSizeField.setBounds(233, 93, 86, 20);
        frmShortestPathFinder.getContentPane().add(gridSizeField);
        gridSizeField.setColumns(10);

        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(Color.BLACK);
        separator_1.setBounds(97, 138, 229, 2);
        frmShortestPathFinder.getContentPane().add(separator_1);

        JButton btnNewButton = new JButton("Continue");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // fetch the grid size 
                String gridSizeStr = gridSizeField.getText();
                int gridSize = 0;
                try {
                    String[] splits = gridSizeStr.split("-");
                    int startRange, endRange;
                    if (splits.length != 2) {
                        throw new InvalidGridSizeException("Please mentione the range as 1-12");
                    } else {
                        String startRangeStr = splits[0].trim();
                        startRange = Integer.parseInt(startRangeStr);
                        String endRangeStr = splits[1].trim();
                        endRange = Integer.parseInt(endRangeStr);

                        Random rand = new Random();
                        gridSize = rand.nextInt(endRange - startRange);
                        gridSize += startRange;

                        if (gridSize < 2) {
                            gridSize = 3;
                        }
                    }

                    if (gridSize < 2) {
                        throw new InvalidGridSizeException("Grid size must be greater than 1.");
                    } else {
                        PathFindingOnSquaredGrid grid = new PathFindingOnSquaredGrid(gridSize);
                        grid.setVisible(true);
                    }
                } catch (NumberFormatException Ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Grid Size. " + Ex.getMessage());
                } catch (InvalidGridSizeException Ex) {
                    JOptionPane.showMessageDialog(null, Ex.getMessage());
                }
            }
        });
        btnNewButton.setBounds(251, 151, 75, 23);
        frmShortestPathFinder.getContentPane().add(btnNewButton);
    }
}
