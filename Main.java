import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class Main 
{
    public static void main(String[] args)
    {
		KeyboardReader reader = new KeyboardReader();
		System.out.println("Hello to Hoppy Rabbit. \n Sir McFluffles is trying his best to get on the other side of the rift. You have to help him but jumping onto the incoming blocks. By eating a fruit Sir MCFluffles can jump higher"+
		"\n\n Press Up arrow is to jump up \n Press Left Arrow to move to the left. \n Press Right Arrow to move to the right\n If you try hard enough u can go through the blocks and you can't eat all the fruits \n\n \t Press enter to continue to the game");
		
		String enter = reader.readLine();// telling the user about the games and getting them ready . 

		if(enter.equals(""))
		{
			Toolkit toolkit= Toolkit.getDefaultToolkit();// need this for images and stuff 
			Dimension screenSize=toolkit.getScreenSize();// my panel and stuff within need to fit within many screen size
			myJFrame frame = new myJFrame((int)screenSize.getWidth(),(int)screenSize.getHeight(), "Hoppy Rabbit");//creating a new fram and sending in the height, width, and the name of the frame. 
			frame.show();//setting the frame so it can pop up. 
			frame.startGame();//starts the game 
		}
    }
    

}
class myJFrame extends JFrame
{
    BeginningJPanel panel;
	public myJFrame(int x, int y, String menu)
	{
		setSize(new Dimension(x,y));//frame is the size of the screen. 
		
		
		KeyList KL= new KeyList();// new keylistern adding it to the frame. sending in to the Jpanel 
		addKeyListener(KL);
		panel = new BeginningJPanel(this, KL);// sending in the Jframe and the listener
		Container container = getContentPane();//adding panel to the frame. 
		container.add(panel);
		
		repaint();//making the frame appear. 
		
	}

    public void startGame()
	{
		panel.startGame();//it's starting the game. 
	}
}

class KeyList implements KeyListener
{
	//keylistener pretty self explanatory . If someone presses this then one of the variable is true. 
	public static final boolean plusx = false;
    boolean up, down, left, right, enter, spin;
	public void keyPressed(KeyEvent e)
	{
        
		switch(e.getKeyCode())
		{
			case 27:/* esc */			
			case KeyEvent.VK_E:		System.exit(0);
			
			case KeyEvent.VK_LEFT:		left = true;	break;
			case KeyEvent.VK_RIGHT:		right= true;	break;
			case KeyEvent.VK_UP:		up = true;		break;
			case KeyEvent.VK_DOWN:		down = true;	break;
			case KeyEvent.VK_ENTER:		enter = true;	break;
			case KeyEvent.VK_A:		    left = true;	break;
			case KeyEvent.VK_D:		    right= true;	break;
			case KeyEvent.VK_W:		    up = true;		break;
			case KeyEvent.VK_SPACE:     up = true;     break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:		left = false;	break;
			case KeyEvent.VK_RIGHT:		right= false;	break;
			case KeyEvent.VK_UP:		up = false;		break;
			case KeyEvent.VK_DOWN:		down = false;	break;
			case KeyEvent.VK_ENTER:		enter = false;	break;
			case KeyEvent.VK_A:			left = false;	break;
			case KeyEvent.VK_D:			right= false;	break;
			case KeyEvent.VK_W:			up = false;		break;
			case KeyEvent.VK_SPACE:		up = false;	break;
		}
	}
		
	public void keyTyped(KeyEvent e){}
}
class BeginningJPanel extends JPanel
{
    Toolkit toolkit= Toolkit.getDefaultToolkit();
    Dimension screenSize=toolkit.getScreenSize();
    int width=(int)screenSize.getWidth();
    int Height=(int)screenSize.getHeight();
    Rabbit rabbit;
    myJFrame Frame;
    KeyList KL;
    boolean startgame=true;
	everything display;
	public int count =0;
	int counttill=101;
	JTextField done;
	Image background = Toolkit.getDefaultToolkit().getImage("sky1.png");

