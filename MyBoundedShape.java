import java.awt.Color;
/**
 * Unit 2 Assignment - Drawing with Polymorphism
 * 
 * @author Benson Pan
 * 
 * @version December 17, 2014
 */
abstract class MyBoundedShape extends MyShape
{
    private boolean filled;
    
    /* 
     * 6 Parameter Constructor
     * Accepts x1, y1, x2, y2 coordinates, color and filled shape value
     * Stores parameters into corresponding data attributes
     */
    public MyBoundedShape( int x1, int y1, int x2, int y2, Color color, boolean filled )
    {
        super( x1, y1, x2, y2, color);
        this.filled = filled;
    }
    
    /* 
     * 7 Parameter Constructor
     * Accepts x1, y1, x2, y2 coordinates, two colors and filled shape value
     * Stores parameters into corresponding data attributes
     */
    public MyBoundedShape( int x1, int y1, int x2, int y2, int strokeWidth, int dashLength, Color color1, Color color2, 
                          boolean paintGradient, boolean dashed, boolean filled )
    {
        super( x1, y1, x2, y2, strokeWidth, dashLength, color1, color2, paintGradient, dashed);
        this.filled = filled;
    }
    
    /*
     * No Parameter Constuctor
     * Sets x and y coordinates 0, color to black by default and an unfilled shape
     */
    public MyBoundedShape()
    {
        super();
        filled = false;
    }
    
    // MUTATOR METHODS
    // Stores filled passed in as a parameter
    protected void setFilled(boolean filled)
    {
        this.filled = filled;
    }
    
    // ACCESSOR/CALCULATION METHODS
    // Returns value filled
    protected boolean getFilled()
    {
        return filled;
    }
    
    // returns the larger x coordinate
    protected int getUpperLeftX(){
        return Math.min(getX1(), getX2());
    }
    
    // returns the larger y coordinate
    protected int getUpperLeftY(){
        return Math.min(getY1(), getY2());
    }
    
    // returns the calculated width of the shape
    protected int getWidth(){
        return Math.abs( getX1() - getX2() );
    }
    
    // returns the calculated height of the shape
    protected int getHeight(){
        return Math.abs( getY1() - getY2() );
    }

}// end class MyBoundedShape