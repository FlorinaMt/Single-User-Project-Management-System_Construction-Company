package view;

import javafx.beans.property.*;
import model.Project;
/**
 * A view model class for the Project
 * @author Michael Barczuk
 */
public class ProjectViewModel
{
    private StringProperty idProperty;
    private DoubleProperty budgetProperty;
    private StringProperty dateProperty;
    private StringProperty typeProperty;
    private IntegerProperty durationProperty;
    private StringProperty statusProperty;
    /**
     * A one-argument constructor. That creates the new view model for the given project.
     * @param project
     *        the project given
     */
    public ProjectViewModel(Project project){
        idProperty = new SimpleStringProperty(project.getId());
        budgetProperty = new SimpleDoubleProperty(project.getBudget());
        typeProperty = new SimpleStringProperty(project.getType());
        dateProperty = new SimpleStringProperty(String.valueOf(project.getStartDate().toString()));
        durationProperty = new SimpleIntegerProperty(project.getExpectedDurationInMonths());
        statusProperty = new SimpleStringProperty(project.getStatus());
    }
    /**
     * A method returning a String property with the id of the project.
     * @return String property with the id of the project.
     */
    public StringProperty getIdProperty(){return idProperty;}
    /**
     * A method returning a String property with the type of the project.
     * @return String property with the type of the project.
     */
    public StringProperty getTypeProperty(){return typeProperty;}
    /**
     * A method returning a Double property containing the budget of the project.
     * @return Double property containing the budget of the project.
     */
    public DoubleProperty getBudgetProperty(){return budgetProperty;}
    /**
     * A method returning a Integer property containing the duration of the project.
     * @return Integer property containing the duration of the project.
     */
    public IntegerProperty getDurationProperty(){return durationProperty;}
    /**
     * A method returning a String property with the status of the project.
     * @return String property with the status of the project.
     */
    public StringProperty getStatusProperty(){return statusProperty;}
    /**
     * A method returning a String property with the start date of the project.
     * @return String property with the start date of the project.
     */
    public StringProperty getDateProperty(){return dateProperty;}

}