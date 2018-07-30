package experiment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mhjang on 2/8/16.
 *
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
public class GenerateParams {

    public Param getMeBaselineParam() {
        Param p = new Param();
        p.querygen = "tf10";
        p.k = 20;
        p.networkSmoothing = "clique";
        p.aggregation = "max";
        p.voting = "M";
        p.threshold_M = 40000;
        p.threshold_C = 0.15;
        p.revised = false;
        p.id = 1;
        return p;

    }
    public LinkedList<Param> generateParamSweep(String dataset) {
        LinkedList<Param> paramList = new LinkedList<Param>();

        int id = 1;
        String[] querygen = {"all", "tf10", "tile"};
        int[] neighborK = {1, 5, 10, 15};
        boolean revised = false;
        String[] networkConstruction = {"clique", "pair"};
        String[] aggregation = {"max", "avg"};
        String[] voting = {"C", "M", "Majority"};
        String[][] thresholds = {{"20000", "0.00418"}, {"40000", "0.17"}, {"84930", "0.00418"}, {"2850000", "0.17"}};

        for(int k : neighborK) {
            for (String a : aggregation) {
                for (String v : voting) {
                    for (String q : querygen) {
                        for (String[] t : thresholds) {
                            for (String network : networkConstruction) {
                                revised = false;
                                Param p = new Param();
                                p.id = id;
                                p.dataset = dataset;
                                p.k = k;
                                p.querygen = q;
                                p.voting = v;
                                p.aggregation = a;
                                p.revised = revised;
                                p.threshold_M = Double.parseDouble(t[0]);
                                p.threshold_C = Double.parseDouble(t[1]);
                                p.networkSmoothing = network;
                                id++;

                                Param p2 = new Param(p);
                                p2.revised = true;
                                p2.id = id;
                                paramList.add(p);
                                paramList.add(p2);
                                id++;
                            }
                        }
                    }
                }
            }
        }
        return paramList;
    }


}

