package com.huge.foundation.common;

public class GeneralAppException extends GeneralException
{
  public GeneralAppException()
  {
    super("#一般性应用程序错误");
  }

  public GeneralAppException(String paramString)
  {
    super("#" + paramString, new Throwable("系统错误"));
  }

  public GeneralAppException(String paramString, Throwable paramThrowable)
  {
    super("#" + paramString, paramThrowable);
    this.cause = paramThrowable;
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.foundation.common.GeneralAppException
 * JD-Core Version:    0.6.0
 */