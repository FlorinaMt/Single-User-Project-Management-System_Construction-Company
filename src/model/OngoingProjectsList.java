package model;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * A class extending ProjectsList representing the ongoing projects.
 * @author Florina Mitigus
 */
public class OngoingProjectsList extends ProjectsList
{
  private ArrayList<Project> projects;
  /**
   * No arguments constructor
   */
  public OngoingProjectsList()
  {
    super();
  }
  /**
   * A method used to get projects by their status.
   * @param isNotBehindTheSchedule choosing to get the projects with either behind or not behind the schedule
   * @see Project
   *        a bolean representing if project is not behind schedule
   * @param projects
   *        the list of the projects on which the search is conducted
   * @return an array of projects that contains the results of the search.
   */
  public Project[] getProjectsByStatus(boolean isNotBehindTheSchedule, Project[] projects)
  {
    return Arrays.stream(projects).filter(project -> project.getIsNotBehindTheSchedule()==isNotBehindTheSchedule).toArray(Project[]::new);
  }
}
