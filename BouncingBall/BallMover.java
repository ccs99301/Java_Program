public class BallMover implements Runnable {

	private int X_MOVEMENT = 5;
	private int Y_MOVEMENT = 20;
	private BouncingBall ball;
	private BouncingBallPanel panel;

	public BallMover(BouncingBall ball, BouncingBallPanel panel) {
		this.ball = ball;
		this.panel = panel;
	}
	
	@Override
	public void run() {
		while (true) {
			if (ball.getyLocation() >= panel.getHeight() - ball.getSize() ||
					ball.getyLocation() <= 0) {
				Y_MOVEMENT = - Y_MOVEMENT;
			}
			if (ball.getxLocation() >= panel.getWidth() - ball.getSize()||
					ball.getxLocation() <= 0){
				X_MOVEMENT = - X_MOVEMENT;
			}
			ball.setxLocation(ball.getxLocation() + X_MOVEMENT);
			ball.setyLocation(ball.getyLocation() + Y_MOVEMENT);
			panel.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}