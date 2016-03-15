package com.huge.foundation.common;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AppException extends Exception
{
  protected Throwable cause = null;

  public AppException()
  {
    super("应用程序发生错误");
  }

  public AppException(String paramString)
  {
    super(paramString);
  }

  public AppException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }

  public Throwable initCause(Throwable paramThrowable)
  {
    this.cause = paramThrowable;
    return paramThrowable;
  }

  public String getMessage()
  {
    if (this.cause != null)
      return super.getMessage() + ": " + this.cause.getMessage();
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

  public String getMessageA()
  {
    return _$1(getMessage());
  }

  private static String _$1(String paramString)
  {
    return paramString.substring(paramString.indexOf("#") + 1, paramString.length());
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.foundation.common.AppException
 * JD-Core Version:    0.6.0
 */