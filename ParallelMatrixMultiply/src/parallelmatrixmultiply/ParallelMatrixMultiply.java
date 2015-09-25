package parallelmatrixmultiply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFx GUI for Matrix Multiplication
 * @author HaydnSlade;
 * @version 1.06 2014-11-16
 */
public class ParallelMatrixMultiply extends Application
{
    /* Public Features */
    
    // Title constants
    static final int MAX_TITLE_HEIGHT = 30;
    static final int TITLE_FONT_SIZE = 16;
    
    // Constants for stage defaults
    static final int DEFAULT_START_HEIGHT = 200;
    static final int DEFAULT_START_WIDTH = 500;
    
    /**
     * Start the Javafx app, read in any parameters, then create the stage
     * and execute the matrix multiplication
     * Need to refactor to be under 30 lines
     * 2014-11-17 Refactored to be compliant with Style guide, I still feel like
     * there is a much better way to handle this. Will look at FXML next time
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage)
    {
        // Grab the parameters from the Command Line run
        List<String> unnamedParams = getParameters().getUnnamed();
        if (unnamedParams.isEmpty())
        {
            System.out.println("Proper usages is: Matrix file pathname");
            System.exit(0);
        }
        
        // Set up the GridPane
        GridPane root = new GridPane();
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().add(colConstraint);
        
        /* Set up the Title
         * With the pref height & the later row constraint the title never grows
         * it is only the textarea that changes size
         */
        StackPane topInfo = new StackPane();
        createTitle(topInfo);
        
        /* Set up the TextArea
         * There were a couple scenarios when the window is shrunk where a
         * scroll bar made the text still viewable rather than hidden
         */
        ScrollPane scrollBottom = new ScrollPane();
        scrollBottom.setFitToHeight(true);
        scrollBottom.setFitToWidth(true);
        TextArea results = new TextArea();
        
        // Run the actual multiplication
        runMatricesInGUI(unnamedParams.get(0), results);
        scrollBottom.setContent(results);
        root.addRow(0, topInfo);
        root.addRow(1, scrollBottom);
        
        // Throw in the rowconstraints to ensure only textarea grows
        setGridPaneConstraints(root);        
        
        Scene mainScene = new Scene(root, DEFAULT_START_WIDTH,
                DEFAULT_START_HEIGHT);
        
        /* There is no real reason to make the window full screen, limit to 1/2
         * the total screen size of the primary screen
         */
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("ParallelMatrixMultiply");
        primaryStage.setScene(mainScene);
        primaryStage.setMaxHeight(screenBounds.getHeight() / 2);
        primaryStage.setMaxWidth(screenBounds.getWidth() / 2);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    /* Private Features */
    
    /**
     * Take the specified pane and add in a Title label with project info
     * @param titlePane Pane to add Label to
     */
    private static void createTitle(Pane titlePane)
    {
        titlePane.setPrefHeight(MAX_TITLE_HEIGHT);
        Label title = new Label("Project 6 Parallel Processing - Haydn Slade");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Verdana", TITLE_FONT_SIZE));
        titlePane.getChildren().add(title);
    }
    
    /**
     * Takes the GridPane and adds rowConstraints to the first two rows
     * The first row should be the title so it should never grow vertically,
     * the second should take up all extra space available vertically.
     * I am sure I could make it more parameterized (for all rows)
     * @param rootPane GridPane to add rowConstraints to
     */
    private static void setGridPaneConstraints(GridPane rootPane)
    {
        RowConstraints rowConstraint1 = new RowConstraints();
        rowConstraint1.setVgrow(Priority.NEVER);
        RowConstraints rowConstraint2 = new RowConstraints();
        rowConstraint2.setVgrow(Priority.ALWAYS);
        rootPane.getRowConstraints().addAll(rowConstraint1, rowConstraint2);
    }
    
