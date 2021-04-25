package frc.robot.pathgeneration.waypoints;

import java.awt.geom.Point2D;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class Waypoint extends Point2D {
  private double m_x;
  private double m_y;

  public Waypoint(double x, double y) {
    m_x = x;
    m_y = y;
  }

  public Waypoint(Pose2d pose) {
    this(pose.getTranslation().getX(), pose.getTranslation().getY());
  }

  public double getX() {
    return m_x;
  }

  public double getY() {
    return m_y;
  }

  @Override
  public void setLocation(double x, double y) {
    x = m_x;
    y = m_y;

  }

  public Waypoint subtract(Waypoint w) {
    double deltaX = m_x - w.getX();
    double deltaY = m_y - w.getY();

    return new Waypoint(deltaX, deltaY);
  }

  public Waypoint add(Waypoint w) {
    double xSum = m_x + w.getX();
    double ySum = m_y + w.getY();

    return new Waypoint(xSum, ySum);
  }

  /**
   * 
   * @param factor
   * @return a new waypoint in which each coordinate has been multiplied by factor
   */
  public Waypoint multiply(double factor) {
    double xTerm = m_x * factor;
    double yTerm = m_y * factor;

    return new Waypoint(xTerm, yTerm);
  }

  /**
   * 
   * @param factor
   * @return a new waypoint in which each coordinate has been divided by factor
   */
  public Waypoint divide(double factor) {
    double xTerm = m_x / factor;
    double yTerm = m_y / factor;

    return new Waypoint(xTerm, yTerm);
  }

  /**
   * 
   * @param waypoints
   * @return a new waypoint that has components which are each the sum of this
   *         waypoint and all the waypoint's passed in components divided by the
   *         total number of waypoints.
   */
  public Waypoint average(Waypoint... waypoints) {
    double sumOfX = m_x;
    double sumOfY = m_y;
    double count = 1;
    for (Waypoint w : waypoints) {
      sumOfX += w.getX();
      sumOfY += w.getY();
      count++;
    }

    return new Waypoint((sumOfX / count), (sumOfY / count));
  }

  public void prettyPrint() {
    System.out.println("(" + getX() + ", " + getY() + ")");
  }
}
