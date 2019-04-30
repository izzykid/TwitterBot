package apiCalls;

import twitter4j.IDs;
import twitter4j.TwitterException;

public class TestCall extends APICall {

	public TestCall() throws TwitterException {
//		System.out.println("id: " + twitter.getId());
		IDs fids = twitter.getFollowersIDs(757074558373343232l, -1);
		System.out.println(fids.getIDs().length);
	}

}
