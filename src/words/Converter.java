package words;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;

public class Converter extends HttpServlet {
    /*We create an object of the class "DataBase" to be able to use all the methods of this class*/
    private DataBase db = new DataBase();    
    
    File information = null;
    FileReader fr = null;
    BufferedReader br = null;
    
    File file; 
    FileWriter out = null;
    
    /*We use this booleans to enter in the information of every word and to take only the first
    noun, verb, adjective and adverb, because sometimes there are more than one noun, verb, adjective
    and adverb*/
    boolean oneNoun, oneVerb, oneAdjective, oneAdverb = true;
    
    /*With "StringTokenizer" we can read the line word by word*/
    StringTokenizer st = null;
    /*We use this string to take every of the file*/
    String s2 = null;
    
    int j = 0;
    
    boolean b = false;
    
    String s;
    int k = 0; 

    public void noun(String line, String word) throws ServletException, IOException {
        out.write("\n\n");
        
        s = "";
         
        /*We use this integer to difference beetwen the three ways we can find the kind of noun*/
        int kind = 0;
        
        boolean plural = false;
        
         /*With "StringTokenizer" we can read the line word by word taking the line we find after the 
        tag "{{en-noun"*/
         st = new StringTokenizer(line);
                 
         //This loop will help us to walk the words of the line we took till the final if the line
         while (st.hasMoreTokens()) {
            /*we take the word after "{{en-noun"*/
            s2 = st.nextToken();
                    
            /*This loop will help us to walk every word of the line and that is way we will be able to
            indetify the meaning of the nouns*/
            for(int i=0;i<s2.length();i++){
                /*If we find the character "|",....*/
                if (s2.charAt(i) == '|') {
                    /*we will take the next character and we will go out 
                    of the loop*/
                    j = i+1;
                    b = true;
                    break;
                /*In the other case, we will go on our search*/
                } else {
                    b = false;
                }
            }
            
            /*We create the head "Noun" in our file ("finalContent")*/
            out.write("  -- Noun --  \n");
            out.write("    ");
            
            /*When we find our limiting character ("|")...*/
            if (b == true) {
                /*We go on our searching in tha next token after our limiting character*/
                for(int i=j;i<s2.length();i++) {
                    /*Now, we identify the meanings of the differents symbols that we can find
                    in Wiktionary inside of nouns head*/
                    if (s2.charAt(i) == '-') {
                        kind = 3;
                        /*We increment the counter to check if there is more relevant information 
                        inside of this head, till find the character "}"*/
                        i += 1;
                        break;
                    } else if (s2.charAt(i) == '~') {
                        kind = 1;
                        i += 1;
                    } 
                    
                    /*If we don't finish the lecture of the noun's features (the character "}" 
                    indicate us if we finished reading the noun head)*/
                    if (s2.charAt(i) != '}') {
                        kind = 2;
                        /*We store the complete noun in the string "s"*/
                        if (s2.charAt(i) != '|') {
                            s += String.valueOf(s2.charAt(i));
                            /*We use this integer to have a way to identify the plural if it is not
                            in the final of the noun head*/
                            k = k + 1;
                        } else {
                            /*If we find "|" we will write in "s" a empty space to join all the kinds
                            of the noun that we are studying*/
                            s += " ";
                            k = k + 1;
                        }
                    /*If we detect an "s" just before "}", we know this noun is the plural way*/
                    } else if (s2.charAt(i) == 's') {
                        plural = true;
                    }
                }
                
                switch (kind) {
                    case 3:
                        out.write("- This noun is uncountable"); 
                        db.setNoun("- This noun is uncountable");
                        break;
                    case 1:
                        out.write("- This noun is countable and uncountable");
                        db.setNoun("- This noun is countable and uncountable");
                        break;
                    case 2:
                        /*If the last letter of the noun is a "s", we will indicate that in 
                        the database*/
                        if (plural = true) {
                            s += " -- plural";
                            out.write(s);
                            /*We add the string "s" (ehich contains the word and the message
                            " -- plural") inside of the database*/
                            db.setNoun(s);
                            plural = false;
                        } else if (s.charAt(k-1) == 's') {
                            s += " -- plural";
                            out.write(word + s);
                            db.setNoun(word + s);
                            plural = false;
                        }
                        break;
                }
                
            /*If we don't find the character "|" at the beginning, it means there is not any noun
            for this word*/
            } else {
               out.write("There is only one kind of noun");
               db.setNoun("There is only one kind of noun");
            }
            
            /*We need to reboot the value of this integer to be always in the same point of the world*/
            k = 0;
        }
    }
    
