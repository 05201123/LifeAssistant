package lifeassistant.zk.com.mylifeassistant.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * 正品会数据库Helper
 * @author 099
 *
 */
public class QGPDBHelper extends SQLiteOpenHelper{
	private static QGPDBHelper mInstance;
	private SQLiteDatabase mDB;
	 synchronized SQLiteDatabase getSQLiteDB() {
		if (mDB == null) {
			mDB = getWritableDatabase();
		}
		DbAutoCloseController.getInstance().userDb();
		return mDB;
	}
	private QGPDBHelper(Context context) {
		super(context, DBManager.DATABASE_NAME, null, DBManager.DATABASE_VERSION);
	}
	/**
	 * @return the mInstance
	 */
	public static QGPDBHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new QGPDBHelper(context.getApplicationContext());
		}
		return mInstance;
	}

	public QGPDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		DBManager.create(db);
		DbAutoCloseController.getInstance().userDb();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DBManager.onUpgrade(db,oldVersion,newVersion);
		DbAutoCloseController.getInstance().userDb();
	}
	/***
	 * 插入数据
	 * @param tableName
	 * @param values
	 */
	public synchronized void insert(String tableName,ContentValues values){
		try {
			long insertCount = getSQLiteDB().insert(tableName, null, values);
			DbAutoCloseController.getInstance().userDb();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 更新数据
	 * @param tableName
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public synchronized void update(String tableName,ContentValues values,String whereClause,String[] whereArgs){
		getSQLiteDB().update(tableName, values, whereClause, whereArgs);
		DbAutoCloseController.getInstance().userDb();
	}
	/**
	 * 查询数据
	 * @param tableName
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	public Cursor query(String tableName,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit){
		DbAutoCloseController.getInstance().userDb();
//		return getSQLiteDB().rawQuery("select * from msg_table where userid=?", selectionArgs);
		return getSQLiteDB().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	/**
	 * 查询数据
	 * @param tableName
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor query(String tableName,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
		DbAutoCloseController.getInstance().userDb();
		return getSQLiteDB().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	/**
	 * 删除数据
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 */
	public synchronized void delete(String table, String whereClause,String[] whereArgs){
		getSQLiteDB().delete(table, whereClause, whereArgs);
		DbAutoCloseController.getInstance().userDb();
	}
	/**
	 * 删除数据
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 */
	public synchronized int deleteNew(String table, String whereClause,String[] whereArgs){
		DbAutoCloseController.getInstance().userDb();
		return getSQLiteDB().delete(table, whereClause, whereArgs);
		
	}
	/**
	 * 开启事务
	 */
	public synchronized void beginTransaction() {
		getSQLiteDB().beginTransaction();
		DbAutoCloseController.getInstance().userDb();
	}
	/**
	 * 事务成功
	 */
	public synchronized void setTransactionSuccessful() {
		getSQLiteDB().setTransactionSuccessful();
		DbAutoCloseController.getInstance().userDb();
	}
	/**
	 * 事务结束
	 */
	public synchronized void endTransaction() {
		getSQLiteDB().endTransaction();
		DbAutoCloseController.getInstance().userDb();
	}
	@Override
	public synchronized void close() {
		super.close();
		mDB=null;
	}
	
}
