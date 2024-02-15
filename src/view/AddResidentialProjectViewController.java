package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * A view controller handling the residential project windows
 * @author Matej Palas
 * @author Michael Barczuk
 */
public class AddResidentialProjectViewController
{
  @FXML private Text title; // Main header title
  //  Main fields
  @FXML private TextField idField;
  @FXML private TextField budgetField;
  @FXML private DatePicker dateField;
  @FXML private TextField durationField;
  @FXML private TextField estimatedHoursField;
  @FXML private TextField expectedExpensesField;
  //  For Residential project fields
  @FXML private TextField sizeField;
  @FXML private TextField numberOfKitchensField;
  @FXML private TextField numberOfBathroomsField;
  @FXML private TextField numberOfRoomsWithPlumbingField;
  @FXML private RadioButton isANewBuild;
  @FXML private RadioButton isARenovation;

  //  Resources fields
  @FXML private TextField manHoursField;
  @FXML private TextField salaryExpensesField;
  @FXML private TextField materialExpensesField;
  @FXML private TextField totalExpensesField;
  //  Other objects referenced for functionality
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private VBox resourceBox;
  @FXML private Label errorLabel;
  @FXML private RadioButton behindButton;
  @FXML private RadioButton notBehindButton;

  private Region root;
  private ConstructionCompanyModel model;
  private ViewHandler viewHandler;
  private ViewState viewState;
  private Function function;
  private Residential projectToEdit;
  /**
   * Default no argument constructor
   */
  public AddResidentialProjectViewController()
  {
  }
  /**
   * Initialisation method taking five arguments. It calls the reset function with view state and function passed.
   * @param viewHandler
   *        the view handler
   * @param model
   *        the interface
   * @param root
   *        the root
   * @param viewState
   *        the view state
   * @param function
   *        the function of the window, custom enum
   */
  public void init(ViewHandler viewHandler, ConstructionCompanyModel model,
      Region root, ViewState viewState,
      Function function)  //  This method gets called only once
  {
    this.viewState = viewState;
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    reset(function,
        viewState);    //  Calling the reset function in initialize, sets the fields depending on the function of the window
  }

