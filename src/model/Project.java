package model;

import java.util.ArrayList;
/**
 * An abstract class representing shared qualities of project types
 *
 * @author Florina Mitigus
 * @author Michael Barczuk
 * version 2.0 -December 5
 */
public abstract class Project
{
  private boolean isNotBehindTheSchedule;
  private boolean isOngoing;
  private double budget;
  private int expectedDurationInMonths;
  private String id;
  private String type;
  private SimpleDate startDate;
  private Resources resources;
  /**
   * list of all projects ids which every project keeps track off so there are no duplicates
   * */
  public static ArrayList<String> ids = new ArrayList<>();
  /**
   * Six-argument constructor. Assigns the necessary values given by the user, also sets the project automatically as ongoing and not behind schedule.
   *When setting the id it checks
   * if it's already in use in other projects
   * if so an exception is thrown, same case
   * if the budget is value is less than 0 and
   * if expected duration in months is less than 0.
   * @param id
   *          the id of the project
   * @param budget
   *          the budget as a double, that cannot be less than 0.
   * @param startDate
   *          the start date of project
   * @see SimpleDate
   * @param expectedDurationInMonths
   *          the expected duration of project as an Integer, that cannot be less than 0.
   * @param resources
   *          the resources of the project
   * @see Resources
   * @param type
   *          the type of the project in question: Commercial, Road Construction, Residential or Industrial.
   */
  public Project(String id, double budget, SimpleDate startDate,
      int expectedDurationInMonths, Resources resources, String type)
  {
    setId(id);
    setBudget(budget);
    this.startDate = startDate; //  Add copy
    setExpectedDurationInMonths(expectedDurationInMonths);
    this.resources = resources.copy();  //  Add copy;
    this.isOngoing = true;
    this.isNotBehindTheSchedule = true;
    this.type = type;
  }

  /**
   * A method responsible for setting/changing the status of the project as either behind or not behind schedule.
   * @param status
   *          a boolean representing if project is not behind schedule
   */
  public void setIsNotBehindTheSchedule(boolean status)
  {
    this.isNotBehindTheSchedule = status;
  }
  /**
   * A method responsible for setting the id of the project. It makes sure the id is not already used in other project by checking the static array list. In case of it being in use it throws an exception.
   * It also ensures the id is not empty and not longer than 24 characters. In both cases it will throw exceptions if the conditions are not met, otherwise the value is assigned.
   * @param id
   *         a string that is the id of the project
   */
  public void setId(String id)
  {
    if (ids.contains(id))
    {
      throw new IllegalArgumentException("ID in use already");
    }
    else if (id.isEmpty()){
      throw new IllegalArgumentException("ID cannot be empty");
    }
    else if(id.length() > 25){
      throw new IllegalArgumentException("ID can be maximum of 25 characters");
    }
    else
      this.id = id;
    ids.add(id);
  }
  /**
   *A method used to change if the project is still ongoing or if it has been completed.
   * @param isGoing
   *          a boolean representing if the project is ongoing
   */
  public void setIsOngoing(boolean isGoing)
  {
    this.isOngoing = isGoing;
  }
  /**
   * A method used to set/change the expected duration in months of the project. It ensures that the value given is > 0 in which case the value is assigned, otherwise it throws an exception.
   * @param newExpectedDurationInMonths
   *        the amount of months that the project is expected to take as an Integer
   */
  public void setExpectedDurationInMonths(int newExpectedDurationInMonths)
  {
    if (newExpectedDurationInMonths >= 0)
    {
      this.expectedDurationInMonths = newExpectedDurationInMonths;
    }
    else
      throw new IllegalArgumentException("Duration cannot be negative.");
  }
  /**
   * A method used to set/change the budget of the project. It ensures that the value is > 0 in which case the value is assigned, otherwise it throws an exception.
   * @param newBudget
   *        the budget of the project as a double
   */
  public void setBudget(double newBudget)
  {
    if (newBudget > 0)
    {
      this.budget = newBudget;
    }
    else
      throw new IllegalArgumentException("Budget must be larger than 0.");
  }
  /**
   * A method used to assign resources to the project. It ensures the resources are not empty in which case it throws an exception, otherwise the value is assigned.
   * @param resources
   *        resources of the project as a different java class of its own
   * @see Resources
   */
  public void setResources(Resources resources)
  {
    if (resources != null)
    {
      this.resources = resources.copy();
    }
    else
      throw new IllegalArgumentException("Invalid resources");
  }
  /**
   * A method returning the value of the budget of the project.
   * @return budget as a double.
   */
  public double getBudget()
  {
    return budget;
  }
  /**
   * A method returning a copy of the start date due to the composition.
   * @return a copy of SimpleDate class that represents the start date of the project.
   */
  public SimpleDate getStartDate()
  {
    return new SimpleDate(startDate.getDay(),startDate.getMonth(),startDate.getYear());
    //T(n)=1+1+1=3 => O(1)
  }
  /**
   * A method used to remove the id from the array list of ids that are in use upon the deletion of the project.
   */
  public void deleteId()
  {
    ids.remove(this.getId());
  }
  /**
   * A method returning the type of the project.
   * @return a String representing the type of the project.
   */
  public String getType()
  {
    return type;
  }
  /**
   * A method returning the id of the project.
   * @return the id as a String.
   */
  public String getId()
  {
    return id;
  }

  /**
   * A method returning if the project is not behind schedule.
   * @return a boolean representing if the project is not behind schedule.
   */
  public boolean getIsNotBehindTheSchedule(){return isNotBehindTheSchedule;}
  /**
   * A method that returns the status(if it's behind schedule) of the project depending on if it is completed or ongoing.
   * @return depending on if the project is ongoing or completed as well as if it's not behind schedule returns a corresponding String.
   */
  public String getStatus()
  {
    if (isNotBehindTheSchedule && isOngoing)
    {
      return "Is Not Behind The Schedule";
    }
    else if (!isNotBehindTheSchedule && isOngoing)
    {
      return "Is Behind The Schedule";
    }
    else if (isNotBehindTheSchedule && !isOngoing)
    {
      return "Was Not Behind The Schedule";
    }
    else
    {
      return "Was Behind The Schedule";
    }
  }
  /**
   * A method returning resources of the project.
   * @return the resources of the project as Resources class.
   */
  public Resources getResources()
  {
    return resources;
  }
  /**
   * A method returning if the project is ongoing.
   * @return a bolean representing if the project is ongoing.
   */
  public boolean isOngoing()
  {
    return isOngoing;
  }



  /**
   * A method returning the expected duration of the project in months.
   * @return the expected duration in months as an Integer.
   */
  public int getExpectedDurationInMonths()
  {
    return expectedDurationInMonths;
  }
  /**
   * A to string method.
   * @return the project to string.
   */
  public String toString()
  {
    return isOngoing + " " + id + " " + budget + " " + startDate + " "
        + expectedDurationInMonths + " " + resources + " " + isNotBehindTheSchedule;
  }
}