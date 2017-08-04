package lifeassistant.zk.com.mylifeassistant.db;
/***
 * 数据库常量
 * @author 099
 *
 */
public interface  DbContacts {
	public static final String ID="_id";//自增长，不用放到getColumnInfo()中
	public static final String LIMIT="10";//限制查询条数
	public static final int INT_LIMIT=10;
	/**数据库表升级时，删除表，建立新表**/
	 int DROP_UPDATE=0;
	/**数据库表升级时，更新已有表*/
	 int MODIFY_UPDATE=1;
	 
	 
}
