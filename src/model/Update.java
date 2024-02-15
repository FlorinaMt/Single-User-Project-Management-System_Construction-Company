package model;

/**
 * An Update class containing the updates to the project's values.
 * @author Florina Mitigus
 */
public class Update
{
  private double salaryToday, materialsExpensesToday;
  private int manHoursUsedToday;
  private String materialsExpensesTodayNote, salaryNote, manHoursUsedTodayNote;
  private SimpleDate today;
  /**
   * A six-arguments constructor. Takes in six values and assigns them without any checks.
   * @param salaryToday
   *        a double representing the salary expenses used since last update
   * @param materialsExpensesToday
   *        a double representing the materials expenses used since last update
   * @param manHoursUsedToday
   *        an integer representing the man hours used since last update
   * @param materialsExpensesTodayNote
   *        a String representing the note for materials expenses used since last update
   * @param salaryNote
   *        a String representing the note for salary expenses used since last update
   * @param manHoursUsedTodayNote
   *        a String representing the note for man hours used since last update
   */
  public Update(double salaryToday, double materialsExpensesToday, int manHoursUsedToday, String materialsExpensesTodayNote, String salaryNote, String manHoursUsedTodayNote)
  {
    this.manHoursUsedToday=manHoursUsedToday;
    this.salaryToday=salaryToday;
    this.materialsExpensesToday=materialsExpensesToday;
    this.manHoursUsedTodayNote=manHoursUsedTodayNote;
    this.salaryNote=salaryNote;
    this.materialsExpensesTodayNote=materialsExpensesTodayNote;

    today=new SimpleDate(); //  Composition handled by creating new SimpleDate object when update constructor called
  }
  /**
   * A method returning the man hours.
   * @return the man hours as an integer.
   */
  public double getManHoursUsedToday()
  {
    return manHoursUsedToday;
  }
  /**
   * A method returning the salary expenses.
   * @return the salary expenses as a double.
   */
  public double getSalaryToday()
  {
    return salaryToday;
  }
  /**
   * A method returning the materials expenses.
   * @return the materials expenses as a double.
   */
  public double getMaterialsExpensesToday()
  {
    return materialsExpensesToday;
  }
  /**
   * A method returning the salary expenses note.
   * @return the salary expenses note as a String.
   */
  public String getSalaryNote()
  {
    return salaryNote;
  }
  /**
   * A method returning the materials expenses note.
   * @return the materials expenses note as a String.
   */
  public String getMaterialsExpensesTodayNote()
  {
    return materialsExpensesTodayNote;
  }
  /**
   * A method returning the man hours used note.
   * @return the man hours used note as a String.
   */
  public String getManHoursUsedTodayNote()
  {
    return manHoursUsedTodayNote;
  }
  /**
   * A method returning the date of the update.
   * @return the date of the update as a SimpleDate.
   */
  public SimpleDate getCurrentDate()
  {
    return today;
  }

}
