package classify;
import java.util.HashMap;

/**
 * Created by mhjang on 12/21/15.
 */
public class VotingClassifer {
    VotingParameter param;
    public VotingClassifer(VotingParameter p) {
        this.param = p;
    }

    public void classify(AugmentedDocument doc) {
        boolean mscoreFlag = doc.mScore>param.MScoreThreshold?true:false;
        boolean cscoreFlag = doc.cScore>param.CScoreThreshold?true:false;
        boolean dscoreFlag = doc.dScore>0.5?true:false;

        boolean isControversial = false;
        if(param.votingMethod == VotingParameter.ISOLATION_M) {
            if (mscoreFlag) isControversial = true;
        }
        else if(param.votingMethod == VotingParameter.ISOLATION_C) {
            if (cscoreFlag) isControversial = true;
        }
        else if(param.votingMethod == VotingParameter.ISOLATION_D) {
            if(dscoreFlag) isControversial = true;
        }

        else if(param.votingMethod == VotingParameter.MAJORITY) {
            int count = 0;
            if(mscoreFlag) count++;
            if(cscoreFlag) count++;
            if(dscoreFlag) count++;
            if(count>=2) isControversial = true;
        }

        else if(param.votingMethod == VotingParameter.OR) {
            if(mscoreFlag || cscoreFlag || dscoreFlag) isControversial = true;
        }
        else if(param.votingMethod == VotingParameter.D_CM) {
            if(dscoreFlag || (cscoreFlag && dscoreFlag)) isControversial = true;
        }

        doc.prediction = isControversial;
    }
    @Deprecated
    public void classify(HashMap<String, String> info) {
        boolean mscore = Double.parseDouble(info.get("MScore"))>param.MScoreThreshold?true:false;
        boolean cscore = Double.parseDouble(info.get("CScore"))>param.CScoreThreshold?true:false;
        boolean dscore = Double.parseDouble(info.get("DScore"))>0.5?true:false;
        boolean rscore = true; // not using binary classificiation by R now


        Integer isControversial = 0;
        if(param.votingMethod == VotingParameter.ISOLATION_M) {
            if (mscore) isControversial = 1;
        }
        else if(param.votingMethod == VotingParameter.ISOLATION_C) {
            if (cscore) isControversial = 1;
        }
        else if(param.votingMethod == VotingParameter.ISOLATION_D) {
            if(dscore) isControversial = 1;
        }

        else if(param.votingMethod == VotingParameter.ISOLATION_R) {
            if(rscore) isControversial = 1;
        }
        else if(param.votingMethod == VotingParameter.MAJORITY) {
            int count = 0;
            if(mscore) count++;
            if(cscore) count++;
            if(dscore) count++;
            if(count>=2) isControversial = 1;
        }

        else if(param.votingMethod == VotingParameter.OR) {
            if(mscore || cscore || dscore) isControversial = 1;
        }
        else if(param.votingMethod == VotingParameter.D_CM) {
            if(dscore || (cscore && dscore)) isControversial = 1;
        }
        info.put("prediction", isControversial.toString());
    }


}
