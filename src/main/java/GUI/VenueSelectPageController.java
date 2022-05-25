package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Controls the GUI page that the user can use to have a venue selected for them
 */
public class VenueSelectPageController {

    /**
     * The label that displays any error information
     */
    @FXML
    Label errLabel;


    /**
     * The checkbox to indicate a user only wants to see their favourite venues
     */
    @FXML
    CheckBox faveCheckBox;

    /**
     * The checkbox to indicate a user wants to see food venues
     */
    @FXML
    CheckBox foodCheckBox;

    /**
     * The checkbox to indicate a user wants to see drinking venues
     */
    @FXML
    CheckBox drinksCheckBox;

    /**
     * The checkbox to indicate a user wants to see sightseeing venues
     */
    @FXML
    CheckBox sightseeingCheckBox;

    /**
     * The checkbox to indicate a user wants to see study space venues
     */
    @FXML
    CheckBox studySpacesCheckBox;




    /**
     * The client that is connected to the server
     */
    Client client;

    /**
     * The current user that is logged into the application
     */
    User currUser;

    /**
     * The list of the venues available for the user to view
     */
    List<String> listOfVenues;

    /**
     * <p>
     *     The xml file containing all of the venue data
     * </p>
     */
    private VenueXMLParser xml;

    /**
     * Sets the client to be used by the controller
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets the current user logged into the app
     * @param currUser the user
     */
    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    /**
     * Sets the list of venues
     * @param listOfVenues the venue list
     */
    public void setListOfVenues(List<String> listOfVenues) {
        this.listOfVenues = new ArrayList<>(listOfVenues);
    }

    /**
     * Sets the current xml file
     * @param xml the xml file
     */
    public void setXml(VenueXMLParser xml) {
        this.xml = xml;
    }

    /**
     * Looks at the options selected by the user, then selects a relevant venue
     * @return true - if a venue was able to be selected with the given criteria
     */
    @FXML
    public boolean onVenuePickButtonPress(){
        //Create a copy of the list of venues so that it isn't edited when the button is pressed
        List<String> listOfPossibleVenues = new ArrayList<>(listOfVenues);;

        //Removes users non-favourite venues from the possible list
        if(faveCheckBox.isSelected()){

            //Get the current users faveourite lists
            List<String> userFaveList = List.of(currUser.getFaveVenues());

            if(userFaveList.isEmpty()){
                errLabel.setText("You have no favourites!");
                return false;
            }

            //Only retain the values that are also stored in the user favourite list
            listOfPossibleVenues.retainAll(userFaveList);

            System.out.println("Current possible list: " + listOfPossibleVenues);
        }

        //This will contain every possible venue from the given criteria
        //It is then compared to the possible list (which is edited by the favourites)
        List<String> tempVenueList = new ArrayList<>();

        //Retains the drinking venues in the possible list
        if(drinksCheckBox.isSelected()){

            //Create the list of drniking venues

            for(String s : listOfPossibleVenues){



                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "nightclub")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "bar")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "pub")){
                    tempVenueList.add(s);
                }

            }

        }

        //Retains the food venues in the possible list
        if(foodCheckBox.isSelected()){

            for(String s : listOfPossibleVenues){



                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "cafe")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "restaurant")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "fast_food")){
                    tempVenueList.add(s);
                }

            }

        }

        //Retains the sightseeing venues in the possible list
        if(sightseeingCheckBox.isSelected()){

            for(String s : listOfPossibleVenues){

                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "green_space")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "sightseeing")){
                    tempVenueList.add(s);
                }

            }

        }

        //Retains the study space venues in the possible list
        if(studySpacesCheckBox.isSelected()) {

            for (String s : listOfPossibleVenues) {

                if (Objects.equals(xml.getPage("title", s).attributes.get("category"), "study_space")) {
                    tempVenueList.add(s);
                }
            }
        }

        //Combines the two lists so that only suitable venues remain

        System.out.println("possible: " + listOfPossibleVenues);
        System.out.println("temp: " + tempVenueList);

        //Makes sure the temp venue list isn't empty or null
        if( tempVenueList == null){
            errLabel.setText("You have not selected any venue types!");
            return false;
        }


        //TODO - CHECK TO SEE IF FINAL LIST IS EMPTY BECAUSE OF FAVOURITES


        listOfPossibleVenues.retainAll(tempVenueList);
        System.out.println("Combined: " + listOfPossibleVenues);


        if(listOfPossibleVenues.isEmpty()){
            errLabel.setText("You have no favourites of the selected types!");
            return false;
        }



        return true;




    }


    /**
     * This opens the venue details page for the given venue
     * @param venueName the name of the given venue
     */
    private void venueDetailsOpener(String venueName){

        //Open that random venues details page
        // TODO: add an extra scene for loading page
        //Opens the generic venue page with the current venue selected which is used to populate the venue information
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("VenueDetailsPage.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        VenueDetailsController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setCurrVenue( venueName, xml.getPage("title", venueName), currUser);
        //Checks to see if the venue has been favourite by the user
        stage.setScene(scene);
        stage.setTitle(venueName);
        stage.show();
        stage.setResizable(false);
        controller.checkIfFavourite();
        try {
            controller.loadVenueData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get venue data");
        }


    }



    /**
     * Selects a random venue from the given venue list
     */
    @FXML
    public void onRandomVenueButtonPress(){
        //Create a random object
        Random rand = new Random();

        //Pick a random item from the venue list
        String randomVenue = listOfVenues.get(rand.nextInt(listOfVenues.size()));

        venueDetailsOpener(randomVenue);

        //Close the venue selector page
        Stage currStage = (Stage) errLabel.getScene().getWindow();
        currStage.close();
    }




}
