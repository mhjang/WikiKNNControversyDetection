package classify;

/**
 * Created by mhjang on 12/21/15.
 */
public class VotingParameter {
    public static int ISOLATION_M = 1;
    public static int ISOLATION_C = 2;
    public static int ISOLATION_D = 3;
    public static int MAJORITY = 4;
    public static int OR = 5;
    public static int D_CM = 6;
    public static int ISOLATION_R= 7;

    public double MScoreThreshold;
    public double CScoreThreshold;
    public int k;
    public int votingMethod;

    public static class Builder {
        private double MScoreThreshold = 2850000;
        private double CScoreThreshold = 0.0418;
        private int k = 8;
        private int votingMethod = ISOLATION_C;
        public Builder setMThreshold(double threshold) {
            this.MScoreThreshold = threshold;
            return this;
        }

        public Builder setCScoreThreshold(double threshold) {
            this.CScoreThreshold = threshold;
            return this;
        }

        public Builder setKNeighbors(int k_) {
            this.k = k_;
            return this;
        }

        public Builder setVotingMethod(String voting) {
            int votingOption = 0;
            if (voting.equals("C"))
                votingOption = VotingParameter.ISOLATION_C;
            else if (voting.equals("M"))
                votingOption = VotingParameter.ISOLATION_M;
            else if (voting.equals("D"))
                votingOption = VotingParameter.ISOLATION_D;
            else if (voting.equals("R"))
                votingOption = VotingParameter.ISOLATION_R;
            else if (voting.equals("Majority"))
                votingOption = VotingParameter.MAJORITY;
            else if (voting.equals("D_CM"))
                votingOption = VotingParameter.D_CM;
            
            
            this.votingMethod = votingOption;
            return this;
        }
        public VotingParameter build() {
            return new VotingParameter(this);
        }
    }


    public VotingParameter(Builder builder) {
        this.votingMethod = builder.votingMethod;
        this.MScoreThreshold = builder.MScoreThreshold;
        this.CScoreThreshold = builder.CScoreThreshold;
        this.k = builder.k;

    }

    public void printParameter() {
        System.out.println("MScoreThreshold: " + this.MScoreThreshold + "\t" +
        "CScoreThreshold: " + this.CScoreThreshold + "\t K: " + this.k +
                "\t aggregationMethod: " + this.votingMethod);
    }

}
