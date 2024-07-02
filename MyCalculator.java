import java.awt.*;  
import java.awt.event.*;  
   
public class MyCalculator extends Frame  
{    
public boolean set_clear=true;  
double num, memValue;  
char op;  
  
String digitButtonText[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+/-", "." };  
String operatorButtonText[] = {"/", "sqrt", "*", "%", "-", "1/X", "+", "=" };  
String specialButtonText[] = {"Backspace", "C", "CE" };  
  
MyDigitButton digitButton[]=new MyDigitButton[digitButtonText.length];  
MyOperatorButton operatorButton[]=new MyOperatorButton[operatorButtonText.length];   
MySpecialButton specialButton[]=new MySpecialButton[specialButtonText.length];  
  
Label displayLabel=new Label("0",Label.RIGHT);  
Label memLabel=new Label(" ",Label.RIGHT);  
  
final int FRAME_WIDTH=325,FRAME_HEIGHT=325;  
final int HEIGHT=30, WIDTH=30, H_SPACE=10,V_SPACE=10;  
final int TOPX=30, TOPY=50;  
 
MyCalculator(String frameText) 
{  
super(frameText);  
  
int tempX=TOPX, y=TOPY;  
displayLabel.setBounds(tempX,y,240,HEIGHT);  
displayLabel.setBackground(Color.BLACK);  
displayLabel.setForeground(Color.pink);  
add(displayLabel);  
  
memLabel.setBounds(TOPX,  TOPY+HEIGHT+ V_SPACE,WIDTH, HEIGHT);  
add(memLabel);  
  
tempX=TOPX+1*(WIDTH+H_SPACE);
 y=TOPY+1*(HEIGHT+V_SPACE);  
for(int i=0;i<specialButton.length;i++)  
{  
specialButton[i]=new MySpecialButton(tempX,y,WIDTH*2,HEIGHT,specialButtonText[i], this);  
specialButton[i].setForeground(Color.pink);  
tempX=tempX+2*WIDTH+H_SPACE; 
}  
   
int digitX=TOPX+WIDTH+H_SPACE;  
int digitY=TOPY+2*(HEIGHT+V_SPACE);  
tempX=digitX;  y=digitY;  
for(int i=0;i<digitButton.length;i++)  
{  
digitButton[i]=new MyDigitButton(tempX,y,WIDTH,HEIGHT,digitButtonText[i], this);  
digitButton[i].setForeground(Color.black);  
tempX+=WIDTH+H_SPACE;  
if((i+1)%3==0){tempX=digitX; y+=HEIGHT+V_SPACE;}  
}  
  
 
int opsX=digitX+2*(WIDTH+H_SPACE)+H_SPACE;  
int opsY=digitY;  
tempX=opsX;  y=opsY;  
for(int i=0;i<operatorButton.length;i++)  
{  
tempX+=WIDTH+H_SPACE;  
operatorButton[i]=new MyOperatorButton(tempX,y,WIDTH,HEIGHT,operatorButtonText[i], this);  
operatorButton[i].setForeground(Color.black);  
if((i+1)%2==0){tempX=opsX; y+=HEIGHT+V_SPACE;}  
}  
  
addWindowListener(new WindowAdapter()  
{  
public void windowClosing(WindowEvent ev)  
{System.exit(0);}  
});  
  
setLayout(null);  
setSize(FRAME_WIDTH,FRAME_HEIGHT);  
setVisible(true);  
}  

static String getFormattedText(double temp)  
{  
String resText=""+temp;  
if(resText.lastIndexOf(".0")>0)  
    resText=resText.substring(0,resText.length()-2);  
return resText;  
}  

public static void main(String []args)  
{  
new MyCalculator("Tech-A-Intern Calculator");  
}  
}   
  
class MyDigitButton extends Button implements ActionListener  
{  
MyCalculator cl;  
  
  
MyDigitButton(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  
 
static boolean isInString(String s, char ch)  
{  
for(int i=0; i<s.length();i++) if(s.charAt(i)==ch) return true;  
return false;  
}   
public void actionPerformed(ActionEvent ev)  
{  
String tempText=((MyDigitButton)ev.getSource()).getLabel();  
  
if(tempText.equals("."))  
{  
 if(cl.set_clear)   
    {cl.displayLabel.setText("0.");cl.set_clear=false;}  
 else if(!isInString(cl.displayLabel.getText(),'.'))  
    cl.displayLabel.setText(cl.displayLabel.getText()+".");  
 return;  
}  
  
int index=0;  
try{  
        index=Integer.parseInt(tempText);  
    }catch(NumberFormatException e){return;}  
  
if (index==0 && cl.displayLabel.getText().equals("0")) return;  
  
if(cl.set_clear)  
            {cl.displayLabel.setText(""+index);cl.set_clear=false;}  
else  
    cl.displayLabel.setText(cl.displayLabel.getText()+index);  
}  
}  
   
class MyOperatorButton extends Button implements ActionListener  
{  
MyCalculator cl;  
  
MyOperatorButton(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  
  
public void actionPerformed(ActionEvent ev)  
{  
String opText=((MyOperatorButton)ev.getSource()).getLabel();  
  
cl.set_clear=true;  
double temp=Double.parseDouble(cl.displayLabel.getText());  
  
if(opText.equals("1/x"))  
    {  
    try  
        {double tempd=1/(double)temp;  
        cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));}  
    catch(ArithmeticException excp)  
                        {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(opText.equals("sqrt"))  
    {  
    try  
        {double tempd=Math.sqrt(temp);  
        cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));}  
            catch(ArithmeticException excp)  
                    {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(!opText.equals("="))  
    {  
    cl.num=temp;  
    cl.op=opText.charAt(0);  
    return;  
    }  

switch(cl.op)  
{  
case '+':  
    temp+=cl.num;
    break;  
case '-':  
    temp=cl.num-temp;
    break;  
case '*':  
    temp*=cl.num;
    break;  
case '%':  
    try{temp=cl.num%temp;}  
    catch(ArithmeticException excp)  
        {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
case '/':  
    try{temp=cl.num/temp;}  
        catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
} 
  
cl.displayLabel.setText(MyCalculator.getFormattedText(temp));  
} 
}  
   
class MySpecialButton extends Button implements ActionListener  
{
MyCalculator cal;  
  
MySpecialButton(int x,int y, int width,int height,String cap, MyCalculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cal=clc;  
this.cal.add(this);  
addActionListener(this);  
}  
 
static String backSpace(String s)  
{  
String Res="";  
for(int i=0; i<s.length()-1; i++)
 Res+=s.charAt(i);  
return Res;  
}  
  
public void actionPerformed(ActionEvent ev)  
{  
String opText=((MySpecialButton)ev.getSource()).getLabel();  
  
if(opText.equals("backspace"))  
{  
String tempText=backSpace(cal.displayLabel.getText());  
if(tempText.equals(""))   
    cal.displayLabel.setText("0");  
else   
    cal.displayLabel.setText(tempText);  
return;  
}  
  
if(opText.equals("C"))   
{  
cal.num=0.0; 
cal.op= ' ';
 cal.memValue=0.0;  
cal.memLabel.setText(" ");  
}
} 
}

