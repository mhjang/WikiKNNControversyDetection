package classify;

import org.lemurproject.galago.core.btree.simple.DiskMapReader;
import org.lemurproject.galago.utility.ByteUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mhjang on 1/8/16.
 * for dispute tag
 */
public class DScoreDatabase {
    HashSet<String> dscoreDB;
    DiskMapReader reader;
    public DScoreDatabase() throws IOException {
        dscoreDB = new HashSet<String>();
        reader = new DiskMapReader(DataPath.DSCORE);
    }

    public void close() throws IOException {
        reader.close();
    }


    public void computeScore(AugmentedDocument doc, String votingMethod, int topK) {
        ArrayList<String> wikidocs = doc.relevantWiki;

        if(wikidocs.size() < topK) {
            topK = wikidocs.size();
        }
        List<String> list  = wikidocs.subList(0, topK);
        int count = 0;

        for (String wiki : list) {
            if (getScore(wiki)> 0.0) {
                count++;
            }
        }
        doc.dScore = count;
    }

    public Double getScore(String word) {
        if(reader.containsKey(ByteUtil.fromString(word)))
            return 1.0;
        else
            return 0.0;
    }
}
