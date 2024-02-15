package model;

/**
 * A class extending Project that is representing one of the four types of the project.
 * @author Florina Mitigus
 */
public class RoadConstruction extends Project
{
  private double length, width;
  private int numberOfBridges, numberOfTunnels;
  private String environmentalChallenges;
  /**
   * this static variable is returning the defaults used in view part of the system
   * */
  public final static String[] defaults = {"18", "0", "none"};

  /**
   * A ten-argument constructor. Uses the checks from Project class constructor. Ensures no illegal values for length, width, number of tunnels and bridges in which case it throws an exception.
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
   * @param length
   *        length of the road as a double
   * @param width
   *        width of the road as a double
   * @param numberOfBridges
   *        number of bridges as an integer
   * @param numberOfTunnels
   *        number of tunnels as an integer
   * @param environmentalChallenges
   *        environmental challenges as a String it can be anything, because of broad definition no limitations are imposed
   */
  public RoadConstruction(String id, double budget, SimpleDate startDate, int expectedDuration, Resources resources, double length, double width, int numberOfBridges, int numberOfTunnels, String environmentalChallenges)
  {
    super(id, budget, startDate, expectedDuration, resources, "Road Construction");
    setLength(length);
    setWidth(width);
    setEnvironmentalChallenges(environmentalChallenges
    );
    setNumberOfBridges(numberOfBridges);
    setNumberOfTunnels(numberOfTunnels);
  }

  /**
   * A method that allows the user to edit(change) some values of the project after creating it in the system.
   * @param budget
   *        a new budget as a double
   * @see Project
   * @param expectedDuration
   *        a new expected duration as an integer
   * @see Project
   * @param isNotBehindTheSchedule
   *        allows user to change if the project is behind schedule or not, a boolean
   * @see Project
   * @param isOngoing
   *        allows user to mark project as completed or as ongoing again, a boolean
   * @see Project
   * @param length
   *        allows user to change the length of the road, a double
   * @param width
   *        allows user to change the width of the road, a double
   * @param numberOfBridges
   *        allows user to change the number of bridges of the road, an integer
   * @param numberOfTunnels
   *        allows user to change the number of tunnels of the road, an integer
   * @param environmentalChallenges
   *        allows user to change the environmental challenges of the road construction, a String
   * @param resources
   *        allows user to edit the resources of the project, Resource class
   * @see Project
   * @see Resources
   */
  public void edit(double budget, int expectedDuration, boolean isNotBehindTheSchedule, boolean isOngoing, double length, double width, int numberOfBridges, int numberOfTunnels, String environmentalChallenges, Resources resources)
  {
    super.setBudget(budget);
    super.setIsNotBehindTheSchedule(isNotBehindTheSchedule);
    super.setIsOngoing(isOngoing);
    super.setExpectedDurationInMonths(expectedDuration);
    super.setResources(resources);
    setLength(length);
    setWidth(width);
    setEnvironmentalChallenges(environmentalChallenges);
    setNumberOfBridges(numberOfBridges);
    setNumberOfTunnels(numberOfTunnels);
  }

  /**
   * A method responsible for setting the length of the road. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param length
   *        length of the road as a double
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setLength(double length)
  {
    if(length>0)
      this.length=length;
    else throw new IllegalArgumentException("Invalid length");
  }
  /**
   * A method returning the length of the road.
   * @return a double representing length of the road.
   */
  public double getLength()
  {
    return length;
  }

  /**
   * A method responsible for setting the width of the road. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param width
   *        width of the road as a double
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setWidth(double width)
  {
    if(width>0)
      this.width = width;
    else throw new IllegalArgumentException("Invalid width");
  }
  /**
   * A method returning the width of the road.
   * @return a double representing width of the road.
   */
  public double getWidth()
  {
    return width;
  }

  /**
   * A method responsible for setting the number of bridges of the road. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfBridges
   *        number of bridges as an integer
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfBridges(int numberOfBridges)
  {
    if(numberOfBridges>=0)
      this.numberOfBridges=numberOfBridges;
    else throw new IllegalArgumentException("Invalid number of bridges");
  }
  /**
   * A method returning the number of bridges of the road.
   * @return an integer representing number of bridges of the road.
   */
  public int getNumberOfBridges()
  {
    return numberOfBridges;
  }

  /**
   * A method responsible for setting the number of tunnels of the road. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfTunnels
   *        number of tunnels as an integer
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfTunnels(int numberOfTunnels)
  {
    if(numberOfTunnels>=0)
      this.numberOfTunnels = numberOfTunnels;
    else throw new IllegalArgumentException("Invalid number of tunnels");
  }

  /**
   * A method returning the number of tunnels of the road.
   * @return an integer representing number of tunnels of the road.
   */
  public int getNumberOfTunnels()
  {
    return numberOfTunnels;
  }

  /**
   * A method responsible for setting the environmental challenges of the road construction. Due to broad range of possible inputs no checks here.
   * @param environmentalChallenges
   *        a String representing environmental challenges of the road construction
   */
  public void setEnvironmentalChallenges(String environmentalChallenges)
  {
    if(environmentalChallenges!=null)
      this.environmentalChallenges=environmentalChallenges;
    else this.environmentalChallenges="none";
  }
  /**
   * A method returning the environmental challenges of the road construction.
   * @return a String representing environmental challenges of the road construction.
   */
  public String getEnvironmentalChallenges()
  {
    return environmentalChallenges;
  }
  /**
   * A to String method.
   * @return the Road Construction project type as String.
   */
  public String toString()
  {
    return "id: " + super.getId();
  }
}
