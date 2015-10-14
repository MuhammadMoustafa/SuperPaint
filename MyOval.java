import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * Unit 2 Assignment - Drawing with Polymorphism
 * 
 * @author Benson Pan
 * 
 * @version December 17, 2014
 */
public class MyOval extends MyBoundedShape
{
    /*
     * 6 Parameter Constructor
     * Accepts coordinates, color, and filled value and calls the superclass's constructor with the values
     */
    public MyOval( int x1, int y1, int x2, int y2, Color color, boolean filled )
    {
        super( x1, y1, x2, y2, color, filled);
    }
    
    /*
     * 7 Parameter Constructor
     * Accepts coordinates, two colors, and filled value and calls the superclass's constructor with the values
     */
    public MyOval( int x1, int y1, int x2, int y2, int strokeWidth, int dashLength, Color color1, Color color2, 
                  boolean paintGradient, boolean dashed, boolean filled )
    {
        super( x1, y1, x2, y2, strokeWidth, dashLength, color1, color2, paintGradient, dashed, filled);
    }
    
    /*
     * No Parameter Constructor
     * Calls the default superclass's constructor
     */
    public MyOval()
    {
        super();
    }
    
    // method used to create a oval on the Graphics object g
    public void draw( Graphics g )
    {
        // Convert to a Graphics2D object
        Graphics2D g2 = (Graphics2D) g;
        // paint gradient if the gradient object is selected
        if ( getPaintGradient() )
            g2.setPaint( getPaint() );
        else
            g2.setPaint( getColor1() );
        
            g2.setStroke( getStroke() );
        
        if (getFilled()){
            // draws a filled/unfilled rectangle based on the filled instance variable of the superclass
            g2.fill(new Ellipse2D.Double(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
        }
        else{
            g2.draw(new Ellipse2D.Double(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
        }
    }
    
}// end class MyOval