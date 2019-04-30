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
import javax.swing.JTextField;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private JPanel contentPane;
	private static TwitterApp app;
	private static JTextField influencerTracker;
	private static JTextField maxFollow;
	private static JTextField pauseTime;
	private static JTextField targetAmount;
	
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
		
		influencerTracker = new JTextField();
		influencerTracker.setColumns(10);
		
		JLabel lblGrabAllFollowing = new JLabel("Grab All Following of this @");
		
		maxFollow = new JTextField();
		maxFollow.setColumns(10);
		
		JLabel lblGrabFollowersFrom = new JLabel("Grab Followers From Influencers");
		
		JLabel lblFollowAmount = new JLabel("Follow Amount");
		
		pauseTime = new JTextField();
		pauseTime.setColumns(10);
		
		JLabel lblBreakTimeIn = new JLabel("Break Time In Between");
		
		JLabel lblseconds = new JLabel("(seconds)");
		
		targetAmount = new JTextField();
		targetAmount.setColumns(10);
		
		JLabel lblGrabTargetAmount = new JLabel("Grab Target Amount");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(pauseTime, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(maxFollow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblBreakTimeIn)
									.addComponent(lblFollowAmount))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblseconds)
									.addGap(39))))
						.addComponent(influencerTracker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrabAllFollowing)
						.addComponent(lblGrabFollowersFrom)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(targetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblGrabTargetAmount)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(maxFollow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFollowAmount))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(pauseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBreakTimeIn))
							.addGap(39))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(58, Short.MAX_VALUE)
							.addComponent(lblseconds)
							.addGap(29)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGrabAllFollowing)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(influencerTracker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(lblGrabFollowersFrom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(targetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrabTargetAmount))
					.addGap(11))
		);
		
		JButton grabTargetUsers = new JButton("Grab Targets");
		grabTargetUsers.setForeground(Color.BLACK);
		grabTargetUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().grabTargets = true;
			}
		});
		
		JButton updateInfluencers = new JButton("Update Influencers");
		updateInfluencers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.getButtonManager().updateInfluencers = true;
			}
		});
		
		JButton followUsers = new JButton("Follow Users");
		followUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().followUsers = true;
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(followUsers, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
						.addComponent(updateInfluencers, GroupLayout.PREFERRED_SIZE, 151, Short.MAX_VALUE)
						.addComponent(grabTargetUsers, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(followUsers)
					.addGap(85)
					.addComponent(updateInfluencers)
					.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
					.addComponent(grabTargetUsers)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static TwitterApp getApp(){
		return app;
	}

	public static JTextField getInfluencerTracker() {
		return influencerTracker;
	}

	public static JTextField getPauseTime() {
		return pauseTime;
	}

	public static JTextField getMaxFollow() {
		return maxFollow;
	}

	public static JTextField getTargetAmount() {
		return targetAmount;
	}

}
