package com.huge.common.query;

import java.io.Serializable;

public class NullSQLType implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1671401645006302416L;
	public static final NullSQLType	ARRAY			= new NullSQLType(2003);
	public static final NullSQLType	BIGINT			= new NullSQLType(-5);
	public static final NullSQLType	BINARY			= new NullSQLType(-2);
	public static final NullSQLType	BIT				= new NullSQLType(-7);
	public static final NullSQLType	BLOB			= new NullSQLType(2004);
	public static final NullSQLType	CHAR			= new NullSQLType(1);
	public static final NullSQLType	CLOB			= new NullSQLType(2005);
	public static final NullSQLType	DATE			= new NullSQLType(91);
	public static final NullSQLType	DECIMAL			= new NullSQLType(3);
	public static final NullSQLType	DISTINCT		= new NullSQLType(2001);
	public static final NullSQLType	DOUBLE			= new NullSQLType(8);
	public static final NullSQLType	FLOAT			= new NullSQLType(6);
	public static final NullSQLType	INTEGER			= new NullSQLType(4);
	public static final NullSQLType	JAVA_OBJECT		= new NullSQLType(2000);
	public static final NullSQLType	LONGVARBINARY	= new NullSQLType(-4);
	public static final NullSQLType	LONGVARCHAR		= new NullSQLType(-1);
	public static final NullSQLType	NULL			= new NullSQLType(0);
	public static final NullSQLType	NUMERIC			= new NullSQLType(2);
	public static final NullSQLType	OTHER			= new NullSQLType(1111);
	public static final NullSQLType	REAL			= new NullSQLType(7);
	public static final NullSQLType	REF				= new NullSQLType(2006);
	public static final NullSQLType	SMALLINT		= new NullSQLType(5);
	public static final NullSQLType	STRUCT			= new NullSQLType(2002);
	public static final NullSQLType	TIME			= new NullSQLType(92);
	public static final NullSQLType	TIMESTAMP		= new NullSQLType(93);
	public static final NullSQLType	TINYINT			= new NullSQLType(-6);
	public static final NullSQLType	VARBINARY		= new NullSQLType(-3);
	public static final NullSQLType	VARCHAR			= new NullSQLType(12);
	private int						fieldType;
	
	public NullSQLType(int paramInt) {
		this.fieldType = paramInt;
	}
	
	public int getFieldType() {
		return this.fieldType;
	}
	
	public String toString() {
		return "" + this.fieldType;
	}
}

/*
 * Location:
 * D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2
 * .0.0\ Qualified Name: com.huge.common.query.NullSQLType JD-Core Version:
 * 0.6.0
 */
