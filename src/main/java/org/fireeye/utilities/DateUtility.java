package org.fireeye.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public String getTodayDate(){

        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        
        String result = ""+year+(month+1) + day;
        return result;
    }
}
