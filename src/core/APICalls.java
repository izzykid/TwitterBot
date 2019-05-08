//package core;
//
//import java.util.List;
//
//import twitter4j.IDs;
//import twitter4j.ResponseList;
//import twitter4j.Status;
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.User;
//import twitter4j.api.UsersResources;
//import twitter4j.conf.ConfigurationBuilder;
//
//public class APICalls {
//	
//	public static String consumerKey = "1W5c62ffTm7fOuOXj1frxiCiz";
//	public static String consumerSecret = "WFQumR6JvGGpx1AFahp8vQbdlzYgsHg8t6A0FDr4Wxsb2hZDlw";
//	public static String accessToken = "1115740636051677184-4a6UjEmDGO29wubIZThqCVmprDxua8";
//	public static String accessTokenSecret = "5iOlOnTwudHJTJxiuPFsAeRXQ4FefwkHgoC3PgShv98Rr";
//	
//	public static void main(String[] args){
//		
//		try{
//			ConfigurationBuilder cb = new ConfigurationBuilder();
//			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
//			.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
//			TwitterFactory twitterFactory = new TwitterFactory(cb.build());
//			
//			Twitter twitter = twitterFactory.getInstance();
////			User user = twitter.verifyCredentials();
//			List<Status> statuses = twitter.getHomeTimeline();
////			IDs ids = twitter.getFollowersIDs(twitter.getId(), -1); // Gets followers list
//			IDs ids = twitter.getFriendsIDs(twitter.getId(), -1); // Gets following list
//			ResponseList<User> users = twitter.lookupUsers(ids.getIDs());
//			// Get followers of friends' accounts
//			IDs fids = twitter.getFollowersIDs(ids.getIDs()[0], -1);
//			ResponseList<User> friendFollowers = twitter.lookupUsers(fids.getIDs()[0]);
//			for(int i = 1; i < 10; i++) {
//				friendFollowers.add(twitter.lookupUsers(fids.getIDs()[i]).get(0));
//			}
//			// Prints out the user name of users
//			for(int i = 0; i < /*users*/friendFollowers.size(); i++){
//				System.out.println(friendFollowers.get(i).getScreenName());
//			}
//		
//			
////			System.out.println(ids.toString());
////			for(int i = 0; i < statuses.size(); i++){
//////				System.out.println("Showing @" + users.get(i).getScreenName() + "'s home timeline");
////				System.out.println("@" + statuses.get(i).getUser().getScreenName() + " - " + statuses.get(i).getText() +
////							" Favorite count: " + statuses.get(i).getFavoriteCount() + " Retweet Count: " +
////							statuses.get(i).getRetweetCount());
////			}
//		}
//		catch(TwitterException e){
//			e.printStackTrace();
//			System.exit(1);
//		}
//	}
//}
