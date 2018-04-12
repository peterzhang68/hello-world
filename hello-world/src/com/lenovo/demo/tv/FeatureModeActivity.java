package com.lenovo.demo.tv;

import android.R.animator;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.demo.tv.TvProductManager.TV_PRODUCT;
import com.lenovo.demo.tv.IconScaleAnimatType;
import com.lenovo.demo.tv.IconTranslatAnimatType;
import com.lenovo.demo.tv.SmallIconImage;

import com.mediatek.tvcommon.TVCommonManager;
import com.mediatek.tvcommon.TVInputCommon;
import com.mediatek.tvcommon.TVMethods;
import com.mediatek.tvcommon.TVOutputCommon;

public class FeatureModeActivity extends Activity {

    // 定义五个泡泡和8个泡泡图标，上面
    private ImageView[] imageBubble = null;
    private SmallIconImage[] topSmallIcon = null;
    
    // 定义五个泡泡和8个泡泡图标，下面
    private ImageView[] bottomBubble = null;
    private SmallIconImage[] bottomSmallIcon = null;

    // 定义左右两个大泡泡，上面
    private ImageView topLeftBigBubble, bottomLeftBigBubble;
    // 定义左右两个大泡泡，下面
    private RelativeLayout imageRightBig1, imageRightBig2;

    // 定义2个尺寸文字
    private TextView InchTop, InchBottom = null;

    private int iImageBubblecount = 1;
    // 定义标志位
    private boolean m_bIsAsideflag = false;// 标记当前动画，只有靠边时才快进！

    // 新定义的动画，均为全局变量，注意：上下需要进行解耦，添加命名规则加_bottom方便添加删改！
    // 上面一组，命名规则：只包含左移、靠边两个动画
    //private TranslateAnimation[] trans = null;
    // 下面一组，命名规则：只包含左移、靠边两个动画

    //private TranslatAnimatManager[] iconTranslate;
    // Scal small and large animation
    private Animation[] scale_big;
    private Animation[] scale_small;

    // 展示图放大的效果，上面
    private ScaleAnimation[] scale_display;
    // 展示图放大的效果，下面
    // private ScaleAnimation[] scale_display_bottom;

    // handler
    Handler myHandler1, myHandler2 = null;

    // InchSetting minch_setting = null;
    String minch_setting = null;

    private static int mTvType = 0;
    private static int m_iIconScaleAnimatMaxNum = 9;  
    private static int m_iIconMaxNum = TvProductManager.IconMaxNum;
    private static int m_iIconTransMaxNum = m_iIconMaxNum +1;
    private static int m_iBubbleMaxNum = 5;
    private static int m_iCurrentIconNum = 0;
    private static boolean IsTopIconAnimat = false;

    private static final int threeD_postion = 0;

    // 3d framelayout
    private FrameLayout m3DLayoutTop, m3DLayoutBottom;

    private ImageView tv_3d, surf_3d, tv_3d_bottom, surf_3d_bottom;

