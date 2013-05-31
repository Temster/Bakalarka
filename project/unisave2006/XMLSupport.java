/*
 * Created on 12.9.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;

import java.io.BufferedWriter;
import java.io.IOException;

public class XMLSupport {
    public static void printOffset(BufferedWriter w, int offset) throws IOException{
        for(int i=0; i<offset; i++)
            w.write(' ');
    }
    public static String escape(String s){
        String res;
        res = s.replace("&", "&amp;");
        
        res = res.replace("<", "&lt;");
        res = res.replace(">", "&gt;");
        res = res.replace("\"", "&quot;");
        return res;
    }
    public static void write(String s, BufferedWriter w, int offset) throws IOException{
        printOffset(w, offset);
        w.write(s);
        w.newLine();
    }
}
