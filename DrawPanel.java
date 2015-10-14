import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Benson Pan
 * 
 * @version April 8, 2015
 * 
 * DrawPanel Class - contains all variables and handles events
 */
public class DrawPanel extends JPanel
{
    // Constants
    private final int LINE_SHAPE = 0;
    private final int RECT_SHAPE = 1;
    private final int OVAL_SHAPE = 2;
    private final int BAD_VALUE = -1;
    private final int EMPTY = 0;
    private final int DEFAULT_WIDTH = 1;
    private final Color DEFAULT_COLOR = Color.BLACK;
    private final String[] preferences = {"shape type:", "fill:", "gradient:", "color 1:", "color 2:", "line width:",
                                          "dash length:", "dashed:"};
    
    private LinkedList<MyShape> shapeObjects;
    private LinkedList<MyShape> removedShapeObjects;
    private int currentShapeType;
    private int strokeWidth;
    private int dashLength;
    private MyShape currentShape;
    private Color currentShapeColor;
    private Color secondShapeColor;
    private boolean currentShapeFilled;
    private boolean currentShapeGradient;
    private boolean dashed;
    private JLabel statusLabel;
    private boolean buttonPressed;
    private boolean[] validPref = new boolean[preferences.length];
    
    // JLabel parameter constructor initalizes variables
    public DrawPanel(JLabel label)
    {
        // Note: default values are set to prevent program from crashing
        statusLabel = label;
        shapeObjects = new LinkedList<MyShape>();
        removedShapeObjects = new LinkedList<MyShape>();
        setBackground( Color.WHITE );
        currentShapeType = LINE_SHAPE;
        strokeWidth = DEFAULT_WIDTH;
        dashLength = DEFAULT_WIDTH;
        currentShapeColor = DEFAULT_COLOR;
        secondShapeColor = DEFAULT_COLOR;
        currentShapeFilled = false;
        currentShapeGradient = false;
        dashed = false;
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
        
        // Override with preferences 
        try 
        {
            Scanner input = new Scanner(new File("preferences.txt"));
            
            while (input.hasNext())
            {
                String line = input.nextLine().toLowerCase().trim();
                int pref = BAD_VALUE;
                int intPref = BAD_VALUE;
                String stringPref = "";
                boolean boolPref = false;
                Color colorPref = Color.BLACK;
                
                // check if line contains preference headers
                for (int i = 0 ; i < preferences.length ; i++)
                {
                    if (line.contains(preferences[i]))
                        pref = i;
                }
                
                // format the ending of the string, after the preference
                if (pref != BAD_VALUE)
                    stringPref = line.substring(preferences[pref].length()).trim().toLowerCase();
                
                // Uses indexes of the preferences array (instead of separate strings, I stored in an array)
                if (pref == 0 || pref == 5 || pref == 6)
                {
                    intPref = checkInt(stringPref);
                }
                else if (pref == 1 || pref == 2 || pref == 7)
                {
                    boolPref = checkBoolean(stringPref);
                }
                else if (pref == 3 || pref == 4)
                {
                    colorPref = checkColor(stringPref);
                }
                
                // match preference to its corresponding variable
                // Uses indexes of the preferences array
                switch (pref)
                {
                    case BAD_VALUE:
                        // does nothing if this line of preference does not contain any valid data
                        break;
                        
                    // check for shape type preference
                    case 0:
                    {
                        if (intPref >= LINE_SHAPE && intPref <= OVAL_SHAPE)
                        {
                            currentShapeType = intPref;
                            validPref[pref] = true;
                        }
                        break;
                    }
                    
                    // check for filled preference
                    case 1:
                    {
                        if (stringPref.equals("true") || stringPref.equals("false"))
                        {
                            currentShapeFilled = boolPref;
                            validPref[pref] = true;
                            break;
                        }
                    }
                    
                    // check for gradient preference
                    case 2:
                    {
                        if (stringPref.equals("true") || stringPref.equals("false"))
                        {
                            currentShapeGradient = boolPref;
                            validPref[pref] = true;
                            break;
                        }
                    }
                        
                    // check for first gradient color preference
                    case 3:
                    {
                        if (colorPref != null)
                        {
                            currentShapeColor = colorPref;
                            validPref[pref] = true;
                            break;
                        }
                    }
                    
                    // check for second gradient color preference
                    case 4:
                    {
                        if (colorPref != null)
                        {
                            secondShapeColor = colorPref;
                            validPref[pref] = true;
                            break;
                        }
                    }
                        
                    // check for line width preference
                    case 5:
                    {
                        if (intPref > EMPTY)
                        {
                            strokeWidth = intPref;
                            validPref[pref] = true;
                        }
                        break;
                    }
                        
                    // check for dash length preference
                    case 6:
                    {
                        if (intPref > EMPTY)
                        {
                            dashLength = intPref;
                            validPref[pref] = true;
                        }
                        break;
                    }
                        
                    // check for dashed line preference
                    case 7:
                    {
                        if (stringPref.equals("true") || stringPref.equals("false"))
                        {
                            dashed = boolPref;
                            validPref[pref] = true;
                            break;
                        }
                    }
                }
                
            }
            
            input.close();

            // Displays failed preferences
            for (int i = 0 ; i < validPref.length ; i++)
            {
                if (!validPref[i])
                    JOptionPane.showMessageDialog(this, (preferences[i] + " unknown input specified. Program default assigned."),
            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }// end try block
        
        catch (FileNotFoundException fileNotFoundException)
        {
        }// end exception handling 
    }
    
    //I/O HANDLING METHODS
    // Checks if input string is an integer value, returns BAD_VALUE if not
    public int checkInt(String input)
    {
        try
        {
            int temp = Integer.parseInt(input.trim());
            return temp;
        }
        catch (NumberFormatException numberFormatException)
        {
            return BAD_VALUE;
        }
    }
    
    // Checks if input string matches boolean value true, otherwise, default to false
    public boolean checkBoolean(String input)
    {
        input = input.trim();
        if (input.equals("true"))
            return true;
        else
            return false;
    }
    
    // Checks if input string is a valid Color constant
    public Color checkColor(String input)
    {
        int i = checkInt(input);
        if (i != BAD_VALUE)
            return new Color(i);
        else 
            return null;
    }
    
    // overridden paintComponent method that draws the shapes
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        
        // draw the shapes in the array
        for (int i = 0 ; i < shapeObjects.size() ; i++)
        {
            shapeObjects.addFront(shapeObjects.removeEnd());
            shapeObjects.first().draw( g );
        }

        // draw the currentShape aswell, if not null
        if (currentShape != null)
            currentShape.draw( g );
    } 
    
    // MUTATOR METHODS
    // Accepts a parameter and stores the new currentShapeType
    public void setCurrentShapeType(int type)
    {
        currentShapeType = type;
    }
    
    // Accepts a parameter and stores the new currentShapeColor
    public void setCurrentShapeColor(Color color)
    {
        currentShapeColor = color;
    }
    
    // Sets currentShapeGradient parameter
    public void setGradient(boolean status)
    {
        currentShapeGradient = status;
    }
    
    // Sets dashed parameter
    public void setDashed(boolean status)
    {
        dashed = status;
    }
    
    // Accepts a parameter and stores the new currentShapeFilled
    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled = filled;
    }
    
