// Tyler Yang week 10 11/8/20 - Homework 10 Manual GUI extending/using example + lecture code
// Add (X), Remove(X), and Calculate (X)

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class YangManualGUI extends JFrame implements ActionListener {

    static JTextField userTxt;                                              // User input
    static JTextArea outputTxt;
    static JFrame frm;
    static JButton addBtn;
    static JButton removeBtn;
    static JButton calculateBtn;
    static JLabel lbl;
    static JList numberList;                                                // List
    static DefaultListModel<String> listModel;

    public YangManualGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);                            // Stop program when closing window
    }
    public static void defaultOutput(){
        outputTxt.setText("Enter a number above and then click 'Add Number' to add it to the list to the left." + "\n" +
                "Click on a number on the left and then click 'Remove Number' to remove it from the list" + "\n" +
                "Click calculate for the median, mode, and mean of the list");
    }


    // Called when calculateBtn/"Calculate" is pressed
    //
    // Simply a modified version of previous homework's code to work with this
    public static void Calculate(){
        // Create an ArrayList of Doubles and sorts it
        ArrayList<Double> listArray = new ArrayList<>();
        for (int i=0; i < listModel.getSize(); i++){
            listArray.add(Double.parseDouble(listModel.get(i)));
        }
        Collections.sort(listArray);
        int size = listArray.size();

        //Mean
        double mean=0;
        for (Double d: listArray){
            mean+=d;
        }
        mean/=size;

        // Median
        double median = 0;
        if ((size % 2) != 0 ){
            median = listArray.get((size-1)/2);
        }else{
            double left = listArray.get(size/2);
            double right = listArray.get((size/2)-1);
            median = (left + right)/2;
        }

        //Mode/Modes
        // Mode
        // Will account for multiple numbers with the highest amounts in an ArrayList modes
        // uses the Set collections found online to get rid of duplicate modes
        ArrayList<Double> modes = new ArrayList<>();
        double high = -9999999;
        double count = -1;
        for (double number1: listArray){
            for (double number2 : listArray){
                if (number1 == number2){
                    count++;
                }
            }

            if (count == high) {                                            // This allows for multiple modes
                modes.add(number1);

            } else if (count > high){                                       // This will overwrite the list of modes
                high = count;
                modes.clear();
                modes.add(number1);
            }
            count = -1;
        }
        Set<Double> set = new HashSet<Double>(modes);
        modes.clear();
        modes.addAll(set);

        defaultOutput();
        outputTxt.append("\n" + "\n" + "Results:");
        outputTxt.append( "\n" + "Median: " + median + "\n");
        outputTxt.append("Mean: " + mean + "\n");
        outputTxt.append("Mode/Modes: ");
        for (double d: modes){
            if (modes.get(modes.size()-1) == d){
                outputTxt.append(String.valueOf(d));
            }else{
                outputTxt.append(d + ", ");
            }
        }
    }

    public static void main(String[] args){

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        //Create panel for list (Border Layout)
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1,2,4,5));

        // Frame
        YangManualGUI frm = new YangManualGUI();

        // Buttons + labels
        lbl = new JLabel("Enter a number");
        addBtn = new JButton("Add Number");
        removeBtn = new JButton("Remove Number");
        calculateBtn = new JButton("Calculate");
        userTxt = new JTextField(16);

        //add the input controls to our input panel
        inputPanel.add(lbl);
        inputPanel.add(userTxt);
        inputPanel.add(addBtn);
        inputPanel.add(removeBtn);
        inputPanel.add(calculateBtn);

        // Create a container w/ border layout
        Container myContainer = frm.getContentPane();
        myContainer.setLayout(new BorderLayout());
        //add our inputPanel to the TOP of our root
        myContainer.add(inputPanel,BorderLayout.NORTH);

        //create and show list panel
        listModel = new DefaultListModel<String>();
        numberList = new JList(listModel);
        listPanel.add(numberList);


        // create, show, and set outputTxt panel
        outputTxt = new JTextArea();
        defaultOutput();

        listPanel.add(outputTxt);

        // Buttons -> action listener
        addBtn.addActionListener(frm);
        removeBtn.addActionListener(frm);
        calculateBtn.addActionListener(frm);

        //add listPanel to the center.
        myContainer.add(listPanel, BorderLayout.CENTER);

        // Set window size
        frm.setSize(850,350);
        frm.setVisible(true);
    }



    public void actionPerformed(ActionEvent event){
        String s = event.getActionCommand();

        // Add:
        // Check if userTxt is a valid double data type
        // Appends the data to the Jlist numberList
        if(s.equals("Add Number")){
            try{
                double test = Double.parseDouble(userTxt.getText());
                listModel.addElement(String.valueOf(test));
                //numberList = new JList(listModel);                            // Caused errors for remove
            } catch (Exception ex){
                defaultOutput();
                outputTxt.append("\n" + "\n" + "Please Enter a valid number!!!");
            }
        }
        // Remove Number:
        // Set to remove the selected number from the list
        else if(s.equals("Remove Number")){
            try {
                int index = numberList.getSelectedIndex();
                listModel.remove(index);
            } catch (Exception ex) {
                defaultOutput();
                outputTxt.append("\n" + "\n" + "Error removing number or no number was selected!!");
            }

        }
        // Calculate:
        // calls Calculate() if the listModel isn't empty
        else if(s.equals("Calculate")) {
            if (listModel.size() == 0){
                defaultOutput();
                outputTxt.append("\n" + "\n" + "No numbers have been added to the list yet!!");
            } else{
                Calculate();
            }
        }
    }
}

