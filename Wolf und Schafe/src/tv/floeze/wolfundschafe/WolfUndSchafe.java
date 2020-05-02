package tv.floeze.wolfundschafe;

import javax.swing.JFrame;

import tv.floeze.wolfundschafe.settings.SettingsManager;
import tv.floeze.wolfundschafe.utils.Utils;
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
		sm.setRandomNameSupplier(() -> Utils.getRandomName());
		sm.addSetting(0, "player1_type", InputType.TOGGLE_BUTTON, "Computer");
		sm.addSetting(0, "player1_name", InputType.NICKNAME, "Name Spieler 1");
		sm.addSetting(0, "player2_type", InputType.TOGGLE_BUTTON, "Computer");
		sm.addSetting(0, "player2_name", InputType.NICKNAME, "Name Spieler 2");
		sm.addSetting(1, "rolle", InputType.TOGGLE_BUTTON, "Spieler1 Wolf");
		sm.setBounds(frame.getBounds());
		sm.onLastPage(() -> System.exit(0));
		frame.add(sm);

		frame.setVisible(true);
	}

}
