package model;

import java.util.ArrayDeque;
import java.util.Deque;
/**
 * A class that's a part of the Project class representing and keeping the resources of the project.
 * @author Florina Mitigus
 */
public class Resources
{
  private Deque<String> updates;
  private int estimatedTotalHours;
  private double expectedExpenses, materialsExpenses, manHoursUsed, salaryExpenses;
  /**
   * A two-argument constructor. It allows user to set his estimates for the project. Ensures no illegal values estimated total hours and expected expenses in which case it throws an exception.
   * @param expectedExpenses
   *        a double representing users expectations of the expenses
   * @param estimatedTotalHours
   *        an integer representing users estimates of the duration
   */
  public Resources(double expectedExpenses, int estimatedTotalHours)
  {
    setExpectedExpenses(expectedExpenses);
    setEstimatedTotalHours(estimatedTotalHours);
    updates=new ArrayDeque<>();
  }
  /**
   * A method responsible for setting the estimated total hours of the project. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param estimatedTotalHours new value for estimated total Hours
   */
  public void setEstimatedTotalHours(int estimatedTotalHours)
  {
    if(estimatedTotalHours>0) this.estimatedTotalHours = estimatedTotalHours;
    else throw new IllegalArgumentException("Invalid number of estimated hours.");
  }
  /**
   * A method responsible for setting the expected total expenses of the project. It checks if the size is > 0 in which case the value is assigned, otherwise throws an exception.
   * @param expectedExpenses new value for expected expenses
   */
  public void setExpectedExpenses(double expectedExpenses)
  {
    if(expectedExpenses>0)
     this.expectedExpenses = expectedExpenses;
    else throw new IllegalArgumentException("Invalid value for the expected expenses field.");
  }
  /**
   * A method used to set the man hours used on the project.
   * @param manHoursUsed
   *        an integer representing the man hours used on the project
   */
  public void setManHours(double manHoursUsed){this.manHoursUsed = manHoursUsed;}
  /**
   * A method used to set the salary expenses used on the project.
   * @param salaryExpenses
   *        a double representing the salary expenses used on the project
   */
  public void setSalaryExpenses(double salaryExpenses){this.salaryExpenses = salaryExpenses;}
  /**
   * A method used to set the material expenses used on the project.
   * @param materialsExpenses
   *        a double representing the material expenses used on the project
   */
  public void setMaterialsExpenses(double materialsExpenses){
    this.materialsExpenses = materialsExpenses;
  }
  /**
   * A method returning the expected expenses of the project.
   * @return expected expenses of the project as a double.
   */
  public double getExpectedExpenses()
  {
    return expectedExpenses;
  }
  /**
   * A method returning the man hours used on the project.
   * @return the man hours used on the project as an integer
   */
  public double getManHoursUsed()
  {
    return manHoursUsed;
  }
  /**
   * A method returning the material expenses used on the project.
   * @return material expenses used on the project as a double.
   */
  public double getMaterialsExpenses()
  {
    return materialsExpenses;
  }
  /**
   * A method returning the total expenses used on the project.
   * @return total expenses used on the project as a double.
   */
  public double getTotalExpenses()
  {
    return materialsExpenses+salaryExpenses;
  }

  /**
   * A method returning the salary expenses used on the project.
   * @return salary expenses used on the project as a double.
   */
  public double getSalaryExpenses()
  {
    return salaryExpenses;
  }

  /**
   * A method returning the estimated total hours of the project.
   * @return the estimated total hours of the project as an integer
   */
  public int getEstimatedTotalHours()
  {
    return estimatedTotalHours;
  }

  /**
   * A method returning the all the Updates of the project.
   * @return A queue of a updates as String objects
   */
  public Deque<String> getUpdates(){return this.updates;}
  /**
   * A method used to set the queue of updates instead of adding to it
   * @param updates
   *        Queue object with all the updates stored in
   */
  public void setUpdates(Deque<String> updates){this.updates = updates;}


  /**
   * A method controlling the size of the queue of updates of the resources.
   * @return a boolean if the queue is full.
   */
  public boolean queueIsFull()
  {
    if(updates.size()>=12)//1 comparison
      return true;//this also takes 1
    return false;//and 1 return statement
    //=> At most 2 steps => O(1)
  }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
  This method updates the worked hours, salary expenses and materials expenses using the
  values found in the update and display all updates as strings in the queue, as long as the
  queue is not full
   */
  /**
   * A method used to update the resources of the project. It also updates the queue of updates.
   * @param update
   *        an Update class containing the values of the update
   * @see Update
   */
  public void updateResources(Update update)
  {
    //If this field is not 0
    if(update.getManHoursUsedToday()!=0)//1 variable accessed and 1 comparison
    {
      //We prepare to add it in the queue, by removing the older updates
      if(queueIsFull())//constant time - at most 2 steps
        updates.pop();//independent of the queue size - constant time

      //We update the value
      //1 assignment, 1 addition, 1 variable accessed = 3
      manHoursUsed = manHoursUsed + update.getManHoursUsedToday();

      //And add the update's information to the queue
      //offer() - independent of the queue size => constant time
      //3 variable accessed
      updates.offer(update.getCurrentDate() + " | Hours used: " + update.getManHoursUsedToday() + " hours; " + update.getManHoursUsedTodayNote());
    }
    //If this field is not 0
    if(update.getSalaryToday()!=0)//1 variable accessed and 1 comparison
    {
      //We prepare to add it in the queue, by removing the older updates
      if(queueIsFull())//constant time - at most 2 steps
        updates.pop();//independent of the queue size - constant time

      //We update the value
      //1 assignment, 1 addition, 1 variable accessed = 3
      salaryExpenses=salaryExpenses+update.getSalaryToday();

      //And add the update's information to the queue
      //offer() - independent of the queue size => constant time
      //3 variable accessed
      updates.offer(update.getCurrentDate() + " | Salary expenses: " + update.getSalaryToday() + " $; " + update.getSalaryNote());
    }
    //If this field is not 0
    if(update.getMaterialsExpensesToday()!=0)//1 variable accessed and 1 comparison
    {
      //We prepare to add it in the queue, by removing the older updates
      if(queueIsFull())//constant time - at most 2 steps
        updates.pop();//independent of the queue size - constant time

      //We update the value
      //1 assignment, 1 addition, 1 variable accessed = 3
      materialsExpenses = materialsExpenses + update.getMaterialsExpensesToday();

      //And add the update's information to the queue
      //offer() - independent of the queue size => constant time
      //3 variable accessed
      updates.offer(update.getCurrentDate() + " | Materials expenses: " + update.getMaterialsExpensesToday() + " $; " + update.getMaterialsExpensesTodayNote() );
    }
  }

  /*
  T(n)=(1+2+1+3+1+3)*3=33 steps => O(1)
  We chose this method because the queue is a very efficient data structure to store information,
  allowing us to easily remove the first element
   */
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * a to String method, returning the list of updates.
   * @return the updates queue as a String.
   */
  @Override
  public String toString()
  {
    String s="";
    for(String i:updates)
      s=s + i + "\n";
    return s;
  }

  /**
   * A method use to create a copy of resources.
   * @return a copy of resources as new Resources class.
   */
  public Resources copy()   { return new Resources(getExpectedExpenses(), getEstimatedTotalHours());  }

}