    public void verb(String line, String word) throws IOException {
        /*We use this integer count the number of the limiting character and that is the way to difference
        beetween the differents vermbal time we can find the verb we are studying*/
        int separator = 0; 
         
        int length = 0;
        
        out.write("\n\n");
         
        /*We reboot the value of the string "s" at the beginning of our method because that is way 
        we will be able to have the different verbs in each moment*/
        s = "";

        /*With "StringTokenizer" we can read the line word by word taking the line we find after the 
        tag "{{en-noun". In that case we use one of the parameters of "StringTokenizer" to read only
        till the finish of the verb head*/
        st = new StringTokenizer(line, "}");
                 
        //This loop will help us to walk the words of the line we took till the final if the line
        while (st.hasMoreTokens()) {
            /*We take the word after "{{en-noun"*/
            s2 = st.nextToken();
                    
            /*This loop will help us to walk every word of the line and that is way we will be able to
            indetify the meaning of the nouns*/
            for(int i=0;i<s2.length();i++) {
                /*If we find the character "|",....*/
                if (s2.charAt(i) == '|') {
                    /*we will take the next character and we will go out 
                    of the loop. We store the next position after "|"*/
                    j = i+1;
                    b = true;
                    separator += 1;
                    break;
                /*In the other case, we will go on our search*/
                } else {
                    b = false;
                }
            }
                    
            /*We create the head "Verb" in our file ("finalContent")*/
            out.write("  -- Verb --  \n");
            out.write("    ");
                        
            /*When we find our limiting character ("|")...*/
            if (b == true) {
                /*We go on our searching in tha previous line since where we were*/
                for(int i=j;i<s2.length();i++) {
                    /*When we don't find the "delimiter" (|) we need to check if the
                    first character of the word coincides with the first character
                    of my string "s2", in that case, we will insert the word with the
                    verbal time that we saw in the document*/
                    if (s2.charAt(i) != '|') {
                        s += s2.charAt(i);
                    } else if (s2.charAt(i) == '|'){
                        s += " ";
                        separator += 1;
                        
                        i = i + 1;
                        s += s2.charAt(i);
                    } 
                    
                    /*That is the way to know if we are in the final of the verb head, because we reach
                    the same length of our line ("s2") and that is the reason why we need to exit from 
                    the loop*/
                    if (i == s2.length()-1) {
                        s += " ";
                        break;
                    }
                }
        
                
                length = s.length();
                
                /*We walk the string "s" with all the information about the verb*/
                for (int k = 0; k < length; k++) {
                    /*When we find a space, it means there is a "|", so we need to see the last 
                    letter to identify which kind of verbal time is it*/
                    if (s.charAt(k) == ' ') {                        
                        /*When you find only one separator, it means we found the past simple form*/
                        if (separator == 1) {
                            if (s.charAt(k-1) == 'd') {
                                word += s;
                                s = word;
                                s += (" -- past tense");
                            }
                        /*With two separators, ir means we have the present third.person sigular form*/
                        } else if (separator == 2) {
                            if (s.charAt(k-1) == 's') {
                                s += (" -- present third-person singular");
                            } else if (s.charAt(k-1) == 'd') {
                                s += (" -- past tense");
                            } else if (s.charAt(k-1) == 'g') {
                                s += (" -- continuos tense");
                            }
                        }    
                        /*With three, we have the past tense form*/
                        if (separator == 3) {
                            s += (" -- past tense");
                        /*and finally, with four, we have the past participle*/
                        } else if (separator == 4) {
                            s += (" -- past participle");
                        }
                    }
                }
                /*We add the string "s" ith all the information inside of the database*/
                out.write(s + "\n");
                db.setVerb(s);

            /*If we don't find the character "|", it means there is not any noun for this word*/
            } else {
                out.write("There is only one kind of verb \n");
                db.setVerb("There is only one kind of verb");
            }            
        }
    }
    
