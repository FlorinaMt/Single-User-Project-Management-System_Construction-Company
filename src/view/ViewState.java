package view;

import model.*;

/**
 * View State class keeps track of values when switching between views or
 * selecting a project
 *
 * @author Matej Palas
 * @author Michael Barczuk
 */
public class ViewState {

    private Project project;
    private Project[] projects;

    /**
     * No argument constructor creating empty view state.
     */
    public ViewState() {
        this.project = null;
        this.projects = null;
    }

    /**
     * A method setting the view state to a specific project.
     *
     * @param project the project viewed
     */
    public void setProject(Project project) {
        this.project = project;
    }
    /**
     * A method setting view state to an array of currently viewed projects.
     * @param projects
     *        the array of viewed projects
     */
    public void setProjects(Project[] projects) {
        this.projects = projects;
    }
    /**
     * A method used for getting all currently stored object in view state
     * @return  currently stored array of projects

     */
    public Project[] getProjects() {
        return projects;
    }

    /**
     * A method for getting an id of currently stored projects
     * @return  returns currently stored project, if null returns empty string

     */
    public String getId() {
        if (project == null) return ""; //  was "temp" before
        return project.getId();
    }
    /**
     * A method clearing the view state.
     */
    public void wipe() {
        this.project = null;
    }
    /**
     * A method returning a boolean representing if the viewed project is ongoing.
     * @return boolean representing if the viewed project is ongoing.
     */
    public boolean getIsOngoing() {
        return project.isOngoing();
    }
    /**
     * A method returning a double representing the budget of the viewed project.
     * @return double representing the budget of the viewed project.
     */
    public double getBudget() {
        return project.getBudget();
    }
    /**
     * A method returning an integer representing duration of the viewed project.
     * @return integer representing duration of the viewed project.
     */
    public int getDuration() {
        return project.getExpectedDurationInMonths();
    }
    /**
     * A method returning a SimpleDate representing the start date of the viewed project.
     * @return SimpleDate representing the start date of the viewed project.
     */
    public SimpleDate getStartDate() {
        return project.getStartDate();
    }
    /**
     * A method returning a String representing the type of the viewed project.
     * @return String representing the type of the viewed project.
     */
    public String getType() {
        return project.getType();
    }
    /**
     * A method returning the currently viewed project.
     * @return the currently viewed project as Project class.
     */
    public Project getProject() {
        return project;
    }
    /**
     * A method returning the resources of the viewed project.
     * @return resources of the viewed project as Resources class.
     */
    public Resources getResources() {
        return project.getResources();
    }
    /**
     * A method returning size of the currently viewed project, if the project is road construction return 0.
     * @return double representing size of the currently viewed project, if the project is road construction return 0.
     */
    public double getSize() {
        switch (getType()) {
            case "Commercial":
                Commercial temp1 = (Commercial) project;
                return temp1.getSize();
            case "Residential":
                Residential temp2 = (Residential) project;
                return temp2.getSize();
            case "Industrial":
                Industrial temp3 = (Industrial) project;
                return temp3.getSize();
        }
        return 0;
    }
    /**
     * A method returning intended use of the currently viewed project, if the project is not Commercial or Industrial return empty String.
     * @return double representing intended use of currently viewed project of the currently viewed project, if the project is road construction return empty String.
     */
    public String getUseOfBuilding() {
        switch (getType()) {
            case "Industrial":
                Industrial temp1 = (Industrial) project;
                return temp1.getUseOfBuilding();
            case "Commercial":
                Commercial temp2 = (Commercial) project;
                return temp2.getUseOfBuilding();
        }
        return "";
    }
    /**
     * A method returning the number of floors for viewed commercial projects.
     * @return an integer representing the number of floors for viewed commercial project.
     */
    public int getNumberOfFloors() {
        Commercial temp = (Commercial) project;
        return temp.getNumberOfFloors();
    }
    /**
     * A method returning a boolean representing if the viewed project is not behind schedule.
     * @return boolean representing if the viewed project is not behind schedule.
     */
    public boolean getIsNotBehind() {
        return project.getIsNotBehindTheSchedule();
    }

    /**
     * A method returning a string representing current status.
     * @return According string for displaying it in project list for user
     */
    public String getStatus() {
        return project.getIsNotBehindTheSchedule() ? "Is not behind the schedule" : "Is behind the schedule";
    }

}
