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

import com.fasterxml.jackson.databind.JsonNode;

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
	
	
	
}
