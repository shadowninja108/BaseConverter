package com.shadowninja108.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Frame extends JFrame {

	private static final long serialVersionUID = -2231095201120495674L;

	private JPanel rootPanel;

	private JTextArea decimalTextPane;
	private JTextArea binaryTextPane;
	private JTextArea hexTextPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		System.out.println("BaseConverter written by Daniel Pickering.");

		setTitle("BaseConverter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 100);
		rootPanel = new JPanel();
		setContentPane(rootPanel);
		rootPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel binaryPanel = new JPanel();
		binaryPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Binary",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rootPanel.add(binaryPanel);
		binaryPanel.setLayout(new BorderLayout(0, 0));

		binaryTextPane = new JTextArea();
		binaryTextPane.setLineWrap(true);
		binaryPanel.add(new JScrollPane(binaryTextPane));
		binaryTextPane.getDocument().addDocumentListener(new OnDocumentChangedListener((e) -> {
			String text = getTextFromDocument(e.getDocument());
			BigInteger num = parseString(text, 2);
			if (num.signum() != -1) { // if negative?
				decimalTextPane.setText(num.toString(10));
				hexTextPane.setText(num.toString(16));
			}
		}));

		JPanel decimalPanel = new JPanel();
		decimalPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Decimal",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rootPanel.add(decimalPanel);
		decimalPanel.setLayout(new BorderLayout(0, 0));

		decimalTextPane = new JTextArea();
		decimalPanel.add(new JScrollPane(decimalTextPane));
		decimalTextPane.setLineWrap(true);
		decimalTextPane.getDocument().addDocumentListener(new OnDocumentChangedListener((e) -> {
			String text = getTextFromDocument(e.getDocument());
			BigInteger num = parseString(text, 10);
			if (num.signum() != -1) { // if negative?
				binaryTextPane.setText(num.toString(2));
				hexTextPane.setText(num.toString(16));
			}
		}));

		JPanel hexPanel = new JPanel();
		hexPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hexadecimal",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rootPanel.add(hexPanel);
		hexPanel.setLayout(new BorderLayout(0, 0));

		hexTextPane = new JTextArea();
		hexPanel.add(new JScrollPane(hexTextPane));
		hexTextPane.setLineWrap(true);
		hexTextPane.getDocument().addDocumentListener(new OnDocumentChangedListener((e) -> {
			String text = getTextFromDocument(e.getDocument());
			BigInteger num = parseString(text, 16);
			if (num.signum() != -1) { // if negative?
				binaryTextPane.setText(num.toString(2));
				decimalTextPane.setText(num.toString(10));
			}
		}));
	}

	public static String getTextFromDocument(Document d) {
		int length = d.getLength();
		String text = null;
		try {
			text = d.getText(0, length);
		} catch (BadLocationException e1) {
			// what the fuck
			e1.printStackTrace();
		}
		return text;
	}

	public static BigInteger parseString(String text, int base) {
		try {
			return new BigInteger(text, base);
		} catch (NumberFormatException e) {
			return BigInteger.valueOf(-1); // negative number indicates invalid number
		}
	}
}