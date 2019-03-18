import com.dateutil.demo.DateUtils;

import java.util.Date;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 10:01
 * @version: V1.0
 * @detail:
 **/
public class DateTest {
    public static void main(String[] args) {
        System.out.println(DateUtils.getNow());
        System.out.println(DateUtils.dateStr(DateUtils.getNow(),"yyyy-HH-dd"));
        System.out.println(DateUtils.dateStr(DateUtils.getNow()));
        System.out.println(DateUtils.getTime(DateUtils.getNow()));
        System.out.println(DateUtils.getDate(String.valueOf(DateUtils.getTime(DateUtils.getNow()))));
        System.out.println(DateUtils.getDayOfMonth(DateUtils.getNow()));
        System.out.println(DateUtils.getDayOfWeek(DateUtils.getNow()));
        System.out.println(DateUtils.getDayOfYear(DateUtils.getNow()));
        System.out.println(DateUtils.valueOf("2019-03-15"));
        System.out.println(DateUtils.getWeekOfDate((DateUtils.getNow())));
        System.out.println(DateUtils.rollMinute(DateUtils.getNow(),1000));
        System.out.println(DateUtils.rollDay(DateUtils.valueOf("2019-03-31"),1));
        System.out.println(DateUtils.rollYear(DateUtils.valueOf("2019-03-31"),1));
        System.out.println(DateUtils.rollDate(DateUtils.valueOf("2019-03-31"),1,1,1));
        System.out.println(DateUtils.getNowTimeStr());
        System.out.println(DateUtils.getNowTime("yyyy-MM-dd hh:mm:ss"));
        System.out.println(DateUtils.getTimeStr(DateUtils.getNow()));
        System.out.println(DateUtils.getTimeStr("2019/03/11 12:02:50","yyyy/mm/dd hh:mm:ss"));
        System.out.println(DateUtils.getIntegralTime()+"--");
        System.out.println(DateUtils.getTime("2019-03-23 12:15:45"));
    }
}
