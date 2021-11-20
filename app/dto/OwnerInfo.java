package dto;
/** This class is used to store a repository owner's login.
 * 
 * @author Rimsha Afzal
 *
 */
public class OwnerInfo {
	
	private String login;
	
	public OwnerInfo() {}
	
	/**Class constructor with name
	 * 
	 * @param onwer's name
	 */
	public OwnerInfo(String name) {
		this.login = name;
	}

	/**This returns the login of the owner.
	 * 
	 * @return login of the owner
	 */
	public String getLogin() {
		return login;
	}
	
	/**This method sets the login of an owner.
	 * 
	 * @param login owner's login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

}