    /**
     * Method to run parallel and sequential matrix multiplication, reads in
     * file and outputs results to the textarea in the javafx scene.
     * @param matrixFile
     * @param resultOutput 
     */
    private static void runMatricesInGUI(String matrixFile,
            TextArea resultOutput)
    {
        try
        {
            // Create the three matrices, A and B are the same, C is blank
            Matrix matrixA = new Matrix(matrixFile);
            Matrix matrixB = matrixA;
            Matrix matrixC = new Matrix(matrixA.getMatrixRows(),
                    matrixA.getMatrixColumns());
            
            // Run the parallel multiplication with timing
            long startTime = System.currentTimeMillis();
            ForkJoinPool runPool = new ForkJoinPool();
            runPool.invoke(new MultiplicationTask(matrixA, matrixB, matrixC));
            long endTime = System.currentTimeMillis();
            
            //Write out to the textarea
            resultOutput.appendText("PARALLEL ALGORITHM\n");
            resultOutput.appendText("C(1,1) = " + matrixC.getValue(1, 1)
                    + "\n");
            resultOutput.appendText("Parallel time is " + (endTime - startTime)
                    + " milliseconds\nwith "
                    + Runtime.getRuntime().availableProcessors()
                    + " processors\n\n");
            
            // Reset the Matrix C to create fresh start, then compute sequential
            matrixC = new Matrix(matrixA.getMatrixRows(),
                    matrixA.getMatrixColumns());
            startTime = System.currentTimeMillis();
            ExecuteMultiplication.multiplyMatrices(matrixA, matrixB, matrixC);
            endTime = System.currentTimeMillis();
            resultOutput.appendText("SEQUENTIAL ALGORITHM\n");
            resultOutput.appendText("C(1,1) = " + matrixC.getValue(1, 1)
                    + "\n");
            resultOutput.appendText("Sequential time is "
                    + (endTime - startTime) + " milliseconds");
        }
        catch (IOException e)
        {
            System.out.println("Unable to open map file provided " 
                    + e.getMessage());
        }
    }
    
    /**
     * Inner class to handle the Recursive Action for the ForkJoinPool
     * Holds references to the Matrices as well as the row from Matrix A the
     * task is working on
     * @author HaydnSlade
     * @version 1.00 2014-11-15
     */
    private static class MultiplicationTask extends RecursiveAction
    {
        /* Public Features */
        
        /**
         * Main initialization, without row sets to -1 to handle the beginning
         * task setup
         * @param A First Matrix to multiply
         * @param B Second Matrix to be used in multiplication
         * @param C Resultant Matrix
         */
        public MultiplicationTask(Matrix A, Matrix B, Matrix C)
        {
            this(A, B, C, -1);
        }
        
        /**
         * Initialization with a specified row from Matrix A
         * @param A First Matrix to multiply
         * @param B Second Matrix to be used in multiplication
         * @param C Resultant Matrix
         * @param row row from Matrix A to work with
         */
        public MultiplicationTask(Matrix A, Matrix B, Matrix C, int row)
        {
            // Verify that the a resultant Matrix is possible from A & B
            if (A.getMatrixColumns() != B.getMatrixRows())
            {
                throw new IllegalArgumentException("Matrices do not match "
                        + "(Matrix A columns (" + A.getMatrixColumns()
                        + ") must equal Matrix B rows ("
                        + B.getMatrixRows() + "))");
            }
            this.A = A;
            this.B = B;
            this.C = C;
            this.rowToMultiply = row;
        }
        
        /**
         * compute for the ForkJoinPool, if it is the first task run we have to
         * break up all of the rows into new tasks and invoke them
         */
        @Override
        public void compute()
        {
            if (rowToMultiply == -1)
            {
                // Create a list of the rows a sub tasks
                List<MultiplicationTask> subTasks = new ArrayList<>();
                for (int row = 0; row < A.getMatrixRows(); row++)
                {
                    subTasks.add(new MultiplicationTask(A, B, C, row));
                }
                invokeAll(subTasks);
            }
            else
            {
                ExecuteMultiplication.multiplyRowByColumn(A, B, C,
                        rowToMultiply);
            }
        }
        
        /* Private Features */
        
        private Matrix A;
        private Matrix B;
        private Matrix C;
        private int rowToMultiply;
    }
}
