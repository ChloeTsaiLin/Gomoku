package tw.org.iii.hw;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;




public class FivePointOutter{
	 public static void main(String args[]) 
	 { 
		 new FivePoint11();
	 }
}

class FivePoint11 extends JFrame{
//	private MenuBar menubar;
//	private Menu menu;
//	private MenuItem item1, item2;
//	private JPanel contentPanel;
	
	public FivePoint11() {
		super("Free-style Gomoku");
//		menubar = new MenuBar();
//		menu = new Menu("menu");
//		item1 = new MenuItem("item1");
//		item2 = new MenuItem("item2");
//		contentPanel = new JPanel();
//		
//		item1.addActionListener(this);
//		item2.addActionListener(this);
//		
//		menu.add(item1);
//		menu.add(item2);
//		menubar.add(menu);
//		setMenuBar(menubar);
		
		this.setContentPane(new MyPanel("Free-style Gomoku"));
		
		setSize(680, 600);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void changeContentPane(Container contentPanel) {
		this.setContentPane(contentPanel);
//		this.revalidate();
	}
	
	class MyPanel extends JPanel {
		private JButton enter;
		private JPanel inner2;
		private JLabel[] lanes;
		private Gomoku[] titleAnime;
		
	    public MyPanel(String msg) {
	    	JLabel message = new JLabel(msg);
	    	message.setFont(new Font(null, Font.BOLD, 50));
	    	message.setHorizontalAlignment(JLabel.CENTER);
	        setLayout(new GridLayout(3,1));
	        add(message);       

			lanes = new JLabel[1];
			for(int i= 0; i< lanes.length; i++) {
				lanes[i] = new JLabel();
				lanes[i].setFont(new Font(null, Font.BOLD, 20));
				lanes[i].setHorizontalAlignment(JLabel.CENTER);
				add(lanes[i]);
			}
			titleAnime = new Gomoku[1];
			for(int i= 0; i< titleAnime.length; i++) {
				titleAnime[i] = new Gomoku(i);
				titleAnime[i].start();
			}

	        inner2 = new JPanel();
	        enter = new JButton("ENTER");
	        inner2.add(enter);
	        add(inner2);
	        
	        enter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					changeContentPane(new FivePoint());
					
				}
			});
	    }
	    
	    private class Gomoku extends Thread {
			private int lane;
			
			public Gomoku(int lane){
				this.lane = lane;
			}
			
			@Override
			public void run() {
				for(int i = 0; i< 5; i++) {
					try{
						lanes[lane].setText(lanes[lane].getText()+ "● ");
						Thread.sleep(500);
					} catch(InterruptedException e) {
						break;
					}
				}
			}
		}
		
	}
}

