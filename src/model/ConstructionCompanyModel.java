package model;
/**
 * An interface class for the Construction company's GUI.
 *
 * @author Florina Mitigus
 */
public interface ConstructionCompanyModel
{
  /**
   * The interface implementation of searching for project by its index.
   *
   * @param index an integer representing the index of the project
   * @return the project of the matching index.
   * @see ProjectsList
   */
  Project getProject(int index);
  /**
   * The interface implementation for adding project to ongoing projects.
   *
   * @param project
   *        the project to add to ongoing
   * @see ProjectsList
   */
  void addToOngoingProjects(Project project);
  /**
   * The interface implementation for adding project to completed projects.
   *
   * @param project
   *        the project to add to completed
   * @see ProjectsList
   */
  void addToCompletedProjects(Project project);
  /**
   * The interface implementation for deleting the project from ongoing.
   *
   * @param project
   *        the project to delete from ongoing
   * @see ProjectsList
   */
  void deleteOngoingProject(Project project);
  /**
   * The interface implementation for deleting the project from completed.
   *
   * @param project
   *        the project to delete from completed
   * @see ProjectsList
   */
  void deleteCompletedProject(Project project);
  /**
   * The interface implementation of searching for project by its id.
   *
   * @param id a String representing the id of the project
   * @return the project of the matching id.
   * @see ProjectsList
   */
  Project getProjectById(String id);
  /**
   * The interface implementation of searching by date.
   * This method takes as parameters an array of projects, two dates and a string that
   * specify an order {Ascending, Descending} and returns all projects that have their
   *   start date in the interval [startDate1; startDate2], in the specified order, in a new array
   *  @param startDate1
   *         a date from which we search from
   *  @see SimpleDate
   *  @param startDate2
   *         a date to which we search to
   *  @see SimpleDate
   *  @param order
   *         an order of display of the results
   *  @param projects
   *         the list of the projects on which the search is conducted
   *  @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  Project[] getOngoingProjectsStartedBetween(SimpleDate startDate1, SimpleDate startDate2, String order, Project[] projects);
  /**
   * the interface implementation of searching by type in ongoing projects.
   * A method used to search projects by their type. It takes the selected types by which it searches and returns the results.
   * @param selectedTypes currently selected types in a form of string
   * @see Project
   *        a string of selected types by which to search
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  Project[] getOngoingProjectsByType(String selectedTypes, Project[] projects);
  /**
   * the interface implementation of searching by type in completed projects.
   * A method used to search projects by their type. It takes the selected types by which it searches and returns the results.
   * @param selectedTypes
   *        a string of selected types by which to search
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  Project[] getCompletedProjectsByType(String selectedTypes, Project[] projects);
  /**
   * The interface implementation of searching by budget.
   * A method used to search projects by their budget. It takes the budget range in which it searches and returns the results.
   * @param budget1
   *        a double representing a budget from which we search from
   * @see Project
   * @param budget2
   *        a double representing a budget to which we search to
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  Project[] getOngoingProjectsByBudget(double budget1, double budget2, Project[] projects);
  /**
   * The interface implementation of search by duration.
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
   * @see ProjectsList
   */
  Project[] getOngoingProjectsByDuration(int duration1, int duration2, Project[] projects);

  /**
   * The interface implementation of searching project by its status.
   * This method takes in a boolean representing if the project is not behind schedule and returns projects that have the matching status.
   * @param isNotBehindSchedule
   *        a boolean representing if the project is not behind schedule
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  Project[] getProjectsByStatus(boolean isNotBehindSchedule, Project[] projects);
  /**
   * The interface implementation of getting the average time spent on the project.
   * A method that calculates average duration of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @return double representing the average duration for different project types.
   * @see ProjectsList
   */
  double getAverageTimeSpent(String type);
  /**
   * The interface implementation of getting the average expenses.
   * A method that calculates average expenses of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @return double representing the average expenses for different project types.
   * @see CompletedProjectsList
   */
  double getAverageExpenses(String type);
  /**
   *  The interface implementation of updating the resources by sending it an update object
   * @param update an update object with values that are meant to be added to resources
   * @param project a project which the update is assigned
   * @see Update
   * @see Resources
   */
  void updateResources(Update update, Project project);
  /**
   * The interface implementation of getting number of ongoing projects.
   * A method returning the number of ongoing projects as an integer
   * @return number of ongoing projects as an integer.
   * @see Update
   * @see Resources
   */
  int getNumberOfOngoingProjects();
  /**
   * The interface implementation of getting number of ongoing projects.
   * A method returning the number of completed projects as an integer
   * @return number of completed projects as an integer.
   */
  int getNumberOfCompletedProjects();
  /**
   * The interface implementation of getting all ongoing projects.
   * A method returning the array of all ongoing projects.
   * @return the array of all ongoing projects.
   */
  Project[] getAllOngoingProjects();
  /**
   * The interface implementation of getting all completed projects.
   * A method returning the array of all ongoing projects.
   * @return the array of all completed projects.
   */
  Project[] getAllCompletedProjects();
  /**
   * The interface implementation of saving Ongoing and Completed
   * projects list as well as list of updates of each individual project
   *
   * @see FilePersistence
   */
  void saveProjects();

}
