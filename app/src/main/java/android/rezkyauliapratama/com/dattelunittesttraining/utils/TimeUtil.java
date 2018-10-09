package android.rezkyauliapratama.com.dattelunittesttraining.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//this logic already separate from activity so we can use unittest on here
public class TimeUtil  {

    private SimpleDateFormat simpleDateFormat;

    //inject class SimpleDateFormat into class TimeUtil
    public TimeUtil(SimpleDateFormat simpleDateFormat) {

        this.simpleDateFormat = simpleDateFormat;
    }

    public String getUserFriendlyDate(Date date){
        //5. check if this method return the correct string value
//        return new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
        return simpleDateFormat.format(date);
    }

    public Date convertStringToDate(String str){

        try {
//            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(str);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
           return null;
        }

    }

}