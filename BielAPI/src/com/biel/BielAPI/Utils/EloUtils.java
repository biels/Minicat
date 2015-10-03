package com.biel.BielAPI.Utils;

import java.util.ArrayList;

public class EloUtils {
	public static Pair<Double, Double> calculateEloChange(double elo1, double elo2, int winner, double K, boolean absolute){
		//WINNER 1, 2, [0] <- Empat
		double R1 = Math.pow(10, elo1 / 400.0),
			   R2 = Math.pow(10, elo2 / 400.0); 
		double E1 = R1 / (R1 + R2),
			   E2 = R2 / (R1 + R2); 
		double S1 = (winner == 1 ? 1 : (winner == 0 ? 0.5 : 0)),
			   S2 = (winner == 2 ? 1 : (winner == 0 ? 0.5 : 0));
		double r1 = (absolute ? elo1 : 0) + K * (S1 - E1),
			   r2 = (absolute ? elo2 : 0) + K * (S2 - E2);
		return new Pair<Double, Double>(r1, r2);
	}
	public static Pair<Double, Double> calculateExpectedPercentage(double elo1, double elo2){
		//WINNER 1, 2, [0] <- Empat
		double R1 = Math.pow(10, elo1 / 400.0),
			   R2 = Math.pow(10, elo2 / 400.0); 
		double E1 = R1 / (R1 + R2),
			   E2 = R2 / (R1 + R2); 
		return new Pair<Double, Double>(E1, E2);
	}
	public static ArrayList<ArrayList<Double>> calculateEloGroupChange(ArrayList<Double> winners, ArrayList<Double> loosers, double K, boolean absolute){
		ArrayList<ArrayList<Double>> results = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> winners_updated = new ArrayList<Double>();
		ArrayList<Double> loosers_updated = new ArrayList<Double>();
		for(Double w : winners){
			int w_id = winners.indexOf(w);
			Double w_change = 0D;
			for(Double l : loosers){
				int l_id = loosers.indexOf(l);
				Pair<Double, Double> link = calculateEloChange(w, l, 1, K / loosers.size(), false);
				w_change  += link.getFirst() * 1.15;
				if(loosers_updated.size() > l_id){ // 1 > 0
					loosers_updated.set(l_id, loosers_updated.get(l_id) + link.getSecond());
				}else{
					loosers_updated.add(link.getSecond());
				}
				System.out.println(link.getFirst());
			}
			winners_updated.add(w_change);
		}
		results.add(winners_updated);
		results.add(loosers_updated);
		return results;
	}
	public static ArrayList<Double> calculateEloGroupChange(ArrayList<Double> orderedWinners, double K, boolean absolute){
		ArrayList<Double> result = new ArrayList<Double>();
		for(Double w : orderedWinners){
			int w_id = orderedWinners.indexOf(w);
			Double w_change = 0D;
			for(Double o : orderedWinners){
				int o_id = orderedWinners.indexOf(o);
				if(w_id == o_id)continue;
				Pair<Double, Double> link = calculateEloChange(w, o, (w_id < o_id ? 1 : 2), K / orderedWinners.size(), false);
				w_change  += link.getFirst();
				System.out.println(link.getFirst());
			}
			result.add(w_change);
		}
		return result;
	}
}
