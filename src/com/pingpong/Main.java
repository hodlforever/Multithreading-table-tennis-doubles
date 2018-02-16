package com.pingpong;

import java.util.concurrent.Semaphore;

public class Main {
	public static void main(String[] args) {
		Semaphore semaphore1=new Semaphore(1);//信号量1
		Semaphore semaphore2=new Semaphore(0);//信号量2
		Thread A1=new Thread(new Player('A',"A1",semaphore1,semaphore2));
		Thread A2=new Thread(new Player('A',"A2",semaphore1,semaphore2));
		Thread B1=new Thread(new Player('B',"B1",semaphore2,semaphore1));
		Thread B2=new Thread(new Player('B',"B2",semaphore2,semaphore1));
		A1.start();
		A2.start();
		B1.start();
		B2.start();
//		System.out.println("比赛结束！\nFinal score=("+Participant.scoreA+","+Participant.scoreB+").");
//		if(Participant.scoreA>Participant.scoreB){
//			System.out.println("The team A1A2 wins.");
//		}else if(Participant.scoreA<Participant.scoreB){
//			System.out.println("The team B1B2 wins.");
//		}else{
//			System.out.println("平局。");
//		}
	}
}

