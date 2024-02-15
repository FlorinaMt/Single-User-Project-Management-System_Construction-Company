package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ConstructionCompanyModel;
import model.Project;
import model.SimpleDate;

import java.time.LocalDate;
/**
 * View model class for projects list
 * @author Michael Barczuk
 */
public class ProjectListViewModel
{
  private ObservableList<ProjectViewModel> list;
  private ConstructionCompanyModel model;
  /**
   * A one-argument constructor creating new observable array of type Project view model.
   * @param model
   *        the interface
   */
  public ProjectListViewModel(ConstructionCompanyModel model)
  {
    this.model = model;
    this.list = FXCollections.observableArrayList();
    updateOnGoing();
  }
  /**
   * A method returning the observable array of type Project view model.
   * @return the observable array of type Project view model.
   */
  public ObservableList<ProjectViewModel> getList()
  {
    return list;
  }
  /**
   * A method that clear the list(array) and assigns to it all ongoing projects
   * @return observable array with all ongoing projects
   */
  public Project[] updateOnGoing()
  {
    list.clear();
    Project[] temp = model.getAllOngoingProjects();
    for (int i = 0; i < temp.length; i++)
    {
      list.add(new ProjectViewModel(temp[i]));
    }
    return temp;
  }
  /**
   * A method that clear the list(array) and assigns to it all completed projects
   * @return observable array with all completed projects
   */
  public Project[] updateCompleted()
  {
    list.clear();
    Project[] temp = model.getAllCompletedProjects();
    for (int i = 0; i < temp.length; i++)
    {
      list.add(new ProjectViewModel(temp[i]));
    }
    return temp;
  }
  /**
   * A method used to remove the project from observable array.
   * @param project
   *        project to remove
   */
  public void remove(Project project)
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (list.get(i).getIdProperty().get().equals(project.getId()))
      {
        list.remove(i);
        break;
      }
    }
  }

  //SEARCH AND FILTERS STUFF///
  /**
   * Implementation of search by id for the display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view model with id property the same as the search and assigns it to the observable array.
   * @param search
   *        the id by which we search
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public void searchById(String search)
  {
    list.clear();
    Project temp = model.getProjectById(search);
    for (Project project : model.getAllOngoingProjects())
    {
      if (project.getId().equals(search))
      {
        list.add(new ProjectViewModel(project));
      }
    }
    for (Project project : model.getAllCompletedProjects())
    {
      if (project.getId().equals(search))
      {
        list.add(new ProjectViewModel(project));
      }
    }
    if (list.isEmpty())
    {
      throw new RuntimeException("No project with that ID exists");
    }
  }


  /**
   * Implementation of search by id for the display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view models with date property that fits in the search range and assigns them to the observable array.
   * @param searchFrom
   *        date from which we search from
   * @param searchTo
   *        date to which we search to
   * @param order
   *        order of display for the results
   * @param projects
   *        the array on which search is conducted
   * @return the filtered array
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public Project[] filterByDate(LocalDate searchFrom, LocalDate searchTo,
      String order, Project[] projects)
  {
    list.clear();
    SimpleDate from = new SimpleDate(searchFrom);
    SimpleDate to = new SimpleDate(searchTo);
    Project[] results = model.getOngoingProjectsStartedBetween(from, to, order,
        projects);
    for (Project project : results)
    {
      list.add(new ProjectViewModel(project));
    }
    if (list.isEmpty())
    {
      throw new RuntimeException("No projects found");
    }
    return results;
  }


  /**
   * Implementation of search by types for the display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view models with type property that fits the search and assigns them to the observable array.
   * @param selectedTypes
   *        a String representing the types by which we search
   * @param projects
   *        the array on which the search is conducted
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public void filterByTypes(String selectedTypes, Project[] projects)
  {
    list.clear();
    Project[] results = model.getOngoingProjectsByType(selectedTypes,
        projects);
    for (Project project : results)
    {
      list.add(new ProjectViewModel(project));
    }
    if (list.isEmpty())
    {
      throw new RuntimeException("No projects found");
    }

  }
  /**
   * Implementation of search by budget for the display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view models with budget property that fits in the search range and assigns them to the observable array.
   * @param budgetFrom
   *        a double of budget we search from
   * @param budgetTo
   *        a double of budget we search to
   * @param projects
   *        the array on which the search is conducted
   * @return the filtered array
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public Project[] filterByBudget(double budgetFrom, double budgetTo, Project[] projects)
  {
    list.clear();
    Project[] results = model.getOngoingProjectsByBudget(budgetFrom, budgetTo,
        projects);
    System.out.println(results.length);
    for (Project project : results)
    {
      list.add(new ProjectViewModel(project));
    }
    if (list.isEmpty())
    {
      throw new RuntimeException("No projects found");
    }
    return results;
  }
  /**
   * Implementation of search by duration for display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view models with duration property that fits in the search range and assigns them to the observable array.
   * @param durationFrom
   *        an integer of duration we search from
   * @param durationTo
   *        an integer of duration we search to
   * @param projects
   *        the array on which the search is conducted
   * @return the filtered array.
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public Project[] filterByDuration(int durationFrom, int durationTo, Project[] projects)
  {
    list.clear();
    Project[] results = model.getOngoingProjectsByDuration(durationFrom,
        durationTo, projects);
    for (Project project : results)
    {
      list.add(new ProjectViewModel(project));
    }
    if (list.isEmpty())
    {
      throw new RuntimeException("No projects found");
    }
    return results;
  }
  /**
   * Implementation of search by status for display.
   * @see ConstructionCompanyModel
   * It clears the list and scans the list searching for view models with status property that fits the search and assigns them to the observable array.
   * @param isNotBehind
   *        a boolean representing if the project is not behind the schedule
   * @param projects
   *        the array on which the search is conducted
   * @return the filtered array.
   * @throws RuntimeException --> if no projects are found exception is thrown
   */
  public Project[] filterByStatus(boolean isNotBehind, Project[] projects)
  {
    list.clear();
    Project[] results = model.getProjectsByStatus(isNotBehind, projects);
    for (Project project : results)
    {
      list.add(new ProjectViewModel(project));
    }

    if (list.isEmpty())
    {
      throw new RuntimeException("No projects found");
    }
    return results;
  }
}

