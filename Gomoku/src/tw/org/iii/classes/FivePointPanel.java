package tw.org.iii.classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class FivePointPanel extends JPanel{ 
	private int[][] chessRecord;
	private int turn, winner;
	private int grid = 15;
	private int n = grid +1;
	private String warning ="  ";
	
	public FivePointPanel() {
		Color chesscolor = new Color(223, 169, 85);
		setBackground(chesscolor);
		
		MouseLisAdp mouseLisLdp = new MouseLisAdp(); 
		addMouseListener(mouseLisLdp);
		init();
	}
	
	public void init() {
		winner = 0;
		turn =1;
		chessRecord = new int[n][n];
		repaint();

//		for(int[] cr:chessRecord) {
//			for(int r :cr) {
//				System.out.print(r);
//			}
//			System.out.print("\n");
//		}
//		System.err.println("-----");
		
	}
	
	public int getTurn() {
		return turn;
	}
	public String getWarning() {
		return warning;
	}
	public String getwinner() {
		if(winner == 1 || winner == 2)	{
			return String.format("player%s won!", winner);
		} else if (winner == 3 ){
			return "Tie!";
		}else {
			return " ";
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		
		float wn = (float)this.getWidth()/n;
		float hn = (float)this.getHeight()/n;
		
		for (int i = 1; i< n; i++) {			
			g2d.drawLine( (int)(1*wn), (int)(i*hn), (int)((n-1)*wn), (int)(i*hn));
			g2d.drawLine( (int)(i*wn), (int)(1*hn), (int)(i*wn), (int)((n-1)*hn));
		}
		
		double dot = 0.3;
		double nReciprocal = (double)1/n;
		g2d.fillOval((int)(this.getWidth()*nReciprocal*(4-dot*0.5)),(int)(this.getHeight()*nReciprocal*(4-dot*0.5)), 
				(int)(this.getWidth()*nReciprocal*dot), (int)(this.getHeight()*nReciprocal*dot));
		g2d.fillOval((int)(this.getWidth()*nReciprocal*(4-dot*0.5)),(int)(this.getHeight()*nReciprocal*(12-dot*0.5)), 
				(int)(this.getWidth()*nReciprocal*dot), (int)(this.getHeight()*nReciprocal*dot));
		g2d.fillOval((int)(this.getWidth()*nReciprocal*(8-dot*0.5)),(int)(this.getHeight()*nReciprocal*(8-dot*0.5)), 
				(int)(this.getWidth()*nReciprocal*dot), (int)(this.getHeight()*nReciprocal*dot));
		g2d.fillOval((int)(this.getWidth()*nReciprocal*(12-dot*0.5)),(int)(this.getHeight()*nReciprocal*(4-dot*0.5)), 
				(int)(this.getWidth()*nReciprocal*dot), (int)(this.getHeight()*nReciprocal*dot));
		g2d.fillOval((int)(this.getWidth()*nReciprocal*(12-dot*0.5)),(int)(this.getHeight()*nReciprocal*(12-dot*0.5)), 
				(int)(this.getWidth()*nReciprocal*dot), (int)(this.getHeight()*nReciprocal*dot));				
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				double piece = 0.8;
				switch (chessRecord[i][j]) {
					case 1:
						g2d.setColor(Color.black);
						g2d.fillOval((int)(wn*(i -piece*0.5)),(int)(hn*(j -piece*0.5)),
								(int)(wn*piece), (int)(hn*piece));
						break;
					case 2:
						g2d.setColor(Color.white);
						g2d.fillOval((int)(wn*(i -piece*0.5)),(int)(hn*(j -piece*0.5)),
								(int)(wn*piece), (int)(hn*piece));
						break;
					default:
						break;
				}
			}
		}
		
	}
	
	private class MouseLisAdp extends MouseAdapter{

		@Override
		public void mouseReleased(MouseEvent e) {
			warning ="";
			int x = e.getX();
			int y = e.getY();	

			if(winner == 0) {
				if(putX(x)!=0 && putX(x)!= n && putY(y)!=0 && putY(y)!= n) {
					
					if(chessRecord[putX(x)][putY(y)] == 0) {
						chessRecord[putX(x)][putY(y)]=turn;
						repaint();
						if (checkFive(turn, putX(x), putY(y)) ) {
	//						System.out.printf("palyer%d won!", turn);
							winner = turn;
							turn =0;
//							System.out.println(winner);
						} else {
							turn = 3-turn;
						}
					}
					else {
						warning = "can't put there.";
					}
					
				}
				else {
					warning = "can't put there.";
//					try {
//						this.wait(3000);
//					} catch (Exception e1) {
//						System.out.println(e1);
//					}
//					warning ="";

				}
			}

			
			if(checkEmpty() ) {
				winner =3;
				turn =0;
			}
		}
		
	}
	
	private boolean checkEmpty(){
		boolean empty = true;
		for(int i=1; i< n; i++) {
			for(int j=1; j<n; j++) {
				if(chessRecord[i][j] ==0) {
					empty = false;
					break;
				}
			}
		}
		return empty;
	}
	
	private int putX(int x) {
		int transX = 0;
		double wn = (double)this.getWidth()/n;
		if( x%(wn) > wn*0.5 ) {
			transX = (int)(x/wn) +1;
		} else if( x%(wn) < wn*0.5) {
			transX = (int)(x/wn) ;
		}
		return transX;
	}
	
	private int putY(int y) {
		int transY = 0;
		double hn = (double)this.getHeight()/n;
		if( y%(hn) > hn*0.6 ) {
			transY = (int)(y/hn) +1;
		} else if( y%(hn) < hn*0.4) {
			transY = (int)(y/hn);
		}
		return transY;
	}
	
	private boolean checkFive(int player, int putRow, int putCol) {
		boolean check5 = false;
		
		//檢查是否還有空位(橫直)
		String checker = ".*"+ Integer.toString(player)+"{5,}.*"; //player= 1 -> ".*1{5,}.*"
		if (checkRowCol(player, putRow, putCol, checker) ||	checkSlash(player, putRow, putCol, checker)) {
			check5 = true;
		}
		return check5;
	}
	
	private boolean checkRowCol(int player, int putRow, int putCol, String checker) {
		boolean checkRC = false; 
		for(int i=0; i<n; i++){
			StringBuffer sbRow =new StringBuffer(); 
			StringBuffer sbCol =new StringBuffer(); 
			for(int j= 0; j<n; j++){
				sbRow.append(chessRecord[i][j]);
				sbCol.append(chessRecord[j][i]);				
			}
			String sR = sbRow.toString(); 
			String sC = sbCol.toString();
			
			if(sR.matches(checker) ) {
				checkRC= true;
			} 
			if(sC.matches(checker) ) {
				checkRC= true;
			} 
		}
		return checkRC ;
	}
	
	private boolean checkSlash(int player, int putRow, int putCol, String checker) {
		boolean checkS = false;

		int slash1 = putRow - putCol; //檢查\ putRow<6才要檢查
		int slash2 = putRow + putCol; //檢查/
		
		StringBuffer sbSlash1 =new StringBuffer(); 
		StringBuffer sbSlash2 =new StringBuffer();				
		for(int i=0; i<n; i++) {
			if(i-slash1 >=0 && i-slash1 <n) {
				sbSlash1.append(chessRecord[i][i-slash1]);
			}
			if(slash2-i >=0 && slash2-i <n) {
				sbSlash2.append(chessRecord[i][slash2-i]);
			}
		}
	
		String sS1 = sbSlash1.toString();
		String sS2 = sbSlash2.toString();
		
		if(sS1.matches(checker)) {
			checkS =true; 
		} 
		if(sS2.matches(checker)) {
			checkS =true;
		} 
		return checkS;
	}
	
}


