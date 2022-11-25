/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package display;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import game.gamerun.Charactor.*;
import Element.Element;
import event.Event;


/** 
 *
 * @author BIG GER
 */

public class Game extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	private static int speed = 50,dogSize = 60 ,waveHeight = 50;
	private static int base=400,xStart = 1000;
	private long point = 0,lastPress=0;
	
	private Rabbit rabbit = new Rabbit(100,base-50);
	static Display display;
//	------------------Wave SIze ----------------------------
	private Wave[] waveSet = makeWave(4);
//--------------------Cloud--------------------------------
	private Environment[] envSet = makeEnv(2,Environment.CLOUD);
	private Environment forest = new Environment(xStart-100,base-150,this,Environment.BUILDING,4);
	
		public Game(){
		this.setBounds(0,0,1000,600);
		this.addKeyListener(this);
		this.setLayout(null);
		this.setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g) {
			try {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				this.drawBackground(g2);
				//---POINT----
				g2.setFont(Element.getFont(30));
				g2.setColor(Color.white);
				g2.drawString("Point : "+point,750,40);
				//--- dog --
				g2.setColor(Color.RED);
				drawDogHealth(g2);
				g2.drawImage(rabbit.getImage(),rabbit.x,rabbit.y,dogSize,dogSize, null);
				//----Wave----
				for(Wave item : waveSet) {
					drawWave(item,g2);
				}
				this.point+=1;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	private void drawBackground(Graphics2D g2) throws IOException {
			g2.drawImage(ImageIO.read(new File("img\\sky.png")),0,0,2000,1000, null);
			g2.drawImage(forest.getImage(),forest.x,forest.y,500,200,null);
			g2.drawImage(ImageIO.read(new File("img\\dir.png")),0,base+10,2000,220, null);
			for(Environment item:envSet) {
				g2.drawImage(item.getImage(),item.x,item.y,250,160, null);
			}
	}
	
	private void drawDogHealth(Graphics2D g2) {
		try {
			g2.drawImage(ImageIO.read(new File("img\\heart.png")),10,20, 20,20,null);
			g2.setStroke(new BasicStroke(18.0f));
			g2.setColor(new Color(241, 98, 69));
			g2.drawLine(60, 30,60+rabbit.health,30);	
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(6.0f));
			g2.drawRect(50,20, 200,20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Wave[] makeWave(int size) {
		Wave[] waveSet = new Wave[size];
		int far = 500;
		for(int i=0;i<size;i++) {
			waveSet[i] = new Wave(xStart+far,base,speed,this);
			far+=500;
		}
		return waveSet;
	}
	
	private Environment[] makeEnv(int size,int eType){
		Environment[] envSet = new Environment[size];
		int far = 0;
		for(int i=0;i<size;i++) {
			envSet[i] = new Environment(xStart+far,20,this,eType,10);
			far+=600;
		}
		return envSet;
	}
	
	private void drawWave(Wave wave,Graphics2D g2) {
			g2.drawImage(wave.getImage(),wave.x ,(wave.y-waveHeight),40,waveHeight+10,null);
			if(Event.checkHit(rabbit,wave,dogSize,waveHeight)){
					g2.setColor(new Color(241, 98, 69));
					g2.fillRect(0, 0,1000,1000);			
					rabbit.health-=20;
					if(rabbit.health<=0) {
						display.endGame(this.point);
						rabbit.health = new Rabbit().health;
						this.point = 0;	
					}
			}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(System.currentTimeMillis() - lastPress > 600) {
			if(e.getKeyCode()==32||e.getKeyCode()==38) {
					rabbit.jump(this);
					lastPress = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//---
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//---
	}
	
	public static void main(String[] arg) {
		 display = new Display();
	}
}
