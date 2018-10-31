/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raidsrotationgenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author austinlee
 */
public class RaidsRotationGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String[] raidrotation1 = {"tekton", "vasa", "guardians", "skeletons", "shamans", "muttadile", "vanguards", "vespula"};
        final String[] raidrotation2 = {"tekton", "muttadile", "guardians", "vespula", "shamans", "vasa", "vanguards", "skeletons"};
        FindRaid<String> raid1 = new FindRaid<>(raidrotation1);
        FindRaid<String> raid2 = new FindRaid<>(raidrotation2);
        Queue<String> raid1CW = new LinkedList<>();
        Queue<String> raid2CW = new LinkedList<>();
        Queue<String> raid1CCW = new LinkedList<>();
        Queue<String> raid2CCW = new LinkedList<>();
        Scanner cin = new Scanner(System.in);
        String temp;
        int counter = 0;
        System.out.printf("%d Enter boss name ", counter++);
        while(cin.hasNext()){
            temp = cin.next().toLowerCase();
            if(temp.equals("0")) break;
            System.out.println("Adding " + temp);
            raid1CW.add(temp);
            raid1CCW.add(temp);
            raid2CW.add(temp);
            raid2CCW.add(temp);
            System.out.printf("%d Enter boss name ", counter++);
        }
        System.out.println("Raid 1 Rotations:");
        raid1.searchRaidCW(raid1CW);
        raid1.searchRaidCCW(raid1CCW);
        System.out.println("Raid 2 Rotations:");
        raid2.searchRaidCW(raid2CW);
        raid2.searchRaidCCW(raid2CCW);
    }
}
