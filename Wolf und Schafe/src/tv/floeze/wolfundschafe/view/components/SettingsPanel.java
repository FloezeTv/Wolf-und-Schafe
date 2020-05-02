package tv.floeze.wolfundschafe.view.components;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A SettingsPanel class for getting settings from the user
 * 
 * @author Floeze
 *
 */
public class SettingsPanel extends JPanel {

	// TODO: implement own ToggleButtons, ... for better styling and maybe automatic
	// font resizing / word wrapping?
	// FIXME: maximizing not working?

	// generated
	private static final long serialVersionUID = 1342031696529395226L;

	/**
	 * JPanel for Buttons, textfields, ... (content added by {@link #addSetting})
	 */
	private final JPanel contentPane;
	private final GridBagConstraints contentConstraints;
	private final HashMap<String, Boolean> contentToggles;
	private final HashMap<String, String> contentTexts;
	private final HashMap<String, Integer> contentIndices;
	private final ActionListener contentListener;

	private final JButton backBtn, nextBtn;

	private Supplier<String> randomNameSupplier;

	/**
	 * Font for buttons
	 */
	private static final Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 48);
	/**
	 * Font for input
	 */
	private static final Font inputFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
	/**
	 * font for navButtons
	 */
	private static final Font navButtonFont = new Font(Font.DIALOG, Font.PLAIN, 64);

	public SettingsPanel() {
		super();
		super.setLayout(null);
		super.setVisible(true);

		// setting up contentPane
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		contentConstraints = new GridBagConstraints();
		contentConstraints.fill = GridBagConstraints.HORIZONTAL;
		contentConstraints.weightx = 1;
		contentConstraints.gridx = 0;
		contentConstraints.gridwidth = 1;
		contentConstraints.gridheight = 1;
		contentPane.setOpaque(false);
		super.add(contentPane);

		contentToggles = new HashMap<String, Boolean>();
		contentTexts = new HashMap<String, String>();
		contentIndices = new HashMap<String, Integer>();

		// actionlistener for updating values
		contentListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				if (o instanceof Component) {
					Component c = (Component) o;
					if (c instanceof JToggleButton) {
						JToggleButton j = (JToggleButton) c;
						contentToggles.put(e.getActionCommand(), j.isSelected());
					} else if (c instanceof JTextArea) {
						JTextArea j = (JTextArea) c;
						contentTexts.put(e.getActionCommand(), j.getText());
					} else if (c instanceof JTextField) {
						JTextField j = (JTextField) c;
						contentTexts.put(e.getActionCommand(), j.getText());
					} else if (c instanceof NicknameField) {
						NicknameField j = (NicknameField) c;
						contentTexts.put(e.getActionCommand(), j.getNickname());
					} else if (c instanceof ListButton) {
						ListButton j = (ListButton) c;
						contentTexts.put(e.getActionCommand(), j.getText());
						contentIndices.put(e.getActionCommand(), j.getIndex());
					}
				}
				contentToggles.forEach((k, v) -> System.out.println(k + ": " + v));
				System.out.println();
				contentTexts.forEach((k, v) -> System.out.println(k + ": " + v));
				System.out.println();
				contentIndices.forEach((k, v) -> System.out.println(k + ": " + v));
				System.out.println();
			}
		};

		// back and next button
		backBtn = new JButton("back");
		backBtn.setLocation(0, 0);
		backBtn.setSize(256, 80);
		backBtn.setFont(navButtonFont);
		backBtn.setVisible(true);
		super.add(backBtn);

		nextBtn = new JButton("next");
		nextBtn.setLocation(getWidth() - 128, getHeight() - 64);
		nextBtn.setSize(256, 80);
		nextBtn.setFont(navButtonFont);
		nextBtn.setVisible(true);
		super.add(nextBtn);

		super.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				updateComponents();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				updateComponents();
			}
		});
		updateComponents();
	}

	/**
	 * adds a new setting to the panel
	 * 
	 * @param key  identifier
	 * @param type type of input (see {@link InputType})
	 * @param text text for buttons, ...
	 */
	public void addSetting(String key, InputType type, String... text) {
		if (type.equals(InputType.TEXT)) {
			// TODO: add text
			JTextField field = new JTextField();
			field.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			field.setActionCommand(key);
			field.addActionListener(contentListener);
			field.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					contentListener.actionPerformed(new ActionEvent(field, ActionEvent.ACTION_PERFORMED, key));
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					contentListener.actionPerformed(new ActionEvent(field, ActionEvent.ACTION_PERFORMED, key));
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					contentListener.actionPerformed(new ActionEvent(field, ActionEvent.ACTION_PERFORMED, key));
				}
			});
			field.setFont(inputFont);
			contentTexts.put(key, "");
			contentPane.add(field, contentConstraints);
		} else if (type.equals(InputType.TOGGLE_BUTTON)) {
			JToggleButton btn = new JToggleButton(text[0]);
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btn.setActionCommand(key);
			btn.addActionListener(contentListener);
			btn.setFont(buttonFont);
			contentToggles.put(key, false);
			contentPane.add(btn, contentConstraints);
		} else if (type.equals(InputType.NICKNAME)) {
			NicknameField nnf = new NicknameField();
			nnf.setButtonFont(buttonFont);
			nnf.setInputFont(inputFont);
			nnf.setRandomNameSupplier(randomNameSupplier);
			nnf.setActionCommand(key);
			nnf.addActionListener(contentListener);
			contentTexts.put(key, "");
			contentPane.add(nnf, contentConstraints);
		} else if (type.equals(InputType.LIST_BUTTON)) {
			ListButton lb = new ListButton(text);
			lb.setFont(buttonFont);
			lb.setActionCommand(key);
			lb.addActionListener(contentListener);
			contentTexts.put(key, "");
			contentPane.add(lb, contentConstraints);
		}
	}

	public void setRandomNameSupplier(Supplier<String> supplier) {
		randomNameSupplier = supplier;
	}

	public void addBackListener(ActionListener a) {
		backBtn.addActionListener(a);
	}

	public void addNextListener(ActionListener a) {
		nextBtn.addActionListener(a);
	}

	public void removeBackListener(ActionListener a) {
		backBtn.removeActionListener(a);
	}

	public void removeNextListener(ActionListener a) {
		nextBtn.removeActionListener(a);
	}

	/**
	 * Gets the value of a toggle button
	 * 
	 * @param key button to get value for
	 * @return value of button
	 */
	public boolean getSettingToggle(String key) {
		return contentToggles.getOrDefault(key, false);
	}

	/**
	 * Gets the text value of a text
	 * 
	 * @param key text field to get value for
	 * @return value of field
	 */
	public String getSettingText(String key) {
		return contentTexts.getOrDefault(key, "");
	}
	
	/**
	 * Gets the index of list buttons, ...
	 * @param key button to get value for
	 * @return index of button
	 */
	public int getSettingIndex(String key) {
		return contentIndices.getOrDefault(key, -1);
	}

	/**
	 * updates components (internal online)
	 */
	private void updateComponents() {
		int width = super.getWidth();
		int height = super.getHeight();

		// contentPane stuff
		contentPane.setSize(width / 2, 9 * height / 10);
		contentPane.setLocation(width / 4, height / 20);

		// nav buttons
		backBtn.setLocation(0, 0);
		nextBtn.setLocation(getWidth() - nextBtn.getWidth(), getHeight() - nextBtn.getHeight());
	}

}