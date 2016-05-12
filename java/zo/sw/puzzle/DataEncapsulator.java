package zo.sw.puzzle;

/**
 * Created by swaathi on 01/05/2016.
 */
public class DataEncapsulator {
    private String CLUE;
    private String ANSWER;
    private int LENGTH;

    public DataEncapsulator() {
        CLUE="";
        ANSWER="";
        LENGTH=0;
    }
    public DataEncapsulator(String clue,String answer,int length)
    {
        CLUE=clue;
        ANSWER=answer;
        LENGTH=length;
    }
    public String getCLUE()
    {
        return CLUE;
    }
    public String getANSWER()
    {
        return ANSWER;
    }
    public int getLENGTH()
    {
        return LENGTH;
    }
    public void setCLUE(String clue)
    {
        CLUE=clue;
    }
    public void setANSWER(String answer)
    {
        ANSWER=answer;
    }
    public void setLENGTH(int length)
    {
        LENGTH=length;
    }
}
