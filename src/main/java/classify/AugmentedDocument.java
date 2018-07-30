package classify;

import java.util.ArrayList;

/*
    Query document augmented with features such as top K relevant Wikipages and their C, D, M score
 */
public class AugmentedDocument {
    public String qid;
    public ArrayList<String> relevantWiki;
    public double mScore;
    public double cScore;
    public double dScore;
    public boolean prediction;
    public boolean label;
    public AugmentedDocument(String qid) {
        this.qid = qid;
    }

    public void setRelevantWiki(ArrayList<String> topKList) {
        this.relevantWiki = topKList;
    }
}
