import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
/**
 * Unit 2 Assignment - Drawing with Polymorphism
 * 
 * @author Benson Pan
 * 
 * @version December 17, 2014
 */
public class MyLine extends MyShape
{
    /*
     * 5 Parameter Constructor
     * Accepts coordinates and color, and calls the superclass's constructor with the values
     */
    public MyLine( int x1, int y1, int x2, int y2, Color color )
    {
        super( x1, y1, x2, y2, color);
    }
    
    /*
     * 6 Parameter Constructor
     * Accepts corrdinates and two colors, and calls the superclass's constructor with the values
     */
    public MyLine( int x1, int y1, int x2, int y2, int strokeWidth, int dashLength, Color color1, Color color2,
    boolean paintGradient, boolean dashed )
    {
        super( x1, y1, x2, y2, strokeWidth, dashLength, color1, color2, paintGradient, dashed );
    }
    
    /*
     * No Parameter Constructor
     * Calls the default superclass's constructor
     */
    public MyLine()
    {
        super();
    }
    
    // method used to create a line on the Graphics object g
    public void draw( Graphics g )
    {
        // Convert to a Graphics2D object
        Graphics2D g2 = (Graphics2D) g;
        // paints gradient if gradient object is selected
        if ( getPaintGradient() )
            g2.setPaint( getPaint() );
        else
            g2.setPaint( getColor1() );

        g2.setStroke( getStroke() );
        g2.draw( new Line2D.Double(getX1(), getY1(), getX2(), getY2()) );
    }
    
}// end class MyLine