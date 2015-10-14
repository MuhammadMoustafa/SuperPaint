import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
/**
 * Unit 2 Assignment - Drawing with Polymorphism
 * 
 * @author Benson Pan
 * 
 * @version December 17, 2014
 */

abstract class MyShape
{
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int strokeWidth;
    private int dashLength;
    private Color color1;
    private Color color2;
    private GradientPaint paint;
    private BasicStroke stroke; 
    private boolean paintGradient;
    private boolean dashed;
    
    /* 
     * 5 Parameter Constructor
     * Accepts x1, y1, x2, y2 coordinates and color
     * Stores parameters into corresponding data attributes
     */
    public MyShape( int x1, int y1, int x2, int y2, Color color1 )
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color1 = color1;
    }
    
    /*
     * 6 Parameter Constructor
     * Accepts x1, y1, x2, y2, coordinates and two colors
     * Calls the setPaint method and stores parameters into corresponding data attributes
     */
    public MyShape( int x1, int y1, int x2, int y2, int strokeWidth, int dashLength, Color color1, Color color2,
    boolean paintGradient, boolean dashed )
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.strokeWidth = strokeWidth;
        this.dashLength = dashLength;
        this.color1 = color1;
        this.color2 = color2;
        this.paintGradient = paintGradient;
        this.dashed = dashed;
        setPaint(color1, color2);
        setStroke();
    }
    
    /*
     * No Parameter Constuctor
     * Sets x and y coordinates 0, color to black by default
     */
    public MyShape()
    {
        this( 0, 0, 0, 0, Color.BLACK );
    }
    
    // MUTATOR METHODS
    // Stores x1 passed in as a parameter
    protected void setX1 ( int x1 )
    {
        this.x1 = x1;
    }

    // Stores y1 passed in as a parameter
    protected void setY1 ( int y1 )
    {
        this.y1 = y1;
    }
    
    // Stores x2 passed in as a parameter
    protected void setX2 ( int x2 )
    {
        this.x2 = x2;
    }

    // Stores y2 passed in as a parameter
    protected void setY2 ( int y2 )
    {
        this.y2 = y2;
    }

    // Stores strokeWidth passed in as a parameter
    protected void setStrokeWidth ( int strokeWidth )
    {
        this.strokeWidth = strokeWidth;
    }
    
    // Stores dashLength passed in as a parameter
    protected void setDashLength ( int dashLength )
    {
        this.dashLength = dashLength;
    }
    
    // Stores color passed in as a parameter
    protected void setColor1( Color color1)
    {
        this.color1 = color1;
    }
    
    // Stores color passed in as a parameter
    protected void setColor2( Color color2)
    {
        this.color2 = color2;
    }
    
    // Creates a new GradientPaint object 
    protected void setPaint(Color color1, Color color2)
    {
        paint = new GradientPaint(getX1(), getY1(), color1, getX2(), getY2(), color2, true);
    }
    
    // Creates a new BasicStroke object
    protected void setStroke()
    {
        float dashes[] = { getDashLength() }; // specify dash pattern
        if (dashed)
            stroke =  new BasicStroke( getStrokeWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 
                                      getDashLength(), dashes, 0 );
        else 
            stroke = new BasicStroke(getStrokeWidth());
    }
    
    // Stores dashed variable passed in as a parameter
    protected void setDashed(boolean dashed)
    {
        this.dashed = dashed;
    }
    
    // Stores paintGradient variable passed in as a parameter
    protected void setPaintGradient(boolean paintGradient)
    {
        this.paintGradient = paintGradient;
    }
    
    // ACCESSOR METHODS
    // Returns value x1
    protected int getX1 ()
    {
        return x1;
    }
    
    // Returns value y1
    protected int getY1 ()
    {
        return y1;
    }
    
    // Returns value x2
    protected int getX2 ()
    {
        return x2;
    }
    
    // Returns value y2
    protected int getY2 ()
    {
        return y2;
    }
    
     // Returns strokeWidth
    protected int getStrokeWidth()
    {
        return strokeWidth;
    }
    
    // Returns dashLength
    protected int getDashLength()
    {
        return dashLength;
    }
    
    // Returns value color1
    protected Color getColor1()
    {
        return color1;
    }
    
    // Returns value color2
    protected Color getColor2()
    {
        return color2;
    }
    
    // returns the gradient created
    protected GradientPaint getPaint()
    {
        return paint;
    }
    
    // returns the stroke created
    protected BasicStroke getStroke()
    {
        return stroke;
    }
    
    // returns the dashed value
    protected boolean getDashed()
    {
        return dashed;
    }
    
    // returns the paintGradient value
    protected boolean getPaintGradient()
    {
        return paintGradient;
    }
    
    // Abstract method used to draw shape on screen
    public abstract void draw( Graphics g );
    
}// end class MyShape