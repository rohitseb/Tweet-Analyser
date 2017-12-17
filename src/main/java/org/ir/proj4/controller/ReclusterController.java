package org.ir.proj4.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
import org.ir.proj4.newRepVO.AdjData;
import org.ir.proj4.newRepVO.AdjacenciesVO;
import org.ir.proj4.newRepVO.NodeData;
import org.ir.proj4.newRepVO.NodeVO;
import org.ir.proj4.vo.Data;

public class ReclusterController {

	public List<NodeVO> recluster(String id, List<NodeVO> data) {
		int listSize = data.size();
		int nextID = Integer.parseInt(data.get(listSize - 1).getId())+1;
		String qry = "";
		ArrayList<Document> documents = new ArrayList<Document>();
		NodeVO root = null;
		GetTweets summarization = new GetTweets();
		for (NodeVO node : data) {
			if (node.getId().equals(id)) {
				qry = node.getName();
				root = node;
				for (Data tweet : node.getTweets()) {
					String temp = " ";
					Document doc = new Document();
					doc.setSummary(tweet.getText_en());
					doc.setTitle(tweet.getHashtags());
					doc.setField("Doc ID", tweet.getDoc_ID());
					doc.setField("tweet_text", tweet.getTweet_text());
					documents.add(doc);

				}
			}
		}
		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();
		// Clustering using Lingo

		final Map<String, Object> fastAttributes = new HashMap<String, Object>();
		LingoClusteringAlgorithmDescriptor.attributeBuilder(fastAttributes).documents(documents)
				.desiredClusterCountBase(5).query(qry).matrixReducer()
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

		for (Cluster cluster : clustersByTopic) {
			boolean flag = true;
			if (!cluster.getLabel().equals("Other Topics")) {
				for (NodeVO node : data) {
					System.out.println(node.getName()+"0--------0"+cluster.getLabel());
					if (node.getName().equals(cluster.getLabel())) {
						System.out.println("Inside check!!!!");
						flag = false;
						break;
					}
				}
				if (flag) {
					NodeVO child = new NodeVO();

					child.setId("" + nextID);
					child.setName(cluster.getLabel());
					child.setData(new NodeData());
					child.getData().setDimension("" + (Math.max(12, cluster.size())));// Math.log10(cluster.size())*20)));
					child.getData().setColor("#ddeeff");// 3CB371
					child.setAdjacencies(new ArrayList<AdjacenciesVO>());
					child.setTest("hello");
					AdjacenciesVO rootAdj = new AdjacenciesVO();
					AdjacenciesVO childAdj = new AdjacenciesVO();

					rootAdj.setNodeTo("" + nextID);
					rootAdj.setData(new AdjData());
					rootAdj.getData().setWeight("3");// +(cluster.size()/2));

					childAdj.setNodeTo("" + id);
					childAdj.setData(new AdjData());
					childAdj.getData().setWeight("3");// +(cluster.size()/2));

					root.getAdjacencies().add(rootAdj);
					child.getAdjacencies().add(childAdj);

					data.add(child);

					List<Data> tweets = new ArrayList<Data>();

					for (Document document : cluster.getAllDocuments()) {
						Data tweet = new Data();
						tweet.setText_en(document.getSummary());
						tweet.setHashtags(document.getTitle());
						tweet.setTweet_text((String) document.getField("tweet_text"));
						tweet.setDoc_ID((String) (document.getField("Doc ID")));

						tweets.add(tweet);

						System.out.println("Cluster!! --- " + cluster.getLabel() + " - " + cluster.size());
						child.setTweets(tweets);
						++nextID;

						try {
							summarization.summarize(child);
							summarization.wikiSummary(child);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}

		}
		return data;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
