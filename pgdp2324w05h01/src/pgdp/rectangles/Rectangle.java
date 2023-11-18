package pgdp.rectangles;

public class Rectangle {

	private Vector2D topLeft, bottomRight;

	public Rectangle(Vector2D topLeft, Vector2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public String toString() {
		return "Rectangle spanned by points " + topLeft.toString() + " and " + bottomRight.toString() + ".";
	}

	public double calculateArea() {
		double deltaX = this.topLeft.getX() - this.bottomRight.getX();
		double deltaY = this.bottomRight.getY() - this.topLeft.getY();

		return deltaX * deltaY;
	}

	public void shiftBy(Vector2D v) {
		this.topLeft.add(v);
		this.bottomRight.add(v);
	}

	public static void main(String[] args) {

		/*
		 * This part is only for testing purposes and should not be changed.
		 * 
		 * @TODO: Remove the comment symbols (Eclipse: Ctrl + Shift + C) and run the
		 * main-method for testing. If you have not implemented the constructor and
		 * toString-method, your program won't build!
		 */

		Vector2D tl = new Vector2D(0.0, 2.0), br = new Vector2D(2.0, 0.5);
		Rectangle myRectangle = new Rectangle(tl, br);
		if (myRectangle.toString().equals("Rectangle spanned by points [0.0, 2.0] and [2.0, 0.5]."))
			System.out.println("The String seems to match. Good Job!");
		else {
			System.out.println("This is what you created: " + myRectangle);
			System.out.println("This is how it should be: Rectangle spanned by points [0.0, 2.0] and [2.0, 0.5].");
			System.out.println("Better luck next time!");
		}

		/*
		 * Down here you might want to add code to create your own tests?
		 */

	}

}