    public void adjective(String line, String word) throws IOException {
         out.write("\n\n");
         s = "";
        
        int length = 0;
        
        boolean uncountable = false;
        
         /*With "StringTokenizer" we can read the line word by word*/
         st = new StringTokenizer(line);
                 
         //This loop will help us to indetify the meaning of the nouns
         while (st.hasMoreTokens()) {
            /*we take the line*/
            s2 = st.nextToken();
                    
            //This loop will help us to indetify the meaning of the nouns
            for(int i=0;i<s2.length();i++){
                /*If we find the character "|",....*/
                if (s2.charAt(i) == '|') {
                    /*we will take the next character and we will go out 
                    of the loop*/
                    j = i+1;
                    b = true;
                    break;
                /*In the other case, we will go on our search*/
                } else {
                    b = false;
                }
            }
            
            /*We create the head "Noun" in our file ("finalContent")*/
            out.write("  -- Adjective --  \n");
            out.write("    ");
            
             /*When we find our limiting character ("|")...*/
            if (b == true) {
                /*We go on our searching in tha previous line since where we were*/
                for(int i=j;i<s2.length();i++) {
                    if (s2.charAt(i) != '}') {
                        /*When we don't find the "delimiter" (|) we need to check if the
                        first character of the word coincides with the first character
                        of my string "s2", in that case, we will insert the word with the
                        verbal time that we saw in the document*/
                        if (s2.charAt(i) != '|') {
                            /*We keep the complete noun*/
                            if (s2.charAt(i) == '-') {
                                uncountable = true;
                            } else {
                                s += s2.charAt(i);
                            }
                        } else if (s2.charAt(i) == '|'){
                            s += " ";
                            i = i + 1;
                            s += s2.charAt(i);
                        } 

                    } else if (s2.charAt(i) == '}') {
                        s += " ";
                        
                        break;
                    }
                }
                 
                length = s.length();

                for (int k = 0; k < length; k++) {
                    if (s.charAt(k) == ' ') {
                        if (k > 1) {
                            if ((s.charAt(k-1) == 'r') || (s.charAt(k-1) == 'e')) {
                                s += (" -- comparative");
                            } else if (s.charAt(k-1) == 't') {
                                s += (" -- superlative");
                            }
                        }
                        if (uncountable == true) {
                            s += (" - This adjective is uncountable");
                        }
                    }
                }
                //db.insertAdjective(s);
                out.write(s + "\n");
                db.setAdj(s);

            /*If we don't find the character "|", it means there isn't any noun for this word*/
            } else {
                //db.insertAdjective("There is only one kind of adjective");
                out.write("There is only one kind of adjective \n");
                db.setAdj("There is only one kind of adjective");
            }            
        }
    }
    
    public void adverb(String line, String word) throws IOException {
         out.write("\n\n");
         s = "";
         
        int kind = 0;
        int separator = 0;
        
        int length = 0;
        
        boolean uncountable = false;

         /*With "StringTokenizer" we can read the line word by word*/
         st = new StringTokenizer(line);
                 
         //This loop will help us to indetify the meaning of the nouns
         while (st.hasMoreTokens()) {
            /*we take the line*/
            s2 = st.nextToken();
                    
            //This loop will help us to indetify the meaning of the nouns
            for(int i=0;i<s2.length();i++){
                /*If we find the character "|",....*/
                if (s2.charAt(i) == '|') {
                    /*we will take the next character and we will go out 
                    of the loop*/
                    j = i+1;
                    b = true;
                    break;
                /*In the other case, we will go on our search*/
                } else {
                    b = false;
                }
            }
            
            /*We create the head "Noun" in our file ("finalContent")*/
            out.write("  -- Adverb --  \n");
            out.write("    ");
            
            /*When we find our limiting character ("|")...*/
            if (b == true) {
                /*We go on our searching in tha previous line since where we were*/
                for(int i=j;i<s2.length();i++) {
                    if (s2.charAt(i) != '}') {
                        /*When we don't find the "delimiter" (|) we need to check if the
                        first character of the word coincides with the first character
                        of my string "s2", in that case, we will insert the word with the
                        verbal time that we saw in the document*/
                        if (s2.charAt(i) != '|') {
                            /*We keep the complete noun*/
                            if (s2.charAt(i) == '-') {
                                uncountable = true;
                            } else {
                                s += s2.charAt(i);
                            }
                        } else if (s2.charAt(i) == '|'){
                            s += " ";
                            i = i + 1;
                            s += s2.charAt(i);
                        } 

                    } else if (s2.charAt(i) == '}') {
                        s += " ";
                        
                        break;
                    }
                }
                 
                length = s.length();

                for (int k = 0; k < length; k++) {
                    if (s.charAt(k) == ' ') {
                        if (k > 1) {
                            if ((s.charAt(k-1) == 'r') || (s.charAt(k-1) == 'e')) {
                                s += (" -- comparative");
                            } else if (s.charAt(k-1) == 't') {
                                s += (" -- superlative");
                            }
                        }
                        if (uncountable == true) {
                            s += (" - This adverb is uncountable");
                        }
                    }
                }
                //db.insertAdverb(s);
                out.write(s + "\n");
                db.setAdv(s);

            /*If we don't find the character "|", it means there isn't any noun for this word*/
            } else {
                out.write("There is only one kind of adverb \n");
                db.setAdv(s);
            }            
        }
    }
    
