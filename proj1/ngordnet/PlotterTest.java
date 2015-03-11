package ngordnet;
/** 
 *  @author Hubert Pham
 */

public class PlotterTest {
	
	public static void main(String[] args){
		TimeSeries<Double> x = new TimeSeries<Double>();
		x.put(1992, 3.6); 
        x.put(1993, 9.2); 
        x.put(1994, 15.2); 
        x.put(1995, 16.1);
        x.put(1996, -15.7);
        Plotter.plotTS(x, "hubert", "years", "data", "hey");
	}
}
	