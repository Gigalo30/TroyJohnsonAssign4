package troy.johnson.s991530754;

public class NatureItem {

    private String mNatureItem;
    private  int mFlagImage;

    public NatureItem(String natureItem, int flagImage) {
        mNatureItem = natureItem;
        mFlagImage = flagImage;

    }

    public String getNatureItem(){
        return mNatureItem;
    }
    public int getFlagImage(){
        return mFlagImage;
    }

}
