package agmark.com.agmarks;

/**
 * 
 * @author anfer
 * 
 */
public class ModelF1 {

	private String str1;
	private String str2;
	private String str3;
	private String str4;


	public ModelF1(String string1, String string2, String string3, String string4) {
		this.str1 = string1;
		this.str2 = string2;
		this.str3 = string3;
		this.str4 = string4;

	}

	public String getsNo() {
		return str1;
	}

	public String getProduct() {
		return str2;
	}

	public String getCategory() {
		return str3;
	}

	public String getPrice() {
		return str4;
	}


}
