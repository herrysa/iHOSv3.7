package com.huge.ecis.inter.helper;

import java.util.Hashtable;

import org.springframework.jdbc.core.JdbcTemplate;



public abstract interface ImportService
{
  public abstract void importData(JdbcTemplate simpleJdbcTemplate, String url, String username, String password, String execsql, String tempTable);
}

