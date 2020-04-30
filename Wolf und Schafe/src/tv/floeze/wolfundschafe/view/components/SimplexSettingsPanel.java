package tv.floeze.wolfundschafe.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tv.floeze.wolfundschafe.utils.OpenSimplexNoise;

/**
 * Settings panel with interesting background, quite laggy though
 * 
 * @author Floeze
 *
 */
public class SimplexSettingsPanel extends SettingsPanel {

	private static final long serialVersionUID = -958125950122327425L;

	private final OpenSimplexNoise osn;

	private final BufferedImage img;

	private int offset = 0;

	public SimplexSettingsPanel() {
		super();
		super.setOpaque(false);

		osn = new OpenSimplexNoise();

		img = new BufferedImage(1920, 1080, BufferedImage.TYPE_3BYTE_BGR);
		new Thread() {
			@Override
			public void run() {
				Graphics g = img.getGraphics();
				while (true) {
					for (int y = 0; y < img.getHeight() / 2; y++) {
						for (int x = 0; x < img.getWidth(); x++) {
							float val = Math.min(1,
									Math.max(0, (float) (osn.eval(x / 300.0d, y / 20.0d, offset / 70.0d) + 1) / 2));
							g.setColor(Color.getHSBColor(((offset % 300) / 300.0f) - (val * 0.1f), 0.8f, val * 0.5f + 0.2f));
							g.fillRect(x, y, 1, 1);
						}
					}
					updateOffset();
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				Graphics g = img.getGraphics();
				while (true) {
					for (int y = img.getHeight() / 2; y < img.getHeight(); y++) {
						for (int x = 0; x < img.getWidth(); x++) {
							float val = Math.min(1,
									Math.max(0, (float) (osn.eval(x / 300.0d, y / 20.0d, offset / 70.0d) + 1) / 2));
							g.setColor(Color.getHSBColor(((offset % 300) / 300.0f) - (val * 0.1f), 0.8f, val * 0.5f + 0.2f));
							g.fillRect(x, y, 1, 1);
						}
					}
				}
			}
		}.start();
	}

	public void updateOffset() {

		offset++;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, super.getWidth(), super.getHeight(), null);
		super.paint(g);
		repaint();
	}

}
