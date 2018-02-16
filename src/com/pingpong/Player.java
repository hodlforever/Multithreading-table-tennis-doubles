package com.pingpong;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Player implements Runnable {
	 public static int  score_TeamA=0,score_TeamB=0;//���Ӹ��Եĵ÷֡�
	 public static int serving = 0;//����Ȩ��0����A�ӷ���1����B�ӷ���
	 public static int number = 0;//������ձ����ɼ��Ĵ���
	 public static boolean status;//��ǰ״̬��false�����ڷ���״̬��true�������״̬��
	 
	 private char team_Name;//��Ӷ���
	 private String player_Name;//�˶�Ա����
	 private Semaphore semaphore1;//�ź���1
	 private Semaphore semaphore2;//�ź���2
	 
	 int count = 0;//ÿ���˶�Ա�ķ������

	 //���캯��
	 public Player(char team_Name,String player_Name,Semaphore semaphore1,Semaphore semaphore2) {
		 this.team_Name=team_Name;
		 this.player_Name=player_Name;
		 this.semaphore1=semaphore1;
		 this.semaphore2=semaphore2;		 
	}
		public boolean shot(){//�ж��Ƿ���磬�����緵��true�������ڷ���false��
			Random random=new Random();
			float s=(float) (random.nextInt(100)/100.0);
			if(s>0.8)
				return true;
			return false;
		}
	@Override
	public synchronized void run() {//synchronized�ؼ������Ρ�
		while(true){
				try {
					semaphore1.acquire();
					//semaphore2.acquire();
					if(score_TeamA+score_TeamB>=24){
						if(number == 0){
							//System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
							if(Player.score_TeamA>Player.score_TeamB){
								System.out.println("����������\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"The team A1A2 wins.");
							}else if(Player.score_TeamA<Player.score_TeamB){
								System.out.println("����������\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"The team B1B2 wins.");
							}else{
								System.out.println("����������\nFinal score=("+Player.score_TeamA+","+Player.score_TeamB+")."+"ƽ�֡�");
							}
							number++;
						}
						semaphore1.release();
						semaphore2.release();
						//System.out.println(scoreA+scoreB+"����");
		//				synchronized(this){
		//					if(number == 0){
		//						System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
		//						if(Participant.scoreA>Participant.scoreB){
		//							System.out.println("The team A1A2 wins.");
		//						}else if(Participant.scoreA<Participant.scoreB){
		//							System.out.println("The team B1B2 wins.");
		//						}else{
		//							System.out.println("ƽ�֡�");
		//						}
		//						number++;
		//					}
		//				synchronized(this){
		//					if(number == 0){
		//						//System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
		//						if(Participant.scoreA>Participant.scoreB){
		//							System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"The team A1A2 wins.");
		//						}else if(Participant.scoreA<Participant.scoreB){
		//							System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"The team B1B2 wins.");
		//						}else{
		//							System.out.println("����������\nFinal score=("+Participant.scoreA+","+Participant.scoreB+")."+"ƽ�֡�");
		//						}
		//						number++;
		//					}
		//				}	
						break;
					}
					
					if(!status){//����״̬
//						synchronization.acquire();//P����
//						mutex.acquire();//P����
						if((team_Name=='A' && serving==0)||(team_Name=='B' && serving==1)){
								if(count<6){//��֤ÿ���˶�Ա��6����
									count++;
									status=true;//����״̬
									if(serving==0)//�������A�ӷ�����ô�´�B�ӷ���
										serving=1;
									else
										serving=0;
								}else{//��������Ѿ��ﵽ6�Σ��Ժ����ٷ����ˡ�
									semaphore1.release();
									//semaphore2.release();
								}
						}else{
							//semaphore1.release();
							semaphore2.release();
						}
					}
					
					if(status){//����״̬
//						synchronization.acquire();//P����
//						mutex.acquire();//P����
						if(this.shot()){//����
							status=false;//����״̬
							if(team_Name == 'A'){//A�Ӷ�Ա������硣
								score_TeamB++;//B���ּܷ�һ�֡�
								System.out.println(player_Name+"-out\n----The side B1B2 wins one point.  Score="+"("+score_TeamA+","+score_TeamB+")");
							}else{//B�Ӷ�Ա������硣
								score_TeamA++;//A���ּܷ�һ�֡�
								System.out.println(player_Name+"-out\n----The side A1A2 wins one point.  Score="+"("+score_TeamA+","+score_TeamB+")");
							}
						}else{//����
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
