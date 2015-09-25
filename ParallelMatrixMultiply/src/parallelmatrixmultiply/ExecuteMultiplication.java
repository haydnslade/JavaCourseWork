package parallelmatrixmultiply;

/**
 * Class to handle static Matrix multiplication methods
 * @author HaydnSlade;
 * @version 1.01 2014-11-16
 */
public class ExecuteMultiplication 
{
    /* Public Features */
    
    /**
     * Matrix multiplication for a specific row in Matrix A
     * @param A First Matrix to multiply
     * @param B Second Matrix to be used in multiplication
     * @param C Resultant Matrix
     * @param row the row in Matrix A to multiply out
     */
    public static void multiplyRowByColumn(Matrix A, Matrix B, Matrix C, int row)
    {
        for (int j = 0; j < B.getMatrixColumns(); j++)
        {
            for (int k = 0; k < A.getMatrixColumns(); k++)
            {
                C.setValue(row, j, C.getValue(row, j) + A.getValue(row, k)
                        * B.getValue(k, j));
            }
        }
    }
    
    /**
     * Sequential matrix multiplication
     * @param A First Matrix to multiply
     * @param B Second Matrix to be used in multiplication
     * @param C Resultant Matrix
     */
    public static void multiplyMatrices(Matrix A, Matrix B, Matrix C)
    {
        for (int i = 0; i < A.getMatrixRows(); i++)
        {
            for (int j = 0; j < B.getMatrixColumns(); j++)
            {
                for (int k = 0; k < A.getMatrixColumns(); k++)
                {
                    C.setValue(i, j, C.getValue(i, j) + A.getValue(i, k)
                            * B.getValue(k, j));
                }
            }
        }
    }

    /* Private Features */
    
}