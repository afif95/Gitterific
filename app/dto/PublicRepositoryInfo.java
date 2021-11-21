package dto;

import java.util.ArrayList;

/**
 * Class used to define the attributes of a repository
 * 
 *
 */

public class PublicRepositoryInfo {
	 public float id;
	 public String node_id;
	 public String name;
	 public String full_name;
	 public boolean _private;
	 public PublicOwnerInfo OwnerObject;
	 public String html_url;
	 public String description;
	 public boolean fork;
	 public String url;
	 public String forks_url;
	 public String keys_url;
	 public String collaborators_url;
	 public String teams_url;
	 public String hooks_url;
	 public String issue_events_url;
	 public String events_url;
	 public String assignees_url;
	 public String branches_url;
	 public String tags_url;
	 public String blobs_url;
	 public String git_tags_url;
	 public String git_refs_url;
	 public String trees_url;
	 public String statuses_url;
	 public String languages_url;
	 public String stargazers_url;
	 public String contributors_url;
	 public String subscribers_url;
	 public String subscription_url;
	 public String commits_url;
	 public String git_commits_url;
	 public String comments_url;
	 public String issue_comment_url;
	 public String contents_url;
	 public String compare_url;
	 public String merges_url;
	 public String archive_url;
	 public String downloads_url;
	 public String issues_url;
	 public String pulls_url;
	 public String milestones_url;
	 public String notifications_url;
	 public String labels_url;
	 public String releases_url;
	 public String deployments_url;
	 public String created_at;
	 public String updated_at;
	 public String pushed_at;
	 public String git_url;
	 public String ssh_url;
	 public String clone_url;
	 public String svn_url;
	 public String homepage;
	 public float size;
	 public float stargazers_count;
	 public float watchers_count;
	 public String language;
	 public boolean has_issues;
	 public boolean has_projects;
	 public boolean has_downloads;
	 public boolean has_wiki;
	 public boolean has_pages;
	 public float forks_count;
	 public String mirror_url = null;
	 public boolean archived;
	 public boolean disabled;
	 public float open_issues_count;
	 public Object LicenseObject;	 
	 public boolean allow_forking;
	 public boolean is_template;
	 public ArrayList < Object > topics = new ArrayList < Object > ();
	 public String visibility;
	 public float forks;
	 public float open_issues;
	 public float watchers;
	 public String default_branch;
	 public String temp_clone_token = null;
	 public float network_count;
	 public float subscribers_count;
}
