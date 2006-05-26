/****************************************/
/*   ICaptionBar.java                   */
/*                                      */
/*   Copyright (c) Forhad Ahmed 2006    */
/*                                      */
/****************************************/

package gov.nih.nci.cagrid.graph.vstheme;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ICaptionBar extends JComponent
{
    protected String title = "";
    protected Color  focusColor = new Color(0, 64, 128);
    protected Color  noFocusColor = Color.gray;

    protected Point  lastClicked = new Point(0,0);

    protected boolean hasFocus = true;


    protected Vector buttons = new Vector();
    protected int    buttonsInset = 3;



    public ICaptionBar()
    {
        super();


        this.addComponentListener(new ICaptionBarComponentListener());
        this.addMouseListener(new ICaptionBarMouseListener());
        this.addMouseMotionListener(new ICaptionBarMouseMotionListener());


        this.addCaptionButton(new ICaptionCloseButton(this));



    }

    public IInternalFrame getIFrame()
    {
        IInternalFrame parent = (IInternalFrame) (this.getParent().getParent().getParent().getParent());

        return parent;
    }

    public void addCaptionButton(ICaptionButton b)
    {
        this.buttons.add(b);
        this.add(b);
    }

    public void setTitle(String title)
    {
        this.title = title;
        this.repaint();
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setFocusColor(Color c)
    {
        this.focusColor = c;
        this.repaint();
    }

    public Color getFocusColor()
    {
        return this.focusColor;
    }

    public void setNoFocusColor(Color c)
    {
        this.noFocusColor = c;
        this.repaint();
    }

    public Color getNoFocusColor()
    {
        return this.noFocusColor;
    }


    public void paint(Graphics g)
    {

        if(this.hasFocus == true)
        {
            g.setColor(this.focusColor);
        }
        else
        {
            g.setColor(this.noFocusColor);
        }

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.PLAIN, 11));
        g.drawString(this.title, 6, this.getHeight()-4);

        super.paint(g);
    }




}



class ICaptionBarComponentListener extends ComponentAdapter
{
    public void componentResized(ComponentEvent e)
    {
        ICaptionBar c = (ICaptionBar) e.getSource();

        for(int k = 0; k < c.buttons.size(); k++)
        {
            int x = c.getWidth() - (k+1) * (c.buttonsInset + (c.getHeight() - 2 * c.buttonsInset))-1;
            ICaptionButton b = (ICaptionButton) c.buttons.elementAt(k);
            b.setBounds(x, c.buttonsInset, c.getHeight() - 2 * c.buttonsInset, c.getHeight() - 2 * c.buttonsInset);
        }

        c.validate();
    }

}

class ICaptionBarMouseListener extends MouseAdapter
{
    public void mouseEntered(MouseEvent e)
    {
        ICaptionBar c = (ICaptionBar) e.getSource();
        c.setCursor(Cursor.getDefaultCursor());
    }
    public void mousePressed(MouseEvent e)
    {
        ICaptionBar c = (ICaptionBar) e.getSource();
        c.lastClicked = e.getPoint();

    }
}


class ICaptionBarMouseMotionListener extends MouseMotionAdapter
{
    public void mouseMoved(MouseEvent e)
    {


    }

    public void mouseDragged(MouseEvent e)
    {
        ICaptionBar c = (ICaptionBar) e.getSource();
        IInternalFrame s = c.getIFrame();
        //s.setLocation(s.getX() + e.getX() - c.lastClicked.x, s.getY() + e.getY() - c.lastClicked.y );

    }
}


