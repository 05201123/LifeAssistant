package lifeassistant.zk.com.mylifeassistant.dto;

/**
 * Created by 099 on 2017/8/1.
 * 消费记录DTO
 */

public class CostRecordDTO {

    /**早餐费用*/
    private double breakfastCost;
    /**午餐费用*/
    private double lunch_cost;
    /**晚餐费用*/
    private double dinner_cost;
    /**额外费用*/
    private double extra_cost;
    /**额外费用说明*/
    private String extra_cost_dsp;
    /**孩子费用*/
    private double child_cost;
    /**老婆费用*/
    private double wife_cost;
    /**提交费用*/
    private long sumbit_time;
    /**消费时间*/
    private String cost_time;
    /**消费总额*/
    private double total_money;
    public double getBreakfastCost() {
        return breakfastCost;
    }

    public void setBreakfastCost(double breakfastCost) {
        this.breakfastCost = breakfastCost;
    }

    public double getLunch_cost() {
        return lunch_cost;
    }

    public void setLunch_cost(double lunch_cost) {
        this.lunch_cost = lunch_cost;
    }

    public double getDinner_cost() {
        return dinner_cost;
    }

    public void setDinner_cost(double dinner_cost) {
        this.dinner_cost = dinner_cost;
    }

    public double getExtra_cost() {
        return extra_cost;
    }

    public void setExtra_cost(double extra_cost) {
        this.extra_cost = extra_cost;
    }

    public String getExtra_cost_dsp() {
        return extra_cost_dsp;
    }

    public void setExtra_cost_dsp(String extra_cost_dsp) {
        this.extra_cost_dsp = extra_cost_dsp;
    }

    public double getChild_cost() {
        return child_cost;
    }

    public void setChild_cost(double child_cost) {
        this.child_cost = child_cost;
    }

    public double getWife_cost() {
        return wife_cost;
    }

    public void setWife_cost(double wife_cost) {
        this.wife_cost = wife_cost;
    }

    public long getSumbit_time() {
        return sumbit_time;
    }

    public void setSumbit_time(long sumbit_time) {
        this.sumbit_time = sumbit_time;
    }

    public String getCost_time() {
        return cost_time;
    }

    public void setCost_time(String cost_time) {
        this.cost_time = cost_time;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }
    public static double getTotalCost(CostRecordDTO dto){
        return dto.breakfastCost+dto.lunch_cost+dto.dinner_cost+dto.child_cost+dto.wife_cost+dto.extra_cost;
    }

    @Override
    public String toString() {
        return "CostRecordDTO{" +
                "breakfastCost=" + breakfastCost +
                ", lunch_cost=" + lunch_cost +
                ", dinner_cost=" + dinner_cost +
                ", extra_cost=" + extra_cost +
                ", extra_cost_dsp='" + extra_cost_dsp + '\'' +
                ", child_cost=" + child_cost +
                ", wife_cost=" + wife_cost +
                ", sumbit_time=" + sumbit_time +
                ", cost_time='" + cost_time + '\'' +
                ", total_money=" + total_money +
                '}';
    }
}
