import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
/*
 * @author Benson Pan
 * 
 * @version May 23, 2015
 * 
 * Preference Pane class - used to manage the preference pane window
 * Was implemented because one cannot add a object to two components (for code reuse)
 */
public class PreferencePane extends JPanel
{
    // Buttons 
    private JButton save;
    private JButton colorButton1;
    private JButton colorButton2;
    // Combobox and checkboxes
    private JComboBox shapeBox;
    private JCheckBox filledBox;
    private JCheckBox dashedBox;
    private JCheckBox gradientBox;
    // Label
    // Text field related items
    private JTextField strokeWidthField;
    private JTextField dashLengthField;
    private JLabel strokeLabel;
    private JLabel dashLabel;
    // I/O handling
    private PrintWriter output;
    
    // Data attributes
    private Color color1;
    private Color color2;
    private final String[] shapes = {"Line", "Rectangle", "Oval"};
    private final String[] preferences = {"shape type:", "fill:", "gradient:", "color 1:", "color 2:", "line width:",
                                          "dash length:", "dashed:"};
    
    public PreferencePane()
    {
        // Set JPanel Properties
        setSize( 500, 500 ); // set frame size
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Instantiate CheckBoxes
        filledBox = new JCheckBox("Filled?");
        gradientBox = new JCheckBox("Gradient?");
        dashedBox = new JCheckBox("Dashed?");
        // Instantiate text field related items
        strokeWidthField = new JTextField("1", 5);
        dashLengthField = new JTextField("1", 5);
        strokeLabel = new JLabel("Stroke Width:");
        dashLabel = new JLabel("Dash Length:");
        // Instantiate combobox items
        shapeBox = new JComboBox(shapes);
        shapeBox.setMaximumRowCount( 3 );
        // Instantiate buttons
        save = new JButton("Save");
        colorButton1 = new JButton("Color 1");
        colorButton2 = new JButton("Color 2");
        colorButton1.addActionListener(new MultiActionHandler());
        colorButton2.addActionListener(new MultiActionHandler());
        save.addActionListener(new MultiActionHandler());
        
        // set defaults
        color1 = Color.BLACK;
        color2 = Color.BLACK;
        
        // arrange items on gridbaglayout
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        // Row 1
        c.gridx = 0;
        c.gridy = 0;
        add(shapeBox, c);
        c.gridwidth = 1;
        c.gridx = 2;
        add(colorButton1, c);
        c.gridx = 3;
        add(colorButton2, c);
        // Row 2
        c.gridx = 0;
        c.gridy = 1;
        add(strokeLabel, c);
        c.gridx = 1;
        add(strokeWidthField, c);
        c.gridx = 2;
        add(dashLabel, c);
        c.gridx = 3;
        add(dashLengthField, c);
        // Row 3
        c.gridx = 0; 
        c.gridy = 2;
        add(filledBox, c);
        c.gridx = 1; 
        add(gradientBox, c);
        c.gridx = 2; 
        add(dashedBox, c);
        // Row 
        c.gridx = 1; 
        c.gridy = 3;
        add(save, c);
    }
    
     private class MultiActionHandler implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            // output preferences to file when save is pressed
            if ( event.getSource() == save )
            {
                try {
                    output = new PrintWriter(new File("preferences.txt"));
                    
                    for (int i = 0 ; i < preferences.length ; i++)
                    {
                        // match preference title with preference
                        // Uses indexes of the preferences array
                        switch (i)
                        {
                            case 0:
                                output.println(preferences[i] + shapeBox.getSelectedIndex());
                                break;
                            case 1:
                                output.println(preferences[i] + filledBox.isSelected());
                            break;
                            case 2:
                                output.println(preferences[i] + gradientBox.isSelected());
                                break;
                            case 3:
                                output.println(preferences[i] + color1.getRGB());
                                break;
                            case 4:
                                output.println(preferences[i] + color2.getRGB());
                                break;
                            case 5:
                                output.println(preferences[i] + strokeWidthField.getText());
                                break;
                            case 6:
                                output.println(preferences[i] + dashLengthField.getText());
                                break;
                            case 7:
                                output.println(preferences[i] + dashedBox.isSelected());
                                break;
                        }
                    }
                    output.close();
                    JOptionPane.showMessageDialog(new JPanel(), "Preferences Saved.", "Saved", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (FileNotFoundException fileNotFoundException)
                {
                }// end exception handling 
                            
            }
            // handle buttons, creating JColorChooser dialog
            if ( event.getSource() == colorButton1 ||  event.getSource() == colorButton2 )
            {
                Color newColor = JColorChooser.showDialog(new JPanel(), "Choose Color", Color.BLACK);
                // Match color change with the corresponding color variable
                if (newColor != null) 
                {
                    if (event.getSource() == colorButton1)
                    {
                        colorButton1.setForeground(newColor);
                        color1 = newColor;
                    }
                    else
                    {
                        colorButton2.setForeground(newColor);
                        color2 = newColor;
                    }
                }
            }
        }
     }
}