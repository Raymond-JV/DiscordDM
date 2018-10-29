package Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StatusPictureTransformer
{
	private final String fontName = "RuneScape UF Regular";
	private final int fontSize = 16;
	private final String healthBar = "health_bar.png";
	private final String specBar = "spec_bar.png";
	private final Font specFont;
	private final Color specRed = new Color(109,19,10);
	private final Color specGreen = new Color(43, 109, 52);


	public StatusPictureTransformer()
	{
		specFont = new Font(fontName, Font.PLAIN, fontSize);
	}


	public List<File> createHealthBarImages() throws IOException {

		List<File> healthStatus = new ArrayList<>();
		URL resource = StatusPictureTransformer.class.getClassLoader().getResource(healthBar);
		if (resource == null)
			throw new IOException("Error. Health Bar file not found.");
		BufferedImage read = ImageIO.read(resource);

		int maxHp = 99;
		double height = (double) read.getHeight();
		double width = (double) read.getWidth();
		double bar_increment = width / maxHp;

		for (int i = 0; i <= maxHp; i++) {
			Graphics2D edit = read.createGraphics();
			edit.setColor(Color.RED);
			Rectangle2D bar = new Rectangle2D.Double(width - bar_increment * i, 0, bar_increment * i, height);
			edit.fill(bar);
			edit.dispose();
			healthStatus.add(createTempImageFile("health" + i + ".png", read));
		}
		return healthStatus;
	}

	public List<File> createSpecBarImages() throws IOException {
		List<File> specImages = new ArrayList<>();

		URL resource = StatusPictureTransformer.class.getClassLoader().getResource(specBar);
		if (resource == null)
			throw new IOException("Error. Spec Bar file not found.");
		BufferedImage read = ImageIO.read(resource);

		int maxSpec = 100;
		double height = (double) read.getHeight();
		double width = (double) read.getWidth();
		double bar_increment = (width / maxSpec)* 0.97;

		for (int i = 0; i <= maxSpec; i++) {
			Graphics2D edit = read.createGraphics();

			Rectangle2D health = new Rectangle2D.Double( width * 0.01, 0 + height/4.1, width*0.98 ,  height/ 1.9);
			edit.setColor(specGreen);
			edit.fill(health);

			Rectangle2D bar = new Rectangle2D.Double(width  - bar_increment * i - width * 0.01, 0 + height/4.1, bar_increment * i,  height/ 1.9);
			edit.setColor(specRed);
			edit.fill(bar);

			edit.setPaint(Color.BLACK);
			edit.setFont(specFont);
			String specLeft = String.format("Special Attack: %d%%", maxSpec - i);
			edit.drawString(specLeft, (int)(width/4), (int)(height/1.5));
			edit.dispose();
			specImages.add(createTempImageFile("spec" + i + ".png", read));
		}

		return specImages;
	}

	private File createTempImageFile(String fileName, BufferedImage toCopy) throws IOException {

			File temp = new File(fileName);
			ImageIO.write(toCopy, "png", temp);
		    temp.deleteOnExit();
			return temp;
	}

	public static void main(String[] args) throws IOException {
//		StatusPictureTransformer finder = new StatusPictureTransformer();
//		List<File> healthStatus = finder.createHealthBarImages();
//		List<File> specBarImages = finder.createSpecBarImages();
//
//		JFrame frame = new JFrame("demo");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		ImageIcon image = new ImageIcon(specBarImages.get(43).toURI().toURL());
//		frame.add(new JLabel(image));
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		frame.pack();

		List<Integer> values = new ArrayList<>();
		values.add(10);
		values.add(12);
		values.add(13);

	}
}