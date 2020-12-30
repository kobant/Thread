package bankerTest;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 13:44
 */

//***********************************************************************//
//*																  	    *//
//*本程序需要预先设置三个文件：Available_list.txt，Max_list.txt，Allocation_list.txt *//
//*  各文件格式如下：      					    					    *//
//*  Available_list.txt		 											    *//
//*  3        //表示共有3类资源					   					    *//
//*  10 5 7   //表示各类资源的初始可用个数，即Available[0]=10, Available[1]=5  *//
//*																	     *//
//*																         *//
//*  Max_list.txt		  												     *//
//*  5        //表示共有5个进程		 								     *//
//*  7 5 3   //表示各个进程需要各类资源的最大数目，即Max[0][0]=7, Max[0][1]=5*//
//*  3 2 2																  *//
//*  9 0 2																  *//
//*  2 2 2																  *//
//*  4 3 3																  *//
//*	  																	  *//
//*	  																	  *//
//*  Allocation_list.txt														  *//
//*  0 1 0 //表示各个进程已分配各类资源的数目                               *//
//*  2 0 0																  *//
//*  3 0 2																  *//
//*  2 1 1																  *//
//*  0 0 2																  *//
//*	  																      *//
//*************************************************************************//

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestBankerThread {
	static int resourceClassNum,processNum;

	static int[] Available;			//可利用资源向量
	static int[][] Max;			    //最大需求矩阵
	static int[][] Allocation;	        //分配矩阵(每类资源当前分配到每一个进程的资源数)
	static int[][] Need;	        	//需求矩阵
	public static void main(String[] args) {
		OuterLoop:
		do {
			readAndSetNum();    //读取并设置资源类型数和进程数
			System.out.println("进程个数: " + processNum + "\t资源个数: " + resourceClassNum);
			Available = new int[resourceClassNum];
			Max = new int[processNum][resourceClassNum];
			Allocation = new int[processNum][resourceClassNum];
			Need=new int[processNum][resourceClassNum];
			//读入Available, Max, Allocation
			readAvailableList();
			readMaxList();
			readAllocationList();
			//计算Need和Available
			for (int i = 0; i < processNum; i++) {
				for (int j = 0; j < resourceClassNum; j++)
					Need[i][j] = Max[i][j] - Allocation[i][j];
			}
			for (int i = 0; i < processNum; i++) {
				for (int j = 0; j < resourceClassNum; j++)
					Available[j] -= Allocation[i][j];
			}
			//打印各数据结构当前值
			printAvailable();
			printMax();
			printAllocation();
			printNeed();
			//输入进程号和请求向量Request
			System.out.print("输入发起请求的进程(0-4): ");
			Scanner input = new Scanner(System.in);
			int requestProcess = input.nextInt();
			System.out.print("输入请求资源的数目:按照这样的格式输入 x x x: ");
			int[] Request = new int[resourceClassNum];
			for (int i = 0; i < resourceClassNum; i++) {
				Request[i] = input.nextInt();
				//Request>Need?
				if (Request[i] > Need[requestProcess][i]) {
					System.out.println("Request>Need");
					continue OuterLoop;
				}
				//Request>Available?
				if (Request[i] > Available[i]) {
					System.out.println("Request>Available");
					continue OuterLoop;
				}
			}
			//试分配，更新各相关数据结构
			for (int i = 0; i < resourceClassNum; i++){
				Available[i]-=Request[i];
				Need[requestProcess][i]-=Request[i];
				Allocation[requestProcess][i]+=Request[i];
			}
			System.out.println();
			//是否通过安全性检测
			if(isSafe()){
				System.out.println("开始给第"+requestProcess+"个进程分配资源...");
				writeAllocationList();
				System.out.println("分配完成，已更新Allocation_list.txt");
			}
		}while(againAsk());
	}

	static boolean isSafe(){
		List<Integer> safeList=new ArrayList<>();
		System.out.println("开始执行银行家算法...");
		//初始化Work
		int[] Work=new int[resourceClassNum];
		for(int i=0;i<resourceClassNum;i++)
			Work[i]=Available[i];
		//初始化Finish
		boolean[] Finish=new boolean[processNum];
		for(int i=0;i<processNum;i++)
			Finish[i]=false;
		//初始化判断标志
		boolean found=false;
		//初始化满足条件的进程数目
		int FinishCount=0;
		while(FinishCount < processNum) {
			for (int i = 0; i < processNum; i++) {
				if (Finish[i] == true)
					continue;
				//Need[i]<=Work？
				boolean flag = true;
				for (int j = 0; j < resourceClassNum; j++) {
					if(Need[i][j] > Work[j])
						flag=false;
				}
				if (flag) {   //Y
					// 释放资源, 修改数据结构，保存进程号,FinishCount++,found=true
					for(int j=0;j<resourceClassNum;j++)
						Work[j]+=Allocation[i][j];
					safeList.add(i);
					FinishCount++;
					found=true;
					Finish[i]=true;
				} else        //N
					continue;
			}
			if(found==false)
				break;
			else
				found=false;
		}
		boolean flag=true;
		for(int i=0;i<processNum;i++){
			if(Finish[i]==false)
				flag=false;
		}
		if(flag){
			System.out.println("已通过安全性测试");
			//打印安全序列
			System.out.println("安全序列:");
			System.out.print("P"+safeList.get(0));
			for(int i=1;i<safeList.size();i++){
				System.out.print("--->P"+safeList.get(i));
			}
			System.out.println();
			return true;
		}
		else{
			System.out.println("未通过安全性测试");
			return false;
		}
	}
	static void readAndSetNum(){
		try {
			Path AvailablePath = Paths.get("Available_list.txt");
			Path MaxPath = Paths.get("Max_list.txt");
			List<String> AvailableLines = Files.readAllLines(AvailablePath);
			List<String> MaxLines = Files.readAllLines(MaxPath);
			resourceClassNum = Integer.parseInt(AvailableLines.get(0));
			processNum = Integer.parseInt(MaxLines.get(0));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	static void readAvailableList(){
		Path filePath=Paths.get("Available_list.txt");
		try{
			List<String> lines = Files.readAllLines(filePath);
			String[] linesInfo=lines.get(1).split(" ");
			int i=0;
			for(String resNum:linesInfo)
				Available[i++]=Integer.parseInt(resNum);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void readMaxList(){
		Path filePath=Paths.get("Max_list.txt");
		try{
			List<String> lines = Files.readAllLines(filePath);
			for(int i=1;i<=processNum;i++){
				int j=0;
				String[] linesInfo=lines.get(i).split(" ");
				for(String resNum:linesInfo)
					Max[i-1][j++]=Integer.parseInt(resNum);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void readAllocationList(){
		Path filePath=Paths.get("Allocation_list.txt");
		try{
			List<String> lines = Files.readAllLines(filePath);
			for(int i=0;i<processNum;i++){
				int j=0;
				String[] linesInfo=lines.get(i).split(" ");
				for(String resNum:linesInfo)
					Allocation[i][j++]=Integer.parseInt(resNum);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void printAvailable(){
		System.out.println("可用资源向量Available:");
		for(int i:Available){
			System.out.print(i+"\t");
		}
		System.out.println();
	}
	static void printMax(){
		System.out.println("最大需求矩阵Max:");
		for(int i=0;i<processNum;i++) {
			for (int j = 0; j < resourceClassNum; j++)
				System.out.print(Max[i][j] + "\t");
			System.out.println();
		}
	}
	static void printAllocation(){
		System.out.println("已分配矩阵Allocation:");
		for(int i=0;i<processNum;i++) {
			for (int j = 0; j < resourceClassNum; j++)
				System.out.print(Allocation[i][j] + "\t");
			System.out.println();
		}
	}
	static void printNeed(){
		System.out.println("已分配矩阵Allocation:");
		for(int i=0;i<processNum;i++) {
			for (int j = 0; j < resourceClassNum; j++)
				System.out.print(Need[i][j] + "\t");
			System.out.println();
		}
	}
	static void writeAllocationList(){
		try {
			Path newPath = Paths.get("Allocation_list.txt");
			List<String> list = new ArrayList<>();
			for (int i = 0; i < processNum; i++) {
				String line = "";
				for (int j = 0; j < resourceClassNum; j++) {
					line += (Allocation[i][j] + " ");
				}
				line = line.substring(0, line.length() - 1);
				list.add(line);
			}
			Files.write(newPath, list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	static boolean againAsk(){
		System.out.println("要继续吗?(y-继续; n-终止):");
		Scanner input=new Scanner(System.in);
		String choice=input.nextLine();
		if(choice.toLowerCase().equals("y"))
			return true;
		else
			return false;
	}
}
