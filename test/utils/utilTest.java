package utils;

import org.junit.*;

import dto.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class utilTest {
	utilClass util = new utilClass();

	/**
	 * This is to used to test <code>JSONtoRepoList</> method
	 * in <code>Util</code> class. The method tests if the list
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
	
	
	
	@Test
	public void testCreateJson() {
		String jsonStr = "{\"items\" : [{\"full_name\" : \"Repo 1\", \"name\" : \"repo 1\"}]}";
		ObjectMapper mapper = new ObjectMapper();

		
		ObjectMapper objM = new ObjectMapper();
		
		JsonNode node;
		try {
			node = objM.readTree(jsonStr);
			JsonNode json = util.createJson(jsonStr);
			assertEquals(node, json);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
