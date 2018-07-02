package agmark.com.agmarks;

/**
 * 
 * @author anfer
 * 
 */
public class Model2 {

	private String Market;
	private String Vegetables;
	private String Rate;
	private String Date;
	private String Id;



	public Model2(String market, String vegetables, String rate, String date, String id) {
		this.Market = market;
		this.Vegetables = vegetables;
		this.Rate=rate;
		this.Date=date;
		this.Id=id;
	}


	public String getMarket() {
		return Market;
	}

	public void setMarket(String market) {
		Market = market;
	}

	public String getVegetables() {
		return Vegetables;
	}

	public void setVegetables(String vegetables) {
		Vegetables = vegetables;
	}

	public String getRate() {
		return Rate;
	}

	public void setRate(String rate) {
		Rate = rate;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
