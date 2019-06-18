import java.awt.BorderLayout;
import javax.swing.JFrame;

public class BouncingBallFrame extends JFrame{

	public static void main(String[] args) {
		
		BouncingBallFrame frame = new BouncingBallFrame();
		BouncingBallPanel panel = new BouncingBallPanel();
		
		frame.setTitle("Bouncing Ball");
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
}