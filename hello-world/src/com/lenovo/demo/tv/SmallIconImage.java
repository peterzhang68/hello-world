package com.lenovo.demo.tv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.ContextWrapper;

public class SmallIconImage extends ImageView {
	
	public int miCurrentTransNum = 0;	
	public Animation [] SmallIconTranslat;
	// 定义8个展示文字(TextView)，上面
	public TextView DisplayTextView = null;
	public ImageView imageDisplayIcon = null;
	public ScaleAnimation scale_display = null;
	
	private int miIconMaxNum = TvProductManager.IconMaxNum;
	private int miAniamtMaxNum = miIconMaxNum+1;
	private int miIconNum = 0;
	private float fOneStepAway = 0.9391f;//216/230;
	private float fLeftSideAway = -2.0f;
	private float fTransBegin = 0;
	private float fTransAfter = 0;
	private long lStartOffset = 0;
	
	public SmallIconImage(Context context){
	    	super(context);
	    }	    
	    
	public SmallIconImage(Context context,AttributeSet paramAttributeSet){
	    	super(context,paramAttributeSet);
	    }  
	
	public void initSmallIconImage(int iIconNum){
		 miIconNum =  iIconNum; 
		initSmallIconAnimate();
	}
	

	private void initSmallIconAnimate(){
		//translat animate 
    	SmallIconTranslat = new Animation[miAniamtMaxNum];
    	LoadTranslatleft1();
    	LoadTranslatleft2();
    	LoadTranslatleft3();
    	LoadTranslatleft4();
    	LoadTranslatToleftSide();
    	LoadTranslatLeftsideStill();
    	LoadTranslatBack();
    	for(int i = 6;i < miAniamtMaxNum ; i++){
    		LoadTranslatRightsideStill(i);
    	}
        //scale animate 	
    	LoadScaleAnimate();
    }
	
    private void LoadScaleAnimate(){
    	scale_display = new ScaleAnimation(1.0f, 20f, 1.0f,
    			 20f, Animation.RELATIVE_TO_SELF, 0.5f,
    			    Animation.RELATIVE_TO_SELF, 0.5f);
    	scale_display.setDuration(500);
    	scale_display.setFillAfter(true);
    	scale_display.setStartOffset(0);
    }
    
