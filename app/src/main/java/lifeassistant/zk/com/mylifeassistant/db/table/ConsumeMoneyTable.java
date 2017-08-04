package lifeassistant.zk.com.mylifeassistant.db.table;


import lifeassistant.zk.com.mylifeassistant.db.BaseTable;
import lifeassistant.zk.com.mylifeassistant.db.DbContacts;

/***
 * 消费表
 * @author 099
 *
 */
public class ConsumeMoneyTable extends BaseTable {
	/***_id自增 字段DBUtils默认添加****/
	/**表名**/
	public static final String TABLE="consume_money_table";
	/**升级模式**/
	private static final int UPDATE_MODE= DbContacts.MODIFY_UPDATE;
	/**固定分类字段*/
	/** 早餐费用 **/
	public static final String BREAKFASTCOST="breakfast_cost";
	/** 午餐费用 **/
	public static final String LUNCHCOST="lunch_cost";
	/** 晚餐费用**/
	public static final String DINNERCOST="dinner_cost";
	/** 其他生活费用 **/
	public static final String EXTRACOST="extra_cost";
	/**其他生活费用说明*/
	public static final String EXTRACOSTDSP="extra_cost_dsp";
	/** 孩子费用 **/
	public static final String CHILDCOST="child_cost";
	/** 老婆费用 **/
	public static final String WIFECOST="wife_cost";
	/**提交时间*/
	public static final String SUMBITTIME="sumbit_time";
	/**消费时间年*/
	public static final String COSTTIMEYEAR="cost_time_year";
    /**消费时间月*/
    public static final String COSTTIMEMONTH="cost_time_month";
    /**消费时间日*/
    public static final String COSTTIMEDAY="cost_time_day";
	/**总消费*/
	public static final String TOTALMONEY="total_money";
	/**最低升级版本*/
	public static final int MIN_UPDATE_VERSON=1;
	
	/**
	 * 表名
	 */
	@Override
	public String getTableName() {
		 return TABLE;
	}
	/**
	 * 表的列名
	 */
	@Override
	public String[] getColumnInfo() {
		return new String[]{BREAKFASTCOST,LUNCHCOST,DINNERCOST,EXTRACOST,EXTRACOSTDSP,SUMBITTIME,COSTTIMEYEAR,COSTTIMEMONTH,COSTTIMEDAY,TOTALMONEY,CHILDCOST,WIFECOST};
	}
	/***
	 * 获取表的升级模式
	 * @return
	 */
	@Override
	public int getUpdateMode() {
		return UPDATE_MODE;
	}

	@Override
	public int getMinUpdateVerson() {
		return MIN_UPDATE_VERSON;
	}

}
