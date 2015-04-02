package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel container = new JPanel();
	
	public Window(String fileName, int width, int height,
			JProgressBar bar) {

		// Windows param
		setSize(width, height);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Object container
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		
		try {
			Panel center = new Panel(fileName, width, height);
			container.add(center, BorderLayout.CENTER);
			container.add(bar, BorderLayout.SOUTH);
			bar.setStringPainted(true);
			setContentPane(container);

			setVisible(true);
		} catch (IOException e) {
			throw new ImageNotFoundException();
		}
	}
}
