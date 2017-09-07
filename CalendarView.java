import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CalendarView extends Application {
  @Override
  public void start(Stage primaryStage) {

    // Create a new CalendarPane
    CalendarPane calendarPane = new CalendarPane();

    // Pane to hold buttons
    HBox buttonBox = new HBox(10);
    Button btAdd = new Button("+");
    Button btSub = new Button("-");
    btAdd.setOnAction(e -> calendarPane.nextMonth());
    btSub.setOnAction(e -> calendarPane.lastMonth());
    buttonBox.getChildren().addAll(btSub, btAdd);
    buttonBox.setAlignment(Pos.CENTER);

    // Create BorderPane and place all of the elements
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(calendarPane);
    borderPane.setBottom(buttonBox);

    Scene scene = new Scene(borderPane, 450, 200);
    primaryStage.setTitle("Calendar");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}

/** Extend the Pane class that will create a calendar pane */
class CalendarPane extends Pane {
  private Calendar calendar;
  private Text calendarLabel;
  private String monthName;

  /** Default constructor */
  CalendarPane() {
    calendar = GregorianCalendar.getInstance();
    calendar.set(Calendar.DATE, 1);
    draw();
  }

  /** Advance the calendar by 1 month */
  public void nextMonth() {
    calendar.add(Calendar.MONTH, 1);
    draw();
  }

  /** Decrement the calendar by 1 month */
  public void lastMonth() {
    calendar.add(Calendar.MONTH, -1);
    draw();
  }

  /** Set up the calendar pane and get the require objects */
  private void draw() {
    this.getChildren().clear();

    // Create the border pane, then get the calendar header and body
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(getCalendarHeader());
    borderPane.setCenter(getCalendarGrid());

    displayCalendar(borderPane);
  }

  /** Return the calendar pane */
  private void displayCalendar(BorderPane borderPane) {
    this.getChildren().addAll(borderPane);
  }

  private Pane getCalendarGrid() {
    // Variables
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    // Create the GridPane for the calendar
    GridPane calendarGrid = new GridPane();
    calendarGrid.setHgap(10.0);
    calendarGrid.setVgap(5.0);
    calendarGrid.setPadding(new Insets(10));

    // Add the days of the week to the top row of the calendar grid
    calendarGrid.addRow(0, new Text("Sunday"), new Text("Monday"),
        new Text("Tuesday"), new Text("Wednesday"), new Text("Thursday"),
        new Text("Friday"), new Text("Saturday"));

    // Print the dates for the current month's calendar
    Calendar thisMonth = (Calendar) calendar.clone(); // Clone does not effect current calendar object
    for (int i = 1; i <= daysInMonth; i++) {
      calendarGrid.add(new Text(thisMonth.get(Calendar.DATE) + ""), thisMonth.get(Calendar.DAY_OF_WEEK) - 1, thisMonth.get(Calendar.WEEK_OF_MONTH));
      thisMonth.add(Calendar.DATE, 1);
    }

    // Print the dates for the previous month on the current calendar
    Calendar lastMonth = (Calendar) calendar.clone();
    lastMonth.add(Calendar.DAY_OF_MONTH, -1);
    if (dayOfWeek != 1)
      for (int i = lastMonth.get(Calendar.DAY_OF_WEEK) - 1; i >= 0; i--) {
        Text dateNumbers = new Text(lastMonth.get(Calendar.DATE) + "");
        dateNumbers.setFill(Color.GRAY);
        calendarGrid.add(dateNumbers, lastMonth.get(Calendar.DAY_OF_WEEK) - 1, 1);
        lastMonth.add(Calendar.DATE, -1);
      }

    // Print the dates for the next month on the current calendar
    Calendar nextMonth = (Calendar) calendar.clone();
    nextMonth.add(Calendar.MONTH, 1);
    if (nextMonth.get(Calendar.DAY_OF_WEEK) != 1)
      for (int i = nextMonth.get(Calendar.DAY_OF_WEEK) - 1; i < 7; i++) {
        Text dateNumbers = new Text(nextMonth.get(Calendar.DATE) + "");
        dateNumbers.setFill(Color.GRAY);
        calendarGrid.add(dateNumbers, nextMonth.get(Calendar.DAY_OF_WEEK) - 1, calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));
        nextMonth.add(Calendar.DATE, 1);
      }

    return calendarGrid;
  }

  /** Create the label pane and display current month and year */
  private Pane getCalendarHeader() {
    calendarLabel = new Text(getMonthName(calendar.get(Calendar.MONTH)) + ", " + calendar.get(Calendar.YEAR));
    HBox labelBox = new HBox();
    labelBox.getChildren().addAll(calendarLabel);
    labelBox.setAlignment(Pos.CENTER);

    return labelBox;
  }

  /** Return month name in English */
  private String getMonthName(int month) {
    // String monthName = null;

    switch (month) {
      case 0: monthName = "January"; break;
      case 1: monthName = "February"; break;
      case 2: monthName = "March"; break;
      case 3: monthName = "April"; break;
      case 4: monthName = "May"; break;
      case 5: monthName = "June"; break;
      case 6: monthName = "July"; break;
      case 7: monthName = "August"; break;
      case 8: monthName = "September"; break;
      case 9: monthName = "October"; break;
      case 10: monthName = "November"; break;
      case 11: monthName = "December"; break;
      default:

    }
    return monthName;
  }
}