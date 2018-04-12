/*******************************************************************
 * 
 * Copyright (C) 2013 SHARP Corporation.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Shanghai Sharp LAB.
 *
 * com.lenovo.demo.tv
 * TvProductManager
 * TvProductManager.java
 * GKX100120
 * ����1:10:33
 ******************************************************************/
package com.lenovo.demo.tv;

import android.os.Build;

/**
 * @author GKX100120
 * 
 */
public class TvProductManager {
	
	public static int IconMaxNum = 9;
    public enum TV_PRODUCT {
	LENOVO_S52(0), LENOVO_S9(1), SHAPR_LX565(3);
	@Override
	public String toString() {
	    // TODO Auto-generated method stub
	    return String.valueOf(value);
	}

	private int value;

	private TV_PRODUCT(int value) {
	    this.value = value;
	}

    }

    public static int getTvType() {
	String tvType = Build.MODEL;
	/*
	if (tvType.contains("S52")|| tvType.contains("S1")) {
	    return TV_PRODUCT.LENOVO_S52.ordinal();
	} else if (tvType.contains("S9")|| tvType.contains("U1")) {
	    return TV_PRODUCT.LENOVO_S9.ordinal();
	} else if (tvType.contains("LX565")) {
	    return TV_PRODUCT.SHAPR_LX565.ordinal();
	}*/
	//return TV_PRODUCT.SHAPR_LX565.ordinal();
	return TV_PRODUCT.LENOVO_S9.ordinal();
    }

    public static String getTvInch() {
	String tvType = Build.MODEL;
	int iOffset = tvType.indexOf(' ') + 1;
	String tvSize = tvType.trim().substring(iOffset, iOffset + 2);
	return tvSize;
    }

}
