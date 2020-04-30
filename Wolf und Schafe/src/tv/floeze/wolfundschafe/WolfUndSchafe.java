package tv.floeze.wolfundschafe;

import javax.swing.JFrame;

import tv.floeze.wolfundschafe.settings.SettingsManager;
import tv.floeze.wolfundschafe.view.components.InputType;

public class WolfUndSchafe {

	public WolfUndSchafe() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// Debug: demo code
		JFrame frame = new JFrame("Wolf und Schafe");
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SettingsManager sm = new SettingsManager();
		sm.addSetting(0, "key1", InputType.TOGGLE_BUTTON, "Button page 1");
		sm.addSetting(0, "key2", InputType.TEXT, "Button page 1");
		sm.addSetting(1, "key1", InputType.TOGGLE_BUTTON, "Button page 2");
		sm.addSetting(1, "key2", InputType.TEXT, "Button page 1");
		sm.addSetting(2, "key1", InputType.TOGGLE_BUTTON, "Button page 3");
		sm.addSetting(2, "key2", InputType.TEXT, "Button page 1");
		sm.addSetting(3, "key1", InputType.TOGGLE_BUTTON, "Button page 4");
		sm.addSetting(3, "key2", InputType.TEXT, "Button page 1");
		sm.addSetting(4, "key1", InputType.TOGGLE_BUTTON, "Button page 5");
		sm.addSetting(4, "key2", InputType.TEXT, "Button page 1");
		sm.setBounds(frame.getBounds());
		sm.onLastPage(() -> System.exit(0));
		frame.add(sm);

		frame.setVisible(true);
	}

}
