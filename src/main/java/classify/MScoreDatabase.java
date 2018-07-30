package classify;

import experiment.Param;
import org.lemurproject.galago.core.btree.simple.DiskMapReader;
import org.lemurproject.galago.tupleflow.Utility;
import org.lemurproject.galago.utility.ByteUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mhjang on 12/20/15.
 */
public class MScoreDatabase {
    HashMap<String, Double> mscoreDB;
    DiskMapReader reader;

    DiskMapReader revisedReader;
    boolean revise = false;

    public MScoreDatabase(boolean r, int networkOption) throws IOException {
        reader = new DiskMapReader(DataPath.MSCORE);
        this.revise = r;

        if (this.revise) {
            if (networkOption == Param.CLIQUE_BASED_NETWORK)
                revisedReader = new DiskMapReader(DataPath.REVISED_CLIQUE_MSCORE);
            else
                revisedReader = new DiskMapReader(DataPath.REVISED_PAIR_MSCORE);
        }

    }
    public void close() throws IOException {
        reader.close();
        if(revisedReader != null)
            revisedReader.close();
    }


    public void computeScore(AugmentedDocument doc, String votingMethod, int topK) {
        ArrayList<String> wikidocs = doc.relevantWiki;

        if (wikidocs.size() < topK) {
            topK = wikidocs.size();
        }
        List<String> list = wikidocs.subList(0, topK);
        Double finalScore = 0.0;

        if (votingMethod == Param.votingMAX) {
            for (String wiki : list) {
                double score = getScore(wiki);
                if (finalScore < score) {
                    finalScore = score;
                }
            }
        } else if (votingMethod == Param.votingAvG) {
            for (String wiki : list) {
                double score = getScore(wiki);
                finalScore += score;

            }
            finalScore /= (double) (list.size());
        }
        doc.mScore = finalScore;
    }


    public Double getScore(String word) {
        if(revise) {
            if(revisedReader.containsKey(ByteUtil.fromString(word))) {
                return Utility.toDouble(revisedReader.get(ByteUtil.fromString(word)));
            }
        }
        if(reader.containsKey(ByteUtil.fromString(word)))
            return Utility.toDouble(reader.get(ByteUtil.fromString(word)));
        else
            return 0.0;
    }

}