    // Accepts a parameter and stores the new strokeWidth
    public void setStrokeWidth(int width)
    {
        strokeWidth = width;
    }
    
    // Accepts a parameter and stores the new dashLength
    public void setDashLength(int length)
    {
        dashLength = length;
    }
    
    // ACCESSOR METHODS
    // Returns currentShapeColor
    public Color getCurrentShapeColor()
    {
        return currentShapeColor;
    }
    
    // Returns secondShapeColor
    public Color getSecondShapeColor()
    {
        return secondShapeColor;
    }
    
    // Returns currentShapeFilled
    public boolean getCurrentShapeFilled()
    {
        return currentShapeFilled;
    }
    
    // Returns cuurentShapeGradient
    public boolean getCurrentShapeGradient()
    {
        return currentShapeGradient;
    }
    
    // Returns dashed
    public boolean getDashed()
    {
        return dashed;
    }
    
    // Returns currentShapeType
    public int getCurrentShapeType()
    {
        return currentShapeType;
    }
    
    // Returns strokeWidth
    public int getStrokeWidth()
    {
        return strokeWidth;
    }
    
    // Returns dashLength
    public int getDashLength()
    {
        return dashLength;
    }
    
    // BUTTON FUNCTIONS
    // Clear the last drawn shape if there is one
    public void clearLastShape()
    {
        if (shapeObjects.size() != EMPTY)
        {
            removedShapeObjects.addFront(shapeObjects.removeFront());
        }
        repaint();
    }
    
