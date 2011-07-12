package org.inal.timeseries;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 1554761219623052958L;

	private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 786;

	public GraphWindow(final JPanel panel) {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		panel.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		LayoutManager layout = new BorderLayout();
		setLayout(layout);
		add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.BLUE);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
