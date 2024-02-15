package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import model.ConstructionCompanyModel;
import model.Project;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * Project list view controller
 * @author Michael Barczuk
 * @author Matej Palas
 */
/**
 * Default no argument constructor
 * */
public class ProjectsListViewController
{


  @FXML private TableView<ProjectViewModel> projectTable;
  @FXML private TableColumn<ProjectViewModel, String> idColumn;
  @FXML private TableColumn<ProjectViewModel, String> typeColumn;
  @FXML private TableColumn<ProjectViewModel, Number> budgetColumn;
  @FXML private TableColumn<ProjectViewModel, String> dateColumn;
  @FXML private TableColumn<ProjectViewModel, Number> durationColumn;
  @FXML private TableColumn<ProjectViewModel, String> statusColumn;

  @FXML private TextField idSearchField;
  @FXML private DatePicker fromDateSearchField;
  @FXML private DatePicker toDateSearchField;
  @FXML private RadioButton ascendingButton;
  @FXML private RadioButton descendingButton;
  @FXML private RadioButton notBehindButton;
  @FXML private RadioButton behindButton;
  @FXML private CheckBox residentialCheckBox;
  @FXML private CheckBox commercialCheckBox;
  @FXML private CheckBox industrialCheckBox;
  @FXML private CheckBox roadConstructionCheckBox;
  @FXML private TextField fromBudgetSearchField;
  @FXML private TextField toBudgetSearchField;
  @FXML private TextField fromDurationSearchField;
  @FXML private TextField toDurationSearchField;
  @FXML private Button ongoingButton;
  @FXML private Button completedButton;
  @FXML private Button reportsButton;
  @FXML private Label countLabel;

  ViewHandler viewHandler;
  ConstructionCompanyModel model;
  Region root;
  private boolean showAlerts;
  ViewState state;
  ProjectListViewModel viewModel;
  private boolean lookingAtOngoing;



  /**
   * An initialization method taking four arguments. It sets up the table data and creates new list view model.
   * @see ProjectListViewModel
   * @param view
   *        the view handler
   * @param model
   *         the interface
   * @param root
   *        the root
   * @param state
   *        the view state
   */
  public void init(ViewHandler view, ConstructionCompanyModel model,
      Region root, ViewState state)
  {
    this.viewHandler = view;
    this.model = model;
    this.root = root;
    this.state = state;
    this.lookingAtOngoing = true;
    notBehindButton.setSelected(false);
    countLabel.setText("");

    this.viewModel = new ProjectListViewModel(model);

    projectTable.setItems(viewModel.getList());

    idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
    budgetColumn.setCellValueFactory(cellData -> cellData.getValue().getBudgetProperty());
    durationColumn.setCellValueFactory(cellData -> cellData.getValue().getDurationProperty());
    statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
    dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
    reportsButton(false);
    reset(true);
//    projectTable.setStyle("-fx-control-inner-background: #333333;");
//    projectTable.setStyle("-fx-table-cell-border-color: #666666;");
  }
  /**
   * A reset method that loads the view according to if the user was last looking at ongoing projects.
   * @param lookingAtOngoing
   *        a boolean representing it the user is looking at ongoing
   */
  public void reset(boolean lookingAtOngoing)
  {
    this.lookingAtOngoing = lookingAtOngoing;

    if (lookingAtOngoing)
    {
      ongoingPressed();

    }
    else
    {
      completedPressed();
    }
    fromDateSearchField.getEditor().setDisable(true);
    toDateSearchField.getEditor().setDisable(true);
  }
  /**
   * A method that is responsible for showing and hiding the reports button.
   * @param disable
   *        a boolean representing if the button should be disabled
   */
  public void reportsButton(boolean disable)
  {
    this.reportsButton.setVisible(disable);
  }
  /**
   * A method returning the root.
   * @return root.
   */
  public Region getRoot()
  {
    return root;
  }
  /**
   * A method that starts after ongoing button has been pressed. It resets all filters sets the label and displays all ongoing projects as well as disables reports button.
   */
  @FXML private void ongoingPressed()
  {
    showAlerts = true;
    projectTable.setItems(viewModel.getList());
    state.setProjects(viewModel.updateOnGoing());
    lookingAtOngoing = true;
    reportsButton(false);
    clearFilters();
    ongoingButton.setStyle("-fx-border-color: orange;");
    completedButton.setStyle("");
    if(model.getAllOngoingProjects().length==1)
      countLabel.setText("Found 1 project");
    else
     countLabel.setText(
        "Found " + model.getAllOngoingProjects().length + " projects");
  }
  /**
   * A method that starts after completed button has been pressed. It resets all filters sets the label and displays all completed projects as well as enables reports button.
   */
  @FXML private void completedPressed()
  {
    showAlerts= true;
    projectTable.setItems(viewModel.getList());
    state.setProjects(viewModel.updateCompleted());
    lookingAtOngoing = false;
    reportsButton(true);
    ongoingButton.setStyle("");
    completedButton.setStyle("-fx-border-color: orange;");
    if(model.getAllCompletedProjects().length==1)
      countLabel.setText("Found 1 project");
    else
     countLabel.setText(
        "Found " + model.getAllCompletedProjects().length + " projects");
    clearFilters();
  }
  /**
   * A method that starts after the open project has been pressed. If no projects have been selected it shows alert window informing user. If project is selected it opens a window corresponding to the selected projects type.
   */
  @FXML private void openProjectPressed()
  {
    ProjectViewModel selectedItem = projectTable.getSelectionModel()
        .getSelectedItem();
    if (selectedItem == null)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("No project selected");
      alert.setHeaderText("No project selected");
      alert.showAndWait();
    }

