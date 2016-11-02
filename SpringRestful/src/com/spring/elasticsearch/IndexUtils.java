package com.spring.elasticsearch;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class IndexUtils {
	
	public static void main(String[] args) throws IOException {
		Client client = getClient();
		String indexName = "test";
		String type = "Article";
		String source = FileUtils.readFileToString(new File("C:\\Users\\abhay.jain\\Workspace\\SpringRestful\\Resources\\ElasticSearch\\Article.json"), "UTF-8");
		if(client != null){
			if(client.admin().indices().prepareExists(indexName).execute().actionGet().isExists()){
				addMapping(client, indexName, type, source);
			}else{
				if(createIndex(client, indexName))
					System.out.println("Index Created successfully");
				else
					System.out.println("Index could not be created");
			}
			addMapping(client, indexName, type, source);
			addData(client, indexName, type);
		}
	}
	
	private static void addData(Client client, String indexName, String type) {
		File f = new File("C:\\Users\\abhay.jain\\Desktop\\reuters");
		try {
			for(File file : f.listFiles()){
				if(file.getName().startsWith("reut2")){
					String title = file.getName().split("\\.")[0];
					XContentBuilder builder = XContentFactory.jsonBuilder();
					builder.startObject()
					.field("Title", title)
					.field("Content", "")
					.field("Attachment Content", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file)))
					.endObject();
					String data = builder.string();
					addDocument(client, indexName, type, data, title);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void getExistingMapping(Client client, String indexName, String type) throws IOException {
		ClusterStateResponse resp = client.admin().cluster().prepareState().execute().actionGet();
		MappingMetaData mappingMetaData = resp.getState().getMetaData().index(indexName).mapping(type);
		/*Map<String, Object> sourceMap = mappingMetaData.getSourceAsMap();
		Map<String, Object> propertyMap = sourceMap.get("properties") != null ? (HashMap<String, Object>) sourceMap.get("properties") : new HashMap<String, Object>();
		propertyMap.remove("author");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "attachment");
		propertyMap.put("Attachment Content", map);
		sourceMap.put("properties", propertyMap);
		
		System.out.println(propertyMap);
		client.admin().indices().preparePutMapping(indexName).setType(type).setSource(sourceMap).execute().actionGet();*/
	}

	@SuppressWarnings("unused")
	private static void deleteDocument(Client client, String indexName, String type, String id) {
		client.prepareDelete(indexName, type, id).execute().actionGet();
	}

	private static void addDocument(Client client, String indexName, String type, String data, String id) throws IOException {
		client.prepareIndex(indexName, type).setId(id).setSource(data).execute().actionGet();
	}

	private static void addMapping(Client client, String indexName, String type, String source) {
		try {
			client.admin().indices().preparePutMapping(indexName).setType(type).setSource(source).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean createIndex(Client client, String indexName) {
		return client.admin().indices().prepareCreate(indexName).execute().actionGet().isAcknowledged();
	}

	private static Client getClient() {
		TransportClient client = null;
		try {
			Settings settings = Settings.settingsBuilder()
			        .put("cluster.name", "test").put("node.name", "abhay").build();
			client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return client;
	}
	
}