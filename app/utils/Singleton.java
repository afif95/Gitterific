package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Repository;

public class Singleton {
	

	   private static Singleton singleton = new Singleton( );

	   /* A private Constructor prevents any other
	    * class from instantiating.
	    */
	   
	   HashMap <String, HashMap <String, List<Repository>>> user_searches;
	   
	   public HashMap <String, List<Repository>> getNum(String user) {
		return user_searches.get(user);
	   }
	   
	   
		public void setNum(String user, HashMap <String, List<Repository>> usm) {
			
			user_searches.put(user, usm);
			//user_searches.putIfAbsent(user,);
			
			//this.user_searches = usm;
		}
		
		public void setNum(String user,String searchTerm, List<Repository> usm) {
			if(user_searches.containsKey(user)) {
				if(user_searches.get(user).containsKey(searchTerm)) {
					user_searches.get(user).replace(searchTerm, usm);
				}
				else {
					user_searches.get(user).put(searchTerm, usm);
				}
			}
			else {
				HashMap <String, List<Repository>> repos = new HashMap<>();
				repos.put(searchTerm, usm);
				user_searches.put(user, repos);
			}
			//user_searches.put(user, usm);
			//user_searches.putIfAbsent(user,);
			
			//this.user_searches = usm;
		}



	private Singleton() { 
		user_searches=new HashMap<>();
	   }

	   /* Static 'instance' method */
	   public static Singleton getInstance( ) {
	      return singleton;
	   }

	   /* Other methods protected by singleton-ness */
	   protected static void demoMethod( ) {
	      System.out.println("demoMethod for singleton");
	   }
	

}
