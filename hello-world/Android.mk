LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src)
	 
LOCAL_JAVA_LIBRARIES := \
	com.mediatek.tv \
	com.mediatek.tvcm \
	
LOCAL_PACKAGE_NAME := TVSample

include $(BUILD_PACKAGE)
include $(CLEAR_VARS)

# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
