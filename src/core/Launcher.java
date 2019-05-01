package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import apiCalls.APICall;
import apiCalls.TweetGrabber;
import apiCalls.TweetPoster;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private JPanel contentPane;
	private static TwitterApp app;
	private static JTextField influencerTracker;
	private static JTextField maxFollow;
	private static JTextField pauseTime;
	private static JTextField targetAmount;
	
	private JTextField pinTxtField;
	private JButton btnPostTweets;
	
	private static TweetPoster tweetPoster = null;
	private static boolean authenticated = false;
	
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
		try {
			tweetPoster = new TweetPoster();
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		pinTxtField = new JTextField();
		pinTxtField.setColumns(11);
		
		JLabel lblUsername = new JLabel("Access PIN");
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
							.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblBreakTimeIn)
									.addComponent(lblFollowAmount))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblseconds)
									.addGap(35))))
						.addComponent(influencerTracker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrabAllFollowing)
						.addComponent(lblGrabFollowersFrom)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(targetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblGrabTargetAmount))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(pinTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUsername)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(maxFollow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFollowAmount))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(pauseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBreakTimeIn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(pinTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addGap(31)
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
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(55)
					.addComponent(lblseconds)
					.addContainerGap(182, Short.MAX_VALUE))
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
		
		btnPostTweets = new JButton("(Req: PIN) Post Tweet");
		btnPostTweets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tweetPoster.requestToken != null && pinTxtField.getText().trim().length() > 0) {
					try {
						if(!authenticated) {
							tweetPoster.getAuthenticatedSession(pinTxtField.getText().trim());
							authenticated = true;
						}
						
						tweetPoster.repostBestTweet();
					} catch (TwitterException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		JButton btnPINBtn = new JButton("Get PIN");
		btnPINBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					authenticated = false;
					tweetPoster.getRequestToken();
					Runtime.getRuntime().exec(new String[] {"cmd", "/c", "start chrome " + tweetPoster.requestToken.getAuthorizationURL()});
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(followUsers, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
								.addComponent(updateInfluencers, GroupLayout.PREFERRED_SIZE, 155, Short.MAX_VALUE)
								.addComponent(grabTargetUsers, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPINBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
								.addComponent(btnPostTweets, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(followUsers)
					.addGap(27)
					.addComponent(btnPINBtn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPostTweets)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(updateInfluencers)
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
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
