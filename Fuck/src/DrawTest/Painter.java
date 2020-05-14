package DrawTest;

import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Painter extends JFrame {

	public int maxDraw = 1500;
	public int drawInterval = 100;
	public int top = 100;
	public int bottom = 900;
	public int left = 100;
	public int right = 1300;
	public LinkedList<MyVec> list = new LinkedList<MyVec>();
	
	public Painter() {
		setTitle("Path Finder");
		setSize(1500,1000);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);

	}
	
	public void paint(Graphics g) {
		g.clearRect(0,0,1500,1500);
		//»æÖÆ×ÝÖá
		for (int draw = 0; draw <= maxDraw; draw += drawInterval) {
			int x = drawToX(draw);
			g.drawLine(x, bottom, x, top);
			g.drawString(""+draw, x, bottom + 20);
		}
		//»æÖÆºáÖá
		for (float percent = 0f; percent <= 1.001; percent += 0.1) {
			int y = percentToY(percent);
			g.drawLine(left, y, right, y);
			g.drawString(percentToString(percent), left - 50, y);
		}
		
		for(MyVec vec: list) {
			drawPoint(g, vec);
//			System.out.println(vec.draw + "|" + vec.percent);
		}
		
	}
	
	public void setList(LinkedList<MyVec> list) {
		this.list = list;
		this.repaint();
	}
	
	private int drawToX(int draw) {
		float ratio = ((float)draw) / ((float)maxDraw);
		return left + (int)(ratio*(right-left));
	}
	
	private int percentToY(float percent) {
		return bottom - (int)(percent*(bottom - top));
	}
	
	private String percentToString(float percent) {
		if (percent == 1f) {
			return "100%";
		} else {
			return Math.floor(percent*100) + "%";
		}
	}
	
	private void drawPoint(Graphics g, MyVec vec) {
		g.fillOval(drawToX(vec.draw)-4, percentToY(vec.percent)-4, 8, 8);
	}
}
