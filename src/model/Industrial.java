package model;

/**
 * A class extending Project that is representing one of the four types of the project.
 * @author Michael Barczuk
 */
public class Industrial extends Project
{
  private double size;
  private String useOfBuilding;
  /**
   * this static variable is returning the defaults used in view part of the system
   * */
  public final static String[] defaults = {"1"};

  /**
   * A seven-arguments constructor. Uses the checks from Project class constructor. Ensures no illegal values for size in which case it throws an exception.
   * @param id
   *         the id of the project
   * @see Project
   * @param budget
   *        the budget as a double, that cannot be less than 0.
   * @see Project
   * @param startDate
   *        the start date of project
   * @see Project
   * @see SimpleDate
   * @param expectedDuration
   *        the expected duration of project as an Integer, that cannot be less than 0.
   * @see Project
   * @param resources
   *        the resources of the project
   * @see Project
   * @see Resources
   * @param size
   *        size of the building in m^2 as a double
   * @param useOfBuilding
   *        intended use of building as a String it can be anything, because of broad definition no limitations are imposed
   */

  public Industrial(String id, double budget, SimpleDate startDate,
                    int expectedDuration, Resources resources, double size, String useOfBuilding)

  {
    super(id, budget, startDate, expectedDuration, resources, "Industrial");
    setSize(size);
    setUseOfBuilding(useOfBuilding);
  }

  /**
   * A method that allows the user to edit(change) some values of the project after creating it in the system.
   * @param budget
   *        a new budget as a double
   * @see Project
   * @param newExpectedDuration
   *        a new expected duration as an integer
   * @see Project
   * @param isNotBehindTheSchedule
   *        allows user to change if the project is behind schedule or not, a boolean
   * @see Project
   * @param isOngoing
   *        allows user to mark project as completed or as ongoing again, a boolean
   * @see Project
   * @param useOfBuilding
   *        a String representing the intended use of building, value of the Industrial type
   * @param resources
   *        allows user to edit the resources of the project, Resource class
   * @see Project
   * @see Resources
   */
  public void edit(double budget, int newExpectedDuration, boolean isNotBehindTheSchedule, boolean isOngoing, String useOfBuilding, Resources resources)
  {
    super.setBudget(budget);
    super.setExpectedDurationInMonths(newExpectedDuration);
    super.setIsNotBehindTheSchedule(isNotBehindTheSchedule);
    super.setResources(resources);
    super.setIsOngoing(isOngoing);
    setUseOfBuilding(useOfBuilding);
    setSize(size);
  }

  /**
   *  A method responsible for setting the size of the project. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param size
   *        size of the building in m^2 as a double
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setSize(double size){
    if (size > 0){
      this.size = size;
    }else throw new IllegalArgumentException("Invalid size.");
  }

  /**
   * A method responsible for setting the intended use of building. Due to broad range of possible inputs no checks here.
   * @param useOfBuilding
   *        a String representing the intended use of building
   */
  public void setUseOfBuilding(String useOfBuilding){
    this.useOfBuilding = useOfBuilding;
  }
  /**
   * A method returning the size of the project.
   * @return a double representing size of the project.
   */
  public double getSize()
  {
    return size;
  }
  /**
   * A method returning the intended use of building.
   * @return a String representing the intended use of building.
   */
  public String getUseOfBuilding(){
    return useOfBuilding;
  }
  /**
   * A to String method.
   * @return the Industrial project type as String.
   */
  public String toString()
  {
    return "id: " + super.getId();
  }
}

