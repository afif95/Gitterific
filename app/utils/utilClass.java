package utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.routes;
import dto.*;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;


public class utilClass {

	/**This method is used for deserialization. 
	 * It converts the <code>JsonNode</code> data into a list of 
	 * objects of <code> Repository</code>.
	 * 
	 * 
	 * @author Amrit Singh
	 * @param node JsonNode extracted from an HTTP request 
	 * @return list of repositories
	 */
	public List<Repository> JSONtoRepoList(JsonNode node) {
		return StreamSupport.stream( node.get("items").spliterator(), false)
                .map(sObj -> Json.fromJson(sObj, Repository.class))
                .collect(Collectors.toList());
	}
	
	/**
	 * This method is used to create a json from a string.
	 * 
	 * @author roxane tissier
	 * @param str
	 * @return created json
	 */
	
	public JsonNode createJson(String str) {
		ObjectMapper objM = new ObjectMapper();
	
		JsonNode node = null;
		try {
			node = objM.readTree(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return node;
	}
	
}
