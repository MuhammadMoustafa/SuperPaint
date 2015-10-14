import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.beans.PropertyVetoException; // unique exception for maximizing window
/**
 * @author Benson Pan
 * 
 * @version April 8, 2015
 * 
 * DrawFrame Class - contains all widgets and interacts with the DrawPanel
 */
public class DrawFrame extends JFrame
{
    // Container, label and instance of DrawPanel class
    private JDesktopPane desktop;
    private JInternalFrame panelFrame;
    private JPanel northContainer;
    private JLabel label;
    private DrawPanel panel;
    
    // Buttons
    private JButton undo;
    private JButton redo;
    private JButton clear;
    private JButton color1;
    private JButton color2;
    
    // Combobox and Checkbox items
    private JComboBox shapeBox;
    private JCheckBox filled;
    private JCheckBox gradient;
    private JCheckBox dashed;
    
    // Text field related items
    private JTextField strokeWidthField;
    private JTextField dashLengthField;
    private JLabel strokeLabel;
    private JLabel dashLabel;
    
    // Menu related items
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu optionsMenu;
    private JMenuItem aboutItem;
    private JMenuItem prefsItem;
    private JMenuItem exitItem;
    private JMenuItem showToolbar;
    
    // Prefs page related items
    private JInternalFrame prefsFrame;
    
    // toolbar item
    private JInternalFrame toolbar;
    
    // Constants
    private final String[] shapes = {"Line", "Rectangle", "Oval"};
    private final int MAX_LINE_WIDTH = 100;
    private final int MIN_VALUE = 0;
    
    // No parameter constructor adds all widgets onto JFrame
    public DrawFrame()
    {
        // Calls superclass' constructor
        super( "SuperPaint" );
        setLayout(new BorderLayout());
        
        // Instantiate JPanel
        northContainer = new JPanel();
        northContainer.setLayout(new GridLayout(1, 10));
        
        // Instantiate JLabel
        label = new JLabel();
        
        // Instantiate DrawPanel and JDesktopPane items
        panel = new DrawPanel(label);
        desktop = new JDesktopPane();
        panelFrame = new JInternalFrame("SuperPaint");
        panelFrame.setLayer(0); // always at the back
        panelFrame.add(panel);
        // try setting JInternalFrame maximized 
        try {
            panelFrame.setMaximum(true);
        }
        // following must be catched in order to compile
        catch(PropertyVetoException e)
        {
        }
        catch (NullPointerException e)
        {
        }
        // end exception handling

        panelFrame.setVisible(true);
        desktop.add(panelFrame);
        
        // Instantiate buttons
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        clear = new JButton("Clear");
        color1 = new JButton("Color 1");
        color2 = new JButton("Color 2");
        color1.setForeground(panel.getCurrentShapeColor());
        color2.setForeground(panel.getSecondShapeColor());
        undo.addActionListener(new MultiActionHandler());
        redo.addActionListener(new MultiActionHandler());
        clear.addActionListener(new MultiActionHandler());
        color1.addActionListener(new MultiActionHandler());
        color2.addActionListener(new MultiActionHandler());
                
        // Instantiate combo box
        shapeBox = new JComboBox(shapes);
        shapeBox.setMaximumRowCount( 3 );
        shapeBox.setSelectedIndex(panel.getCurrentShapeType());
        shapeBox.addItemListener(new ComboBoxCheckBoxHandler());
        
        // Instantiate check box
        filled = new JCheckBox("Filled?", panel.getCurrentShapeFilled());
        gradient = new JCheckBox("Gradient?", panel.getCurrentShapeGradient());
        dashed = new JCheckBox("Dashed?", panel.getDashed());
        filled.addItemListener(new ComboBoxCheckBoxHandler());
        gradient.addItemListener(new ComboBoxCheckBoxHandler());
        dashed.addItemListener(new ComboBoxCheckBoxHandler());
        
        // Instantiate text fields and labels
        strokeWidthField = new JTextField(String.format("%d", panel.getStrokeWidth()), 7);
        dashLengthField = new JTextField(String.format("%d", panel.getDashLength()), 7);
        strokeLabel = new JLabel("Stroke Width:");
        dashLabel = new JLabel("Dash Length:");
        strokeWidthField.addKeyListener(new KeyboardHandler());
        dashLengthField.addKeyListener(new KeyboardHandler());
        
        // Instantiate and setup menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        optionsMenu = new JMenu("Options");
        aboutItem = new JMenuItem("About");
        prefsItem = new JMenuItem("Prefs");
        exitItem = new JMenuItem("Exit");
        showToolbar = new JMenuItem("Show Toolbar");
        
        fileMenu.add(aboutItem);
        fileMenu.add(prefsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        optionsMenu.add(showToolbar);
        menuBar.add(optionsMenu);
        showToolbar.addActionListener(new MultiActionHandler());
        aboutItem.addActionListener(new MultiActionHandler());
        exitItem.addActionListener(new MultiActionHandler());
        prefsItem.addActionListener(new MultiActionHandler());
        
        // Preference dialog related items
        prefsFrame = new JInternalFrame("Please select program defaults and click save.", false, true);
        prefsFrame.setSize(350, 200);
        prefsFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        prefsFrame.add(new PreferencePane()); // add the PreferencePane JPanel 
        desktop.add(prefsFrame); // add to the JDesktopPane

        // Create toolbar
        toolbar = new JInternalFrame("Toolbar");
        toolbar.setVisible(true);
        toolbar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Add all items onto toolbar
        c.fill = GridBagConstraints.HORIZONTAL;
        // Row 1
        c.gridy = 0;
        c.gridx = 0;
        toolbar.add(undo, c);
        c.gridx = 1;
        toolbar.add(redo, c);
        c.gridx = 2;
        toolbar.add(clear, c);
        // Row 2
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        toolbar.add(shapeBox, c);
        c.gridwidth = 1;
        c.gridx = 2;
        toolbar.add(color1, c);
        c.gridx = 3;
        toolbar.add(color2, c);
        // Row 3
        c.gridy = 2;
        c.gridx = 0;
        toolbar.add(strokeLabel, c);
        c.gridx = 1;
        toolbar.add(strokeWidthField, c);
        c.gridx = 2;
        toolbar.add(dashLabel, c);
        c.gridx = 3;
        toolbar.add(dashLengthField, c);
        c.gridx = 0;
        c.gridy = 3;
        // Row 4
        c.gridy = 3;
        toolbar.add(filled, c);
        c.gridx = 1;
        toolbar.add(gradient, c);
        c.gridx = 2;
        toolbar.add(dashed, c);
        toolbar.pack();
        desktop.add(toolbar);
        toolbar.moveToFront(); // pushes the toolbar to the front
        
        // Add all the widgets onto the JFrame
        setJMenuBar(menuBar); // add MenuBar
        add(desktop, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
    }
    
    // Private inner class used to handle JButton events and calls appropriate methods 
    private class MultiActionHandler implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            // Handles undo button presses
            if ( event.getSource() == undo )
                panel.clearLastShape();
            // Handles redo button presses
            if ( event.getSource() == redo )
                panel.redoLastShape();
            // Handles clear button presses
            if ( event.getSource() == clear )
                panel.clearDrawing();
            // Handles both color button presses
            if ( event.getSource() == color1 ||  event.getSource() == color2 )
            {
                Color newColor = JColorChooser.showDialog(panel, "Choose Color", Color.BLACK);
                // Match color change with the corresponding color variable
                if (newColor != null) 
                {
                    if (event.getSource() == color1)
                    {
                        color1.setForeground(newColor);
                        panel.setCurrentColor(newColor);
                    }
                    else
                    {
                        color2.setForeground(newColor);
                        panel.setSecondColor(newColor);
                    }
                }
            }
            // Handles exit presses (from the JMenuItem)
            if ( event.getSource() == exitItem )
                System.exit(0); // terminates program when clicked
            // Handles about presses (from the JMenuItem)
            if ( event.getSource() == aboutItem )
                JOptionPane.showMessageDialog(panel, "By: Benson Pan\nMay 2015", "About", JOptionPane.INFORMATION_MESSAGE);
            // Handles prefs presses (from the JMenuItem)
            if ( event.getSource() == prefsItem )
            {
                prefsFrame.setLocation(0, 0); // move back to top left corner
                prefsFrame.moveToFront();
                prefsFrame.setVisible(true);
            }
            // check for show toolbar state
            if ( event.getSource() == showToolbar )
            {
                toolbar.setLocation(0, 0); 
                toolbar.moveToFront();
            }
        }
    }
    
