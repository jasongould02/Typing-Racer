package runic.engine.util.math;

import java.util.Random;

public class MathUtil {

	public static int randInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(max-min) + min;
	}
	
	public static boolean inRange(int number, int max, int min) {
		if(number <= max && number >= min) {
			return true;
		} else {
			return false;
		}
	}

	public static int perimeter(int length) {
		int p = length*4;
		return p;
	}

	public static int perimeter(int width, int height) {
		int p = (width*2) + (height*2);
		return p;
	}

	public static double circumference(double radius) {
		double C = 2* java.lang.Math.PI *(radius);
		return C;
	}

	public static int aSquare(int length) {
		int a = length^2;
		return a;
	}

	public static int aRect(int width, int height) {
		return width*height;
	}

	public static double aCircle(double radius) {
		double a = java.lang.Math.PI * (radius * radius);
		return a;
	}

	public static double square(double arg0) {
		return arg0 * arg0;
	}

	public static double getDistance(int x1, int y1, int x2, int y2) {
		double distance =  java.lang.Math.sqrt(square(x2 - x1) + square(y2 - y1));
		if(distance < 0) {
			distance = distance * -1.0;
		}
		return distance;
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double distance =  java.lang.Math.sqrt(square(x2 - x1) + square(y2 - y1));
		if(distance < 0) {
			distance = distance * -1.0;
		}
		return distance;
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2) {
		float distance =  (float) java.lang.Math.sqrt(square(x2 - x1) + square(y2 - y1));
		if(distance < 0) {
			distance = distance * -1.0f;
		}
		return distance;
	}

	public static double getDistance(Vec2f p1, Vec2f p2) {
		double distance =  java.lang.Math.sqrt(square(p2.getX() - p1.getX()) + square(p2.getY() - p1.getY()));
		return distance;
	}

	public static int getYDistance(int y1, int y2) {
		int ydistance = y1 - y2;
		return ydistance;
	}

	public static int getXDistance(int x1, int x2) {
		int xdistance = x1 - x2;
		return xdistance;
	}

	public static double distanceTo(Vec2f pos1, Vec2f pos2) {
		return getDistance(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
	}
	public static int distance(Vec2f pos1, Vec2f pos2) {
		return (int) getDistance(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
	}

}
