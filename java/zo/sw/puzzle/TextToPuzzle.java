package zo.sw.puzzle;

import android.content.Context;
import android.util.Log;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class TextToPuzzle {

    int height, width ;
    String solution;
    String path,title;


    TextToPuzzle(final Context context, String p)throws IOException
    {
        path = p;
        PuzzleHelper mydb=new PuzzleHelper(context);
        DataEncapsulator encapsulator;
        RandomAccessFile raf = new RandomAccessFile(path, "r");

        raf.seek(0x02);
        byte[] b = new byte[0xC];
        raf.readFully(b);
        //Version String(?)
        raf.seek(0x18);
        b = new byte[0x4];
        raf.readFully(b);
        //width
        raf.seek(0x2C);
        width = raf.readByte();
        //Height
        raf.seek(0x2D);
        height = raf.readByte();

        raf.seek(0x2E);
        int cells = width * height;
        int solutionStart = 0x34;
        int stateStart = solutionStart + cells;
        //Solution
        raf.seek(solutionStart);
        b = new byte[cells];
        raf.readFully(b);
        //solution=raf.readLine();
        solution = new String(b);
        //State
        raf.seek(stateStart);
        b = new byte[cells];
        raf.readFully(b);
        //Strings
        int stringStart = stateStart + cells;
        raf.seek(stringStart);
        String strings = raf.readLine();
        String[] str = strings.split("\0");
        title = str[0];

        //Clues
        String cluesStr[] = new String[str.length];
        System.arraycopy(str, 3, cluesStr, 0, str.length - 1 - 3);
        List<String> clueList=new ArrayList<>();
        for(String s:cluesStr)
        {
            if(s!=null&&s.length()>1)
            {
                clueList.add(s);
            }
        }
        cluesStr=clueList.toArray(new String[clueList.size()]);
        //Solution
        StringBuffer soln=new StringBuffer(solution);
        StringBuilder soln1=new StringBuilder(solution);

        //Down

        for(int pos=width;pos<=soln1.length();pos=pos+width+1) {
            soln1.insert(pos, "\n");
        }
        char charArray[]=new char[soln1.length() + (width * 2) + 1];
        for(int i=0;i<soln1.length()+width*2+1;i++)
        {
            if(i<width||i>=soln1.length()+width+1)
            {
                charArray[i]='.';
            }
            else if(i==width)
                charArray[i]='\n';
            else
            {
                charArray[i]=soln1.charAt(i-width-1);
            }
        }
        char downArray[]=new char[charArray.length];
        int ind=0;

        for(int i=width+1;i<charArray.length;i++)
        {
            int var=i;
            if(charArray[i]=='.')
            {
                continue;
            }
            if(charArray[var]!='\n'&&charArray[var-(width+1)]=='.'&&charArray[var+width+1]!='.')
            {
                while(charArray[var]!='.')
                {
                    downArray[ind]=charArray[var];
                    var=var+width+1;
                    ind++;
                }
                downArray[ind]='.';
                ind++;
            }
        }

        String down=new String(downArray);
        down=down.trim();
        String finalDownArray[]=down.split("[.]+");
        List<String> downlist=new ArrayList<>();
        for(String str1:finalDownArray)
        {
            if(str1!=null&&str1.length()>1)
            {
                downlist.add(str1);
            }
        }
        finalDownArray= downlist.toArray(new String[downlist.size()]);

        //Across

        for(int pos=width;pos<=soln.length();pos=pos+width+1)
            soln.insert(pos,".");
        String sol=new String(soln);
        String finalAcrossArray[]=sol.split("[.]+");
        List<String> acrosslist=new ArrayList<>();
        for(String answer:finalAcrossArray)
        {
            if(answer!=null&&answer.length()>0)
            {
                acrosslist.add(answer);
            }
        }
        finalAcrossArray= acrosslist.toArray(new String[acrosslist.size()]);
        //Across&Down
        String finalAns[]=new String[finalAcrossArray.length+finalDownArray.length];

        int index=1,t=1;
        finalAns[0]=finalAcrossArray[0];
        for(int i=width+1;i<charArray.length-2;i++)
        {
            int var=i;
            if(charArray[i]=='.'||charArray[i]=='\n') {
                if (charArray[i + 1] == '.' || charArray[i + 1] == '\n') {
                    continue;
                }
                else {
                    finalAns[index] = finalAcrossArray[t];
                    t++;
                    index++;
                }
            }
            else if(charArray[var]!='.'&&charArray[var]!='\n'&&charArray[var-(width+1)]=='.'&&charArray[var+width+1]!='.'&&charArray[var+width+width+2]!='.')
            {
                while(charArray[var]!='.')
                {
                    var=var+width+1;
                }
                finalAns[index]=null;
                index++;
            }
        }

        int x=0;
        for(int i=0;i<finalAns.length;i++)
        {
            if(finalAns[i]==null)
            {
                finalAns[i]=finalDownArray[x];
                x++;
            }
        }
        List<String> list=new ArrayList<>();
        for(String ans:finalAns)
        {
            if(ans!=null&&!ans.isEmpty()&&ans.length()>1)
            {
                list.add(ans);
            }
        }
        finalAns= list.toArray(new String[list.size()]);

        for (int i = 0; i < cluesStr.length; i++) {
            encapsulator = new DataEncapsulator(cluesStr[i], finalAns[i], finalAns[i].length());
            encapsulator.setCLUE(cluesStr[i]);
            encapsulator.setANSWER(finalAns[i]);
            encapsulator.setLENGTH(finalAns[i].length());
            if(i==0)
                mydb.deleteRecords();
            mydb.addPuz(encapsulator);
            Log.i("In TextToPuzzle","Added row");
       }
        raf.close();
    }

}
