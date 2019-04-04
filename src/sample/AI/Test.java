package sample.AI;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Vector;
import static java.lang.Math.toIntExact;
import java.util.Scanner;

public class Test 
{
	int compteur=0;
	public static void main(String[] args) {
		try {
			test();
		} 
		catch (Exception e) {
			System.out.println("Test.main()");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void test()
	{
		Vector<int[][]> vect=new Vector<int[][]>();
		HashMap<String, double[]> C=getEveryGoodPossibility(vect);
		double[] inputs;
		double[] output;
		try {
			//MultiLayerPerceptron net = MultiLayerPerceptron.load("AI/Veteran_Difficile");
			int[] layers = new int[]{ 9, 10 , 9 };
			
			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
			
			double samples = 100000.0 ;
			/////////////////////////////////////////////////////////////////////


			/////////////////////////////////////////////////////////////////////
			//TRAINING ...
			
			
			
			
			
			
			
			
			for(int o=0;o<1000;o++)
			{
				for(int i=0;i<vect.size();i++)
				{
					inputs=intTableToDoubleTable(vect.get(i));
					if(C.containsKey(doubleTableToString(inputs)))
					{
						output=C.get(doubleTableToString(inputs));
						error += net.backPropagate(inputs, output);
					}
					if ( (i+(o*vect.size())) %	(vect.size()*2500/10) == 0 ) System.out.println("Error at step "+(i+(o*vect.size()))+" is "+ (error/(double)(i+(o*vect.size())))+" at o="+o+" i= "+i);
				}
			}
			error /= vect.size()*10 ;
			System.out.println("Error is "+error);
			//
			System.out.println("Learning completed!");



			
			net.save("src/sample/AI/IA/Veteran_Facile");
			inputs=new double[]{0,0,0,
								0,0,0,
								0,0,0};	   
			output = net.forwardPropagation(inputs);
			int[] c=getTurn(output);

			System.out.println("X : "+c[0]+" Y :"+c[1]);


			//output=C.get("002110200");
		} 



		catch (Exception e) {
			System.out.println("Test.test()");
			e.printStackTrace();
			System.exit(-1);
		}
	} 

	//CHAMPS ...
	public static HashMap<double[], double[]> mapTrain ;
	public static HashMap<double[], double[]> mapTest ;
	public static HashMap<double[], double[]> mapDev ;
	
	public static boolean boolZero2(double[] test,int k)
	{
		int o=0;
		for(int i=0;i<9;i++)
		{
			if(test[i]!=0)
				o++;
		}
		if((o%2!=0)&&(o>=k))
			return true;
		else
			return false;
	}
	public static boolean boolZero(int d,int f,double[] test)
	{
		int o=0;
		for(int i=0;i<9;i++)
		{
			if(test[i]!=0)
				o++;
		}
		if((o<=f)&&(o>=d))
			return true;
		else
			return false;
	}
	
	public static void testAMove(double[] inputs,HashMap<String, double[]> C,MultiLayerPerceptron net)
	{
			double[] output;
			output = net.forwardPropagation(inputs);
			
			//int[] c=getTurn(output);


			//net.save("AI/UltimateAI");

			System.out.print("[");
			for(int h=0;h<9;h++)
			{
				System.out.print(inputs[h]);
				if((h==2)||(h==5)||(h==8))
					System.out.println("");
				if(h!=8)
					System.out.print(",");
			}
			System.out.print("[");
			for(int h=0;h<9;h++)
			{
				if(output[h]>0.01)
					System.out.print(output[h]);
				else
					System.out.print(0);
				if((h==2)||(h==5)||(h==8))
					System.out.println("");
				if(h!=8)
					System.out.print(",");
			}
			output=C.get(doubleTableToString(inputs));
			for(int h=0;h<9;h++)
			{
				System.out.print(output[h]);
				if((h==2)||(h==5)||(h==8))
					System.out.println("");
				if(h!=8)
					System.out.print(",");
			}	
	}
	public static int[] getTurn(double[] input)
	{
		double max=0;
		int x=0;
		int y=0;
		int j=0;
		for(int i=0;i<9;i++)
		{
			if(max<input[i])
			{
				max=input[i];
				x=i%3;
				j=i;
				if((i>=0)&&(i<=2))
					y=0;
				else if((i>=3)&&(i<=5))
					y=1;
				else if((i>=6)&&(i<=8))
					y=2;
			}
		}
		int[] c=new int[2];
		c[0]=x;
		c[1]=y;
		//System.out.println(j);
		return c;
	}
	public static String doubleTableToString(double[] input)
	{
		String output="";
		for(int i=0;i<9;i++)
		{
			output+=(int)input[i]+"";
		}
		return output;
	}
	public static void appendStrToFile(String fileName, String str) 
    { 
        try 
		{ 
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true)); 
            out.write(str+"\n"); 
            out.close(); 
        } 
        catch (IOException e) { 
            System.out.println(e); 
        } 
    } 
	public static void readAndInputThem(MultiLayerPerceptron net)
	{
		String fileName="src/sample/AI/IA/train.txt";
		String line;
		try 
		{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) 
			{
                System.out.println(line);
            }
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
		{
            System.out.println("Fichier introuvable'" + fileName + "'");
        }
        catch(IOException ex)
		{
            System.out.println("Erreur lors de la lecture de '"  + fileName + "'");
        }
	}
	public static HashMap<String, double[]> getEveryGoodPossibility(Vector<int[][]> vect)
	{
		int[][] board=new int[3][];
		for(int i=0;i<3;i++)
		{
			 board[i]=new int[3];
			for(int j=0;j<3;j++)
			{
				board[i][j]=0;
			}	
		}
		/*board[1][1]=1;
		board[0][0]=2;
		board[1][2]=1;
		board[1][0]=2;
		board[2][0]=1;
		board[0][2]=2;
		board[0][1]=1;
		board[2][1]=2;*/

		Node father=new Node(board,-1,-1,false);
		System.out.println("test");
		double test=max(board,father,vect);
		System.out.println("fin de test");
		HashMap<String, double[]> hash=new HashMap<String, double[]>();
		hash=addToTrainTrain(hash,father);
		System.out.println("Finish");
		return hash;
	}

