package experiment;

/*
 *
 *id: # of experiment run
 *dataset: "clueweb" or "genweb"
 *querygen: "all", "tf10", "tile"
 *k: integer, 1 - 20. # of k in KNN neighbors
 *revised: boolean, are we using revised score or not. True: use, False: not use
 *threshold_C: threshold for C Score
 *threshold_M: threshold for M Score
 *aggregation: "max" or "avg"
 *voting: "C", "M", "D", "majority", "AND", "OR", "D_CM"
 */

public class Param {
    public int id;
    public String dataset;
    public String querygen;
    public int k;
    public boolean revised;
    public double threshold_C;
    public double threshold_M;
    public String aggregation;
    public String voting;
    public String networkSmoothing;

    public static String votingMAX = "max";
    public static String votingAvG = "avg";

    public static int CLIQUE_BASED_NETWORK = 1;
    public static int PAIR_BASED_NETWORK = 2;

    public static class QueryMethod {
        public static String ALLQUERY = "all";
        public static String TIlEQUERY = "tile";
        public static String TF10QUERY = "tf10";
        public static String WIKIFIER = "wikifier";
    }

    public Param() {

    }
    public Param(Param _p) {
        this.id = _p.id;
        this.dataset = _p.dataset;
        this.querygen = _p.querygen;
        this.k = _p.k;
        this.revised = _p.revised;
        this.threshold_C = _p.threshold_C;
        this.threshold_M = _p.threshold_M;
        this.aggregation = _p.aggregation;
        this.voting = _p.voting;
        this.networkSmoothing = _p.networkSmoothing;
    }

    public void print() {
        System.out.println(id + "\t" + dataset + "\t" + querygen + "\t" + revised + "\t" + threshold_M + "\t" + threshold_C + "\t" + aggregation + "\t" + voting + "\t" + networkSmoothing);
    }
}
