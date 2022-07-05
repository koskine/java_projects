/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.prog3.standings;



import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

/**
 * A class for maintaining team statistics and standings. Team standings are 
 * determined by the following rules:
 * 
 * <ul>
 *      <li> Primary rule: points total. Higher points come first.</li>
 *      <li> Secondary rule: goal difference (scored minus allowed). Higher 
 *           difference comes first.</li>
 *      <li> Tertiary rule: number of goals scored. Higher number comes 
 *           first.</li>
 *      <li> Last rule: natural String order of team names.</li>
 * </ul>
 */

public class Standings {
    private ArrayList<Team> teams;

    /**
     * Constructs an empty Standings object.
     */
    public Standings(){
        ArrayList<Team> team_list = new ArrayList<>();
        this.teams = team_list;
    }

    /**
     * Constructs a Standings object that is initialized with the game data read
     * from the specified file. The result is identical to first constructing an
     * empty Standing object and then calling {@link #readMatchData(filename) }.
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the 
     * specified file does not exist).
     */
    public Standings(String filename) throws IOException{
        ArrayList<Team> team_list = new ArrayList<>();
        this.teams = team_list;
        try(var file = new BufferedReader(new FileReader(filename))){
            String result;
            while((result = file.readLine()) != null){
                String[] parts = result.split("\\t");
                String[] score = parts[1].split("-");
                addMatchResult(parts[0], Integer.parseInt(score[0]), 
                        Integer.parseInt(score[1]), parts[2]);
            }
        }
    }

    /**
     * <p>Reads game data from the specified file and updates the team statistics 
     * and standings accordingly.</p>
     * 
     * <p>The match data file is expected to contain lines of form 
     * "teamNameA\tgoalsA-goalsB\tteamNameB". Note that the '\t' are tabulator 
     * characters.</p>
     * 
     * <p>E.g. the line "Iceland\t3-2\tFinland" would describe a match between 
     * Iceland and Finland where Iceland scored 3 and Finland 2 goals.</p>
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the 
     * specified file does not exist).
     */
    public final void readMatchData(String filename) throws IOException{
        try(var file = new BufferedReader(new FileReader(filename))){
            String result;
            while((result = file.readLine()) != null){
                String[] parts = result.split("\\t");
                String[] score = parts[1].split("-");
                addMatchResult(parts[0], Integer.parseInt(score[0]), 
                        Integer.parseInt(score[1]), parts[2]);
            }
        }
    }

    /**
     * Updates the team statistics and standings according to the match result 
     * described by the parameters.
     * @param teamNameA the name of the first ("home") team.
     * @param goalsA the number of goals scored by the first team.
     * @param goalsB the number of goals scored by the second team.
     * @param teamNameB the name of the second ("away") team.
     */
    public void addMatchResult(String teamNameA, int goalsA, int goalsB, 
            String teamNameB){
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

    /**
     * Returns a list of the teams in the same order as they would appear in a 
     * standings table.
     * @return a list of the teams in the same order as they would appear in a 
     * standings table.
     */
    public List<Team> getTeams() {
        Collections.sort(this.teams, team_comparator);
        return this.teams;
    }

    private static Comparator<Team> team_comparator = new 
        Comparator<Standings.Team>() {
        public int compare(Team teamA, Team teamB){
            if (teamA.getPoints() != teamB.getPoints()){
                int pointsA = teamA.getPoints();
                int pointsB = teamB.getPoints();
                if (pointsA < pointsB) return 1;
                else if (pointsA > pointsB) return -1;
                else return 0;
            }
            else if (teamA.getScored()-teamA.getAllowed() != teamB.getScored()- 
                    teamB.getAllowed()){
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

    /**
     * Prints a formatted standings table to the provided output stream.
     * @param out the output stream to use when printing the standings table.
     */
    public void printStandings(PrintStream out){
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


    

    /**
     * A class for storing statistics of a single team. The class offers only 
     * public getter functions. The enclosing class Standings is responsible for
     * setting and updating team statistics.
     */
    public static class Team{
        private String name;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int points;
        private int games;

        /**
         * Constructs a Team object for storing statistics of the named team.
         * @param name the name of the team whose statistics the new team object
         * stores. 
         */
        public Team(String name){
            this.name = name;
            this.wins = 0;
            this.ties = 0;
            this.losses = 0;
            this.scored = 0;
            this.allowed = 0;
            this.games = 0;
        }
        
        /**
         * Returns the name of the team.
         * @return the name of the team.
         */
        public String getName(){
            return this.name;
        }
        /**
         * Returns the number of wins of the team.
         * @return the number of wins of the team.
         */
        public int getWins(){
            return this.wins;
        }
        /**
         * Returns the number of ties of the team.
         * @return the number of ties of the team.
         */
        public int getTies(){
            return this.ties;
        }
        /**
         * Returns the number of losses of the team.
         * @return the number of losses of the team.
         */
        public int getLosses(){
            return this.losses;
        }
        /**
         * Returns the number of goals scored by the team.
         * @return the number of goals scored by the team.
         */
        public int getScored(){
            return this.scored;
        }
        /**
         * Returns the number of goals allowed (conceded) by the team.
         * @return the number of goals allowed (conceded) by the team.
         */
        public int getAllowed(){
            return this.allowed;
        }
        /**
         * Returns the overall number of points of the team.
         * @return the overall number of points of the team.
         */
        public int getPoints(){
            return this.points;
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
