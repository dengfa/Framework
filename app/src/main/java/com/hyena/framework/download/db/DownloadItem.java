/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.download.db;

import com.hyena.framework.database.BaseItem;
import com.hyena.framework.download.Task;

import java.sql.Date;

/**
 * @author yangzc on 15/12/10.
 */
public class DownloadItem extends BaseItem {

    public String mTaskId;
    public String mSrcPath;
    public String mDestPath;
    public long   mDownloaded;
    public long   mTotalLen;
    public int mStatus = Task.STATUS_UNINITED;
    //下载类型
    public String mSourceType;
    //扩展字段
    public String mExt;
    public Date   mAddDate;
}
