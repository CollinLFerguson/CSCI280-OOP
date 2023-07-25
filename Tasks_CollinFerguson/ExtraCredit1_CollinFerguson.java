/*
CREATE AN ARRAY OF 10 INTEGERS AND PRINT EACH ELEMENT OF THE ARRAY. ASSIGN THE VALUES TO THE ARRAY IN THE PROGRAM. NO INPUT FROM USER_EXCEPTION
Collin L. Ferguson
2/16/2023
 */



public class ExtraCredit1_CollinFerguson
{
	public static void main(String[] args)
	{
		int[] array = new int[10];
		
		for(int i = 0; i<10;i++)
		{
			array[i] = i;
		}
		
		for(int i = 0; i<10;i++)
		{
			System.out.println(array[i]);
		}
	}
}
