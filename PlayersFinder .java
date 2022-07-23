import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.awt.*;

interface IPlayersFinder {

    /**
     * Search for players locations at the given photo
     * @param photo
     *     Two dimension array of photo contents
     *     Will contain between 1 and 50 elements, inclusive
     * @param team
     *     Identifier of the team
     * @param threshold
     *     Minimum area for an element
     *     Will be between 1 and 10000, inclusive
     * @return
     *     Array of players locations of the given team
     */
    java.awt.Point[] findPlayers(String[] photo, int team, int threshold);
}


public class PlayersFinder implements IPlayersFinder{
    public static int[] xy = {0,0,0,0};
    public static int h = 0;
    public int sumofsquares(String[][] photo,String team,int i,int j){
        int totalarea = 0;
        photo[i][j] = "X";
        if(i < photo.length-1){
            if(photo[i+1][j].equals(team)){
                totalarea++;
                totalarea += sumofsquares(photo,team,i+1,j);
            }
        }
        if(j < photo[0].length-1){
            if(photo[i][j+1].equals(team)){
                totalarea++;
                totalarea += sumofsquares(photo,team,i,j+1);
            }
        }
        if(i > 0){
            if(photo[i-1][j].equals(team)){
                totalarea++;
                totalarea += sumofsquares(photo,team,i-1,j);
            }
        }
        if(j > 0){
            if(photo[i][j-1].equals(team)){
                totalarea++;
                totalarea += sumofsquares(photo,team,i,j-1);
            }
        }
        
        if(Math.abs(xy[0] - i) > Math.abs(xy[0] - xy[2]))
            xy[2] = i;
        
        if(Math.abs(xy[1] - j) > Math.abs(xy[1] - xy[3]))
            xy[3] = j;
        
        if(xy[0] < i)
            xy[0] = i;
        
        if(xy[1] <j)
            xy[1] = j;
        
        return totalarea;
    }
    

    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold){
        Point[] players = new Point[50];
        
        int totalarea;
        String strteam = Integer.toString(team);
        String[][] betterphoto = new String[photo.length][];
        for(int i=0;i<photo.length;i++){
            betterphoto[i] = photo[i].split("");
        }
        for(int i=0;i<betterphoto.length;i++){
            for(int j=0;j<betterphoto[0].length;j++){
                if(betterphoto[i][j].equals(strteam)){
                    xy[0] = i;
                    xy[1] = j;
                    xy[2] = i;
                    xy[3] = j;
                    totalarea = 0;
                    totalarea++;
                    totalarea += sumofsquares(betterphoto,strteam,i,j);
                    if(totalarea*4 >= threshold){
                        players[h] = new Point(xy[1] + xy[3] + 1, xy[0] + xy[2] + 1);
                        h++;
                    }
                }
            }
        }
     
        
        return players;
    }
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] rowandcol = sc.nextLine().split(", ");
        int row = Integer.parseInt(rowandcol[0]);
        int col = Integer.parseInt(rowandcol[1]);
        String photoin = "";
        for(int i=0;i<row;i++){
            if(i == row - 1)
                photoin = photoin + sc.next();
            else
                photoin = photoin + sc.next() + " ";
        }
        String[] photo = photoin.split(" ");
        int team = sc.nextInt();
        int threshold = sc.nextInt();
        Point[] players = new PlayersFinder().findPlayers(photo,team,threshold);
        
        Point temp;
        for(int i=0;i<h;i++){
            for(int j=0;j<h;j++){
                if(players[j].x > players[i].x){
                    temp = players[i];
                    players[i] = players[j];
                    players[j] = temp;
                }
                else if(players[j].x == players[i].x){
                    if(players[j].y > players[i].y){
                    temp = players[i];
                    players[i] = players[j];
                    players[j] = temp;
                }
                }
            }
        }
        System.out.print("[");
        for(int i =0;i<h;i++){
            System.out.print("(" + players[i].x + ", " + players[i].y + ")");
            if(i != h-1)
              System.out.print(", ");
        }
        System.out.print("]");
        
    }
}



