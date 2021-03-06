package tv.floeze.wolfundschafe.settings;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JLayeredPane;

import tv.floeze.wolfundschafe.view.components.InputType;
import tv.floeze.wolfundschafe.view.components.SettingsPanel;

/**
 * A settingsManager for displaying settings
 * 
 * @author Floeze
 *
 */
public class SettingsManager extends JLayeredPane {

	private static final long serialVersionUID = -786813846177963844L;

	private final HashMap<Integer, SettingsPanel> panels;

	private int currentPage = 0;

	private Runnable onLastPage;

	private Supplier<String> randomNameSupplier;

	/**
	 * DON'T FORGET to also set Bounds to parent bounds
	 */
	public SettingsManager() {
		super();
		super.setLayout(null);
		super.setVisible(true);
		super.setBackground(Color.BLACK);

		panels = new HashMap<Integer, SettingsPanel>();

		super.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				updateComponents();
			}
		});
	}

	/**
	 * Adds a setting to the manager at the specified page. See {@link SettingsPanel#addSetting(String, InputType, String...)}
	 * @param num page to add setting to
	 */
	public void addSetting(int num, String key, InputType type, String... text) {
		if (!panels.containsKey(num)) {
			panels.put(num, new SettingsPanel());
			panels.get(num).addBackListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					back();
				}
			});
			panels.get(num).addNextListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					next();
				}
			});
			panels.get(num).setRandomNameSupplier(randomNameSupplier);
			add(panels.get(num), num);
			updatePages();
		}
		panels.get(num).addSetting(key, type, text);
	}

	/**
	 * Sets the random name supplier for all Panels
	 * @param supplier Supplier to set
	 */
	public void setRandomNameSupplier(Supplier<String> supplier) {
		randomNameSupplier = supplier;
		panels.forEach((k, v) -> v.setRandomNameSupplier(randomNameSupplier));
	}

	/**
	 * Function to call when last page is reached
	 * @param function called when last page is reached
	 */
	public void onLastPage(Runnable function) {
		onLastPage = function;
	}

	/**
	 * goes to the previous page
	 */
	public void back() {
		currentPage--;
		updatePages();
	}

	/**
	 * goes to the next page
	 */
	public void next() {
		currentPage++;
		updatePages();
	}

	/**
	 * {@link SettingsPanel#getSettingToggle(String)}
	 */
	public boolean getSettingToggle(int page, String key) {
		if(!panels.containsKey(page))
			return false;
		return panels.get(page).getSettingToggle(key);
	}

	/**
	 * {@link SettingsPanel#getSettingText(String)}
	 */
	public String getSettingText(int page, String key) {
		if(!panels.containsKey(page))
			return "";
		return panels.get(page).getSettingText(key);
	}
	
	/**
	 * {@link SettingsPanel#getSettingIndex(String)}
	 */
	public int getSettingIndex(int page, String key) {
		if(!panels.containsKey(page))
			return -1;
		return panels.get(page).getSettingIndex(key);
	}

	private void updateComponents() {
		panels.forEach((k, v) -> {
			v.setBounds(getBounds());
		});
	}

	private void updatePages() {
		List<Integer> l = new ArrayList<Integer>(panels.keySet());
		Collections.sort(l);
		boolean f = false;
		for (int i : l) {
			if (i >= currentPage && !f) {
				panels.get(i).setVisible(true);
				f = true;
				currentPage = i;
			} else {
				panels.get(i).setVisible(false);
			}
		}
		if (!f) {
			onLastPage.run();
		}
		updateComponents();
	}

}
