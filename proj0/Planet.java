public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
                  
    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67E-11;
        double m1 = p.mass;
        double m2 = this.mass;
        double d = calcDistance(p);
        return G*m1*m2/(d*d);
    }

    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        double dx = p.xxPos - this.xxPos;
        double r = calcDistance(p);
        return F*dx/r;
    }

    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        double dy = p.yyPos - this.yyPos;
        double r = calcDistance(p);
        return F*dy/r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double x = 0;
        for (Planet p: planets) {
            if (!this.equals(p)) {
                x += calcForceExertedByX(p);
            }
        }
        return x;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double y = 0;
        for (Planet p: planets) {
            if (!this.equals(p)) {
                y += calcForceExertedByY(p);
            }
        }
        return y;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX/this.mass;
        double aY = fY/this.mass;
        this.xxVel += aX * dt;
        this.yyVel += aY * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}