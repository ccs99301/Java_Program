import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

public class BouncingBallPanel extends JPanel{

	private final static SecureRandom generator = new SecureRandom();
	
	private BouncingBall ball = new BouncingBall(220,220,Color.blue);
	boolean flag = false;
	
	public BouncingBallPanel(){
		this.setSize(400, 350);
		ExecutorService executor = Executors.newCachedThreadPool(); 
		this.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
		    	if(flag == false)
		    		executor.execute(new BallMover(ball, BouncingBallPanel.this));
		    	flag = true;
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ball.getColor());
		g.fillOval(ball.getxLocation(), ball.getyLocation(), ball.getSize(), ball.getSize());
	}	
}
