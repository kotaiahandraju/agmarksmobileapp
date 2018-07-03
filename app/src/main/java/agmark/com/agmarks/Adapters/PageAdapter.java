package agmark.com.agmarks.Adapters;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import agmark.com.agmarks.Fragements.Fragmen3;
import agmark.com.agmarks.Fragements.Fragment1;
import agmark.com.agmarks.Fragements.Fragment2;
import agmark.com.agmarks.Fragements.Fragment4;
import agmark.com.agmarks.Fragements.Fragment5;
import agmark.com.agmarks.Fragements.Fragment6;

public class PageAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment1 tab1 = new Fragment1();
                return tab1;
            case 1:
                Fragment2 tab2 = new Fragment2();
                return tab2;
            case 2:
                Fragmen3 tab3 = new Fragmen3();
                return tab3;
            case 3:
                Fragment4 tab4 = new Fragment4();
                return tab4;
            case 4:
                Fragment5 tab5 = new Fragment5();
                return tab5;
            case 5:
                Fragment6 tab6 = new Fragment6();
                return tab6;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
