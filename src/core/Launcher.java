package core;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Launcher extends JFrame {
	private static TwitterApp app;
	private static JTextField maxFollow;
	private static JTextField pauseTime;
	private static JTextField influencerTracker;
	private static JTextField targetAmount;
	private static JTextField numOfInfluencers;
	private static JTextField numOfTweets;
	private static JTextField userTxtField;
	private static JPasswordField passwordTxtField;
	private static JLabel warningLabel;
	private static JLabel lblHello;
	private static JButton btnPostTweets;

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
		setTitle("Twitter Manager");
		File imgPath = new File("res/TwitterManagerIcon.png");
		try {
			setIconImage(ImageIO.read(imgPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().setBackground(new Color(204, 204, 204));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 437);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(215, 11, 443, 376);
		getContentPane().add(tabbedPane);
		
		JPanel tab1 = new JPanel();
		tab1.setBackground(SystemColor.window);
		tab1.setToolTipText("");
		tabbedPane.addTab("Follow Users", null, tab1, null);
		tab1.setLayout(null);
		
		maxFollow = new JTextField();
		maxFollow.setBounds(99, 74, 230, 43);
		tab1.add(maxFollow);
		maxFollow.setColumns(10);
		
		pauseTime = new JTextField();
		pauseTime.setBounds(102, 183, 230, 43);
		tab1.add(pauseTime);
		pauseTime.setColumns(10);
		
		JLabel lblEnterTheNumber = new JLabel("Enter the number of users you want to follow:");
		lblEnterTheNumber.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		lblEnterTheNumber.setBounds(49, 29, 379, 34);
		tab1.add(lblEnterTheNumber);
		
		JLabel lblEnterTheFollowing = new JLabel("Enter the rate of following (? per second):");
		lblEnterTheFollowing.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		lblEnterTheFollowing.setBounds(69, 142, 342, 32);
		tab1.add(lblEnterTheFollowing);
		
		JButton followUsers = new JButton("Follow Users");
		followUsers.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 15));
		followUsers.setBounds(146, 268, 143, 48);
		tab1.add(followUsers);
		
		JPanel tab2 = new JPanel();
		tab2.setLayout(null);
		tab2.setToolTipText("");
		tab2.setBackground(Color.WHITE);
		tabbedPane.addTab("Update Influencers", null, tab2, null);
		
		influencerTracker = new JTextField();
		influencerTracker.setColumns(10);
		influencerTracker.setBounds(99, 67, 230, 43);
		tab2.add(influencerTracker);
		
		targetAmount = new JTextField();
		targetAmount.setColumns(10);
		targetAmount.setBounds(99, 221, 230, 43);
		tab2.add(targetAmount);
		
		JLabel lblEnterAUsername = new JLabel("Enter a username to grab their following list:");
		lblEnterAUsername.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		lblEnterAUsername.setBounds(49, 22, 379, 34);
		tab2.add(lblEnterAUsername);
		
		JLabel lblEnterTheTarget = new JLabel("Enter the target amount:");
		lblEnterTheTarget.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		lblEnterTheTarget.setBounds(120, 178, 172, 32);
		tab2.add(lblEnterTheTarget);
		
		JButton updateInfluencers = new JButton("Update Influencers");
		updateInfluencers.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 15));
		updateInfluencers.setBounds(120, 119, 197, 48);
		tab2.add(updateInfluencers);
		
		JButton grabTargetUsers = new JButton("Grab Targets");
		grabTargetUsers.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 15));
		grabTargetUsers.setBounds(150, 272, 143, 48);
		tab2.add(grabTargetUsers);
		
		JPanel tab3 = new JPanel();
		tab3.setLayout(null);
		tab3.setToolTipText("");
		tab3.setBackground(Color.WHITE);
		tabbedPane.addTab("Tweet", null, tab3, null);
		
		numOfInfluencers = new JTextField();
		numOfInfluencers.setColumns(10);
		numOfInfluencers.setBounds(99, 74, 230, 43);
		tab3.add(numOfInfluencers);
		
		numOfTweets = new JTextField();
		numOfTweets.setColumns(10);
		numOfTweets.setBounds(102, 183, 230, 43);
		tab3.add(numOfTweets);
		
		JLabel lblEnterTheNumber_1 = new JLabel("How many influencers do you want to get tweets from?");
		lblEnterTheNumber_1.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblEnterTheNumber_1.setBounds(33, 29, 351, 34);
		tab3.add(lblEnterTheNumber_1);
		
		JLabel howManyTweetsSearch = new JLabel("How many tweets do you want to search through per influencer?");
		howManyTweetsSearch.setFont(new Font("Dialog", Font.PLAIN, 14));
		howManyTweetsSearch.setBounds(22, 138, 414, 32);
		tab3.add(howManyTweetsSearch);
		
		btnPostTweets = new JButton("Post Tweet");
		btnPostTweets.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 15));
		btnPostTweets.setBounds(150, 253, 143, 48);
		tab3.add(btnPostTweets);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 204, 255));
		panel.setBounds(10, 11, 206, 376);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblHello = new JLabel("Welcome to TwitterManager");
		lblHello.setFont(new Font("Microsoft JhengHei Light", Font.BOLD | Font.ITALIC, 11));
		lblHello.setBounds(24, 220, 211, 26);
		panel.add(lblHello);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Launcher.class.getResource("/media/Webp.net-resizeimage.png")));
		lblNewLabel.setBounds(2, 0, 196, 210);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 204, 255));
		panel_1.setBounds(20, 251, 186, 114);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		userTxtField = new JTextField();
		userTxtField.setText("Username");
		userTxtField.setBounds(43, 11, 86, 20);
		panel_1.add(userTxtField);
		userTxtField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(40, 73, 89, 23);
		panel_1.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().login = true;
			}
		});
		
		passwordTxtField = new JPasswordField();
		passwordTxtField.setText("Password");
		passwordTxtField.setBounds(43, 42, 86, 20);
		panel_1.add(passwordTxtField);
		
		warningLabel = new JLabel("");
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setBounds(24, 209, 170, 14);
		panel.add(warningLabel);
		
		//event listeners
		

		grabTargetUsers.setForeground(Color.BLACK);
		grabTargetUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().grabTargets = true;
			}
		});
		
		updateInfluencers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.getButtonManager().updateInfluencers = true;
			}
		});
		
		followUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.getButtonManager().followUsers = true;
			}
		});
		
		btnPostTweets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.getButtonManager().postTweet = true;
			}
		});
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


	public static JTextField getUserTxtField() {
		return userTxtField;
	}


	public static JPasswordField getPasswordTxtField() {
		return passwordTxtField;
	}


	public static JLabel getWarningLabel() {
		return warningLabel;
	}


	public static void setWarningLabel(String warningLabel) {
		Launcher.warningLabel.setText(warningLabel);
	}


	public static JLabel getLblHello() {
		return lblHello;
	}


	public static void setLblHello(String lblHello) {
		Launcher.lblHello.setText(lblHello);;
	}


	public static void setUserTxtField(String userTxtField) {
		Launcher.userTxtField.setText(userTxtField);
	}


	public static void setPasswordTxtField(String passwordTxtField) {
		Launcher.passwordTxtField.setText(passwordTxtField);
	}

}
