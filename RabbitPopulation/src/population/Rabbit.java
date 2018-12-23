package population;

public class Rabbit {

	private String sex;
	private int age;
	private int maturity;
	private boolean gestation;
	private int litters;

	public Rabbit(String sex, int age, int maturity, boolean gestation, int litters) {
		super();
		this.sex = sex;
		this.age = age;
		this.maturity = maturity;
		this.gestation = gestation;
		this.litters = litters;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMaturity() {
		return maturity;
	}

	public void setMaturity(int maturity) {
		this.maturity = maturity;
	}

	public boolean isGestation() {
		return gestation;
	}

	public void setGestation(boolean gestation) {
		this.gestation = gestation;
	}

	public int getLitters() {
		return litters;
	}

	public void setLitters(int litters) {
		this.litters = litters;
	}

	public boolean isMature() {
		return age >= maturity;
	}

	public int getYears() {
		return age / 12;
	}

}