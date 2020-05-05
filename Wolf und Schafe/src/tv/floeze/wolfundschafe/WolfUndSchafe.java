package tv.floeze.wolfundschafe;

import javax.swing.JFrame;

import tv.floeze.wolfundschafe.settings.SettingsManager;
import tv.floeze.wolfundschafe.utils.Utils;
import tv.floeze.wolfundschafe.view.components.InputType;

public class WolfUndSchafe {

	public WolfUndSchafe() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Main method currently only running demo stuff
	 * @param args
	 */
	public static void main(String[] args) {
		// Debug: demo code
		JFrame frame = new JFrame("Wolf und Schafe");
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SettingsManager sm = new SettingsManager();
		sm.setRandomNameSupplier(() -> Utils.getRandomName());
		sm.addSetting(0, "player1_type", InputType.LIST_BUTTON, "Computer", "Mensch");
		sm.addSetting(0, "player1_name", InputType.NICKNAME, "Name Spieler 1");
		sm.addSetting(0, "player2_type", InputType.LIST_BUTTON, "Computer", "Mensch");
		sm.addSetting(0, "player2_name", InputType.NICKNAME, "Name Spieler 2");
		sm.addSetting(1, "rolle", InputType.LIST_BUTTON, "Spieler1 Wolf", "Spieler1 Schaf");
		sm.setBounds(frame.getBounds());
		sm.onLastPage(() -> {
			System.out.println("Player1: " + sm.getSettingText(0, "player1_name") + " is " + sm.getSettingText(0, "player1_type"));
			System.out.println("Player2: " + sm.getSettingText(0, "player2_name") + " is " + sm.getSettingText(0, "player2_type"));
			System.out.println(sm.getSettingText(1, "rolle"));
			System.exit(0);
		});
		frame.add(sm);

		frame.setVisible(true);
	}

}
