package classify;

import experiment.Evaluator;
import experiment.Param;
import utils.DirectoryManager;
import utils.SimpleFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by mhjang on 7/30/2018
 */

public class ControversyClassifier {
    CScoreDatabase csd;
    MScoreDatabase msd;
    DScoreDatabase dsd;


    public void run(Param param, String trainDir) throws IOException {
        int networkOption;
        if(param.networkSmoothing == "clique")
            networkOption = Param.CLIQUE_BASED_NETWORK;
        else
            networkOption = Param.PAIR_BASED_NETWORK;
        csd = new CScoreDatabase(param.revised, networkOption);
        msd = new MScoreDatabase(param.revised, networkOption);
        dsd = new DScoreDatabase();

        VotingParameter votingParam = new VotingParameter.Builder().setVotingMethod(param.voting)
                .setMThreshold(param.threshold_M).setCScoreThreshold(param.threshold_C).build();
        VotingClassifer votingClassifer = new VotingClassifer(votingParam);

        String retrievedDir = "";  // to set
        String datasetDir = DataPath.CLUEWEB;// to set
        String querymethod = param.querygen;
        if (querymethod.equals(Param.QueryMethod.ALLQUERY))
            retrievedDir = datasetDir + DataPath.ALLQUERY;
        else if (querymethod.equals(Param.QueryMethod.TF10QUERY))
            retrievedDir = datasetDir + DataPath.TF10QUERY;
        else if (querymethod.equals(Param.QueryMethod.TIlEQUERY))
            retrievedDir = datasetDir + DataPath.TILEQUERY;
        else if (querymethod.equals(Param.QueryMethod.WIKIFIER))
            retrievedDir = datasetDir + DataPath.WIKIFIERQUERY;

        HashMap<String, ArrayList<String>> wikiRankedList = new HashMap<String, ArrayList<String>>();
        SimpleFileReader sr2 = new SimpleFileReader(retrievedDir);
        while (sr2.hasMoreLines()) {
            String line = sr2.readLine();
            String[] tokens = line.split("\t");
            String queryId = tokens[0];
            if (!wikiRankedList.containsKey(queryId))
                wikiRankedList.put(queryId, new ArrayList<String>());
            wikiRankedList.get(queryId).add(tokens[1].toLowerCase());
        }
        sr2.close();

        Evaluator eval = new Evaluator(datasetDir + DataPath.GOLDSTANDARD);

        DirectoryManager dm = new DirectoryManager(trainDir);
        ArrayList<AugmentedDocument> queryDocs = new ArrayList<AugmentedDocument>();
        for (String queryId : dm.getFileNameList()) {
            AugmentedDocument doc = new AugmentedDocument(queryId);
            ArrayList<String> topKList = wikiRankedList.get(queryId);
            doc.setRelevantWiki(topKList);
            /**
             * Note: a label with higher than 2.5 is non-controversial!!
             */
            if(eval.getLabeledControversyScore(queryId) > 2.5)
                doc.label = false;
            else
                doc.label = true;

            csd.computeScore(doc, param.aggregation, param.k);
            msd.computeScore(doc, param.aggregation, param.k);
            dsd.computeScore(doc, param.aggregation, param.k);
            votingClassifer.classify(doc);

            queryDocs.add(doc);
        }
        eval.evaluate(queryDocs);

    }
}
