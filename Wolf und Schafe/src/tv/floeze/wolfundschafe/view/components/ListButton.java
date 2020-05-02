package tv.floeze.wolfundschafe.view.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class ListButton extends JButton {

	private static final long serialVersionUID = -6850372388641631034L;

	private final List<String> list;

	private final List<ActionListener> actionListeners;

	private int index;

	public ListButton(String... strings) {
		super(strings[0]);

		list = new ArrayList<String>(Arrays.asList(strings));

		actionListeners = new ArrayList<ActionListener>();

		super.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				index = (index + 1) % list.size();
				setText(list.get(index));
				actionListeners.forEach(l -> l.actionPerformed(e));
			}
		});
	}

	@Override
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		if (actionListeners.contains(l))
			actionListeners.remove(l);
	}

	public int getIndex() {
		return index;
	}

}
