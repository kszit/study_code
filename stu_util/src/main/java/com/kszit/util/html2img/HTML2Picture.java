package com.kszit.util.html2img;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicEditorPaneUI;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * HTML转换图片的方式
 *
 * 
 */
public class HTML2Picture {

    public static int DEFAULT_IMAGE_WIDTH = 730;
    public static int DEFAULT_IMAGE_HEIGHT = 700;

    public static boolean paintPage(Graphics g, int hPage, int pageIndex,
            JTextPane panel) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = ((BasicEditorPaneUI) panel.getUI())
                .getPreferredSize(panel);
        double panelHeight = d.height;
        double pageHeight = hPage;
        int totalNumPages = (int) Math.ceil(panelHeight / pageHeight);
        g2.translate(0f, -(pageIndex - 1) * pageHeight);
        panel.paint(g2);
        boolean ret = true;

        if (pageIndex >= totalNumPages) {
            ret = false;
            return ret;
        }
        return ret;
    }

    /**
     * html转换为ｊｐｅｇ文件
     * 
     * @param bgColor
     *            图片的背景色
     * @param html
     *            html的文本信息
     * @param width
     *            显示图片的Ｔｅｘｔ容器的宽度
     * @param height
     *            显示图片的Ｔｅｘｔ容器的高度
     * @param eb
     *            設置容器的边框
     * @return
     * @throws Exception
     */
    private static void html2jpeg(Color bgColor, String html, int width,
            int height, EmptyBorder eb) throws Exception {

        JTextPane tp = new JTextPane();
        tp.setSize(width, height);
        if (eb == null) {
            eb = new EmptyBorder(0, 50, 0, 50);
        }
        if (bgColor != null) {
            tp.setBackground(bgColor);
        }
        if (width <= 0) {
            width = DEFAULT_IMAGE_WIDTH;
        }
        if (height <= 0) {
            height = DEFAULT_IMAGE_HEIGHT;
        }
        tp.setBorder(eb);
        tp.setContentType("text/html");
        tp.setText(html);

        int pageIndex = 1;
        boolean bcontinue = true;
        while (bcontinue) {
            BufferedImage image = new java.awt.image.BufferedImage(width,
                    height, java.awt.image.BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width, height);
            bcontinue = paintPage(g, height, pageIndex, tp);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(image);
            byte[] bytes = baos.toByteArray();
            baos.close();

          //  FileUtil.writeBinFile("C:\123.jpg", bytes);
            
            pageIndex++;
        }
    }

    public static void main(String[] args) throws Exception {
       
        System.out.println("over!");

    }
}