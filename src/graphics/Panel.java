package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private int width;
	private int height;
	
	public Panel(String fileName, int width, int height) throws IOException {
		// Image
		File file = new File(fileName);
		image = ImageIO.read(file);
		this.width = width;
		this.height = height;
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, width, height, this);
	}
}
