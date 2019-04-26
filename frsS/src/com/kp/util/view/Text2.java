package com.kp.util.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * 重写Text控件
 * @author Mr Wang
 *
 */
public class Text2 {
	
	private ViewForm view;
	private Text text;
	
	private Color viewBG;
	private RGB rgb;

	public Text2(Composite arg0, int arg1, RGB rgb) {
		this.rgb = rgb;
		viewBG = new Color(arg0.getDisplay(), 255, 255, 255);
		view = new ViewForm(arg0, SWT.NONE);
		view.setBackground(viewBG);
		view.addPaintListener(viewPaintListener);
		text = new Text(view, arg1);
	}
	
	public void setBounds(int x, int y, int w, int h){
		view.setBounds(x, y, w, h);
		text.setBounds(3, (h-17)/2, w-6, 17);
	}
	
	public void setToolTipText(String arg0){
		text.setToolTipText(arg0);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public String getText(){
		return text.getText();
	}
	
	public void addListener(int arg0, Listener arg1){
		text.addListener(arg0, arg1);
	}
	
	public void setVisible(boolean arg0){
		view.setVisible(arg0);
	}
	
	public void addTraverseListener(TraverseListener arg0){
		text.addTraverseListener(arg0);
	}
	
	public void forceFocus(){
		text.forceFocus();
	}
	
	public void dispose(){
		ViewUtil.dispose(viewBG);
	}
	
	/**
	 * 重绘TextView边框
	 */
	PaintListener viewPaintListener = new PaintListener() {
		@Override
		public void paintControl(PaintEvent arg0) {
			GC gc_container_1 = new GC(view);  
			Color color = new Color(Display.getDefault(), rgb);
			gc_container_1.setForeground(color); 
            gc_container_1.setLineWidth(1);  
            gc_container_1.drawRoundRectangle(0, 0, view.getSize().x-1, view.getSize().y-1, 3, 3);  
            gc_container_1.dispose();
            color.dispose();
		}
	};

}
