package tv.floeze.wolfundschafe;

import javax.swing.JFrame;

import tv.floeze.wolfundschafe.view.components.InputType;
import tv.floeze.wolfundschafe.view.components.SimplexSettingsPanel;

public class WolfUndSchafe {

	public WolfUndSchafe() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// Debug: demo code
		JFrame frame = new JFrame("Wolf und Schafe");
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SimplexSettingsPanel sp = new SimplexSettingsPanel();
		sp.addSetting("key", InputType.TOGGLE_BUTTON, "Button 1");
		sp.addSetting("key2", InputType.TOGGLE_BUTTON, "Button 2");
		sp.addSetting("key3", InputType.TEXT, "Text");
		frame.add(sp);

		frame.setVisible(true);
	}

}