    public TVCommonManager commonManager;
    public TVOutputCommon outputCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.feature_demo_layout);

	// initialize the image(including
	// imagePop/imagePopIcon/imageDisplayIcon/imageDisplayCharacter/)

	mTvType = TvProductManager.getTvType();
	initImage();
	
	// solve the smear problem, set the image invisible at the beginning.
	initView();

	commonManager = TVCommonManager.getInstance(this);
	outputCommon = TVOutputCommon.getInstance(this);
	// commonManager.changeInputSource("main", "hdmi0");

	outputCommon.setScreenPosition("main", 0.0f, 0.0f, 1.0f, 1.0f);
	// initSourceDialog();

	// start the animation
	initViewsWithAnimation();
    }

    @Override
    protected void onResume() {
	super.onResume();
	startPlay();
    }

    @Override
    protected void onStop() {
	super.onStop();
	stopPlay();
    }

    private void startPlay() {
	TVMethods.getInstance(this).enterTV();
	outputCommon.enterOutputMode(TVInputCommon.OUTPUT_MODE_NORMAL);
	String input = outputCommon.getInput("main");
	outputCommon.connect("main", input);
    }

    private void stopPlay() {
	outputCommon.stop("main");
	TVMethods.getInstance(this).leaveTV();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	// TODO Auto-generated method stub
	super.onWindowFocusChanged(hasFocus);
	SetIconPosition();
    }

    private void SetIconPosition() {

	int[] location = new int[2];
	int iX = 0;
	int iY = 0;
	int iTempx = 0;
	int iTempy = 0;
	int iBubbleWidth = 0;
	int iBubbleHeight = 0;
	int iIconWidth = 0;
	int iIconHeight = 0;
	for (int iBubbleCount = 0; iBubbleCount < m_iBubbleMaxNum; iBubbleCount++) {
	    int iIconCount = iBubbleCount;
	    imageBubble[iBubbleCount].getLocationOnScreen(location);
	    iX = location[0];
	    iY = location[1];
	    Log.d("", "x=" + iX + " y=" + iY);
	    iBubbleWidth = imageBubble[iBubbleCount].getWidth();
	    iBubbleHeight = imageBubble[iBubbleCount].getHeight();
	    iIconWidth = topSmallIcon[iIconCount].getWidth();
	    iIconHeight = topSmallIcon[iIconCount].getHeight();
	    iTempx = (iBubbleWidth - iIconWidth) / 2 + iX;
	    iTempy = (iBubbleHeight - iIconHeight) / 2 + iY + 20;
	    Log.d("", "tempx=" + iTempx + " tempy=" + iTempy);
	    if (iBubbleCount == (m_iBubbleMaxNum - 1)) {
		while (iIconCount < m_iIconMaxNum) {
		    topSmallIcon[iIconCount].setX(iTempx);
		    topSmallIcon[iIconCount].setY(iTempy);
		    iIconCount++;
		}
	    } else {
		topSmallIcon[iIconCount].setX(iTempx);
		topSmallIcon[iIconCount].setY(iTempy);
	    }
	}

	for (int iBubbleCount = 0; iBubbleCount < m_iBubbleMaxNum; iBubbleCount++) {
	    int iIconCount = iBubbleCount;
	    bottomBubble[iBubbleCount].getLocationOnScreen(location);
	    iX = location[0];
	    iY = location[1];
	    Log.d("", "x=" + iX + " y=" + iY);
	    iBubbleWidth = bottomBubble[iBubbleCount].getWidth();
	    iBubbleHeight = bottomBubble[iBubbleCount].getHeight();
	    iIconWidth = bottomSmallIcon[iIconCount].getWidth();
	    iIconHeight = bottomSmallIcon[iIconCount].getHeight();
	    iTempx = (iBubbleWidth - iIconWidth) / 2 + iX;
	    iTempy = (iBubbleHeight - iIconHeight) / 2 + iY;
	    Log.d("", "tempx=" + iTempx + " tempy=" + iTempy);
	    if (iBubbleCount == (m_iBubbleMaxNum - 1)) {
		while (iIconCount < m_iIconMaxNum) {
		    bottomSmallIcon[iIconCount].setX(iTempx);
		    bottomSmallIcon[iIconCount].setY(iTempy);
		    iIconCount++;
		}
	    } else {
		bottomSmallIcon[iIconCount].setX(iTempx);
		bottomSmallIcon[iIconCount].setY(iTempy);
	    }
	}
    }

    private void initImage() {
	// get top five bubbles
	imageBubble = new ImageView[m_iBubbleMaxNum];
	for (int i = 0; i < m_iBubbleMaxNum; i++) {
	    int resId = getResources().getIdentifier("image_bubble" + i, "id",
		    getPackageName());
	    imageBubble[i] = (ImageView) this.findViewById(resId);
	}

	// get top 8 icons
	topSmallIcon = new SmallIconImage[m_iIconMaxNum];
	
	
	for (int i = 0; i < m_iIconMaxNum; i++) {
	    int resId = getResources().getIdentifier("image" + i, "id",
		    getPackageName());
	    topSmallIcon[i] = (SmallIconImage) this.findViewById(resId);
	    topSmallIcon[i].DisplayTextView = (TextView) findViewById(R.id.textViewTop); 
		
		int resDisplayId = getResources().getIdentifier("display" + i, "id",
				getPackageName());
		topSmallIcon[i].imageDisplayIcon = (ImageView)findViewById(resDisplayId);
		topSmallIcon[i].initSmallIconImage(i);
	}

	// get bottom five bubbles
	bottomBubble = new ImageView[m_iBubbleMaxNum];
	for (int i = 0; i < m_iBubbleMaxNum; i++) {
	    int resId = getResources().getIdentifier("image_bubble" + i + "_2",
		    "id", getPackageName());
	    bottomBubble[i] = (ImageView) this.findViewById(resId);
	}

	// get bottom 8 icons
	bottomSmallIcon = new SmallIconImage[m_iIconMaxNum];
	for (int i = 0; i < m_iIconMaxNum; i++) {
	    int resId = getResources().getIdentifier("image" + i + "_2", "id",
		    getPackageName());
	    bottomSmallIcon[i] = (SmallIconImage) this.findViewById(resId);
	    bottomSmallIcon[i].DisplayTextView = (TextView) findViewById(R.id.textViewBottom); 
	    int resDisplayId = getResources().getIdentifier("display" + i+ "_2", "id",
				getPackageName());
	    bottomSmallIcon[i].imageDisplayIcon = (ImageView)findViewById(resDisplayId);
	    bottomSmallIcon[i].initSmallIconImage(i);
	}

	// get 2 top big bubbles
	topLeftBigBubble = (ImageView) this
		.findViewById(R.id.bubble_big_top_left);
	imageRightBig1 = (RelativeLayout) this
		.findViewById(R.id.bubble_big_top_right);
	// get 2 bottom big bubbles
	bottomLeftBigBubble = (ImageView) this
		.findViewById(R.id.bubble_big_bottom_left);
	imageRightBig2 = (RelativeLayout) this
		.findViewById(R.id.bubble_big_bottom_right);

	// 获取2个尺寸文字
	InchTop = (TextView) this.findViewById(R.id.TextViewInchTop);
	InchBottom = (TextView) this.findViewById(R.id.TextViewInchBottom);

	// For Lenovo TV
	minch_setting = TvProductManager.getTvInch();

	InchTop.setText(minch_setting);
	InchBottom.setText(minch_setting);
    }
    /**
     * @name initView
     * 
     *       initialize the pop and icons in the bottom.
     * @version 0.6 2014.2.14
     * @author ChenGuihua
     */
    private void initView() {
	topLeftBigBubble.setAlpha(0f);// 左边大泡泡，上面，设为不可见
	bottomLeftBigBubble.setAlpha(0f);// 左边大泡泡，下面
	imageRightBig2.setAlpha(0f);// 右边大泡泡，下面

	m3DLayoutTop = (FrameLayout) findViewById(R.id.container);
	tv_3d = (ImageView) findViewById(R.id.iv_3d_1);
	surf_3d = (ImageView) findViewById(R.id.iv_3d_2);

	m3DLayoutBottom = (FrameLayout) findViewById(R.id.container_bottom);
	tv_3d_bottom = (ImageView) findViewById(R.id.iv_3d_1_bottom);
	surf_3d_bottom = (ImageView) findViewById(R.id.iv_3d_2_bottom);

	for (int index = 0; index < m_iIconMaxNum; index++) {
	    // 展示的8张图片，上面，设为不可见
	    if (index != threeD_postion) {
	    	topSmallIcon[index].imageDisplayIcon.setAlpha(0f);
		// 展示的8张图片，下面，设为不可见
	    	bottomSmallIcon[index].imageDisplayIcon.setAlpha(0f);
	    }
	    // 8个展示文字，上面，设为不可见
		topSmallIcon[index].DisplayTextView.setAlpha(0f);
	    // 8个展示文字，下面，设为不可见
		bottomSmallIcon[index].DisplayTextView.setAlpha(0f);
	    // 8个泡泡图片，下面，设为不可见
	    bottomSmallIcon[index].setAlpha(0f);
	}
	// imageDisplayIcon2b.setAlpha(0f);
	// imageDisplayIcon2b_bottom.setAlpha(0f);

	for (int index = 0; index < m_iBubbleMaxNum; index++) {
	    bottomBubble[index].setAlpha(0f);
	}

	InchBottom.setAlpha(0f);// 尺寸文字，下面，设为不可见
	
	for(int index = m_iBubbleMaxNum;index < m_iIconMaxNum;index++){
	topSmallIcon[index].setAlpha(0f);// 三个泡泡图标，上面，设为不可见
	}

    }

    /**
     * @name initViewsWithAnimation
     * 
     *       The animation display all the pop and icons.
     * @version 0.60 2014.2.14
     * @author ChenGuihua
     */
    @SuppressLint("HandlerLeak")
    private void initViewsWithAnimation() {
	// scale animation of pop
	startBubbleAnimation();

	// TranslateAnimation of Icon
	startIconAnimation();
    }

    private void startBubbleAnimation() {

	// 泡泡的放大、缩小的动画
	scale_big = new Animation[m_iBubbleMaxNum];
	scale_small = new Animation[m_iBubbleMaxNum];
	// 放大1.25，泡泡1起始0.8
	// 缩小0.8，泡泡1起始1.25
	for (int i = 0; i < m_iBubbleMaxNum; i++) {
	    scale_big[i] = AnimationUtils.loadAnimation(this, R.anim.scale_big);
	    scale_big[i].setStartOffset(i * 50);
	    scale_small[i] = AnimationUtils.loadAnimation(this,
		    R.anim.scale_small);
	    scale_small[i].setStartOffset(i * 50);
	}

	// 上面五个泡泡的缩放动画
	for (int i = 0; i < m_iBubbleMaxNum; i++) {
	    imageBubble[i].startAnimation(scale_big[i]);
	}

	// 下面五个泡泡的缩放动画
	for (int i = 0; i < m_iBubbleMaxNum; i++) {
	    bottomBubble[i].startAnimation(scale_big[i]);
	}

	// add scale_big listener
	for (iImageBubblecount = 0; iImageBubblecount < m_iBubbleMaxNum; iImageBubblecount++) {
	    BubbleScaleListener Listener = new BubbleScaleListener();
	    Listener.SetOffset(iImageBubblecount, true);
	    scale_big[iImageBubblecount].setAnimationListener(Listener);
	}

	// add scale_small listener
	for (iImageBubblecount = 0; iImageBubblecount < m_iBubbleMaxNum; iImageBubblecount++) {
	    BubbleScaleListener Listener = new BubbleScaleListener();
	    Listener.SetOffset(iImageBubblecount, false);
	    scale_small[iImageBubblecount].setAnimationListener(Listener);
	}

    }

    class BubbleScaleListener implements AnimationListener {
	private int m_iBubbleCount;
	private boolean m_bScaleType;

	public void SetOffset(int iBubbleCount, boolean bScaleType) {
	    m_iBubbleCount = iBubbleCount;
	    m_bScaleType = bScaleType;
	}

	@Override
	public void onAnimationStart(Animation animation) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
	    // TODO Auto-generated method stub
	    if (true == m_bScaleType) {
		// scal_big ended,start scale_small
		imageBubble[m_iBubbleCount]
			.startAnimation(scale_small[m_iBubbleCount]);
		bottomBubble[m_iBubbleCount]
			.startAnimation(scale_small[m_iBubbleCount]);
	    } else {
		// scale_small ended,start scale_big
		imageBubble[m_iBubbleCount]
			.startAnimation(scale_big[m_iBubbleCount]);
		bottomBubble[m_iBubbleCount]
			.startAnimation(scale_big[m_iBubbleCount]);
	    }
	}
    }

    private void startIconAnimation() {
	// new the TranslateAnimation object of all the pop and icons
	//initIconTranslateAnimation();

	// scale display
	//initIconScaleAnimation();

    	// set the listener of Icon Translate animation
    	setIconTranslateListener();
    	
    	// set the listener of Icon Scale animation
    	setIconDisplayListener();
    	
    	// start the animation by begin imagePopIcon
    	IsTopIconAnimat = true;
    	
    	topSmallIcon[0].miCurrentTransNum = 4;
		topSmallIcon[1].miCurrentTransNum = 3;
		topSmallIcon[2].miCurrentTransNum = 2;
		topSmallIcon[3].miCurrentTransNum = 1;
		topSmallIcon[4].miCurrentTransNum = 0;
		topSmallIcon[5].miCurrentTransNum = 9;
		topSmallIcon[6].miCurrentTransNum = 8;
		topSmallIcon[7].miCurrentTransNum = 7;
		topSmallIcon[8].miCurrentTransNum = 6;
    	for(int iIconNum = 0 ; iIconNum < m_iIconMaxNum; iIconNum++){
    		topSmallIcon[iIconNum]
    				.SmallIconTranslat[topSmallIcon[iIconNum].miCurrentTransNum].setStartOffset(1000);
    		topSmallIcon[iIconNum].startAnimation(topSmallIcon[iIconNum]
    				.SmallIconTranslat[topSmallIcon[iIconNum].miCurrentTransNum]); // 五个泡泡图标，上面				    
    	}
    	
    }
       	
    private void setIconDisplayListener(){
    	for(int index = 0; index < m_iIconMaxNum ;index++){
    		IconAnimateListener Listener = new IconAnimateListener(index,0,0);
    		topSmallIcon[index].scale_display.setAnimationListener(Listener);
    		//bottomSmallIcon[index].scale_display.setAnimationListener(Listener);
    	}
    	for(int index = 0; index < m_iIconMaxNum ;index++){
    		IconAnimateListener Listener = new IconAnimateListener(index,0,0);
    		//topSmallIcon[index].scale_display.setAnimationListener(Listener);
    		bottomSmallIcon[index].scale_display.setAnimationListener(Listener);
    	}
    }
    
    private void setIconTranslateListener() {
    	for(int iIconNum = 0 ; iIconNum < m_iIconMaxNum; iIconNum++){
    		for(int iAnimatNum = 0; iAnimatNum < m_iIconTransMaxNum;iAnimatNum++){
    			IconAnimateListener  Listener = new IconAnimateListener(iIconNum,1,iAnimatNum);
    			topSmallIcon[iIconNum].SmallIconTranslat[iAnimatNum]
    					.setAnimationListener(Listener);
    		}
        } 
    	
    	for(int iIconNum = 0 ; iIconNum < m_iIconMaxNum; iIconNum++){
    		for(int iAnimatNum = 0; iAnimatNum < m_iIconTransMaxNum;iAnimatNum++){
    			IconAnimateListener  Listener = new IconAnimateListener(iIconNum,1,iAnimatNum);
    			bottomSmallIcon[iIconNum].SmallIconTranslat[iAnimatNum]
    					.setAnimationListener(Listener);
    		}
        } 
    }
    class IconAnimateListener implements AnimationListener{
  		
  		private int m_iIconAnimateType;
  		private int m_iIconNum;
  		private int m_iTransNum;
  		
  		public IconAnimateListener(int iIconNum,int iIconAnimateType,int iIconTransNum){
  			m_iIconNum = iIconNum;
  			m_iIconAnimateType = iIconAnimateType;
  			m_iTransNum = iIconTransNum;
  		}
  		
  		@Override
  		public void onAnimationStart(Animation animation) {
  		// TODO Auto-generated method stub
  			if(0 == m_iIconAnimateType){
//  				if (mTvType == TV_PRODUCT.LENOVO_S9.ordinal()) {
//  					if(IsTopIconAnimat){
//  						topSmallIcon[m_iIconNum].setAlpha(0f);
//  					}
//  					else{
//  						bottomSmallIcon[m_iIconNum].setAlpha(0f);
//  					}
//  				}
  			}
  		}

	@Override
	public void onAnimationRepeat(Animation animation) {
	    // TODO Auto-generated method stub
	}

  	@Override
  	public void onAnimationEnd(Animation animation) {
  	// TODO Auto-generated method stub	
  		Log.d("", "onAnimationEnd enter");
  		Log.d("", "m_iIconAnimateType"+m_iIconAnimateType+"m_iIconNum"+m_iIconNum+"m_iTransNum"+m_iTransNum);
  		if(0 == m_iIconAnimateType){			
  			 if (IsTopIconAnimat) {
//	    		  int resId = getResources().getIdentifier("text_lenovo_" + m_iIconNum, "id",
//	    					 getPackageName());
	    			topSmallIcon[m_iIconNum].DisplayTextView
	    			    .setText(getString(R.string.text_lenovo_0));
	    			topSmallIcon[m_iIconNum].DisplayTextView.setAlpha(1f);
	    			
	    	  } else {
//	    		  int resId = getResources().getIdentifier("text_lenovo_" + m_iIconNum, "id",
//					 getPackageName());
	    		  bottomSmallIcon[m_iIconNum].DisplayTextView
	    		  		.setText(getString(R.string.text_lenovo_0));
	    		  bottomSmallIcon[m_iIconNum].DisplayTextView.setAlpha(1f);
	    	  }						
  		}
 	
  		if(1 == m_iIconAnimateType){
  			
  			 Log.d("", "1 == m_iIconAnimateType");
  			if(IconTranslatAnimatType.TranslatToleftSide == m_iTransNum){
  				 Log.d("", "1 == m_iIconAnimateType,IconTranslatAnimatType.TranslatToleftSide == m_iTransNum");
  				if(IsTopIconAnimat){
  					 Log.d("", "1 == m_iIconAnimateType,IconTranslatAnimatType.TranslatToleftSide == m_iTransNum,IsTopIconAnimat is ture");
  					topLeftBigBubble.setAlpha(1f);// 左边大泡泡显示
  					// 展示图放大的效果
  					topSmallIcon[m_iIconNum].imageDisplayIcon.setAlpha(1f);// 左边展示图1显示		
  					topSmallIcon[m_iIconNum].imageDisplayIcon
  		    			.startAnimation(topSmallIcon[m_iIconNum].scale_display);
  					topSmallIcon[m_iIconNum].miCurrentTransNum = ++topSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  	  				topSmallIcon[m_iIconNum].startAnimation(topSmallIcon[m_iIconNum].
  	  						SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum]);
  					
  					}
  				else{
  					Log.d("", "1 == m_iIconAnimateType,IconTranslatAnimatType.TranslatToleftSide == m_iTransNum,bottom"+m_iIconNum);
  					bottomLeftBigBubble.setAlpha(1f);// 左边大泡泡显示
  					bottomSmallIcon[m_iIconNum].imageDisplayIcon.setAlpha(1f);// 左边展示图1显示
	  	  	  		// 展示图放大的效果
  					bottomSmallIcon[m_iIconNum].imageDisplayIcon
	  	  				.startAnimation(bottomSmallIcon[m_iIconNum].scale_display); 
  					bottomSmallIcon[m_iIconNum].miCurrentTransNum = ++bottomSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
	  		    	bottomSmallIcon[m_iIconNum].startAnimation(bottomSmallIcon[m_iIconNum]
	  		    		.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum]);
  					
  					}
  			}
  			else if(IconTranslatAnimatType.TranslatWaitForDisplay == m_iTransNum){
  				if(IsTopIconAnimat){
  	  				topLeftBigBubble.setAlpha(0f);// 左边大泡泡消失
  	  				topSmallIcon[m_iIconNum].imageDisplayIcon.setAlpha(0f);// 左边展示图8消失				
  	  				topSmallIcon[m_iIconNum].DisplayTextView.setAlpha(0f);
  	  				
  	  				topSmallIcon[m_iIconNum].miCurrentTransNum = ++topSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  	  				topSmallIcon[m_iIconNum].startAnimation(topSmallIcon[m_iIconNum].
  	  						SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum]);
  	  				topSmallIcon[m_iIconNum].setAlpha(0f);// last泡泡图标消失
  	  				Log.d("", "topSmallIcon" + m_iIconNum+"startAnimation"+topSmallIcon[m_iIconNum].miCurrentTransNum);
  	  				
  	  		    	if((m_iIconMaxNum-1) == m_iIconNum){
  						for (int i = 0; i < m_iBubbleMaxNum; i++) {
  							imageBubble[i].setAlpha(0f);// 五个泡泡消失，上面
  							topSmallIcon[i].setAlpha(0f);// 五个泡泡图标消失，上面
  						}
  						
  						imageRightBig1.setAlpha(0f);// 右边大泡泡，上面
  						InchTop.setAlpha(0f);// 尺寸文字，上面，设为不可见
  						// 以下为启动下面的循环过程
  						for (int i = 0; i < m_iBubbleMaxNum; i++) {
  							   bottomBubble[i].setAlpha(1f);// 五个泡泡，下面，设为可见
  							   bottomSmallIcon[i].setAlpha(1f);// 五个泡泡图片，下面，设为可见
  						}

  						imageRightBig2.setAlpha(1f);// 右边大泡泡，下面
  						InchBottom.setAlpha(1f);// 尺寸文字，下面，设为可见

  						IsTopIconAnimat = false;
  						bottomSmallIcon[0].miCurrentTransNum = 4;
  						bottomSmallIcon[1].miCurrentTransNum = 3;
  						bottomSmallIcon[2].miCurrentTransNum = 2;
  						bottomSmallIcon[3].miCurrentTransNum = 1;
  						bottomSmallIcon[4].miCurrentTransNum = 0;
  						bottomSmallIcon[5].miCurrentTransNum = 9;
  						bottomSmallIcon[6].miCurrentTransNum = 8;
  						bottomSmallIcon[7].miCurrentTransNum = 7;
  						bottomSmallIcon[8].miCurrentTransNum = 6;
  				    	for(int iIconNum = 0 ; iIconNum < m_iIconMaxNum; iIconNum++){
  				    		//bottomSmallIcon[iIconNum]
  				    				//.SmallIconTranslat[bottomSmallIcon[iIconNum].miCurrentTransNum].setStartOffset(5000);
  				    		//Log.d("", "bottomSmallIcon[6]"+bottomSmallIcon[6].miCurrentTransNum);
  				    		//Log.d("", "bottomSmallIcon[7]"+bottomSmallIcon[7].miCurrentTransNum);
  				    		//Log.d("", "bottomSmallIcon[8]"+bottomSmallIcon[8].miCurrentTransNum);
  				    		bottomSmallIcon[iIconNum].startAnimation(bottomSmallIcon[iIconNum]
  				    				.SmallIconTranslat[bottomSmallIcon[iIconNum].miCurrentTransNum]); 			    
  				    	}
  	  		    		}
  	  			}
  	  		   else{ 		    		
  	  		    		bottomLeftBigBubble.setAlpha(0f);// 左边大泡泡消失
  	  		    		bottomSmallIcon[m_iIconNum].imageDisplayIcon.setAlpha(0f);// 左边展示图8消失				
  	  		    		bottomSmallIcon[m_iIconNum].DisplayTextView.setAlpha(0f);
  	  		    		    		
  	  		    		bottomSmallIcon[m_iIconNum].miCurrentTransNum = ++bottomSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  	  		    		bottomSmallIcon[m_iIconNum].startAnimation(bottomSmallIcon[m_iIconNum]
  	  		    				.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum]);
  	  		    		bottomSmallIcon[m_iIconNum].setAlpha(0f);// last泡泡图标消失
  	  	  		    	if((m_iIconMaxNum-1) == m_iIconNum){
  	  						for (int i = 0; i < m_iBubbleMaxNum; i++) {
  	  							bottomBubble[i].setAlpha(0f);// 五个泡泡消失，上面
  	  							bottomSmallIcon[i].setAlpha(0f);// 五个泡泡图标消失，上面
  	  						}
  	  						
  	  						imageRightBig2.setAlpha(0f);// 右边大泡泡，上面
  	  						InchBottom.setAlpha(0f);// 尺寸文字，上面，设为不可见
  	  						// 以下为启动下面的循环过程
  	  						for (int i = 0; i < m_iBubbleMaxNum; i++) {
  	  							 imageBubble[i].setAlpha(1f);// 五个泡泡，下面，设为可见
  	  							 topSmallIcon[i].setAlpha(1f);// 五个泡泡图片，下面，设为可见
  	  						}

  	  						imageRightBig1.setAlpha(1f);// 右边大泡泡，下面
  	  						InchTop.setAlpha(1f);// 尺寸文字，下面，设为可见

  	  						IsTopIconAnimat = true;
  	  						topSmallIcon[0].miCurrentTransNum = 4;
  	  						topSmallIcon[1].miCurrentTransNum = 3;
  	  						topSmallIcon[2].miCurrentTransNum = 2;
  	  						topSmallIcon[3].miCurrentTransNum = 1;
  	  						topSmallIcon[4].miCurrentTransNum = 0;
  	  						topSmallIcon[5].miCurrentTransNum = 9;
  	  						topSmallIcon[6].miCurrentTransNum = 8;
  	  						topSmallIcon[7].miCurrentTransNum = 7;
  	  						topSmallIcon[8].miCurrentTransNum = 6;
  	  						for(int iIconNum = 0 ; iIconNum < m_iIconMaxNum; iIconNum++){
  	  							topSmallIcon[iIconNum].startAnimation(topSmallIcon[iIconNum]
  				    				.SmallIconTranslat[topSmallIcon[iIconNum].miCurrentTransNum]); 			    
  	  						}				
  	  	  		    	}
  					    }
  			}
  			else if(m_iTransNum == (m_iIconTransMaxNum-1)){ 
  				if(IsTopIconAnimat){
  					topSmallIcon[m_iIconNum].setAlpha(1f);// 左6泡泡图标显示		
  					topSmallIcon[m_iIconNum].miCurrentTransNum = ++topSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  					topSmallIcon[m_iIconNum]
  							.SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum].setStartOffset(5000);
					topSmallIcon[m_iIconNum].startAnimation(topSmallIcon[m_iIconNum]
							.SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum]);
  				}
  				else{
  					bottomSmallIcon[m_iIconNum].setAlpha(1f);// 左6泡泡图标显示
  					
  					bottomSmallIcon[m_iIconNum].miCurrentTransNum = ++bottomSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  					bottomSmallIcon[m_iIconNum]
  							.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum].setStartOffset(5000);
  					Log.d("", "bottomSmallIcon[6]"+bottomSmallIcon[6].miCurrentTransNum);
			    		Log.d("", "bottomSmallIcon[7]"+bottomSmallIcon[7].miCurrentTransNum);
			    		Log.d("", "bottomSmallIcon[8]"+bottomSmallIcon[8].miCurrentTransNum);
					bottomSmallIcon[m_iIconNum].startAnimation(bottomSmallIcon[m_iIconNum]
							.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum]);
  				}
  			}
  			else{ 	
  				
  				if(IsTopIconAnimat){
  					Log.d("", "normal translate end");
  					topSmallIcon[m_iIconNum].miCurrentTransNum = ++topSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  					topSmallIcon[m_iIconNum]
  							.SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum].setStartOffset(5000);		
				    topSmallIcon[m_iIconNum].startAnimation(topSmallIcon[m_iIconNum]
				    		.SmallIconTranslat[topSmallIcon[m_iIconNum].miCurrentTransNum]);
  				}
  				else{
  					bottomSmallIcon[m_iIconNum].miCurrentTransNum = ++bottomSmallIcon[m_iIconNum].miCurrentTransNum % m_iIconTransMaxNum;
  					bottomSmallIcon[m_iIconNum]
  							.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum].setStartOffset(5000);	
  					Log.d("", "bottomSmallIcon[6]"+bottomSmallIcon[6].miCurrentTransNum);
			    		Log.d("", "bottomSmallIcon[7]"+bottomSmallIcon[7].miCurrentTransNum);
			    		Log.d("", "bottomSmallIcon[8]"+bottomSmallIcon[8].miCurrentTransNum);
  					bottomSmallIcon[m_iIconNum].startAnimation(bottomSmallIcon[m_iIconNum]
  							.SmallIconTranslat[bottomSmallIcon[m_iIconNum].miCurrentTransNum]);
  				}
  			}
  		  }
  	}
    }
    
    	  

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	boolean bTmpflag = m_bIsAsideflag;
	int iCurrentNum = m_iCurrentIconNum;
	super.onKeyDown(keyCode, event);
	switch (keyCode) {
	case KeyEvent.KEYCODE_DPAD_LEFT:
	    if (true == scale_display[iCurrentNum].hasEnded()) {// 要判断一下展示文字的动画是否已经结束！
		if (m_bIsAsideflag) {
		    //iconTranslate[iCurrentNum].mTranslatAside.cancel();
		    if (iCurrentNum == threeD_postion) {
			// cancle 3D
			cancel3dAnimation();
		    }
		}
	    }
	    return true;

	case KeyEvent.KEYCODE_DPAD_CENTER:
	    Log.d("", "KEYCODE_DPAD_CENTER");
	    Intent FeatureIntent = getIntent();
	    String uName = FeatureIntent.getStringExtra("uName");

	    if (true != uName.equals("DemoModeActivity")) {
		Toast.makeText(getApplicationContext(), "按下了【确认】键" + uName,
			Toast.LENGTH_SHORT).show();
		if (m_bIsAsideflag) {
		    if (true == IsTopIconAnimat) {
		    	if (true == scale_display[iCurrentNum].hasEnded()) {// 要判断一下展示文字的动画是否已经结束！
		    		//iconTranslate[iCurrentNum].mTranslatAside
				    	//.setDuration(60000);
		    	}
		    } 
		    else {
		    	if (true == scale_display[iCurrentNum + m_iIconMaxNum]
		    			.hasEnded()) {// 要判断一下展示文字的动画是否已经结束！
		    		//iconTranslate[iCurrentNum].mTranslatAside
				    	//.setDuration(60000);
		    	}
		    }
		}
	    }
	    return true;
	case KeyEvent.KEYCODE_BACK:
	    finish();
	    return true;
	case KeyEvent.KEYCODE_POWER:
	    return false;
	default:
	    return true;
	}

    }

    // 3D animation
    //
    int mDuration = 4000;

    float mCenterX = 0.0f;

    float mCenterY = 0.0f;

    float mDepthZ = 0.0f;

    private AnimationSet as;

    private Rotate3dAnimation rotation;

    private ObjectAnimator scaleX, scaleY;

    private TranslateAnimation trans_3d, trans_3d_surf;

    private void cancel3dAnimation() {
	if (scaleX != null && scaleY != null) {
	    if (scaleX.isRunning()) {
		scaleX.cancel();
	    }
	    if (scaleY.isRunning()) {
		scaleY.cancel();
	    }
	}
	if (as != null) {
	    as.cancel();
	    if (trans_3d_surf != null) {
		trans_3d_surf.cancel();
	    }
	}

    }

    private void scale3DAnimation(View view, final View tv, final View surf) {

	scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 22f);
	scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 22f);
	scaleX.setDuration(500);
	scaleY.setDuration(500);

	scaleX.start();
	scaleY.start();
	scaleY.addListener(new AnimatorListener() {

	    @Override
	    public void onAnimationStart(Animator arg0) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onAnimationRepeat(Animator arg0) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onAnimationEnd(Animator arg0) {
		// TODO Auto-generated method stub
		apply3DAnimation(tv, surf, 0, 55);
	    }

	    @Override
	    public void onAnimationCancel(Animator arg0) {
		// TODO Auto-generated method stub

	    }
	});
    }

    private void apply3DAnimation(View animViewTv, View animViewSurface,
	    float startAngle, float toAngle) {
	float centerX = mCenterX;
	float centerY = mCenterY;
	if (as == null) {
	    as = new AnimationSet(true);
	    if (rotation == null)
		rotation = new Rotate3dAnimation(startAngle, toAngle, centerX,
			centerY, mDepthZ, true);
	    if (trans_3d == null) {
		trans_3d = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
			0f, Animation.RELATIVE_TO_SELF, 0.3f,
			Animation.RELATIVE_TO_SELF, 0f,
			Animation.RELATIVE_TO_SELF, -0.1f);
		trans_3d.setDuration(mDuration);
		trans_3d.setFillAfter(true);
		trans_3d.setInterpolator(new AccelerateInterpolator());
	    }
	    if (trans_3d_surf == null) {
		trans_3d_surf = new TranslateAnimation(
			Animation.RELATIVE_TO_SELF, 0f,
			Animation.RELATIVE_TO_SELF, 0.2f,
			Animation.RELATIVE_TO_SELF, 0f,
			Animation.RELATIVE_TO_SELF, 0f);
	    }
	    as.addAnimation(trans_3d_surf);
	    as.addAnimation(rotation);
	    as.setDuration(mDuration);
	    as.setInterpolator(new AccelerateInterpolator());
	    as.setFillAfter(true);
	}
	animViewSurface.startAnimation(trans_3d);
	animViewTv.startAnimation(as);

    }

    private void Back3DAnimation(View animViewTv, View animViewSurface,
	    float startAngle, float toAngle) {
	float centerX = mCenterX;
	float centerY = mCenterY;
	Rotate3dAnimation rotation = new Rotate3dAnimation(startAngle, toAngle,
		centerX, centerY, mDepthZ, true);
	rotation.setFillAfter(true);
	TranslateAnimation trans = new TranslateAnimation(
		Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF,
		0f, Animation.RELATIVE_TO_SELF, -0.1f,
		Animation.RELATIVE_TO_SELF, 0f);
	trans.setFillAfter(true);
	trans.setInterpolator(new AccelerateInterpolator());

	TranslateAnimation trans1 = new TranslateAnimation(
		Animation.RELATIVE_TO_SELF, -0.2f, Animation.RELATIVE_TO_SELF,
		0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
		0f);
	trans1.setFillAfter(true);

	animViewTv.startAnimation(trans1);
	animViewSurface.startAnimation(trans);
	animViewTv.startAnimation(rotation);
    }
}
