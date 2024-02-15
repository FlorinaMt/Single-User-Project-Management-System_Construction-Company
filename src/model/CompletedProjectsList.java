package model;

import java.util.ArrayList;

/**
 * A class extending ProjectsList representing the completed projects.
 * @author Florina Mitigus
 */

public class CompletedProjectsList extends ProjectsList
{
  /**
   * No arguments constructor
   */
  public CompletedProjectsList()
  {
    super();
  }
  /**
   * A method that calculates average duration of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @see Project
   * @return doubles representing the average duration for different project types.
   */
  public double getAverageTimeSpent(String type)
  {
    double time=0;
    int count=0;
    for(int i=0; i<getSize(); i++)
      if(super.getProject(i).getType().equals(type))
      {
        time=time+super.getProject(i).getExpectedDurationInMonths();
        count++;
      }
    return time/count;
  }

  /**
   * A method that calculates average expenses of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @see Project
   * @return doubles representing the average expenses for different project types.
   */
  public double getAverageExpenses(String type)
  {
    double expenses=0;
    int count=0;
    for(int i=0; i<getSize(); i++)
      if(super.getProject(i).getType().equals(type))
      {
        expenses=expenses+super.getProject(i).getResources().getTotalExpenses();
        count++;
      }
    return expenses/count;
  }


}
