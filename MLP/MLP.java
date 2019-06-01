import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class MLP
{
	final int READ=10;
	final int READSTRING=11;
	final int WRITE=12;
	final int WRITESTRING=13;
	final int NEWLINE=14;
	final int LOAD=20;
	final int STORE=21;
	final int ADD=30;
	final int SUBTRACT=31;
	final int DIVIDE=32;
	final int MULTIPLY=33;
	final int MODULUS=34;
	final int EXPONENT=35;
	final int BRANCH=40;
	final int BRANCHNEG=41;
	final int BRANCHZERO=42;
	final int HALT=43;
	final int FATALERROR=44;
	private int[] mem=new int[1000];
	private int accumulator=0;
	private int ins_count=0;
	private int opcode=0;
	private int operand=0;
	private int ins_reg=0;
	private Scanner scint=new Scanner(System.in);
	private Scanner scstr=new Scanner(System.in);

	public MLP()
	{
		Arrays.fill(mem,0);
		accumulator=0;
		ins_count=0;
		opcode=0;
		ins_reg=0;
	}

	public void run()
	{
		int cnt=0,input;
		System.out.println("*** Welcome to Simpletron! ***");
		System.out.println("*** Please enter your program one instruction ***");
		System.out.println("*** (or data word) at a time. I will display ***");
		System.out.println("*** the location number and a question mark (?). ***");
		System.out.println("*** You then type the word for that location. ***");
		System.out.println("*** Type -99999 to stop entering your program. ***");
		while(true)
		{
			System.out.printf("%02d ? ",cnt);
			input=scint.nextInt();
			if(input==-99999)
				break;
			if(get_op(input)>mem.length)
				continue;
			mem[cnt++]=input;
		}
		System.out.println("\n*** Program loading complete ***");
		System.out.println("*** Program execution begins ***\n");
		while(opcode!=HALT&&opcode!=FATALERROR)
		{
			ins_reg=mem[ins_count++];
			opcode=get_opcode(ins_reg);
			operand=get_op(ins_reg);
			execute();
		}
	}

	private void execute()
	{
		switch(opcode)
		{
			case READ:
				System.out.println("Enter an interger: ");
				mem[operand]=scint.nextInt();
				break;
			case READSTRING:
				System.out.println("Enter a string: ");
				StoA(scstr.nextLine());
				break;
			case WRITE:
				System.out.printf("%02d ? %d\n",operand,mem[operand]);
				break;
			case WRITESTRING:
				AtoS();
				break;
			case NEWLINE:
				System.out.printf("\n");
				break;
			case LOAD:
				accumulator=mem[operand];
				break;
			case STORE:
				mem[operand]=accumulator;
				break;
			case ADD:
				if(isValid(accumulator+mem[operand]))
					accumulator+=mem[operand];
				else
					fatalerror("Accumulator memory overflow");
				break;
			case SUBTRACT:
				if(isValid(accumulator-mem[operand]))
					accumulator-=mem[operand];
				else
					fatalerror("Accumulator memory overflow");
				break;
			case DIVIDE:
				if(accumulator==0||mem[operand]==0)
					fatalerror("Attempt to divide bu zero");
				else
					accumulator/=mem[operand];
				break;
			case MULTIPLY:
				mem[999]=accumulator*mem[operand];
				if(isValid(mem[999]))
					accumulator=mem[999];
				else
					fatalerror("Accumulator memory overflow");
				break;
			case MODULUS:
				mem[999]=accumulator%mem[operand];
				if(isValid(mem[999]))
					accumulator=mem[999];
				else
					fatalerror("Accumulator memory overflow");
				break;
			case EXPONENT:
				mem[999]=exp(accumulator,mem[operand]);
				if(isValid(mem[999]))
					accumulator=mem[999];
				else
					fatalerror("Accumulator memory overflow");
				break;
			case BRANCH:
				ins_count=operand;
				break;
			case BRANCHNEG:
				if(accumulator<0)
					ins_count=operand;
				break;
			case BRANCHZERO:
				if(accumulator==0)
					ins_count=operand;
				break;
			case HALT:
				System.out.println("\n*** Simpletron execution terminated. ***\n");
				break;
			case FATALERROR:
				break;
			default:
				fatalerror("Invalid execution code.");
				break;
		}
	}
	private int exp(int n,int x)
	{
		if(x==1)
			return n;
		else
			return n*exp(n,--x);
	}

	private int get_opcode(int x)
	{
		while(x>99)
			x/=10;
		return x;
	}

	private int get_op(int x)
	{
		while(x>mem.length)
			x%=100;
		return x;
	}

	public void StoA(String s)
	{
		int cnt=operand;
		mem[cnt++]=(s.length()*100)+(int)s.charAt(0)-32;
		for(int i=1;i<s.length();i++)
		{
			if(i<s.length()-1)
				mem[cnt++]=toUpper(s.charAt(i))*100+toUpper(s.charAt(++i));
			else
				mem[cnt++]=toUpper(s.charAt(i))*100;
		}
	}

	public void AtoS()
	{
		int len=(mem[operand]/100)-1;
		int cnt=operand;
		System.out.printf("%02d ? %c",operand,tochar(mem[cnt++]%100));
		while(len>0)
		{
			System.out.print(tochar(mem[cnt]/100));
			System.out.print(tochar(mem[cnt++]%100));
			len-=2;
		}
		System.out.println();
	}

	public int toUpper(int c)
	{
		return (c==32)?c:c-32;
	}

	public char tochar(int c)
	{
		return (char)c;
	}

	private void memorydump()
	{
		System.out.println("REGISTERS:");
		System.out.printf("accumulator %c%04d\n",(accumulator>=0)?'+':' ',accumulator);
		System.out.printf("instructionCounter %02d\n",ins_count);
		System.out.printf("instructionRegister +%04d\n",ins_reg);
		System.out.printf("operationCode %d\n",opcode);
		System.out.printf("operand %02d\n",operand);
		System.out.println("\nMEMORY:");
		System.out.printf("%8d%6d%6d%6d%6d%6d%6d%6d%6d%6d\n",0,1,2,3,4,5,6,7,8,9);
		for(int i=0;i<mem.length;i+=10)
		{
			System.out.printf("%3d ",i);
			for(int j=0;j<i+10;j++)
				System.out.printf("%c%04d ",(mem[j]>=0)?'+':' ',mem[j]);
			System.out.println();
		}
	}

	private void fatalerror(String s)
	{
		System.out.printf("\n*** %s ***\n",s);
		System.out.println("*** Simpletron execution abnormally terminated. ***");
		memorydump();
		opcode=FATALERROR;
	}

	private boolean isValid(int x)
	{
		return (x>=-9999&&x<=9999);
	}

	public static void main(String args[])
	{
		MLP SML=new MLP();
		SML.run();
	}
}
