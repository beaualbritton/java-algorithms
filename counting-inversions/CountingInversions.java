

public class CountingInversions
{
    public static void main(String[] args) {

        int [] L = {1,13,14,2,25,26};

        System.out.println(sortAndCount(L));
    }

    public static int sortAndCount(int[] L)
    {
        //one member in L
        if(L.length == 1)
            return 0;

        //generating new lists
        int [] A, B;

        //checking if L.length is uneven. if so, lefthand gets 'shorter end' of the stick. or list in this case :^)
        if (L.length % 2 == 0)
        {
            A = new int[L.length/2];
            B = new int[L.length/2];

        }
        else
        {
            A = new int[L.length/2];
            B = new int[(L.length/2) + 1];
        }   

        //populating these new lists
        for(int i = 0; i < A.length; ++i)
        {
            A[i] = L[i];
        }
        int j;
        for(j = A.length; j < L.length; ++j)
        {
            B[j-A.length] = L[j];
        }
        printArray(A);
        printArray(B);
        int ra  = sortAndCount(A);
        int rb = sortAndCount(B);
        int a = mergeAndCount(A, B);


        return ra+rb+a;
    }
    
    public static int mergeAndCount(int[] A, int[] B)
    {
        int[] L;
        int inversionCount = 0;
        //inversion count 
        for (int i = 0; i < A.length; ++i)
        {
            int currentNumA = A[i];
            for (int j = 0; j < B.length; ++j)
            {
                int currentNumB = B[j];
                //System.out.println(currentNumB);
                if(currentNumA > currentNumB)
                {
                    System.out.println(currentNumA + " " + currentNumB);
                    ++inversionCount;
                }

            }
        }

        //TODO: implement merge sort x

        return inversionCount;
    }
    
    public static void printArray(int[] arr)
    {
        System.out.print("{");
        for(int i = 0; i < arr.length; ++i)
        {
            System.out.print(arr[i] + ", ");
        }
        System.out.print("}\n");
    }
}

class SortCountResult
{
    int count;
    int[] sortedArray;

    public SortCountResult(int c, int[] l)
    {
        this.count = c;
        this.sortedArray = l;
    }
}