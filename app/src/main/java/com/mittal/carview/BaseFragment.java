package com.mittal.carview;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;


/**
 * Created by Mittal on 30/11/15.
 */
public class BaseFragment extends Fragment {
    public void replaceFragmentWithoutBack(int containerViewId, Fragment fragment, String fragmentTag) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
    }

    public void replaceFragmentWithBack(int containerViewId,
                                        Fragment fragment,
                                        String fragmentTag,
                                        String backStackStateName) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void addFragmentWithoutRemove(int containerViewId, Fragment fragment, String fragmentName) {
        String tag = (String) fragment.getTag();
        getActivity().getSupportFragmentManager().beginTransaction()
                // remove fragment from fragment manager
                //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
                // add fragment in fragment manager
                .add(containerViewId, fragment, fragmentName)
                // add to back stack
                .addToBackStack(tag)
                .commit();
    }

  /*  Dialog dialogOK = null, dialogValidation = null;

    public void dialogOK(Context context, String message) {
        if (dialogOK != null && dialogOK.isShowing())
            dialogOK.dismiss();
        dialogOK = new DialogOK(context, message);
        dialogOK.show();
    }

    public void dialogValidation(Context context, String title, String subTitle, ArrayList<String> messages) {
        if (dialogValidation != null && dialogValidation.isShowing())
            dialogValidation.dismiss();
        dialogValidation = new DialogValidation(context, title, subTitle, messages);
        dialogValidation.show();
    }
*/
    /**
     * Method for get screen height and width
     */
    private int width, height;
    private int[] getDeviceHeightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        int[] i = new int[2];
        i[0] = height;
        i[1] = width;
        return i;
    }
}
