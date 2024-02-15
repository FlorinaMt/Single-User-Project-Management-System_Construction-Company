package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import model.ConstructionCompanyModel;

import javax.swing.text.View;

/**
 * View Handler class is responsible for loading and switching views in
 * the application
 *
 * @author Matej Palas
 * @author Michael Barczuk
 * @author Florina Mitigus
 */
public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private ProjectsListViewController projectsListViewController;
    private AddResidentialProjectViewController addResidentialProjectViewController;
    private AddIndustrialProjectViewController addIndustrialProjectViewController;
    private AddCommercialProjectViewController addCommercialProjectViewController;
    private AddRoadConstructionProjectViewController addRoadConstructionProjectViewController;
    private ReportsViewController reportsViewController;
    private UpdateViewController updateViewController;
    private ConstructionCompanyModel model;
    ViewState state;

    /**
     * A one-argument constructor creating new view handler.
     *
     * @param model system model for functionality made in views
     */
    public ViewHandler(ConstructionCompanyModel model) {
        this.currentScene = new Scene(new Region());
        this.model = model;
        state = new ViewState();
    }

    /**
     * A method starting the view handler with primary stage.
     *
     * @param primaryStage the default stage
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("projects", state);
    }

    /**
     * A method opening different views that passes the view state in between them
     * and opens them according to it. It also ensures the windows are not resizeable
     * as well as makes them appear in the centre of the screen,
     * setting a title and an icon as well.
     *
     * @param id    the id of the window to load
     * @param state the view state to be passed between the windows
     */
    public void openView(String id, ViewState state) {

        Region root = null;

        switch (id) {
            case "projects": {
                root = loadProjectsListView("/Scenes/ProjectsListView.fxml", true);
                model.saveProjects();
                break;
            }
            case "projectsComplete":
                root = loadProjectsListView("/Scenes/ProjectsListView.fxml", false);
                model.saveProjects();
                break;
            //                      _____ FOR RESIDENTIAL ______
            //  loading scene graph with specified function for determined window use
            case "addResidential": {
                root = loadAddResidentialProjectView("/Scenes/AddResidentialProjectView.fxml", null, Function.add);
                break;
            }
            case "editResidential": {
                root = loadAddResidentialProjectView("/Scenes/AddResidentialProjectView.fxml", state, Function.edit);
                break;
            }
            case "displayResidential": {
                root = loadAddResidentialProjectView("/Scenes/AddResidentialProjectView.fxml", state, Function.display);
                break;
            }
            //                      _____ FOR COMMERCIAL ______
            case "addCommercial": {
                root = loadAddCommercialProjectView("/Scenes/AddCommercialProjectView.fxml", null, Function.add);
                break;
            }
            case "editCommercial": {
                root = loadAddCommercialProjectView("/Scenes/AddCommercialProjectView.fxml", state, Function.edit);
                break;
            }
            case "displayCommercial": {

                root = loadAddCommercialProjectView("/Scenes/AddCommercialProjectView.fxml", state, Function.display);
                break;
            }

            //                      _____ FOR INDUSTRIAL ______
            case "addIndustrial": {
                root = loadAddIndustrialProjectView("/Scenes/AddIndustrialProjectView.fxml", null, Function.add);
                break;
            }
            case "editIndustrial": {
                root = loadAddIndustrialProjectView("/Scenes/AddIndustrialProjectView.fxml", state, Function.edit);
                break;
            }
            case "displayIndustrial": {
                root = loadAddIndustrialProjectView("/Scenes/AddIndustrialProjectView.fxml", state, Function.display);
                break;
            }

            case "addRoadConstruction": {
                root = loadAddRoadConstructionProjectView("/Scenes/AddRoadConstructionProjectView.fxml", state, Function.add);
                break;
            }
            case "editRoadConstruction": {
                root = loadAddRoadConstructionProjectView("/Scenes/AddRoadConstructionProjectView.fxml", state, Function.edit);
                break;
            }
            case "displayRoadConstruction": {
                root = loadAddRoadConstructionProjectView("/Scenes/AddRoadConstructionProjectView.fxml", state, Function.display);
                break;
            }

            case ("reports"): {
                root = loadReportsView("/Scenes/ReportsView.fxml");
                break;
            }
            case ("update"): {
                root = loadUpdateView("/Scenes/UpdateProjectView.fxml", state);
                break;
            }
        }
        currentScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setResizable(false);

//    primaryStage.setTitle(title);
        primaryStage.setTitle("Construction Company System");
        Image logo = new Image(getClass().getResourceAsStream("/images/logo2.png"));

        // Set the logo in the stage
        primaryStage.getIcons().add(logo);

        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());

        //  Centering the window
        double x = (bounds.getWidth() - primaryStage.getWidth()) / 2;
        double y = (bounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(x);
        primaryStage.setY(y);

        primaryStage.show();
    }

    /**
     * A method responsible for loading the default window of project list.
     *
     * @param fxmlFile         the fxml file to load
     * @param lookingAtOnGoing a boolean passed representing if user was last looking on ongoing projects list
     * @return the root to load.
     */
    private Region loadProjectsListView(String fxmlFile, boolean lookingAtOnGoing) {
        Region root = null;

        if (projectsListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                root = loader.load();

                projectsListViewController = loader.getController();
                projectsListViewController.init(this, model, root, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            projectsListViewController.reset(lookingAtOnGoing);
        return projectsListViewController.getRoot();
    }

    /**
     * A method responsible for loading the window handling Residential project type. It passes down the view state as well as the function of the window.
     *
     * @param fxmlFile the fxml file to load
     * @param state    the current view state
     * @param function the function of the window, custom enum
     * @return the root to load.
     */
    private Region loadAddResidentialProjectView(String fxmlFile, ViewState state,
                                                 Function function) {
        Region root = null;
        if (state == null) {
            if (addResidentialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addResidentialProjectViewController = loader.getController();
                    addResidentialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addResidentialProjectViewController.reset(function, state);
        } else {
            if (addResidentialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addResidentialProjectViewController = loader.getController();
                    addResidentialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addResidentialProjectViewController.reset(function, state);
        }
        return addResidentialProjectViewController.getRoot();
    }

    /**
     * A method responsible for loading the window handling Commercial project type. It passes down the view state as well as the function of the window.
     *
     * @param fxmlFile the fxml file to load
     * @param state    the current view state
     * @param function the function of the window, custom enum
     * @return the root to load.
     */
    private Region loadAddCommercialProjectView(String fxmlFile, ViewState state,
                                                Function function) {
        Region root = null;
        if (state == null) {
            if (addCommercialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addCommercialProjectViewController = loader.getController();
                    addCommercialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addCommercialProjectViewController.reset(function, state);
        } else {
            if (addCommercialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addCommercialProjectViewController = loader.getController();
                    addCommercialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addCommercialProjectViewController.reset(function, state);
        }
        return addCommercialProjectViewController.getRoot();
    }

    //  Function with loading the root object, takes it the viewState because of the edit function....
    //  which gets the selected object values before loading the root

    /**
     * A method responsible for loading the window handling Industrial project type. It passes down the view state as well as the function of the window.
     *
     * @param fxmlFile the fxml file to load
     * @param state    the current view state
     * @param function the function of the window, custom enum
     * @return the root to load.
     */
    private Region loadAddIndustrialProjectView(String fxmlFile, ViewState state,
                                                Function function) {
        Region root = null;
        if (state == null) {
            if (addIndustrialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addIndustrialProjectViewController = loader.getController();
                    addIndustrialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addIndustrialProjectViewController.reset(function, state);
        } else {
            if (addIndustrialProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addIndustrialProjectViewController = loader.getController();
                    addIndustrialProjectViewController.init(this, model, root, state,
                            function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addIndustrialProjectViewController.reset(function, state);
        }
        return addIndustrialProjectViewController.getRoot();
    }

    /**
     * A method responsible for loading the window handling Road Construction project type. It passes down the view state as well as the function of the window.
     *
     * @param fxmlFile the fxml file to load
     * @param state    the current view state
     * @param function the function of the window, custom enum
     * @return the root to load.
     */
    private Region loadAddRoadConstructionProjectView(String fxmlFile,
                                                      ViewState state, Function function) {
        Region root = null;
        if (state == null) {
            if (addRoadConstructionProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addRoadConstructionProjectViewController = loader.getController();
                    addRoadConstructionProjectViewController.init(this, model, root,
                            state, function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addRoadConstructionProjectViewController.reset(function, state);
        } else {
            if (addRoadConstructionProjectViewController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(fxmlFile));
                    root = loader.load();
                    addRoadConstructionProjectViewController = loader.getController();
                    addRoadConstructionProjectViewController.init(this, model, root,
                            state, function);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                addRoadConstructionProjectViewController.reset(function, state);
        }
        return addRoadConstructionProjectViewController.getRoot();
    }

    /**
     * A method responsible for opening the window containing reports on completed projects.
     *
     * @param fxmlFile the fxml file to load
     * @return the root to load.
     */
    private Region loadReportsView(String fxmlFile) {
        Region root = null;
        if (reportsViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                root = loader.load();
                reportsViewController = loader.getController();
                reportsViewController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            reportsViewController.reset();
        return reportsViewController.getRoot();
    }

    /**
     * A method responsible for loading the update view for updating currently viewed project.
     *
     * @param fxmlFile the fxml file to load
     * @param state    the current view state
     * @return root to load.
     */
    private Region loadUpdateView(String fxmlFile, ViewState state) {
        Region root = null;
        if (updateViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                root = loader.load();
                updateViewController = loader.getController();
                updateViewController.init(model, this, state, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else updateViewController.reset(state);
        return updateViewController.getRoot();
    }

    /**
     * A method to close view
     */
    public void closeView() {
        primaryStage.close();
    }

}



