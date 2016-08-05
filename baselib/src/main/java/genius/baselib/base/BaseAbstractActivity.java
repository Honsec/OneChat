package genius.baselib.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import genius.utils.UtilsActivity;

/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract class BaseAbstractActivity extends AppCompatActivity {


    protected  String TAG = getClass().getSimpleName();

    /**
     * 앱티비티가 로드 완료되였는지 표기하는 필더 onWindowFocusChanged 참고
     */
    private boolean loaded = false;

    /**
     * ContentView ResourceID
     *
     * @return
     */
    protected abstract int setContentLayoutResID();

    /**
     * 액티비티 뷰가 완전히 로드되였을때 호출됨 <br>
     * (액티비티가 완전히 로드되였는지는 onWindowsFocused가 호출될때 완료되였다고 판단함 )
     */
    protected abstract void viewLoadFinished();



    /**
     * 뷰초기화  in onCreate
     */
    protected abstract void initViews();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayoutResID());
        initViews();
        //activity관리를 위해 register 하여 저장
        UtilsActivity.getInstance().registerActivity(this, getClass().getSimpleName());

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            if (!loaded) {
                //로드완료
                loaded = true;

                viewLoadFinished();
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaded = false;
        //activity 관리자에서 제거
        UtilsActivity.getInstance().unregisterActivity(this, getClass().getSimpleName());

    }



    /**
     * findViewById를 다시 만듬
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewBId(@IdRes int id) {
        return (T) super.findViewById(id);
    }


}
