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

	public void addSetting(int num, String key, InputType type, String text) {
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
			add(panels.get(num), num);
			updatePages();
		}
		panels.get(num).addSetting(key, type, text);
	}
	
	public void onLastPage(Runnable function) {
		onLastPage = function;
	}

	public void back() {
		currentPage--;
		updatePages();
	}

	public void next() {
		currentPage++;
		updatePages();
	}

	private void updateComponents() {
		panels.forEach((k, v) -> {
			v.setBounds(getBounds());
		});
	}

	public void updatePages() {
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
