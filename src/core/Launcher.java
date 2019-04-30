package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private JPanel contentPane;
	private static TwitterApp app;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		app = new TwitterApp();
		app.start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
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
	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(102, 204, 204));
		contentPane.setBackground(new Color(102, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(250, 235, 215));
		panel.setBackground(new Color(250, 235, 215));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(263, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
		);
		
		JButton followUsers = new JButton("Follow Users");
		followUsers.setForeground(Color.BLACK);
		followUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().followTargets = true;
			}
		});
		
		JButton updateInfluencers = new JButton("Update Influencers");
		updateInfluencers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.getButtonManager().updateInfluencers = true;
			}
		});
		
		JButton test = new JButton("Test");
		test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().test = true;
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(followUsers, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(updateInfluencers, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(test, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(followUsers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(updateInfluencers)
					.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
					.addComponent(test)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static TwitterApp getApp(){
		return app;
	}
}
