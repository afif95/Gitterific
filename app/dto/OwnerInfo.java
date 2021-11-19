package dto;

public class OwnerInfo {
	
	private String login;
	
	public OwnerInfo() {}
	
	public OwnerInfo(String name) {
		this.login = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
