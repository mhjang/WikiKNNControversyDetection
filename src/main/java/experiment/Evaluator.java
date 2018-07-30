package experiment;

import classify.AugmentedDocument;
import utils.SimpleFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mhjang on 7/30/2018
 */
public class Evaluator {


    HashMap<String, Double> controveryGoldstandard;

    public Evaluator(String dir) throws IOException {
        controveryGoldstandard = new HashMap<String, Double>();

        SimpleFileReader sr = new SimpleFileReader(dir);
        while(sr.hasMoreLines()) {
            String line  = sr.readLine();
            String[] tokens = line.split("\t");
            controveryGoldstandard.put(tokens[0], Double.parseDouble(tokens[1]));
        }
    }


    public double getLabeledControversyScore(String docName) {
        if(controveryGoldstandard.containsKey(docName)) {
            return controveryGoldstandard.get(docName);
        }
        return -1;
    }

    public void evaluate(ArrayList<AugmentedDocument> queryDocs) {
        int truePositive=0, trueNegative=0, falsePositive=0, falseNegative=0;
        int N = queryDocs.size();
        for(AugmentedDocument doc : queryDocs) {
            if(doc.prediction) {
                if(doc.label) truePositive++;
                else falsePositive++;
            }
            else {
                if(doc.label) falseNegative++;
                else trueNegative++;
            }
        }

        double recall = (double)(truePositive) / (double)(truePositive + falseNegative);
        double precision = (double)(truePositive) / (double)(truePositive + falsePositive);
        double accuracy = (double)(truePositive + trueNegative) / (double)(N);
        double f1 = (precision * recall * 2) / (precision + recall);
        System.out.println("Accuracy: " + accuracy);
        System.out.println("F1: " + f1);
    }



    }

