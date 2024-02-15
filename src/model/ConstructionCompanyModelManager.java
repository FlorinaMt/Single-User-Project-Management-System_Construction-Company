package model;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
/**
 * A manager class for the interface.
 * @author Florina Mitigus
 * @author Matej Palas
 */
public class ConstructionCompanyModelManager implements ConstructionCompanyModel
{
  private CompletedProjectsList completed;
  private OngoingProjectsList ongoing;

  private FilePersistence files;
  /**
   * No argument constructor loads list of Ongoing and Completed projects
   * as well as updates for each individual ongoing project
   */
  public ConstructionCompanyModelManager()
  {
    files = new FilePersistence();

    //  Loading the lists from files
    ongoing=files.loadOnGoingFromTxt();
    completed=files.loadCompletedFromTxt();

    //  Loading updates for each individual project
    for (int i = 0; i < ongoing.getSize(); i++) {
      ongoing.getProject(i).getResources().setUpdates(files.loadUpdatesFromOngoing(ongoing.getProject(i)));
    }
  }

  /**
   * The interface manager implementation of searching for project by its index.
   *
   * @param index an integer representing the index of the project
   * @return the project of the matching index.
   * @see ProjectsList
   */
  @Override public Project getProject(int index)
  {
    if(ongoing.getProject(index)!=null)
      return ongoing.getProject(index);
    else if(completed.getProject(index)!=null)
      return completed.getProject(index);
    return null;
  }
  /**
   * The interface manager implementation for adding project to ongoing projects.
   *It removes the project form completed and adds to ongoing projects.
   * @param project the project to add to ongoing
   * @see ProjectsList
   */
  @Override public void addToOngoingProjects(Project project)
  {
    completed.removeProject(project);
    ongoing.addProject(project);
  }
  /**
   * The interface implementation for adding project to completed projects.
   *It removes the project from ongoing and adds it to completed projects.
   * @param project the project to add to completed
   * @see ProjectsList
   */
  @Override public void addToCompletedProjects(Project project)
  {
    ongoing.removeProject(project);
    completed.addProject(project);
  }
  /**
   * The interface manager implementation for deleting the project from ongoing.
   * A method that completely deletes the project from the system.
   * @param project
   *        the project to delete from ongoing
   * @see ProjectsList
   */
  @Override public void deleteOngoingProject(Project project)
  {
    ongoing.deleteProject(project);
  }
  /**
   * The interface manager implementation for deleting the project from completed.
   * A method that completely deletes the project from the system.
   * @param project
   *        the project to delete from completed
   * @see ProjectsList
   */
  @Override public void deleteCompletedProject(Project project)
  {
    completed.deleteProject(project);
  }
  /**
   * The interface manager implementation of searching for project by its id.
   *
   * @param id a String representing the id of the project
   * @return the project of the matching id.
   * @see ProjectsList
   */
  @Override public Project getProjectById(String id)
  {
    if(ongoing.getProjectById(id)!=null)
      return ongoing.getProjectById(id);
    else if(completed.getProjectById(id)!=null)
      return completed.getProjectById(id);
    return null;
  }
  /**
   * The interface manager implementation of searching by date.
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
  @Override public Project[] getOngoingProjectsStartedBetween(SimpleDate startDate1, SimpleDate startDate2, String order, Project[] projects)
  {
    return ongoing.getProjectsStartedBetween(startDate1, startDate2, order, projects);
  }
  /**
   * the interface manager implementation of searching by type in ongoing projects.
   * A method used to search projects by their type. It takes the selected types by which it searches and returns the results.
   * @param selectedTypes
   *        a string of selected types by which to search
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  @Override public Project[] getOngoingProjectsByType(String selectedTypes, Project[] projects)
  {
    return ongoing.getProjectsByType(selectedTypes, projects);
  }
  /**
   * the interface manager implementation of searching by type in completed projects.
   * A method used to search projects by their type. It takes the selected types by which it searches and returns the results.
   * @param selectedTypes
   *        a string of selected types by which to search
   * @see Project
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  @Override public Project[] getCompletedProjectsByType(String selectedTypes, Project[] projects)
  {
    return completed.getProjectsByType(selectedTypes, projects);
  }
  /**
   * The interface manager implementation of searching by budget.
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
  @Override public Project[] getOngoingProjectsByBudget(double budget1, double budget2, Project[] projects)
  {
    return ongoing.getProjectsByBudget(budget1, budget2, projects);
  }

  /**
   * The interface manager implementation of search by duration.
   * A method used to search projects by their duration in months.
   * It takes the months range in which it searches and returns the results.
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
  @Override public Project[] getOngoingProjectsByDuration(int duration1, int duration2, Project[] projects)
  {
    return ongoing.getProjectsByDuration(duration1, duration2, projects);
  }
  /**
   * The interface manager implementation of searching project by its status.
   * This method takes in a boolean representing if the project is not behind schedule and returns projects that have the matching status.
   * @param isNotBehindTheSchedule
   *        a boolean representing if the project is not behind schedule
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   * @see ProjectsList
   */
  @Override public Project[] getProjectsByStatus(boolean isNotBehindTheSchedule, Project[] projects)
  {
    return ongoing.getProjectsByStatus(isNotBehindTheSchedule, projects);
  }
  /**
   * The interface implementation of getting the average time spent on the project.
   * A method that calculates average duration of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @return doubles representing the average duration for different project types.
   * @see OngoingProjectsList
   */
  @Override public double getAverageTimeSpent(String type)
  {
    return completed.getAverageTimeSpent(type);
  }
  /**
   * The interface implementation of getting the average expenses.
   * A method that calculates average expenses of completed projects by type and returns them.
   * @param type
   *        the type of the project
   * @return doubles representing the average expenses for different project types.
   * @see CompletedProjectsList
   */
  @Override public double getAverageExpenses(String type)
  {
    return completed.getAverageExpenses(type);
  }
  /**
   * The interface implementation of the update resources method.
   * A method used to update the resources of the project. It also updates the queue of updates.
   * @param update
   *        an Update class containing the values of the update
   * @see Update
   * @see Resources
   */
  @Override public void updateResources(Update update, Project project)
  {
    project.getResources().updateResources(update);
  }
  /**
   * The interface implementation of getting number of all ongoing projects.
   * A method returning int value of all ongoing projects.
   * @return the whole number as integer of all ongoing projects.
   */
  @Override
  public int getNumberOfOngoingProjects(){return ongoing.getSize();}
  /**
   * The interface implementation of getting number completed projects.
   * A method returning int value of all completed projects.
   * @return the whole number as integer of all completed projects.
   */
  @Override
  public int getNumberOfCompletedProjects(){return completed.getSize();}
  /**
   * The interface implementation of getting all ongoing projects.
   * A method returning the array of all ongoing projects.
   * @return the array of all ongoing projects.
   */
  @Override public Project[] getAllOngoingProjects(){
    Project[] temp = new Project[getNumberOfOngoingProjects()];
    for (int i = 0; i < getNumberOfOngoingProjects(); i++) {
      temp[i] = ongoing.getProject(i);
    }
    return temp;
  }
  /**
   * The interface implementation of getting all completed projects.
   * A method returning the array of all completed projects.
   * @return the array of all completed projects.
   */
  @Override public Project[] getAllCompletedProjects(){
    Project[] temp = new Project[getNumberOfCompletedProjects()];
    for (int i = 0; i < getNumberOfCompletedProjects(); i++) {
      temp[i] = completed.getProject(i);
    }
    return temp;
  }
  /**
   * The interface implementation of saving Ongoing and Completed
   * projects list as well as list of updates of each individual project
   *
   * @see FilePersistence
   */
  @Override
  public void saveProjects(){
    //  Writing to a text file here
    files.writeToTextFile(ongoing, "files/ongoingProjects.txt");
    files.writeToTextFile(completed, "files/completedProjects.txt");
    files.saveAllUpdateLists(ongoing,"files/listOfUpdates.txt");

    //  Saving the xml files here
    try {
      files.saveXML(ongoing);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

}
