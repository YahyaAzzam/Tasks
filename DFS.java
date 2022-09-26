import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.awt.*;



public class PlayersFinder {
    static int count = 0;
    public int findSiz(int[] dim, int i, int j, int[][] Photo){
        if((i<0)||(i==Photo.length)||(j<0)||(j==Photo[i].length)||(Photo[i][j]==0))
            return 0;
        Photo[i][j] = 0;
        if(dim[0]>i)
            dim[0]=i;
        else if(dim[1]<i)
            dim[1]=i;
        if(dim[2]>j)
            dim[2]=j;
        else if(dim[3]<j)
            dim[3]=j;
        return 1+findSiz(dim,i-1,j,Photo)+findSiz(dim,i+1,j,Photo)+findSiz(dim,i,j-1,Photo)+findSiz(dim,i,j+1,Photo);
    }
    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold){
        Point[] centre = new Point[51];Point temp = new Point();
        int size;int[] dim = new int[4];
        int[][] Photo = new int[photo.length][photo[0].length()];
        String t = Integer.toString(team);
        String[][] PHOTO = new String[photo.length][photo[0].length()];
        for(int i=0;i<photo.length;i++){
            PHOTO[i] = photo[i].split("");
            for(int j=0;j<photo[0].length();j++){
                if(PHOTO[i][j].equals(t))
                    Photo[i][j] = 1;
                else
                    Photo[i][j] = 0;
            }
        }
        for(int i=0;i<PHOTO.length;i++){
            for(int j=0;j<PHOTO[0].length;j++){
                if(Photo[i][j]==1){
                    dim[0] = Photo.length;dim[1] = -1;dim[2] = Photo[0].length;dim[3] = -1;
                    size = findSiz(dim,i,j,Photo);
                    if(size*4>=threshold){
                        if(size==1)
                            centre[count++] = new Point((dim[2]*2+1), (dim[0]*2+1));
                        else
                            centre[count++] = new Point((dim[2]+dim[3]+1), (dim[0]+dim[1]+1));
                    }
                }
            }
        }
        for(int i=1;i<count;i++){
            for(int j=i;j>0&&centre[j].x<centre[j-1].x;j--){
                temp = centre[j];
                centre[j] = centre[j-1];
                centre[j-1] = temp;
            }
        }
        for(int i=1;i<count;i++){
            for(int j=i;j>0&&centre[j].x==centre[j-1].x;j--){
                for(int k=j;k>0&&centre[k].y<centre[k-1].y;k--){
                    temp = centre[k];
                    centre[k] = centre[k-1];
                    centre[k-1] = temp;
                }
            }
        }
        return centre;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] n = input.nextLine().split(", ");
        if(n[0].isEmpty())
            System.out.println("[]");
        else{
            int n1 =Integer.parseInt(n[0]);int n2 = Integer.parseInt(n[1]);
            String[] photo = new String[n1];
            for(int i=0;i<n1;i++){
                photo[i] = input.nextLine();
            }
            int t = input.nextInt();
            int ts = input.nextInt();
            Point[] x = new PlayersFinder().findPlayers(photo,t,ts);
            System.out.print("[");
            for(int i=0;n1!=0&&n2!=0&&i<count;i++){
                System.out.print("("+x[i].x+", "+x[i].y+")");
                if(i<count-1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}
