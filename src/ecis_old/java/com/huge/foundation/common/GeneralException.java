package com.huge.foundation.common;

import java.io.PrintStream;
import java.io.PrintWriter;

public class GeneralException extends RuntimeException
{
  protected Throwable cause = null;

  public GeneralException()
  {
    super("一般性错误");
  }

  public GeneralException(String paramString)
  {
    super(paramString);
  }

  public GeneralException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }

  public String getMessage()
  {
    if (this.cause != null)
      return super.getMessage() + ":" + this.cause.getMessage();
    return super.getMessage();
  }

  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.cause != null)
      this.cause.printStackTrace();
  }

  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.cause != null)
      this.cause.printStackTrace(paramPrintStream);
  }

  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.cause != null)
      this.cause.printStackTrace(paramPrintWriter);
  }

  public Throwable getCause()
  {
    return this.cause;
  }

  private static String _$1(String paramString)
  {
    return paramString.substring(paramString.lastIndexOf("#") + 1, paramString.length());
  }

  public String getUserMessage()
  {
    String str = super.getMessage();
    str = _$1(str);
    return str;
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.foundation.common.GeneralException
 * JD-Core Version:    0.6.0
 */