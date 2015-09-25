package calculator;

/**
 * Interface to hold all Constants for the UI of the Calculator
 * @author HaydnSlade;
 * @version 1.00 2014-12-14
 */
public interface CalculatorGUI 
{
    /* Constants for JavaFX UI */
    final int SCENE_WIDTH = 210;
    final int SCENE_HEIGHT = 310;
    
    final int HBOX_PAD_TOP = 5;
    final int HBOX_PAD_RGT = 5;
    final int HBOX_PAD_BOT = 5;
    final int HBOX_PAD_LFT = 8; //Different since the UI wasn't centering
    final int HBOX_WIDTH = 195;
    final int HBOX_HEIGHT = 40;
    
    final int TEXT_PAD_TOP = 10;
    final int TEXT_PAD_RGT = 5;
    final int TEXT_PAD_BOT = 10;
    final int TEXT_PAD_LFT = 8;
    
    final int FLOW_PAD_TOP = 5;
    final int FLOW_PAD_RGT = 5;
    final int FLOW_PAD_BOT = 5;
    final int FLOW_PAD_LFT = 8;
    final int FLOW_VGAP = 5;
    final int FLOW_HGAP = 5;
    final int FLOW_WRAP = 200;
    
    final int BUTTON_WIDTH = 45;
    final int BUTTON_HEIGHT = 45;
    
    final int ZERO_BUTTON_WIDTH = 95;
    
    final int PERCENT = 100;
    
    final int DECIMAL = 10;
    
    final String basicButtonStyle = "-fx-base: lightslategrey";
    final String opButtonStyle = "-fx-base: lightsteelblue";
    final String clearButtonStyle = "-fx-base: crimson";
    final String equalButtonStyle = "-fx-base: steelblue";
    
    final String textFieldFont = "VERDANA";
    final int textFieldSize = 16;
    
    final int BUTTON_COLS = 4;
    
}