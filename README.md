# SuperPaint
A paint program in Java, using the Swing API.

Features:
- Use of Swing components, such as JFrame, JPanel, JInternalFrame, etc.
- Non-API linked list data structure
- Object Oriented Programming practices (use of inheritance, polymorphism, encapsulation)

Classes:
MyShape
  - The base class, used to store values associated with the shapes
  - Contains accessor and mutator methods for all its variables
MyBoundedShape
  - Inherits from the MyShape class
  - Additional boolean flag to indicate whether the shape is filled or not
  - Contains methods for bounded shapes (i.e calculates dimensions)
MyLine
  - Inherits from the MyShape class
  - Contains various constructors and method to draw the shape
MyRectangle
  - Inherits from the MyBoundedShape class
  - Contains various constructors and method to draw the shape
MyOval
  - Inherits from the MyBoundedShape class
  - Contains various constructors and method to draw the shape
ListNode
  - Node class used in linked list
  - Contains accessor and mutator methods for its data and pointer
LinkedList
  - Varibles to indicate size of linked list, and front and end of the linked list
  - Contains accessor and mutator methods to handle the list of nodes
PreferencePane
  - Encapsulates preference window GUI objects for use in DrawFrame class
  - Handles its events and file output of the preference window
DrawFrame
  - Handles all GUI objects, and interacts with DrawPanel
DrawPanel
  - Contains all variables used to handle events 
SuperPaint 
  - Main program; creates an instance of the DrawFrame class
  
  
- Benson Pan
