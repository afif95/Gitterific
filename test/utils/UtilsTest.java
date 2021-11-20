package utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
	
	/*@Test
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
			assertEquals(list, pri);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
