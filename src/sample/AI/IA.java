package sample.AI;

import javafx.scene.control.ProgressBar;
import sample.Controller;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.io.File;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class IA
{
	int type=0;				//	0 -> Entrainement	;	1 -> Vétéran
	int diff=0;				//  0 -> Facile   ;   1 -> Moyen   ;  2 -> Difficile
	MultiLayerPerceptron net;
	String fileName="src/sample/AI/IA/train.txt";						//Modifier le path du fichier suivant le vrai path
	Vector<String[]> temporary=new Vector<String[]>();
	
	public IA(int type,int diff)
	{
		this.diff=diff;
		if(type==1)
		{
			if(diff==0)
				net=MultiLayerPerceptron.load("src/sample/AI/IA/Veteran_Facile");
			else if(diff==1)
				net=MultiLayerPerceptron.load("src/sample/AI/IA/Veteran_Moyen");
			else if(diff==2)
				net=net=MultiLayerPerceptron.load("src/sample/AI/IA/Veteran_Difficile");
		}
		else if(type==2)
		{
			if(diff==0)
				net=MultiLayerPerceptron.load("src/sample/AI/IA/Bleu_Facile");
			else if(diff==1)
				net=MultiLayerPerceptron.load("src/sample/AI/IA/Bleu_Moyen");
			else if(diff==2)
				net=net=MultiLayerPerceptron.load("src/sample/AI/IA/Bleu_Difficile");
		}
		else
			net=null;
	}
	/*public int[] doTurn(int[][] board)					//A utiliser pour recevoir un int[2] du coup joué par l'IA : int[0] = x ; int[1] = y; board : le plateau actuel
	{
		if(net!=null)
		{
			double[] output;
			output = net.forwardPropagation(intTableToDoubleTable(board));
			return getTurn(output,board);
		}
		else
			System.out.println("IA non initialisé");
		return null;
		
	}*/
	public int[] doTurn(double[] board)					//A utiliser pour recevoir un int[2] du coup joué par l'IA : int[0] = x ; int[1] = y; board : le plateau actuel
	{
		if(net!=null)
		{
			double[] output;
			output = net.forwardPropagation(board);
			return getTurn(output,board);
		}
		else
			System.out.println("IA non initialisé");
		return null;
		
	}
	public double[] copyPasteDoubleTable(double[] input)
	{
		double[] news=new double[9];
		for(int i=0;i<news.length;i++)
		{
			news[i]=input[i];
		}
		return news;
	}
	public int[] getTurn(double[] input,double[] board)
	{
		double max=0;
		int x=0;
		int y=0;
		int j=0;
		
			for(int u=0;u<input.length;u++)
			{
				System.out.print(input[u]+",");
			}
			System.out.println("");
		for(int i=0;i<9;i++)
		{
			if((max<input[i])&&(board[i]==0))
			{
				max=input[i];
				y=i%3;
				j=i;
				if((i>=0)&&(i<=2))
					x=0;
				else if((i>=3)&&(i<=5))
					x=1;
				else if((i>=6)&&(i<=8))
					x=2;
			}
		}
		int[] c=new int[2];
		c[0]=x;
		c[1]=y;
		return c;
	}
	public boolean doTraining(Controller control)						// A utiliser dans le bouton entrainement /// Faire barre de chargement
	{
		
		int[] layers=null;
		if(diff==0)
			layers=new int[]{ 9, 50 , 9 };
		else if(diff==1)
			layers=new int[]{ 9, 250 , 9 };
		else
			layers=new int[]{ 9, 800 , 9 };
		if(net==null)
		{
			if(diff==0)
				net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
			else
				net = new MultiLayerPerceptron(layers, 0.01, new SigmoidalTransferFunction());
		}
		double error = 0.0 ;
		
		
		////////////////////////////////////
		String line;
		
		Vector<double[]> vect=new Vector<double[]>();
		HashMap<String, double[]> C=new HashMap<String, double[]>();
		double[] inputs;
		double[] output;

		try 
		{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) 
			{
				int mode=0;
				String board="";
				String coup="";
				
                for(int i=0;i<line.length();i++)
				{
					char c=line.charAt(i);
					if(c=='\t')
						mode++;
					else if(mode==0)
						board+=c;
					else
						coup+=c;
				}
				coup=getGoodOutput(coup);
				add(vect,stringToDoubleTable(board));
				C.put(board,stringToDoubleTable(getGoodOutput(coup)));
			}
		}
		catch(FileNotFoundException ex)
		{
            System.out.println("Fichier introuvable'" + fileName + "'");
        }
        catch(IOException ex)
		{
            System.out.println("Erreur lors de la lecture de '"  + fileName + "'");
        }
		////////////////////////////////////
		/*for(int i=0;i<vect.size();i++)
		{
			double[] inputs222 = vect.get(i);
			for(int u=0;u<inputs222.length;u++)
			{
				System.out.print(inputs222[u]+",");
			}
			System.out.println("");
			double[] inputs22 = C.get(doubleTableToString(vect.get(i)));
			for(int u=0;u<inputs22.length;u++)
			{
				System.out.print(inputs22[u]+",");
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
		System.out.println("fin affich vect\n\n");*/
		for(int o=0;o<2500;o++)
			{
				for(int i=0;i<vect.size();i++)
				{
					inputs=vect.get(i);
					String inputsS=doubleTableToString(inputs);
					if((C.containsKey(inputsS)))
					{
						output=C.get(inputsS);
						error += net.backPropagate(inputs, output);
					}
					////////////////////////
					////////////////////////
					//TO DO : 
					//			((i+(o*vect.size()))/ (vect.size()*2500.0)) == pourcentage 
					//Rajoute un argument à la fonction (barre de chargement) si besoin
					if((((i+(o*vect.size()))/ (vect.size()*2500.0))==0.2)||(((i+(o*vect.size()))/ (vect.size()*2500.0))==0.4)||(((i+(o*vect.size()))/ (vect.size()*2500.0))==0.6)||(((i+(o*vect.size()))/ (vect.size()*2500.0))==0.8)){
						control.progressbarupdate(((i+(o*vect.size()))/ (vect.size()*2500.0)));
						System.out.println(((i+(o*vect.size()))/ (vect.size()*2500.0)));
					}
					////////////////////////
					////////////////////////
					//try { Thread.sleep(10); } catch (Exception e) { }
				}
			}
			error /= vect.size()*2500 ;
			System.out.println("Error is "+error);
			System.out.println("Learning completed!");
			if(diff==0)
				net.save("src/sample/AI/IA/Bleu_Facile");
			else if(diff==1)
				net.save("src/sample/AI/IA/Bleu_Moyen");
			else if(diff==2)
				net.save("src/sample/AI/IA/Bleu_Difficile");
		return true;
		
	}
	public String doubleTableToString(double[] input)
	{
		String output="";
		for(int i=0;i<9;i++)
		{
			output+=(int)input[i]+"";
		}
		return output;
	}
	public double[] stringToDoubleTable(String input)
	{
		double[] news=new double[9];
		for(int i=0;i<input.length();i++)
		{
			news[i]=input.charAt(i)-48;
		}
		return news;
	}
	public void add(Vector<double[]> vect,double[] news)
	{
		for(int i=0;i<vect.size();i++)
		{
			if(equalsTable(news,vect.get(i)))
				return;
		}
		vect.add(news);
	}
	public boolean equalsTable(double[] a,double[] b)
	{
		for(int i=0;i<9;i++)
		{
			if(a[i]!=b[i])
				return false;
		}
		return true;
	}
	public void appendStrToFile(String str) 
    { 
        try 
		{ 
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true)); 
            out.write(str+'\n'); 
            out.close(); 
        } 
        catch (IOException e) { 
			try
			{
				File dir = new File(fileName);
				dir.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true)); 
           	 	out.write(str+"\n"); 
          		out.close();
			}
			catch(Exception er)
			{
				System.err.println("Save: "+er.getMessage());
			}
        } 
    } 
	public double[] intTableToDoubleTable(int[][] board)
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
	public void addEnnemyTurn(double[] input,int x,int y)			// A utiliser à chaque coup ennemy (input -> intTableToDoubleTable si besoin -> board avant le coup) ; x et y les coordonnées du coup 
	{
		double[] output=new double[9];
		for(int i=0;i<9;i++)
		{
			output[i]=0;
		}
		output[(3*x)+y]=1;
		String[] newStr=new String[2];
		newStr[0]=doubleTableToString(input);
		newStr[1]=doubleTableToString(output);
		temporary.add(newStr);
	}
	public void iaWin()					// A utiliser si l'IA a gagné
	{
		temporary=new Vector<String[]>();
	}
	public void enemyWin()				// A utiliser si l'IA a perdu (en considérant que tu as bien utiliser addEnnemyTurn)
	{
		try 
		{ 
			BufferedReader bufferedReader;
			for(int i=0;i<temporary.size();i++)
			{
				String line="";
				String[] geti=temporary.get(i);
				//System.out.println("temporary : "+geti[0]+" "+geti[1]);
				bufferedReader = new BufferedReader(new FileReader(fileName));
				boolean test=false;
				int nbLine=0;
				while((line = bufferedReader.readLine()) != null) 
				{
					int mode=0;
					String board="";
					String coup="";
					
					for(int y=0;y<line.length();y++)
					{
						char c=line.charAt(y);
						if(c=='\t')
							mode++;
						else if(mode==0)
							board+=c;
						else
							coup+=c;
					}
					if(board.equals(geti[0]))
					{
						test=true;
						bufferedReader.close();
						
						try 
						{ 
							/*BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true)); 
							int newNbLine=0;
							while(newNbLine<nbLine)
							{
								newNbLine++;
								out.nextLine();
							}
							out.write(board+'\t'+incrementString(geti[1],coup)); 
							out.close(); */
							Path path = Paths.get(fileName);
							List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

							for (int y = 0; y < fileContent.size(); y++) {
								if (y==nbLine) {
									fileContent.set(y,board+'\t'+incrementString(geti[1],coup)); // set(i,[...] potenciellement ?
									break;
								}
							}

							Files.write(path, fileContent);
						} 
						catch (IOException e) { }
						break;
					}
					
					nbLine++;
				}
				if(!test)
				{
					bufferedReader.close();
					appendStrToFile(geti[0]+'\t'+writeAction(geti[1]));
				}
				
			}
           /* out.write(str); 
            out.close(); */
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
	public String incrementString(String actu,String ancient)
	{
		String[] change=ancient.split(" ");
		char c;
		int xy=0;
		for(int i=0;i<actu.length();i++)
		{
			c=actu.charAt(i);
			if(c==49)
			{
				xy=i;
				break;
			}
		}
		int number=Integer.parseInt(change[xy]);
		number++;
		change[xy]=""+number;
		String returns="";
		for(int i=0;i<change.length;i++)
		{
			returns+=change[i]+" ";
		}
		return returns;
		
	}
	public String writeAction(String input)
	{
		String output="";
		for(int i=0;i<input.length();i++)
		{
			output+=input.charAt(i)+" ";
		}
		return output;
	}
	
	public String getGoodOutput(String input)
	{
		String[] change=input.split(" ");
		if(change.length<=2)
			return input;
		int max=-1;
		int value;
		int j=0;
		for(int i=0;i<change.length;i++)
		{
			value=Integer.parseInt(change[i]);
			if(value>max)
			{
				max=value;
				j=i;
			}
		}
		String output="";
		for(int i=0;i<9;i++)
		{
			if(i!=j)
				output+='0';
			else
				output+='1';
		}
		return output;
	}

	public void reset()
	{
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
			out.write("");
			out.close();
		}
		catch(Exception e)
		{}
	}
}
