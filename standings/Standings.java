/*
    creator Iiro Koskinen H299947
*/

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

import javax.xml.transform.Templates;


public class Standings {
    private ArrayList<Team> teams;

    public Standings(){
        ArrayList<Team> team_list = new ArrayList<>();
        this.teams = team_list;
    }

    public Standings(String filename) throws IOException{
        ArrayList<Team> team_list = new ArrayList<>();
        this.teams = team_list;
        try(var file = new BufferedReader(new FileReader(filename))){
            String result;
            while((result = file.readLine()) != null){
                String[] parts = result.split("\\t");
                String[] score = parts[1].split("-");
                addMatchResult(parts[0], Integer.parseInt(score[0]), Integer.parseInt(score[1]), parts[2]);
            }
        }
    }

    public void readMatchData(String filename) throws IOException{
        try(var file = new BufferedReader(new FileReader(filename))){
            String result;
            while((result = file.readLine()) != null){
                String[] parts = result.split("\\t");
                String[] score = parts[1].split("-");
                addMatchResult(parts[0], Integer.parseInt(score[0]), Integer.parseInt(score[1]), parts[2]);
            }
        }
    }

    public void addMatchResult(String teamNameA, int goalsA, int goalsB, String teamNameB){
        //figuring out result
        int result = whoWon(goalsA, goalsB);
        // adding score to teams, and creating team if it doesnt exist
        Boolean team_a_exists = false;
        Boolean team_b_exists = false;
        /*if (this.teams == null){
            Team new_team = new Team(teamNameA);
            this.teams.add(new_team);
        }*/
        for (Team team : this.teams){
            if(team.name.equalsIgnoreCase(teamNameA)){
                team_a_exists = true;
                team.addGame();
                team.addScored(goalsA);
                team.addAllowed(goalsB);
                if (result == 1){
                    team.addWin();
                    team.addPoints(3);
                }
                else if (result == 2){
                    team.addTie();
                    team.addPoints(1);
                }
                else team.addLoss();
            }
            else if(team.name.equalsIgnoreCase(teamNameB)){
                team_b_exists = true;
                team.addGame();
                team.addScored(goalsB);
                team.addAllowed(goalsA);
                if (result == 3){
                    team.addWin();
                    team.addPoints(3);
                }
                else if (result == 2){
                    team.addTie();
                    team.addPoints(1);
                }
                else team.addLoss();
            }
        }
        if (!team_a_exists){
            Team new_team = new Team(teamNameA);
            this.teams.add(new_team);
            new_team.addGame();
                new_team.addScored(goalsA);
                new_team.addAllowed(goalsB);
                if (result == 1){
                    new_team.addWin();
                    new_team.addPoints(3);
                }
                else if (result == 2){
                    new_team.addTie();
                    new_team.addPoints(1);
                }
                else new_team.addLoss();
        }
        if (!team_b_exists){
            Team new_team = new Team(teamNameB);
            this.teams.add(new_team);
            new_team.addGame();
                new_team.addScored(goalsB);
                new_team.addAllowed(goalsA);
                if (result == 3){
                    new_team.addWin();
                    new_team.addPoints(3);
                }
                else if (result == 2){
                    new_team.addTie();
                    new_team.addPoints(1);
                }
                else new_team.addLoss();
        }
    }
    private int whoWon(int A, int B){
        // function returns 1 if A won, 2 if tied and 3 if B won
        if (A > B) return 1;
        else if (A == B) return 2;
        else return 3;

    }

    public ArrayList<Team> getTeams() {
        Collections.sort(this.teams, team_comparator);
        return this.teams;
    }

    private static Comparator<Team> team_comparator = new Comparator<Standings.Team>() {
        public int compare(Team teamA, Team teamB){
            if (teamA.getPoints() != teamB.getPoints()){
                int pointsA = teamA.getPoints();
                int pointsB = teamB.getPoints();
                if (pointsA < pointsB) return 1;
                else if (pointsA > pointsB) return -1;
                else return 0;
            }
            else if (teamA.getScored()-teamA.getAllowed() != teamB.getScored()- teamB.getAllowed()){
                int a_difference = teamA.getScored()-teamA.getAllowed();
                int b_difference = teamB.getScored()- teamB.getAllowed();
                if (a_difference < b_difference) return 1;
                else if (a_difference > b_difference) return -1;
                else return 0;
            }
            else if (teamA.getScored() != teamB.getScored()){
                if (teamA.getScored() < teamB.getScored()) return 1;
                else if (teamA.getScored() > teamB.getScored()) return -1;
                else return 0;
            }
            else return teamA.getName().compareTo(teamB.getName());
        }
    };

    public void printStandings(){
        Collections.sort(this.teams, team_comparator);
        int longest_name = 1;
        for (Team team : this.teams){
            int length = team.getName().length();
            if (length > longest_name){
                longest_name = length;
            }
        }
        String format = "%-" + longest_name + "s %3d %3d %3d %3d %6s %3d%n";
        for (Team team : this.teams){
            String n = team.getName();
            int g = team.getGames();
            int w = team.getWins();
            int t = team.getTies();
            int l = team.getLosses();
            String d = team.getScored() + "-" + team.getAllowed();
            int p = team.getPoints();
            System.out.format(format, n, g, w, t, l, d, p);
        }
    }


    


    public static class Team{
        private String name;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int points;
        private int games;

        public Team(String name){
            this.name = name;
            this.wins = 0;
            this.ties = 0;
            this.losses = 0;
            this.scored = 0;
            this.allowed = 0;
            this.games = 0;
        }
        public int getWins(){
            return this.wins;
        }
        public int getTies(){
            return this.ties;
        }
        public int getLosses(){
            return this.losses;
        }
        public int getScored(){
            return this.scored;
        }
        public int getAllowed(){
            return this.allowed;
        }
        public int getPoints(){
            return this.points;
        }
        public String getName(){
            return this.name;
        }
        private int getGames(){
            return this.games;
        }

        private void addGame(){
            this.games++;
        }
        private void addWin(){
            this.wins++;
        }
        private void addTie(){
            this.ties++;
        }
        private void addLoss(){
            this.losses++;
        }
        private void addScored(int i){
            this.scored += i; 
        }
        private void addAllowed(int i){
            this.allowed += i; 
        }
        private void addPoints(int i){
            this.points += i; 
        }
        
        
    }

    
    
}
