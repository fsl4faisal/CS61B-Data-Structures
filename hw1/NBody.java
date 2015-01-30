public class NBody {
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		In in = new In(filename);
		int planets = in.readInt();
		double r = in.readDouble();
		Planet[] planetArray = new Planet[planets];
		for (int i=0; i<planets; i++) {
			planetArray[i] = getPlanet(in);
		}
		StdDraw.setScale(-r, r);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Planet h : planetArray) {
			h.draw();
		}
		double time = 0;
		while (time <= T) {
			for (Planet p : planetArray) {
			p.setNetForce(planetArray);
			p.update(dt);
		}
		StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet h : planetArray) {
			h.draw();
		}
		StdDraw.show(10);
		time += dt;
	}

	StdOut.printf("%d\n", planets);
	StdOut.printf("%.2e\n", r);
	for (int i = 0; i < planets; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planetArray[i].x , planetArray[i].y, planetArray[i].xVelocity, planetArray[i].yVelocity, planetArray[i].mass, planetArray[i].img);
}
}
public static Planet getPlanet(In filename) {
		return new Planet(filename.readDouble(), filename.readDouble(), filename.readDouble(), filename.readDouble(), filename.readDouble(), filename.readString());
	}
}