	public static HashMap<String, double[]> addToTrainTrain(HashMap<String, double[]> hashiMappo,Node current)
	{
		/*compteur++;
		if(compteur% 1000 == 0) System.out.println("Whuat ?");*/
		
		double choice;
		//System.out.println("Police 1");
		if(current!=null)
		{
			Node studied=current.getSon();
			while(studied!=null)
			{
				addToTrainTrain(hashiMappo,studied);
				studied=studied.getBrother();
			}
			//System.out.println("Police");
			//System.out.println("Police"+((current.getMax())&&(!current.getVictory()))+" "+((!current.getMax())&&(!current.getVictory())));
			if((current.getMax())&&(!current.getVictory())&&(current.getSon()!=null))
				choice=current.getSon().getMinimusValius();
			else if((!current.getMax())&&(!current.getVictory())&&(current.getSon()!=null))
				choice=current.getSon().getMaximusValius();
			else
				return hashiMappo;
			//System.out.println("Police");
			studied=current.getSon();
			while(studied!=null)
			{
				//	System.out.println("Police");
					if(choice==studied.getValue())
					{
						//System.out.println("Police");
						//appendStrToFile("AI/train.txt","Police")
						int[][] board=studied.getBoard();
					//	System.out.println("Police");
						double[] input=new double[9];
						for(int i=0;i<3;i++)
						{
							for(int j=0;j<3;j++)
							{
								input[(i*3)+j]=board[i][j];
							}
						}
						double[] output=new double[9];
						for(int i=0;i<9;i++)
						{
							output[i]=0;
						}
						output[(studied.getX()*3)+studied.getY()]=1;
						hashiMappo.put(doubleTableToString(input),output);
					}
				studied=studied.getBrother();
			}
			return hashiMappo;
		}
		return hashiMappo;
	}
	public static void toDisplayBoard(int[][] board)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(board[i][j]+" ");
			}
			System.out.println("");	
		}
	}
	public static int[][] copyPaste(int[][] board)
	{
		int[][] boards=new int[3][];
		for(int i=0;i<3;i++)
		{
			boards[i]=new int[3];
			for(int j=0;j<3;j++)
			{
				boards[i][j]=board[i][j];
			}
		}
		return boards;
	}
	public static double[] intTableToDoubleTable(int[][] board)
	{
		double[] input=new double[9];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				input[(i*3)+j]=(double)board[i][j];
			}
		}
		return input;
	}
	public static int compt(Node Current)
	{
		int i=0;
		if(Current==null)
		{
			//System.out.println("Hey ! C'est fini");
			return 0;
		}
		i+=compt(Current.getBrother());
		//toDisplayBoard(Current.getBoard());
		i+=compt(Current.getSon());
		i++;
		//System.out.println(i);
		return i;
	}
	public static int compt2(Node Current)
	{
		int i=0;
		if(Current==null)
			return 0;
		
		System.out.println(i++);
		//i+=compt(Current.getBrother());
		//i+=compt(Current.getSon());
		i++;
		return i;
	}
	public static double max(int[][] board, Node current,Vector<int[][]> vect)
	{
		double val=0;
		boolean finish=false;
			if((board[2][0]==2)&&(board[1][0]==2)&&(board[0][0]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][1]==2)&&(board[1][1]==2)&&(board[0][1]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][2]==2)&&(board[1][2]==2)&&(board[0][2]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[0][2]==2)&&(board[0][1]==2)&&(board[0][0]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[1][2]==2)&&(board[1][1]==2)&&(board[1][0]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][2]==2)&&(board[2][1]==2)&&(board[2][0]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[0][0]==2)&&(board[1][1]==2)&&(board[2][2]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][0]==2)&&(board[1][1]==2)&&(board[0][2]==2))
			{
				val+=-1;
				finish=true;
				current.changeVictory();
			}
		if(finish)
			return val;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if((board[i][j]==0))
				{
					Node noth=new Node(copyPaste(board),i,j,true);
					current.add(noth);
					if(!getMaxNumber(board))
						add(vect,copyPaste(board));
					board[i][j]=1;
					val+=min(board,noth,vect);
					current.changeValue(val);
					board[i][j]=0;
				}
			}
		}
		current.counterAttack();
		return val;
	}
	
	public static void add(Vector<int[][]> vect,int[][] news)
	{
		for(int i=0;i<vect.size();i++)
		{
			if(equalsTable(news,vect.get(i)))
				return;
		}
		vect.add(news);
	}
	public static boolean equalsTable(int[][] a,int[][] b)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(a[i][j]!=b[i][j])
					return false;
			}
		}
		return true;
	}
	
	
	
	public static boolean getMaxNumber(int[][] board)
	{
		boolean test=true;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(board[i][j]==0)
					test=false;
			}
		}
		return test;
	}
	public static double min(int[][] board, Node current,Vector<int[][]> vect)
	{
		double val=0;
		boolean finish=false;
			if((board[2][0]==1)&&(board[1][0]==1)&&(board[0][0]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][1]==1)&&(board[1][1]==1)&&(board[0][1]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][2]==1)&&(board[1][2]==1)&&(board[0][2]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[0][2]==1)&&(board[0][1]==1)&&(board[0][0]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[1][2]==1)&&(board[1][1]==1)&&(board[1][0]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][2]==1)&&(board[2][1]==1)&&(board[2][0]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[0][0]==1)&&(board[1][1]==1)&&(board[2][2]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
			else if((board[2][0]==1)&&(board[1][1]==1)&&(board[0][2]==1))
			{
				val+=1;
				finish=true;
				current.changeVictory();
			}
		if(finish)
			return val;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if((board[i][j]==0))
				{
					Node noth=new Node(copyPaste(board),i,j,false);
					current.add(noth);
					if(!getMaxNumber(board))
						add(vect,copyPaste(board));
					board[i][j]=2;
					val+=max(board,noth,vect);
					current.changeValue(val);
					board[i][j]=0;
				}
			}
		}
		current.counterAttack();
		return val;
	}
}