  //  sets the Schedule buttons accordingly
  /**
   * A method that sets the schedule buttons accordingly to the current view state.
   */
  public void setScheduleButtons()
  {
    if (viewState.getIsNotBehind())
    {
      this.notBehindButton.setSelected(true);
      this.behindButton.setSelected(false);
    }
    else
    {
      this.notBehindButton.setSelected(false);
      this.behindButton.setSelected(true);
    }
  }
  /**
   * A method that returns a boolean from the schedule buttons
   * @return a boolean representing if the project is not behind schedule
   */
  public boolean getScheduleFromButtons()
  {
    return this.notBehindButton.isSelected();
  }
  /**
   * A method that sets the renovation buttons accordingly to the current view state.
   */
  public void setRenovationButton()
  {
    if (projectToEdit.getIsNewBuild())
    {
      this.isANewBuild.setSelected(true);
      this.isARenovation.setSelected(false);
    }
    else
    {
      this.isANewBuild.setSelected(false);
      this.isARenovation.setSelected(true);
    }
  }
  /**
   * A reset method that changes the windows look and functionality according to the function it currently fulfills.
   * Contains a lot of setters and enables with disables.
   * @param function
   *        the function of the window, custom enum
   * @param state
   *        the current view state
   */
  public void reset(Function function,
      ViewState state)    //  Reset gets called everytime after initializing method
  {
    this.errorLabel.setText("");
    this.function = function;
    this.viewState = state;

    if (viewState != null)
      this.projectToEdit = (Residential) viewState.getProject();

    //  bunch of setters ugh
    switch (function)
    {
      case add ->
      {
        settingDisableForAll(false);
        resettingForAdd();
        this.updateButton.setDisable(true);
        this.updateButton.setVisible(false);
        this.budgetField.setText("");
        this.durationField.setText(Residential.defaults[0]);
        this.numberOfBathroomsField.setText(Residential.defaults[1]);
        this.numberOfKitchensField.setText(Residential.defaults[1]);
        this.numberOfRoomsWithPlumbingField.setText(Residential.defaults[1]);

        this.resourceBox.setVisible(false);

      }
      case edit ->
      {
        if (viewState.getIsOngoing())
        {
          this.updateButton.setText("Mark as Completed");
        }
        else
          this.updateButton.setText("Mark as Ongoing");

        this.title.setText("Edit a Residential Project");
        this.updateButton.setDisable(false);
        this.updateButton.setVisible(true);
        this.resourceBox.setVisible(true);
        this.addButton.setText("Save Edit");
        settingDisableForAll(false);
        this.idField.setText(viewState.getId());
        this.idField.setDisable(true);
        this.budgetField.setText(String.valueOf(viewState.getBudget()));
        this.durationField.setText(String.valueOf(viewState.getDuration()));
        this.dateField.setValue(viewState.getStartDate().getDate());
        this.estimatedHoursField.setText(String.valueOf(viewState.getResources().getEstimatedTotalHours()));
        this.expectedExpensesField.setText(String.valueOf(viewState.getResources().getExpectedExpenses()));
        this.sizeField.setText(String.valueOf(viewState.getSize()));
        this.numberOfKitchensField.setText(String.valueOf(projectToEdit.getNumberOfKitchens()));
        this.numberOfBathroomsField.setText(String.valueOf(projectToEdit.getNumberOfKitchens()));
        setRenovationButton();
        isSelected(isANewBuild);
        isSelected(isARenovation);
        this.manHoursField.setDisable(true);
        this.salaryExpensesField.setDisable(true);
        this.materialExpensesField.setDisable(true);
        this.totalExpensesField.setDisable(true);
        this.dateField.setDisable(true);
        setScheduleButtons();
        isSelected(notBehindButton);
        isSelected(behindButton);
        this.isARenovation.setDisable(true);
        this.isANewBuild.setDisable(true);
        this.sizeField.setDisable(true);
      }
      case display ->
      {
        if (viewState.getIsOngoing())
        {
          this.updateButton.setVisible(true);
          this.updateButton.setDisable(false);
        }
        else
        {
          this.updateButton.setDisable(true);
          this.updateButton.setVisible(false);
        }
        this.title.setText("Displaying Residential Project");
        this.addButton.setText("Edit details");
        this.updateButton.setText("Update resources");
        this.resourceBox.setVisible(true);
        this.idField.setText(viewState.getId());
        this.budgetField.setText(String.valueOf(viewState.getBudget()));
        this.dateField.setValue(viewState.getStartDate().getDate());
        this.durationField.setText(String.valueOf(viewState.getDuration()));
        this.estimatedHoursField.setText(
            String.valueOf(viewState.getResources().getEstimatedTotalHours()));
        this.expectedExpensesField.setText(
            String.valueOf(viewState.getResources().getExpectedExpenses()));
        this.sizeField.setText(String.valueOf(viewState.getSize()));
        this.numberOfKitchensField.setText(String.valueOf(((Residential) viewState.getProject()).getNumberOfKitchens()));
        this.numberOfBathroomsField.setText(String.valueOf(((Residential) viewState.getProject()).getNumberOfBathrooms()));
        this.numberOfRoomsWithPlumbingField.setText(String.valueOf(((Residential) viewState.getProject()).getNumberOfOtherRoomsWithPlumbing()));
        setRenovationButton();
        this.isANewBuild.setDisable(false);
        this.isARenovation.setDisable(false);

        this.manHoursField.setText(
            String.valueOf(viewState.getResources().getManHoursUsed()));
        this.salaryExpensesField.setText(String.valueOf(
            viewState.getResources().getSalaryExpenses()));
        this.materialExpensesField.setText(
            String.valueOf(viewState.getResources().getMaterialsExpenses()));
        this.totalExpensesField.setText(
            String.valueOf(viewState.getResources().getTotalExpenses()));
        this.notBehindButton.setDisable(true);
        this.behindButton.setDisable(true);
        setScheduleButtons();
        settingDisableForAll(true);

      }
    }
  }

