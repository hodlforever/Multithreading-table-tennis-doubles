package com.pingpong;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Player implements Runnable {
	 public static int  score_TeamA=0,score_TeamB=0;//两队各自的得分。
	 public static int serving = 0;//发球权。0代表A队发球；1代表B队发球。
	 public static int number = 0;//输出最终比赛成绩的次数
	 public static boolean status;//当前状态，false代表处于发球状态，true代表打球状态。
	 
	 private char team_Name;//球队队名
	 private String player_Name;//运动员姓名
	 private Semaphore semaphore1;//信号量1
	 private Semaphore semaphore2;//信号量2
	 
	 int count = 0;//每名运动员的发球次数

	 //构造函数
	 public Player(char team_Name,String player_Name,Semaphore semaphore1,Semaphore semaphore2) {
		 this.team_Name=team_Name;
		 this.player_Name=player_Name;
		 this.semaphore1=semaphore1;
		 this.semaphore2=semaphore2;		 
	}
		public boolean shot(){//判断是否出界，若出界返回true；若界内返回false。
			Random random=new Random();
			float s=(float) (random.nextInt(100)/100.0);
			if(s>0.8)
				return true;
			return false;
		}
	@Override
	public synchronized void run() {//synchronized关键词修饰。
		while(true){
				try {
					semaphore1.acquire();
					//semaphore2.acquire();
					if(score_TeamA+score_TeamB>=24){
						if(number == 0){
							//System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
							if(Player.score_TeamA>Player.score_TeamB){
								System.out.println("比赛结束！\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"The team A1A2 wins.");
							}else if(Player.score_TeamA<Player.score_TeamB){
								System.out.println("比赛结束！\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"The team B1B2 wins.");
							}else{
								System.out.println("比赛结束！\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"平局。");
							}
							number++;
						}
						semaphore1.release();
						semaphore2.release();
						//System.out.println(scoreA+scoreB+"结束");
		//				synchronized(this){
		//					if(number == 0){
		//						System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
		//						if(Participant.scoreA>Participant.scoreB){
		//							System.out.println("The team A1A2 wins.");
		//						}else if(Participant.scoreA<Participant.scoreB){
		//							System.out.println("The team B1B2 wins.");
		//						}else{
		//							System.out.println("平局。");
		//						}
		//						number++;
		//					}
		//				synchronized(this){
		//					if(number == 0){
		//						//System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
		//						if(Participant.scoreA>Participant.scoreB){
		//							System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"The team A1A2 wins.");
		//						}else if(Participant.scoreA<Participant.scoreB){
		//							System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"The team B1B2 wins.");
		//						}else{
		//							System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"平局。");
		//						}
		//						number++;
		//					}
		//				}	
						break;
					}
					
					if(!status){//发球状态
//						synchronization.acquire();//P操作
//						mutex.acquire();//P操作
						if((team_Name=='A' && serving==0)||(team_Name=='B' && serving==1)){
								if(count<6){//保证每个运动员发6个球。
									count++;
									status=true;//打球状态
									if(serving==0)//如果本次A队发球，那么下次B队发球。
										serving=1;
									else
										serving=0;
								}else{//发球次数已经达到6次，以后不能再发球了。
									semaphore1.release();
									//semaphore2.release();
								}
						}else{
							//semaphore1.release();
							semaphore2.release();
						}
					}
					
					if(status){//打球状态
//						synchronization.acquire();//P操作
//						mutex.acquire();//P操作
						if(this.shot()){//出界
							status=false;//发球状态
							if(team_Name == 'A'){//A队队员打球出界。
								score_TeamB++;//B队总分加一分。
								System.out.println(player_Name+"-out\n----The side B1B2 wins one point.  Score="+"("+score_TeamA+","+score_TeamB+")");
							}else{//B队队员打球出界。
								score_TeamA++;//A队总分加一分。
								System.out.println(player_Name+"-out\n----The side A1A2 wins one point.  Score="+"("+score_TeamA+","+score_TeamB+")");
							}
						}else{//界内
							System.out.print(player_Name+"-in-");
						}
						//semaphore1.release();
						semaphore2.release();
					 }
					
				}catch (InterruptedException e) {
			              e.printStackTrace();
				}
		}
	}
}
