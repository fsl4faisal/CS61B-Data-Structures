public class Planet {
	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	public double mass;
	public String img;
	public double xNetForce;
	public double yNetForce;
	public double xAccel;
	public double yAccel;

	public Planet(double x, double y, double xVelocity, double yVelocity, double mass, String img) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.mass = mass;
		this.img = img;
	}	
	public double calcDistance(Planet p) {
		double dx = p.x - x;
		double dy = p.y - y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	public double calcPairwiseForce(Planet p) {
		double r = calcDistance(p);
		return 6.67e-11 * mass * p.mass / (r*r);
	}
	public double calcPairwiseForceX(Planet p) {
		double dx = p.x - x;
		return calcPairwiseForce(p) * dx / calcDistance(p);
	}
	public double calcPairwiseForceY(Planet p) {
		double dy = p.y - y;
		return calcPairwiseForce(p) * dy / calcDistance(p);
	}
	public void setNetForce(Planet[] planets) {
		xNetForce = 0;
		yNetForce = 0;
		for (Planet p : planets) {
			if (this != p) {
				xNetForce += calcPairwiseForceX(p);
				yNetForce += calcPairwiseForceY(p);
			}
		}
	}
	public void draw() {
		StdDraw.picture(x,y, "images/" + img);
	}
	public void update(double dt) {
		xAccel = xNetForce / mass;
		yAccel = yNetForce / mass;
		xVelocity = xVelocity + dt * xAccel;
		yVelocity = yVelocity + dt * yAccel;
		x = x + dt * xVelocity;
		y = y + dt * yVelocity; 
	}
	}