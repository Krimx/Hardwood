import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
		private boolean left, right, middle;
		private boolean canLeft = true, canRight = true, canMiddle = true;
		private int x,y, lastX = 0, lastY = 0;
		private int scroll, scrollDifference = 0;
		private int dX = 0, dY = 0;
		private boolean locked;
		private Robot bot;
		
		public Mouse(boolean lockIn) {
			locked = lockIn;
			
			if (locked) {
				try {
					bot = new Robot();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int key = e.getButton();

			if (key == MouseEvent.BUTTON1) {
				left = true;
			
			}
			else left = false;
			
			if (key == MouseEvent.BUTTON2) {
				middle = true;
			}
			
			if (key == MouseEvent.BUTTON3) {
				right = true;
			}
			
			x = e.getX();
			y = e.getY();
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int key = e.getButton();

			if (key == MouseEvent.BUTTON1) {
				left = false;
				canLeft = true;
			}
			if (key == MouseEvent.BUTTON2) {
				middle = false;
				canMiddle = true;
			}
			if (key == MouseEvent.BUTTON3) {
				right = false;
				canRight = true;
			}
			
			x = e.getX();
			y = e.getY();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			
		}
		
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			
			dX = x - lastX;
			dY = y - lastY;
			
			lastX = x;
			lastY = y;
		}

	    public void mouseMoved(MouseEvent e) {
	        x = e.getX();
	        y = e.getY();
			
			dX = x - lastX;
			dY = y - lastY;
			
			lastX = x;
			lastY = y;
	    }
	    
	    @Override
		public void mouseWheelMoved(MouseWheelEvent e) {
	    	scroll -= e.getWheelRotation();
	    	scrollDifference = -e.getWheelRotation();
		}
		
		public int getX() {return x - 1;}
		public int getY() {return y - 30;}

		public int[] getDelta() {
			int[] toOut = {dX,dY};
			if (locked) {
				bot.mouseMove(100, 100);
			}
			return toOut;
		}

		public boolean LEFT() {return left;}
		public boolean RIGHT() {return right;}
		public boolean MIDDLE() {return middle;}
		
		public boolean LEFTCLICKED() {
			if (left && canLeft) {
				canLeft = false;
				return true;
			}
			else {
				return false;
			}
		}
		public boolean RIGHTCLICKED() {
			if (right && canRight) {
				canRight = false;
				return true;
			}
			else {
				return false;
			}
		}
		public boolean MIDDLECLICKED() {
			if (middle && canMiddle) {
				canMiddle = false;
				return true;
			}
			else {
				return false;
			}
		}
		public int getScrollAmount() {
			return scroll;
		}
		public int getScrollDifference() {
			int toOut = scrollDifference;
			scrollDifference = 0;
			return toOut;
		}
	}