    // Redraw last cleared shape if there is one
    public void redoLastShape()
    {
        if (removedShapeObjects.size() != EMPTY)
        {
            shapeObjects.addFront(removedShapeObjects.removeFront());
        }
        repaint();
    }
    
    // Remove all shapes in the current drawing
    public void clearDrawing()
    {
        currentShape = null;
        shapeObjects.makeEmpty();
        removedShapeObjects.makeEmpty();
        repaint();
    }
    
    // stores the new color in currentShapeColor
    public void setCurrentColor( Color newColor )
    {
        currentShapeColor = newColor;
    }
    
    // Stores the new color in temp or changes it directly in secondShapeColor, depending on the currentShapeGradient variable
    public void setSecondColor( Color newColor )
    {
        secondShapeColor = newColor;
    }
    
    // MOUSE HANDLERS
    // Private inner class to handle mouse events
    private class MouseHandler extends MouseAdapter
    {
        // assigns a new shape to the currentShape variable, according to the currentShapeType
        public void mousePressed(MouseEvent event) 
        {
            if (!buttonPressed) // ensure only first button press triggers event
            {
                buttonPressed = true;
                switch (currentShapeType)
                {
                    case LINE_SHAPE:
                        currentShape = new MyLine(event.getX(), event.getY(), event.getX(),
                                                  event.getY(), strokeWidth, dashLength, currentShapeColor, secondShapeColor,
                                                  currentShapeGradient, dashed);
                        break;
                    case RECT_SHAPE:
                        currentShape = new MyRectangle(event.getX(), event.getY(), event.getX(),
                                                       event.getY(), strokeWidth, dashLength, currentShapeColor, 
                                                       secondShapeColor, currentShapeGradient, dashed, currentShapeFilled);
                        break;
                    case OVAL_SHAPE:
                        currentShape = new MyOval(event.getX(), event.getY(), event.getX(),
                                                  event.getY(), strokeWidth, dashLength, currentShapeColor, 
                                                  secondShapeColor, currentShapeGradient, dashed, currentShapeFilled);
                        break;
                }
            }
        }
        // finish drawing the current shape and adds it to shapeObjects
        public void mouseReleased(MouseEvent event)
        {
            if (buttonPressed) // ensures only first released button triggers event
            {
                buttonPressed = false;
                // update variables for gradient painting
                currentShape.setX2(event.getX());
                currentShape.setY2(event.getY());
                currentShape.setPaint(currentShapeColor, secondShapeColor);
                // add currentShape into the linked list, clears current shape and repaints it
                shapeObjects.addFront(currentShape);
                currentShape = null;
                // clear the redo list - cannot redo an item after a new one is drawn
                removedShapeObjects.makeEmpty();
                repaint();
            }
        }
    }
    
    // Private inner class to handle mouse movement events
    private class MouseMotionHandler extends MouseMotionAdapter
    {
        // set the text of the statusLabel to display updated coordinates
        public void mouseMoved(MouseEvent event)
        {
            statusLabel.setText(String.format("x: %d y: %d", event.getX(), event.getY()));
        }
        // updates the x and y coordinates of the shape, and updates statusLabel
        public void mouseDragged(MouseEvent event)
        {
            if (buttonPressed) // ensures only pressed button triggers event
            {
                // update variables for gradient painting
                currentShape.setX2(event.getX());
                currentShape.setY2(event.getY());
                currentShape.setPaint(currentShapeColor, secondShapeColor);
                statusLabel.setText(String.format("x: %d y: %d", event.getX(), event.getY()));
                repaint();
            }
        }
    }
    
}

    
    
    