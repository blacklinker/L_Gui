
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
import java.util.*;

class L_Gui extends JFrame {
    /** default constructor  */
    public L_Gui() {
        initComponents();
    }

    // all the initializations in one method
    private void initComponents() {

        jLabel0		= new JLabel("generation");
        jLabel1		= new JLabel("axiom");
        jLabel2		= new JLabel("rule");
        jLabel3		= new JLabel("angle");
        jLabel4		= new JLabel("scale");
        jLabel5		= new JLabel("x-coor");
        jLabel6		= new JLabel("y-coor");
        jLabel7		= new JLabel("direction");
        jSpace1		= new JLabel("");
        jSpace2		= new JLabel("");
        jSpace3		= new JLabel("");

        jTextField0	= new JTextField();
        jTextField1	= new JTextField();
        jTextField2	= new JTextField();
        jTextField3	= new JTextField();
        jTextField4	= new JTextField();
        jTextField5	= new JTextField();
        jTextField6	= new JTextField();
        jTextField7	= new JTextField();


        drawButton	= new JButton("DRAW");
        loadButton	= new JButton("LOAD" );

        // make a drawing surface
          p = new DrawBot();
          p.setBackground(/*new Color(255, 255, 255*/  Color.white);

        // make the panel of buttons
          controlPanel = new JPanel();
          controlPanel.setPreferredSize( new Dimension(100,600));
          controlPanel.setLayout(new FlowLayout());
          controlPanel.setBackground(new Color(225, 225, 123));

        // set the Layout to FlowLayout
          getContentPane().setLayout(new FlowLayout());

        // add a WindowListener to terminate the program when
        // window is closed
          addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(1);
            }
          });


        // set up drawButton
          drawButton.setPreferredSize( new Dimension(80,25));
          drawButton.setBackground(new Color(0, 0, 235));
          drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	p.repaint();
            	
            }
          });

          jTextField0.setPreferredSize( new Dimension(80,25));
          jTextField1.setPreferredSize( new Dimension(80,25));
          jTextField2.setPreferredSize( new Dimension(80,25));
          jTextField3.setPreferredSize( new Dimension(80,25));
          jTextField4.setPreferredSize( new Dimension(80,25));
          jTextField5.setPreferredSize( new Dimension(80,25));
          jTextField6.setPreferredSize( new Dimension(80,25));
          jTextField7.setPreferredSize( new Dimension(80,25));

          jTextField0.setHorizontalAlignment(JTextField.CENTER);
          jTextField1.setHorizontalAlignment(JTextField.CENTER);
          jTextField2.setHorizontalAlignment(JTextField.CENTER);
          jTextField3.setHorizontalAlignment(JTextField.CENTER);
          jTextField4.setHorizontalAlignment(JTextField.CENTER);
          jTextField5.setHorizontalAlignment(JTextField.CENTER);
          jTextField6.setHorizontalAlignment(JTextField.CENTER);
          jTextField7.setHorizontalAlignment(JTextField.CENTER);

          jSpace1.setPreferredSize( new Dimension(80,20));
          jSpace2.setPreferredSize( new Dimension(80,20));
          jSpace3.setPreferredSize( new Dimension(80,20));

        // Fix up loadButton
          loadButton.setPreferredSize( new Dimension(80,25));
          loadButton.setBackground(new Color(235, 235, 235));

          loadButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt){

            	// **** CODE to read in L_System parameters
            	 try {
            		 Scanner in = new Scanner(new File("L_System1.txt"));

            		 p.S = in.nextLine();
            		 p.rule = in.nextLine();
            		 System.out.println("Axiom: " + p.S + "\nRule: " + p.rule);
            		 p.angle = (int) in.nextDouble();
            		 p.step = in.nextInt();
            		 System.out.println("Angle: "+ p.angle + "\nStep: " + p.step);
            		 p.Xcoor = in.nextInt();
            		 p.Ycoor = in.nextInt();
            		 p.direction= in.nextInt();
            		
            		 
      ;    	 }
            	 catch (FileNotFoundException e){
            		 System.out.println("NO File found");
            		 System.exit(0);
            	 }
             }
          });

        // Add the components to the control panel
          controlPanel.add(jSpace1);
          controlPanel.add(jTextField1);
          controlPanel.add(jLabel1);
          controlPanel.add(jTextField2);
          controlPanel.add(jLabel2);
          controlPanel.add(jTextField3);
          controlPanel.add(jLabel3);
          controlPanel.add(jTextField4);
          controlPanel.add(jLabel4);
          controlPanel.add(jTextField5);
          controlPanel.add(jLabel5);
          controlPanel.add(jTextField6);
          controlPanel.add(jLabel6);
          controlPanel.add(jTextField7);
          controlPanel.add(jLabel7);
          controlPanel.add(jSpace2);
          controlPanel.add(loadButton);
          controlPanel.add(drawButton);
          controlPanel.add(jSpace3);
          controlPanel.add(jTextField0);
          controlPanel.add(jLabel0);
          
          jTextField1.setText("F");
          jTextField2.setText("FF-[-F+F+F]+[+F-F-F]");
          jTextField3.setText("22.5");
          jTextField4.setText("5");
          jTextField5.setText("300");
          jTextField6.setText("400");
          jTextField7.setText("-90");
          jTextField0.setText("5");
          
          
          

          
        // add the panels to the L_GUI
          getContentPane().add(controlPanel);
          getContentPane().add(p);
          pack();
          setVisible(true);
    } // _________  end of InitComponents


 ////////////   the DRAWBOT class ////////////////////////////////

   private static class  DrawBot extends JPanel{
	   private String axiom;
	   private String rule;
	   private int angle;	// in degrees
	   private int step;	// units in a forward motion
	   private int Xcoor;	// initial x-coordinate position
	   private int Ycoor;	// initial y-coordinate position
	   private int direction;
	   private int generation;
	   private int MAX_Gen;	// maximum # of generations
	   private String S;

      public DrawBot(){
         setBackground(Color.pink);
         setPreferredSize( new Dimension( 600,600));
         generation = -1;
         S = "";
         
         
      }

      

      public void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println(" Drawbot paintcomponent called");
        setProduction(g);
        newRule();
      }
      
      private int sign=0;
      private int oldx=0;
      private int oldy=0;
      private int newx=0;
      private int newy=0;
      
      private Stack<Integer> x = new Stack<Integer>();

      
      
      
      public int setProduction(Graphics g){
    	  System.out.println(S);
    	  oldx = Xcoor;
    	  System.out.println(Xcoor);
    	  oldy = Ycoor;
    	  System.out.println(Ycoor);
    	  System.out.println("setProduction");
    	  int i =0;
          while(i<S.length()){
              switch(S.charAt(i)){
                  case 'F':	System.out.println("F"); 
                  			i++;setPosition();
                  			System.out.println("newdraw");
                  			g.drawLine(oldx,oldy,newx,newy);
                  			oldx= newx;
                  			oldy= newy;
                  			break;
                  case '-':System.out.println("-"); i++;sign++;break;
                  case '+':System.out.println("+");i++;sign--;break;
                  case '[': System.out.println(x);i++;x.push(sign);x.push(oldx);x.push(oldy);break;
                  case ']':	System.out.println(x);i++;oldy = x.pop();oldx = x.pop();sign = x.pop();break;
                 
              }
          }
          return 0;
      }
      
      protected void setPosition(){
          double radians = Math.toRadians(direction+sign*angle);
          newx = (int)((Math.cos(radians))*step)+oldx;  
          System.out.println(oldx+" "+newx);
          newy = (int)((Math.sin(radians))*step)+oldy;
          System.out.println(oldy+" "+newy); 
      }
      
      protected void newRule() {
    	  S=S.replaceAll("F", rule);
      }
      
      
      
      
      
      
      
   }
   	
   
   

 // Variable declarations
    private JLabel		jLabel0;
    private JLabel		jLabel1;
    private JLabel		jLabel2;
    private JLabel		jLabel3;
    private JLabel		jLabel4;
    private JLabel		jLabel5;
    private JLabel		jLabel6;
    private JLabel		jLabel7;
    private JLabel		jSpace1;
    private JLabel		jSpace2;
    private JLabel		jSpace3;

    private static JTextField	jTextField0;
    private static JTextField	jTextField1;
    private static JTextField	jTextField2;
    private static JTextField	jTextField3;
    private static JTextField	jTextField4;
    private static JTextField	jTextField5;
    private static JTextField	jTextField6;
    private static JTextField	jTextField7;

    private JButton		drawButton;
    private JButton		loadButton;

    private DrawBot 	p;
    private JPanel	  	controlPanel;


   static public void main( String[] args){

   	L_Gui l = new L_Gui();

   }
}
