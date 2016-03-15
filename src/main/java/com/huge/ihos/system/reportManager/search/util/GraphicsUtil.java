package com.huge.ihos.system.reportManager.search.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

public final class GraphicsUtil
    extends JPanel {
    //x零点
    public static int LX = 50;

    //y零点
    public static int LY = 500;

    //x极点
    public static int JX = 500;

    //y极点
    public static int JY = 50;

    //经计算得到的值
    private static double[] jiSuan;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void paintComponent( double k1, double k2, double chengB,int baoBenD,double baoBSR, String fileName, String title, String[] xyName, String[] biaoZhu ) {
        jiSuan = jiaoDian( k1, k2, chengB );
        BufferedImage image = new BufferedImage( 900, 600, BufferedImage.TYPE_INT_ARGB );
        Graphics g = image.getGraphics();
        ( (Graphics2D) g ).setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        Font font = new Font( FontUtil.GetSystemFontOne(), Font.BOLD, 12 );
        g.setFont( font );
        int ljx = JX + 300;
        /* 标注 */
        g.setColor( Color.BLACK );
        //字体位置
        int zx = ( ljx / 4 ) + 10;
        int zy = LY + 60;
        g.drawString( biaoZhu[2], zx, zy );
        g.drawString( biaoZhu[1], 3 * zx - 15, zy );
        g.drawString( biaoZhu[0], 2 * zx, zy );

        //实际焦点
        int shiX = (int) ( jiSuan[0]);
        int shiY = (int) ( jiSuan[1]);

        //长方形(标注外框)
        int x1 = ( ljx / 4 ) - 20;
        int x2 = ljx / 2 + 300;
        int y1 = LY + 45;
        int y2 = LY + 65;
        g.drawLine( x1, y1, x1, y2 );//左
        g.drawLine( x1, y1, x2, y1 );//上
        g.drawLine( x2, y1, x2, y2 );//右
        g.drawLine( x1, y2, x2, y2 );//下

        //焦点
        double jiaoX = shiX;
        double jiaoY = shiY;

        double chengBen = jiSuan[4];//成本
        double maxLJY = jiSuan[2] + 0;//极值

        double maxMJY = jiSuan[3] + 0;
        /*float timesX = 0;
        float timesY = 0;*/
        
        /*if(jiaoY>250){
        	float zhi=jiaoY-230;
        	jiaoY=jiaoY-zhi;
        	maxLJY=maxLJY-zhi;
        	maxMJY
        	jiaoX=jiaoY/k1;
        }*/
        while ( Math.abs( jiaoY ) >= 230 || Math.abs( chengBen ) >= 250 || Math.abs( maxLJY ) >= 430 || Math.abs( maxMJY ) >= 430
            || Math.abs( jiaoX ) >= 330 || ( maxMJY >= ( LY - chengBen ) ) ) {
            if ( Math.abs( jiaoX ) >= 230 ) {
            	double compareValueX = ( (double) jiaoX ) / 450;
                if ( compareValueX <= 1.0 )
                    compareValueX = 1.2f;
                jiaoX = Math.abs( jiaoX / compareValueX );
                //timesX++;
            }
            if ( Math.abs( jiaoY ) >= 230 || Math.abs( chengBen ) >= 250 || Math.abs( maxLJY ) >= 430 || Math.abs( maxMJY ) >= 430
                || ( maxMJY >= ( LY - chengBen ) ) ) {
            	double compareValueY = (double) jiaoY / 450;
                if ( compareValueY <= 1.0 )
                    compareValueY = 1.2f;
                jiaoY = Math.abs( jiaoY / compareValueY );
                chengBen = Math.abs( chengBen / compareValueY );
                maxLJY = Math.abs( ( (double) maxLJY ) / compareValueY );
                maxMJY = Math.abs( ( (double) maxMJY ) / compareValueY );
               // timesY++;
            }
        }

        /* 画参考线 */
		int cx=LX;int cy=LY;int cjy=JY;//int canH=shiY/9;
		Stroke xuXian = new BasicStroke(1f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,
				3.5f,new float[]{5,5,},0f);
				((Graphics2D) g).setStroke(xuXian);
				g.setColor(Color.decode("#C3C3C3"));	
		//横向参考线
		int j=1;
		for(int i=500;i>50;i=i-50){
			g.drawLine(cx,i,ljx,i);
			//g.drawString(canH*j+"", cx-20, i);
			j++;
		}
		//纵向参考线
		for(int i=50;i<500;i=i+50){
			g.drawLine(i,cy,i,cjy);
		}
		((Graphics2D) g).setStroke(new   BasicStroke(0.7f)); 

        /* 物理焦点 */
        jiaoX =
            Math.abs( ( Math.abs( ( LX - JX ) ) * Math.abs( ( LX * maxMJY - Math.abs( JX * ( Math.abs( (int) ( LY - chengBen ) ) ) ) ) ) - Math.abs( ( LX - JX ) )
                * Math.abs( ( LX * maxLJY - JX * LY ) ) )
                / ( ( Math.abs( ( LX - JX ) ) * Math.abs( ( LY - maxLJY ) ) ) - Math.abs( ( LX - JX ) )
                    * Math.abs( ( ( (int) ( LY - chengBen ) ) - maxMJY ) ) ) );
        jiaoY =
            Math.abs( ( ( LY - maxLJY ) * ( LX * maxMJY - JX * ( (int) ( LY - chengBen ) ) ) - ( LX * maxLJY - JX * LY )
                * ( ( (int) ( LY - chengBen ) ) - maxMJY ) )
                / ( ( LY - maxLJY ) * ( LX - JX ) - ( LX - JX ) * ( ( (int) ( LY - chengBen ) ) - maxMJY ) ) );

        if ( jiaoX > JX || jiaoY > LY ) {
            jiaoX = JX;
            maxMJY = maxLJY>maxMJY?maxMJY:maxLJY;
            jiaoY = maxMJY;
            double yanChuX = jiaoX + 100;

            //收入
            g.setColor( Color.decode( "#0000FF" ) );
            //k1 = jiaoY / jiaoX;
            int YR = (int) (yanChuX * ( jiaoY / jiaoX ) - ( 2.2 * JY ) );
            
            k2 = Math.abs( ( jiaoY - chengBen ) / jiaoX );
            int YB = (int) ( ( yanChuX * ( ( jiaoY - chengBen ) / jiaoX )+chengBen )-(2.2*JY) );
            
            if (YB<YR){
            	int temp=YB;
            	YB=YR;
            	YR=temp;
            }
          

            g.drawLine( (int) jiaoX, (int) jiaoY, (int) ( yanChuX ), (int) YR );
            g.drawLine( LX, LY, (int) jiaoX, (int) jiaoY );

            ( (Graphics2D) g ).setStroke( new BasicStroke( 10.0f ) );
            g.drawLine( ( 3 * zx ) - 30, zy - 5, 3 * zx - 35, zy - 5 );
            ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );

            //成本
            g.setColor( Color.decode( "#660000" ) );
            
            g.drawLine( (int) jiaoX, (int) jiaoY, (int) yanChuX, YB );
            g.drawLine( LX, (int) ( LY - chengBen ), (int) jiaoX, (int) jiaoY );

            ( (Graphics2D) g ).setStroke( new BasicStroke( 10.0f ) );
            g.drawLine( ( 2 * zx ) - 20, zy - 5, ( 2 * zx ) - 15, zy - 5 );
            ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );

        }
        else {
            //收入
            g.setColor( Color.decode( "#0000FF" ) );
            g.drawLine( LX, LY, JX, (int) maxLJY );

            ( (Graphics2D) g ).setStroke( new BasicStroke( 10.0f ) );
            g.drawLine( ( 3 * zx ) - 30, zy - 5, 3 * zx - 35, zy - 5 );
            ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );

            //成本
            g.setColor( Color.decode( "#660000" ) );
            g.drawLine( LX, (int) ( LY - chengBen ), JX, (int) maxMJY );

            ( (Graphics2D) g ).setStroke( new BasicStroke( 10.0f ) );
            g.drawLine( ( 2 * zx ) - 20, zy - 5, ( 2 * zx ) - 15, zy - 5 );
            ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );

        }

        //固定成本线
        g.setColor( Color.decode( "#000066" ) );
        g.drawLine( LX, (int) ( LY - chengBen ), JX + 100, (int) ( LY - chengBen ) );

        ( (Graphics2D) g ).setStroke( new BasicStroke( 10.0f ) );
        g.drawLine( zx - 20, zy - 5, zx - 15, zy - 5 );
        ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );

        //显示成本Y坐标
        //成本实际值
        int chengShi = (int) ( jiSuan[4] + 0 );
        g.drawString( chengShi + "", LX - 30, (int) ( LY - chengBen ) );

        //保本点
        g.setColor( Color.decode( "#336638" ) );
        ( (Graphics2D) g ).setStroke( new BasicStroke( 0.7f ) );
        int dianX = (int) ( jiaoX + 5 );
        int dianY = (int) ( jiaoY + 5 );
        g.drawLine( dianX, dianY, dianX + 5, dianY );
        g.drawLine( dianX, dianY, dianX, dianY + 5 );
        g.drawLine( dianX, dianY, dianX + 50, dianY + 50 );

        //显示焦点坐标
        g.drawString( "保本点", dianX + 50, dianY + 70 );
        g.drawString( "(" + baoBenD + "," + baoBSR + ")", dianX + 50, dianY + 85 );

        //显示焦点Y坐标
        g.drawString( baoBSR + "", LX - 30, (int) jiaoY );
        //显示焦点X坐标
        g.drawString( baoBenD + "", (int) jiaoX, LY + 20 );

        Stroke dash = new BasicStroke( 1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[] { 5, 5, }, 0f );
        ( (Graphics2D) g ).setStroke( dash );
        //画竖参考线
        g.drawLine( (int) jiaoX, LY, (int) jiaoX, (int) jiaoY );
        //画横参考线
        g.drawLine( LX, (int) jiaoY, (int) jiaoX, (int) jiaoY );

        g.setColor( Color.RED );
        ( (Graphics2D) g ).setStroke( new BasicStroke( 1.0f ) );

        //x
        g.setColor( Color.decode( "#000000" ) );
        g.drawLine( ljx, LY, ljx - 5, LY - 5 );
        g.drawLine( LX, LY, ljx, LY );
        g.drawLine( ljx, LY, ljx - 5, LY + 5 );

        //y
        g.drawLine( LX, JY, LX - 5, JY + 5 );
        g.drawLine( LX, LY, LX, JY );
        g.drawLine( LX, JY, LX + 5, JY + 5 );

        g.drawString( "y" + xyName[1], LX - 20, JY - 10 );
        g.drawString( "x" + xyName[0], ljx - 30, LY + 20 );
        g.setColor( Color.DARK_GRAY );
        Font font1 = new Font( "黑体", Font.BOLD, 16 );
        g.setFont( font1 );
        g.drawString( title, ljx / 3, JY - 10 );
        try {
            javax.imageio.ImageIO.write( image.getSubimage( 0, 0, 900, 600 ), "PNG", new File( fileName ) );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        g.dispose();
        LX = 50;
        LY = 500;
        JX = 500;
        JY = 50;

    }

    /**
     * 计算毛利线、利润线焦点、毛利和利润线的另一点并且返回成本
     * @param k1 成本线斜率
     * @param k2 利润线斜率
     * @param chengBen 成本
     * @return
     */
    public static double[] jiaoDian( double k1, double k2, double chengBen ) {
        double x = chengBen / ( k1 - k2 );
        double y = x * k1;
        double maxLJY = LY - ( k1 * ( JX - LX ) );//利润线最大值(Y)
        double maxMJY = ( LY - chengBen ) - k2 * ( JX - LX );//毛利线最大值(Y)
        double[] point = new double[5];
        /*存焦点*/
        point[0] = x;
        point[1] = y;
        /*存极值*/
        point[2] = maxLJY;
        point[3] = maxMJY;
        /*存成本*/
        point[4] = chengBen;
        return point;
    }
}
