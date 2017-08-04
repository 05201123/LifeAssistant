package lifeassistant.zk.com.mylifeassistant.db;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import lifeassistant.zk.com.mylifeassistant.core.CoreApi;
import lifeassistant.zk.com.mylifeassistant.db.table.ConsumeMoneyTable;


/***
 * 数据库管理器
 * @author 099
 *
 */
public class DBManager {
	public final static String DATABASE_NAME = "qgp.db";
	public final static int DATABASE_VERSION = 3;
	/**
	 * 获取所有的table(每新建一个表都需要添加进来一下)
	 * @return
	 */
	public static List<ITable> getAllTable(){
		List<ITable> listTable=new ArrayList<ITable>();
		//TODO
		listTable.add(new ConsumeMoneyTable());

		
		return listTable;
	}
	
	/***
	 *	创建数据库表
	 * @param db
	 */
	public static void create(SQLiteDatabase db) {
		for(ITable table:getAllTable()){
			table.createTable(db);
		}
	}
	/***
	 * 更新数据库表
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		List<ITable> listTable=getAllTable();
		try{
			db.beginTransaction();
			for(ITable table:listTable){
				if(oldVersion<=table.getMinUpdateVerson()){
					if(table.getUpdateMode()==DbContacts.DROP_UPDATE){
						table.initTableForException(db);
					}else if(table.getUpdateMode()==DbContacts.MODIFY_UPDATE){
						String[] coumnUpdateNames=DbUtils.getTableColumnNames(db,table.getTableName());
						if(coumnUpdateNames==null||coumnUpdateNames.length==0){
							table.createTable(db);
						}else{
							List<String> updateCoumnNames=DbUtils.compareColunNames(coumnUpdateNames,table.getColumnInfo());
							if(updateCoumnNames!=null){
								DbUtils.updateTableAddColunNames(db, table.getTableName(), updateCoumnNames);	
							}
						}
						
						
					}
				}
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			initAllTable(listTable,db);
		}finally{
			db.endTransaction();
		}
		
	}
	/**初始化所有表*/
	private static void initAllTable(List<ITable> listTable,SQLiteDatabase db) {
		for(ITable table:listTable){
			table.initTableForException(db);
		}
	}
	/***
	 * Appcation 启动时，校验数据库是否升级(
	 *)
	 */
	public static void initDBSqlite(){
//		 TODO 未测试
		QGPDBHelper.getInstance(CoreApi.getInstance().getApplicationContext());
	};
	
}