    public void readingFle() throws IOException, ServletException, SQLException {
       
        try {
            /*Opening the file and creating BufferedReader to be able to make a lecture
            of the file through the method (.readLine())*/
            information = new File ("information.txt");
            fr = new FileReader (information);
            br = new BufferedReader(fr);
         
            //It's just an object 
            file = new File("finalContent.txt"); 
            //We create the previous file with a FileWriter's file
            out = new FileWriter(file);   
            

            //Through this String, we can read the "Information" file
            String line;
            String word = null;            
                       
            /*With this loop we will be able to read all the file, line by line*/
            while((line=br.readLine())!=null) {
               
                /*We distinguish the tag "title" to take the word that we are searching*/
                if (line.contains("<title>")) {
                    /*We initialize the booleans to true, because at the begining we need to check 
                    if the word that we are studying has a noun, verb, adjective or adverb*/
                    oneNoun = true;
                    oneVerb = true;
                    oneAdjective = true;
                    oneAdverb = true;
                    word = br.readLine();

                    /*We print the word inside of the database*/
                    out.write("\n\n\n WORD--> " + word); 
                    db.setWord(word);
                }
                
                
                if (oneNoun == true) {
                    /*We check if the word has a noun*/
                    if (line.contains("{{en-noun")) {
                        noun(line, word);
                        oneNoun = false;
                    /*With this "else", we can avoid to add information in the words aren't 
                    english words*/
                    } else {
                        db.setNoun("");
                    }
                } 
                
                if (oneVerb == true) {
                    /*We check if the word has a verb*/
                    if (line.contains("{{en-verb")) {
                        verb(line, word);
                        oneVerb = false;
                    /*With this "else", we can avoid to add information in the words aren't 
                    english words*/
                    } else {
                        db.setVerb("");
                    }
                }
                
                if (oneAdjective == true) {
                    /*We check if the word has an adjective*/
                    if (line.contains("{{en-adj")) {
                        adjective(line, word);
                        oneAdjective = false;
                    /*With this "else", we can avoid to add information in the words aren't 
                    english words*/
                    } else {
                        db.setAdj("");
                    }
                }
                
                if (oneAdverb == true) {
                    /*We check if the word has an adverb*/
                    if (line.contains("{{en-adv")) {
                        adverb(line, word);
                        oneAdverb = false;
                    /*With this "else", we can avoid to add information in the words aren't 
                    english words*/
                    } else {    
                        db.setAdv("");
                    }
                }
                
                //This way we can print all the information related with noun, verb, adjective and
                //adverb of the word we are checking
                if (line.contains("</text>")) {
                    db.insertWord();
                }
                
            }
            
        } finally {
            /* Here we close the file, this way we can be sure it closes if everything is right 
            or something is worng */
            if (fr != null) {
                fr.close();  
            }  
          
            if (out != null) {
                out.close();  
            }
        }
      
    }
    
    /*We close the connection with the database*/
    public void destroy() {
        db.closeConnection();
        super.destroy();
    }
    
}
