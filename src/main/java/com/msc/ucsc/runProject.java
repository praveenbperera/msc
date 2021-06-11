package com.msc.ucsc;

import java.io.IOException;

public class runProject {

    public static void main(String[] args) {
        readDefinitionFile rdf = new readDefinitionFile();
        try {
            System.out.println("======== " + rdf.readFile("GET","version"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
