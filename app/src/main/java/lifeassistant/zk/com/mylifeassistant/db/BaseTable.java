package lifeassistant.zk.com.mylifeassistant.db;

import android.database.sqlite.SQLiteDatabase;



/***
 * 正品会基础表(除了Id，其他字段类型未赋值)
 * @author 099
 *
 */
public abstract  class BaseTable implements ITable{
	/***
	 * 创建表
	 * @param db
	 */
	@Override
	public void createTable(SQLiteDatabase db) {
		String sql=DbUtils.startCreateTable(getTableName(),getColumnInfo());	
		db.execSQL(sql);
	}
	/***
	 * 删除表
	 * @param db
	 */
	@Override
	public void dropTable(SQLiteDatabase db) {
		db.execSQL(DbUtils.DB_DROP+getTableName());	
	}
	/**
	 * 初始化表（用于异常）
	 * @param db
	 */
	@Override
	public void initTableForException(SQLiteDatabase db) {
		dropTable(db);
		createTable(db);
	}
	
	

}
