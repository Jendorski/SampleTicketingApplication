package com.testapplication.toastUI;

import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.testapplication.R;

/**
 * Here, we display short timed messages that disappear.
 *
 * The UI is customised and the color can be changed as needed.
 * */
public class ToastUI {

    private static final String TAG = ToastUI.class.getSimpleName();

    public static void showToast(String toastMessage, AppCompatActivity context) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    context.findViewById(R.id.custom_toast_container));

            TextView text = layout.findViewById(R.id.toast_text);
            text.setText(toastMessage);

            Toast toast = new Toast(context);
            toast.setGravity(Gravity.TOP, 16, 48);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        }catch (Exception e){
            e.printStackTrace();
            //FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    public static void showToast(String toastMessage, AppCompatActivity context, @ColorRes int colorRes) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    context.findViewById(R.id.custom_toast_container));

            ImageView imageView = layout.findViewById(R.id.toast_image);

            //ViewCompat.setBackgroundTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)));

            TextView text = layout.findViewById(R.id.toast_text);
            text.setText(toastMessage);

            ViewCompat.setBackgroundTintList(text, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)));

            Toast toast = new Toast(context);
            toast.setGravity(Gravity.TOP, 16, 48);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        }catch (Exception e){
            e.printStackTrace();
            //FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

}
