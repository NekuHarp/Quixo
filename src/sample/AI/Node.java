package sample.AI;

public class Node
{
	private double value=0;
	private Node father=null;
	private Node brother=null;
	private Node son=null;
	private int[][] board;
	private int x;
	private int y;
	boolean victory=false;
	boolean max;
	public Node(int[][] boards,int xs,int ys,boolean max)
	{
		board=boards;
		x=xs;
		y=ys;
		this.max=max;
	}
	public boolean getMax()
	{
		return max;
	}
	public void counterAttack()
	{
		Node C=son;
		while(C!=null)
		{
			if(C.getVictory())
			{
				if(max)
					value-=1000.0;
				else
					value+=1000.0;
			}
			C=C.getBrother();
		}
	}
	public void changeVictory()
	{
		if(victory)
			victory=false;
		else
			victory=true;
		
		if(victory)
		{
			if(max)
				value+=9999.0;
			else
				value-=9999.0;
		}
	}
	public boolean getVictory()
	{
		return victory;
	}
	public void changeX(int val)
	{
		x=val;
	}
	public int getX()
	{
		return x;
	}
	public void changeY(int val)
	{
		y=val;
	}
	public int getY()
	{
		return y;
	}
	public Node getFather()
	{
		return father;
	}
	public void changeFather(Node c)
	{
		father=c;
	}
	public Node getBrother()
	{
		return brother;
	}
	public void changeBrother(Node c)
	{
		brother=c;
	}
	public Node getSon()
	{
		return son;
	}
	public void changeSon(Node c)
	{
		son=c;
	}
	public int[][] getBoard()
	{
		return board;
	}
	public double getValue()
	{
		return value;
	}
	public void changeValue(double val)
	{
		double g=17976457.0;
		if(val>g)
		{
			System.out.println("WHAAATTTTTTTTTTTT	?");
			return;
		}
		value=val;
	}
	public void add(Node news)
	{
		if(news!=null)
		{
			news.changeBrother(this.son);
			news.changeFather(this);
			this.changeSon(news);
		}
		else
		{
			System.out.println("Bug");
		}
	}
	public double getMaximusValius()
	{
		double t;
		//System.out.println("X = "+x+" , Y = "+y+" , Value = "+value);
		if(brother==null)
			return value;
		else
		{
			t=brother.getMaximusValius();
			if(t>value)
				return t;
			else
				return value;
		}
	}
	public double getMinimusValius()
	{
		double t;
		//System.out.println("X = "+x+" , Y = "+y+" , Value = "+value);
		if(brother==null)
			return value;
		else
		{
			t=brother.getMinimusValius();
			if(t<value)
				return t;
			else
				return value;
		}
	}
}