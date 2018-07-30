package experiment;

import classify.ControversyClassifier;
import classify.DataPath;

import java.io.IOException;
import java.util.LinkedList;

public class ExperimentRun {
    public static void main(String[] args) throws IOException {
        GenerateParams genParam = new GenerateParams();
        ControversyClassifier cc = new ControversyClassifier();
        cc.run(genParam.getMeBaselineParam(), DataPath.CLUEWEB1 + "train");
        /*
        LinkedList<Param> paramList = genParam.generateParamSweep("clueweb");
        ControversyClassifier cc = new ControversyClassifier();


        for(Param p : paramList.subList(0, 3)) {
            // p.print();
            cc.run(p, DataPath.CLUEWEB1);
        }
        */
    }


}
