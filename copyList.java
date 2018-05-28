package shareTwitterList;

import twitter4j.TwitterException;
import twitter4j.UserList;

public class copyList {
	Twitterer tw1;
	Twitterer tw2;
	
	public copyList(Twitterer tw1,Twitterer tw2) {
		this.tw1 = tw1;
		this.tw2 = tw2;
	}
	
	public void copy() throws TwitterException {
		int i = 0;
		for(String listName1 : tw1.listNames) {
			boolean exist = false;
			for(String listName2 : tw2.listNames) {
				if(listName1.equals(listName2)) {
					exist = true;
					break;
				}
			}
			UserList ul = null;
			String slug = null;
			if(exist) {
				System.out.println(listName1 + "is exist");
				for(int j = 0;j<tw2.userNames.size();j++) {
					if(listName1.equals(tw2.listNames.get(j))) {
						slug = tw2.listSlugs.get(j);
						break;
					}
				}
			} else {
				ul = tw2.twitter.createUserList(listName1,false,"");
				slug = ul.getSlug();
			}
			
			
			for(long userID :tw1.userNames.get(i)) {
				tw2.twitter.createUserListMembers(tw2.twitter.getId(),slug,userID);
			}
			
			i++;
		}
	}

}
