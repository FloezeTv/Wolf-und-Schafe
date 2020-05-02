package tv.floeze.wolfundschafe.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NicknameField extends JPanel {

	private static final long serialVersionUID = -7206059276375919836L;

	private static final int HEIGHT = 64;

	private final JTextField nameField;
	private final JButton randomButton;

	private String actionCommand;
	private final List<ActionListener> actionListeners;

	private Supplier<String> rndNameSupplier;

	public NicknameField() {
		super();
		super.setLayout(new GridBagLayout());
		super.setBackground(Color.BLUE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;

		actionListeners = new ArrayList<ActionListener>();
		
		nameField = new JTextField();
		nameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callActionListeners();
			}
		});
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				callActionListeners();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				callActionListeners();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				callActionListeners();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		super.add(nameField, gbc);

		randomButton = new JButton("Random");
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		randomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rndNameSupplier != null)
					nameField.setText(rndNameSupplier.get());
			}
		});
		super.add(randomButton, gbc);
	}

	private void callActionListeners() {
		actionListeners
				.forEach(l -> l.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand)));
	}

	public String getNickname() {
		return nameField.getText();
	}

	public void setRandomNameSupplier(Supplier<String> provider) {
		rndNameSupplier = provider;
	}

	public void setButtonFont(Font f) {
		randomButton.setFont(f);
	}

	public void setInputFont(Font f) {
		nameField.setFont(f);
	}

	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		if (actionListeners.contains(l))
			actionListeners.remove(l);
	}

	public void setActionCommand(String command) {
		actionCommand = command;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width, HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(super.getMinimumSize().width, HEIGHT);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(super.getMaximumSize().width, HEIGHT);
	}

}
