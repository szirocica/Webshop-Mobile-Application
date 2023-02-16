package com.example.fasszo;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class RandomAsyncTask extends AsyncTask<Void, Void, String> {
    //Ez a megoldás nem jó ha közben megváltozik a konfiguráció (pl elfordítják a telefont)
    // mert elveszti a referenciát
    //Erre megoldás a Loader a randomasync osztályban

    private WeakReference<TextView> mTextView; //azért weak reference h a gc fel tudja szabadítani, ne legyen memory leak

    public RandomAsyncTask(TextView textView) {
        this.mTextView = new WeakReference<>(textView);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int number = random.nextInt(11);
        int ms = number * 300;

        try {
            Thread.sleep(ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Bejelentkezés vendégként " + ms + " ms után";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mTextView.get().setText(s);
    }
}
