/*
CSCI 280 - Object oriented programming
Collin L. Ferguson
Project 2
Due: 5/8/2023
Purpose: Create a java GUI calculator and report my findings.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//https://docs.oracle.com/javase/tutorial/uiswing/components/index.html


public class Project2_CollinFerguson extends JPanel implements ActionListener {

    private String opSymbol;
    private double  numberOne, numberTwo;
    JLabel calcLabel;


    Project2_CollinFerguson() //Constructor
    {
        this.setLayout(null);

        numberOne = Double.NaN; numberTwo = Double.NaN;
        opSymbol = "";

        calcLabel = new JLabel();
        calcLabel.setSize(420,60);
        calcLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calcLabel.setLocation(40,20); //TODO
        add(calcLabel);

        JButton deleteButton = new JButton("Del");
        JButton clearButton = new JButton("Clr");

        deleteButton.setActionCommand("delete");
        clearButton.setActionCommand("clear");

        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);

        add(deleteButton);
        add(clearButton);

        JButton[] buttons = new JButton[10];

        for(int i = 0;i < 10; i++)
        {
            buttons[i] = new JButton(Integer.toString(i));
            buttons[i].setActionCommand(Integer.toString(i));
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        JButton decimalButton = new JButton(".");
        decimalButton.setActionCommand(".");
        decimalButton.addActionListener(this);
        add(decimalButton);

        JButton opAddButton = new JButton("+");
        opAddButton.setActionCommand("+");
        opAddButton.addActionListener(this);
        add(opAddButton);

        JButton opSubButton = new JButton("-");
        opSubButton.setActionCommand("-");
        opSubButton.addActionListener(this);
        add(opSubButton);

        JButton opMultButton = new JButton("*");
        opMultButton.setActionCommand("*");
        opMultButton.addActionListener(this);
        add(opMultButton);

        JButton opDivButton = new JButton("/");
        opDivButton.setActionCommand("/");
        opDivButton.addActionListener(this);
        add(opDivButton);

        JButton enterButton = new JButton("Enter");
        enterButton.setActionCommand("enter");
        enterButton.addActionListener(this);
        add(enterButton);

        buttons[7].setLocation(40,100);  buttons[7].setSize(65,40);
        buttons[4].setLocation(40,160);  buttons[4].setSize(65,40);
        buttons[1].setLocation(40,220);  buttons[1].setSize(65,40);
        buttons[0].setLocation(40,280);  buttons[0].setSize(150,40);

        buttons[8].setLocation(125,100);  buttons[8].setSize(65,40);
        buttons[5].setLocation(125,160);  buttons[5].setSize(65,40);
        buttons[2].setLocation(125,220);  buttons[2].setSize(65,40);

        buttons[9].setLocation(210,100);  buttons[9].setSize(65,40);
        buttons[6].setLocation(210,160);  buttons[6].setSize(65,40);
        buttons[3].setLocation(210,220);  buttons[3].setSize(65,40);
        decimalButton.setLocation(210,280);  decimalButton.setSize(65,40);

        opAddButton.setLocation(295,100);  opAddButton.setSize(65,40);
        opSubButton.setLocation(295,160);  opSubButton.setSize(65,40);
        opMultButton.setLocation(295,220);  opMultButton.setSize(65,40);
        opDivButton.setLocation(295,280);  opDivButton.setSize(65,40);

        clearButton.setLocation(380,100);  clearButton.setSize(65,40);
        deleteButton.setLocation(380,160);  deleteButton.setSize(65,40);
        enterButton.setLocation(380,220);  enterButton.setSize(65,100);
    }


    public void  actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("+") || e.getActionCommand().equals("-") ||
                e.getActionCommand().equals("*") || e.getActionCommand().equals("/"))
        {
            if(opSymbol.equals(""))
            {
                opSymbol = e.getActionCommand();
                calcLabel.setText(calcLabel.getText()+opSymbol);
            }
            else
            {
                String newOpSymbol = e.getActionCommand();

                calcLabel.setText(calcLabel.getText().replace(opSymbol, newOpSymbol));
                opSymbol = newOpSymbol;
            }
        }
        else if(e.getActionCommand().equals("."))
        {
            //if the number contains a decimal point, do nothing.
            if(checkValid(calcLabel.getText()+e.getActionCommand()))
            {
                calcLabel.setText(calcLabel.getText()+e.getActionCommand());
            }
        }
        else if(e.getActionCommand().equals("clear"))
        {
            numberOne = Double.NaN; numberTwo = Double.NaN;
            opSymbol = "";
            calcLabel.setText("");
        }
        else if (e.getActionCommand().equals("delete"))
        {
            if(calcLabel.getText().length() != 0) {
                String temp = calcLabel.getText().substring(0, calcLabel.getText().length() - 1);
                //System.out.format("---Delete Temp: %s\n", temp);

                if (temp.equals("")) {
                    numberOne = Double.NaN;
                    calcLabel.setText(temp);
                } else if (checkValid(temp)) {
                    calcLabel.setText(temp);
                }
            }
        }
        else if (e.getActionCommand().equals("enter"))
        {
            if(!(Double.isNaN(numberOne)) && !(Double.isNaN(numberTwo)))
            {
                double total = 0.0;
                try
                {
                    if(opSymbol.equals("+"))
                    {
                        total = numberOne + numberTwo;
                    }
                    else if (opSymbol.equals("-"))
                    {
                        total = numberOne - numberTwo;
                    }
                    else if(opSymbol.equals("*"))
                    {
                        total = numberOne * numberTwo;
                    }
                    else if (opSymbol.equals("/"))
                    {
                        total = numberOne / numberTwo;
                        if (Double.isInfinite(total))
                        {
                            throw new ArithmeticException("Div by zero!"); //Why does it allow me to div by zero!
                        }
                    }

                    calcLabel.setText(Double.toString(total));
                    numberOne = total; numberTwo = Double.NaN;
                    opSymbol = "";
                }
                catch(Exception junk)
                {
                    numberOne = Double.NaN; numberTwo = Double.NaN;
                    opSymbol = "";
                    calcLabel.setText("Div by Zero error");
                }
            }
        }
        else //If a numeric input was entered.
        {
            if(checkValid(calcLabel.getText()+e.getActionCommand()))
            {
                calcLabel.setText(calcLabel.getText()+e.getActionCommand());
            }
        }
    }


    private Boolean checkValid(String newCalcString)
    {
        try
        {
            String[] temp = (newCalcString.split("[^0-9^.]",2)); //Regex - splits the string
            // on only op symbols. (split if it's not 0-9 or not .)
            numberOne = Double.parseDouble(temp[0]);

            if (temp.length > 1 && !temp[1].equals(""))
            {
                numberTwo = Double.parseDouble(temp[1]);
            }
            else {
                if (!opSymbol.equals(""))
                {
                    opSymbol = ""; //Sets the OpSymbol to nothing if it was deleted.
                }
                if (!Double.isNaN(numberTwo))
                {
                    numberTwo = Double.NaN;
                }
            }
            //calcLabel.setText(newString);
            return true;
        }
        catch(Exception castError)
        {
            //System.out.print("Error! ");
            return false;
        }
    }


    private static void makeCalculator()
    {
        JFrame frame = new JFrame("Calculator - FergusonC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //What happens when you hit the close button?
        //frame.setLayout(null);

        Project2_CollinFerguson contentPane =  new Project2_CollinFerguson();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);

        frame.setSize(505,370);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    public static void main(String[] args)
    {
        makeCalculator();
    }
}