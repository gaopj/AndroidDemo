package com.gpj.databindingdemo;

import android.databinding.BindingConversion;


import com.gpj.databindingdemo.model.Swordsman;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
    public static String getName(Swordsman swordsman) {
        return swordsman.getName();
    }

    @BindingConversion
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
