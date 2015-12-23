package com.fjby.travel.leyou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	/**
	 * 显示时间格式为 hh:mm
	 * 
	 * @param when
	 * @return String
	 */
	public static String formatDateToString(Date when) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		String temp = sdf.format(when);
		return temp;
	}

	/**
	 * 显示时间格式为 hh:mm
	 * 
	 * @return String
	 */
	public static Date formatStringToDate(String string) {
		String formatStr = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date;
		try {
			date = sdf.parse( string);
			LogUtil.v("formatDateToString   " + date);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


    /**
     * 获取时间字符串，格式为：yyyy.mm.dd HH:mm
     *
     * @param date Date类的实例
     *
     * @return dataStr String类的实例
     */
    public static String  getStringFromCurrentTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 获取格式：yyyy年mm月dd日 HH:mm.
     *
     * @param date
     *            the date
     * @return the date cn
     */
    public static String getDateCN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateStr = sdf.format(date);
        return dateStr;
    }
	/**
	 * 是否同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(long date1, long date2) {
		long days1 = date1 / (1000 * 60 * 60 * 24);
		long days2 = date2 / (1000 * 60 * 60 * 24);
		return days1 == days2;
	}
	
	/**
	 * 获取增加多少月的时间
	 * 
	 * @return addMonth - 增加多少月
	 */
	public static Date getAddMonthDate(int addMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, addMonth);
		return calendar.getTime();
	}


	public static String formatDateTime(Date time) {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		return mDateFormat.format(time);
	}
	/**
	 * 获取增加多少天的时间
	 * 
	 * @return addDay - 增加多少天
	 */
	public static Date getAddDayDate(int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, addDay);
		return calendar.getTime();
	}
	/**
	 * 获取增加多少小时的时间
	 * 
	 * @return addDay - 增加多少消失
	 */
	public static Date getAddHourDate(int addHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, addHour);
		return calendar.getTime();
	}





	/**
	 * 获取当前时间的小时数
	 *
	 * @return int 小时
	 */
	public static int getCurrentHour(){
		long time=System.currentTimeMillis();
		final Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		return mCalendar.get(Calendar.HOUR_OF_DAY);

	}

	/**
	 * 获取当前时间的分钟
	 *
	 * @return int 分
	 */
	public static int getCurrentMin(){
		long time=System.currentTimeMillis();
		final Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		return mCalendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间的秒
	 *
	 * @return int 秒
	 */
	public static int getCurrentSec(){
		long time=System.currentTimeMillis();
		final Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		return mCalendar.get(Calendar.SECOND);
	}

	/**
	 * 分钟转换小时（整数值）
	 *
	 * @param minuter
	 * @return int 小时
	 */
	public static int minCastToHour(int minuter){
		return minuter/60;
	}

	/**
	 * 分钟转换小时（求余值）
	 *
	 * @param minuter
	 * @return int 求余值
	 */
	public static int minCastToHourMore(int minuter){
		return minuter%60;
	}

	/**
	 * 小时转换分钟
	 *
	 * @param hour
	 * @return int 分钟
	 */
	public static int hourCastToMin(int hour){
		return hour*60;
	}
	
}
