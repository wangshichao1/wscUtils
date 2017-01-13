import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.wsc.utils.ConfigParam;
import com.wsc.utils.FileUtil;


public class TestMain {
	public static int data_save_month = 3;
	public static String JF_INF_NAME = "A09019";
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -data_save_month);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxDay);
		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int dateInt = Integer.parseInt(dateFormat.format(date));
		System.out.println(dateInt);
		String fileName = "A0901920161031000000.AVL";
		int fileDateInt = Integer.parseInt(fileName.substring(JF_INF_NAME.length(), JF_INF_NAME.length()+8));
		System.out.println(fileDateInt);
		if(fileDateInt <= dateInt)
		{
			System.out.println("delete");
		}else{
			System.out.println("leave");
		}
	}
}
