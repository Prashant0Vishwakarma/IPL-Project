package com.prashant.ipl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    private static final int ID = 0;
    private static final int SEASON = 1;
    private static final int CITY = 2;
    private static final int DATE = 3;
    private static final int TEAM1 = 4;
    private static final int TEAM2 = 5;
    private static final int TOSS_WINNER = 6;
    private static final int TOSS_DECISION = 7;
    private static final int RESULT = 8;
    private static final int DL_APPLIED = 9;
    private static final int WINNER = 10;
    private static final int WIN_BY_RUNS = 11;
    private static final int WIN_BY_WICKETS = 12;
    private static final int PLAYER_OF_MATCH = 13;
    private static final int VENUE = 14;
    private static final int UMPIRE1 = 15;
    private static final int UMPIRE2 = 16;
    private static final int UMPIRE3 = 17;

    private static final int MATCH_ID = 0;
    private static final int INNING = 1;
    private static final int BATTING_TEAM = 2;
    private static final int BOWLING_TEAM = 3;
    private static final int OVER = 4;
    private static final int BALL = 5;
    private static final int BATSMAN = 6;
    private static final int NON_STRIKER = 7;
    private static final int BOWLER = 8;
    private static final int IS_SUPER_OVER = 9;
    private static final int WIDE_RUNS = 10;
    private static final int BYE_RUNS = 11;
    private static final int LEGBYE_RUNS = 12;
    private static final int NOBALL_RUNS = 13;
    private static final int PENALTY_RUNS = 14;
    private static final int BATSMAN_RUNS = 15;
    private static final int EXTRA_RUNS = 16;
    private static final int TOTAL_RUNS = 17;
    private static final int PLAYER_DISMISSED = 18;
    private static final int DISMISSAL_KIND = 19;
    private static final int FIELDER = 20;
    public static HashSet<String> yearContains = new HashSet<>();

    public static void main(String[] args) {
        List<Match> matches = getMatchData();
        List<Delivery> deliveries = getDeliveryData();
        numberOfMatchesPlayedPerYear(matches);
        numberOfMatchesWonPerTeam(matches);
        extraRunConcededPerTeamIn2016(matches, deliveries);
        mostEconomicalBowlerIn2015(matches, deliveries);
        maximumNumberOfWicketsTakenBYBowler(deliveries);
        for (String str : yearContains) {
            findPlayerWhoCaughtMostWickets(matches, deliveries, str);
        }
    }

    public static List<Match> getMatchData() {
        List<Match> matches = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/prashant/matches.csv"));
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Match match = new Match();
                match.setId(data[ID]);
                match.setSeason(data[SEASON]);
                match.setCity(data[CITY]);
                match.setDate(data[DATE]);
                match.setTeam1(data[TEAM1]);
                match.setTeam2(data[TEAM2]);
                match.setToss_winner(data[TOSS_WINNER]);
                match.setToss_decision(data[TOSS_DECISION]);
                match.setResult(data[RESULT]);
                match.setD1_applied(data[DL_APPLIED]);
                match.setWinner(data[WINNER]);
                match.setWin_by_runs(data[WIN_BY_RUNS]);
                match.setWin_by_wickets(data[WIN_BY_WICKETS]);
                match.setPlayer_of_match(data[PLAYER_OF_MATCH]);
                match.setVenue(data[VENUE]);
                if (data.length > 15) {
                    match.setUmpire1(data[UMPIRE1]);
                    match.setUmpire2(data[UMPIRE2]);
                }
                yearContains.add(data[SEASON]);
                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public static List<Delivery> getDeliveryData() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/prashant/deliveries.csv"));
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setMatch_id(data[MATCH_ID]);
                delivery.setInning(data[INNING]);
                delivery.setBatting_team(data[BATTING_TEAM]);
                delivery.setBowling_team(data[BOWLING_TEAM]);
                delivery.setOver(data[OVER]);
                delivery.setBall(data[BALL]);
                delivery.setBatsman(data[BATSMAN]);
                delivery.setNon_striker(data[NON_STRIKER]);
                delivery.setBowler(data[BOWLER]);
                delivery.setIts_super_over(data[IS_SUPER_OVER]);
                delivery.setWide_runs(data[WIDE_RUNS]);
                delivery.setBye_runs((data[BYE_RUNS]));
                delivery.setLegbye_runs(data[LEGBYE_RUNS]);
                delivery.setNoball_runs(data[NOBALL_RUNS]);
                delivery.setPenalty_runs(data[PENALTY_RUNS]);
                delivery.setBatsman_runs(data[BATSMAN_RUNS]);
                delivery.setExtra_runs(data[EXTRA_RUNS]);
                delivery.setTotal_runs(data[TOTAL_RUNS]);
                if (data.length > 18) {
                    delivery.setPlayer_dismissed(data[PLAYER_DISMISSED]);
                    delivery.setDismissal_kind(data[DISMISSAL_KIND]);
                    if (data.length > 20) {
                        delivery.setFielder(data[FIELDER]);
                    }
                }
                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    public static void numberOfMatchesPlayedPerYear(List<Match> matches) {
        Map<String, Integer> matchPerYear = new HashMap<>();
        System.out.println("Number of matches played per year");
        for (Match match : matches) {
            if (matchPerYear.containsKey(match.getSeason())) {
                matchPerYear.put(match.getSeason(), matchPerYear.get(match.getSeason()) + 1);
            } else {
                matchPerYear.put(match.getSeason(), 1);
            }
        }
        for (Map.Entry<String, Integer> matchEntry : matchPerYear.entrySet()) {
            System.out.println("Number of matches in " + matchEntry.getKey() + " " + "is " + matchEntry.getValue());
        }
    }

    public static void numberOfMatchesWonPerTeam(List<Match> matches) {
        Map<String, Integer> matchWonPerTeam = new HashMap<>();
        for (Match match : matches) {
            if (matchWonPerTeam.containsKey(match.getWinner())) {
                matchWonPerTeam.put(match.getWinner(), matchWonPerTeam.get(match.getWinner()) + 1);
            } else {
                matchWonPerTeam.put(match.getWinner(), 1);
            }
        }
        System.out.println();
        for (Map.Entry<String, Integer> matchEntry : matchWonPerTeam.entrySet()) {
            if (!(matchEntry.getKey().equals("")))
                System.out.println("Number of matches won by  " + matchEntry.getKey() + " " + "is " + matchEntry.getValue());
        }
    }

    public static void extraRunConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        Map<String, Integer> extraRunsConcededPerTeam = new HashMap<>();
        List<String> matchId = new ArrayList<>();
        for (Match match : matches) {
            if (match.getSeason().equals("2016")) {
                matchId.add(match.getId());
            }
        }
        for (Delivery delivery : deliveries) {
            if (matchId.contains(delivery.getMatch_id())) {
                int extraRuns = Integer.parseInt(delivery.getExtra_runs());
                if (extraRunsConcededPerTeam.containsKey(delivery.getBatting_team())) {
                    extraRunsConcededPerTeam.put(delivery.getBatting_team(), extraRunsConcededPerTeam.get(delivery.getBatting_team()) + extraRuns);
                } else {
                    extraRunsConcededPerTeam.put(delivery.getBatting_team(), extraRuns);
                }
            }
        }
        System.out.println();
        for (Map.Entry<String, Integer> entry : extraRunsConcededPerTeam.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }

    public static void mostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        Map<String, Integer> totalRunGivenByBowler = new HashMap<>();
        List<String> matchId = new ArrayList<>();
        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                matchId.add(match.getId());
            }
        }
        Map<String, Double> topEconomicBowlers = new HashMap<>();
        for (Delivery delivery : deliveries) {
            if (matchId.contains(delivery.getMatch_id())) {
                int totalRunByBowler = Integer.parseInt(delivery.getTotal_runs());
                int extraRun = Integer.parseInt(delivery.getBye_runs()) + Integer.parseInt(delivery.getLegbye_runs()) + Integer.parseInt(delivery.getPenalty_runs());
                totalRunByBowler -= extraRun;
                if (totalRunGivenByBowler.containsKey(delivery.getBowler())) {
                    totalRunGivenByBowler.put(delivery.getBowler(), totalRunGivenByBowler.get(delivery.getBowler()) + totalRunByBowler);
                } else {
                    totalRunGivenByBowler.put(delivery.getBowler(), totalRunByBowler);
                }
                if (delivery.getWide_runs().equals("0") && delivery.getNoball_runs().equals("0")) {
                    if (topEconomicBowlers.containsKey(delivery.getBowler())) {
                        topEconomicBowlers.put(delivery.getBowler(), topEconomicBowlers.get(delivery.getBowler()) + 1);
                    } else {
                        topEconomicBowlers.put(delivery.getBowler(), 1.0);
                    }
                }
            }
        }
        for (Map.Entry<String, Double> entry : topEconomicBowlers.entrySet()) {
            int runs = totalRunGivenByBowler.get(entry.getKey());
            double bowl = entry.getValue();
            int over = (int) bowl / 6;
            int temp = (int) bowl % 6;
            String s = over + "." + temp;
            double economy = runs / Double.parseDouble(s);
            topEconomicBowlers.put(entry.getKey(), economy);
        }
        List<Map.Entry<String, Double>> bowlersList = new ArrayList<>(topEconomicBowlers.entrySet());
        bowlersList.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        int count = 1;
        System.out.println();
        for (Map.Entry<String, Double> entry : bowlersList) {
            double value = entry.getValue();
            System.out.print(entry.getKey() + "  ");
            System.out.printf("%.2f", value);
            System.out.println();
            if (count == 5) break;
            count++;
        }
    }

    public static void maximumNumberOfWicketsTakenBYBowler(List<Delivery> deliveries) {
        Map<String, Integer> wicketTakenByBowler = new HashMap<>();
        for (Delivery delivery : deliveries) {
            if (delivery.getPlayer_dismissed() != null) {
                if (wicketTakenByBowler.containsKey(delivery.getBowler())) {
                    wicketTakenByBowler.put(delivery.getBowler(), wicketTakenByBowler.get(delivery.getBowler()) + 1);
                } else {
                    wicketTakenByBowler.put(delivery.getBowler(), 1);
                }
            }
        }
        List<Map.Entry<String, Integer>> wicketList = new ArrayList<>(wicketTakenByBowler.entrySet());
        wicketList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return Integer.compare(o2.getValue(), o1.getValue());
            }
        });
        int count = 1;
        System.out.println();
        for (Map.Entry<String, Integer> entry : wicketList) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
            if (count == 5)
                break;
            count++;
        }
        System.out.println();
    }

    public static void findPlayerWhoCaughtMostWickets(List<Match> matches, List<Delivery> deliveries, String year) {
        try {
            Map<String, Integer> playerCaughtWickets = new HashMap<>();
            List<String> matchId = new ArrayList<>();
            for (Match match : matches) {
                if (match.getSeason().equals(year)) {
                    matchId.add(match.getId());
                }
            }
            for (Delivery delivery : deliveries) {
                if (matchId.contains(delivery.getMatch_id())) {
                    if (delivery.getDismissal_kind() != null && delivery.getDismissal_kind().equals("caught")) {
                        if (playerCaughtWickets.containsKey(delivery.getFielder())) {
                            playerCaughtWickets.put(delivery.getFielder(), playerCaughtWickets.get(delivery.getFielder()) + 1);
                        } else {
                            playerCaughtWickets.put(delivery.getFielder(), 1);
                        }
                    }
                }
            }
            List<Map.Entry<String, Integer>> catchList = new ArrayList<>(playerCaughtWickets.entrySet());
            catchList.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return Integer.compare(o2.getValue(), o1.getValue());
                }
            });
            for (Map.Entry<String, Integer> entry : catchList) {
                System.out.println("maximum number of catch in " + year + " by");
                System.out.println(entry.getKey() + " " + entry.getValue());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
