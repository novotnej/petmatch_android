package uk.ac.bath.petmatch.Utils;

import android.content.Context;

public class ToastAdapter {
    /**
     * customizable toast
     * @param message
     */
    public static void toastMessage(Context context, String message){
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }

}



