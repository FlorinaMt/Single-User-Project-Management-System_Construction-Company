package model;

import java.util.List;

/**
 * A class extending Project that is representing one of the four types of the project.
 * @author Florina Mitigus
 */

public class Residential extends Project
{
  private double size;
  private int numberOfKitchens, numberOfBathrooms, numberOfOtherRoomsWithPlumbing;
  private boolean isNewBuild;
  /**
   * this static variable is returning the defaults used in view part of the system
   * */
  public final static String[] defaults = {"9", "1"};

  /**
   * A ten-argument constructor. Uses the checks from Project class constructor. Ensures no illegal values for size, number of kitchens, bathrooms and other rooms with plumbing in which case it throws an exception.
   * @param id
   *         the id of the project
   * @see Project
   * @param budget
   *        the budget as a double, that cannot be less than 0.
   * @see Project
   * @param startDate
   *        the start date of project
   * @see SimpleDate
   * @see Project
   * @param expectedDuration
   *        the expected duration of project as an Integer, that cannot be less than 0.
   * @see Project
   * @param resources
   *        the resources of the project
   * @see Project
   * @see Resources
   * @param size
   *        size of the building in m^2 as a double
   * @param numberOfKitchens
   *        number of kitchens of the project as an integer
   * @param numberOfBathrooms
   *        number of bathrooms of the project as an integer
   * @param numberOfOtherRoomsWithPlumbing
   *        number of other rooms with plumbing of the project as an integer
   * @param isNewBuild
   *        a boolean representing if the project is a new build rather than renovation
   */
  public Residential(String id, double budget, SimpleDate startDate, int expectedDuration, Resources resources, double size, int numberOfKitchens, int numberOfBathrooms, int numberOfOtherRoomsWithPlumbing, boolean isNewBuild)
  {
    super(id, budget, startDate, expectedDuration, resources, "Residential");
    setSize(size);
    setNumberOfKitchens(numberOfKitchens);
    setNumberOfBathrooms(numberOfBathrooms);
    setNumberOfOtherRoomsWithPlumbing(numberOfOtherRoomsWithPlumbing);
    setAsNewBuildOrRenovation(isNewBuild);
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
   *  A method responsible for setting the size of the project. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param size
   *        size of the building in m^2 as a double
   */
  public void setSize(double size)
  {
    if(size>0)
      this.size=size;
    else throw new IllegalArgumentException("Invalid size.");
  }
  /**
   * A method returning the number of bathrooms of the project.
   * @return an integer representing number of bathrooms of the project.
   */
  public int getNumberOfBathrooms()
  {
    return numberOfBathrooms;
  }
  /**
   * A method returning the number of kitchens of the project.
   * @return an integer representing number of kitchens of the project.
   */

  public int getNumberOfKitchens()
  {
    return numberOfKitchens;
  }
  /**
   * A method returning the number of other rooms with plumbing of the project.
   * @return an integer representing number of other rooms with plumbing of the project.
   */
  public int getNumberOfOtherRoomsWithPlumbing()
  {
    return numberOfOtherRoomsWithPlumbing;
  }

  /**
   * A method responsible for setting the number of bathrooms. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfBathrooms
   *        an integer representing the number of bathrooms
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfBathrooms(int numberOfBathrooms)
  {
    if(numberOfBathrooms>=0)
      this.numberOfBathrooms=numberOfBathrooms;
    else throw new IllegalArgumentException("Invalid number of bathrooms.");
  }

  /**
   * A method responsible for setting the number of other rooms with plumbing. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfOtherRoomsWithPlumbing
   *        an integer representing the number of other rooms with plumbing
   * @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfOtherRoomsWithPlumbing(int numberOfOtherRoomsWithPlumbing)
  {
    if(numberOfOtherRoomsWithPlumbing>=0)
      this.numberOfOtherRoomsWithPlumbing = numberOfOtherRoomsWithPlumbing;
    else throw new IllegalArgumentException("Invalid number of rooms with plumbing.");
  }

  /**
   * A method responsible for setting the number of kitchens. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param numberOfKitchens
   *        an integer representing the number of kitchens
   *        @throws IllegalArgumentException --> for invalid input throws an exception
   */
  public void setNumberOfKitchens(int numberOfKitchens)
  {
    if(numberOfKitchens>=0)
      this.numberOfKitchens = numberOfKitchens;
    else throw new IllegalArgumentException("Invalid number of kitchens.");
  }

  /**
   * A method returning the value of the boolean representing if the project is a new build.
   * @return the boolean representing if the project is a new build.
   */
  public boolean getIsNewBuild()
  {
    return isNewBuild;
  }

  /**
   * A method responsible for setting the boolean representing if the project is a new build.
   * @param isNewBuild
   *        the boolean representing if the project is a new build
   */
  public void setAsNewBuildOrRenovation(boolean isNewBuild)
  {
    this.isNewBuild = isNewBuild;
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
   * @param numberOfBathrooms
   *        allows user to change the number of bathrooms, an integer
   * @param numberOfKitchens
   *        allows user to change the number of kitchens, an integer
   * @param numberOfOtherRoomsWithPlumbing
   *        allows user to change the number of other rooms with plumbing, an integer
   * @param resources
   *        allows user to edit the resources of the project, Resource class
   * @see Project
   * @see Resources
   */
  public void edit(double budget, int expectedDuration, boolean isNotBehindTheSchedule, boolean isOngoing, int numberOfBathrooms, int numberOfKitchens, int numberOfOtherRoomsWithPlumbing,  Resources resources)
  {
    super.setBudget(budget);
    super.setIsNotBehindTheSchedule(isNotBehindTheSchedule);
    super.setIsOngoing(isOngoing);
    super.setExpectedDurationInMonths(expectedDuration);
    super.setResources(resources);
    setAsNewBuildOrRenovation(isNewBuild);
    setNumberOfBathrooms(numberOfBathrooms);
    setNumberOfKitchens(numberOfKitchens);
    setNumberOfOtherRoomsWithPlumbing(numberOfOtherRoomsWithPlumbing);
  }

  /**
   * A to String method returning Residential project to String.
   * @return Residential project to String.
   */
  public String toString()
  {
    return "id: " + super.getId();
  }

}
