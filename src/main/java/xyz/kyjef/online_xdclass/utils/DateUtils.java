package xyz.kyjef.online_xdclass.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {

    private static final String DEFAULT_FORMAT = "yyyyMMdd";

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     *
     * @param date 比较的日期
     * @return boolean 如果在返回true，否则返回false。
     */
    public static Date plusDays(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("日期不能为null");
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        cal2.add(Calendar.DAY_OF_YEAR, days);
        return cal2.getTime();
    }

    public static Date defaultDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	String dateStr=dataFormatToString(new Date(),"yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDate(Date date, String dataFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        try {
            String dateStr=dataFormatToString(date,dataFormat);
            Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date parseDate(String dateStr, SimpleDateFormat dataFormat) {
        try {
            Date d = dataFormat.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr) {
        dateStr = dateStr.replaceAll("(-|/|\\.)","");
        return parseDate(dateStr, DEFAULT_FORMAT);
    }

    /**
     * 将日期按照一定的格式进行格式化为字符串。<br/>
     * 例如想将时间格式化为2012-03-05 12:56 ,则只需要传入formate为yyyy-MM-dd HH:mm即可。
     *
     * @param formate 格式化格式，如：yyyy-MM-dd HH:mm
     * @return String 格式后的日期字符串。如果当前对象为null，则直接返回null。
     */
    public String toString(String formate) {
        DateFormat df = new SimpleDateFormat(formate);
        return (null == this) ? null : df.format(this);
    }
    
    /**
     * 将日期按照一定的格式进行格式化为字符串。<br/>
     * 例如想将时间格式化为2012-03-05 12:56 ,则只需要传入formate为yyyy-MM-dd HH:mm即可。
     *
     * @param formate 格式化格式，如：yyyy-MM-dd HH:mm
     * @return String 格式后的日期字符串。如果当前对象为null，则直接返回null。
     */
    public static String dataFormatToString(Date date, String formate) {
        DateFormat df = new SimpleDateFormat(formate);
        return (null == date) ? "" : df.format(date);
    }
    
 	/**
 	 * 获取下一月的日期
 	 * @return
 	 */
    public static Date nextMonth(Date date) {
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(date);
     cal.add(GregorianCalendar.MONTH, 1);// 在月份上加1
     return cal.getTime();
    }
    
    /**
 	 * 获取上一月的日期
 	 * @return
 	 */
    public static Date lastMonth(Date date) {
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(date);
     cal.add(GregorianCalendar.MONTH, -1);// 在月份上减1
     return cal.getTime();
    }
    
    /**
     *  获取下一年的日期
     * @return
     */
    public static Date nextYear(Date date) {
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(date);
     cal.add(GregorianCalendar.YEAR, 1);// 在年上加1
     return cal.getTime();
    }
    
    /**
 	 * 获取下一天的日期
 	 * @return
 	 */
    public static Date nextDay(Date date) {
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(date);
     cal.add(GregorianCalendar.DAY_OF_MONTH, 1);// 在天上加1
     return cal.getTime();
    }
    /**
     * 获取下一天的日期
     * @return
     */
    public static Date nowDay(Date date,int i) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DAY_OF_MONTH, i);// 在天上加1
        return cal.getTime();
    }


    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        str = str.replaceAll("(-|/|\\.)","");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    public static long getDaySub(Date beginDate, Date endDate) {
        long day = 0;
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 获前几个时间时间
     * @return
     */
    public static Date BeforeTime(int i) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - i);
        //cal.add(GregorianCalendar.DAY_OF_MONTH, i);// 在天上加1
        return cal.getTime();
    }

    //获取学期 1上学期2下学期
    public static int getsubTerm(){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 6){
            return 2;
        }
        return 1;
    }

    public static String getGradeStr(String seasonId){
        try {
            if(StringUtils.isBlank(seasonId)) return "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int seasonYear = Integer.parseInt(seasonId.substring(0,4));
            int year = calendar.get(Calendar.YEAR);
            int sub = year - seasonYear;
            int month = calendar.get(Calendar.MONTH);
            if( (seasonId.contains("春") && month >= 2)
                    || (seasonId.contains("秋") && month >= 8)) {
                sub++;
            }
            if(sub>5){
                return "五年级";
            }else if(sub<=0){
                return "一年级";
            }else{
                return toChinese(sub+"")+"年级";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }

    private static String toChinese(String str) {
        String[] s2 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        StringBuffer sb=new StringBuffer();
        for (char c : str.toCharArray()) {
            sb.append(s2[Integer.parseInt(String.valueOf(c))]);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
    	/*Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
		 System.out.println("当前的时间：" + df.format(new Date()));*/
    	System.out.println(getGradeStr("2011年秋"));
    }

}
