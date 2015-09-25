package calculator;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Calculator application
 * Implements a calculator by using JavaFX
 * @author HaydnSlade;
 * @version 1.05 2014-12-14
 */
public class Calculator extends Application implements CalculatorGUI
{
    /* Public Features */
    
    /**
     * start
     * Essentially main, as it implements the main JavaFX stage to be shown
     * @param primaryStage stage that should be started with
     */
    @Override
    public void start(Stage primaryStage)
    {
        
        StackPane root = new StackPane();
        GridPane mainGrid = createGridPane();
        HBox calcDisplay = createComputationDisplay();
        GridPane calcControls = createControlsPaneGrid();
        
        mainGrid.add(calcDisplay, 0, 0);
        mainGrid.add(calcControls, 0, 1);
        
        root.getChildren().add(mainGrid);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.GREY);

        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    

    /* Private features */
    
    // String arrary or arrays to handle the button layout
    private static final String[][] buttonTemplate = {
        {"C", "+/-", "%", "/"},
        {"7", "8", "9", "*"},
        {"4", "5", "6", "-"},
        {"1", "2", "3", "+"},
        {"0", "", ".", "="}
    };
    
    // Available operations
    private enum Operation {NO_OP, ADD, DIVIDE, MULTIPLY, SUBTRACT, DECIMAL};
    
    // Current and last operation
    private Operation curOp = Operation.NO_OP;
    private Operation stackOp = Operation.NO_OP;
    
    // Current and last value
    private DoubleProperty curValue = new SimpleDoubleProperty();
    private DoubleProperty stackValue = new SimpleDoubleProperty();
    
    /* Current value and DecimalFormat to try and get the output displaying
     * properly. I tried a few options like Bindings.format(), but a generic
     * format does not work well.
     * Instead it worked best to write out to a StringProperty with the format
     * after each change of curValue instead of running off curValue directly.
    */
    private StringProperty curValueDisp = new SimpleStringProperty();
    private DecimalFormat dispFormat = new DecimalFormat("0.###############");
    
    /**
     * createComputationDisplay
     * Creates the top level display including the display window.
     * Binds the curValueDisp to the textbox to have easy updates.
     * @return HBox that contains the top box with the text display
     */
    private HBox createComputationDisplay()
    {
        HBox horizBox = new HBox();
        horizBox.setPadding(new Insets(HBOX_PAD_TOP, HBOX_PAD_RGT, HBOX_PAD_BOT,
                HBOX_PAD_LFT));
        horizBox.setPrefSize(HBOX_WIDTH, HBOX_HEIGHT);
        
        TextField display = new TextField();
        display.setPrefSize(HBOX_WIDTH, HBOX_HEIGHT); // Same as HBOX to fill
        display.setFont(Font.font(textFieldSize));
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(false);
        display.setPadding(new Insets(TEXT_PAD_TOP, TEXT_PAD_RGT, TEXT_PAD_BOT,
                TEXT_PAD_LFT));
        display.textProperty().bind(curValueDisp);
        curValueDisp.setValue(dispFormat.format(curValue.get()));
        
        horizBox.getChildren().addAll(display);
        return horizBox;
    }
    
    /**
     * createControlsPane
     * Creates the bottom level display area that includes the controls and
     * numbers.
     * Utilizes the buttons template, should be easy to append new buttons.
     * @deprecated Use createControlsPaneGrid() instead
     * @return FlowPane that includes all buttons
     */
    private FlowPane createControlsPane()
    {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(FLOW_PAD_TOP, FLOW_PAD_RGT, FLOW_PAD_BOT,
                FLOW_PAD_LFT));
        flow.setVgap(FLOW_VGAP);
        flow.setHgap(FLOW_HGAP);
        flow.setPrefWrapLength(FLOW_WRAP);
        
