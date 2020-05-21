package com.irprogram.ebook;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class myClass {

    public static void textview_face(Context context, String font, TextView...pTxt){

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),font+".ttf");
        for(TextView txt : pTxt){
            txt.setTypeface(typeface);

        }

    }
}
