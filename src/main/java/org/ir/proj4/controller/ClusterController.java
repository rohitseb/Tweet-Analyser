package org.ir.proj4.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.carrot2.matrix.factorization.IterationNumberGuesser;
import org.carrot2.matrix.factorization.LocalNonnegativeMatrixFactorizationFactory;
import org.carrot2.matrix.factorization.NonnegativeMatrixFactorizationEDFactory;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.ir.proj4.newRepVO.AdjData;
import org.ir.proj4.newRepVO.AdjacenciesVO;
import org.ir.proj4.newRepVO.Graph;
import org.ir.proj4.newRepVO.NodeData;
import org.ir.proj4.newRepVO.NodeVO;
import org.ir.proj4.vo.Data;
import org.ir.proj4.vo.Docs;
import org.ir.proj4.vo.SubTopic;
import org.ir.proj4.vo.Tweet;
import org.ir.proj4.vo.VizualizationVO;

public class ClusterController {

	// TODO Auto-generated method stub
	// http://localhost:9000/solr/IRF16P1/select?fq=hashtags%20=%20%22Russia%22&indent=on&q=*:*&rows=1000&wt=json

	static String qry = "";
	String user_name;

	// function for clustering using lingo.

	public Graph cluster(String query)// int n, double merge, double phr_boost, double
							// title_wrd_boost, double trunc_label_thresh,
							// double wrd_doc_freq_thresh) throws
							// URISyntaxException
	{
		int i = 0;
		ArrayList<Document> documents = new ArrayList<Document>();
		VizualizationVO data = new VizualizationVO();
		Graph graph = new Graph();
		data.setId("1");
		data.setName("Christmas");
		data.setChildren(new ArrayList<VizualizationVO>());
		List<SubTopic> clusters = new ArrayList<SubTopic>();
		GetTweets summarization = new GetTweets();
		Map<String,String> idTweetMap = new HashMap<String,String>();
		// doc.set
		try {
			//String qry = "http://localhost:8983/solr/IRF16P1/select?fl=tweet_text,%20tweet_lang,%20id,%20hashtags&indent=on&q=select%20*.*%20where%20topic=%22News%22&rows=500&wt=json";
			//http://localhost:9000/solr/IRP4/select?fl=text_en,%20score&indent=on&q=Christmas&sort=score%20desc&wt=json
			
			String qry = "http://ec2-35-163-233-12.us-west-2.compute.amazonaws.com:8983/solr/IRP4/select?fl=text_en,tweet_text,topic_name,doc_ID,hashtags&indent=on&q="+query.trim()+"&rows=5000&sort=score%20desc&wt=json";
			
			String inputline, pprids = "";
			System.out.println("query term is!!!!!!!!!!!!!!!" + qry);
			// qry="select paper_ids from query_1 where query_name =
			// '"+qry+"'";//where query= '" + qry +"'"; //the abstract of the
			// papers to be clustered is retrieved
			ObjectMapper mapper = new ObjectMapper();
			List<Data> rootTweets = new ArrayList<Data>();
			System.out.println(qry);
			URL url;
			url = new URL(qry);
			Tweet response = mapper.readValue(url, Tweet.class);
	//		db.createTweetStore(1);
	//		db.createClustersTable();
			for (i = 0; i < response.getResponse().getDocs().length; ++i) {
				Document doc = new Document();

				String temp = " ";
				if (response.getResponse().getDocs()[i].getHashtags() != null
						&& response.getResponse().getDocs()[i].getHashtags().length > 0) {
					for (int j = 0; j < response.getResponse().getDocs()[i].getHashtags().length; ++j) {
						temp += response.getResponse().getDocs()[i].getHashtags()[j] + " ";
					}
				}
				Data tweet = new Data();
				tweet.setTweet_text(response.getResponse().getDocs()[i].getTweet_text());
				tweet.setHashtags(temp);
				System.out.println(response.getResponse().getDocs()[i].getDoc_ID());
				//tweet.setDoc_ID((String)response.getResponse().getDocs()[i].getDoc_ID()[0]);
				tweet.setText_en(response.getResponse().getDocs()[i].getText_en());
				rootTweets.add(tweet);

				// DB statement
				
			//	db.insertTweetStore(1, tweet.getTweet_text(), tweet.getHashtags());

				doc.setSummary(response.getResponse().getDocs()[i].getText_en());

				doc.setTitle(temp);
				doc.setField("Doc ID", response.getResponse().getDocs()[i].getDoc_ID());
				doc.setField("tweet_text", response.getResponse().getDocs()[i].getTweet_text());
				idTweetMap.put(response.getResponse().getDocs()[i].getDoc_ID(), response.getResponse().getDocs()[i].getTweet_text());
				documents.add(doc);
				
				// System.out.println(i+" -
				// "+response.getResponse().getDocs()[i].getTweet_text()[0]);

			}
			// documents= create_doc.getdocuments();

			/* A controller to manage the processing pipeline. */
			final Controller controller = ControllerFactory.createSimple();
			// Clustering using Lingo

			final Map<String, Object> fastAttributes = new HashMap<String, Object>();
			LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes).documents(documents)
					.desiredClusterCountBase(15).query(qry).matrixReducer()
					.factorizationFactory(LocalNonnegativeMatrixFactorizationFactory.class)// IterativeMatrixFactorizationFactory.class,
																							// KMeansMatrixFactorizationFactory.class,
																							// LocalNonnegativeMatrixFactorizationFactory.class,
																							// PartialSingularValueDecompositionFactory.class,
																							// NonnegativeMatrixFactorizationKLFactory.class,
																							// NonnegativeMatrixFactorizationEDFactory.class)
					.factorizationQuality(IterationNumberGuesser.FactorizationQuality.LOW);
			LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes).clusterBuilder()
					.clusterMergingThreshold(0.40).phraseLabelBoost(1);

			LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes).matrixBuilder().titleWordsBoost(1)
					.maximumMatrixSize(40000).maxWordDf(0.6);
			final ProcessingResult byTopicClusters = controller.process(fastAttributes, LingoClusteringAlgorithm.class);
			final List<Cluster> clustersByTopic = byTopicClusters.getClusters();
			// ConsoleFormatter c= new
			// ConsoleFormatter(0,0,qry,connection,user_name);
			// c.displayClusters(clustersByTopic);

			int count = 2;

			graph.setGraph(new ArrayList<NodeVO>());
			NodeVO root = new NodeVO();

			int nodeID = 1;
			root.setName(query);
			root.setId("" + nodeID);
			
			graph.getGraph().add(root);
			root.setData(new NodeData());
			root.getData().setDimension("25");
			root.getData().setType("star");
			root.getData().setColor("#d10a0a");
			root.setAdjacencies(new ArrayList<AdjacenciesVO>());
			root.setTweets(rootTweets);
			root.setTest("hellei");
			summarization.summarize(root);
			summarization.wikiSummary(root);

			//db Statement
			//db.insertClustersTable(nodeID, "Christmas", 0);
			++nodeID;
			int clusCount=0;
			for (Cluster cluster : clustersByTopic) {
				if (!cluster.getLabel().equals("Other Topics")&&clusCount<30) {
					NodeVO child = new NodeVO();
					/*
					 * VizualizationVO child = new VizualizationVO();
					 * child.setName(cluster.getLabel());
					 * child.setId(""+count++);
					 */
					child.setId("" + nodeID);
					child.setName(cluster.getLabel());
					child.setData(new NodeData());
					child.getData().setDimension("" + (Math.log10(cluster.size())*20));//Math.max(12, cluster.size())));//
					child.getData().setColor("#ddeeff");//3CB371
					child.setAdjacencies(new ArrayList<AdjacenciesVO>());
					child.setTest("hello");
					AdjacenciesVO rootAdj = new AdjacenciesVO();
					AdjacenciesVO childAdj = new AdjacenciesVO();

					rootAdj.setNodeTo("" + nodeID);
					rootAdj.setData(new AdjData());
					rootAdj.getData().setWeight("3");// +(cluster.size()/2));

					childAdj.setNodeTo("" + 1);
					childAdj.setData(new AdjData());
					childAdj.getData().setWeight("3");// +(cluster.size()/2));

					root.getAdjacencies().add(rootAdj);
					child.getAdjacencies().add(childAdj);

					graph.getGraph().add(child);

					/*
					 * SubTopic subtopic = new SubTopic();
					 * subtopic.setName(cluster.getLabel());
					 * subtopic.setSize(cluster.size());
					 */

					List<Data> tweets = new ArrayList<Data>();

					// db create
			//		db.createTweetStore(nodeID);
			//		db.insertClustersTable(nodeID, cluster.getLabel(), 1);

					for (Document document : cluster.getAllDocuments()) {
						Data tweet = new Data();
						tweet.setHashtags(document.getTitle());
						tweet.setText_en(document.getSummary());
						tweet.setTweet_text((String)document.getField("tweet_text"));
						tweet.setDoc_ID((String)(document.getField("Doc ID")));
						
						tweets.add(tweet);

						// db
			//			db.insertTweetStore(nodeID, tweet.getTweet_text(), tweet.getHashtags());

					}
					/*
					 * subtopic.setTweets(tweets); clusters.add(subtopic);
					 */
					
					System.out.println("Cluster!! --- " + cluster.getLabel() + " - " + cluster.size());
					child.setTweets(tweets);
					++nodeID;
					summarization.summarize(child);
					summarization.wikiSummary(child);
					
					// data.getChildrCen().add(child);
					++clusCount;
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return graph;

	}

	// Function which reclusters a node on right clicking
	/*
	 * public int re_cluster(String s,int n) throws URISyntaxException {
	 * 
	 * int i=0; ArrayList<Document> documents = new ArrayList<Document>();
	 * PreparedStatement pst; ResultSet rs; String qry="",abs=null,pprids="";
	 * int l=0,clusid=0; try { System.err.println("REST1.JAVA!!!");
	 * pst=connection.con.
	 * prepareStatement("select clusid, pprids from clusters_"
	 * +user_name+" where name = '"+s+"'"); rs = pst.executeQuery(); while
	 * (rs.next()) { clusid=rs.getInt(1); pprids=rs.getString(2); } pst.close();
	 * rs.close(); create_document create_doc = new
	 * create_document(pprids,connection);
	 * documents=create_doc.get_doc_recluster();
	 * 
	 * 
	 * int j;
	 * 
	 * A controller to manage the processing pipeline. final Controller
	 * controller = ControllerFactory.createSimple();
	 * 
	 * 
	 * Perform clustering by topic using the Lingo algorithm. Lingo can take
	 * advantage of the original query, so we provide it along with the
	 * documents.
	 * 
	 * final Map<String, Object> fastAttributes = Maps.newHashMap();
	 * LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes)
	 * .documents(documents) .desiredClusterCountBase(6) .query(s)
	 * .matrixReducer().factorizationFactory(
	 * NonnegativeMatrixFactorizationEDFactory.class)//
	 * IterativeMatrixFactorizationFactory.class,
	 * KMeansMatrixFactorizationFactory.class,
	 * LocalNonnegativeMatrixFactorizationFactory.class,
	 * PartialSingularValueDecompositionFactory.class,
	 * NonnegativeMatrixFactorizationKLFactory.class,
	 * NonnegativeMatrixFactorizationEDFactory.class)
	 * .factorizationQuality(IterationNumberGuesser.FactorizationQuality.LOW);
	 * LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes)
	 * .clusterBuilder().clusterMergingThreshold(0.7) .phraseLabelBoost(4.0);
	 * //title word boost, max matrix size, max worddf
	 * LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes).
	 * matrixBuilder().titleWordsBoost(2.5).maximumMatrixSize(40000).maxWordDf(0
	 * .9); final ProcessingResult byTopicClusters =
	 * controller.process(fastAttributes,
	 * LingoClusteringAlgorithm.class);//LingoClusteringAlgorithm.class); final
	 * List<Cluster> clustersByTopic = byTopicClusters.getClusters();
	 * ConsoleFormatter cf= new
	 * ConsoleFormatter(clusid,n,s,connection,user_name);
	 * cf.displayClusters(clustersByTopic);
	 * 
	 * } catch(Exception e) {
	 * System.err.println("Normal_Clustering function - recluster()"+e); }
	 * return (1);
	 * 
	 * 
	 * }
	 */

	public static void main(String[] args) throws URISyntaxException {
		/*
		 * database con= new database(); normal_clustering_lingo c=new
		 * normal_clustering_lingo("granule+neuron",con,""); //c.cluster(6, 0.7,
		 * 4.0, 2.5, 0.5, 0.9); clusdisp cldisp= new clusdisp(con,user_name);
		 * //cldisp.clus_to_neo(qry); cleardb clr= new cleardb(con);
		 * clr.clear(); c.re_cluster("Other Topics_0", 9);
		 * cldisp.reclus_neo("Other Topics_0", 42);
		 */
		ClusterController test = new ClusterController();
		test.cluster("Christmas");// n, merge, phr_boost, title_wrd_boost,
						// trunc_label_thresh, wrd_doc_freq_thresh)

	}
}
