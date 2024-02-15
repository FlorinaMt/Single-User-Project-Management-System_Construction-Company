package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import model.ConstructionCompanyModel;
import model.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * Update view controller class.
 *
 * @author Michael Barczuk
 * @author Matej Palas
 */
public class UpdateViewController
{
  @FXML private TextField hoursUsedField;
  @FXML private TextArea noteForHours;
  @FXML private TextField materialExpensesField;
  @FXML private TextArea noteForMaterials;
  @FXML private TextField salaryField;
  @FXML private TextArea noteForSalary;
  @FXML private Label errorLabel;
  @FXML private TextArea listOfUpdates;
 private double salary, materials;
  private int hours;

  private Region root;
  private ConstructionCompanyModel model;
  private ViewHandler viewHandler;
  private ViewState viewState;
    /**
     * An initialisation method taking four arguments.
     *
     * @param model       the interface
     * @param viewHandler the view handler
     * @param viewState   the current view state of the user
     * @param root        the root of the window
     * @see ConstructionCompanyModel
     * @see ViewHandler
     */
  public void init(ConstructionCompanyModel model, ViewHandler viewHandler,
      ViewState viewState, Region root)
  {
    this.root = root;
    this.viewHandler = viewHandler;
    this.model = model;
    reset(viewState);
    updatePressed();
  }
    /**
     * A method returning root.
     *
     * @return root.
     */
  public Region getRoot()
  {
    return root;
  }
    /**
     * A method responsible for restarting the window and assigning the view state.
     *
     * @param viewState the current view state
     */
  public void reset(ViewState viewState)
  {
    this.errorLabel.setText("");
    this.viewState = viewState;
    resetingFields();
  }
    /**
     * A method responsible for resetting fields.
     */
  private void resetingFields()
  {
    List<TextField> fieldsToEnable = Arrays.asList(hoursUsedField,
        materialExpensesField, salaryField);
    for (TextField field : fieldsToEnable)
    {
      field.setText("");
    }
    List<TextArea> areasToEnable = Arrays.asList(noteForHours, noteForMaterials,
        noteForSalary);
    for (TextArea area : areasToEnable)
    {
      area.setText("");
    }
    this.listOfUpdates.setText(
        viewState.getProject().getResources().toString());
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
     * A method responsible for returning the TAB pressed key event.
     *
     * @return TAB pressed key event.
     */
  private KeyEvent pushTab()
  {
    return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false,
        false, false, false);
  }
    /**
     * A method enabling the user to jump between the fields when pressing enter utilising the pushTab method to do so.
     *
     * @param event the button press on the keyboard
     */
  @FXML private void goNext(KeyEvent event){
    if(event.getCode() == KeyCode.ENTER){
      Node focusedNode = (Node) event.getSource();
      focusedNode.fireEvent(pushTab());
    }
  }
    /**
     * A method responsible for opening the alert window of type confirmation that returns the boolean according to the users choice.
     * @return boolean according to the users choice.
     */
  private boolean confirmation()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("There are negative values, proceed?");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }

    /**
     * A method that starts after the update button has been pressed in the window. It uses listeners to inform the user of incorrect inputs. If the inputs given are negative it uses confirmation method to approve the choice. If the values are illegal it throws an exception. Afterwards it loads the view window corresponding to the view state.
     * @throws IllegalArgumentException --> if the input type is incorrect or the value is illegal.
     */
  @FXML private void updatePressed()
  {
    try
    {

      salaryField.textProperty()
          .addListener((observable, oldValue, newValue) -> {
            if (!isDouble(newValue) && !salaryField.getText().isEmpty())
            {
              errorLabel.setText("Salary expenses field must be a number.");
              salary = Double.MAX_VALUE;
            }
            else if (isDouble(newValue))
            {

              salary = Double.parseDouble(salaryField.getText());
              errorLabel.setText("");
            }
            else
            {
              salary = 0;
              errorLabel.setText("");
            }
          });
      materialExpensesField.textProperty()
          .addListener((observable, oldValue, newValue) -> {
            if (!isDouble(newValue) && !materialExpensesField.getText()
                .isEmpty())
            {
              errorLabel.setText("Materials expenses field must be a number.");
              materials = Double.MAX_VALUE;
            }
            else if (isDouble(newValue))
            {
              materials = Double.parseDouble(materialExpensesField.getText());
              errorLabel.setText("");
            }
            else
            {
              materials = 0;
              errorLabel.setText("");
            }
          });
      hoursUsedField.textProperty()
          .addListener((observable, oldValue, newValue) -> {
            if (!isInteger(newValue) && !hoursUsedField.getText().isEmpty())
            {
              errorLabel.setText("Hours used field must be a whole number.");
              hours = Integer.MAX_VALUE;
            }
            else if (isDouble(newValue))
            {
              hours = Integer.parseInt(hoursUsedField.getText());
              errorLabel.setText("");
            }
            else
            {
              hours = 0;
              errorLabel.setText("");
            }
          });
      if (hours != Integer.MAX_VALUE && materials != Double.MAX_VALUE
          && salary != Double.MAX_VALUE)
      {
        if (hours < 0 || materials < 0 || salary < 0)
        {
          if (confirmation())
          {
            Update update = new Update(salary, materials, hours,
                noteForMaterials.getText(), noteForSalary.getText(),
                noteForHours.getText());
            model.updateResources(update, viewState.getProject());
            String backWindow = "";
            if (viewState.getType().equals("Road Construction"))
            {
              backWindow = "displayRoadConstruction";
            }
            else
            {
              backWindow = "display" + viewState.getType();
            }
            viewHandler.openView(backWindow, viewState);
          }
        }
        Update update = new Update(salary, materials, hours,
            noteForMaterials.getText(), noteForSalary.getText(),
            noteForHours.getText());
        model.updateResources(update, viewState.getProject());
        String backWindow = "";
        if (viewState.getType().equals("Road Construction"))
        {
          backWindow = "displayRoadConstruction";
        }
        else
        {
          backWindow = "display" + viewState.getType();
        }
        viewHandler.openView(backWindow, viewState);


      }
      else
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid input type");
        alert.showAndWait();
      }
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
    /**
     * The method that starts after cancel is pressed. It displays the confirm closing alert and if it returns boolean equal true it redirects user back to the view window for the corresponding view state.
     */
  @FXML private void cancelPressed()
  {
    if (confirmClosing())
    {
      String backWindow = "";

      if (viewState.getType().equals("Road Construction"))
      {
        backWindow = "displayRoadConstruction";
      }
      else
      {
        backWindow = "display" + viewState.getType();
      }
      viewHandler.openView(backWindow, viewState);
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
