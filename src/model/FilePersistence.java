package model;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
/**
 * A class used for saving ongoing and completed projects list
 * as well as saving update list of each project in a text file
 * It also is used for saving ongoing project to xml file for website usage
 *
 * @author Matej Palas
 */
public class FilePersistence  {
    private String ongoingTextFile, completedTextFile, ongoingXmlFile, ongoingUpdateFile;

    /**
     *  A no argument constructor loading string to according file path
     * */
    public FilePersistence(){

        //  Paths
        ongoingTextFile = "files/ongoingProjects.txt";
        completedTextFile = "files/completedProjects.txt";

        ongoingUpdateFile = "files/listOfUpdates.txt";

        ongoingXmlFile = "website/xml/xmlFile.xml";
    }
    //  Saving to XML file
    /**
     *  A method Saving the specified list of ongoing projects to XML file
     * @param list getting list of ongoing projects that are going to be saved and displayed on the website
     * @throws ParserConfigurationException exceptions for handling the ParserConfig in DOM parsing
     * @throws TransformerException exception for handling the Transformer exceptions
     * */
    public void saveXML(OngoingProjectsList list) throws ParserConfigurationException, TransformerException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("onGoingProjects");

            for (int i = 0; i < list.getSize(); i++) {
                Element project = doc.createElement("project");
                project.setAttribute("type", list.getProject(i).getType());
                project.setAttribute("schedule", list.getProject(i).getStatus());

                //  ID
                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(list.getProject(i).getId()));
                project.appendChild(id);
                //  budget
                Element budget = doc.createElement("budget");
                budget.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getBudget())));
                project.appendChild(budget);
                //  Date
                Element date = doc.createElement("date");
                Element day = doc.createElement("day");
                Element month = doc.createElement("month");
                Element year = doc.createElement("year");
                day.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getStartDate().getDay())));
                month.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getStartDate().getMonth())));
                year.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getStartDate().getYear())));
                date.appendChild(day);
                date.appendChild(month);
                date.appendChild(year);
                project.appendChild(date);
                //  Expected Duration
                Element expectedDuration = doc.createElement("expectedDuration");
                expectedDuration.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getExpectedDurationInMonths())));
                project.appendChild(expectedDuration);
                //  Expected Expenses
                Element expectedExpenses = doc.createElement("expectedExpenses");
                expectedExpenses.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getExpectedExpenses())));
                project.appendChild(expectedExpenses);
                //  Estimated Total Hours
                Element estimatedTotalHours = doc.createElement("estimatedTotalHours");
                estimatedTotalHours.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getEstimatedTotalHours())));
                project.appendChild(estimatedTotalHours);
                //  Resources -> ManHoursUsed, EstimatedTotalHours,salaryExpenses, materialExpenses, totalExpenses
                Element resources = doc.createElement("resources");
                Element manHoursUsed = doc.createElement("manHoursUsed");
                Element salaryExpenses = doc.createElement("salaryExpenses");
                Element materialExpenses = doc.createElement("materialExpenses");
                Element totalExpenses = doc.createElement("totalExpenses");
                manHoursUsed.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getManHoursUsed())));
                salaryExpenses.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getSalaryExpenses())));
                materialExpenses.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getMaterialsExpenses())));
                totalExpenses.appendChild(doc.createTextNode(String.valueOf(list.getProject(i).getResources().getTotalExpenses())));
                resources.appendChild(manHoursUsed);
                resources.appendChild(salaryExpenses);
                resources.appendChild(materialExpenses);
                resources.appendChild(totalExpenses);
                project.appendChild(resources);


                rootElement.appendChild(project);
            }
            //  Final
            doc.appendChild(rootElement);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            File file = new File(ongoingXmlFile);
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        }
        catch (Exception e) { e.printStackTrace();}

    }

    //  Reading and writing with text files, because of its similarities in functionality with database... (easy transition)
    //  Only using OnGoingProjects for now
    /**
     * A method for writing specified list information to specified path for text file
     *
     * @param list specified projects list information that is going to be stored in text file
     * @param filename path to where it is meant to be created
     * */
    /*
    This method write the data from list in the text file
     */
    public void writeToTextFile(ProjectsList list, String filename)
    {
        try (PrintWriter out = new PrintWriter(filename)){  //  using try resources, declaring it in argument

            //  Writing size of the list first
            out.println(list.getSize());

            for (int i = 0; i < list.getSize(); i++) {
                //  Project
                out.println(list.getProject(i).getType());  //  Type first for reading
                out.println(list.getProject(i).getId());
                out.println(list.getProject(i).getBudget());
                out.println(list.getProject(i).getStartDate().getDay());
                out.println(list.getProject(i).getStartDate().getMonth());
                out.println(list.getProject(i).getStartDate().getYear());
                out.println(list.getProject(i).getExpectedDurationInMonths());
                //  Status
                out.println((list.getProject(i).getIsNotBehindTheSchedule()) ? 1 : 0);

                //  Resources
                out.println(list.getProject(i).getResources().getManHoursUsed());
                out.println(list.getProject(i).getResources().getSalaryExpenses());
                out.println(list.getProject(i).getResources().getMaterialsExpenses());

                out.println(list.getProject(i).getResources().getExpectedExpenses());
                out.println(list.getProject(i).getResources().getEstimatedTotalHours());


                switch (list.getProject(i).getType()) {
                    case "Residential" -> {
                        out.println(((Residential) list.getProject(i)).getSize());
                        out.println(((Residential) list.getProject(i)).getNumberOfKitchens());
                        out.println(((Residential) list.getProject(i)).getNumberOfBathrooms());
                        out.println(((Residential) list.getProject(i)).getNumberOfOtherRoomsWithPlumbing());
                        out.println((((Residential) list.getProject(i)).getIsNewBuild()) ? 1 : 0);
                    }
                    case "Commercial" -> {
                        out.println(((Commercial) list.getProject(i)).getSize());
                        out.println(((Commercial) list.getProject(i)).getUseOfBuilding());
                        out.println(((Commercial) list.getProject(i)).getNumberOfFloors());
                    }
                    case "Industrial" -> {
                        out.println(((Industrial) list.getProject(i)).getSize());
                        out.println(((Industrial) list.getProject(i)).getUseOfBuilding());
                    }
                    case "Road Construction" -> {
                        out.println(((RoadConstruction) list.getProject(i)).getLength());
                        out.println(((RoadConstruction) list.getProject(i)).getWidth());
                        out.println(((RoadConstruction) list.getProject(i)).getNumberOfBridges());
                        out.println(((RoadConstruction) list.getProject(i)).getNumberOfTunnels());
                        out.println(((RoadConstruction) list.getProject(i)).getEnvironmentalChallenges());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method used for reading an ongoing list projects from text file
     *
     * @return list class storing the list of ongoing projects that are read from text file accordingly
     * */

    public OngoingProjectsList loadOnGoingFromTxt(){
        OngoingProjectsList temp = new OngoingProjectsList();

        try {
            BufferedReader in = new BufferedReader(new FileReader(ongoingTextFile));

            int listSize = Integer.parseInt(in.readLine());

            for (int i = 0; i < listSize; i++) {
                String line = in.readLine();
                String id = in.readLine();
                double budget = Double.parseDouble(in.readLine());
                int day = Integer.parseInt(in.readLine());
                int month = Integer.parseInt(in.readLine());
                int year = Integer.parseInt(in.readLine());
                int expectedDuration = Integer.parseInt(in.readLine());

                boolean isNotBehindSchedule = Integer.parseInt(in.readLine()) == 1;

                double manHoursUsed = Double.parseDouble(in.readLine());
                double salaryExpenses = Double.parseDouble(in.readLine());
                double materialExpenses = Double.parseDouble(in.readLine());

                double expectedExpenses = Double.parseDouble(in.readLine());
                int estimatedTotalHours = Integer.parseInt(in.readLine());

                switch (line) {
                    case "Residential" -> {
                        double size = Double.parseDouble(in.readLine());
                        int numberOfKitchens = Integer.parseInt(in.readLine());
                        int numberOfBathrooms = Integer.parseInt(in.readLine());
                        int numberOfRoomsWithPlumbing = Integer.parseInt(in.readLine());
                        boolean isNewBuild = Integer.parseInt(in.readLine()) == 1;

                        Residential project = new Residential(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size,numberOfKitchens,numberOfBathrooms,numberOfRoomsWithPlumbing,isNewBuild);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);

                        temp.addProject(project);

                    }
                    case "Commercial" -> {
                        double size = Double.parseDouble(in.readLine());
                        String useOfBuilding = in.readLine();
                        int numberOfFloors = Integer.parseInt(in.readLine());

                        Commercial project = new Commercial(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size, useOfBuilding, numberOfFloors);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);

                        temp.addProject(project);
                    }
                    case "Industrial" -> {
                        double size = Double.parseDouble(in.readLine());
                        String useOfBuilding = in.readLine();

                        Industrial project = new Industrial(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size, useOfBuilding);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);

                        temp.addProject(project);
                    }
                    case "Road Construction" -> {
                        double length = Double.parseDouble(in.readLine());
                        double width = Double.parseDouble(in.readLine());
                        int numberOfBridges = Integer.parseInt(in.readLine());
                        int numberOfTunnels = Integer.parseInt(in.readLine());
                        String environmentalChallenges = in.readLine();

                        RoadConstruction project = new RoadConstruction(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                length,width,numberOfBridges,numberOfTunnels,environmentalChallenges);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);

                        temp.addProject(project);
                    }
                }
            }

            in.close();
        } catch (IOException e) {e.printStackTrace();}

        return temp;
    }
    /**
     * A method used for reading a completed list of projects from text file
     *
     * @return list class storing the list of completed projects that are read from text file accordingly
     * */
    public CompletedProjectsList loadCompletedFromTxt(){
        CompletedProjectsList temp = new CompletedProjectsList();

        try {
            BufferedReader in = new BufferedReader(new FileReader(completedTextFile));

            int listSize = Integer.parseInt(in.readLine());

            for (int i = 0; i < listSize; i++) {
                String line = in.readLine();
                String id = in.readLine();
                double budget = Double.parseDouble(in.readLine());
                int day = Integer.parseInt(in.readLine());
                int month = Integer.parseInt(in.readLine());
                int year = Integer.parseInt(in.readLine());
                int expectedDuration = Integer.parseInt(in.readLine());

                boolean isNotBehindSchedule = Integer.parseInt(in.readLine()) == 1;

                double manHoursUsed = Double.parseDouble(in.readLine());
                double salaryExpenses = Double.parseDouble(in.readLine());
                double materialExpenses = Double.parseDouble(in.readLine());

                double expectedExpenses = Double.parseDouble(in.readLine());
                int estimatedTotalHours = Integer.parseInt(in.readLine());

                switch (line) {
                    case "Residential" -> {
                        double size = Double.parseDouble(in.readLine());
                        int numberOfKitchens = Integer.parseInt(in.readLine());
                        int numberOfBathrooms = Integer.parseInt(in.readLine());
                        int numberOfRoomsWithPlumbing = Integer.parseInt(in.readLine());
                        boolean isNewBuild = Integer.parseInt(in.readLine()) == 1;

                        Residential project = new Residential(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size,numberOfKitchens,numberOfBathrooms,numberOfRoomsWithPlumbing,isNewBuild);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);
                        project.setIsOngoing(false);

                        temp.addProject(project);

                    }
                    case "Commercial" -> {
                        double size = Double.parseDouble(in.readLine());
                        String useOfBuilding = in.readLine();
                        int numberOfFloors = Integer.parseInt(in.readLine());

                        Commercial project = new Commercial(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size, useOfBuilding, numberOfFloors);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);
                        project.setIsOngoing(false);

                        temp.addProject(project);
                    }
                    case "Industrial" -> {
                        double size = Double.parseDouble(in.readLine());
                        String useOfBuilding = in.readLine();

                        Industrial project = new Industrial(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                size, useOfBuilding);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);
                        project.setIsOngoing(false);

                        temp.addProject(project);
                    }
                    case "Road Construction" -> {
                        double length = Double.parseDouble(in.readLine());
                        double width = Double.parseDouble(in.readLine());
                        int numberOfBridges = Integer.parseInt(in.readLine());
                        int numberOfTunnels = Integer.parseInt(in.readLine());
                        String environmentalChallenges = in.readLine();

                        RoadConstruction project = new RoadConstruction(id,budget,new SimpleDate(day,month,year),expectedDuration, new Resources(expectedExpenses,estimatedTotalHours),
                                length,width,numberOfBridges,numberOfTunnels,environmentalChallenges);

                        project.getResources().setManHours(manHoursUsed);
                        project.getResources().setSalaryExpenses(salaryExpenses);
                        project.getResources().setMaterialsExpenses(materialExpenses);

                        project.setIsNotBehindTheSchedule(isNotBehindSchedule);
                        project.setIsOngoing(false);

                        temp.addProject(project);
                    }
                }
            }

            in.close();
        } catch (IOException e) {e.printStackTrace();}

        return temp;
    }

    //  This method only works for individual project, because of ID usage
    /**
     * A method used for, if found getting the according queue of updates for specified project
     *
     * @param project checks if the file stores updates under the current id in updateList files
     *
     * @return returns the queue of update if project under id has no updates returns empty queue
     * */
    public Deque<String> loadUpdatesFromOngoing(Project project){
        Deque<String> temp = new ArrayDeque<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(ongoingUpdateFile));

            //  Looping until ID matches, then getting the count of updates and returning it
            String checkID = "";
            while (!checkID.equals(project.getId())){checkID = in.readLine();}

            int countOfUpdates = Integer.parseInt(in.readLine());
            for (int j = 0; j < countOfUpdates; j++) {
                temp.offer(in.readLine());
            }


            in.close();
        } catch (IOException e) {e.printStackTrace();}

        return temp;
    }

    /**
     * A method used for storing the updates of each individual project of specified project list in a text file
     *
     * @param list a list of projects that updates are being stored in a text file
     *
     * @param filename path to where the text file is being saved
     * */
    public void saveAllUpdateLists(ProjectsList list, String filename){
        try (PrintWriter out = new PrintWriter(filename)){  //  using try resources, declaring it in argument

            for (int i = 0; i < list.getSize(); i++) {
                out.println(list.getProject(i).getId());    //  Writing ID for matching and assigning updates accordingly
                out.println(list.getProject(i).getResources().getUpdates().size()); //  Writing how many updates are in the project
                out.println(list.getProject(i).getResources()); //  Writing the updates, using toString method of Resources
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
