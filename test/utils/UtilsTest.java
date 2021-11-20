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
	
	
	/*public void testGetIssues() {
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