	//all of these are varabibles in need it hink idk man. 

    
	public BeginningJPanel(myJFrame frame, KeyList kl)
	{

		//set Background Color
        //setting it so we can change positions inside the JPanel
        super.setLayout(null);
        Frame=frame;//instances variable 
        KL=kl;//keylisterner 
		rabbit= new Rabbit(width, Height, KL);//creating a rabbit and sending in the width and height so it doesn't go off to the left or right. 
		display= new everything(width, Height);//this creates fruits and blocks 
		
	}

    public void startGame()
	{
		while(true)
		{
			repaint();//while the game is running repaints the frame. 
			
		}
	}
	public void paintComponent(Graphics g)
	{
        
		super.paintComponent(g);// calling the super paintcompent 
		
		g.drawImage(background, 0,0, width, Height, null);//drawing the sky background 
		
		if(KL.left)//if left is pressed then we call the turnleft method 
			rabbit.turnLeft();
		if(KL.right)//if right is pressed then we call the turnright method 
			rabbit.turnRight();

		if(onblock(display)|| count!=0)//this checks if the rabbit in on the block or that it's still in the air. 
		{	if(KL.up)//if the player is pressing the up button then we jump up. 
				{	count++;
					if(count<counttill)//this help allows the player to jump for a long period of time. smooth animation. 
						rabbit.turnUp(count, counttill);//sending when count is and what's the make the count can be 
					else	
					{
						count=0;//if it's stops then we create pause and reset count to 0;
						pause(5);
					}	
				}
			else 
				{// if the player is not jumping anymore then we set the count to 0; 
					count=0;
					if(KL.left)
						rabbit.turnLeft();
					if(KL.right)
						rabbit.turnRight();
				}	
			}

		if(!onblock(display) && count==0)// if rabbit is not a block then we just make it go down down down . 
			rabbit.turnDown();

	
		atefruit(display);// seeing if it ate anything. 

        rabbit.draw(g,Frame,onblock(display));  //drawing the rabbit . 
		display.draw(g,Frame);// drawing the blocks and the fruits. 

		if(rabbit.y>Height+70)// if it falls of the map 
		{
			g.setColor(new Color(0, 0, 0, 128)); // Black with 50% opacity
            g.fillRect(0, 0, width, Height); // Fill the entire panel with the semi-transparent rectangle
            g.setColor(Color.WHITE); // White color for the text
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message = "Rabbit fell off the map";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            int messageX = (width - messageWidth) / 2; // Center the text horizontally
            int messageY = Height / 2; // Center the text vertically
            g.drawString(message, messageX, messageY);

			pause(2000);

			Frame.removeAll();

			repaint();

		}
		if(rabbit.y<200 & rabbit.x>1720)// if it reach the top block and the goal. 
		{
			g.setColor(new Color(0, 0, 0, 128)); // Black with 50% opacity
            g.fillRect(0, 0, width, Height); // Fill the entire panel with the semi-transparent rectangle
            g.setColor(Color.WHITE); // White color for the text
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message = "Rabbit reached it's destination . Thank you";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            int messageX = (width - messageWidth) / 2; // Center the text horizontally
            int messageY = Height / 2; // Center the text vertically
            g.drawString(message, messageX, messageY);

			pause(2000);

			Frame.removeAll();

			repaint();

		}

		
        
		pause(5);


	}

	
	public static void pause(int millis)//allow to pause the game. 
	{
		int start = (int) System.currentTimeMillis();
		
		while (((int)System.currentTimeMillis()) < start + millis)
		{
			// pause during this loop
		}
		
	}
	public boolean onblock(everything g)//going through every one of the blocks and seeing the rabbit is sitting on it . if it is then we return true or else false; 
	{
		for(blocks check: display.blocks)
		{
			if(check.y - rabbit.y<75 && check.y - rabbit.y>70)
			{
				if(rabbit.x-check.x>-40 && rabbit.x-check.x<240 )
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean atefruit(everything g)// seeing it's x and y is within the fruit . if it is then we increase countill so the rabbit can jump higher and delete that fruit from the display . 
	{

		for(int i=0; i<display.all.size();i++)
		{
			fruit fruit = display.all.get(i);
			if(fruit.y - rabbit.y<0 && fruit.y - rabbit.y>-40)
			{
				if(rabbit.x-fruit.x>0 && rabbit.x-fruit.x<40 )
				{
					
					counttill+=20;
					display.all.remove(i);
					i--;
				}
			}
		
		}

		return false;

	}

}



class Rabbit {
    public int x;  // X coordinate of the rabbit

    public int y;//y coordinate of the rabbit 
    private int Width;//height and width of the screen 
    private int Height;
	private KeyList KL;// keylistener pointer 
    private Image image;  // Image of the rabbit


    public Rabbit(int width, int height, KeyList k1) {
        x =0;  // Initial X coordinate in the middle of the screen
        y = 600;  // Initial Y coordinate in the middle of the screen
        image = Toolkit.getDefaultToolkit().getImage("Bunny Right Arrow Resting.png");
        Width=width;
        Height=height;
		KL= k1;
    }

    public void draw(Graphics g , myJFrame MyJFrame , boolean onblock) 
    {
        	g.drawImage(image, x,y,70,70, MyJFrame ); //drawing the rabbit. 

    }

    public void turnLeft()
	{
		image=Toolkit.getDefaultToolkit().getImage("Bunny Left Arrow Resting.png");// if it's turns left then we change the iamge and the x value; 
		if(x>0)//making sure it doesn't go off the map
        	x-=2;
        
	}
	public void turnRight()
	{
		image=Toolkit.getDefaultToolkit().getImage("Bunny Right Arrow Resting.png");// if it's turns right then we change the image and increase the x value;
		if(x<Width-85) //maping sure it doesn;t go off the map. 
		x+=2;
	}
	public void turnUp(int count, int  counttill)// the jump up and down. 
	{
		if(count< counttill/2)// for the first half it goes up and then it comes down for the other half . 
		{	y-=3;//making it go highers and higher 
			if(KL.right && x<Width-85)
			{
				image=Toolkit.getDefaultToolkit().getImage("Bunny Right Arrow Jumping.png");//making the jumping animation and changing the x value 
				x+=2;
			}
			else if(KL.left && x>0)
			{
				image=Toolkit.getDefaultToolkit().getImage("Bunny Left Arrow Jumping.png");//making the jumping left animation and changing the x values 
				x-=2;
			}
		}
		else
		{
			y+=2;//dcreasing the values for the second half so it looks like a jump. 
			if(KL.right && x<Width-85)
			{
				image=Toolkit.getDefaultToolkit().getImage("Bunny Right Arrow Jumping.png");//making the jumping left animation and changing the x values 
				x+=2;
			}
			else if(KL.left && x>0)
			{
				image=Toolkit.getDefaultToolkit().getImage("Bunny Left Arrow Jumping.png");//making the jumping left animation and changing the x values 
				x-=5;
			}
		}
		
		
		
	}
	public void turnDown()// making it go down when it's not on a block. 
	{
		image=Toolkit.getDefaultToolkit().getImage("Bunny Down Arrow Resting.png");
		y+=2;
		
	}
	

    public int getX()// accessor methods 
    {return x;}

    
    public int getY()
    {return y;}
}

class everything
{
	public ArrayList <fruit> all = new ArrayList <fruit>();//arraylist of all the fruits that were created 
	public ArrayList <blocks> blocks= new ArrayList <blocks>();//arraylist of all of the blocks that were created 
	
	private int Width;//screen width so we can put them randomly display  in the middle of the screen. 
	private int count = 0; //count the number of time so we don't just have a lot of blocks and fruits 

	public everything(int width, int Height)
	{
		Width=width-700;
		blocks.add(new blocks(0,Height-400, true));// stationary blocks that will not move , the one were the rabbit starts and the one were rabbit needs to go to. 
		blocks.add(new blocks(width-200, 200, true));
	}

	public void draw (Graphics g, myJFrame frame)
	{
		if(count==50)// when we have enough calls 
		{
			int which= (int) (Math.random()*4) +1 ;// math.random to created fruits or blocks 

			if(which==1||which==2)//if which is 1 or 2 then we create a fruit 
				addfruit();
			if(which==2 || which==3||which==4)//if which is 2 3 of 4 we create a block.
				addBlock();
			
			count=0;
		}
		else
			count++;

		for(fruit onefruit:all)
			{
				onefruit.draw(g,frame);//drawing each of the fruits 
			}

		for(blocks block:blocks)
			{
				block.draw(g,frame);//drawing each one of the blocks 
			}
	}

	public void addfruit()
	{
		int X= (int)(Math.random()*Width)+50;// randomly decides the x value for the blocks 
		
		if(all.size()>0)// checks if there is even on fruit 
		{
			if(Math.abs( all.get(all.size()-1).x - X)>300) // if the new x value is too close to the one that was just created last time we just recall the method until it's far away. 
				all.add(new fruit(X ,-20));
			else
				addfruit();
		}
		else	
				all.add(new fruit(X ,-20));// if it's the first fruit. 
	}
	public void addBlock()
	{
		int X= (int)(Math.random()*Width)+150;
		
		if(blocks.size()>0)
		{
			if(Math.abs( blocks.get(blocks.size()-1).x - X)>200) // if the new x value is too close to the one that was just created last time we just recall the method until it's far away. 
				blocks.add(new blocks(X ,-20, false));
			else
				addBlock();
		}
		else	
				blocks.add(new blocks(X ,-20, false));// if it's the first block. 
	
	}
	
}
class fruit
{

	public int x;
	public int y;
	private Image fruitimage;
	private int speed;

	public fruit(int X, int Y)
	{
		fruitimage= getImage((int) (Math.random()*8) + 1);//random image 
		x=X;//sends in a random x value it needs to get displayed at. 
		y=Y;
		speed=(int)Math.random()*2 + 1;//each one as a random speed. 
	}

	public void draw(Graphics g, myJFrame frame)
	{
		g.drawImage(fruitimage, x, y+2, 40, 40, frame);//redrawing the fruit so it looks like it moving. 
		y+=speed;
	}


	public Image getImage(int i)//random image generator for the fruits 
	{
		if(i==1)
			return Toolkit.getDefaultToolkit().getImage("apple.png");
		if(i==2)
			return Toolkit.getDefaultToolkit().getImage("apple2.png");
		if(i==3)
			return Toolkit.getDefaultToolkit().getImage("banana.png");
		if(i==4)
			return Toolkit.getDefaultToolkit().getImage("grapes.png");
		if(i==5)
			return Toolkit.getDefaultToolkit().getImage("lemon.png");
		if(i==6)
			return Toolkit.getDefaultToolkit().getImage("mango.png");
		if(i==7)
			return Toolkit.getDefaultToolkit().getImage("orange.png");
		if(i==8)
			return Toolkit.getDefaultToolkit().getImage("pear.png");

	return null;
		
	}
}

class blocks
{
	public int x;// where it is randomly assigned 
	public int y;
	private boolean special;//stationary of not 

	public blocks(int X , int Y, boolean a)
	{
		x=X;
		y=Y;
		special=a;//stationary of not. 
	}

	public void draw (Graphics g, myJFrame frame)
	{
	
		g.drawImage(Toolkit.getDefaultToolkit().getImage("block.png"),x,y,200,50,frame);// drawing it 
		if(special)
		{
			//if it's special that we don't move it at all. 
		}
		else 
			y+=1;
	}
}

