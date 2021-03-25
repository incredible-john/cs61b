public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        int numOfPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int numOfPlanets = in.readInt();
        Planet[] planets = new Planet[numOfPlanets];
        double radius = in.readDouble();
        for (int i = 0; i < numOfPlanets; i += 1) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), 
                                    in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String file = args[2];
        Planet[] planets = readPlanets(file);
        double radius = readRadius(file);
        // Drawing universe
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        double t = 0;
        while (t < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i += 1) {
                planets[i].update(dt,xForces[i], yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg", radius*2, radius*2);
            for (Planet p: planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}