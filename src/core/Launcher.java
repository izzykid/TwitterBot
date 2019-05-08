package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Launcher extends JFrame {
	private JPanel contentPane;
	private static TwitterApp app;
	private static JTextField influencerTracker;
	private static JTextField maxFollow;
	private static JTextField pauseTime;
	private static JTextField targetAmount;
	private static JTextField numOfInfluencers;
	private static JTextField numOfTweets;
	private JButton btnPostTweets;
	public static JTextField userTxtField;
	public static JPasswordField passwordTxtField;
	public static JLabel lblWarning;
	
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
	 * @throws IOException 
	 */
	public Launcher() throws IOException {
		setTitle("Twitter Manager");
		File imgPath = new File("res/TwitterManagerIcon.png");
		setIconImage(ImageIO.read(imgPath));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 403);
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
		
		JLabel lblseconds = new JLabel("in seconds (+ 0-5 seconds)");
		
		targetAmount = new JTextField();
		targetAmount.setColumns(10);
		
		JLabel lblGrabTargetAmount = new JLabel("Grab Target Amount");
		
		numOfInfluencers = new JTextField();
		numOfInfluencers.setColumns(10);
		
		numOfTweets = new JTextField();
		numOfTweets.setColumns(10);
		
		JLabel lblOfInfluencers = new JLabel("# of influencers");
		
		JLabel lblOfTweets = new JLabel("# of tweets/ influencer");
		
		userTxtField = new JTextField();
		userTxtField.setColumns(11);
		
		passwordTxtField = new JPasswordField();
		passwordTxtField.setColumns(11);
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		
		lblWarning = new JLabel("");
		lblWarning.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWarning.setForeground(Color.RED);
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
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblseconds)
								.addComponent(lblBreakTimeIn)
								.addComponent(lblFollowAmount)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(targetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblGrabTargetAmount))
						.addComponent(lblGrabFollowersFrom)
						.addComponent(influencerTracker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrabAllFollowing)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(numOfInfluencers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblOfInfluencers))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(numOfTweets, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblOfTweets))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(userTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUsername))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(passwordTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPassword))
						.addComponent(lblWarning))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 294, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(maxFollow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFollowAmount))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(pauseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBreakTimeIn))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(numOfInfluencers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOfInfluencers))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(numOfTweets, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOfTweets))
					.addGap(37)
					.addComponent(lblGrabAllFollowing)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(influencerTracker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGrabFollowersFrom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(targetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrabTargetAmount))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWarning)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(69)
					.addComponent(lblseconds)
					.addContainerGap(211, Short.MAX_VALUE))
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
		
		btnPostTweets = new JButton("Post Tweet");
		btnPostTweets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnLogin = new JButton("Login");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
						.addComponent(grabTargetUsers, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
						.addComponent(followUsers, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
						.addComponent(btnPostTweets, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
						.addComponent(updateInfluencers, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(followUsers)
					.addGap(59)
					.addComponent(btnPostTweets)
					.addGap(76)
					.addComponent(updateInfluencers)
					.addGap(18)
					.addComponent(grabTargetUsers)
					.addGap(27)
					.addComponent(btnLogin)
					.addContainerGap(63, Short.MAX_VALUE))
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

	public static JTextField getNumOfInfluencers() {
		return numOfInfluencers;
	}

	public static JTextField getNumOfTweets() {
		return numOfTweets;
	}
}
