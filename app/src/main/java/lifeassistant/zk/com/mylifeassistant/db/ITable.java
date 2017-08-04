package lifeassistant.zk.com.mylifeassistant.db;

import android.database.sqlite.SQLiteDatabase;

/***
 * 数据库表基础实现接口
 * @author 099
 *
 */
public interface ITable {
	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName();
	/**
	 * 获取表名
	 * @return
	 */
	public String[] getColumnInfo();
	/**
	 * 最低升级版本
	 * @return
	 */
	public int getMinUpdateVerson();
	/***
	 * 更新模式
	 * @return
	 */
	public int getUpdateMode();
	/***
	 * 生成表
	 * @param db
	 */
	public void createTable(SQLiteDatabase db);
	/**
	 * 删除表
	 * @param db
	 */
	public void dropTable(SQLiteDatabase db);

	/**
	 * 初始化
	 * @param db
	 */
	public  void initTableForException(SQLiteDatabase db);

}
