package textWriting;

import java.awt.Point;

public class Line {
	private Point beginLine;
	private Point endLine;
	
	public Line(Point beginLine, Point endLine) {
		this.beginLine = beginLine;
		this.endLine = endLine;
	}

	public Point getBeginLine() {
		return beginLine;
	}

	public Point getEndLine() {
		return endLine;
	}

	@Override
	public String toString() {
		return "Line [beginLine=" + beginLine + ", endLine=" + endLine + "]";
	}
	
}
