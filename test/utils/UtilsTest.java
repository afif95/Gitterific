package utils;

import static org.junit.Assert.*;

import java.util.AbstractMap;
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
import play.libs.ws.WSClient;

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
		listOfRepos.add("abc");
		//pri.name = "Repo 1";
		//pri.description = "Desc 1";
		String jsonStr = "{\"full_name\" : \"Repo 1/abc\", \"description\" : \"Desc 1\"}";
		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			List<String> repos = util.getOwnerRepos(node);
			//System.out.println(repos);
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
	

	
	/**
	 * This is to used to test <code>JsontoIssueList</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>List</code> as an input, does it correctly 
	 * return the frequency of each word.
	 * 
	 * @author Afif Bin Kamrul
	 */
	@Test
	public void testJsontoIssueList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("issue1");
				

		String jsonStr = "{ \"title\" : \"issue1\"}";
			
		ObjectMapper object = new ObjectMapper();
		
		JsonNode node;
		try {
			node = object.readTree(jsonStr);
						
			List<String> testerlist = util.JsontoIssueList(node);
			assertEquals(list1,testerlist);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is to used to test <code>issue_to_title_frequency</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>List</code> as an input, does it correctly 
	 * return the frequency of each word.
	 * 
	 * @author Afif Bin Kamrul
	 */
	public void testissue_to_title_frequency() {
		List<String> list = new ArrayList<>();
		list.add("issue1");
		list.add("issue2");
		list.add("issue2");
		
		Map<String, Integer> freq = util.issue_to_title_frequency(list);
		
		assertTrue(freq.containsKey("issue1"));
		assertTrue(freq.get("issue1") == 1);
		
		assertTrue(freq.containsKey("issue2"));
		assertTrue(freq.get("issue2") == 2);
	}
	
	/**
	 * This is to used to test <code>issue_to_title_frequency_descendingorder</> method
	 * in <code>Utils</code> class. The method tests given the mentioned 
	 * method takes a <code>List</code> as an input, does it correctly 
	 * return the frequency of each word in sorted condition.
	 * 
	 * @author Afif Bin Kamrul
	 */
	public void testissue_to_title_frequency_descendingorder() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		list.put("issue1", 1);
		list.put("issue2", 2);
		
		Map<String, Integer> freq = util.issue_to_title_frequency_descendingorder(list);
		
		Map.Entry<String, Integer> actual = freq.entrySet().stream().findFirst().get();
		
		Map.Entry<String, Integer> expected = new AbstractMap.SimpleEntry<String,Integer>("issue2",2);

		assertEquals(expected,actual);
	}
	
	/**
	 * This is used to test <code>getIssuesRepo</> method
	 * in <code>Utils</> class.
	 * @author Roxane Tissier
	 */
	@Test
	public void testGetIssuesRepo() {
		String jsonStr = "{\"title\" : \"issue1\"}";
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			List<String> issues = util.getIssuesRepo(node);
			System.out.println(issues);
		//assertTrue(issues.contains("issue1"));
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This is used to test getCommit method
	 * in Utils class
	 * 
	 * @author Jason Khaou
	 */
	@Test
    public void testCommit() {

        String jsonStr = "{\"commit\" : \"commit1\"}";

        ObjectMapper mapper = new ObjectMapper();

        ObjectMapper objM = new ObjectMapper();
        try {
        List<JsonNode> testList = new ArrayList<>();
        testList.add(objM.readTree(jsonStr));

        JsonNode node;

            node = objM.readTree(jsonStr);


            List<JsonNode> list = util.getCommit(node);
            assertEquals("\"commit1\"", list.get(0).toString());
            

        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