        for (String[] r: buttonTemplate)
        {
            for (String s: r)
            {
                if (!s.isEmpty())
                {
                    flow.getChildren().add(createButton(s));
                }
            }
        }
        return flow;
    }
    
    /**
     * Creates the control buttons in a gridpane rather than a flowpane
     * @return GridPane for button layout
     */
    private GridPane createControlsPaneGrid()
    {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(FLOW_PAD_TOP, FLOW_PAD_RGT, FLOW_PAD_BOT,
                FLOW_PAD_LFT));
        grid.setVgap(FLOW_VGAP);
        grid.setHgap(FLOW_HGAP);
        
        int row = 0;
        int col = 0;
        for (String[] r: buttonTemplate)
        {
            for (String s: r)
            {
                if (!s.isEmpty())
                {
                    Button nextButton = createButton(s);
                    grid.add(nextButton, col++, row);
                    
                    if ("0".equals(nextButton.getText()))
                    {
                        GridPane.setColumnSpan(nextButton, 2);
                        nextButton.setMaxHeight(Double.MAX_VALUE);
                        nextButton.setMaxWidth(Double.MAX_VALUE);
                        col++;
                    }
                    if (col >= BUTTON_COLS)
                    {
                        col = 0;
                        row++;
                    }
                    //grid.getChildren().add(createButton(s));
                }
            }
        }
        return grid;
    }
    
    /**
     * createGridPane
     * Creates a GridPane, currently nothing else, but easy to add additional options.
     * @return GridPane to be used for the grid to separate the top and bottom
     */
    private GridPane createGridPane()
    {
        GridPane grid = new GridPane();
        return grid;
    }
    
    /**
     * createButton
     * Creates a button and depending on the disp will change the type of button
     * created.
     * @param disp string for display
     * @return Button that can be either a number or operation button such as add
     */
    private Button createButton(final String disp)
    {
        Button button = new Button(disp);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setStyle(basicButtonStyle);
        
        if (disp.matches("[0-9]"))
        {
            configNumericButton(disp, button);
        }
        else
        {
            final ObjectProperty<Operation> buttonOp = determineOperation(disp);
            if (buttonOp.get() != Operation.NO_OP)
            {
                configOpButton(buttonOp, button);
            }
            else if ("=".equals(disp))
            {
                configEqualsButton(button);
            }
            else if ("C".equals(disp))
            {
                configClearButton(button);
            }
            else if ("+/-".equals(disp))
            {
                configInvertButton(button);
            }
            else if ("%".equals(disp))
            {
                configPercentButton(button);
            }
            else if (".".equals(disp))
            {
                configDecimalButton(button);
            }
        }
        return button;
    }
    
    /**
     * determineOperation
     * Handles the creation of the a Property containing the particular operation
     * for a string to be used in a button.
     * This will make it extremely easy to implement keypresses if wanted.
     * @param oper String representing the operation to be created
     * @return ObjectProperty<Operation> a property that contains the operation
     * from the String
     */
    private ObjectProperty<Operation> determineOperation(final String oper)
    {
        final ObjectProperty<Operation> buttonOp 
                = new SimpleObjectProperty<>(Operation.NO_OP);
        switch (oper)
        {
            case "+": buttonOp.set(Operation.ADD);
                break;
            case "-": buttonOp.set(Operation.SUBTRACT);
                break;
            case "/": buttonOp.set(Operation.DIVIDE);
                break;
            case "*": buttonOp.set(Operation.MULTIPLY);
                break;
        }
        
        return buttonOp;
    }
    
    /**
     * configOpButton
     * Configures the button to work with a specific Operation property.
     * @param buttonOp Operation Property to be used on action
     * @param button button to configure
     */
    private void configOpButton(final ObjectProperty<Operation> buttonOp,
            Button button)
    {
        button.setStyle(opButtonStyle);
        button.setOnAction((ActionEvent e) ->
        {
           curOp = buttonOp.get();
        });
    }
    
    /**
     * configClearButton
     * Configures the Clear button to clear the current state.
     * @param button Button that will execute the clear functionality
     */
    private void configClearButton(Button button)
    {
        button.setStyle(clearButtonStyle);
        button.setOnAction((ActionEvent e) ->
        {
           curValue.set(0);
           curValueDisp.setValue(dispFormat.format(curValue.get()));
           curOp = Operation.NO_OP;
        });
    }
    
    /**
     * configEqualsButton
     * Configure the equals button. Runs the stack value and the current value
     * with the current operation.
     * @param button Button to configure with equals options
     */
    private void configEqualsButton(Button button)
    {
        button.setStyle(equalButtonStyle);
        button.setOnAction((ActionEvent e) ->
        {
            switch (stackOp)
            {
                case ADD: curValue.set(stackValue.get() + curValue.get());
                    break;
                case SUBTRACT: curValue.set(stackValue.get() - curValue.get());
                    break;
                case DIVIDE: curValue.set(stackValue.get() / curValue.get());
                    break;
                case MULTIPLY: curValue.set(stackValue.get() * curValue.get());
                    break;
                case DECIMAL: curOp = Operation.NO_OP;
            }
            curValueDisp.setValue(dispFormat.format(curValue.get()));
        });
    }
    
    /**
     * configNumericButton
     * Configure numeric buttons 0-9
     * Runs one of three different operations, updates curValue to the number pressed,
     * tries to add a decimal, or moves numbers and operations on the stack to be
     * evaluated via equals.
     * @param num String representation of the number being pressed
     * @param button Button for the number
     */
    private void configNumericButton(final String num, Button button)
    {
        if ("0".equals(num))
        {
            button.setPrefWidth(ZERO_BUTTON_WIDTH);
        }
        
        button.setOnAction((ActionEvent e) ->
        {
           if (curOp == Operation.NO_OP)
           {
               if (curValue.get() != 0)
               {
                   curValue.set((curValue.get() * DECIMAL)
                           + Integer.parseInt(num));
               }
               else
               {
                    curValue.set(Integer.parseInt(num));
               }
           }
           else if (curOp == Operation.DECIMAL)
           {
               String decimals = Double.toString(curValue.get());
               int totDec = decimals.substring(decimals.indexOf(".")).length();
               if ("0".equals(decimals.substring(decimals.indexOf(".") + 1)))
               {
                   totDec -= 1;
               }
               /* Double representation is terrible. This would work MUCH better
                * if I was using BigDecimal.
               */
               curValue.set(curValue.get() + ((double) (Integer.parseInt(num)) 
                       / (double)(DECIMAL * totDec)));
           }
           else
           {
               stackValue.set(curValue.get());
               curValue.set(Integer.parseInt(num));
               stackOp = curOp;
               curOp = Operation.NO_OP;
           }
           curValueDisp.setValue(dispFormat.format(curValue.get()));
        });
    }
    
    /**
     * configInvertButton
     * Configure the negative button to flip the sign of current number
     * @param button Button to configure to be invert
     */
    private void configInvertButton(Button button)
    {
        button.setStyle(opButtonStyle);
        button.setOnAction((ActionEvent e) ->
        {
            curValue.set(curValue.get() * -1);
            curValueDisp.setValue(dispFormat.format(curValue.get()));
            curOp = Operation.NO_OP;
        });
    }
    
    /**
     * configPercentButton
     * Configure the percentage button that divides the current number by 100
     * @param button Button to configure to be percentage button
     */
    private void configPercentButton(Button button)
    {
        button.setStyle(opButtonStyle);
        button.setOnAction((ActionEvent e) ->
        {
            curValue.set(curValue.get() / PERCENT);
            curValueDisp.setValue(dispFormat.format(curValue.get()));
            curOp = Operation.NO_OP;
        });
    }
    
    /**
     * configDecimalButton
     * Configure decimal button, which modifies how the number buttons work.
     * @param button Button to implement the decimal operation on
     */
    private void configDecimalButton(Button button)
    {
        button.setOnAction((ActionEvent e) ->
        {
           curOp = Operation.DECIMAL;
        });
    }
}