  //  Disables all fields, used for only to view the project, and not being able to edit
  /**
   * A method used to disable or enable all fields for different functions of the window.
   * @param isDisabled
   *        a boolean representing if the fields are to be disabled
   */
  private void settingDisableForAll(boolean isDisabled)
  {
    List<TextField> fieldsToEnable = Arrays.asList(idField, budgetField,
            durationField, estimatedHoursField, expectedExpensesField, sizeField,
            manHoursField, salaryExpensesField, materialExpensesField,
            numberOfRoomsWithPlumbingField, numberOfBathroomsField, numberOfKitchensField,
            totalExpensesField);
    for (TextField field : fieldsToEnable)
    {
      field.setDisable(isDisabled);
    }
    this.dateField.setDisable(isDisabled);
    this.notBehindButton.setDisable(isDisabled);
    this.behindButton.setDisable(isDisabled);
    this.isANewBuild.setDisable(isDisabled);
    this.isARenovation.setDisable(isDisabled);

  }


  //  Used for add, sets all fields to null then sets its default values. which is handled above
  /**
   * A method that resets all fields for the add function of the window.
   */
  private void resettingForAdd()
  {
    List<TextField> fieldsToEnable = Arrays.asList(idField, budgetField,
        durationField, estimatedHoursField, expectedExpensesField, sizeField,
        numberOfKitchensField, numberOfBathroomsField, manHoursField,
        numberOfRoomsWithPlumbingField, salaryExpensesField, materialExpensesField,
        totalExpensesField);
    for (TextField field : fieldsToEnable)
    {
      field.setText("");
    }
    this.title.setText("Add a Residential project");
    this.addButton.setText("Add project");
    this.dateField.setDisable(false);
    this.isANewBuild.setSelected(true);
    this.isARenovation.setSelected(false);
    this.isANewBuild.setDisable(true);
    this.dateField.setValue(null);
    this.dateField.getEditor().setDisable(true);

  }
  /**
   * A method returning root
   * @return root
   */
  public Region getRoot()
  {
    return root;
  }
  /**
   * A method for save edit button. It tries to apply all the edits made by the user and catches exceptions.
   */
  private void saveEditButtonPressed()
  {
    errorLabel.setText("");
    try
    {
      Resources sameResources = new Resources(
          Double.parseDouble(expectedExpensesField.getText()),
          Integer.parseInt(estimatedHoursField.getText()));

      projectToEdit.edit(Double.parseDouble(budgetField.getText()),
          Integer.parseInt(durationField.getText()), getScheduleFromButtons(),
          true, Integer.parseInt(numberOfBathroomsField.getText()),
          Integer.parseInt(numberOfKitchensField.getText()),
          Integer.parseInt(numberOfRoomsWithPlumbingField.getText()), sameResources);
    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
    finally
    {
      viewHandler.openView("displayResidential", viewState);
    }
  }

  //  add project button which acts as edit the same time, used switch for handling different functions of the view
  /**
   * A method that fires up after the add button has been pressed. It fulfills different roles depending on the function of the window.
   * For display --> it opens edit view.
   * For add --> it tries to create and add a new project.
   * For edit --> it tries to save the edits made by the user.
   */
  @FXML private void addPressed()
  {
    switch (function)
    {
      case add ->
      {
        errorLabel.setText("");
        try
        {
          SimpleDate dateToAssign = null;
          if (dateField.getValue() == null)
          {
            dateToAssign = new SimpleDate();
          }
          else
          {
            dateToAssign = new SimpleDate(dateField.getValue());
          }

          Resources newResources = new Resources(
              Double.parseDouble(expectedExpensesField.getText()),
              Integer.parseInt(estimatedHoursField.getText()));
          Residential newProject = new Residential(idField.getText(),
              Double.parseDouble(budgetField.getText()), dateToAssign,
              Integer.parseInt(durationField.getText()), newResources,
              Double.parseDouble(sizeField.getText()),
              Integer.parseInt(numberOfKitchensField.getText()),
              Integer.parseInt(numberOfBathroomsField.getText()),
              Integer.parseInt(numberOfRoomsWithPlumbingField.getText()),
              getIsNewBuildButton());
          model.addToOngoingProjects(newProject);
          viewHandler.openView("projects", viewState);

        }
        catch (NumberFormatException e){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Illegal values");
          alert.setHeaderText("Illegal values");
          alert.showAndWait();
          errorLabel.setText("Illegal values");
        }
        catch (Exception e)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Invalid Input");
          alert.setHeaderText(e.getMessage());
          alert.showAndWait();
          errorLabel.setText(e.getMessage());
        }
      }
      case display ->
      {
        errorLabel.setText("");
        viewHandler.openView("editResidential", viewState);
      }
      case edit ->
      {
        errorLabel.setText("");
        saveEditButtonPressed();
      }
    }

  }
  /**
   * A method executing after radio button has been pressed calling is Selected method and deselecting the twin button.
   */
  @FXML private void notBehindPressed()
  {
    behindButton.setSelected(false);
    isSelected(behindButton);
    isSelected(notBehindButton);
  }
  /**
   * A method executing after radio button has been pressed calling is Selected method and deselecting the twin button.
   */
  @FXML private void behindPressed()
  {
    notBehindButton.setSelected(false);
    isSelected(notBehindButton);
    isSelected(behindButton);
  }

