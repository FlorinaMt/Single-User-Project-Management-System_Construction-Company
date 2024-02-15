package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
/**
 * An abstract class representing shared qualities of project list types.
 * @author Florina Mitigus
 */
public abstract class ProjectsList
{
  private ArrayList<Project> projects;
  /**
   * No argument constructor creating new array list.
   */
  public ProjectsList()
  {
    super();
    this.projects=new ArrayList<>();
  }
  /**
   * A method responsible for adding new Project to the list of projects.
   * @param project
   *        a project class of one of the four types to be added to the list
   * @see Project
   */
  public void addProject(Project project)
  {
    projects.add(project);
  }
  /**
   * A method responsible for deleting a Project from the list of projects. It also frees up the id utilised by Project in question.
   * @param project
   *        a project class of one of the four types to be deleted from the list
   */
  public void deleteProject(Project project)
  {
    project.deleteId();
    projects.remove(project);
  }
  /**
   * A method used to search for the project by id.
   * @param id
   *        a string representing id of the Project
   * @see Project
   * @return a Project with matching id from projects list.
   */
  public Project getProjectById(String id)
  {
    for(int i=0; i<getSize(); i++)
      if(projects.get(i).getId().equals(id))
        return projects.get(i);
    return null;
  }
  /**
   * A method used to remove project from the projects list. Does not delete id. This method is used to move projects between the ongoing and completed list.
   * @param project
   *        a project class of one of the four types to be removed from the list
   * @see Project
   */
  public void removeProject(Project project){
    projects.remove(project);
  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
  This method takes as parameters an array of projects, two dates and a string that
  specify an order {Ascending, Descending} and returns all projects that have their
  start date in the interval [startDate1; startDate2], in the specified order, in a new array
  */
  /**
   * This method takes as parameters an array of projects, two dates and a string that
   *   specify an order {Ascending, Descending} and returns all projects that have their
   *   start date in the interval [startDate1; startDate2], in the specified order, in a new array
   * @param startDate1
   *        a date from which we search from
   * @see SimpleDate
   * @param startDate2
   *        a date to which we search to
   * @see SimpleDate
   * @param order
   *        an order of display of the results
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   */
  public Project[] getProjectsStartedBetween(SimpleDate startDate1, SimpleDate startDate2, String order, Project[] projects)
  {
    //We create a new array that contains all the projects that respect the date range condition
    /*
    Initialization - 1 step
    The stream().filter() methods involve iterating through the array of projects once and applying
    the date range condition => n iterations, where n is the number of projects
    */
    Project[] filteredProjects = Arrays.stream(projects).filter(project ->
        // isBefore() and isAfter() methods have a constant time complexity, both taking up to 4 steps (see SimpleDate class)
        //getStartDate() has a constant time complexity (3 steps)
        /*
        toArray() method creates the new array and copies the elements from the stream into that array =>
        n steps, where n is the number of projects that respect the date range condition
        */
    startDate1.isBefore(project.getStartDate()) && startDate2.isAfter(project.getStartDate())).toArray(Project[]::new);

    //We sort the projects according to the specified order
    if(order.equals("Ascending"))//1 comparison
     Arrays.sort(filteredProjects, Comparator.comparing(Project::getStartDate));
    else
      //sort() method takes n*log n iterations, where n is the number of projects that respect the date range condition
      //reversed() method has a constant time complexity, because it simply changes the sorting order
      Arrays.sort(filteredProjects, Comparator.comparing(Project::getStartDate).reversed());

    //And return the new array
    return filteredProjects;//1 return statement
    /*
    T(n)= 1 + n + 4 + 4 + 3 + n + 1 + (n*log n) + 1 + 1 = 15 + 2n + (n*log n)
    Using the Master Theorem and ignoring the constants and coefficients, we get O(n)=n*log n,
    where n is the number of projects that have to be sorted.
     */
    /*
    We chose this method since it is efficient, especially for large projects arrays,
    where the sorting section dominates the complexity
     */
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * A method used to search projects by their type. It takes the selected types by which it searches and returns the results.
   * @param selectedTypes
   *        a string of selected types by which to search
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   */
  public Project[] getProjectsByType(String selectedTypes, Project[] projects)
  {
    return Arrays.stream(projects).filter(project -> selectedTypes.contains(project.getType())).toArray(Project[]::new);
  }
  /**
   * A method used to search projects by their budget. It takes the budget range in which it searches and returns the results.
   * @param budget1
   *        a double representing a budget from which we search from
   * @see Project
   * @param budget2
   * a double representing a budget to which we search to
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   */
  public Project[] getProjectsByBudget(double budget1, double budget2, Project[] projects)
  {
    return Arrays.stream(projects).filter(project -> project.getBudget()>=budget1 && project.getBudget()<=budget2).toArray(Project[]::new);
  }
  /**
   * A method used to search projects by their duration in months. It takes the months range in which it searches and returns the results.
   * @param duration1
   *        an integer representing a duration from which we search from
   * @see Project
   * @param duration2
   *        an integer representing a duration to which we search to
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   */
  public Project[] getProjectsByDuration(double duration1, double duration2, Project[] projects)
  {
    return Arrays.stream(projects).filter(project -> project.getExpectedDurationInMonths()>=duration1 && project.getExpectedDurationInMonths()<=duration2).toArray(Project[]::new);
  }

  /**
   * A method used to get project by its index.
   * @param index
   *        an integer representing the index of the project
   * @return the project with matching index.
   */
  public Project getProject(int index)
  {
    return projects.get(index);
  }
  /**
   * A method used to get the size of the projects array list.
   * @return an integer representing the size of the array list.
   */
  public int getSize()
  {
    return projects.size();
  }

}