    else
    {
      state.setProject(
          model.getProjectById(selectedItem.getIdProperty().get()));


      switch (state.getType())
      {
        case "Commercial":
          viewHandler.openView("displayCommercial", state);
          break;
        case "Industrial":
          viewHandler.openView("displayIndustrial", state);
          break;
        case "Road Construction":
          viewHandler.openView("displayRoadConstruction", state);
          break;
        case "Residential":
          viewHandler.openView("displayResidential", state);
          break;
      }
    }
  }
  /**
   * A method that starts after the add residential button has been pressed it opens the corresponding window
   */
  @FXML private void addResidential()
  {
    viewHandler.openView("addResidential", state);
  }
  /**
   * A method that starts after the add industrial button has been pressed it opens the corresponding window
   */
  @FXML private void addIndustrial()
  {
    viewHandler.openView("addIndustrial", state);
  }
  /**
   * A method that starts after the add road construction button has been pressed it opens the corresponding window
   */
  @FXML private void addRoadConstruction()
  {
    viewHandler.openView("addRoadConstruction", state);
  }
  /**
   * A method that starts after the add commercial button has been pressed it opens the corresponding window
   */
  @FXML private void addCommercial()
  {
    viewHandler.openView("addCommercial", state);
  }
  /**
   * A method that starts after delete button has been pressed, if no projects has been selected it shows an alert window informing user. If the project is selected it shows alert window asking for confirmation by calling confirmation method and either deletes the project or does nothing.
   */
  @FXML private void deletePressed()
  {
    try
    {

      ProjectViewModel selectedItem = projectTable.getSelectionModel().getSelectedItem();

      if(selectedItem != null){
        boolean delete = confirmation();
        if (delete)
        {
          viewModel.remove(
                  model.getProjectById(selectedItem.getIdProperty().get()));
          if (!lookingAtOngoing)
          {

            model.deleteCompletedProject(
                    model.getProjectById(selectedItem.getIdProperty().get()));
          }
          else if (lookingAtOngoing)
          {
            model.deleteOngoingProject(
                    model.getProjectById(selectedItem.getIdProperty().get()));
          }
          projectTable.getSelectionModel().clearSelection();
          model.saveProjects();
        }
      }
      else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DELETION INTERUPTED");
        alert.setHeaderText("No project selected");
        alert.showAndWait();
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  /**
   * A method responsible for opening the alert window of type confirmation that returns the boolean according to the users choice.
   * @return boolean according to the users choice.
   */
  private boolean confirmation()
  {
    ProjectViewModel selectedItem = projectTable.getSelectionModel()
        .getSelectedItem();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Are you sure you wish to delete the project id: " + selectedItem.getIdProperty().get() + "?");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
  /**
   * A method that start after the reports button has been pressed, it opens reports view.
   */
  @FXML private void reportsPressed()
  {
    viewHandler.openView("reports", state);
  }

  //FILTERS SECTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  /**
   * A method responsible for clearing all applied filters.
   */
  private void clearFilters()
  {
    List<TextField> fieldsToEnable = Arrays.asList(fromDurationSearchField,
        idSearchField, toDurationSearchField, fromBudgetSearchField,
        toBudgetSearchField);
    for (TextField field : fieldsToEnable)
    {
      field.setText("");
    }
    List<CheckBox> boxesToEnable = Arrays.asList(residentialCheckBox,
        industrialCheckBox, commercialCheckBox, roadConstructionCheckBox);
    for (CheckBox box : boxesToEnable)
    {
      box.setSelected(true);
    }
    notBehindButton.setSelected(false);
    behindButton.setSelected(false);
    isSelected(notBehindButton);
    isSelected(behindButton);
    fromDateSearchField.setValue(null);
    toDateSearchField.setValue(null);
    showAlerts = true;
  }
  /**
   * A method that start after ENTER key has been pressed in search by id field, it filters and displays the project matching the id. If no projects are found it shows an alert window informing user. It also changes the label accordingly.
   * @param event
   *        the key pressed on keyboard
   */
  @FXML private void handleEnterKeyPressed(KeyEvent event)
  {
    if (event.getCode() == KeyCode.ENTER)
    {
      try
      {
        viewModel.searchById(idSearchField.getText());
        countLabel.setText("Project found");
      }
      catch (Exception e)
      {
        if (!idSearchField.getText().isEmpty())
        {
          if (showAlerts)
          {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No projects found");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
          }
          countLabel.setText("No projects found");
        }
      }
      if (idSearchField.getText().isEmpty())
      {
        ongoingPressed();
      }

    }
  }
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
   * A method using is selected for the order radio buttons.
   */
  @FXML private void ascendPressed()
  {
    descendingButton.setSelected(false);
    isSelected(ascendingButton);
    isSelected(descendingButton);
  }
  /**
   * A method using is selected for the order radio buttons.
   */
  @FXML private void descendPressed()
  {
    ascendingButton.setSelected(false);
    isSelected(descendingButton);
    isSelected(ascendingButton);
  }
  /**
   * A method using is selected the schedule radio buttons
   * calls filtering by status.
   */
  @FXML private void behindPressed()
  {
    notBehindButton.setSelected(false);
    isSelected(notBehindButton);
    isSelected(behindButton);
    statusPressed();
  }
  /**
   * A method using is selected the schedule radio buttons
   * calls filtering by status.
   */
  @FXML private void notBehindPressed()
  {
    behindButton.setSelected(false);
    isSelected(behindButton);
    isSelected(notBehindButton);
    statusPressed();
  }
  /**
   * A method that start after ENTER key has been pressed in search by date field, it filters and displays the projects matching the date range. If no projects are found it shows an alert window informing user. It also changes the label accordingly.
   * @param event
   *        the key pressed on keyboard
   * @see ProjectListViewModel
   */
  @FXML private void handleEnterKeyPressedToDate(KeyEvent event)
  {
    if (event.getCode() == KeyCode.ENTER)
    {
      LocalDate from = LocalDate.MIN;
      LocalDate to = LocalDate.MAX;
      if (fromDateSearchField.getValue() != null)
      {
        from = fromDateSearchField.getValue();
      }
      if (toDateSearchField.getValue() != null)
      {
        to = toDateSearchField.getValue();
      }
      String order = "";
      if (ascendingButton.isSelected())
      {
        order = "Ascending";
      }
      else
        order = "Descending";
      try
      {
        state.setProjects(
            viewModel.filterByDate(from, to, order, state.getProjects()));
        if(state.getProjects().length==1)
          countLabel.setText("Found 1 project");
        else
         countLabel.setText(
            "Found " + setCountLabel(state.getProjects()) + " projects");
      }
      catch (NullPointerException e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText("No projects found");
          alert.showAndWait();
        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
      catch (Exception e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText(e.getMessage());
          alert.showAndWait();
        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
    }
  }
  /**
   * A method responsible for setting the label displaying the number of projects.
   * @param projects
   *        the projects displayed
   * @return a tring to set the label.
   * @see ProjectListViewModel
   */
  private String setCountLabel(Project[] projects)
  {
    if (projects != null)
    {
      return String.valueOf(projects.length);
    }
    else
      return "0";
  }
  /**
   * A method used to filter by type, that keeps track of check boxes selected and filtering accordingly.
   * @see ProjectListViewModel
   */
  private void filteringByType()
  {
    String typesSelected = "";
    if (residentialCheckBox.isSelected())
    {
      typesSelected += "Residential";
    }
    else if (!residentialCheckBox.isSelected())
    {
      typesSelected.replace("Residential", "");
    }
    if (commercialCheckBox.isSelected())
    {
      typesSelected += "Commercial";
    }
    else if (!commercialCheckBox.isSelected())
    {
      typesSelected.replace("Commercial", "");
    }
    if (industrialCheckBox.isSelected())
    {
      typesSelected += "Industrial";
    }
    else if (!industrialCheckBox.isSelected())
    {
      typesSelected.replace("Industrial", "");
    }
    if (roadConstructionCheckBox.isSelected())
    {
      typesSelected += "Road Construction";
    }
    else if (!roadConstructionCheckBox.isSelected())
    {
      typesSelected.replace("Road Construction", "");
    }
    viewModel.filterByTypes(typesSelected, state.getProjects());
  }
  /**
   * A method that start after a check-box has been pressed in search by type, it filters and displays the projects matching the types selected.
   */
  @FXML private void checkBoxClicked()
  {
    try
    {
      filteringByType();
    }
    catch (Exception e)
    {
    }

  }
  /**
   * A method that start after ENTER key has been pressed in search by budget field, it filters and displays the projects matching the budget range. If no projects are found it shows an alert window informing user. It also changes the label accordingly.
   * @param event
   *        the key pressed on keyboard
   * @see ProjectListViewModel
   */
  @FXML private void handleEnterKeyPressedToBudget(KeyEvent event)
  {

    if (event.getCode() == KeyCode.ENTER)
    {

      double from = 0;
      double to = Double.MAX_VALUE;


      if (!fromBudgetSearchField.getText().isEmpty() && isDouble(fromBudgetSearchField.getText()))
      {
        from = Double.parseDouble(fromBudgetSearchField.getText());
      }
      if (!toBudgetSearchField.getText().isEmpty() && isDouble(toBudgetSearchField.getText()))
      {
        to = Double.parseDouble(toBudgetSearchField.getText());
      }
      try
      {
        state.setProjects(
            viewModel.filterByBudget(from, to, state.getProjects()));
        if(state.getProjects().length==1)
          countLabel.setText("Found 1 project");
        else
         countLabel.setText(
            "Found " + setCountLabel(state.getProjects()) + " projects");
      }
      catch (NullPointerException e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText("No projects found");
          alert.showAndWait();
        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
      catch (Exception e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText(e.getMessage());
          alert.showAndWait();
        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
    }
  }
  /**
   * A method that start after ENTER key has been pressed in search by duration field, it filters and displays the projects matching the duration range. If no projects are found it shows an alert window informing user. It also changes the label accordingly.
   * @param event
   *        the key pressed on keyboard
   * @see ProjectListViewModel
   */
  @FXML private void handleEnterKeyPressedToDuration(KeyEvent event)
  {

    if (event.getCode() == KeyCode.ENTER)
    {
      int from = Integer.MIN_VALUE;
      int to = Integer.MAX_VALUE;



      if (!fromDurationSearchField.getText().isEmpty() && isInteger(fromDurationSearchField.getText())) {
        from = Integer.parseInt(fromDurationSearchField.getText());
      }
      if (!toDurationSearchField.getText().isEmpty() && isInteger(toDurationSearchField.getText())) {
        to = Integer.parseInt(toDurationSearchField.getText());
      }
      try
      {
        state.setProjects(
            viewModel.filterByDuration(from, to, state.getProjects()));
        if(state.getProjects().length==1) {
          countLabel.setText("Found 1 project");
        }
        else {
          countLabel.setText(
             "Found " + setCountLabel(state.getProjects()) + " projects");
        }

      }
      catch (NullPointerException e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText("No projects found");
          alert.showAndWait();
        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
      catch (Exception e)
      {
        if (showAlerts)
        {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("No projects found");
          alert.setHeaderText(e.getMessage());
          alert.showAndWait();

        }
        countLabel.setText("No projects found");
        state.setProjects(null);
      }
    }
  }
  /**
   * A method that starts after the apply filters button has been pressed it calls ENTER being pressed on all fields except the id and displays the results. If no projects are found it shows an alert window informing the user.
   */
  @FXML private void ApplyFiltersPressed()
  {
    try
    {
      showAlerts = false;
      handleEnterKeyPressedToDate(pushEnter());
      handleEnterKeyPressedToBudget(pushEnter());
      handleEnterKeyPressedToDuration(pushEnter());
      filteringByType();
    }
    catch (Exception e)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("No projects found");
      alert.setHeaderText("No projects found");
      alert.showAndWait();
    }
    finally
    {
      showAlerts = true;
    }
  }
  /**
   * A method that starts after the clear filters button has been pressed. It calls the clear filters method and resets the displayed projects according to if user is looking at ongoing
   */
  @FXML private void ClearFiltersPressed()
  {
    clearFilters();
    if (lookingAtOngoing)
    {
      ongoingPressed();
    }
    else
    {
      completedPressed();
    }
  }
  /**
   * A method used to simulate ENTER button being pressed.
   * @return key event of ENTER being pressed.
   */
  private KeyEvent pushEnter()
  {
    return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false,
        false, false, false);
  }
  /**
   * A method that filters the projects by status after the radio button has been pressed and changes the label accordingly.
   * @see ProjectListViewModel
   */
  private void statusPressed()
  {
    boolean isNotBehind = notBehindButton.isSelected();
    if (notBehindButton.isSelected())
    {
      isNotBehind = true;
    }
    else if (behindButton.isSelected())
    {
      isNotBehind = false;
    }
    try
    {
      viewModel.filterByStatus(isNotBehind, state.getProjects());
      if(state.getProjects().length==1)
        countLabel.setText("Found 1 project");
      else
       countLabel.setText("Found " + state.getProjects().length + " projects");
    }
    catch (Exception e) {e.printStackTrace();}
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

}
