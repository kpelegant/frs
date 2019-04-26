package com.kp.util.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;


/** 
 * 自定义图片按钮 
 * @author Lifeng-Leven 
 * 
 */  
public class ImageButton extends Widget {  
    private Image normalImage;  
    private Image mouseOverImage;  
    private Image mouseDownImage;  
    private Image disabledImage;  
    
    private ImageData normalImagedata;  
    private ImageData mouseOverImagedata;  
    private ImageData mouseDownImagedata;  
    private ImageData disabledImagedata;  
    //默认鼠标样式  
    private Cursor normalCursor;  
    private Cursor handCursor = new Cursor(null, SWT.CURSOR_HAND);  
      
    private Label label;
    private Boolean firstSetBounds = true;
    /** 
     * 按钮是否可用 
     */  
    private boolean isDisabled = false;  
      
    private List<Listener> listeners = new ArrayList<Listener>();  
    /** 
     * 自定义带图片的按钮控件 
     * @param parent 
     * @param normalImage 
     * @param mouseOverImage 
     * @param mouseDownImage 
     */  
    public ImageButton(Composite parent,String normalImage,String mouseOverImage,String mouseDownImage) {  
        this(parent,normalImage,mouseOverImage,mouseDownImage,normalImage);  
    }
    
    /**只显示一张背景图*/
    public ImageButton(Composite parent,String normalImage) {  
        this(parent,normalImage,null,null,null);
    }
    
    /** 
     * 自定义带图片的按钮控件 
     * @param parent 
     * @param normalImage 普通按钮背景图 
     * @param mouseOverImage 鼠标悬浮按钮背景图 
     * @param mouseDownImage 鼠标点击按钮背景图 
     * @param disabledImage 按钮失效背景图 
     */  
    public ImageButton(final Composite parent,String normalImage,String mouseOverImage,String mouseDownImage,String disabledImage) {  
        super(parent, SWT.NONE);
        checkSubclass();
    	label = new Label(parent, SWT.NONE);
    	//一般情况下显示的图片
        this.normalImagedata = new ImageData(normalImage);
        //移动到按钮上显示的图片
        if(mouseOverImage == null){
        	this.mouseOverImagedata = null;
        }else{
        	this.mouseOverImagedata = new ImageData(mouseOverImage);
        }
        //在按钮上按下显示的图片
        if(mouseDownImage == null){
        	this.mouseDownImagedata = null;
        }else{
        	this.mouseDownImagedata = new ImageData(mouseDownImage);
        }
        //按钮不可操作时显示的图片
        if(disabledImage == null){
        	this.disabledImagedata = null;
        }else{
        	this.disabledImagedata = new ImageData(disabledImage);
        }
        normalCursor = parent.getCursor();
        label.addListener(SWT.MouseEnter, new Listener() {  
            @Override
			public void handleEvent(Event event) {  
                if (!isDisabled && ImageButton.this.mouseOverImage != null) {  
                    label.setImage(ImageButton.this.mouseOverImage);  
                    parent.getShell().setCursor(handCursor);  
                }  
            }  
        });  
        label.addListener(SWT.MouseExit, new Listener() {  
            @Override
			public void handleEvent(Event event) {  
                if (!isDisabled && ImageButton.this.normalImage != null) {  
                    label.setImage(ImageButton.this.normalImage);  
                    parent.getShell().setCursor(normalCursor);  
                }  
            }  
        });  
        label.addListener(SWT.MouseDown, new Listener() {  
            @Override
			public void handleEvent(Event event) {  
                if (!isDisabled && ImageButton.this.mouseDownImage != null) {  
                    label.setImage(ImageButton.this.mouseDownImage);  
                }  
            }  
        });  
        label.addListener(SWT.MouseUp, new Listener() {  
            @Override
			public void handleEvent(Event event) {  
                if(!isDisabled){  
                    if(event.widget.equals(label)){  
                        if (ImageButton.this.mouseOverImage != null) {  
                            label.setImage(ImageButton.this.mouseOverImage);  
                        }  
                    }  
                    for(Listener listener : listeners){  
                        listener.handleEvent(event);  
                    }  
                }  
            }  
        });  
    }
    
	/**默认不检测*/
	@Override
	protected void checkSubclass(){
	}
	
    /**
     * 设置按钮的位置及大小
     * */
    public void setBounds(int arg0, int arg1, int arg2, int arg3){
    	label.setBounds(arg0, arg1, arg2, arg3);
    	//修改图片的大小
    	if(firstSetBounds){
	        this.normalImage = new Image(label.getDisplay(), normalImagedata.scaledTo(arg2, arg3));
	        if(mouseOverImagedata == null){
	        	this.mouseOverImage = null;
	        }else{
	        	this.mouseOverImage = new Image(label.getDisplay(), mouseOverImagedata.scaledTo(arg2, arg3));
	        }
	        if(mouseDownImagedata == null){
	        	this.mouseDownImage = null;
	        }else{
	        	this.mouseDownImage = new Image(label.getDisplay(), mouseDownImagedata.scaledTo(arg2, arg3));
	        }
	        if(disabledImagedata == null){
	        	this.disabledImage = null;
	        }else{
	        	this.disabledImage = new Image(label.getDisplay(), disabledImagedata.scaledTo(arg2, arg3));
	        }
	        label.setImage(this.normalImage);
	        firstSetBounds = false;
        }
    }
    
    /**
     * 设置提示信息
     */
    public void setToolTipText(String arg0){
    	label.setToolTipText(arg0);
    }
    
    /**设置背景颜色*/
    public void setBackground(Color arg0){
    	label.setBackground(arg0);
    }
    
    /** 
     * 设置按钮是否可用状态 
     * @param isDisabled 
     */  
    public void setDisabled(boolean isDisabled){  
        this.isDisabled = isDisabled;  
        if(isDisabled){  
            if(disabledImage!=null){  
                label.setImage(disabledImage);  
            }  
        }  
    }  
    /** 
     * 处理无用资源 
     */  
    @Override
	public void dispose(){  
        ViewUtil.dispose(normalImage, mouseOverImage, mouseDownImage, disabledImage);
    }
    
    /** 
     * 添加动作事件 
     * @param listener 
     */ 
    public void addClickListener(Listener arg0){  
        listeners.add(arg0);  
    } 
	
    /** 
     * 移除动作事件 
     * @param listener 
     */  
    public void removeClickListener(Listener listener){  
        listeners.remove(listener);  
    } 
  
    public void setLayoutData(Object btnData) {  
        if(label!=null && !label.isDisposed()){  
            label.setLayoutData(btnData);  
        }  
    }  
    public Shell getShell() {  
        if(label!=null && !label.isDisposed()){  
            return label.getShell();  
        }  
        return null;  
    }  
    
    /**设置ImageButton是否显示*/
    public void setVisible(Boolean show){
    	label.setVisible(show);
    }
    
    /**获取控件当前位置*/
    public Point getLocation(){
    	return label.getLocation();
    }
} 
