package frc.robot.telemetries;

import java.io.BufferedWriter;
import java.util.function.Function;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TraceEntry<T> {
  private BufferedWriter m_file;
  private int m_numbOfValues;
  private Function<TracePair<T>[], String> formatter;
  private Function<TracePair<T>[], String> headerFormatter;
  private boolean activated;
  private String traceName;

  public TraceEntry(BufferedWriter file, int numbOfValues, Function<TracePair<T>[], String> formatter,
      Function<TracePair<T>[], String> headerFormatter, String traceName) {
    m_file = file;
    m_numbOfValues = numbOfValues;
    this.formatter = formatter;
    this.headerFormatter = headerFormatter;
    this.traceName = traceName;
    SmartDashboard.putBoolean("Trace/" + traceName, true);
  }

  public boolean getActivated() {
    return SmartDashboard.getBoolean("Trace/" + traceName, true);
  }

  public BufferedWriter getFile() {
    return (m_file);
  }

  public long getNumbOfValues() {
    return (m_numbOfValues);
  }

  public String format(TracePair<T>... tracePair) {
    return formatter.apply(tracePair);
  }

  public String formatHeader(TracePair<T>... header) {
    return headerFormatter.apply(header);
  }

}
