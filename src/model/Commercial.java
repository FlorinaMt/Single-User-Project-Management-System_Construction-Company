package model;

/**
 * A class extending Project that is representing one of the four types of the project.
 * @author Michael Barczuk
 */
public class Commercial extends Project
{
  private double size;
  private String useOfBuilding;
  private int numberOfFloors;
  /**
   * this static variable is returning the defaults used in view part of the system
   * */
  public final static String[] defaults = {"18", "1"};

  /**
   * An eight-argument constructor. Uses the checks from Project class constructor. Ensures no illegal values for size and number of floors are allowed in which case it throws an exception.
   * @param id
   *         the id of the project
   * @see Project
   * @param budget
   *        the budget as a double, that cannot be less than 0.
   * @see Project
   * @param startDate
   *        the start date of project
   * @see SimpleDate
   * @param expectedDuration
   *        the expected duration of project as an Integer, that cannot be less than 0.
   *  @see Project
   * @param resources
   *        the resources of the project
   *  @see Project
   * @param size
   *        size of the building in m^2 as a double
   * @param useOfBuilding
   *        intended use of building as a String it can be anything, because of broad definition no limitations are imposed
   * @param numberOfFloors
   *        number of floors of the building as an int
   */
  public Commercial(String id, double budget, SimpleDate startDate,
                    int expectedDuration, Resources resources, double size, String useOfBuilding, int numberOfFloors)
  {
    super(id, budget, startDate, expectedDuration, resources, "Commercial");
    setSize(size);
    setUseOfBuilding(useOfBuilding);
    setNumberOfFloors(numberOfFloors);
  }

  /**
   * A method that allows the user to edit(change) some values of the project after creating it in the system.
   * @param budget
   *        a new budget as a double
   * @see Project
   * @param newExpectedDuration
   *        a new expected duration as an integer
   *  @see Project
   * @param isNotBehindTheSchedule
   *        allows user to change if the project is behind schedule or not, a boolean
   *  @see Project
   * @param isOngoing
   *        allows user to mark project as completed or as ongoing again, a boolean
   *  @see Project
   * @param useOfBuilding
   *        a String representing the intended use of building, value of the Commercial type
   * @param numberOfFloors
   *        number of floors as an integer, value of the Commercial type
   * @param resources
   *        allows user to edit the resources of the project, Resource class
   *  @see Project
   * @see Resources
   */
  public void edit(double budget, int newExpectedDuration, boolean isNotBehindTheSchedule, boolean isOngoing, String useOfBuilding,int numberOfFloors, Resources resources){
    super.setBudget(budget);
    super.setExpectedDurationInMonths(newExpectedDuration);
    super.setIsNotBehindTheSchedule(isNotBehindTheSchedule);
    super.setResources(resources);
    super.setIsOngoing(isOngoing);
    setUseOfBuilding(useOfBuilding);
    setNumberOfFloors(numberOfFloors);
  }

  /**
   * A method responsible for setting the size of the project. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
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
   * A method responsible for setting the number of floors. It checks if the input is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfFloors
   *        number of floors as an int
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfFloors(int numberOfFloors){
    if (numberOfFloors >= 0){
      this.numberOfFloors = numberOfFloors;
    }else throw new IllegalArgumentException("Invalid number of floors");
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
   * A method returning number of floors of the project.
   * @return an integer representing the number of floors.
   */
  public int getNumberOfFloors()
  {
    return numberOfFloors;
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
   * @return the Commercial project type as String.
   */
  public String toString()
  {
    return "id: " + super.getId();
  }
}
