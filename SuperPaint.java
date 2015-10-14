import javax.swing.JFrame;
import java.awt.Dimension;
/**
 * @author Benson Pan
 * 
 * @version April 8, 2015
 * 
 * SuperPaint Test Class - instantiate DrawFrame
 */
public class SuperPaint
{
   public static void main( String args[] )
   { 
      DrawFrame superPaint = new DrawFrame(); 
      superPaint.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      superPaint.setMinimumSize( new Dimension (1200, 700) ); // set frame size
      superPaint.setVisible( true ); // display frame
   } // end main
} // end class SuperPaint