	private void LoadTranslatleft1(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
			case 0:
				fTransBegin = fOneStepAway*4;
				fTransAfter = fOneStepAway*3;
				break;
			case 1:
				fTransBegin = fOneStepAway*3;
				fTransAfter = fOneStepAway*2;
				break;
			case 2:
				fTransBegin = fOneStepAway*2;
				fTransAfter = fOneStepAway*1;
				break;
			case 3:
				fTransBegin = fOneStepAway;
				fTransAfter = 0;
				break;
			case 4:
				fTransBegin = 0;
				fTransAfter = -(fOneStepAway);
				lStartOffset = 0;
				break;
			case 5:
			case 6:
			case 7:	
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				fTransBegin = 0;
				fTransAfter = -(fOneStepAway);
				break;		
			default:
				break;
		}
		SmallIconTranslat[0] = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fTransBegin,
				Animation.RELATIVE_TO_SELF, fTransAfter, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[0].setDuration(500);
		SmallIconTranslat[0].setFillAfter(true);
		SmallIconTranslat[0].setFillEnabled(true);
		SmallIconTranslat[0].setStartOffset(lStartOffset);// 现在是相对的偏移时间！
	}
	
	private void LoadTranslatleft2(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = fOneStepAway*3;
			fTransAfter = fOneStepAway*2;
			break;
		case 1:
			fTransBegin = fOneStepAway*2;
			fTransAfter = fOneStepAway;
			break;
		case 2:
			fTransBegin = fOneStepAway;
			fTransAfter = 0;
			break;
		case 3:
			fTransBegin = 0;
			fTransAfter = -(fOneStepAway);
			lStartOffset = 0;
			break;
		case 4:
			fTransBegin = -(fOneStepAway*1);
			fTransAfter = -(fOneStepAway*2);
			break;
		case 5:	
		case 6:			
		case 7:			
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = -(fOneStepAway*1);
			fTransAfter = -(fOneStepAway*2);
			break;
		default:
			break;
	    }
		SmallIconTranslat[1] = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fTransBegin,
				Animation.RELATIVE_TO_SELF, fTransAfter, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[1].setDuration(500);
		SmallIconTranslat[1].setFillAfter(true);
		SmallIconTranslat[1].setFillEnabled(true);
		SmallIconTranslat[1].setStartOffset(lStartOffset);// 现在是相对的偏移时间！
		
	}
	private void LoadTranslatleft3(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = fOneStepAway*2;
			fTransAfter = fOneStepAway;
			break;
		case 1:
			fTransBegin = fOneStepAway;
			fTransAfter = 0;
			break;
		case 2:
			fTransBegin = 0;
			fTransAfter = -fOneStepAway;
			lStartOffset = 0;
			break;
		case 3:
			fTransBegin = -fOneStepAway;
			fTransAfter = -(2*fOneStepAway);
			break;
		case 4:
			fTransBegin = -(2*fOneStepAway);
			fTransAfter = -(3*fOneStepAway);
			break;
		case 5:
		case 6:			
		case 7:			
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = -(2*fOneStepAway);
			fTransAfter = -(3*fOneStepAway);
			break;
		default:
			break;
	    }
		SmallIconTranslat[2] = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fTransBegin,
				Animation.RELATIVE_TO_SELF, fTransAfter, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[2].setDuration(500);
		SmallIconTranslat[2].setFillAfter(true);
		SmallIconTranslat[2].setFillEnabled(true);
		SmallIconTranslat[2].setStartOffset(lStartOffset);// 现在是相对的偏移时间
		
	}
	private void LoadTranslatleft4(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = fOneStepAway;
			fTransAfter = 0;
			break;
		case 1:
			fTransBegin = 0;
			fTransAfter = -fOneStepAway;
			lStartOffset = 0;
			break;
		case 2:
			fTransBegin = -fOneStepAway;
			fTransAfter = -2*fOneStepAway;
			break;
		case 3:
			fTransBegin = -2*fOneStepAway;
			fTransAfter = -3*fOneStepAway;
			break;
		case 4:
			fTransBegin = -3*fOneStepAway;
			fTransAfter = -4*fOneStepAway;
			break;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = -3*fOneStepAway;
			fTransAfter = -4*fOneStepAway;
			break;
		default:
			break;
	    }
		SmallIconTranslat[3] = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fTransBegin,
				Animation.RELATIVE_TO_SELF, fTransAfter, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[3].setDuration(500);
		SmallIconTranslat[3].setFillAfter(true);
		SmallIconTranslat[3].setFillEnabled(true);
		SmallIconTranslat[3].setStartOffset(lStartOffset);// 现在是相对的偏移时间！
		
	}
	private void LoadTranslatToleftSide(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = 0;
		    fTransAfter = fLeftSideAway;
			lStartOffset = 0;
			break;
		case 1:
			fTransBegin = -fOneStepAway;
		    fTransAfter = fTransBegin + fLeftSideAway;
			break;
		case 2:
			fTransBegin = -2*fOneStepAway;
		    fTransAfter = fTransBegin + fLeftSideAway;
			break;
		case 3:
			fTransBegin = -3*fOneStepAway;
		    fTransAfter = fTransBegin + fLeftSideAway;
			break;
		case 4:
			fTransBegin = -4*fOneStepAway;
		    fTransAfter = fTransBegin + fLeftSideAway;
			break;
		case 5:	
		case 6:		
		case 7:			
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = -4*fOneStepAway;
		    fTransAfter = fTransBegin + fLeftSideAway;			
			break;
		default:
			break;
	    }
		SmallIconTranslat[4] = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				fTransBegin, Animation.RELATIVE_TO_SELF,fTransAfter,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[4].setDuration(500);
		SmallIconTranslat[4].setFillAfter(true);
		SmallIconTranslat[4].setFillEnabled(true);
		SmallIconTranslat[4].setStartOffset(lStartOffset);// 现在是相对的偏移时间！
		
	}
	
	private void LoadTranslatLeftsideStill(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = fLeftSideAway;
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 1:
			// 左二泡泡图标靠边
			fTransBegin = fLeftSideAway-fOneStepAway;
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 2:
			fTransBegin = fLeftSideAway-(2*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 3:
			fTransBegin = fLeftSideAway-(3*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 4:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 5:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 6:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
			break;
		case 7:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = fTransBegin;
			//lStartOffset = 5000;
		    break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = fTransBegin;
			break;
		default:
			break;
	    }
		SmallIconTranslat[5] = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				fTransBegin, Animation.RELATIVE_TO_SELF, fTransAfter,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[5].setDuration(5000);
		SmallIconTranslat[5].setFillAfter(true);
		SmallIconTranslat[5].setFillEnabled(true);
		SmallIconTranslat[5].setStartOffset(lStartOffset);// 现在是相对的偏移时间！
	
	}
	private void LoadTranslatBack(){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = fLeftSideAway;
		    fTransAfter = 4*fOneStepAway;
			break;
		case 1:
			fTransBegin = fLeftSideAway-fOneStepAway;
		    fTransAfter = 3*fOneStepAway;
			break;
		case 2:
			fTransBegin = fLeftSideAway-(2*fOneStepAway);
		    fTransAfter = 2*fOneStepAway;
			break;
		case 3:
			fTransBegin = fLeftSideAway-(3*fOneStepAway);
		    fTransAfter = fOneStepAway;
			break;
		case 4:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = 0;
			break;
		case 5:	
		case 6:		
		case 7:		
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = fLeftSideAway-(4*fOneStepAway);
		    fTransAfter = 0;
			break;
		default:
			break;
	   }
		SmallIconTranslat[6] = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				fTransBegin, Animation.RELATIVE_TO_SELF,fTransAfter,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[6].setDuration(500);
		SmallIconTranslat[6].setFillAfter(true);
		SmallIconTranslat[6].setFillEnabled(true);
		SmallIconTranslat[6].setStartOffset(lStartOffset);// 现在是相对的偏移时间
	}
	
	private void LoadTranslatRightsideStill(int i){
		fTransBegin = 0;
	    fTransAfter = 0;
		lStartOffset = 0;
		switch(miIconNum){
		case 0:
			fTransBegin = 4*fOneStepAway;
		    fTransAfter = 4*fOneStepAway;
			break;
		case 1:
			fTransBegin = 3*fOneStepAway;
		    fTransAfter = 3*fOneStepAway;
			break;
		case 2:
			fTransBegin = 2*fOneStepAway;
		    fTransAfter = 2*fOneStepAway;
			break;
		case 3:
			fTransBegin = fOneStepAway;
		    fTransAfter = fOneStepAway;
			break;
		case 4:
		case 5:	
		case 6:		
		case 7:		
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			fTransBegin = 0;
		    fTransAfter = 0;
			break;
		default:
			break;
	   }
		SmallIconTranslat[i] = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				fTransBegin, Animation.RELATIVE_TO_SELF,fTransAfter,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		SmallIconTranslat[i].setDuration(500);
		SmallIconTranslat[i].setFillAfter(true);
		SmallIconTranslat[i].setFillEnabled(true);
		SmallIconTranslat[i].setStartOffset(lStartOffset);// 现在是相对的偏移时间
	}
	
	public int getTranslatAnimatNum() {
		// TODO Auto-generated method stub
		return miAniamtMaxNum;
	}
}
