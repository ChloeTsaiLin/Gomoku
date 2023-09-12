package tw.org.iii.hw;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.Style;

import tw.org.iii.classes.FivePointPanel;




public class FivePoint extends JPanel{
	private FivePointPanel fpp;
	private JButton restart;
	private JLabel turn;

	public FivePoint() {
//		super("Free-style Gomoku");
		setLayout(new BorderLayout());
		
		JPanel top = new JPanel(new GridLayout(2, 1));		
		JPanel toplayer1 = new JPanel(new FlowLayout());		
		JPanel toplayer2 = new JPanel(new FlowLayout());		
		JPanel bottom = new JPanel(new FlowLayout());		
		fpp = new FivePointPanel();
		
		restart = new JButton("restart");
		turn = new JLabel("player"+ fpp.getTurn()+"'s turn");
		turn.setFont(new Font("Arial", Font.BOLD, 16));

		Judge judge = new Judge(); 
		Post post = new Post();
		
		PlayerInf player1 = new PlayerInf(1);
		PlayerInf player2 = new PlayerInf(2);

		top.add(toplayer1);
		top.add(toplayer2);
		toplayer1.add(judge);
		toplayer2.add(post);
		bottom.add(restart);
		
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(fpp, BorderLayout.CENTER);
		add(player1, BorderLayout.WEST);
		add(player2, BorderLayout.EAST);
		
//		setSize(1080, 960);
//		setSize(680, 600);
//		setVisible(true);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fpp.init();
			}
		});
		
		
	}
	

	class PlayerInf extends JPanel {
		private JLabel labelp;
		private PiecePanel pieceColor;
	
		public PlayerInf(int player) {
			setLayout(new GridLayout(8,1));
			pieceColor = new PiecePanel(player);
			add(pieceColor);
			
			labelp = new JLabel("  \t  player" + Integer.toString(player) +"  \t  ");
			labelp.setFont(new Font("Arial", Font.BOLD, 20));			
			add(labelp);
			TurnLabel labels = new TurnLabel(player, fpp.getTurn());
			labels.setAlignmentX(RIGHT_ALIGNMENT);
			add(labels);		
		}
		
		private class PiecePanel extends JPanel{ 
			Color color;
			
			public PiecePanel(int player) {
				if(player ==1) {
					color = Color.black;
				} else if(player ==2) {
					color = Color.white;
				}
			}
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(color);
				
				int r;
				if(this.getHeight()> this.getWidth()/2) {
					r = this.getWidth()/2;
				}else {
					r = this.getHeight();
				}
				
				g2d.fillOval(this.getWidth()/4,0, r, r);
				g2d.setStroke(new BasicStroke(1));
				g2d.setColor(Color.black);
				g2d.drawOval(this.getWidth()/4,0, r, r);
			}

		}
		private class TurnLabel extends JLabel{
			private Timer timer;
			
			public TurnLabel(int player, int turn) {
				Color thinkcolor = new Color(230, 126, 34);
				timer = new Timer();
				if(player == turn) {
					timer.schedule(new TurnTask1(), 0, 200);
				} else {
					timer.schedule(new TurnTask2(), 0, 200);
				}
				setFont(new Font(null, (Font.ITALIC+Font.BOLD), 16));
				setForeground(thinkcolor);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
			
			private class TurnTask1 extends TimerTask{
				@Override
				public void run() {
					if(fpp.getTurn() ==1 ) {
						setText("thinking...");
					} else{
						setText("  ");
					}

				}
			}
			private class TurnTask2 extends TimerTask{
				@Override
				public void run() {
					if(fpp.getTurn() ==2 ) {
						setText("thinking...");
					} else{
						setText("  ");
					}
				}
			}
		}
	}
	
	public class Judge extends JLabel{
		private Timer timer;
		public Judge() {
			timer = new Timer();
			timer.schedule(new WinTask(), 0, 300);
		}
		
		private class WinTask extends TimerTask{
			@Override
			public void run() {
				setText(fpp.getwinner());
				setFont(new Font(null, Font.CENTER_BASELINE, 40));
				setForeground(Color.red);
			}
		}
	}
	
	class Post extends JLabel{
		private Timer timer;
		public Post() {
//			if(fpp.getWarning().contentEquals(new StringBuffer("c"))) {
				timer = new Timer();
				timer.schedule(new PostTask(), 500, 200);
//			} 
		}
		private class PostTask extends TimerTask{
			@Override
			public void run() {
				setText(fpp.getWarning());
				setFont(new Font(null, Font.CENTER_BASELINE, 14));
				setForeground(Color.black);
			}
		}
	}
	
	public static void main(String[] args) {
		new FivePoint();
	}

}



