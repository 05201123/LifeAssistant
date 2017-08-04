package lifeassistant.zk.com.mylifeassistant.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * 数据库工具类
 * @author 099
 *
 */
public class DbUtils {
	private static final String DB_CREATE = "CREATE TABLE IF NOT EXISTS ";// 创建语句
	public static final String 	DB_DROP = "DROP TABLE IF EXISTS ";//删除表的语句
	/***
	 * 创建表的语句
	 * @param
	 * @param ColumnNames
	 * @param
	 */
	public static String startCreateTable(String tableName, String[] ColumnNames) {
		StringBuffer sb = new StringBuffer();
		sb.append(DB_CREATE); // 创建
		sb.append(tableName);// 表
		sb.append("(");// 开始
		sb.append(" _id INTEGER PRIMARY KEY AUTOINCREMENT , ");
		for(String columnName:ColumnNames){
			sb.append(columnName).append(" TEXT,");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
	/***
	 * 获取表列名
	 * @param db
	 * @param tableName
	 * @return
	 */
	public static String[] getTableColumnNames(SQLiteDatabase db,String tableName) {
		Cursor cursor = null;
		try {// 查询一行
			cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
			String [] columNames=cursor.getColumnNames();
			
			return columNames;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
		}
		
		return null;
	}
	/***
	 * 比较新旧列表返回更新的字段
	 * @param
	 * @param
	 * @return
	 */
	public static List<String> compareColunNames(String[] oldCoumnNames,
			String[] newCoumnNames) {
		if(oldCoumnNames!=null&&newCoumnNames!=null){
			List<String> updateList=new ArrayList<String>();
			List<String> oldComnNamesList=Arrays.asList(oldCoumnNames);
			for(String newComnNames:newCoumnNames){
				if(!oldComnNamesList.contains(newComnNames)){
					updateList.add(newComnNames);
				}
			}
			return updateList;
		}
		return null;
	}
	/***
	 * 更新数据库表
	 */
	public static void updateTableAddColunNames(SQLiteDatabase db,String tableName,List<String> updateColunNames){
		for(String updateColunName:updateColunNames){
			db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + updateColunName);
		}
		
	}
	
}
