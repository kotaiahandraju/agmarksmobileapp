package example.com.agmarks;

/**
 * 
 * @author anfer
 * 
 */
public class Model1 {


	private String Market;
	private String Commodity;
	private String Units;
	private String Variety;
	private String Date;
	private String Modalprice;
	private String UnitofPrice;
	private String Maxprice;
	private String Minprice;

	public Model1(String market,String commodity,String units, String variety, String date, String modal_price,
				  String unit_of_price, String max_price, String min_price) {
		this.Market=market;
		this.Commodity=commodity;
		this.Units=units;
		this.Variety=variety;
		this.Date=date;
		this.Modalprice=modal_price;
		this.UnitofPrice=unit_of_price;
		this.Maxprice=max_price;
		this.Minprice=min_price;
	}


	public void setUnits(String units){
		Units=units;
	}

	public String getUnits() {
		return Units;
	}

	public void setVariety(String variety){
		Variety=variety;
	}

	public String getVariety() {
		return Variety;
	}

	public void setDate(String date){
		Date=date;
	}

	public String getDate(){
		return Date;
	}

	public String getUnitofPrice() {
		return UnitofPrice;
	}

	public void setUnitofPrice(String unitofPrice) {
		UnitofPrice = unitofPrice;
	}

	public String getModalprice() {
		return Modalprice;
	}

	public void setModalprice(String modalprice) {
		Modalprice = modalprice;
	}

	public String getMaxprice() {
		return Maxprice;
	}

	public void setMaxprice(String maxprice) {
		Maxprice = maxprice;
	}

	public String getMinprice() {
		return Minprice;
	}

	public void setMinprice(String minprice) {
		Minprice = minprice;
	}

	public String getMarket() {
		return Market;
	}

	public void setMarket(String market) {
		Market = market;
	}

	public String getCommodity() {
		return Commodity;
	}

	public void setCommodity(String commodity) {
		Commodity = commodity;
	}
}
