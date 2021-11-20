package utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import utils.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.PublicOwnerInfo;
import dto.PublicRepositoryInfo;
import dto.Repository;
import play.api.libs.ws.WSResponse;
import play.libs.Json;

import static java.util.stream.Collectors.toList;

public class UtilsTest {
	
	UtilClass util = new UtilClass();
	
	
	/**
	 * This is to used to test <code>JSONtoRepoList</> method
	 * in <code>Utils</code> class. The method tests if the list
	 * returned by the mentioned method is the same as what was expected.
	 * 
	 * @author Amrit Singh
	 */
	@Test
	public void testJSONtoRepoList() {
		
		List<Repository> testList = new ArrayList<>();
		testList.add(new Repository("Repo 1", "repo 1"));
		//testList.add(new Repository("Repo 1", "repo 1"));
		//testList.add(new Repository("Repo 1", "repo 1"));
		
		String jsonStr = "{\"items\" : [{\"full_name\" : \"Repo 1\", \"name\" : \"repo 1\"}]}";
		
		
		ObjectMapper mapper = new ObjectMapper();

		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			
			//node = mapper.readValue("{[{\"full_name\" : \"Repo 1\", \"name\" : \"repo 1\"}]}", JsonNode.class);
			
			List<Repository> list = util.JSONtoRepoList(node);
			//assertEquals(list, testList);
			assertTrue(list.get(0).full_name.equals(testList.get(0).full_name));
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * This is to used to test <code>GetPublicOwnerInfo</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>JsonNode</code> as an input, does it
	 * map it correctly to the <code>PublicOwnerInfo</code> object.
	 * 
	 * @author Rimsha Afzal
	 */
	@Test
	public void testGetPublicOwnerInfo() {
		
		PublicOwnerInfo poi = new PublicOwnerInfo();
		poi.login = "User 1";
		poi.id = 1;
		
		String jsonStr = "{\"owner\" : \"{\"login\" : \"User 1\", \"id\" : 1}}";
		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			PublicOwnerInfo list = util.getPublicOwnerInfo(node);
			assertEquals(list, poi);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * This is to used to test <code>GetPublicRepositoryInfo</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>JsonNode</code> as an input, does it map it correctly to the
	 * <code>PublicRepositoryInfo</code> object.
	 * 
	 * @author Roxane Tissier
	 */
	@Test
	public void testetPublicRepositoryInfo() {
		
		PublicRepositoryInfo pri = new PublicRepositoryInfo();
		pri.name = "Repo 1";
		pri.description = "Desc 1";
		
		String jsonStr = "{\"name\" : \"Repo 1\", \"description\" : \"Desc 1\"}";
		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			PublicRepositoryInfo list = util.getPublicRepositoryInfo(node);
			assertEquals(list.name, pri.name);
			assertEquals(list.description, list.description);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This is to used to test <code>getOwnerRepos</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>JsonNode</code> as an input, does it correctly returns
	 * the list of repositories for an owner.
	 * 
	 * @author Rimsha Afzal
	 */
	@Test
	public void testOwnerRepo() {
		List<String> listOfRepos = new ArrayList<>();
		listOfRepos.add("Repo 1");
		//pri.name = "Repo 1";
		//pri.description = "Desc 1";
		
		String jsonStr = "{\"name\" : \"Repo 1\", \"description\" : \"Desc 1\"}";
		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			List<String> repos = util.getOwnerRepos(node);
			assertEquals(listOfRepos, repos);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This is to used to test <code>insertKeyInOwnerMap</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>JsonNode</code>, a <code>HashMap</code> and User Login 
	 * as an input, does it correctly returns and stores the owner login 
	 * and owner's object in a <code>HashMap</code>.
	 * 
	 * @author Rimsha Afzal
	 */
	@Test
	public void testInsertKeyInOwnerMap() {
		HashMap <String, PublicOwnerInfo> map = new HashMap();
		
		String jsonStr = "{\"owner\" : \"{\"login\" : \"User 1\", \"id\" : 1}}";
		ObjectMapper objM = new ObjectMapper();
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			String searchKey = "User 1";
			map = util.insertKeyInOwnerMap(node, map, searchKey);
			assertTrue(map.containsKey(searchKey));
			assertTrue(map.containsValue(Json.fromJson(node, PublicOwnerInfo.class)));
		}
		catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This is to used to test <code>addRepoToUserMap</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>HashMap</code>, a <code>List</code> and and the id 
	 * of the user who was searching as an input, does it correctly returns 
	 * and stores the user and his searches in a <code>HashMap</code>.
	 * 
	 * @author Roxane Tissier
	 */
	@Test
	public void testAddRepoToUserMap() {
		HashMap <String, List<Repository>> map = new HashMap();
		
		String s = "tetris";
		List<Repository> list = new ArrayList<>();
		List<Repository> list101 = Stream
				.generate(() -> (new Repository("temp", "temp/temp")))
				.limit(101)
				.collect(toList());
		
		map = util.addRepoToUserMap(s, map, list);
		assertTrue(map.containsKey(s));
		assertTrue(map.containsValue(list));
		
		map = util.addRepoToUserMap(s, map, list);
		assertTrue(map.containsKey(s));
		assertTrue(map.containsValue(list));
		
		map = util.addRepoToUserMap(s, map, list101);
		map = util.addRepoToUserMap(s, map, list);
		assertTrue(map.containsKey(s));
		
	}
	
	
<<<<<<< HEAD
	/*public void testGetIssues() {
=======
	
	/**
	 * This is to used to test <code>getIssues</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>List</code> as an input, does it correctly 
	 * return the frequency of each word.
	 * 
	 * @author Afif Bin Kamrul
	 */
	@Test
	public void testGetIssues() {
>>>>>>> d9d2fd9ce34437b62d2f0ac03af8e5238d05e39a
		List<String> list = new ArrayList<>();
		list.add("issue1");
		list.add("issue2");
		list.add("issue2");
		
		Map<String, Integer> freq = util.getIssues(list);
		
		assertTrue(freq.containsKey("issue1"));
		assertTrue(freq.get("issue1") == 1);
		
		assertTrue(freq.containsKey("issue2"));
		assertTrue(freq.get("issue2") == 2);
	}*/
	
}
