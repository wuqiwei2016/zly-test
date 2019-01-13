package com.zpkj.exam.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtil {
	 private static SimpleDateFormat datetime = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss");
	public static String getFormatDate(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(time);
		return strDate;
	}
	
	public static String getFormatDate(Date time,SimpleDateFormat sdf){
		String strDate = sdf.format(time);
		return strDate;
	}
	public static String getFormatDate(Date time,String fromat){
		SimpleDateFormat sdf = new SimpleDateFormat(fromat);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String str= sdf1.format(time);
		String strDate = sdf.format(time);
		return strDate;
	}
	public static String getFormatDate(long time){
		Date cup = new Date();
		cup.setTime(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(cup);
		return strDate;
	}
	public static String getFormatDates(long time){
		Date cup = new Date();
		cup.setTime(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(cup);
		return strDate;
	}
	public static String getFormatDate(long time,SimpleDateFormat sdf){
		Date cup = new Date();
		cup.setTime(time);
		String strDate = sdf.format(cup);
		return strDate;
	}
	public static Long getMinuteBeginNum(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis();
	}
	
	public static Date getMinuteBegin(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	
	public static String getCustomFormatDate(long time, String formatStyle){
		Date cup = new Date();
		cup.setTime(time);
		SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
		String strDate = sdf.format(cup);
		return strDate;
	}
    
	public static Long getDateBeginNum(String dateStr){
		String str = dateStr + " 00:00:00,000";	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
	public static Long getDateBeginNums(String dateStr){
		String str = dateStr;	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
   public static synchronized Date strToDate( String strDate )
    {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if (strDate.length() > 10) {
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			Date d = ft.parse(strDate);
			return new java.sql.Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date(Calendar.getInstance().getTime().getTime());
		}
    }
	
	public static Long getDateEndNum(String dateStr){
		String str = dateStr + " 23:59:59,999";	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
	
	public static Long getTodayBeginNum(){
		String today = DateUtil.getCustomFormatDate(DateUtil.getDateNum(), "yyyy-MM-dd");
		String str = today + " 00:00:00,000";	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();		
	}
	public static Long getTodayBeginNum(Date date){
		String today = DateUtil.getCustomFormatDate(date.getTime(), "yyyy-MM-dd");
		String str = today + " 00:00:00,000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
	
	public static Long getTodayEndNum(){
		String today = DateUtil.getCustomFormatDate(DateUtil.getDateNum(), "yyyy-MM-dd");
		String str = today + " 23:59:59,999";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
	public static Long getTodayEndNum(Date date){
		String today = DateUtil.getCustomFormatDate(date.getTime(), "yyyy-MM-dd");
		String str = today + " 23:59:59,999";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date begin = null;
		try {
			begin = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin.getTime();
	}
	
	public static Long getDateNum(){
		return new Date().getTime();
	}

    public static Long getTimeBySomeDateFormat(String dateString, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date temp = null;
		try {
			temp = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        long result = temp.getTime();
        return result;
    }
	
	public static String getRollDateStrByStr(String dateString,String dateFormat,int rollDay) {
	
		if(dateFormat==null||dateFormat.equals("")){
			dateFormat="yyyy-MM-dd";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		
		Calendar cd = Calendar.getInstance();
		try {
			Date date = sdf.parse(dateString);
			String todaydate = sdf.format(date);
			cd.setTime(sdf.parse(todaydate));
			cd.add(Calendar.DATE, rollDay);
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
	public static String getRollDateStrByLong(Long dateTime,String dateFormat,int rollDay) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		
		Date date = new Date();
		date.setTime(dateTime);
		Calendar cd = Calendar.getInstance();
		try {
			String todaydate = sdf.format(date);
			cd.setTime(sdf.parse(todaydate));
			cd.add(Calendar.DATE, rollDay);
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	   * 得到几天前的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static Date getDateBefore(Date d,int day){
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
	   return now.getTime();
	  }
	  
	  /**
	   * 得到几天后的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static Date getDateAfter(Date d,int day){
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
	  }
	  
	  /**
	   * 得到几小时后时间
	   * @param d
	   * @param hour
	   * @return
	   */
	  public static Date getHourAfter(Date d,int hour){
		  Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.HOUR, now.get(Calendar.HOUR)+hour);
		   return now.getTime();
	  }
	  
	  /**
	   * 得到几分钟后时间
	   * @param d
	   * @param
	   * @return
	   */
	  public static Date getMinuteAfter(Date d,int minute){
		  Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.MINUTE, now.get(Calendar.MINUTE)+minute);
		   return now.getTime();
	  }
	  /**
	   * 得到几小时前时间
	   * @param d
	   * @param hour
	   * @return
	   */
	  public static Date getHourBefore(Date d,int hour){
		  Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.HOUR, now.get(Calendar.HOUR)-hour);
		   return now.getTime();
	  }
	  
	  /**
	   * 得到几分钟前时间
	   * @param d
	   * @param
	   * @return
	   */
	  public static Date getMinuteBefore(Date d,int minute){
		  Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.MINUTE, now.get(Calendar.MINUTE)-minute);
		   return now.getTime();
	  }

	  /**
	   * 得到几天前的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static String getDateTimeBefore(Date d,int day, String format){
		   Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
		   return getCustomFormatDate(now.getTime().getTime(), format);
	  }
	  
	  private static synchronized String formatDateTime(Date targetDate,DateFormat formatter){
	    	if(targetDate==null) return "";
			return formatter.format(targetDate);
	    }
	  
	  public static synchronized String formatDateTime(Date targetDate,String formatStr){
	    	DateFormat dateFormater = new SimpleDateFormat(formatStr, Locale.SIMPLIFIED_CHINESE );
			return formatDateTime(targetDate, dateFormater);
	    }
	  
	  /**
	   * 得到几天前的8点
	   * @param d
	   * @param day
	   * @return
	   */
	  public static String getWarnDateTimeBefore(Date d, int day, String format){
		   Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
		   now.set(Calendar.HOUR, 8);
		   now.set(Calendar.MINUTE, 0);
		   now.set(Calendar.SECOND, 0);
		   
		   return getCustomFormatDate(now.getTime().getTime(), format);
	  }
	  /**
	   * 得到几小时前的时间
	   * @param d
	   * @param
	   * @return
	   */
	  public static String getWarnHoursTimeBefore(Date d, int hour, String format){
		   Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.HOUR,now.get(Calendar.HOUR)-hour);
		   
		   return getCustomFormatDate(now.getTime().getTime(), format);
	  }
	  public static Date getDateByStr(String dateStr)
	  {
		  if(dateStr==null || dateStr.equals("null"))
			  return null;
		  if(dateStr.length()==0)
			  return null;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date d = null;
		 try{
			 d = sdf.parse(dateStr);
		 }catch (Exception e) {
			e.printStackTrace();
			System.out.println("时间格式不正确!时间格式必须为:  yyyy-MM-dd HH:mm:ss");
		}
		 return d;
	  }
	  public static Date getDateByStr(String dateStr,String fromate)
	  {
		  if(dateStr==null || dateStr.equals("null"))
			  return null;
		  if(dateStr.length()==0)
			  return null;
		 Date d = null;
		 try{
			 SimpleDateFormat sdf = new SimpleDateFormat(fromate);
			 d = sdf.parse(dateStr);
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 return d;
	  }
	  public static Date getDateByStr(String dateStr,SimpleDateFormat sdf)
	  {
		  if(dateStr==null || dateStr.equals("null"))
			  return null;
		  if(dateStr.length()==0)
			  return null;
		 Date d = null;
		 try{
			 d = sdf.parse(dateStr);
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 return d;
	  }
	  public static Date getDateByStrN(String dateStr)
	  {
		  if(dateStr==null)
			  return new Date();
		  if(dateStr.length()==0)
			  return new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date d = null;
		 try{
			 d = sdf.parse(dateStr);
		 }catch (Exception e) {
			e.printStackTrace();
			System.out.println("时间格式不正确!时间格式必须为:  yyyy-MM-dd HH:mm:ss");
		}
		 return d;
	  }
	  public static Date getDateByString(String time) throws ParseException{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			Date date=sdf.parse(time); 
			return date;
	  }
	public static Date getDateByStrings(String time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");//小写的mm表示的是分钟
		Date d =  null;
		try{
			d = sdf.parse(time);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("时间格式不正确!时间格式必须为:  yyyy/MM/dd");
		}
		return d;
	}

	public static String getDateByStringsInfo(String time){
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");//小写的mm表示的是分钟
		Date d =  null;
		try{
			d = sdf.parse(time);
		}catch (Exception e) {
			buffer.append("时间格式不正确!时间格式必须为:  yyyy/MM/dd");
		}
		return buffer.toString();
	}
    /**
     * 时间差
     * @param startDate
     * @param endDate
     * @return 
     */
    public static String getDateDifference (Date startDate , Date endDate) {
    	if (startDate == null || endDate == null) {
    		return "";
    	}
    	long between=(endDate.getTime() - startDate.getTime())/1000; //除以1000是为了转换成秒
    	if(between==0) between = 1;
    	long day1 = between/(24*3600);
    	long hour1 = between%(24*3600)/3600;
    	long minute1 = between%3600/60;
    	long second1 = between%60;
    	StringBuffer dataString = new StringBuffer("");
    	if (day1 > 0) {
    		dataString.append(day1 + "天");
    	}
    	if (hour1 > 0) {
    		dataString.append(hour1 + "小时");
    	}
    	if (minute1 > 0) {
    		dataString.append(minute1 + "分");
    	}
    	if (second1 > 0) {
    		dataString.append(second1 + "秒");
    	}
    	return dataString.toString();
    }
    
    //两个时间相差的小时
    public static long getDateDifferenceHour (Date startDate , Date endDate) {
    	if (startDate == null || endDate == null) {
    		return 0;
    	}
    	long between=(endDate.getTime() - startDate.getTime())/1000; //除以1000是为了转换成秒
    	if(between==0) between = 1;
    	
    	long hour1 = between%(24*3600)/3600;
    	
    	return hour1;
    }
    
    //两个时间相差的分钟
    public static long getDateDifferenceMinute (Date startDate , Date endDate) {
    	if (startDate == null || endDate == null) {
    		return 0;
    	}
    	long between=(endDate.getTime() - startDate.getTime())/1000; //除以1000是为了转换成秒
    	if(between==0) between = 1;
    	
    	long minutes = between%60;
    	
    	return minutes;
    }
    
    /**
     * 查询当前时间 年月日 八点开始
     * @author:wuqiwei
     * @param @return
     * @date:2016年9月2日上午11:00:11
     */
    public static Date getStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 8);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        todayStart.get(Calendar.DAY_OF_WEEK);
        return todayStart.getTime();  
    }  
    /**
     * 查询当前时间 年月日  18点结束
     * @author:wuqiwei
     * @param @return
     * @date:2016年9月2日上午11:00:11
     */
    public static Date getEndTime() {  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR_OF_DAY, 18);  
        todayEnd.set(Calendar.MINUTE, 0);  
        todayEnd.set(Calendar.SECOND, 0);  
        todayEnd.set(Calendar.MILLISECOND, 0);  
        return todayEnd.getTime();  
    }
    /**
     * 返回当前时间是周几
     * @author:wuqiwei
     * @param @return
     * @date:2016年9月2日下午1:13:47
     */
    public static String weekDay(Date date){
  	  String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	        Calendar cal = Calendar.getInstance();
	        if(date == null){
	        	cal.setTimeInMillis(System.currentTimeMillis());
	        }else{
	        	cal.setTime(new Date(date.getTime()));  
	        }
	        int day = cal.get(Calendar.DAY_OF_WEEK)-1;
			return weekDays[day];
    }
    
    /**
     * 日期格式转换yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  TO  yyyy-MM-dd HH:mm:ss
     */
    public static String dealDateFormat(String oldDateStr) throws ParseException{
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  
	//这个后面的.SSSXXX写了的话这一行就直接抛异常了，所以我去掉了，还有前面的T  一定要用英文的单引号包裹起来
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date  date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(date1);
    }
    /**
     * 获取当前月第一天
     */
    public static String getMonthFirst() {
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
         //获取前月的第一天
         Calendar   cal_1=Calendar.getInstance();//获取当前日期 
         cal_1.add(Calendar.MONTH, 0);
         cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
         String firstDay = format.format(cal_1.getTime());
		return firstDay;
	}
    /**
     * 字符串转换Date
     */
    public static Date pStringToDate(String str) throws ParseException {
        return datetime.parse(str);
    }

	/**
	 * 将long类型转化为Date
	 */
	public static Date LongToDare(long str) throws ParseException{
		return new Date(str);
	}


	/**
	 * 获取现在时间
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(currentTime);
		Date time = getDateByStr(dateString,"yyyy-MM-dd HH:mm");
		return time;
	}
}