  //  If one radio button is selected other is disabled, this is used in the behind or not behind for example
  /**
   * A method making it so if the radio button is selected it cannot be deselected and makes it so it can be selected again if the twin button becomes selected.
   * @param button
   *        the radio button to check
   */
  private void isSelected(RadioButton button)
  {
    if (button.isSelected())
    {
      button.setDisable(true);
    }
    else
      button.setDisable(false);
  }

  /**
   * A method that fires up after the update button has been pressed. It fulfills different purpose depending on the window function.
   * For display --> it opens the update view.
   * For edit --> it either marks project as completed or ongoing.
   * For add --> is not visible.
   */
  @FXML private void updatePressed()
  {
    switch (function)
    {
      case display ->
      {
        viewHandler.openView("update", viewState);
      }
      case edit ->
      {
        if (viewState.getIsOngoing())
        {

          Project toFinish = model.getProjectById(viewState.getId());
          model.addToCompletedProjects(toFinish);
          toFinish.setIsOngoing(false);
          viewState.wipe();
        }
        else
        {
          Project toFinish = model.getProjectById(viewState.getId());
          model.addToOngoingProjects(toFinish);
          toFinish.setIsOngoing(true);
          viewState.wipe();
        }
        viewHandler.openView("projects", viewState);
      }
    }
  }
  /**
   * A method that returns a boolean from the is a renovation buttons.
   * @return a boolean representing if the project is a new build.
   */
  public boolean getIsNewBuildButton()
  {
    return this.isANewBuild.isSelected();
  }
  /**
   * A method executing after radio button has been pressed calling is Selected method and deselecting the twin button.
   */
  @FXML private void newBuildPressed()
  {
    isARenovation.setSelected(false);
    isSelected(isARenovation);
    isSelected(isANewBuild);
  }
  /**
   * A method executing after radio button has been pressed calling is Selected method and deselecting the twin button.
   */
  @FXML private void renovationPressed()
  {
    isANewBuild.setSelected(false);
    isSelected(isARenovation);
    isSelected(isANewBuild);
  }
  /**
   * A method that fires up after the cancel button has been pressed. It resets all fields style to default. It fulfils slightly different purpose depending on the windows function.
   * For add --> shows confirmation alert window, if confirmed opens the ongoing projects list view.
   * For edit --> shows confirmation alert window, if confirmed opens the display project view.
   * For display --> opens either completed or ongoing project list view depending on view state and wipes the view state.
   */
  @FXML private void cancelPressed()
  {
    List<TextField> fieldsToEnable = Arrays.asList(idField, budgetField,
        durationField, estimatedHoursField, expectedExpensesField, sizeField,
        numberOfKitchensField, numberOfBathroomsField, manHoursField,
        numberOfRoomsWithPlumbingField, salaryExpensesField, materialExpensesField,
        totalExpensesField);
    for (TextField field : fieldsToEnable)
    {
      field.setStyle("");
    }
    switch (function)
    {
      case add ->
      {
        if (confirmClosing())
        {
          viewHandler.openView("projects", null);
        }
      }
      case edit ->
      {
        if (confirmClosing())
        {
          viewHandler.openView("projects", viewState);
        }
      }
      case display ->
      {
        if (viewState.getIsOngoing())
        {
          viewHandler.openView("projects", null);
          viewState.wipe();
        }
        else if (!viewState.getIsOngoing())
        {
          viewHandler.openView("projectsComplete", null);
          viewState.wipe();
        }
      }
    }
  }
  /**
   * A method simulating the TAB key being pressed.
   * @return a key event of TAB being pressed.
   */
  private KeyEvent pushTab()
  {
    return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false,
        false, false, false);
  }
  /**
   * A method allowing the user to skip to the next field after pressing ENTER by calling push tab method.
   * @param event
   *        the key being pressed on keyboard
   */
  @FXML private void goNext(KeyEvent event){
    if(event.getCode() == KeyCode.ENTER){
      Node focusedNode = (Node) event.getSource();
      focusedNode.fireEvent(pushTab());
    }
  }
  /**
   * A method allowing the user to automatically press the add button after pressing ENTER on the last field by calling push tab method.
   * @param event
   *        the key being pressed on keyboard
   */
  @FXML private void last(KeyEvent event){
    if (event.getCode() == KeyCode.ENTER && function == Function.add){
      addPressed();
    }
  }
  /**
   * A method checking if the give input is a double by trying to cast it as one and returning a boolean.
   *
   * @param input the input from the field
   * @return the boolean representing if the input is a double.
   */
  public static boolean isDouble(String input)
  {
    try
    {
      // Try parsing the string as a double
      Double.parseDouble(input);
      return true;
    }
    catch (NumberFormatException e)
    {
      // If an exception is caught, the input is not a valid double
      return false;
    }
  }
  /**
   * A method checking if the give input is an integer by trying to cast it as one and returning a boolean.
   *
   * @param input the input from the field
   * @return the boolean representing if the input is an integer.
   */
  public static boolean isInteger(String input)
  {
    try
    {
      // Try parsing the string as an integer
      Integer.parseInt(input);
      return true;
    }
    catch (NumberFormatException e)
    {
      // If an exception is caught, the input is not a valid integer
      return false;
    }
  }
  /**
   * A method checking the fields for doubles and highlighting them as well as changing the label if the value is incorrect. Changes both back to normal if mistake is corrected.
   * @param event
   *      a key on keyboard pressed
   */
  @FXML private void checkDouble(KeyEvent event)
  {
    TextField focusedNode = (TextField) event.getSource();
    if (!isDouble(focusedNode.getText()) && !focusedNode.getText().isEmpty())
    {
      errorLabel.setText("Invalid input for " + focusedNode.getAccessibleText());
      focusedNode.setStyle("-fx-border-color: red;");
    }
    else
    {
      errorLabel.setText("");
      focusedNode.setStyle("");
    }
  }

  /**
   * A method checking the fields for integers and highlighting them as well as changing the label if the value is incorrect. Changes both back to normal if mistake is corrected.
   * @param event
   *      a key on keyboard pressed
   */
  @FXML private void checkInt(KeyEvent event)
  {
    TextField focusedNode = (TextField) event.getSource();
    if (!isInteger(focusedNode.getText()) && !focusedNode.getText().isEmpty())
    {
      errorLabel.setText("Invalid input for " + focusedNode.getAccessibleText());
      focusedNode.setStyle("-fx-border-color: red;");
    }
    else
    {
      errorLabel.setText("");

    }
  }

  /**
   * A method responsible for showing alert window of type confirmation for closing the window that returns the boolean accordingly to users choice.
   * @return boolean accordingly to users choice.
   */
  private boolean confirmClosing()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Are you sure you wish to cancel?");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}
