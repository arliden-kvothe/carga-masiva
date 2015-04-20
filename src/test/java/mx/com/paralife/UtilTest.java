/**
 * 
 */
package mx.com.paralife;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author martinni
 *
 */
public class UtilTest {
	
	@Test
	public void weekOfMonth() {
		Calendar cal = Calendar.getInstance();
        /*cal.set(Calendar.YEAR, 2013);*/
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("start date" + cal.getTime());
        int start = cal.get(Calendar.WEEK_OF_YEAR);
        System.out.println("Start is " + start);
        /*cal.set(Calendar.YEAR, 2013);*/
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        System.out.println("end date" + cal.getTime());
        int end = cal.get(Calendar.WEEK_OF_YEAR);
        System.out.println("Value is " + end);
        System.out.println("Num weeks:: " + (end - start +1 ));
	}

}