    // Private inner class used to handle JComboBox and JCheckBox events
    private class ComboBoxCheckBoxHandler implements ItemListener
    {
        public void itemStateChanged(ItemEvent event) 
        {
            if (event.getStateChange() == ItemEvent.SELECTED)
            {
                // handles new shape selection
                if ( event.getSource() == shapeBox )
                    panel.setCurrentShapeType(shapeBox.getSelectedIndex());
            }
            // check for filled/unfilled state
            if ( event.getSource() == filled )
                panel.setCurrentShapeFilled(filled.isSelected());
            // check for gradient state
            if ( event.getSource() == gradient )
                panel.setGradient(gradient.isSelected());
            // check for dashed state
            if ( event.getSource() == dashed )
                panel.setDashed(dashed.isSelected());
        }
    }
    
    // Private inner class used to handle JTextField
    private class KeyboardHandler extends KeyAdapter
    {
        public void keyReleased(KeyEvent e)
        {
            try {
                // retrieves data from both text fields
                int input1 = Integer.parseInt(strokeWidthField.getText());
                int input2 = Integer.parseInt(dashLengthField.getText());
                // validate input from first textfield
                if (input1 > MIN_VALUE)
                {
                    if (input1 < MAX_LINE_WIDTH)
                        panel.setStrokeWidth(input1);
                    else
                        panel.setStrokeWidth(MAX_LINE_WIDTH);
                }
                // validate input from second textfield
                if (input2 > MIN_VALUE)
                {
                    if (input2 < MAX_LINE_WIDTH)
                        panel.setDashLength(input2);
                    else
                        panel.setDashLength(MAX_LINE_WIDTH);
                }
            }
            catch (NumberFormatException numberFormatException)
            {
            }
        }
        // adapter class must provide implementation for the follow methods
        public void keyTyped(KeyEvent e)
        {
        }
        public void keyPressed(KeyEvent e)
        {
        }
    }
}