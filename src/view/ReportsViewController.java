package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.ConstructionCompanyModel;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
/**
 * Reports window view controller
 *
 * @author Michael Barczuk
 */
public class ReportsViewController
{
  @FXML private TextField expensesResidential;
  @FXML private TextField durationResidential;
  @FXML private TextField expensesCommercial;
  @FXML private TextField durationCommercial;
  @FXML private TextField expensesIndustrial;
  @FXML private TextField durationIndustrial;
  @FXML private TextField expensesRoad;
  @FXML private TextField durationRoad;
  @FXML private Label residentialLabel;
  @FXML private Label commercialLabel;
  @FXML private Label industrialLabel;
  @FXML private Label roadLabel;
  ConstructionCompanyModel model;
  Region root;
  ViewHandler viewHandler;
  /**
   * An initialisation method taking three arguments.
   *
   * @param viewHandler the view handler
   * @param model the interface
   * @param root the root
   * @see ConstructionCompanyModel
   * @see ViewHandler
   */
  public void init(ViewHandler viewHandler, ConstructionCompanyModel model,
      Region root)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    reset();
  }
  /**
   * A reset method that sets the fields every time.
   */
  public void reset()
  {
    setFields();
  }
  /**
   * A method returning root.
   * @return root.
   */
  public Region getRoot()
  {
    return root;
  }
  /**
   * A method responsible for setting the fields with the statistics reports. It utilises fieldsCheck method to check if the fields are empty if so sets them to "no data available".
   */
  public void setFields()
  {
    this.expensesResidential.setText(
        String.valueOf(model.getAverageExpenses("Residential")));
    this.durationResidential.setText(
        String.valueOf(model.getAverageTimeSpent("Residential")));
    this.expensesCommercial.setText(
        String.valueOf(model.getAverageExpenses("Commercial")));
    this.durationCommercial.setText(
        String.valueOf(model.getAverageTimeSpent("Commercial")));
    this.expensesIndustrial.setText(
        String.valueOf(model.getAverageExpenses("Industrial")));
    this.durationIndustrial.setText(
        String.valueOf(model.getAverageTimeSpent("Industrial")));
    this.expensesRoad.setText(
        String.valueOf(model.getAverageExpenses("Road Construction")));
    this.durationRoad.setText(
        String.valueOf(model.getAverageTimeSpent("Road Construction")));
    fieldsCheck();
    this.residentialLabel.setText("Number of projects: " +String.valueOf(model.getCompletedProjectsByType("Residential", model.getAllCompletedProjects()).length));
    this.commercialLabel.setText("Number of projects: " +String.valueOf(model.getCompletedProjectsByType("Commercial", model.getAllCompletedProjects()).length));
    this.industrialLabel.setText("Number of projects: " +String.valueOf(model.getCompletedProjectsByType("Industrial", model.getAllCompletedProjects()).length));
    this.roadLabel.setText("Number of projects: " +String.valueOf(model.getCompletedProjectsByType("Road Construction", model.getAllCompletedProjects()).length));
  }
  /**
   * A method that check if the fields contain statistic data if they do not it sets them to "no data available".
   */
  public void fieldsCheck()
  {
    List<TextField> fields = Arrays.asList(expensesCommercial,
        durationCommercial, expensesIndustrial, durationIndustrial,
        expensesResidential, durationResidential, expensesRoad, durationRoad);
    for (TextField field : fields){
      if (field.getText().endsWith("N")){
        field.setText("No data available");
      }
    }
  }
  /**
   * A method that starts after back button has been pressed that returns the user back to the last viewed window.
   */
  @FXML private void backPressed()
  {
    viewHandler.openView("projectsComplete", null);
  }
}
