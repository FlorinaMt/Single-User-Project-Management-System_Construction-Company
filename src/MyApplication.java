import javafx.application.Application;
import javafx.stage.Stage;
import model.ConstructionCompanyModel;
import model.ConstructionCompanyModelManager;
import view.ViewHandler;

public class MyApplication extends Application
{
    ConstructionCompanyModel model;
    @Override public void start(Stage primaryStage)
    {
        model=new ConstructionCompanyModelManager();
        ViewHandler view = new ViewHandler(model);
        view.start(primaryStage);
    }
}
