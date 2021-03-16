package pro.watchnews.watchnewspro.helper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import pro.watchnews.watchnewspro.R;


/**
 * Created by laptop on 23/02/15.
 */
public class ShowMessagesDialogsUtitly {

    private static Dialog alertDialog;
    private static ProgressDialog mProgressDialog;

    public static void showToast(String message, Context mContext) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    }
    
    public static void showProgressDialog(Context mContext) {
        if ((alertDialog != null) && (alertDialog.isShowing())) {
            return;
        }
        alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        
        LayoutInflater li = LayoutInflater.from(mContext);
        View view = li.inflate(R.layout.dialog_progress_layout, null);
        alertDialog.setContentView(view);
        alertDialog.show();

    }

    public static void hideProgressDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }

    }
}
