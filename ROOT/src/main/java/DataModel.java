package rest;


public class DataModel {
    public String gyroscopeValue;

    public String microphoneValue;

    public String acceleroMeterValue;

    public String currentTime;
	
	public String userName;
	
	public String city;
	
	public DataModel(String gyro,String accelero, String micro, String time, String user, String city)
	{
		this.gyroscopeValue = gyro;
		this.acceleroMeterValue = accelero;
		this.microphoneValue = micro;
		this.currentTime = time;
		this.userName = userName;
		this.city = city;
	}
}