package genius.baselib.inter;

/**
 * Created by Hongsec on 2016-08-04.
 */
public class ClickFilter  {

    private long lasttime = 0;

    public boolean isClicked(){

        long l = System.currentTimeMillis();
        if(l - lasttime > 500l){
            lasttime = l;
            return  false;
        }

        return  true;
    }

}
