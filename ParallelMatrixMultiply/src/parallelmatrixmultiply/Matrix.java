package parallelmatrixmultiply;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Object for a Matrix, holds double array and associated methods
 * @author HaydnSlade;
 * @version 1.00 2014-11-16
 */
public class Matrix 
{
    /* Public Features */
    
    /**
     * Initialize an empty Matrix for use as Resultant
     * @param rows number of rows
     * @param columns number of columns
     */
    public Matrix(int rows, int columns)
    {
        matrix = new double[rows][columns];
    }
    
    /**
     * Initialize a Matrix from the specified file
     * @param matrixFile file to read in and load into Matrix
     * @throws IOException MatrixFile had some issue, error was thrown
     */
    public Matrix(String matrixFile) throws IOException
    {
        readMatrixFile(matrixFile);
    }
    
    /**
     * Get number of rows in Matrix
     * @return int of rows
     */
    public int getMatrixRows()
    {
        return matrix.length;
    }
    
    /**
     * Get number of columns in Matrix
     * @return int of columns
     */
    public int getMatrixColumns()
    {
        return matrix[0].length;
    }
    
    /**
     * Get the value at the specified row and column
     * Needed to get value for display, and to add to in multiplication
     * @param row row for lookup
     * @param column column for lookup
     * @return int of value at specified row and column
     */
    public double getValue(int row, int column)
    {
        return matrix[row][column];
    }
    
    /**
     * Set the value at the specified row and column
     * @param row row for where to set value
     * @param column column for where to set value
     * @param value value to set at coords
     */
    public void setValue(int row, int column, double value)
    {
        matrix[row][column] = value;
    }

    /* Private Features */
    
    double[][] matrix;
    
    /**
     * Read in the matrixFile, initialize with the first line for rows and cols
     * then load in the numbers into the Matrix
     * @param matrixFile file path for file to create matrix with
     * @throws IOException file read can fail, caller needs to catch
     */
    private void readMatrixFile(String matrixFile) throws IOException
    {
        try (Scanner matrixInfo = new Scanner(Paths.get(matrixFile)))
        {
            String currentLine = matrixInfo.nextLine();
            String[] matrixDimensions = currentLine.split(" ");
            int matrixRows = Integer.parseInt(matrixDimensions[0]);
            int matrixColumns = Integer.parseInt(matrixDimensions[1]);
            matrix = new double[matrixRows][matrixColumns];
            
            populateMatrix(matrixInfo);
        }
    }
    
    /**
     * Populate the matrix with the data from the file
     * @param matrixInfo Scanner based on provided file with the first line 
     *      already read in (which contains rows & columns)
     * @throws IOException file read can fail, caller needs to catch
     */
    private void populateMatrix(Scanner matrixInfo) throws IOException
    {
        for (double[] matrixRow : matrix)
        {
            String currentLine = matrixInfo.nextLine();
            String[] matrixValues = currentLine.split(" ");
            for (int i = 0; i < matrixRow.length; i++)
            {
                matrixRow[i] = Double.parseDouble(matrixValues[i]);
            }
        }
    }
}
