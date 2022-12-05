import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Robot{
    protected int x, y, d;
    Robot()
    {
        x = 0; y = 0; d = 0;
    }
    
    public void checkBounds()
    {
        if(x<0 || y<0 || x>4 || y>4)
        {
            System.out.println("Robot out of grid.");
            System.exit(0);
        }
    }
    
    public void F()
    {
         switch(d)
        {
            case 0:
                y++;
                break;
            case 1:
                x++;
                break;
            case 2:
                y--;
                break;
            case 3:
                x--;
                break;
        }
        
        checkBounds();
    }
    
    public void B()
    {
         switch(d)
        {
            case 0:
                y--;
                break;
            case 1:
                x--;
                break;
            case 2:
                y++;
                break;
            case 3:
                x++;
                break;
        }
        checkBounds();
    }
        
    public void L()
    {
         switch(d)
        {
            case 0:
                x--;
                break;
            case 1:
                y++;
                break;
            case 2:
                x++;
                break;
            case 3:
                y--;
                break;
        }
        checkBounds();
    }
    
    public void R()
    {
         switch(d)
        {
            case 0:
                x++;
                break;
            case 1:
                y--;
                break;
            case 2:
                x--;
                break;
            case 3:
                y++;
                break;
        }
        checkBounds();
    }
    
    public void RoL()
    {
        d = (d+3)%4;
    }
    
    public void RoR()
    {
        d = (d+1)%4;
    }
    
    public void get()
    {
        String dir;
        switch(d)
        {
            case 0:
                dir = "E";
                break;
            case 1:
                dir = "S";
                break;
            case 2:
                dir = "W";
                break;
            case 3:
                dir = "N";
                break;
            default:
                dir = "Invalid";
                break;
        }
        System.out.println("("+x+","+y+") "+dir);
    }
}

class SwerveDrive extends Robot{
    
    public void Actions(char c)
    {
        switch(c)
        {
            case 'F':
                F();break;
            case 'B':
                B();break;
            case 'L':
                L();break;
            case 'R':
                R();break;
            default:
                System.out.println("Invalid");
        }
        
        get();
    }
    
    public void Inputs()
    {
        try{
            File text = new File("JoyStick.txt");
            Scanner scnr = new Scanner(text);
            get();
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                if(line.length()==1)
                Actions(line.charAt(0));
                else
                System.out.println("Invalid String");
            } 
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found");
        }
    }
    
}

class ArcadeDrive extends SwerveDrive{
    
    public void L()
    {
        super.L();
        F();
        RoL();
    }
    
    public void R()
    {
        super.R();
        F();
        RoR();
    }
    
    public void Left()
    {
        super.L();
    }
    
    public void Right()
    {
        super.R();
    }
}

class TankDrive extends ArcadeDrive{
    public void Actions(String s)
    {
        if(s.equals("F, F"))
        {
            F();
        }
        else if(s.equals("B, B"))
        {
            B();
        }
        else if(s.equals("F, B"))
        {
            RoR();
        }
        else if(s.equals("B, F"))
        {
            RoL();
        }
        get();
    }
    
    public void Inputs()
    {
        try{
            File text = new File("TwoJoystick.txt");
            Scanner scnr = new Scanner(text);
            get();
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                if(line.length()==4)
                Actions(line);
                else
                System.out.println("Invalid String");
            } 
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found");
        }
       
    }
    
}

class OmniDrive extends TankDrive{

    public void Actions(String s)
    {
        if(s.equals("F, F"))
        {
            F();
        }
        else if(s.equals("B, B"))
        {
            B();
        }
        else if(s.equals("F, B"))
        {
            RoR();
        }
        else if(s.equals("B, F"))
        {
            RoL();
        }
        else if(s.equals("L, L"))
        {
            Left();
        }
        else if(s.equals("R, R"))
        {
            Right();
        }
        else if(s.charAt(0) == 'F' && s.length()==4)
        {
            R();
        }
        else if(s.charAt(3) == 'F')
        {
            L();
        }
        get();
    }
}
    
class Main {
    public static void main(String[] args) {
        ArcadeDrive R = new ArcadeDrive();
        R.Inputs();
    }
}
