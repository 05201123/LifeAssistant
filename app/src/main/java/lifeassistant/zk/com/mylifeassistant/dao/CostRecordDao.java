package lifeassistant.zk.com.mylifeassistant.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import lifeassistant.zk.com.mylifeassistant.core.CoreApi;
import lifeassistant.zk.com.mylifeassistant.db.QGPDBHelper;
import lifeassistant.zk.com.mylifeassistant.db.table.ConsumeMoneyTable;
import lifeassistant.zk.com.mylifeassistant.dto.CostRecordDTO;


/**
 * 费用记录dao
 * 
 * @author 099
 * 
 */
public class CostRecordDao {
	private static CostRecordDao instance;
	private QGPDBHelper helper;

	private CostRecordDao() {
		helper = QGPDBHelper.getInstance(CoreApi.getInstance().getApplicationContext());
	}

	public static synchronized CostRecordDao getInstance() {
		if (instance == null)
			instance = new CostRecordDao();
		return instance;
	}

	/*
	 * 写入表
	 * 
	 * @param msg
	 */
	public void saveData(CostRecordDTO content) {
		synchronized (helper) {
			try {
				ContentValues values = new ContentValues();
				String time = content.getCost_time();
				String year = time.substring(0, 4);
				String month = time.substring(4, 6);
				String day = time.substring(6);

				values.put(ConsumeMoneyTable.BREAKFASTCOST, content.getBreakfastCost());
				values.put(ConsumeMoneyTable.LUNCHCOST, content.getLunch_cost());
				values.put(ConsumeMoneyTable.DINNERCOST, content.getDinner_cost());
				values.put(ConsumeMoneyTable.EXTRACOST, content.getExtra_cost());
				values.put(ConsumeMoneyTable.EXTRACOSTDSP, content.getExtra_cost_dsp());
				values.put(ConsumeMoneyTable.CHILDCOST, content.getChild_cost());
				values.put(ConsumeMoneyTable.WIFECOST, content.getWife_cost());
				values.put(ConsumeMoneyTable.SUMBITTIME, content.getSumbit_time());
				values.put(ConsumeMoneyTable.COSTTIMEYEAR, year);
				values.put(ConsumeMoneyTable.COSTTIMEMONTH, month);
				values.put(ConsumeMoneyTable.COSTTIMEDAY, day);
				values.put(ConsumeMoneyTable.TOTALMONEY, content.getTotal_money());
				helper.insert(ConsumeMoneyTable.TABLE, values);
			} catch (Exception e) {

			}
		}
	}

    /**
     * 通过日期查询消费情况
     * @param date
     */
    public CostRecordDTO queryCostInfoBydate(String date) {
		Cursor cursor = null;
		try{
			synchronized (helper) {
				String year = date.substring(0, 4);
				String month = date.substring(4, 6);
				String day = date.substring(6);
				CostRecordDTO dto = new CostRecordDTO();
				cursor=helper.query(ConsumeMoneyTable.TABLE,null,ConsumeMoneyTable.COSTTIMEYEAR+"= ? AND "+ConsumeMoneyTable.COSTTIMEMONTH+"= ? AND "
				+ConsumeMoneyTable.COSTTIMEDAY+"= ? ",new String[]{year,month,day},null,null,null);
				if(cursor!=null&&cursor.moveToFirst()){
					dto.setBreakfastCost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.BREAKFASTCOST)));
					dto.setLunch_cost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.LUNCHCOST)));
					dto.setDinner_cost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.DINNERCOST)));
					dto.setChild_cost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.CHILDCOST)));
					dto.setWife_cost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.WIFECOST)));
					dto.setExtra_cost(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.EXTRACOST)));
					dto.setExtra_cost_dsp(cursor.getString(cursor.getColumnIndex(ConsumeMoneyTable.EXTRACOSTDSP)));
					dto.setSumbit_time(cursor.getLong(cursor.getColumnIndex(ConsumeMoneyTable.SUMBITTIME)));
					dto.setTotal_money(cursor.getDouble(cursor.getColumnIndex(ConsumeMoneyTable.TOTALMONEY)));
					dto.setCost_time(date);
					return dto;
				}

			}

		}catch (Exception e){

		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

    /**
	 * 获取收集的数据
	 * 
	 * @return
	 */
	/*public List<DataCollectContentDTO> getCollectDataList() {
		List<DataCollectContentDTO> msgList = new ArrayList<DataCollectContentDTO>();
		Cursor cursor = null;
		try {
			synchronized (helper) {
				cursor = helper.query(DataCollectTable.TABLE, new String[] { DataCollectTable.ACCESS_TIME, DataCollectTable.APPID, DataCollectTable.DESC_INFO, DataCollectTable.IP,
						DataCollectTable.ITEM_ID, DataCollectTable.LOCATION, DataCollectTable.MSG_ID, DataCollectTable.OPER_TYPE, DataCollectTable.REAL_USERID, DataCollectTable.SERVICE_TYPE,
						DataCollectTable.SESSION_INFO, DataCollectTable.SYS_NAME, DataCollectTable.USER_ID }, null, null, null, null, DbContacts.ID, DbContacts.LIMIT);
				if (cursor != null) {
					for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
						DataCollectContentDTO contentdto = new DataCollectContentDTO();
						DataCollectAddInfoDTO addinfodto = new DataCollectAddInfoDTO();
						DataCollectBaseInfoDTO baseinfodto = new DataCollectBaseInfoDTO();

						addinfodto.setAppid(cursor.getString(cursor.getColumnIndex(DataCollectTable.APPID)));
						addinfodto.setIp(cursor.getString(cursor.getColumnIndex(DataCollectTable.IP)));
						addinfodto.setLoctaion(cursor.getString(cursor.getColumnIndex(DataCollectTable.LOCATION)));
						contentdto.setAdd_info(addinfodto);

						baseinfodto.setAccess_time(cursor.getString(cursor.getColumnIndex(DataCollectTable.ACCESS_TIME)));
						baseinfodto.setDesc_info(cursor.getString(cursor.getColumnIndex(DataCollectTable.DESC_INFO)));
						baseinfodto.setItem_id(cursor.getString(cursor.getColumnIndex(DataCollectTable.ITEM_ID)));
						baseinfodto.setMsg_id(cursor.getString(cursor.getColumnIndex(DataCollectTable.MSG_ID)));
						baseinfodto.setOper_type(cursor.getString(cursor.getColumnIndex(DataCollectTable.OPER_TYPE)));
						baseinfodto.setReal_userid(cursor.getString(cursor.getColumnIndex(DataCollectTable.REAL_USERID)));
						baseinfodto.setService_type(cursor.getString(cursor.getColumnIndex(DataCollectTable.SERVICE_TYPE)));
						baseinfodto.setSession_id(cursor.getString(cursor.getColumnIndex(DataCollectTable.SESSION_INFO)));
						baseinfodto.setSys_name(cursor.getString(cursor.getColumnIndex(DataCollectTable.SYS_NAME)));
						baseinfodto.setUser_id(cursor.getString(cursor.getColumnIndex(DataCollectTable.USER_ID)));
						contentdto.setBase_info(baseinfodto);

						msgList.add(contentdto);
					}
				}
			}

		} catch (Exception e) {
			LogUtil.println("get collectData error");
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return msgList;
	}*/

	

}
