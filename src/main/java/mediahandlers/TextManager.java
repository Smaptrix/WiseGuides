package mediahandlers;

//CREATED BY ENTROPY DESIGNS FOR MAPTRIX

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TextManager{

    protected int mediaID;
    protected String mediaName;
    protected int positionX;
    protected int positionY;
    protected int width = 200;
    protected int height = 100;
    protected String filePath;
    private Font font = new Font("Times New Roman", 12);    //Default font
    private String content = "";
    private Color colour = Color.BLACK; //Default colour
    private Text textArea;
    private boolean underline = false;
    private boolean fromFile = true;
    private boolean loaded = false;

    public TextManager(String fileLocation, int width, int height){
        this.width = width;
        this.height = height;
        this.filePath = fileLocation;
        this.loadTextFromFile();

    }

    public void loadTextFromFile(){
        //Read the data from the text file and store in the content attribute.
        try{
            File textFile = new File(filePath);
            Scanner fileReader = new Scanner(textFile);
            while(fileReader.hasNextLine()){
                content = content + fileReader.nextLine() + "\n";
            }
        }
        catch (FileNotFoundException e){
            content = "*UNABLE TO IMPORT TEXT, FILE NOT FOUND*";
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }

        //Attach the content to the JPanel displayScene attribute.
        this.textArea = new Text();
        //Set attributes
        textArea.setFont(font);
        textArea.setText(content);
        textArea.setFill(colour);
        textArea.setUnderline(underline);
        textArea.setTextAlignment(TextAlignment.CENTER);
        textArea.setWrappingWidth(width);

    }

    public Text getTextArea(){
        return this.textArea;
    }

    //Font name as a string of format: "Times New Roman", "Arial", "Helvetica" etc
    public void setFont(String fontName, int size, boolean bold, boolean italics) {
        FontWeight fontWeight = FontWeight.NORMAL;
        FontPosture fontPosture = FontPosture.REGULAR;
        if(bold){
            fontWeight = FontWeight.BOLD;
        }
        if(italics){
            fontPosture = FontPosture.ITALIC;
        }
        this.font = Font.font(fontName, fontWeight, fontPosture, size);
    }

    //Takes a hex rgb/rgba value as a string
    public void setColour(String colourValue){
        this.colour = Color.web(colourValue);
    }

    public void setUnderline(boolean underline){
        this.underline = underline;
    }

    public void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
